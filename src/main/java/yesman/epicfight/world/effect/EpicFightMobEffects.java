package yesman.epicfight.world.effect;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

public class EpicFightMobEffects {
	public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, EpicFightMod.MODID);
	
	public static final RegistryObject<Effect> STUN_IMMUNITY = EFFECTS.register("stun_immunity", () -> new VisibleMobEffect(EffectType.BENEFICIAL, "stun_immunity", 16758016));
	public static final RegistryObject<Effect> BLOOMING = EFFECTS.register("blooming", () -> new VisibleMobEffect(EffectType.BENEFICIAL, "blooming", 16735744));




	//tkk

	public static final RegistryObject<Effect> ATTACK_SPEED_ADD = EFFECTS.register("attack_speed_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"attack_speed_add", 14270531)).addAttributeModifier(Attributes.ATTACK_SPEED, "B8AEB320-1F53-30CD-4277-15D907E73E29", (double)0.05F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> ATTACK_SPEED_REMOVE = EFFECTS.register("attack_speed_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"attack_speed_remove", 14270531)).addAttributeModifier(Attributes.ATTACK_SPEED, "9E62C0CE-8A5B-0E75-4AD8-61916EC64C2A", (double)-0.05F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> ARMOR_ADD = EFFECTS.register("armor_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"armor_add", 14270531)).addAttributeModifier(Attributes.ARMOR, "E7170F5C-EDEF-37EF-2712-744A5FBA5506", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> ARMOR_REMOVE = EFFECTS.register("armor_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"armor_remove", 14270531)).addAttributeModifier(Attributes.ARMOR, "65E8B482-CE2B-CEC2-74BF-F2B939D99319", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> ARMOR_TOUGHNESS_ADD = EFFECTS.register("armor_toughness_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"armor_toughness_add", 14270531)).addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "70382F89-B98D-225B-DAAB-9A5866D985A9", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> ARMOR_TOUGHNESS_REMOVE = EFFECTS.register("armor_toughness_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"armor_toughness_remove", 14270531)).addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "EBA61E4A-6AB9-5B24-2B66-DA693F8ECF12", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> ATTACK_DAMAGE_ADD = EFFECTS.register("attack_damage_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"attack_damage_add", 14270531)).addAttributeModifier(Attributes.ATTACK_DAMAGE, "3E779A69-5C9A-B1C0-9ED1-513BE75F2949", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> ATTACK_DAMAGE_REMOVE = EFFECTS.register("attack_damage_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"attack_damage_remove", 14270531)).addAttributeModifier(Attributes.ATTACK_DAMAGE, "5F1CB06B-439B-DC35-E43F-B06547BB0678", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> KNOCKBACK_RESISTANCE_ADD = EFFECTS.register("knockback_resistance_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"knockback_resistance_add", 14270531)).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "E02BCF44-366B-CF92-2D74-F27DD656EDB8", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> KNOCKBACK_RESISTANCE_REMOVE = EFFECTS.register("knockback_resistance_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"knockback_resistance_remove", 14270531)).addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE, "893E1338-B077-439C-2AF7-303ED49614F4", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> STUN_ARMOR_ADD = EFFECTS.register("stun_armor_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"stun_armor_add", 14270531)).addAttributeModifier(EpicFightAttributes.STUN_ARMOR.get(), "9938C948-07DD-DE8D-AC56-0EC4EAE9901F", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> STUN_ARMOR_REMOVE = EFFECTS.register("stun_armor_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"stun_armor_remove", 14270531)).addAttributeModifier(EpicFightAttributes.STUN_ARMOR.get(), "5ECD522A-BC81-FC21-94E9-2FE30500254E", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> MAX_STAMINA_ADD = EFFECTS.register("max_stamina_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"max_stamina_add", 14270531)).addAttributeModifier(EpicFightAttributes.MAX_STAMINA.get(), "AB09E0D9-3F5E-D806-B788-7D6827BB844E", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> MAX_STAMINA_REMOVE = EFFECTS.register("max_stamina_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"max_stamina_remove", 14270531)).addAttributeModifier(EpicFightAttributes.MAX_STAMINA.get(), "03A41B10-0EDF-B64E-409B-71D6C9B8F070", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> WEIGHT_ADD = EFFECTS.register("weight_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"weight_add", 14270531)).addAttributeModifier(EpicFightAttributes.WEIGHT.get(), "BD2C0D7C-0C2F-37C5-D531-3959B27C4C02", (double)5F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> WEIGHT_REMOVE = EFFECTS.register("weight_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"weight_remove", 14270531)).addAttributeModifier(EpicFightAttributes.WEIGHT.get(), "205D2411-4240-C403-6A0C-6AFED4C6D06D", (double)-5F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> ARMOR_NEGATION_ADD = EFFECTS.register("armor_negation_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"armor_negation_add", 14270531)).addAttributeModifier(EpicFightAttributes.ARMOR_NEGATION.get(), "AE0890A5-7BEF-DD56-96A6-AC6E8988B147", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> ARMOR_NEGATION_REMOVE = EFFECTS.register("armor_negation_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"armor_negation_remove", 14270531)).addAttributeModifier(EpicFightAttributes.ARMOR_NEGATION.get(), "07CC6A82-DFC2-E02C-DC87-4623B52A4394", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> IMPACT_ADD = EFFECTS.register("impact_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"impact_add", 14270531)).addAttributeModifier(EpicFightAttributes.IMPACT.get(), "5A56641D-5DD4-1893-6D5D-B706F71ED44C", (double)0.5F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> IMPACT_REMOVE = EFFECTS.register("impact_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"impact_remove", 14270531)).addAttributeModifier(EpicFightAttributes.IMPACT.get(), "6EE2773B-9DEF-728E-4072-0076656E8F3F", (double)-0.5F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> OFFHAND_ATTACK_DAMAGE_ADD = EFFECTS.register("offhand_attack_damage_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_attack_damage_add", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_ATTACK_DAMAGE.get(), "CBA6BA14-4E37-CC30-6871-B91BC4EA408A", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> OFFHAND_ATTACK_DAMAGE_REMOVE = EFFECTS.register("offhand_attack_damage_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_attack_damage_remove", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_ATTACK_DAMAGE.get(), "3A911F1C-0EAA-32DE-5FF5-64A24351E32E", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> OFFHAND_ARMOR_NEGATION_ADD = EFFECTS.register("offhand_armor_negation_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_armor_negation_add", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get(), "D18322F3-9453-8B60-77BC-C5367C3D48E9", (double)1F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> OFFHAND_ARMOR_NEGATION_REMOVE = EFFECTS.register("offhand_armor_negation_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_armor_negation_remove", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_ARMOR_NEGATION.get(), "9EA7449E-0F81-409F-1F61-D99D62C41AD4", (double)-1F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> OFFHAND_IMPACT_ADD = EFFECTS.register("offhand_impact_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_impact_add", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_IMPACT.get(), "F33BEE69-C414-793C-A84A-8459F1B26480", (double)0.5F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> OFFHAND_IMPACT_REMOVE = EFFECTS.register("offhand_impact_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_impact_remove", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_IMPACT.get(), "F8D65BDB-F54A-2257-A5D9-62D802F5271D", (double)-0.5F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> OFFHAND_ATTACK_SPEED_ADD = EFFECTS.register("offhand_attack_speed_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_attack_speed_add", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_ATTACK_SPEED.get(), "468C6873-B58F-9A55-EC27-5CCF635418CE", (double)0.05F, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<Effect> OFFHAND_ATTACK_SPEED_REMOVE = EFFECTS.register("offhand_attack_speed_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"offhand_attack_speed_remove", 14270531)).addAttributeModifier(EpicFightAttributes.OFFHAND_ATTACK_SPEED.get(), "E666A18D-DD68-56A5-AE60-C55E31F663D2", (double)-0.05F, AttributeModifier.Operation.ADDITION));

	public static final RegistryObject<Effect> NAMETAG_DISTANCE_ADD = EFFECTS.register("nametag_distance_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"nametag_distance_add", 14270531)).addAttributeModifier(ForgeMod.NAMETAG_DISTANCE.get(), "D074BE8B-17B3-E6E6-CFA9-6990D4427B98", (double)0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<Effect> NAMETAG_DISTANCE_REMOVE = EFFECTS.register("nametag_distance_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"nametag_distance_remove", 14270531)).addAttributeModifier(ForgeMod.NAMETAG_DISTANCE.get(), "51631760-6440-2DC7-8389-9BAC32317CAE", (double)-0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL));

	public static final RegistryObject<Effect> ENTITY_GRAVITY_ADD = EFFECTS.register("entity_gravity_add", () -> (new AttributeEffect(EffectType.BENEFICIAL,"entity_gravity_add", 14270531)).addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "A9115376-8830-6C47-15A6-1A57E3E35258", (double)0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<Effect> ENTITY_GRAVITY_REMOVE = EFFECTS.register("entity_gravity_remove", () -> (new AttributeEffect(EffectType.BENEFICIAL,"entity_gravity_remove", 14270531)).addAttributeModifier(ForgeMod.ENTITY_GRAVITY.get(), "20AC9492-C534-D0DD-4E8D-0DFDEB981F5C", (double)-0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL));





}