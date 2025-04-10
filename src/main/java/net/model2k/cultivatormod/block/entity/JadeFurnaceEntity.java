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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.model2k.cultivatormod.effect.QiEffect;
import net.model2k.cultivatormod.screen.custom.LowGradeJadeFurnaceMenu;
import net.model2k.cultivatormod.item.ModItems;
import net.neoforged.neoforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class JadeFurnaceEntity extends BlockEntity implements MenuProvider {
    static int QiCost = 10;
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
                for (int i = 0; i < inventory.getSlots(); i++) {
                if(!(inventory.getStackInSlot(i).isEmpty()))
                    switch (i) {
                        case 0 :
                                lowGradeFoundationPillRecipeChecker();
                                spiritPillRecipeChecker();
                                bodyAndBonePillRecipeChecker();
                                heavenAndEarthPillRecipeChecker();
                                bloodBeadRecipeChecker();
                            break;
                        case 1 :
                                lowGradeFoundationPillRecipeChecker();
                                spiritPillRecipeChecker();
                                bodyAndBonePillRecipeChecker();
                                heavenAndEarthPillRecipeChecker();
                                bloodBeadRecipeChecker();
                            break;
                        case 2 :
                                lowGradeFoundationPillRecipeChecker();
                                spiritPillRecipeChecker();
                                bodyAndBonePillRecipeChecker();
                                heavenAndEarthPillRecipeChecker();
                                bloodBeadRecipeChecker();
                            break;
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
    public void lowGradeFoundationPillRecipeChecker(){
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (this.inventory.getStackInSlot(i).is(ModItems.LOW_GRADE_HERB_CLUMP)) {
                for (int o = 0; o < this.inventory.getSlots(); o++) {
                    if (this.inventory.getStackInSlot(o).is(ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE)) {
                        for(int k = 0; k < this.inventory.getSlots(); k++) {
                            if(this.inventory.getStackInSlot(k).is(ModItems.LOW_GRADE_GINSENG)) {
                              if (QiEffect.getQi() >= 10  && !(QiEffect.getQi() - QiCost < 0)) {
                                    clearContents();
                                    this.inventory.setStackInSlot(1, ModItems.LOW_GRADE_FOUNDATION_PILL.toStack(1));
                                    QiEffect.setQi(QiEffect.getQi() - QiCost);
                                    Minecraft.getInstance().player.sendSystemMessage(Component.literal(QiEffect.getQiString()));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void spiritPillRecipeChecker() {
        int slotsFull = 0;
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (this.inventory.getStackInSlot(i).is(ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE)) {
                  slotsFull++;
              if (slotsFull == this.inventory.getSlots()  && !(QiEffect.getQi()- QiCost < 0)) {
                  clearContents();
                  this.inventory.setStackInSlot(1, ModItems.LOW_GRADE_SPIRIT_PILL.toStack());
                  QiEffect.setQi(QiEffect.getQi() - QiCost);
                  Minecraft.getInstance().player.sendSystemMessage(Component.literal(QiEffect.getQiString()));
                  slotsFull = 0;
               }
            }
        }
    }

    public void bodyAndBonePillRecipeChecker(){
        int slotsFull = 0;
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (this.inventory.getStackInSlot(i).is(ModItems.LOW_GRADE_GINSENG )) {
                    slotsFull++;
                if (slotsFull == this.inventory.getSlots() && !(QiEffect.getQi() - QiCost < 0)) {
                    clearContents();
                    this.inventory.setStackInSlot(1, ModItems.LOW_GRADE_BODY_AND_BONE_PILL.toStack());
                    QiEffect.setQi(QiEffect.getQi() - QiCost);
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal(QiEffect.getQiString()));
                    slotsFull = 0;
                }
            }
        }
    }
    public void bloodBeadRecipeChecker()  {
        int slotsFull = 0;
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (this.inventory.getStackInSlot(i).is(ModItems.BLOOD_FROM_EARTH )) {
                slotsFull++;
                if (slotsFull == this.inventory.getSlots() && !(QiEffect.getQi() - QiCost < 0)) {
                    clearContents();
                    this.inventory.setStackInSlot(1, ModItems.LOW_GRADE_BLOOD_BEAD.toStack());
                    QiEffect.setQi(QiEffect.getQi() - QiCost);
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal(QiEffect.getQiString()));
                    slotsFull = 0;
                }
            }
        }
    }
    public void heavenAndEarthPillRecipeChecker()  {
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            if (this.inventory.getStackInSlot(i).is(ModItems.LOW_GRADE_GINSENG )) {
                for(int o = 0; o < this.inventory.getSlots(); o++) {
                    if(this.inventory.getStackInSlot(o).is(ModItems.LOW_GRADE_SPIRIT_FLOWER_BUNDLE)){
                if (!(QiEffect.getQi() - QiCost <= 0)) {
                    clearContents();
                    this.inventory.setStackInSlot(1, ModItems.LOW_GRADE_HEAVEN_AND_EARTH_PILL.toStack());
                    QiEffect.setQi(QiEffect.getQi() - QiCost);
                    Minecraft.getInstance().player.sendSystemMessage(Component.literal(QiEffect.getQiString()));
                      }
                   }
                }
            }
        }
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
