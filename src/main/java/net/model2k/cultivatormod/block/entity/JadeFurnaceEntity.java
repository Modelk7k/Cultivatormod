package net.model2k.cultivatormod.block.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.model2k.cultivatormod.effect.QiEffect;
import net.model2k.cultivatormod.recipe.JadeFurnaceRecipe;
import net.model2k.cultivatormod.recipe.JadeFurnaceRecipeInput;
import net.model2k.cultivatormod.recipe.ModRecipes;
import net.model2k.cultivatormod.screen.custom.LowGradeJadeFurnaceMenu;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JadeFurnaceEntity extends BlockEntity implements MenuProvider {
    static int QiCost = 10;
    private static final int OUTPUT_SLOT = 1;
    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                if (QiEffect.Qi - QiCost >= 0) {
                    hasRecipe();
                }
            }
        }
    };
    public JadeFurnaceEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.JADE_FURNACE_BE.get(), pos, blockState);
    }
    public void drops() {
        SimpleContainer inv = new SimpleContainer(this.inventory.getSlots());
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            inv.setItem(i, this.inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }
    public void clearContents() {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
    }
    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
    private boolean hasRecipe() {
        // Ensure all slots are filled with valid items
        for (int i = 0; i < 3; i++) {
            if (this.inventory.getStackInSlot(i).isEmpty()) {
                System.out.println("Slot " + i + " is empty or contains air, skipping recipe match.");
                return false;
            }
        }
        // Proceed to check the recipe
        Optional<RecipeHolder<JadeFurnaceRecipe>> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            System.out.println("No matching recipe found.");
            return false;
        }
        clearContents();
        this.inventory.setStackInSlot(1, recipe.get().value().output().copy()); // assuming slot 1 is output
        return true;
    }
    private Optional<RecipeHolder<JadeFurnaceRecipe>> getCurrentRecipe() {
        List<ItemStack> inputs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            inputs.add(stack);
        }
        JadeFurnaceRecipeInput input = new JadeFurnaceRecipeInput(inputs);
        List<RecipeHolder<JadeFurnaceRecipe>> allRecipes = level.getRecipeManager().getAllRecipesFor(ModRecipes.JADE_FURNACE_TYPE.get());
        for (RecipeHolder<JadeFurnaceRecipe> recipeHolder : allRecipes) {
            if (recipeHolder.value().matches(input, level)) {
                return Optional.of(recipeHolder);
            } else {
                System.out.println("No match for this recipe.");
            }
        }
        System.out.println("No matching recipe found.");
        return Optional.empty();
    }
    public static void qiEfficiency () {
       QiCost -= 1;
    }
    @Override
    public Component getDisplayName() {
        return Component.literal("Jade Furnace");
    }
    @Nullable
    @Override
    public  AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new LowGradeJadeFurnaceMenu(containerId, playerInventory, this);
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
}
