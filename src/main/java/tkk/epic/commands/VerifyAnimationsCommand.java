package tkk.epic.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import tkk.epic.utils.AntiCheatUtil;
import yesman.epicfight.main.EpicFightMod;

public class VerifyAnimationsCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralArgumentBuilder<CommandSource> builder = Commands.literal("epicanimationsverify").requires((commandSourceStack) -> commandSourceStack.hasPermission(2));

        builder.then(Commands.argument("target", EntityArgument.players()).executes((p_137728_) -> {
            int i=0;
            for(ServerPlayerEntity player:EntityArgument.getPlayers(p_137728_, "target")){
                AntiCheatUtil.verifyAnimationsHashcode(player);
                ++i;
            }
            return i;
        }));
        dispatcher.register(Commands.literal(EpicFightMod.MODID).then(builder));
    }
}
