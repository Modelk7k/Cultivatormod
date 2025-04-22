package net.model2k.cultivatormod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.model2k.cultivatormod.advancement.ModAdvancements;
import net.model2k.cultivatormod.advancement.RealmAdvancementTrigger;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.network.ModNetwork;
import net.neoforged.neoforge.common.util.INBTSerializable;

import java.util.HashMap;
import java.util.Map;

public class PlayerData implements INBTSerializable {
    public int tick = 0;
    private String Home = "";
    private String NickName = "";
    private String ChatPrefix = "";
    private String ChatColor = "";
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
    private Map<String, Boolean> QiType = new HashMap<>();
    private Map<String, Boolean> Race = new HashMap<>();
    private Map<String, Boolean> SubRace = new HashMap<>();
    public PlayerData() {
        QiType.put("YangQi", false);
        QiType.put("YinQi", false);
        QiType.put("DemonQi", false);
        QiType.put("HeavenlyQi", false);
        Race.put("Human",false);
        Race.put("Beast",false);
        Race.put("Elf",false);
        Race.put("Undead",false);
        Race.put("LowerImmortal",false);
        Race.put("HigherImmortal",false);
        //Human SubRaces
        SubRace.put("Dwarf",false);
        //Beast SubRace
        SubRace.put("Dragon",false);
        SubRace.put("Phoenix",false);
        SubRace.put("Werewolves",false);
        SubRace.put("Orc",false);
        SubRace.put("Ogre",false);
        //Undead SubRace
        SubRace.put("Vampires",false);
        SubRace.put("Zombie",false);
        //Elf SubRace
        SubRace.put("WoodElf",false);
        SubRace.put("HighElf",false);
        SubRace.put("DarkElf",false);
        //LowerImmortal
        SubRace.put("Angel",false);
        SubRace.put("Demon",false);
        SubRace.put("ShapeShifter",false);
        SubRace.put("BetterZombie",false);
        SubRace.put("ElderDwarf",false);
        SubRace.put("ElderOrc",false);
        SubRace.put("ElderOgre",false);
        SubRace.put("SpiritElf",false);
        SubRace.put("LightElf",false);
        SubRace.put("VoidElf",false);
        SubRace.put("ElderDragon",false);
        SubRace.put("ElderPhoenix",false);
        SubRace.put("ElderVampire",false);
        //HigherImmortals
        SubRace.put("SunElf",false);
        SubRace.put("MoonElf",false);
        SubRace.put("AscendedElf",false);
        SubRace.put("DopplGanger",false);
        SubRace.put("AncientOrc",false);
        SubRace.put("AncientOgre",false);
        SubRace.put("Necromancer",false);
        SubRace.put("AscendedDwarf",false);
        SubRace.put("AncientVampire",false);
        SubRace.put("AncientPhoenix",false);
        SubRace.put("AncientDragon",false);
        SubRace.put("Archangel",false);
        SubRace.put("ArchDemon",false);
    }
    @Override
    public String toString() {
        return "PlayerData{" +
                "Home=" + getHome() +
                "NickName=" + getNickName() +
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
                ", QiQuality=" + getQiQuality() +
                ", YangQi=" + getQiType("YangQi") +
                ", YinQi=" + getQiType("YinQi") +
                ", DemonQi=" + getQiType("DemonQi") +
                ", Human=" + getRace("Human") +
                ", Beast=" + getRace("Beast") +
                ", Elf=" + getRace("Elf") +
                ", Undead=" + getRace("Undead") +
                ", LowerImmortal=" + getRace("LowerImmortal") +
                ", Dwarf=" + getSubRace("Dwarf") +
                ", Dragon=" + getSubRace("Dragon") +
                ", Phoenix=" + getSubRace("Phoenix") +
                ", Werewolf=" + getSubRace("Werewolf") +
                ", Vampire=" + getSubRace("Vampire") +
                ", Zombie=" + getSubRace("Zombie") +
                ", WoodElf=" + getSubRace("WoodElf") +
                ", HighElf=" + getSubRace("HighElf") +
                ", DarkElf=" + getSubRace("DarkElf") +
                ", Angel=" + getSubRace("Angel") +
                ", Demon=" + getSubRace("Demon") +
                ", ShapeShifter=" + getSubRace("ShapeShifter") +
                ", BetterZombie=" + getSubRace("BetterZombie") +
                ", ElderDwarf=" + getSubRace("ElderDwarf") +
                ", SpiritElf=" + getSubRace("SpiritElf") +
                ", LightElf=" + getSubRace("LightElf") +
                ", VoidElf=" + getSubRace("VoidElf") +
                ", ElderDragon=" + getSubRace("ElderDragon") +
                ", ElderVampire=" + getSubRace("ElderVampire") +
                ", ElderPhoenix=" + getSubRace("ElderPhoenix") +
                ", SunElf=" + getSubRace("SunElf") +
                ", MoonElf=" + getSubRace("MoonElf") +
                ", AscendedElf=" + getSubRace("AscendedElf") +
                ", DopplGanger=" + getSubRace("DopplGanger") +
                ", Necromancer=" + getSubRace("Necromancer") +
                ", AscendedDwarf=" + getSubRace("AscnededDwarf") +
                ", AncientVampire=" + getSubRace("AncientVampire") +
                ", AncientPhoenix=" + getSubRace("AncientPhoenix") +
                ", AncientDragon=" + getSubRace("AncientDragon") +
                ", Archangel=" + getSubRace("Archangel") +
                ", ArchDemon=" + getSubRace("Archdemon") +
                '}';
    }
    public String getHome() {
        return this.Home;
    }
    public void setHome(String home) {
        this.Home = home;
    }
    public String getNickName() {
        return this.NickName;
    }
    public void setNickName(String nickName) {
        this.NickName = nickName;
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
    public boolean getQiType (String type) {
        return this.QiType.getOrDefault(type, false)
                ;}
    public void setQiType(String qiType, boolean value) {
        this.QiType.put(qiType, value)
        ;}
    public boolean getRace (String race) {
        return this.Race.getOrDefault(race, false)
                ;}
    public void setRace(String race, boolean value) {
        this.Race.put(race, value)
        ;}
    public boolean getSubRace (String subRace) {
        return this.SubRace.getOrDefault(subRace, false)
                ;}
    public void setSubRace(String subRace, boolean value) {
        this.SubRace.put(subRace, value)
        ;}
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
                                RealmAdvancementTrigger trigger = ModAdvancements.REALM_ADVANCEMENT_TRIGGER.get();
                                trigger.trigger((ServerPlayer) player);
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
    public Map<String, Boolean> getAllSubRaces() {
        return this.SubRace;
    }
    public void syncStatsToClient(Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            ModNetwork.sendSyncPlayerData(serverPlayer); // Sends packet to actual client
        }
    }
    @Override
    public Tag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Home", getHome());
        tag.putString("NickName", getNickName());
        tag.putString("ChatPrefix", getChatPrefix());
        tag.putString("ChatColor", getChatColor());
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
        for (String key : this.QiType.keySet()) {
            tag.putBoolean("QiType_" + key, getQiType(key));
        }
        for (String key : this.Race.keySet()) {
            tag.putBoolean("Race_" + key, getRace(key));
        }
        for (String key : this.SubRace.keySet()) {
            tag.putBoolean("SubRace_" + key, getSubRace(key));
        }
        return tag;
    }
    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag tag) {
        if (tag instanceof CompoundTag compoundTag) {
            setHome(compoundTag.getString("Home"));
            setNickName(compoundTag.getString("NickName"));
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
            for (String key : this.QiType.keySet()) {
                setQiType(key, compoundTag.getBoolean("QiType_" + key));
            }
            for (String key : this.Race.keySet()) {
                setRace(key, compoundTag.getBoolean("Race_" + key));
            }
            for (String key : this.SubRace.keySet()) {
                setSubRace(key, compoundTag.getBoolean("SubRace_" + key));
            }
        }
    }
}