package tkk.epic.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;

@Cancelable
public class ExecuteSkillEvent extends Event {
    public ServerPlayerEntity serverPlayer;
    public ServerPlayerPatch playerPatch;
    public int skillSlot;
    public boolean active;



    public ExecuteSkillEvent(ServerPlayerPatch serverPlayerPatch,ServerPlayerEntity serverPlayerEntity,int skillSlot,boolean active){
        this.active=active;
        this.skillSlot=skillSlot;
        this.serverPlayer=serverPlayerEntity;
        this.playerPatch=serverPlayerPatch;
    }

}
