package net.model2k.cultivatormod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.model2k.cultivatormod.advancement.ModAdvancements;
import net.model2k.cultivatormod.advancement.RealmAdvancementTrigger;
import net.model2k.cultivatormod.item.ModItems;
import net.model2k.cultivatormod.network.ModNetwork;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PlayerData implements INBTSerializable {
    private int SpeedLevel = 1, JumpStrength = 1,MaxHealth = 20, Health = 20, Defense = 0, Jump = 0, Dash = 0, Speed = 0,  SpiritPower,
            MaxSpiritPower = 10, QiQuality = 0, MinorRealm = 0, MajorRealm = 0, Strength = 0, Qi = 0, MaxQi = 10,tick = 0;
    private String Home = "", NickName = "", ChatPrefix = "", ChatColor = "";
    private boolean CanFly = false, WalkOnWater = false, CanDash = false, FirstQiType = false;
    private final Map<String, Boolean> QiType = new HashMap<>(), Body = new HashMap<>(), Principles = new HashMap<>(), Race = new HashMap<>();
    private final Map<String, String> Homes = new HashMap<>();
    public PlayerData() {
        //QiTypes
        QiType.put("Earth Qi", false);
        QiType.put("Fire Qi", false);
        QiType.put("Water Qi", false);
        QiType.put("Wood Qi", false);
        QiType.put("Metal Qi", false);
        //Bodies
        Body.put("Pill Body", false);
        Body.put("Artifact Body", false);
        Body.put("Dragon Body", false);
        Body.put("Phoenix Body", false);
        Body.put("Poison Body", false);
        Body.put("Ghost Body", false);
        Body.put("Demon Body", false);
        Body.put("Heaven Body", false);
        Body.put("Vampiric Body", false);
        Body.put("Beast Body", false);
        //Principles
        Principles.put("Yang Principle", false);
        Principles.put("Yin Principle", false);
        Principles.put("Time Principle", false);
        Principles.put("Space Principle", false);
        Principles.put("Sword Principle", false);
        Principles.put("Spear Principle", false);
        Principles.put("Axe Principle", false);
        Principles.put("Hammer Principle", false);
        Principles.put("Scythe Principle", false);
        Principles.put("Wind Principle", false);
        Principles.put("Ice Principle", false);
        Principles.put("Sound Principle", false);
        Principles.put("Strength Principle", false);
        Principles.put("Gold Principle", false);
        Principles.put("Blood Principle", false);

        //Human SubRaces
        Race.put("Human", false);
        Race.put("Dwarf", false);
        //Beast SubRace
        Race.put("Dragon", false);
        Race.put("Phoenix", false);
        Race.put("Werewolf", false);
        Race.put("Orc", false);
        Race.put("Ogre", false);
        //Undead SubRace
        Race.put("Vampire", false);
        Race.put("Zombie", false);
        //Elf SubRace
        Race.put("WoodElf", false);
        Race.put("HighElf", false);
        Race.put("DarkElf", false);
        //LowerImmortal
        Race.put("Angel", false);
        Race.put("Demon", false);
        Race.put("Shape Shifter", false);
        Race.put("Better Zombie", false);
        Race.put("Elder Dwarf", false);
        Race.put("Elder Orc", false);
        Race.put("Elder Ogre", false);
        Race.put("Spirit Elf", false);
        Race.put("Light Elf", false);
        Race.put("Void Elf", false);
        Race.put("Elder Dragon", false);
        Race.put("Elder Phoenix", false);
        Race.put("Elder Vampire", false);
        //HigherImmortals
        Race.put("Sun Elf", false);
        Race.put("Moon Elf", false);
        Race.put("Ascended Elf", false);
        Race.put("Doppl Ganger", false);
        Race.put("Ancient Orc", false);
        Race.put("Ancient Ogre", false);
        Race.put("Necromancer", false);
        Race.put("Ascended Dwarf", false);
        Race.put("Ancient Vampire", false);
        Race.put("Ancient Phoenix", false);
        Race.put("Ancient Dragon", false);
        Race.put("Archangel", false);
        Race.put("ArchDemon", false);
    }
    @Override
    public String toString() {
        return "PlayerData{" +
                "SpeedLevel=" + getSpeedLevel() +
                ", JumpStrength=" + getJumpStrength() +
                ", Home='" + getHome() + '\'' +
                ", NickName='" + getNickName() + '\'' +
                ", ChatPrefix='" + getChatPrefix() + '\'' +
                ", ChatColor='" + getChatColor() + '\'' +
                ", CanFly=" + getCanFly() +
                ", WalkOnWater=" + getWalkOnWater() +
                ", CanDash=" + getCanDash() +
                ", MaxHealth=" + getMaxHealth() +
                ", Health=" + getHealth() +
                ", Defense=" + getDefense() +
                ", Speed=" + getSpeed() +
                ", Jump=" + getJump() +
                ", Dash=" + getDash() +
                ", FirstQiType=" + getFirstQiType() +
                ", MinorRealm=" + getMinorRealm() +
                ", MajorRealm=" + getMajorRealm() +
                ", Strength=" + getStrength() +
                ", Qi=" + getQi() +
                ", MaxQi=" + getMaxQi() +
                ", SpiritPower=" + getSpiritPower() +
                ", MaxSpiritPower=" + getMaxSpiritPower() +
                ", QiQuality=" + getQiQuality() +
                ", EarthQi=" + getQiType("Earth Qi") +
                ", FireQi=" + getQiType("Fire Qi") +
                ", WaterQi=" + getQiType("Water Qi") +
                ", WoodQi=" + getQiType("Wood Qi") +
                ", MetalQi=" + getQiType("Metal Qi") +
                ", PillBody=" + getBody("Pill Body") +
                ", ArtifactBody=" + getBody("Artifact Body") +
                ", DragonBody=" + getBody("Dragon Body") +
                ", PhoenixBody=" + getBody("Phoenix Body") +
                ", PoisonBody=" + getBody("Poison Body") +
                ", GhostBody=" + getBody("Ghost Body") +
                ", DemonBody=" + getBody("Demon Body") +
                ", HeavenBody=" + getBody("Heaven Body") +
                ", VampiricBody=" + getBody("Vampiric Body") +
                ", YangPrinciple=" + getPrinciples("Yang Principle") +
                ", YinPrinciple=" + getPrinciples("Yin Principle") +
                ", TimePrinciple=" + getPrinciples("Time Principle") +
                ", SpacePrinciple=" + getPrinciples("Space Principle") +
                ", SwordPrinciple=" + getPrinciples("Sword Principle") +
                ", SpearPrinciple=" + getPrinciples("Spear Principle") +
                ", AxePrinciple=" + getPrinciples("Axe Principle") +
                ", HammerPrinciple=" + getPrinciples("Hammer Principle") +
                ", ScythePrinciple=" + getPrinciples("Scythe Principle") +
                ", WindPrinciple=" + getPrinciples("Wind Principle") +
                ", IcePrinciple=" + getPrinciples("Ice Principle") +
                ", SoundPrinciple=" + getPrinciples("Sound Principle") +
                ", StrengthPrinciple=" + getPrinciples("Strength Principle") +
                ", GoldPrinciple=" + getPrinciples("Gold Principle") +
                ", BloodPrinciple=" + getPrinciples("Blood Principle") +
                ", Human=" + getRace("Human") +
                ", Dwarf=" + getRace("Dwarf") +
                ", Dragon=" + getRace("Dragon") +
                ", Phoenix=" + getRace("Phoenix") +
                ", Werewolf=" + getRace("Werewolf") +
                ", Orc=" + getRace("Orc") +
                ", Ogre=" + getRace("Ogre") +
                ", Vampire=" + getRace("Vampire") +
                ", Zombie=" + getRace("Zombie") +
                ", WoodElf=" + getRace("WoodElf") +
                ", HighElf=" + getRace("HighElf") +
                ", DarkElf=" + getRace("DarkElf") +
                ", Angel=" + getRace("Angel") +
                ", Demon=" + getRace("Demon") +
                ", ShapeShifter=" + getRace("Shape Shifter") +
                ", BetterZombie=" + getRace("Better Zombie") +
                ", ElderDwarf=" + getRace("Elder Dwarf") +
                ", ElderOrc=" + getRace("Elder Orc") +
                ", ElderOgre=" + getRace("Elder Ogre") +
                ", SpiritElf=" + getRace("Spirit Elf") +
                ", LightElf=" + getRace("Light Elf") +
                ", VoidElf=" + getRace("Void Elf") +
                ", ElderDragon=" + getRace("Elder Dragon") +
                ", ElderPhoenix=" + getRace("Elder Phoenix") +
                ", ElderVampire=" + getRace("Elder Vampire") +
                ", SunElf=" + getRace("Sun Elf") +
                ", MoonElf=" + getRace("Moon Elf") +
                ", AscendedElf=" + getRace("Ascended Elf") +
                ", DopplGanger=" + getRace("Doppl Ganger") +
                ", Necromancer=" + getRace("Necromancer") +
                ", AscendedDwarf=" + getRace("Ascended Dwarf") +
                ", AncientVampire=" + getRace("Ancient Vampire") +
                ", AncientPhoenix=" + getRace("Ancient Phoenix") +
                ", AncientDragon=" + getRace("Ancient Dragon") +
                ", Archangel=" + getRace("Archangel") +
                ", ArchDemon=" + getRace("ArchDemon") +
                '}';
    }
    public int getSpeedLevel() {
        return this.SpeedLevel;
    }
    public void setSpeedLevel(int speed) {
        this.SpeedLevel = speed;
    }
    public int getJumpStrength() {
        return this.JumpStrength;
    }
    public void setJumpStrength(int jumpStrength) {
        this.JumpStrength = jumpStrength;
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
    public boolean getWalkOnWater() {
        return this.WalkOnWater;
    }
    public void setWalkOnWater(boolean walkOnWater) {
        this.WalkOnWater = walkOnWater;
    }
    public boolean getCanDash() {
        return this.CanDash;
    }
    public void setCanDash(boolean canDash) {
        this.CanDash = canDash;
    }
    public int getSpeed() {
        return this.Speed;
    }
    public void setSpeed(int speed) {
        this.Speed = speed;
    }
    public int getJump() {
        return this.Jump;
    }
    public void setJump(int jump) {
        this.Jump = jump;
    }
    public int getDash() {
        return this.Dash;
    }
    public void setDash(int dash) {
        this.Dash = dash;
    }
    public int getMaxHealth() {
        return this.MaxHealth;
    }
    public void setMaxHealth(int health) {
        this.MaxHealth = health;
    }
    public int getHealth() {
        return this.Health;
    }
    public void setHealth(int health) {
        this.Health = health;
    }
    public int getDefense() {
        return this.Defense;
    }
    public void setDefense(int defense) {
        this.Defense = defense;
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
    public boolean getQiType(String type) {
        return this.QiType.getOrDefault(type, false)
                ;
    }
    public void setQiType(String qiType, boolean value) {
        this.QiType.put(qiType, value)
        ;
    }
    public boolean getBody(String body) {
        return this.Body.getOrDefault(body, false)
                ;
    }
    public void setBody(String body, boolean value) {
        this.Body.put(body, value)
        ;
    }
    public boolean getPrinciples(String principle) {
        return this.Principles.getOrDefault(principle, false)
                ;
    }
    public void setPrinciples(String principles, boolean value) {
        this.Principles.put(principles, value)
        ;
    }
    public boolean getRace(String race) {
        return this.Race.getOrDefault(race, false)
                ;
    }
    public void setRace(String race, boolean value) {
        this.Race.put(race, value)
        ;
    }
    public String getHomes(String home){return this.Homes.get(home);}
    public void setHomes(String name, String home){this.Homes.put(name, home);}
    public void removeHome(String name) {
        getAllHomes().remove(name);
    }
    public Map<String, String> getAllHomes(){return this.Homes;}
    public Map<String, Boolean> getAllQiTypes() {
        return this.QiType;
    }
    public Map<String, Boolean> getAllBodies() {
        return this.Body;
    }
    public Map<String, Boolean> getAllPrinciples() {
        return this.Principles;
    }
    public Map<String, Boolean> getAllSubRaces() {
        return this.Race;
    }
    public void charge(Player player) {
        tick++;
        if (tick >= 20) {
            if (player.isHolding(ModItems.BAODING_BALLS.get()) && player.isShiftKeyDown()) {
                int newQi = getQi() + qiChargeEfficiency();
                setQi(Math.min(newQi, getMaxQi()));
                int newSpiritPower = getSpiritPower() + spiritPowerChargeEfficiency();
                setSpiritPower(Math.min(newSpiritPower, getMaxSpiritPower()));
                int healingAmount = healEfficiency(player);
                int currentHealth = getHealth();
                int newHealth = currentHealth + healingAmount;
                setHealth(Math.min(newHealth, getMaxHealth()));
                realmChecker(player);
                ModNetwork.sendSyncPlayerData((ServerPlayer) player);
                tick = 0;
            }
        }
    }
    public int qiChargeEfficiency() {
        return getMaxQi() / 10;
    }
    public int spiritPowerChargeEfficiency() {
        return getMaxSpiritPower() / 10;
    }
    public int healEfficiency(Player player) {
        return (int) player.getMaxHealth() / 10;
    }
    public void maxHealth(){
        setHealth(getMaxHealth());
    }
    public void maxQi(){
        setQi(getMaxQi());
    }
    public void maxSpiritPower(){
        setSpiritPower(getMaxSpiritPower());
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
                            setMinorRealm(0);
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
    public void applySpeedToPlayer(Player player) {
        float[] speedMultipliers = {1.0f, 5.0f, 7.5f, 10.0f};
        int currentToggle = getSpeedLevel();  // Just read, don't increment
        float speedMultiplier = speedMultipliers[currentToggle];

        double baseWalkSpeed = 0.1;
        double baseFlySpeed = 0.05;

        double walkSpeed = baseWalkSpeed * speedMultiplier;
        double flySpeed = baseFlySpeed * speedMultiplier;

        Objects.requireNonNull(player.getAttribute(Attributes.MOVEMENT_SPEED)).setBaseValue(walkSpeed);
        player.onUpdateAbilities();

        if (getCanFly()) {
            player.getAbilities().setFlyingSpeed((float) flySpeed);
            player.onUpdateAbilities();
        }

        ModNetwork.sendSyncPlayerData((ServerPlayer) player);
    }
    public void toggleSpeedLevel(Player player) {
        int newToggle = (getSpeedLevel() + 1) % 4;
        setSpeedLevel(newToggle);
        speedPercentMessage(player, newToggle);
    }
    public void applyJumpBoost(Player player) {
        float[] jumpMultipliers = {1.0f, 1.5f, 2.0f, 2.5f};
        int currentToggle = getJumpStrength();
        float jumpMultiplier = jumpMultipliers[currentToggle];
        double baseJump = 0.42D;
        double jumpStrength = baseJump * jumpMultiplier;
        if (player.getAttribute(Attributes.JUMP_STRENGTH) != null) {
            Objects.requireNonNull(player.getAttribute(Attributes.JUMP_STRENGTH)).setBaseValue(jumpStrength);
        }
        ModNetwork.sendSyncPlayerData((ServerPlayer) player);
    }
    public void toggleJumpLevel(Player player) {
        int currentToggle = getJumpStrength();
        int newToggle = (currentToggle + 1) % 4;
        setJumpStrength(newToggle);
        jumpPercentMessage(player, newToggle);
        applyJumpBoost(player);
    }
    public void dashForward(Player player, int dashStrength) {
        Vec3 lookVec = player.getLookAngle().normalize();
        Vec3 dashVec = lookVec.scale(dashStrength);
        player.setDeltaMovement(dashVec);
        player.hurtMarked = true;
    }
    private void speedPercentMessage(Player player, int speedLevel) {
        // Send a message to the player based on the new speed level
        switch (speedLevel) {
            case 0:
                player.sendSystemMessage(Component.literal("Speed set to 25%"));
                break;
            case 1:
                player.sendSystemMessage(Component.literal("Speed set to 50%"));
                break;
            case 2:
                player.sendSystemMessage(Component.literal("Speed set to 75%"));
                break;
            case 3:
                player.sendSystemMessage(Component.literal("Speed set to 100%"));
                break;
        }
    }
    public void jumpPercentMessage(Player player, int jump){
        switch (jump) {
            case 0:
                player.sendSystemMessage(Component.literal("Jump Power set to 25%"));
                break;
            case 1:
                player.sendSystemMessage(Component.literal("Jump Power set to 50%"));
                break;
            case 2:
                player.sendSystemMessage(Component.literal("Jump Power set to 75%"));
                break;
            case 3:
                player.sendSystemMessage(Component.literal("Jump Power set to 100%"));
                break;
        }
    }
    @Override
    public Tag serializeNBT(HolderLookup.@NotNull Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("SpeedLevel", getSpeedLevel());
        tag.putInt("JumpStrength", getJumpStrength());
        tag.putString("Home", getHome());
        tag.putString("NickName", getNickName());
        tag.putString("ChatPrefix", getChatPrefix());
        tag.putString("ChatColor", getChatColor());
        tag.putBoolean("CanFly", getCanFly());
        tag.putBoolean("WalkOnWater", getWalkOnWater());
        tag.putBoolean("CanDash", getCanDash());
        tag.putInt("MaxHealth", getMaxHealth());
        tag.putInt("Health", getHealth());
        tag.putInt("Defense", getDefense());
        tag.putInt("Speed", getSpeed());
        tag.putInt("Jump", getJump());
        tag.putInt("Dash", getDash());
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
            tag.putBoolean("QiType" + key, getQiType(key));
        }
        for (String key : this.Body.keySet()) {
            tag.putBoolean("Body" + key, getBody(key));
        }
        for (String key : this.Principles.keySet()) {
            tag.putBoolean("Principles" + key, getPrinciples(key));
        }
        for (String key : this.Race.keySet()) {
            tag.putBoolean("Race" + key, getRace(key));
        }
        for (String key : this.Homes.keySet()) {
            tag.putString("Homes" + key, getHomes(key));
        }
        return tag;
    }
    @Override
    public void deserializeNBT(HolderLookup.@NotNull Provider provider, @NotNull Tag tag) {
        if (tag instanceof CompoundTag compoundTag) {
            setSpeedLevel(compoundTag.getInt("SpeedLevel"));
            setJumpStrength(compoundTag.getInt("JumpStrength"));
            setHome(compoundTag.getString("Home"));
            setNickName(compoundTag.getString("NickName"));
            setChatPrefix(compoundTag.getString("ChatPrefix"));
            setChatColor(compoundTag.getString("ChatColor"));
            setCanFly(compoundTag.getBoolean("CanFly"));
            setWalkOnWater(compoundTag.getBoolean("WalkOnWater"));
            setCanDash(compoundTag.getBoolean("CanDash"));
            setMaxHealth(compoundTag.getInt("MaxHealth"));
            setHealth(compoundTag.getInt("Health"));
            setDefense(compoundTag.getInt("Defense"));
            setSpeed(compoundTag.getInt("Speed"));
            setJump(compoundTag.getInt("Jump"));
            setDash(compoundTag.getInt("Dash"));
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
                setQiType(key, compoundTag.getBoolean("QiType" + key));
            }
            for (String key : this.Body.keySet()) {
                setBody(key, compoundTag.getBoolean("Body" + key));
            }
            for (String key : this.Principles.keySet()) {
                setPrinciples(key, compoundTag.getBoolean("Principles" + key));
            }
            for (String key : this.Race.keySet()) {
                setRace(key, compoundTag.getBoolean("Race" + key));
            }
            for (String key : this.Homes.keySet()) {
                setHomes(key, compoundTag.getString("Homes" + key));
            }
        }
    }
}