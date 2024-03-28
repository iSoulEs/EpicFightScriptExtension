package tkk.epicrelic.capability;

import net.minecraft.nbt.CompoundNBT;

public interface IEnrCapability {
    double getStrength();
    double getVitality();
    double getDexterity();
    double getWillpower();
    double getMentality();
    double getTechnique();

    double getStrengthLoad();
    double getVitalityLoad();
    double getDexterityLoad();
    double getWillpowerLoad();
    double getMentalityLoad();
    double getTechniqueLoad();

    void setStrength(double d);
    void setVitality(double d);
    void setDexterity(double d);
    void setWillpower(double d);
    void setMentality(double d);
    void setTechnique(double d);

    void setStrengthLoad(double d);
    void setVitalityLoad(double d);
    void setDexterityLoad(double d);
    void setWillpowerLoad(double d);
    void setMentalityLoad(double d);
    void setTechniqueLoad(double d);

    double getInteractSpeed();
    double getSkillCdSpeed();
    double getCostReduction();
    void setInteractSpeed(double d);
    void setSkillCdSpeed(double d);
    void setCostReduction(double d);


    void sync();
    CompoundNBT serializeNBT();
    void deserializeNBT(CompoundNBT nbt);



}
