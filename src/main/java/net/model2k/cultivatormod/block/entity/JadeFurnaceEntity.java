package net.model2k.cultivatormod.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;
import net.model2k.cultivatormod.recipe.JadeFurnaceRecipe;
import net.model2k.cultivatormod.recipe.JadeFurnaceRecipeInput;
import net.model2k.cultivatormod.recipe.ModRecipes;
import net.model2k.cultivatormod.screen.custom.LowGradeJadeFurnaceMenu;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JadeFurnaceEntity extends BlockEntity implements MenuProvider {
    static int QiCost = 10;
    static Player CurrentPlayer;
    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return slot != 3;
        }
        @Override
        protected int getStackLimit(int slot, @NotNull ItemStack stack) {
            if (slot == 3) {
                return 64;
            }
            return 1;
        }
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
                PlayerData data = CurrentPlayer.getData(ModAttachments.PLAYER_DATA);
                if (data.getQi() - QiCost >= 0) {
                    if (hasRecipe()) {
                        data.setQi(data.getQi() - QiCost);
                        ModNetwork.sendSyncPlayerData((ServerPlayer)CurrentPlayer);
                    }
                }
            }
        }
    };
    public JadeFurnaceEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.JADE_FURNACE_BE.get(), pos, blockState);
    }
    public void drops() {
        SimpleContainer inv = new SimpleContainer(this.inventory.getSlots());
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, this.inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }
    public void clearContents() {
        for (int i = 0; i < 3; i++) {
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", this.inventory.serializeNBT(registries));
    }
    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
    private boolean hasRecipe() {
        for (int i = 0; i < 3; i++) {
            if (this.inventory.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }
        Optional<RecipeHolder<JadeFurnaceRecipe>> recipe = getCurrentRecipe();
        if (recipe.isEmpty()) {
            return false;
        }
        if (this.inventory.getStackInSlot(3).isEmpty()) {
            clearContents();
            this.inventory.setStackInSlot(3, recipe.get().value().output().copy());
            return true;
        } return false;
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
            }
        }
        System.out.println("No matching recipe found.");
        return Optional.empty();
    }
    public static void qiEfficiency () {
       QiCost -= 1;
    }
    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Jade Furnace");
    }
    @Nullable
    @Override
    public  AbstractContainerMenu createMenu(int containerId, @NotNull Inventory playerInventory, @NotNull Player player) {
        return new LowGradeJadeFurnaceMenu(containerId, playerInventory, this);
    }
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.@NotNull Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }
    public static void onPlayerInteracts(Player player) {
        CurrentPlayer = player;
    }
}
