package tkk.epicrelic.capability;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.nbt.CompoundNBT;
import tkk.epicrelic.Enr;
import tkk.epicrelic.attribute.EnrAttribute;

import java.util.UUID;

public class EnrCapability implements IEnrCapability {

    public static final UUID STRENGTH_UUID=UUID.fromString("733E4F0B-38F5-3163-062C-18DED429BB37");
    public static final UUID VITALITY_UUID=UUID.fromString("F90648B3-7A61-7151-D5F8-950CF09AB350");
    public static final UUID DEXTERITY_UUID=UUID.fromString("59F1B939-CAE0-3CD8-8C6E-226A0D72BF2D");
    public static final UUID WILLPOWER_UUID=UUID.fromString("2774F241-406C-929C-02BF-713E8BB0A43A");
    public static final UUID MENTALITY_UUID=UUID.fromString("9B62D9BC-2B23-D745-1C88-9FAB6B7666BB");
    public static final UUID TECHNIQUE_UUID=UUID.fromString("480FE202-A5BB-8DDD-A242-8CA9B2C56444");

    public static final UUID STRENGTH_LOAD_UUID=UUID.fromString("5BD8B560-7267-B02B-3F61-84EA657AFCA5");
    public static final UUID VITALITY_LOAD_UUID=UUID.fromString("E0EAB666-021D-C1C5-6C30-68A015446233");
    public static final UUID DEXTERITY_LOAD_UUID=UUID.fromString("4CC3187D-B1FF-42E2-8778-8CF0F1F0933C");
    public static final UUID WILLPOWER_LOAD_UUID=UUID.fromString("95CEA542-142D-0E64-860C-81E34299E51D");
    public static final UUID MENTALITY_LOAD_UUID=UUID.fromString("307D5DC7-3A8F-BA3F-5761-E130A30C69F8");
    public static final UUID TECHNIQUE_LOAD_UUID=UUID.fromString("C214D1EB-1468-0B5C-6F25-B552011D0E5B");

    public static final UUID INTERACT_SPEED_UUID=UUID.fromString("8FB19652-55A2-7EA8-6047-D002C83BDD45");
    public static final UUID SKILL_CD_SPEED_UUID=UUID.fromString("60F86143-F302-6F71-3132-83CBA8565DD6");
    public static final UUID COST_REDUCTION_UUID=UUID.fromString("84585DBD-415E-5834-AA42-06BA7E843E6B");

    public LivingEntity entity;

    public double strength;
    public double vitality;
    public double dexterity;
    public double willpower;
    public double mentality;
    public double technique;


    public double strengthLoad;
    public double vitalityLoad;
    public double dexterityLoad;
    public double willpowerLoad;
    public double mentalityLoad;
    public double techniqueLoad;

    public double interactSpeed;
    public double skillCdSpeed;
    public double costReduction;


    public EnrCapability(LivingEntity entity){
        this.entity=entity;
        strength=0;
        vitality=0;
        dexterity=0;
        willpower=0;
        mentality=0;
        technique=0;

        strengthLoad=0;
        vitalityLoad=0;
        dexterityLoad=0;
        willpowerLoad=0;
        mentalityLoad=0;
        techniqueLoad=0;
    }


    @Override
    public double getStrength() {
        return strength;
    }

    @Override
    public double getVitality() {
        return vitality;
    }

    @Override
    public double getDexterity() {
        return dexterity;
    }

    @Override
    public double getWillpower() {
        return willpower;
    }

    @Override
    public double getMentality() {
        return mentality;
    }

    @Override
    public double getTechnique() {
        return technique;
    }

    @Override
    public double getStrengthLoad() {
        return strengthLoad;
    }

    @Override
    public double getVitalityLoad() {
        return vitalityLoad;
    }

    @Override
    public double getDexterityLoad() {
        return dexterityLoad;
    }

    @Override
    public double getWillpowerLoad() {
        return willpowerLoad;
    }

    @Override
    public double getMentalityLoad() {
        return mentalityLoad;
    }

    @Override
    public double getTechniqueLoad() {
        return techniqueLoad;
    }

    @Override
    public void setStrength(double d) {
        strength=d;
    }

