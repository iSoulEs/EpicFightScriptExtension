package yesman.epicfight.world.capabilities.item;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.item.Item;
import net.minecraft.item.TieredItem;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.ModLoader;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.forgeevent.WeaponCapabilityPresetRegistryEvent;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.gameasset.Skills;
import yesman.epicfight.skill.KatanaPassive;
import yesman.epicfight.skill.SkillCategories;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem.Styles;
import yesman.epicfight.world.capabilities.item.CapabilityItem.WeaponCategories;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.Map;
import java.util.function.Function;

public class WeaponCapabilityPresets {
	public static final Function<Item, CapabilityItem.Builder> AXE = (item) -> {
		CapabilityItem.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.AXE)
			.styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == WeaponCategories.AXE ? Styles.TWO_HAND : Styles.ONE_HAND)
			.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).weaponCategory == WeaponCategories.AXE)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.collider(ColliderPreset.TOOLS)
			.canBePlacedOffhand(true)
			.newStyleCombo(Styles.TWO_HAND, Animations.AXE_AUTO1, Animations.FROM_118_SWORD_DUAL_AUTO2, Animations.VANILLA_SWORD_DUAL_AUTO3,Animations.SWORD_DUAL_DASH, Animations.BIPED_MOB_SWORD_DUAL3_DUALAXE)
			.newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
			.specialAttack(Styles.TWO_HAND, Skills.DANCING_EDGE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_ONE_HAND_IDLE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_HOLD_DUAL_WEAPON)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR);
		
		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			
			if (harvestLevel != 0) {
				builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.ARMOR_NEGATION.get(), EpicFightAttributes.getArmorNegationModifier(10.0D * harvestLevel)));
			}
			
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.7D + 0.3D * harvestLevel)));
		}
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> HOE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.HOE)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.collider(ColliderPreset.TOOLS).newStyleCombo(Styles.ONE_HAND, Animations.TOOL_AUTO1, Animations.TOOL_AUTO2, Animations.TOOL_DASH, Animations.SWORD_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK);
		
		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(-0.4D + 0.1D * harvestLevel)));
		}
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> PICKAXE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.PICKAXE)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.collider(ColliderPreset.TOOLS)
			.newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK);
		
		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			
			if (harvestLevel != 0) {
				builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.ARMOR_NEGATION.get(), EpicFightAttributes.getArmorNegationModifier(6.0D * harvestLevel)));
			}
			
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.4D + 0.1D * harvestLevel)));
		}
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SHOVEL = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.SHOVEL)
			.collider(ColliderPreset.TOOLS)
			.newStyleCombo(Styles.ONE_HAND, Animations.AXE_AUTO1, Animations.AXE_AUTO2, Animations.AXE_DASH, Animations.AXE_AIRSLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK);
		
		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.8D + 0.4D * harvestLevel)));
		}
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.SWORD)
			.styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == WeaponCategories.SWORD ? Styles.TWO_HAND : Styles.ONE_HAND)
			.collider(ColliderPreset.SWORD)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.VANILLA_SWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
			.newStyleCombo(Styles.TWO_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.SWORD_DUAL_DASH, Animations.SWORD_DUAL_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
			.specialAttack(Styles.TWO_HAND, Skills.DANCING_EDGE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SWORD_DUAL_GUARD)
			.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).weaponCategory == WeaponCategories.SWORD)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_ONE_HAND_IDLE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_HOLD_DUAL_WEAPON)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR);
		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
		}
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_SPEAR_DASH = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.SPEAR_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		if (item instanceof TieredItem) {
			int harvestLevel = ((TieredItem)item).getTier().getLevel();
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.IMPACT.get(), EpicFightAttributes.getImpactModifier(0.5D + 0.2D * harvestLevel)));
			builder.addStyleAttibutes(Styles.COMMON, Pair.of(EpicFightAttributes.MAX_STRIKES.get(), EpicFightAttributes.getMaxStrikesModifier(1)));
		}

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_CHINESE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.DAGGER_AUTO2, Animations.SWORD_DASH, Animations.BIPED_ROLL_BACKWARD, Animations.SPEAR_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);
				

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_SWIFT = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.LONGSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.LONGSWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.TACHI_DASH, Animations.DAGGER_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_DASH, Animations.SWORD_DUAL_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_THORN = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.DAGGER_AUTO1, Animations.SPEAR_ONEHAND_AUTO, Animations.TACHI_DASH, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.HEARTPIERCER)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_MASTER = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.KATANA_AUTO1, Animations.SWORD_AUTO2, Animations.KATANA_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SLAUGHTER_STANCE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_HEAVY = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_DOUBLE_AXE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_DUAL_AUTO2, Animations.SWORD_DUAL_AUTO3, Animations.DANCING_EDGE, Animations.SWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.GIANT_WHIRLWIND)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_CORPS = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SPEAR_ONEHAND_AUTO, Animations.SWORD_AUTO2, Animations.SWORD_DUAL_AUTO2_RAPID, Animations.SWORD_DASH, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.HEARTPIERCER)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_SHIELDSWORD = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_DASH, Animations.SWORD_DUAL_AUTO2, Animations.SPEAR_ONEHAND_AUTO, Animations.FIST_AUTO3_LONG, Animations.SPEAR_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_PALADIN = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_DUAL_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_DUAL_AUTO2, Animations.DAGGER_DUAL_AUTO2, Animations.SWORD_DUAL_DASH, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.DANCING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SWORD_DOUBLESPEAR = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.BIPED_MOB_DAGGER_TWOHAND1, Animations.SPEAR_DASH, Animations.SWORD_DUAL_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.HEARTPIERCER)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SPEAR = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.SPEAR)
			.styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD ? Styles.ONE_HAND : Styles.TWO_HAND)
			.collider(ColliderPreset.SPEAR)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.canBePlacedOffhand(false)
			.newStyleCombo(Styles.ONE_HAND, Animations.SPEAR_ONEHAND_AUTO, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH)
			.newStyleCombo(Styles.TWO_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.SPEAR_DASH, Animations.SPEAR_TWOHAND_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
			.specialAttack(Styles.ONE_HAND, Skills.HEARTPIERCER)
			.specialAttack(Styles.TWO_HAND, Skills.SLAUGHTER_STANCE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_ONE_HAND_IDLE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)

			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.MOUNT, Animations.MENZEN_HORSE_IDLE)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.MOUNT, Animations.MENZEN_HORSE_IDLE);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SPEAR_GUANDAO = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.SWORD_AUTO3, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.SWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.SLAUGHTER_STANCE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SPEAR_LONGAXE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.GIANT_WHIRLWIND)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SPEAR_WENDIGO = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SPEAR_ONEHAND_AUTO, Animations.SWORD_AUTO2, Animations.SPEAR_DASH, Animations.AXE_AUTO2, Animations.SWORD_DASH, Animations.SPEAR_THRUST)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> SPEAR_HALBERD = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.KATANA_DASH, Animations.GREATSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> PITCH_FORK = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SPEAR_ONEHAND_AUTO, Animations.SPEAR_DASH, Animations.SPEAR_ONEHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.HEARTPIERCER)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GIANDT_SICKLE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.SICKLE_TWOHAND_AUTO1, Animations.SICKLE_TWOHAND_AUTO2, Animations.SICKLE_TWOHAND_AUTO3, Animations.SICKLE_TWOHAND_AUTO4, Animations.BIPED_ROLL_BACKWARD, Animations.SWEEPING_EDGE_CORRECT, Animations.SPEAR_TWOHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.SLAUGHTER_STANCE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GIANDT_SICKLE_CORRECT = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SPEAR)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.SPEAR)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.SICKLE_TWOHAND_AUTO1, Animations.SICKLE_TWOHAND_AUTO2, Animations.SICKLE_TWOHAND_AUTO4, Animations.SWEEPING_EDGE, Animations.BIPED_ROLL_BACKWARD, Animations.SWEEPING_EDGE_CORRECT, Animations.SPEAR_TWOHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SPEAR_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.SLAUGHTER_STANCE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.SPEAR_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GREATSWORD = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.GREATSWORD)
			.styleProvider((playerpatch) -> Styles.TWO_HAND)
			.collider(ColliderPreset.GREATSWORD)
			.swingSound(EpicFightSounds.WHOOSH_BIG)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.canBePlacedOffhand(false)
			.newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH2)
			.specialAttack(Styles.TWO_HAND, Skills.GIANT_WHIRLWIND)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_WALK_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.FROM_118_BIPED_RUN_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.FROM_118_BIPED_RUN_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.FROM_118_BIPED_WALK_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);
			/*
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
	    	.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);
	    	*/
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_HEAVY = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_HEAVY_CHOPPING, Animations.GREATSWORD_AIR_SLASH2)
				.specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_HIGHLANDER = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.TACHI_DASH, Animations.GREATSWORD_AIR_SLASH)
				.specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_STREET_DANCE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_DUAL_AUTO2_RAPID, Animations.SPEAR_TWOHAND_AUTO1, Animations.DAGGER_AUTO3_DUNT, Animations.AXE_DASH_DUNT, Animations.DAGGER_DUAL_AUTO4_HOLD, Animations.AXE_AUTO1_DUNT, Animations.DAGGER_AUTO3_DUNT, Animations.SWORD_AUTO3_DUNT, Animations.AXE_DASH, Animations.LONGSWORD_AIR_SLASH_JUMP)
				.specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD);

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_STREET_DANCE2 = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SPEAR_TWOHAND_AUTO1, Animations.SWORD_DASH, Animations.SWORD_DUAL_AUTO1, Animations.DAGGER_DUAL_AUTO4_HOLD, Animations.SWORD_AUTO3, Animations.AXE_DASH, Animations.LONGSWORD_AIR_SLASH_JUMP)
				.specialAttack(Styles.ONE_HAND, Skills.GUILLOTINE_AXE)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD);

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_BERSERK = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.GREATSWORD_AUTO2, Animations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH, Animations.SWORD_AIR_SLASH, Animations.GUILLOTINE_AXE, Animations.GREATSWORD_DASH, Animations.SWORD_AIR_SLASH)
				.specialAttack(Styles.ONE_HAND, Skills.GIANT_WHIRLWIND)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_BERSERK1 = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO2, Animations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH, Animations.SWORD_AIR_SLASH, Animations.GUILLOTINE_AXE, Animations.GREATSWORD_DASH, Animations.SWORD_AIR_SLASH)
				.specialAttack(Styles.TWO_HAND, Skills.GIANT_WHIRLWIND)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_GUTS = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.BIPED_MOB_LONGSWORD2, Animations.GREATSWORD_DASH, Animations.GREATSWORD_AIR_SLASH)
				.specialAttack(Styles.ONE_HAND, Skills.GIANT_WHIRLWIND)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> GREATSWORD_LONGNIGHT = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.GREATSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_AUTO3_LONGNIGHT, Animations.GREATSWORD_AUTO4_LONGNIGHT, Animations.GUILLOTINE_AXE, Animations.GREATSWORD_HEAVY_CHOPPING_LONGNIGHT, Animations.GREATSWORD_AIR_SLASH_LONGNIGHT)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder>  GREATSWORD_WITCHCURSE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.GREATSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.GREATSWORD)
				.swingSound(EpicFightSounds.WHOOSH_BIG)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.GREATSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.SWORD_AUTO3, Animations.LONGSWORD_DASH2, Animations.GREATSWORD_AIR_SLASH2)
				.specialAttack(Styles.TWO_HAND, Skills.SWEEPING_EDGE)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.GREATSWORD_GUARD);

		return builder;
	};

	public static final Function<Item, CapabilityItem.Builder> KATANA = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.KATANA)
			.styleProvider((entitypatch) -> {
				if (entitypatch instanceof PlayerPatch) {
					PlayerPatch<?> playerpatch = (PlayerPatch<?>)entitypatch;
					if (playerpatch.getSkill(SkillCategories.WEAPON_PASSIVE).getDataManager().hasData(KatanaPassive.SHEATH) && 
							playerpatch.getSkill(SkillCategories.WEAPON_PASSIVE).getDataManager().getDataValue(KatanaPassive.SHEATH)) {
						return Styles.SHEATH;
					}
				}
				return Styles.TWO_HAND;
			})
			.passiveSkill(Skills.KATANA_PASSIVE)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.collider(ColliderPreset.KATANA)
			.canBePlacedOffhand(false)
			.newStyleCombo(Styles.SHEATH, Animations.KATANA_SHEATHING_AUTO, Animations.KATANA_SHEATHING_DASH, Animations.KATANA_SHEATH_AIR_SLASH)
			.newStyleCombo(Styles.TWO_HAND, Animations.KATANA_AUTO1, Animations.KATANA_AUTO2, Animations.KATANA_AUTO3, Animations.KATANA_AUTO4, Animations.SWORD_DASH, Animations.KATANA_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.specialAttack(Styles.SHEATH, Skills.FATAL_DRAW)
			.specialAttack(Styles.TWO_HAND, Skills.FATAL_DRAW)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_KATANA)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_WALK_UNSHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_UNSHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_WALK_UNSHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_WALK_UNSHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_WALK_UNSHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_WALK_UNSHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.KNEEL, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.WALK, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.CHASE, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.RUN, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.SNEAK, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.SWIM, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.FLOAT, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.SHEATH, LivingMotions.FALL, Animations.BIPED_HOLD_KATANA_SHEATHING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.KATANA_GUARD);
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> TACHI = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.TACHI)
			.styleProvider((playerpatch) -> Styles.TWO_HAND)
			.collider(ColliderPreset.KATANA)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.canBePlacedOffhand(false)
			.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.VANILLA_TACHI_DASH, Animations.LONGSWORD_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.specialAttack(Styles.TWO_HAND, Skills.LETHAL_SLICING)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> TACHI2 = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.TACHI)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.KATANA)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.LETHAL_SLICING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> TACHI_SNAKE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.TACHI)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.KATANA)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.KATANA_AUTO1, Animations.DAGGER_AUTO2, Animations.AXE_DASH, Animations.GREATSWORD_DASH, Animations.SWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> TACHI_GREATKATANA = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.TACHI)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.KATANA)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.SPEAR_TWOHAND_AUTO2, Animations.TACHI_DASH, Animations.BIPED_MOB_LONGSWORD2, Animations.SPEAR_TWOHAND_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.GIANT_WHIRLWIND)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> TACHI_FIER = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.TACHI)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.KATANA)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.SWORD_DUAL_AUTO1, Animations.DAGGER_AUTO2, Animations.GREATSWORD_DASH, Animations.TACHI_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.LETHAL_SLICING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_KATANA)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_UNSHEATHING)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FLOAT, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.FALL, Animations.BIPED_HOLD_TACHI)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> LONGSWORD = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.LONGSWORD)
			.styleProvider((entitypatch) -> {
				if (entitypatch instanceof PlayerPatch<?>) {
					if (((PlayerPatch<?>)entitypatch).getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK).getRemainDuration() > 0) {
						return Styles.LIECHTENAUER;
					}
				}
				return Styles.TWO_HAND;
			})
			.hitSound(EpicFightSounds.BLADE_HIT)
			.collider(ColliderPreset.LONGSWORD)
			.canBePlacedOffhand(false)
			.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
			.newStyleCombo(Styles.LIECHTENAUER, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.specialAttack(Styles.TWO_HAND, Skills.LIECHTENAUER)
			.specialAttack(Styles.LIECHTENAUER, Skills.LIECHTENAUER)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.WALK, Animations.BIPED_HOLD_LONGSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.RUN, Animations.BIPED_HOLD_LONGSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
			.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> LONGSWORD_GUILLOTINE_AXE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.LONGSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.LONGSWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> LONGSWORD_PLATE_AXE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.LONGSWORD)
				.styleProvider((playerpatch) -> Styles.TWO_HAND)
				.collider(ColliderPreset.LONGSWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.GREATSWORD_AUTO2, Animations.GREATSWORD_HEAVY_CHOPPING, Animations.GREATSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> HAND_HALF_SWORD = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.LONGSWORD)
				.styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == WeaponCategories.SHIELD ? Styles.ONE_HAND : Styles.TWO_HAND)
				.collider(ColliderPreset.LONGSWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.ONE_HAND, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.SWORD_AUTO3, Animations.AXE_DASH, Animations.SWORD_AIR_SLASH)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.specialAttack(Styles.TWO_HAND, Skills.GUILLOTINE_AXE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> DAGGER = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
			.category(WeaponCategories.DAGGER)
			.styleProvider((playerpatch) -> playerpatch.getHoldingItemCapability(Hand.OFF_HAND).getWeaponCategory() == WeaponCategories.DAGGER ? Styles.TWO_HAND : Styles.ONE_HAND)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.collider(ColliderPreset.DAGGER)
			.weaponCombinationPredicator((entitypatch) -> EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).weaponCategory == WeaponCategories.DAGGER)
			.newStyleCombo(Styles.ONE_HAND, Animations.DAGGER_AUTO1, Animations.DAGGER_AUTO2, Animations.DAGGER_AUTO3, Animations.SWORD_DASH, Animations.DAGGER_AIR_SLASH)
			.newStyleCombo(Styles.TWO_HAND, Animations.DAGGER_DUAL_AUTO1, Animations.DAGGER_DUAL_AUTO2, Animations.DAGGER_DUAL_AUTO3, Animations.DAGGER_DUAL_AUTO4, Animations.DAGGER_DUAL_DASH, Animations.DAGGER_DUAL_AIR_SLASH)
			.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
			.specialAttack(Styles.ONE_HAND, Skills.EVISCERATE)
			.specialAttack(Styles.TWO_HAND, Skills.BLADE_RUSH)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_ONE_HAND_IDLE)
			.livingMotionModifier(Styles.ONE_HAND, LivingMotions.WALK, Animations.FROM_118_BIPED_ONE_HAND_WALK)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.FROM_118_BIPED_HOLD_DUAL_WEAPON)
			.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK);
		
		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> DAGGER_ROMAN = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.DAGGER_AUTO1, Animations.DAGGER_AUTO2, Animations.FIST_AUTO3_LONG, Animations.BIPED_MOB_DAGGER_ONEHAND3, Animations.SPEAR_DASH, Animations.DAGGER_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.EVISCERATE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> DAGGER_STREET_DANCE = (item) -> WeaponCapability.builder()
			.newStyleCombo(Styles.ONE_HAND, Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH)
			.specialAttack(Styles.ONE_HAND, Skills.EVISCERATE)
			.category(WeaponCategories.FIST)
			.hitSound(EpicFightSounds.BLADE_HIT)
			.constructor(KnuckleCapability::new);
	public static final Function<Item, CapabilityItem.Builder> FIST = (item) -> WeaponCapability.builder()
			.newStyleCombo(Styles.ONE_HAND, Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH)
			.specialAttack(Styles.ONE_HAND, Skills.RELENTLESS_COMBO)
			.category(WeaponCategories.FIST)
			.constructor(KnuckleCapability::new);
	public static final Function<Item, CapabilityItem.Builder> FIST_KNOCKDOWN = (item) -> WeaponCapability.builder()
			.newStyleCombo(Styles.ONE_HAND, Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.FIST_AUTO3, Animations.FIST_DASH, Animations.FIST_AIR_SLASH_KNOCKDOWN)
			.specialAttack(Styles.ONE_HAND, Skills.RELENTLESS_COMBO)
			.category(WeaponCategories.FIST)
			.constructor(KnuckleCapability::new);
	public static final Function<Item, CapabilityItem.Builder> FIST_BOXER = (item) -> WeaponCapability.builder()
			.newStyleCombo(Styles.ONE_HAND, Animations.FIST_AUTO1, Animations.FIST_AUTO2, Animations.SWORD_DUAL_AUTO2, Animations.BIPED_STEP_FORWARD, Animations.SWORD_DASH, Animations.SWORD_AIR_SLASH)
			.specialAttack(Styles.ONE_HAND, Skills.RELENTLESS_COMBO)
			.category(WeaponCategories.FIST)
			.constructor(KnuckleCapability::new);
	public static final Function<Item, CapabilityItem.Builder> FIST_ONE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.BIPED_MOB_DAGGER_TWOHAND1, Animations.BIPED_MOB_DAGGER_TWOHAND2, Animations.BIPED_MOB_THROW)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> FIST_TWO = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.BIPED_MOB_LONGSWORD1, Animations.BIPED_MOB_LONGSWORD2, Animations.BIPED_MOB_TACHI)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> FIST_THREE = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.SWORD)
				.styleProvider((playerpatch) -> Styles.ONE_HAND)
				.collider(ColliderPreset.SWORD)
				.hitSound(EpicFightSounds.BLADE_HIT)
				.newStyleCombo(Styles.ONE_HAND, Animations.BIPED_MOB_DAGGER_ONEHAND1, Animations.BIPED_MOB_DAGGER_ONEHAND2, Animations.BIPED_MOB_DAGGER_ONEHAND3)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.ONE_HAND, Skills.SWEEPING_EDGE)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
				.livingMotionModifier(Styles.ONE_HAND, LivingMotions.BLOCK, Animations.SWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> FIST_FOUR = (item) -> {
		WeaponCapability.Builder builder = WeaponCapability.builder()
				.category(WeaponCategories.LONGSWORD)
				.styleProvider((entitypatch) -> {
					if (entitypatch instanceof PlayerPatch<?>) {
						if (((PlayerPatch<?>)entitypatch).getSkill(SkillCategories.WEAPON_SPECIAL_ATTACK).getRemainDuration() > 0) {
							return Styles.LIECHTENAUER;
						}
					}
					return Styles.TWO_HAND;
				})
				.hitSound(EpicFightSounds.BLADE_HIT)
				.collider(ColliderPreset.LONGSWORD)
				.canBePlacedOffhand(false)
				.newStyleCombo(Styles.TWO_HAND, Animations.LONGSWORD_AUTO1, Animations.LONGSWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.LIECHTENAUER, Animations.SWORD_AUTO1, Animations.SWORD_AUTO2, Animations.LONGSWORD_AUTO3, Animations.LONGSWORD_DASH, Animations.LONGSWORD_AIR_SLASH)
				.newStyleCombo(Styles.MOUNT, Animations.SWORD_MOUNT_ATTACK)
				.specialAttack(Styles.TWO_HAND, Skills.LIECHTENAUER)
				.specialAttack(Styles.LIECHTENAUER, Skills.LIECHTENAUER)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SNEAK, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.KNEEL, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.JUMP, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.WALK, Animations.BIPED_HOLD_LONGSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.CHASE, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.RUN, Animations.BIPED_HOLD_LONGSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.SNEAK, Animations.BIPED_HOLD_LONGSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.KNEEL, Animations.BIPED_HOLD_LONGSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.JUMP, Animations.BIPED_HOLD_LONGSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.SWIM, Animations.BIPED_HOLD_GREATSWORD)
				.livingMotionModifier(Styles.LIECHTENAUER, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD);

		return builder;
	};
	public static final Function<Item, CapabilityItem.Builder> OLDGUN =  (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.KNEEL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SNEAK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SWIM, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FLOAT, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FALL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
			.addAnimationsModifier(LivingMotions.RELOAD, Animations.MENZEN_FIREGUN_RELOAD2)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_BOW_AIM);

	public static final Function<Item, CapabilityItem.Builder> FIREGUN =  (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.KNEEL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SNEAK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SWIM, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FLOAT, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FALL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
			.addAnimationsModifier(LivingMotions.RELOAD, Animations.MENZEN_FIREGUN_RELOAD)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_BOW_AIM);

	public static final Function<Item, CapabilityItem.Builder> CROSSBOWXX =  (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.KNEEL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SNEAK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SWIM, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FLOAT, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FALL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
			.addAnimationsModifier(LivingMotions.RELOAD, Animations.MENZEN_XBOW_RELOAD)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_BOW_AIM);

	public static final Function<Item, CapabilityItem.Builder> CROSSBOWX =  (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.KNEEL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SNEAK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SWIM, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FLOAT, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FALL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_BOW_SHOT)
			.addAnimationsModifier(LivingMotions.RELOAD, Animations.MENZEN_CROSSBOW_RELOAD)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_BOW_AIM);

	public static final Function<Item, CapabilityItem.Builder> BOW =  (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_IDLE)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_WALK)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_BOW_AIM)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_BOW_SHOT);
			
	public static final Function<Item, CapabilityItem.Builder> CROSSBOW =  (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.KNEEL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.RUN, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SNEAK, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.SWIM, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FLOAT, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.FALL, Animations.BIPED_HOLD_CROSSBOW)
			.addAnimationsModifier(LivingMotions.RELOAD, Animations.BIPED_CROSSBOW_RELOAD)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_CROSSBOW_AIM)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_CROSSBOW_SHOT);
	
	public static final Function<Item, CapabilityItem.Builder> TRIDENT = (item) -> RangedWeaponCapability.builder()
			.addAnimationsModifier(LivingMotions.IDLE, Animations.BIPED_IDLE)
			.addAnimationsModifier(LivingMotions.WALK, Animations.BIPED_WALK)
			.addAnimationsModifier(LivingMotions.AIM, Animations.BIPED_JAVELIN_AIM)
			.addAnimationsModifier(LivingMotions.SHOT, Animations.BIPED_JAVELIN_THROW)
			.constructor(TridentCapability::new)
			.category(WeaponCategories.TRIDENT);
	
	public static final Function<Item, CapabilityItem.Builder> SHIELD = (item) -> CapabilityItem.builder()
			.constructor(ShieldCapability::new)
			.category(WeaponCategories.SHIELD);
	
	private static final Map<String, Function<Item, CapabilityItem.Builder>> PRESETS = Maps.newHashMap();
	
	public static void register() {
		Map<String, Function<Item, CapabilityItem.Builder>> typeEntry = Maps.newHashMap();
		typeEntry.put("axe", AXE);
		typeEntry.put("fist", FIST);
		typeEntry.put("fistknockdown", FIST_KNOCKDOWN);
		typeEntry.put("fistboxer", FIST_BOXER);
		typeEntry.put("hoe", HOE);
		typeEntry.put("pickaxe", PICKAXE);
		typeEntry.put("shovel", SHOVEL);
		typeEntry.put("sword", SWORD);
		typeEntry.put("swordspeardash", SWORD_SPEAR_DASH);
		typeEntry.put("swordchinese", SWORD_CHINESE);
		typeEntry.put("swordthorn", SWORD_THORN);
		typeEntry.put("swordswift", SWORD_SWIFT);
		typeEntry.put("swordmaster", SWORD_MASTER);
		typeEntry.put("swordheavy", SWORD_HEAVY);
		typeEntry.put("sworddoubleaxe", SWORD_DOUBLE_AXE);
		typeEntry.put("swordcorps", SWORD_CORPS);
		typeEntry.put("swordshieldsword", SWORD_SHIELDSWORD);
		typeEntry.put("swordpaladin", SWORD_PALADIN);
		typeEntry.put("sworddoublespear", SWORD_DOUBLESPEAR);
		typeEntry.put("spear", SPEAR);
		typeEntry.put("spearguandao", SPEAR_GUANDAO);
		typeEntry.put("spearlongaxe", SPEAR_LONGAXE);
		typeEntry.put("spearwendigo", SPEAR_WENDIGO);
		typeEntry.put("spearhalberd", SPEAR_HALBERD);
		typeEntry.put("pitchfork", PITCH_FORK);
		typeEntry.put("giantsickle", GIANDT_SICKLE);
		typeEntry.put("giantsicklecorrect", GIANDT_SICKLE_CORRECT);
		typeEntry.put("greatsword", GREATSWORD);
		typeEntry.put("greatswordheavy", GREATSWORD_HEAVY);
		typeEntry.put("greatswordhighlander", GREATSWORD_HIGHLANDER);
		typeEntry.put("greatswordstreetdance", GREATSWORD_STREET_DANCE);
		typeEntry.put("greatswordstreetdance2", GREATSWORD_STREET_DANCE2);
		typeEntry.put("greatswordberserk", GREATSWORD_BERSERK);
		typeEntry.put("greatswordberserk1", GREATSWORD_BERSERK1);
		typeEntry.put("greatswordguts", GREATSWORD_GUTS);
		typeEntry.put("greatswordlongnight", GREATSWORD_LONGNIGHT);
		typeEntry.put("greatswordwitchcurse", GREATSWORD_WITCHCURSE);
		typeEntry.put("katana", KATANA);
		typeEntry.put("tachi", TACHI);
		typeEntry.put("tachi2", TACHI2);
		typeEntry.put("tachisnake", TACHI_SNAKE);
		typeEntry.put("tachigreatkatana", TACHI_GREATKATANA);
		typeEntry.put("tachifire", TACHI_FIER);
		typeEntry.put("longsword", LONGSWORD);
		typeEntry.put("longswordguillotineaxe", LONGSWORD_GUILLOTINE_AXE);
		typeEntry.put("longswordplateaxe", LONGSWORD_PLATE_AXE);
		typeEntry.put("handhalfsuord", HAND_HALF_SWORD);
		typeEntry.put("dagger", DAGGER);
		typeEntry.put("daggerroman", DAGGER_ROMAN);
		typeEntry.put("daggerstreetdance", DAGGER_STREET_DANCE);
		typeEntry.put("bow", BOW);
		typeEntry.put("crossbow", CROSSBOW);
		typeEntry.put("trident", TRIDENT);
		typeEntry.put("shield", SHIELD);
		typeEntry.put("fistone", FIST_ONE);
		typeEntry.put("fisttwo", FIST_TWO);
		typeEntry.put("fistthree", FIST_THREE);
		typeEntry.put("fistfour", FIST_FOUR);
		typeEntry.put("crossbowx", CROSSBOWX);
		typeEntry.put("crossbowxx", CROSSBOWXX);
		typeEntry.put("firegun", FIREGUN);
		typeEntry.put("oldgun", OLDGUN);

		WeaponCapabilityPresetRegistryEvent weaponCapabilityPresetRegistryEvent = new WeaponCapabilityPresetRegistryEvent(typeEntry);
		ModLoader.get().postEvent(weaponCapabilityPresetRegistryEvent);
		weaponCapabilityPresetRegistryEvent.getTypeEntry().forEach(PRESETS::put);
	}
	
	public static Function<Item, CapabilityItem.Builder> get(String typeName) {
		return PRESETS.get(typeName);
	}
}