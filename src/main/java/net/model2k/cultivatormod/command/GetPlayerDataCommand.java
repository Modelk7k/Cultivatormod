package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;

public class GetPlayerDataCommand {
    public GetPlayerDataCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        register(dispatcher); // just call your register method
    }
    public  void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("getplayerdata")
                        .requires(source -> source.hasPermission(2)) // OP-only
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(this::execute))
        );
    }
    private int execute(CommandContext<CommandSourceStack> context) {
        ServerPlayer target = context.getSource().getPlayer();
        PlayerData data = target.getData(ModAttachments.PLAYER_DATA);
        CommandSourceStack source = context.getSource();
        source.sendSuccess(() -> Component.literal("PlayerData for " + target.getName().getString() + ":"), false);
        source.sendSuccess(() -> Component.literal("• Home: " + data.getHome()), false);
        source.sendSuccess(() -> Component.literal("• Nickname: " + data.getNickName()), false);
        source.sendSuccess(() -> Component.literal("• ChatPrefix: " + data.getChatPrefix()), false);
        source.sendSuccess(() -> Component.literal("• ChatColor: " + data.getChatColor()), false);
        source.sendSuccess(() -> Component.literal("• CanFly: " + data.getCanFly()), false);
        source.sendSuccess(() -> Component.literal("• Health: " + data.getHealth()), false);
        source.sendSuccess(() -> Component.literal("• FirstQiType: " + data.getFirstQiType()), false);
        source.sendSuccess(() -> Component.literal("• MinorRealm: " + data.getMinorRealm()), false);
        source.sendSuccess(() -> Component.literal("• MajorRealm: " + data.getMajorRealm()), false);
        source.sendSuccess(() -> Component.literal("• Strength: " + data.getStrength()), false);
        source.sendSuccess(() -> Component.literal("• Qi: " + data.getQi() + " / " + data.getMaxQi()), false);
        source.sendSuccess(() -> Component.literal("• Spirit Power: " + data.getSpiritPower() + " / " + data.getMaxSpiritPower()), false);
        source.sendSuccess(() -> Component.literal("• Qi Quality: " + data.getQiQuality()), false);
        if (data.getQiType("YangQi")) {
            source.sendSuccess(() -> Component.literal("• YangQi: " + data.getQiType("YangQi")), false);
        }
        if (data.getQiType("YinQi")) {
            source.sendSuccess(() -> Component.literal("• YinQi: " + data.getQiType("YinQi")), false);
        }
        if (data.getQiType("DemonQi")) {
            source.sendSuccess(() -> Component.literal("• DemonQi: " + data.getQiType("DemonQi")), false);
        }
        if (data.getQiType("HeavenlyQi")) {
            source.sendSuccess(() -> Component.literal("• HeavenlyQi: " + data.getQiType("HeavenlyQi")), false);
        }
// Races
        if (data.getRace("Human")) {
            source.sendSuccess(() -> Component.literal("• Race: Human"), false);
        }
        if (data.getRace("Beast")) {
            source.sendSuccess(() -> Component.literal("• Race: Beast"), false);
        }
        if (data.getRace("Elf")) {
            source.sendSuccess(() -> Component.literal("• Race: Elf"), false);
        }
        if (data.getRace("Undead")) {
            source.sendSuccess(() -> Component.literal("• Race: Undead"), false);
        }
        if (data.getRace("LowerImmortal")) {
            source.sendSuccess(() -> Component.literal("• Race: LowerImmortal"), false);
        }
        if (data.getRace("HigherImmortal")) {
            source.sendSuccess(() -> Component.literal("• Race: HigherImmortal"), false);
        }
        String[] subraces = {
                // Human
                "Dwarf",
                // Beast
                "Dragon", "Phoenix", "Werewolves","Ogre","Orc",
                // Undead
                "Vampires", "Zombie",
                // Elf
                "WoodElf", "HighElf", "DarkElf",
                // LowerImmortal
                "Angel", "Demon", "ShapeShifter", "BetterZombie", "ElderDwarf","ElderOgre", "ElderOrc",
                "SpiritElf", "LightElf", "VoidElf", "ElderDragon", "ElderPhoenix", "ElderVampire",
                // HigherImmortal
                "SunElf", "MoonElf", "AscendedElf", "DopplGanger", "Necromancer", "AscendedDwarf","AncientOgre","AncientOrc",
                "AncientVampire", "AncientPhoenix", "AncientDragon", "Archangel", "ArchDemon"
        };
        for (String sub : subraces) {
            if (data.getSubRace(sub)) {
                source.sendSuccess(() -> Component.literal("• SubRace: " + sub), false);
            }
        }
        return 1;
    }
}

