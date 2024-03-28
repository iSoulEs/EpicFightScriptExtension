package tkk.epicrelic.attribute;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tkk.epicrelic.Enr;

public class EnrAttribute {
    /*
    力量 strength-增加力量系伤害，少量防御
    体质 vitality-增加耐力上限，少量生命
    敏捷 dexterity-增加敏捷系伤害，交互速度
    毅力 willpower-降低耐力消耗，少量全负荷
    魔力 mentality-增加魔力系伤害
    技艺 technique-增加耐力恢复速度，少量降低cd

    交互速度
    降低cd
    降低消耗Cost Reduction cost_reduction

    */
    public static final String ENR_ID = Enr.MODID;
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, ENR_ID);

    public static final RegistryObject<Attribute> STRENGTH = ATTRIBUTES.register("strength", () -> new RangedAttribute("attribute.name." + ENR_ID + ".strength", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> VITALITY = ATTRIBUTES.register("vitality", () -> new RangedAttribute("attribute.name." + ENR_ID + ".vitality", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> DEXTERITY = ATTRIBUTES.register("dexterity", () -> new RangedAttribute("attribute.name." + ENR_ID + ".dexterity", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> WILLPOWER = ATTRIBUTES.register("willpower", () -> new RangedAttribute("attribute.name." + ENR_ID + ".willpower", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MENTALITY = ATTRIBUTES.register("mentality", () -> new RangedAttribute("attribute.name." + ENR_ID + ".mentality", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> TECHNIQUE = ATTRIBUTES.register("technique", () -> new RangedAttribute("attribute.name." + ENR_ID + ".technique", 0.0D, 0.0D, 1024.0D).setSyncable(true));

    public static final RegistryObject<Attribute> STRENGTH_LOAD = ATTRIBUTES.register("strength_load", () -> new RangedAttribute("attribute.name." + ENR_ID + ".strength_load", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> VITALITY_LOAD = ATTRIBUTES.register("vitality_load", () -> new RangedAttribute("attribute.name." + ENR_ID + ".vitality_load", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> DEXTERITY_LOAD = ATTRIBUTES.register("dexterity_load", () -> new RangedAttribute("attribute.name." + ENR_ID + ".dexterity_load", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> WILLPOWER_LOAD = ATTRIBUTES.register("willpower_load", () -> new RangedAttribute("attribute.name." + ENR_ID + ".willpower_load", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MENTALITY_LOAD = ATTRIBUTES.register("mentality_load", () -> new RangedAttribute("attribute.name." + ENR_ID + ".mentality_load", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> TECHNIQUE_LOAD = ATTRIBUTES.register("technique_load", () -> new RangedAttribute("attribute.name." + ENR_ID + ".technique_load", 0.0D, 0.0D, 1024.0D).setSyncable(true));

    public static final RegistryObject<Attribute> INTERACT_SPEED = ATTRIBUTES.register("interact_speed", () -> new RangedAttribute("attribute.name." + ENR_ID + ".interact_speed", 0.0D, -1024.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> SKILL_CD_SPEED = ATTRIBUTES.register("skill_cd_speed", () -> new RangedAttribute("attribute.name." + ENR_ID + ".skill_cd_speed", 0.0D, -1024.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> COST_REDUCTION = ATTRIBUTES.register("cost_reduction", () -> new RangedAttribute("attribute.name." + ENR_ID + ".cost_reduction", 0.0D, -1024.0D, 1024.0D).setSyncable(true));




    @SubscribeEvent
    public static void modifyExistingMobs(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER,STRENGTH.get());
        event.add(EntityType.PLAYER,VITALITY.get());
        event.add(EntityType.PLAYER,DEXTERITY.get());
        event.add(EntityType.PLAYER,WILLPOWER.get());
        event.add(EntityType.PLAYER,MENTALITY.get());
        event.add(EntityType.PLAYER,TECHNIQUE.get());

        event.add(EntityType.PLAYER,STRENGTH_LOAD.get());
        event.add(EntityType.PLAYER,VITALITY_LOAD.get());
        event.add(EntityType.PLAYER,DEXTERITY_LOAD.get());
        event.add(EntityType.PLAYER,WILLPOWER_LOAD.get());
        event.add(EntityType.PLAYER,MENTALITY_LOAD.get());
        event.add(EntityType.PLAYER,TECHNIQUE_LOAD.get());

        event.add(EntityType.PLAYER,INTERACT_SPEED.get());
        event.add(EntityType.PLAYER,SKILL_CD_SPEED.get());
        event.add(EntityType.PLAYER,COST_REDUCTION.get());
    }



}
