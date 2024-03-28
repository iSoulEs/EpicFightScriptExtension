package yesman.epicfight.skill;

import net.minecraft.network.PacketBuffer;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

public class RollSkill extends DodgeSkill {
    public RollSkill(DodgeSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, PacketBuffer args) {
        super.executeOnServer(executer, args);

        executer.getOriginal().level.playSound(null, executer.getOriginal().getX(), executer.getOriginal().getY(), executer.getOriginal().getZ(), EpicFightSounds.SKILL_ROLL, executer.getOriginal().getSoundSource(), 1.0F, 1.0F);
    }

}