    @Override
    public void setVitality(double d) {
        vitality=d;
    }

    @Override
    public void setDexterity(double d) {
        dexterity=d;
    }

    @Override
    public void setWillpower(double d) {
        willpower=d;
    }

    @Override
    public void setMentality(double d) {
        mentality=d;
    }

    @Override
    public void setTechnique(double d) {
        technique=d;
    }

    @Override
    public void setStrengthLoad(double d) {
        strengthLoad=d;
    }

    @Override
    public void setVitalityLoad(double d) {
        vitalityLoad=d;
    }

    @Override
    public void setDexterityLoad(double d) {
        dexterityLoad=d;
    }

    @Override
    public void setWillpowerLoad(double d) {
        willpowerLoad=d;
    }

    @Override
    public void setMentalityLoad(double d) {
        mentalityLoad=d;
    }

    @Override
    public void setTechniqueLoad(double d) {
        techniqueLoad=d;
    }

    @Override
    public double getInteractSpeed() {
        return interactSpeed;
    }

    @Override
    public double getSkillCdSpeed() {
        return skillCdSpeed;
    }

    @Override
    public double getCostReduction() {
        return costReduction;
    }

    @Override
    public void setInteractSpeed(double d) {
        interactSpeed=d;
    }

    @Override
    public void setSkillCdSpeed(double d) {
        skillCdSpeed=d;
    }

    @Override
    public void setCostReduction(double d) {
        costReduction=d;
    }

