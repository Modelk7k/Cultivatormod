package net.model2k.cultivatormod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.model2k.cultivatormod.datagen.ModAttachments;
import net.model2k.cultivatormod.datagen.PlayerData;
import net.model2k.cultivatormod.network.ModNetwork;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class StatsCommand {
    public StatsCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("heal")
                .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                .executes(this::heal)
        );
        dispatcher.register(
                Commands.literal("stats")
                        .requires(source -> {
                            if (!source.isPlayer()) return false;
                            ServerPlayer player = source.getPlayer();
                            assert player != null;
                            return player.getTags().contains("staff");
                        })
                        .executes(context -> getStats(context, Objects.requireNonNull(context.getSource().getPlayer())))
                        .then(Commands.argument("player", EntityArgument.player())
                                .executes(context -> getStats(context, EntityArgument.getPlayer(context, "player"))))
        );
        dispatcher.register(Commands.literal("maxspirit")
                .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                .executes(this::maxSpirit)
        );
        dispatcher.register(Commands.literal("maxqi")
                .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                .executes(this::maxQi)
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("body")
                                .then(Commands.argument("body", StringArgumentType.greedyString()) // Accepts spaces
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllBodies().keySet()), builder);
                                        })
                                        .executes(context -> setBody(context))
                                )
                        )
        );
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff");
                })
                .then(Commands.literal("waterwalk")
                        .then(Commands.argument("enabled", IntegerArgumentType.integer(0, 1))
                                .executes(this::walkOnWater)
                        )
                )
        );
        dispatcher.register(
                Commands.literal("set")  // Main 'set' command
                        .then(Commands.literal("dash")  // Sub-command for setting fly
                                .requires(source -> {
                                    ServerPlayer player = source.getPlayer();
                                    return player != null && player.getTags().contains("staff");  // Check if the player has "staff" tag
                                })
                                .then(Commands.argument("enabled", IntegerArgumentType.integer(0, 1))  // Argument for 0 or 1
                                        .executes(this::dash)  // Execution method
                                )
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("dashdistance")  // This is the second argument, "speed"
                                .then(Commands.argument("distance", IntegerArgumentType.integer(1, 10))  // Accepts integer values between 1 and 6
                                        .executes(this::dashDistance))  // Executes the method when the command is run
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("defense")  // This is the second argument, "defense"
                                .then(Commands.argument("defense", IntegerArgumentType.integer(0))  // Accepts integer values between 1 and 100
                                        .executes(this::defense))  // Executes the method when the command is run
                        )
        );
        dispatcher.register(
                Commands.literal("set")  // Main 'set' command
                        .then(Commands.literal("fly")  // Sub-command for setting fly
                                .requires(source -> {
                                    ServerPlayer player = source.getPlayer();
                                    return player != null && player.getTags().contains("staff");  // Check if the player has "staff" tag
                                })
                                .then(Commands.argument("enabled", IntegerArgumentType.integer(0, 1))  // Argument for 0 or 1
                                        .executes(this::fly)  // Execution method
                                )
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && Objects.requireNonNull(source.getPlayer()).getTags().contains("staff"))
                        .then(Commands.literal("maxhealth")
                                .then(Commands.argument("health", IntegerArgumentType.integer())
                                        .executes(this::health)
                                )
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("jump")  // This is the second argument, "speed"
                                .then(Commands.argument("strength", IntegerArgumentType.integer(1, 10))  // Accepts integer values between 1 and 6
                                        .executes(this::jump))  // Executes the method when the command is run
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");
                        })
                        .then(Commands.literal("majorrealm")
                                .then(Commands.argument("realm", IntegerArgumentType.integer(0, 10))
                                        .executes(this::majorRealm))
                        )
        );
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("maxqi")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(this::setMaxQi)
                        )
                )
        );
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("maxspiritpower")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(this::setMaxSpirit)
                        )
                )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");
                        })
                        .then(Commands.literal("minorrealm")
                                .then(Commands.argument("realm", IntegerArgumentType.integer(0,10))
                                        .executes(this::minorRealm))
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("principle")
                                .then(Commands.argument("principle", StringArgumentType.greedyString()) // Accepts spaces
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllPrinciples().keySet()), builder);
                                        })
                                        .executes(context -> principle(context))
                                )
                        )
        );
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();

                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("qi")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(qi -> IntegerArgumentType.getInteger(qi, "amount"))
                                .executes(this::qi)
                        )
                )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> source.isPlayer() && source.getPlayer().getTags().contains("staff"))
                        .then(Commands.literal("qitype")
                                .then(Commands.argument("qiType", StringArgumentType.greedyString()) // Accepts spaces
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllQiTypes().keySet()), builder);
                                        })
                                        .executes(context -> qiType(context))
                                )
                        )
        );
        dispatcher.register(
                Commands.literal("set")
                        .requires(source -> {
                            ServerPlayer player = source.getPlayer();
                            return player != null && player.getTags().contains("staff");  // Only staff can use this
                        })
                        .then(Commands.literal("speed")  // This is the second argument, "speed"
                                .then(Commands.argument("speed", IntegerArgumentType.integer(1, 6))  // Accepts integer values between 1 and 6
                                        .executes(this::speed))  // Executes the method when the command is run
                        )
        );
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("spiritpower")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(spirit -> IntegerArgumentType.getInteger(spirit, "amount"))
                                .executes(this::setSpirit)
                        )
                )
        );
        dispatcher.register(Commands.literal("set")
                .requires(source -> {
                    ServerPlayer player = source.getPlayer();
                    return player != null && player.getTags().contains("staff"); // Requires 'staff' tag
                })
                .then(Commands.literal("strength")
                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                .executes(strength -> IntegerArgumentType.getInteger(strength, "amount"))
                                .executes(this::strength)
                        )
                )
        );
        dispatcher.register(
                Commands.literal("set")
                        .then(Commands.literal("race")
                                .requires(source -> {
                                    if (!source.isPlayer()) return false;
                                    ServerPlayer player = source.getPlayer();
                                    return player.getTags().contains("staff");
                                })
                                .then(Commands.argument("subrace", StringArgumentType.word())
                                        .suggests((context, builder) -> {
                                            ServerPlayer player = context.getSource().getPlayer();
                                            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
                                            return SharedSuggestionProvider.suggest(new ArrayList<>(data.getAllSubRaces().keySet()), builder);
                                        })
                                        .executes(this::setSubrace)
                                )
                        )
        );
    }
    private int heal(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()) {
            ServerPlayer player = context.getSource().getPlayer();
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.maxHealth();
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("You were healed"), false);
            return 1;
        }else {
            return 0;
        }
    }
    private int getStats(CommandContext<CommandSourceStack> context, ServerPlayer target) {
        PlayerData data = target.getData(ModAttachments.PLAYER_DATA);
        CommandSourceStack source = context.getSource();
        source.sendSuccess(() -> Component.literal("Stats for " + target.getName().getString() + ":"), false);
        source.sendSuccess(() -> Component.literal("• Nickname: " + data.getNickName()), false);
        source.sendSuccess(() -> Component.literal("• Home: " + data.getHome()), false);
        for (Map.Entry<String, Boolean> entry : data.getAllSubRaces().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Race: " + entry.getKey()), false);
            }
        }
        for (Map.Entry<String, Boolean> entry : data.getAllBodies().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Body: " + entry.getKey()), false);
            }
        }
        for (Map.Entry<String, Boolean> entry : data.getAllPrinciples().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Principle: " + entry.getKey()), false);
            }
        }
        for (Map.Entry<String, Boolean> entry : data.getAllQiTypes().entrySet()) {
            if (entry.getValue()) {
                source.sendSuccess(() -> Component.literal("• Qi Type: " + entry.getKey()), false);
            }
        }
        source.sendSuccess(() -> Component.literal("• Minor Realm: " + data.getMinorRealm()), false);
        source.sendSuccess(() -> Component.literal("• Major Realm: " + data.getMajorRealm()), false);
        source.sendSuccess(() -> Component.literal("• Qi Quality: " + data.getQiQuality()), false);
        source.sendSuccess(() -> Component.literal("• Health: " + data.getHealth() + " / " + data.getMaxHealth()), false);
        source.sendSuccess(() -> Component.literal("• Strength: " + data.getStrength()), false);
        source.sendSuccess(() -> Component.literal("• Defense: " + data.getDefense()), false);
        source.sendSuccess(() -> Component.literal("• Speed: " + data.getSpeed()), false);
        source.sendSuccess(() -> Component.literal("• Jump: " + data.getJump()), false);
        source.sendSuccess(() -> Component.literal("• Dash: " + data.getDash()), false);
        source.sendSuccess(() -> Component.literal("• Qi: " + data.getQi() + " / " + data.getMaxQi()), false);
        source.sendSuccess(() -> Component.literal("• Spirit Power: " + data.getSpiritPower() + " / " + data.getMaxSpiritPower()), false);
        source.sendSuccess(() -> Component.literal("• Walk On Water: " + data.getWalkOnWater() + "• Can Dash: " + data.getCanDash() + "• Can Fly: " + data.getCanFly()), false);
        return 1;
    }
    private int maxSpirit(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()) {
            ServerPlayer player = context.getSource().getPlayer();
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.maxSpiritPower();
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("You were healed"), false);
            return 1;
        }else {
            return 0;
        }
    }
    private int maxQi(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()) {
            ServerPlayer player = context.getSource().getPlayer();
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.maxQi();
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("You were healed"), false);
            return 1;
        }else {
            return 0;
        }
    }
    private int setBody(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String body = StringArgumentType.getString(context, "body");
        if (data.getAllBodies().containsKey(body)) {
            for (String otherBody : data.getAllBodies().keySet()) {
                if (!otherBody.equals(body)) {
                    data.setBody(otherBody, false);
                }
            }
            data.setBody(body, true);
            String status = data.getPrinciples(body) ? "set" : "removed";
            context.getSource().sendSuccess(() -> Component.literal("Body " + body + " has been " + status + "."), false);
        } else {
            context.getSource().sendFailure(Component.literal("Invalid Body: " + body));
        }
        return 1;
    }
    private int walkOnWater(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Only players can run this."));
            return 0;
        }
        int enabled = IntegerArgumentType.getInteger(context, "enabled");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setWalkOnWater(enabled == 1);

        context.getSource().sendSuccess(() ->
                Component.literal("Water walk ability has been " + (enabled == 1 ? "enabled" : "disabled") + "."), false);
        return 1;
    }
    private int dash(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()) {
            ServerPlayer player = context.getSource().getPlayer();
            int value = IntegerArgumentType.getInteger(context, "enabled");
            boolean enabled = value == 1;
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            ModNetwork.sendSyncPlayerData(player);
            data.setCanDash(enabled);
            context.getSource().sendSuccess(() ->
                    Component.literal("Dash has been " + (enabled ? "enabled." : "disabled.")), true);

        }
        return 1;
    }
    private int dashDistance(CommandContext<CommandSourceStack> context) {
        if(context.getSource().getPlayer() != null) {
            ServerPlayer player = context.getSource().getPlayer();
            int distance = IntegerArgumentType.getInteger(context, "distance");
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.setDash(distance);
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("Dash set to: " + distance), true);
        } return 1;
    }
    private int defense(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        int defense = IntegerArgumentType.getInteger(context, "defense");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setDefense(defense);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() -> Component.literal("Defense set to: " + defense), true);  // Send confirmation message
        return 1;
    }
    private int fly(CommandContext<CommandSourceStack> context) {
        if (!context.getSource().getPlayer().level().isClientSide()){
            ServerPlayer player = context.getSource().getPlayer();
            int value = IntegerArgumentType.getInteger(context, "enabled");
            boolean enabled = value == 1;
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.setCanFly(enabled);
            ModNetwork.sendSyncPlayerData(player);
            player.getAbilities().mayfly = enabled;
            if (!enabled && player.getAbilities().flying) {
                player.getAbilities().flying = false;
            }
            player.onUpdateAbilities();
            context.getSource().sendSuccess(() ->
                    Component.literal("Flying has been " + (enabled ? "enabled." : "disabled.")), true);

        }
        return 1;
    }
    private int health(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        assert player != null;
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        int maxHealth = IntegerArgumentType.getInteger(context, "health");
        data.setMaxHealth(maxHealth);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() -> Component.literal("Health set to " + data.getMaxHealth()),true);
        return 1;
    }
    private int jump(CommandContext<CommandSourceStack> context) {
        if(context.getSource().getPlayer() != null) {
            ServerPlayer player = context.getSource().getPlayer();
            int strength = IntegerArgumentType.getInteger(context, "strength");
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            ModNetwork.sendSyncPlayerData(player);
            data.setJump(strength);
            context.getSource().sendSuccess(() -> Component.literal("Jump set to: " + strength), true);
        } return 1;
    }
    private int majorRealm(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        int realm = IntegerArgumentType.getInteger(context, "realm");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setMajorRealm(realm);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() -> Component.literal("Major Realm set to: " + realm), true);  // Send confirmation message
        return 1;
    }
    private int setMaxQi(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setMaxQi(IntegerArgumentType.getInteger(context, "amount"));
        ModNetwork.sendSyncPlayerData(player);
        return 1;
    }
    private int setMaxSpirit(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setMaxSpiritPower(IntegerArgumentType.getInteger(context, "amount"));
        ModNetwork.sendSyncPlayerData(player);
        return 1;
    }
    private int minorRealm(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) return 0;
        int realm = IntegerArgumentType.getInteger(context, "realm");
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setMinorRealm(realm);
        ModNetwork.sendSyncPlayerData(player);
        context.getSource().sendSuccess(() -> Component.literal("Minor Realm set to: " + realm), true);  // Send confirmation message
        return 1;
    }
    private int principle(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String principle = StringArgumentType.getString(context, "principle");
        if (data.getAllPrinciples().containsKey(principle)) {
            for (String otherprinciples : data.getAllPrinciples().keySet()) {
                if (!otherprinciples.equals(principle)) {
                    data.setPrinciples(otherprinciples, false);
                }
            }
            data.setPrinciples(principle, true);
            String status = data.getPrinciples(principle) ? "set" : "removed";
            context.getSource().sendSuccess(() -> Component.literal("Principle " + principle + " has been " + status + "."), false);
        } else {
            context.getSource().sendFailure(Component.literal("Invalid Principle: " + principle));
        }
        return 1;
    }
    private int qi(CommandContext<CommandSourceStack> context) {
        PlayerData data = context.getSource().getPlayer().getData(ModAttachments.PLAYER_DATA);
        if (IntegerArgumentType.getInteger(context, "amount") <= data.getMaxQi()) {
            data.setQi(IntegerArgumentType.getInteger(context, "amount"));
            ModNetwork.sendSyncPlayerData(context.getSource().getPlayer());
            return 1;
        }else {
            context.getSource().sendFailure((Component.literal("Cannot set above your max qi")));
            return 0;
        }
    }
    private int qiType(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String qiType = StringArgumentType.getString(context, "qiType");
        if (data.getAllQiTypes().containsKey(qiType)) {
            boolean currentValue = data.getQiType(qiType);
            data.setQiType(qiType, !currentValue);
            String status = currentValue ? "removed" : "set";
            context.getSource().sendSuccess(() -> Component.literal("Qi type " + qiType + " has been " + status + "."), false);
            if(data.getQiType("Water Qi")){
                player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(Integer.MAX_VALUE);
                player.getAttribute(Attributes.OXYGEN_BONUS).setBaseValue(Integer.MAX_VALUE);
                data.setWalkOnWater(true);
            }if(!data.getQiType("Water Qi")){
                player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY).setBaseValue(0);
                player.getAttribute(Attributes.OXYGEN_BONUS).setBaseValue(0);
                data.setWalkOnWater(false);
            }
        } else {
            context.getSource().sendFailure(Component.literal("Invalid Qi type: " + qiType));
        }
        return 1;
    }
    private int speed(CommandContext<CommandSourceStack> context) {
        if(context.getSource().getPlayer() != null) {
            ServerPlayer player = context.getSource().getPlayer();
            int speed = IntegerArgumentType.getInteger(context, "speed");
            PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
            data.setSpeed(speed);
            data.applySpeedToPlayer(player);
            ModNetwork.sendSyncPlayerData(player);
            context.getSource().sendSuccess(() -> Component.literal("Speed set to: " + speed), true);
        } return 1;
    }
    private int setSpirit(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        if (IntegerArgumentType.getInteger(context, "amount") <= data.getMaxSpiritPower()) {
            data.setSpiritPower(IntegerArgumentType.getInteger(context, "amount"));
            ModNetwork.sendSyncPlayerData(player);
            return 1;
        }else {
            context.getSource().sendFailure((Component.literal("Cannot set above your max Spirit Power")));
            return 0;
        }
    }
    private int strength(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        data.setStrength(IntegerArgumentType.getInteger(context, "amount"));
        context.getSource().sendSuccess(() -> Component.literal("Strength set to " + data.getStrength()), true);
        ModNetwork.sendSyncPlayerData(player);
        return 1;
    }
    private int setSubrace(CommandContext<CommandSourceStack> context) {
        ServerPlayer player = context.getSource().getPlayer();
        if (player == null) {
            context.getSource().sendFailure(Component.literal("Only players can execute this command!"));
            return -1;
        }
        String input = StringArgumentType.getString(context, "subrace").trim();
        PlayerData data = player.getData(ModAttachments.PLAYER_DATA);
        String matchedKey = data.getAllSubRaces().keySet().stream()
                .filter(key -> key.equalsIgnoreCase(input))
                .findFirst()
                .orElse(null);
        if (matchedKey == null) {
            context.getSource().sendFailure(Component.literal("Invalid subrace: " + input));
            return -1;
        }
        for (String sub : data.getAllSubRaces().keySet()) {
            data.setRace(sub, false);
        }
        data.setRace(matchedKey, true);
        context.getSource().sendSuccess(() -> Component.literal("Your subrace has been set to: " + matchedKey), true);
        return 1;
    }
}