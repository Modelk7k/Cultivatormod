package net.model2k.cultivatormod.network.packet;

public class PlayerStatsClient {
    private static int MaxHealth, Health, qi, maxQi, spiritPower, maxSpiritPower,speed, jump, Dash,JumpLevel, SpeedLevel;
    private static boolean WalkOnWater, canDash, HasSynced, CanFly;
    public static int getSpeed() { return speed; }
    public static int getJump() { return jump; }
    public static boolean getCanDash() { return canDash; }
    public static void setSpeed(int s) { speed = s; }
    public static void setJump(int j) { jump = j; }
    public static void setCanDash(boolean c) { canDash = c; }
    public static int getQi() {return qi;}
    public static int getMaxQi() {return maxQi;}
    public static void setQi(int qiVal) {qi = qiVal;}
    public static void setMaxQi(int maxVal) {maxQi = maxVal;}
    public static int getMaxSpiritPower() {return maxSpiritPower;}
    public static void setMaxSpiritPower(int spiritVal) {maxSpiritPower = spiritVal;}
    public static int getSpiritPower() {return spiritPower;}
    public static void setSpiritPower(int spiritVal) {spiritPower = spiritVal;}
    public static boolean getWalkOnWater() {return WalkOnWater;}
    public static void setWalkOnWater(boolean walkOnWater) {WalkOnWater = walkOnWater;}
    public static int getDash() {return Dash;}
    public static void setDash(int dash) {Dash = dash;}
    public static int getSpeedLevel() {return SpeedLevel;}
    public static void setSpeedLevel(int speedLevel) {SpeedLevel = speedLevel;}
    public static int getJumpLevel() {return JumpLevel;}
    public static void setJumpLevel(int jumpLevel) {JumpLevel = jumpLevel;}
    public static int getMaxHealth() {return MaxHealth;}
    public static void setMaxHealth(int maxHealth) {MaxHealth = maxHealth;}
    public static int getHealth() {
        return Health;
    }
    public static void setHealth(int health) {
        Health = health;
    }
    public static boolean isHasSynced() {
        return HasSynced;
    }
    public static void setHasSynced(boolean hasSynced) {
        HasSynced = hasSynced;
    }
    public static boolean isCanFly() {
        return CanFly;
    }
    public static void setCanFly(boolean fly) {
        CanFly = fly;
    }
}