    @Override
    public void sync() {
        entity.getAttribute(EnrAttribute.STRENGTH.get()).removeModifier(STRENGTH_UUID);
        entity.getAttribute(EnrAttribute.STRENGTH.get()).addTransientModifier(new AttributeModifier(STRENGTH_UUID, Enr.MODID+":base_modifier",this.strength,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.VITALITY.get()).removeModifier(VITALITY_UUID);
        entity.getAttribute(EnrAttribute.VITALITY.get()).addTransientModifier(new AttributeModifier(VITALITY_UUID, Enr.MODID+":base_modifier",this.vitality,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.DEXTERITY.get()).removeModifier(DEXTERITY_UUID);
        entity.getAttribute(EnrAttribute.DEXTERITY.get()).addTransientModifier(new AttributeModifier(DEXTERITY_UUID, Enr.MODID+":base_modifier",this.dexterity,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.WILLPOWER.get()).removeModifier(WILLPOWER_UUID);
        entity.getAttribute(EnrAttribute.WILLPOWER.get()).addTransientModifier(new AttributeModifier(WILLPOWER_UUID, Enr.MODID+":base_modifier",this.willpower,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.MENTALITY.get()).removeModifier(MENTALITY_UUID);
        entity.getAttribute(EnrAttribute.MENTALITY.get()).addTransientModifier(new AttributeModifier(MENTALITY_UUID, Enr.MODID+":base_modifier",this.mentality,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.TECHNIQUE.get()).removeModifier(TECHNIQUE_UUID);
        entity.getAttribute(EnrAttribute.TECHNIQUE.get()).addTransientModifier(new AttributeModifier(TECHNIQUE_UUID, Enr.MODID+":base_modifier",this.technique,AttributeModifier.Operation.ADDITION));

        entity.getAttribute(EnrAttribute.STRENGTH_LOAD.get()).removeModifier(STRENGTH_LOAD_UUID);
        entity.getAttribute(EnrAttribute.STRENGTH_LOAD.get()).addTransientModifier(new AttributeModifier(STRENGTH_LOAD_UUID, Enr.MODID+":base_modifier",this.strengthLoad,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.VITALITY_LOAD.get()).removeModifier(VITALITY_LOAD_UUID);
        entity.getAttribute(EnrAttribute.VITALITY_LOAD.get()).addTransientModifier(new AttributeModifier(VITALITY_LOAD_UUID, Enr.MODID+":base_modifier",this.vitalityLoad,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.DEXTERITY_LOAD.get()).removeModifier(DEXTERITY_LOAD_UUID);
        entity.getAttribute(EnrAttribute.DEXTERITY_LOAD.get()).addTransientModifier(new AttributeModifier(DEXTERITY_LOAD_UUID, Enr.MODID+":base_modifier",this.dexterityLoad,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.WILLPOWER_LOAD.get()).removeModifier(WILLPOWER_LOAD_UUID);
        entity.getAttribute(EnrAttribute.WILLPOWER_LOAD.get()).addTransientModifier(new AttributeModifier(WILLPOWER_LOAD_UUID, Enr.MODID+":base_modifier",this.willpowerLoad,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.MENTALITY_LOAD.get()).removeModifier(MENTALITY_LOAD_UUID);
        entity.getAttribute(EnrAttribute.MENTALITY_LOAD.get()).addTransientModifier(new AttributeModifier(MENTALITY_LOAD_UUID, Enr.MODID+":base_modifier",this.mentalityLoad,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.TECHNIQUE_LOAD.get()).removeModifier(TECHNIQUE_LOAD_UUID);
        entity.getAttribute(EnrAttribute.TECHNIQUE_LOAD.get()).addTransientModifier(new AttributeModifier(TECHNIQUE_LOAD_UUID, Enr.MODID+":base_modifier",this.techniqueLoad,AttributeModifier.Operation.ADDITION));

        entity.getAttribute(EnrAttribute.INTERACT_SPEED.get()).removeModifier(INTERACT_SPEED_UUID);
        entity.getAttribute(EnrAttribute.INTERACT_SPEED.get()).addTransientModifier(new AttributeModifier(INTERACT_SPEED_UUID, Enr.MODID+":base_modifier",this.interactSpeed,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.SKILL_CD_SPEED.get()).removeModifier(SKILL_CD_SPEED_UUID);
        entity.getAttribute(EnrAttribute.SKILL_CD_SPEED.get()).addTransientModifier(new AttributeModifier(SKILL_CD_SPEED_UUID, Enr.MODID+":base_modifier",this.skillCdSpeed,AttributeModifier.Operation.ADDITION));
        entity.getAttribute(EnrAttribute.COST_REDUCTION.get()).removeModifier(COST_REDUCTION_UUID);
        entity.getAttribute(EnrAttribute.COST_REDUCTION.get()).addTransientModifier(new AttributeModifier(COST_REDUCTION_UUID, Enr.MODID+":base_modifier",this.costReduction,AttributeModifier.Operation.ADDITION));


    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putDouble("strength",strength);
        nbt.putDouble("vitality",vitality);
        nbt.putDouble("dexterity",dexterity);
        nbt.putDouble("willpower",willpower);
        nbt.putDouble("mentality",mentality);
        nbt.putDouble("technique",technique);

        nbt.putDouble("strengthLoad",strengthLoad);
        nbt.putDouble("vitalityLoad",vitalityLoad);
        nbt.putDouble("dexterityLoad",dexterityLoad);
        nbt.putDouble("willpowerLoad",willpowerLoad);
        nbt.putDouble("mentalityLoad",mentalityLoad);
        nbt.putDouble("techniqueLoad",techniqueLoad);

        nbt.putDouble("skillCdSpeed",skillCdSpeed);
        nbt.putDouble("interactSpeed",interactSpeed);
        nbt.putDouble("costReduction",costReduction);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.strength=nbt.getDouble("strength");
        this.vitality=nbt.getDouble("vitality");
        this.dexterity=nbt.getDouble("dexterity");
        this.willpower=nbt.getDouble("willpower");
        this.mentality=nbt.getDouble("mentality");
        this.technique=nbt.getDouble("technique");

        this.strengthLoad=nbt.getDouble("strengthLoad");
        this.vitalityLoad=nbt.getDouble("vitalityLoad");
        this.dexterityLoad=nbt.getDouble("dexterityLoad");
        this.willpowerLoad=nbt.getDouble("willpowerLoad");
        this.mentalityLoad=nbt.getDouble("mentalityLoad");
        this.techniqueLoad=nbt.getDouble("techniqueLoad");

        this.skillCdSpeed=nbt.getDouble("skillCdSpeed");
        this.interactSpeed=nbt.getDouble("interactSpeed");
        this.costReduction=nbt.getDouble("costReduction");
    }


}
