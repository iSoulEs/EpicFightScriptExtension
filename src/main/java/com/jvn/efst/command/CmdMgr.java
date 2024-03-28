package com.jvn.efst.command;

import com.jvn.efst.EpicAddon;
import com.jvn.efst.config.ClientConfig;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.RegisterCommandsEvent;

public class CmdMgr {
    protected static LiteralArgumentBuilder<CommandSource> command;


    public static void MSG(String str){
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            player.displayClientMessage(new TranslationTextComponent(str),false);
        }
    }

    public static void registerClientCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();

        command = Commands.literal(EpicAddon.MODID).executes(context -> {
                    ClientPlayerEntity player = Minecraft.getInstance().player;
                    if (player != null) {
                        MSG("");
                    }
                    return Command.SINGLE_SUCCESS;
                })
                .then(Commands.literal("Reload")
                        .executes(context -> {
                            ClientConfig.Load();
                            MSG("[EpicFightSwordTrail]Reload All Config.");
                            return Command.SINGLE_SUCCESS;
                        }))
                .then(Commands.literal("SwordTrail")
                        .executes(context -> {
                            MSG("[EpicFightSwordTrail]SwordTrail Render: " + ClientConfig.cfg.EnableSwordTrail);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = true;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicFightSwordTrail]Enabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableSwordTrail = false;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicFightSwordTrail]Disabled Sword Trail Render.");
                                    return Command.SINGLE_SUCCESS;
                                })))
                .then(Commands.literal("OptFineMode")
                        .executes(context -> {
                            MSG("[EpicFightSwordTrail]OptFineMode: " + ClientConfig.cfg.EnableOptFineMode);
                            return Command.SINGLE_SUCCESS;
                        })
                        .then(Commands.literal("true")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = true;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicFightSwordTrail]Enabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                }))
                        .then(Commands.literal("false")
                                .executes(context -> {
                                    ClientConfig.cfg.EnableOptFineMode = false;
                                    ClientConfig.SaveCommon();
                                    MSG("[EpicFightSwordTrail]Disabled OptFineMode.");
                                    return Command.SINGLE_SUCCESS;
                                })))
        ;

        dispatcher.register(command);
    }
}
