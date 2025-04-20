package net.model2k.cultivatormod.network.packet;

public class PlayerStatsClient {
    private static int qi = 0;
    private static int maxQi = 10;
    private static int spiritPower = 0;
    private static int maxSpiritPower = 10;

    public static int getQi() {return qi;}
    public static int getMaxQi() {return maxQi;}
    public static void setQi(int qiVal) {qi = qiVal;}
    public static void setMaxQi(int maxVal) {maxQi = maxVal;}
    public static int getMaxSpiritPower() {return maxSpiritPower;}
    public static void setMaxSpiritPower(int spiritVal) {maxSpiritPower = spiritVal;}
    public static int getSpiritPower() {return spiritPower;}
    public static void setSpiritPower(int spiritVal) {spiritPower = spiritVal;}
}