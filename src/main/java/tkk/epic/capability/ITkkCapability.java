package tkk.epic.capability;

import net.minecraft.nbt.CompoundNBT;
import tkk.epic.skill.SkillContainer;

public interface ITkkCapability{
    //id 0-SKILL_SIZE
    SkillContainer getSkillContainer(int id);

    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);



}
