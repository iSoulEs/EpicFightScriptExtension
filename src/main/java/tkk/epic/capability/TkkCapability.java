package tkk.epic.capability;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import tkk.epic.skill.SkillContainer;

import static tkk.epic.gui.hud.hotbar.HotBar.SKILL_SIZE;

public class TkkCapability implements ITkkCapability{

    private SkillContainer[] containers;

    public TkkCapability(ServerPlayerEntity player){
        containers=new SkillContainer[SKILL_SIZE];
        for(int i=0;i<SKILL_SIZE;i++){
            containers[i]=new SkillContainer(player,i);
        }
    }


    @Override
    public SkillContainer getSkillContainer(int id) {
        return containers[id];
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        for(int i=0;i<SKILL_SIZE;i++){
            compound.put(i+"",containers[i].serializeNBT());
        }
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        for(int i=0;i<SKILL_SIZE;i++){
            containers[i].deserializeNBT(nbt.getCompound(i+""));
        }
    }
}
