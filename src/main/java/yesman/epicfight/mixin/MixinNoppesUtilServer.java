package yesman.epicfight.mixin;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.rcon.RConConsoleSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import noppes.npcs.CustomNpcs;
import noppes.npcs.NoppesUtilServer;
import noppes.npcs.shared.common.util.LogWriter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.UUID;

@Mixin(value = NoppesUtilServer.class, remap = false)
public class MixinNoppesUtilServer {

    /**
     * @author
     * @reason 干掉错误提示
     */
    @Overwrite(remap = false)
    public static String runCommand(final World level, BlockPos pos, String name, String command, PlayerEntity player, Entity executer) {
        if (!level.getServer().isCommandBlockEnabled()) {
            LogWriter.warn("Cant run commands if CommandBlocks are disabled");
            return "Cant run commands if CommandBlocks are disabled";
        }
        if (player != null)
            command = command.replace("@dp", player.getName().getString());
        command = command.replace("@npc", name);
        final StringTextComponent output = new StringTextComponent("");
        RConConsoleSource rConConsoleSource = new RConConsoleSource(level.getServer()) {
            public void func_145747_a(ITextComponent component, UUID senderUUID) {
                output.append(component);
            }

            public boolean func_195041_r_() {
                ServerWorld lvt_1_1_ = level.getServer().getLevel(World.OVERWORLD);
                return lvt_1_1_.getGameRules().getBoolean(GameRules.RULE_COMMANDBLOCKOUTPUT);
            }
        };
        int permLvl = CustomNpcs.NpcUseOpCommands ? 4 : 2;
        Vector3d point = new Vector3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
        CommandSource commandSource = new CommandSource((ICommandSource)rConConsoleSource, point, Vector2f.ZERO, (ServerWorld)level, permLvl, "@CustomNPCs-" + name, (ITextComponent)new StringTextComponent("@CustomNPCs-" + name), level.getServer(), executer) {

        };
        Commands icommandmanager = level.getServer().getCommands();
        icommandmanager.performCommand(commandSource, command);
        if (output.getString().isEmpty())
            return null;
        return output.getString();
    }
}
