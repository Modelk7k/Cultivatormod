package net.model2k.cultivatormod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.network.ModNetwork;
import net.model2k.cultivatormod.network.packet.PlayerStatsClient;
import net.neoforged.neoforge.common.util.INBTSerializable;

public class PlayerData implements INBTSerializable {
    public int tick = 0;
    private String ChatPrefix = "";
    private String ChatColor = "WHITE";
    private boolean CanFly = false;
    private int Health = 20;
    private boolean FirstQiType = false;
    private int MinorRealm = 0;
    private int MajorRealm = 0;
    private int Strength = 0;
    private int Qi = 0;
    private int MaxQi = 10;
    private int SpiritPower;
    private int MaxSpiritPower = 10;
    private int QiQuality = 0;
    private boolean YangQi = false;
    private boolean YinQi = false;
    private boolean HeavenlyQi = false;
    private boolean DemonQi = false;

    @Override
    public String toString() {
        return "PlayerData{" +
                "ChatPrefix=" + getChatPrefix() +
                "ChatColor=" + getChatColor() +
                "CanFly=" + getCanFly() +
                "Health=" + getHealth() +
                "FirstQiType=" + getFirstQiType() +
                "MinorRealm=" + getMinorRealm() +
                "MajorRealm=" + getMajorRealm() +
                "Strength=" + getStrength() +
                "Qi=" + getQi() +
                ", MaxQi=" + getMaxQi() +
                ", SpiritPower=" + getSpiritPower() +
                ", MaxSpiritPower=" + getMaxSpiritPower() +
                ", YangQiQuality=" + getQiQuality() +
                ", YangQi=" + getYangQi() +
                ", YinQi=" + getYinQi() +
                ", DemonQi=" + getDemonQi() +
                ", HeavenlyQi=" + getHeavenlyQi() +
                '}';
    }
    public String getChatPrefix() {
        return this.ChatPrefix;
    }
    public void setChatPrefix(String prefix) {
        this.ChatPrefix = prefix;
    }
    public String getChatColor() {
        return this.ChatColor;
    }
    public void setChatColor(String color) {
        this.ChatColor = color;
    }
    public boolean getCanFly() {
        return this.CanFly;
    }
    public void setCanFly(boolean canFly) {
        this.CanFly = canFly;
    }
    public int getHealth() {
        return this.Health;
    }
    public void setHealth(int health) {
        this.Health = health;
    }
    public boolean getFirstQiType() {
        return this.FirstQiType;
    }
    public void setFirstQiType(boolean firstQiType) {
        this.FirstQiType = firstQiType;
    }
    public int getMinorRealm() {
        return this.MinorRealm;
    }
    public void setMinorRealm(int minorRealm) {
        this.MinorRealm = minorRealm;
    }
    public int getMajorRealm() {
        return this.MajorRealm;
    }
    public void setMajorRealm(int majorRealm) {
        this.MajorRealm = majorRealm;
    }
    public int getStrength() {
        return this.Strength;
    }
    public void setStrength(int strength) {
        this.Strength = strength;
    }
    public int getQi() {
        return this.Qi;
    }
    public void setQi(int qi) {
        this.Qi = qi;
    }
    public int getMaxQi() {
        return this.MaxQi;
    }
    public void setMaxQi(int maxQi) {
        this.MaxQi = maxQi;
    }
    public int getSpiritPower() {
        return this.SpiritPower;
    }
    public void setSpiritPower(int spiritPower) {
        this.SpiritPower = spiritPower;
    }
    public int getMaxSpiritPower() {
        return this.MaxSpiritPower;
    }
    public void setMaxSpiritPower(int maxSpiritPower) {
        this.MaxSpiritPower = maxSpiritPower;
    }
    public int getQiQuality() {
        return this.QiQuality;
    }
    public void setQiQuality(int qiQuality) {
        this.QiQuality = qiQuality;
    }
    public boolean getYangQi() {
        return this.YangQi;
    }
    public void setYangQi(boolean yangQi) {
        this.YangQi = yangQi;
    }
    public boolean getYinQi() {
        return this.YinQi;
    }
    public void setYinQi(boolean yinQi) {
        this.YinQi = yinQi;
    }
    public boolean getHeavenlyQi() {
        return this.HeavenlyQi;
    }
    public void setHeavenlyQi(boolean heavenlyQi) {
        this.HeavenlyQi = heavenlyQi;
    }
    public boolean getDemonQi() {
        return this.DemonQi;
    }
    public void setDemonQi(boolean demonQi) {
        this.DemonQi = demonQi;
    }
    public void charge(Player player) {
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        tick++;
        if (tick >= 20) {
            if (player.isHolding(ModItems.BAODING_BALLS.get()) && data.getQi() + qiChargeEfficiency(data) <= data.getMaxQi() && player.isShiftKeyDown()) {
                data.setQi(data.getQi() + qiChargeEfficiency(data));
                realmChecker(player);
                syncStatsToClient(player);
                tick = 0;
            }
            if (player.isHolding(ModItems.BAODING_BALLS.get()) && data.getSpiritPower() + spiritPowerChargeEfficiency(data) <= data.getMaxSpiritPower() && player.isShiftKeyDown()) {
                data.setSpiritPower(data.getSpiritPower() + data.spiritPowerChargeEfficiency(data));
                realmChecker(player);
                syncStatsToClient(player);
                tick = 0;
            }
            if (player.isHolding(ModItems.BAODING_BALLS.get()) && data.getQi() + qiChargeEfficiency(data) > data.getMaxQi() && player.isShiftKeyDown()) {
                data.setQi(data.getMaxQi());
                realmChecker(player);
                syncStatsToClient(player);
                tick = 0;
            }
            if (player.isHolding(ModItems.BAODING_BALLS.get()) && data.getSpiritPower() + spiritPowerChargeEfficiency(data) > data.getMaxSpiritPower() && player.isShiftKeyDown()) {
                data.setSpiritPower(data.getMaxSpiritPower());
                realmChecker(player);
                syncStatsToClient(player);
                tick = 0;
            }
        }
    }
    public int qiChargeEfficiency(PlayerData data) {
        return data.getMaxQi() / 10;
    }
    public int spiritPowerChargeEfficiency(PlayerData data) {
        return data.getMaxSpiritPower() / 10;
    }
    public void realmChecker(Player player) {
        switch (getMajorRealm()) {
            case 0:
                switch (getMinorRealm()) {
                    case 0:
                        if (getSpiritPower() >= 25 && getQi() >= 50 && getQiQuality() >= 1) {
                            setMinorRealm(1);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 1:
                        if (getSpiritPower() >= 50 && getQi() >= 100 && getQiQuality() >= 2) {
                            setMinorRealm(2);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 2:
                        if (getSpiritPower() >= 75 && getQi() >= 150 && getQiQuality() >= 2) {
                            setMinorRealm(3);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 3:
                        if (getSpiritPower() >= 100 && getQi() >= 200 && getQiQuality() >= 3) {
                            setMinorRealm(4);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 4:
                        if (getSpiritPower() >= 200 && getQi() >= 250 && getQiQuality() >= 3) {
                            setMinorRealm(5);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 5:
                        if (getSpiritPower() >= 300 && getQi() >= 300 && getQiQuality() >= 4) {
                            setMinorRealm(6);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 6:
                        if (getSpiritPower() >= 400 && getQi() >= 500 && getQiQuality() >= 4) {
                            setMinorRealm(7);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 7:
                        if (getSpiritPower() >= 500 && getQi() >= 600 && getQiQuality() >= 5) {
                            setMinorRealm(8);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 8:
                        if (getSpiritPower() >= 750 && getQi() >= 800 && getQiQuality() >= 5) {
                            setMinorRealm(9);
                            player.sendSystemMessage(Component.literal("You broke through to minor realm " + (getMinorRealm() + 1)));
                            break;
                        }
                    case 9:
                        if (getSpiritPower() >= 1000 && getQi() >= 1000 && getQiQuality() >= 5) {
                            setMajorRealm(1);
                            player.sendSystemMessage(Component.literal("You broke through to Major realm " + getMajorRealm()));
                            player.sendSystemMessage(Component.literal("You can fly nerd"));
                            player.getAbilities().mayfly = true;  // Allow flying
                            player.onUpdateAbilities();           // Sync to client
                            setCanFly(true);
                            break;
                        }
                        break;
                }
            case 1:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 2:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 3:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 4:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 5:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 6:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 7:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 8:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
            case 9:
                switch (getMinorRealm()) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
                break;
        }
    }
    public void syncStatsToClient(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ModNetwork.sendSyncPlayerData(serverPlayer); // Sends packet to actual client
        }
    }
    @Override
    public Tag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putString("ChatPrefix", getChatPrefix());  // Save the prefix as a string
        tag.putString("ChatColor", getChatColor());    // Save the color as a string
        tag.putBoolean("CanFly", getCanFly());
        tag.putInt("Health", getHealth());
        tag.putBoolean("FirstQiType", getFirstQiType());
        tag.putInt("MinorRealm", getMinorRealm());
        tag.putInt("MajorRealm", getMajorRealm());
        tag.putInt("Strength", getStrength());
        tag.putInt("Qi", getQi());
        tag.putInt("MaxQi", getMaxQi());
        tag.putInt("SpiritPower", getSpiritPower());
        tag.putInt("MaxSpiritPower", getMaxSpiritPower());
        tag.putInt("QiQuality", getQiQuality());
        tag.putBoolean("YangQi", getYangQi());
        tag.putBoolean("YinQi", getYinQi());
        tag.putBoolean("DemonQi", getDemonQi());
        tag.putBoolean("HeavenlyQi", getHeavenlyQi());
        return tag;
    }
    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag tag) {
        if (tag instanceof CompoundTag compoundTag) {
            setChatPrefix(compoundTag.getString("ChatPrefix"));
            setChatColor(compoundTag.getString("ChatColor"));
            setCanFly(compoundTag.getBoolean("CanFly"));
            setHealth(compoundTag.getInt("Health"));
            setFirstQiType(compoundTag.getBoolean("FirstQiType"));
            setMinorRealm(compoundTag.getInt("MinorRealm"));
            setMajorRealm(compoundTag.getInt("MajorRealm"));
            setStrength(compoundTag.getInt("Strength"));
            setQi(compoundTag.getInt("Qi"));
            setMaxQi(compoundTag.getInt("MaxQi"));
            setSpiritPower(compoundTag.getInt("SpiritPower"));
            setMaxSpiritPower(compoundTag.getInt("MaxSpiritPower"));
            setQiQuality(compoundTag.getInt("QiQuality"));
            setYangQi(compoundTag.getBoolean("YangQi"));
            setYinQi(compoundTag.getBoolean("YinQi"));
            setDemonQi(compoundTag.getBoolean("DemonQi"));
            setHeavenlyQi(compoundTag.getBoolean("HeavenlyQi"));
        }
    }
}