package yesman.epicfight.world.capabilities.item;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeMod;
import yesman.epicfight.config.ConfigManager;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ArmorCapability extends CapabilityItem {
	protected static final UUID[] ARMOR_MODIFIERS = new UUID[] {UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
	protected final double weight;
	protected final double stunArmor;
	//ykk
	protected final double moveSpeed;
	protected final double attackSpeed;
	protected final double maxStamina;
	protected final double armor;
	protected final double armorToughness;
	protected final double nametagDistance;
	protected final double entityGravity;
	private final EquipmentSlotType equipmentSlot;
	
	protected ArmorCapability(CapabilityItem.Builder builder) {
		super(builder);
		
		ArmorCapability.Builder armorBuilder = (ArmorCapability.Builder)builder;
		
		this.equipmentSlot = armorBuilder.equipmentSlot;
		this.weight = armorBuilder.weight;
		this.stunArmor = armorBuilder.stunArmor;
		this.moveSpeed = armorBuilder.moveSpeed;
		this.attackSpeed = armorBuilder.attackSpeed;
		this.maxStamina = armorBuilder.maxStamina;
		this.armor = armorBuilder.armor;
		this.armorToughness = armorBuilder.armorToughness;
		this.nametagDistance = armorBuilder.nametagDistance;
		this.entityGravity = armorBuilder.entityGravity;
	}
	
	@Override
	public void modifyItemTooltip(ItemStack stack, List<ITextComponent> itemTooltip, LivingEntityPatch<?> entitypatch) {
		itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + (int)this.weight + " ").append(new TranslationTextComponent(EpicFightAttributes.WEIGHT.get().getDescriptionId()).withStyle(TextFormatting.BLUE)));

		if (this.stunArmor > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.stunArmor) + " ").append(new TranslationTextComponent(EpicFightAttributes.STUN_ARMOR.get().getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.moveSpeed > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.moveSpeed*100) + "% ").append(new TranslationTextComponent(Attributes.MOVEMENT_SPEED.getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.attackSpeed > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.attackSpeed*100) + "% ").append(new TranslationTextComponent(Attributes.ATTACK_SPEED.getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.maxStamina > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.maxStamina) + " ").append(new TranslationTextComponent(EpicFightAttributes.MAX_STAMINA.get().getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.armor > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.armor) + " ").append(new TranslationTextComponent(Attributes.ARMOR.getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.armorToughness > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.armorToughness) + " ").append(new TranslationTextComponent(Attributes.ARMOR_TOUGHNESS.getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.nametagDistance > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.nametagDistance*100) + "% ").append(new TranslationTextComponent(ForgeMod.NAMETAG_DISTANCE.get().getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}
		if (this.entityGravity > 0.0F) {
			itemTooltip.add(1, new StringTextComponent(TextFormatting.BLUE + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(this.entityGravity*100) + "% ").append(new TranslationTextComponent(ForgeMod.ENTITY_GRAVITY.get().getDescriptionId()).withStyle(TextFormatting.BLUE)));
		}

	}
	
	@Override
	public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot, LivingEntityPatch<?> entitypatch) {
		Multimap<Attribute, AttributeModifier> map = HashMultimap.<Attribute, AttributeModifier>create();
		
		if (entitypatch != null && equipmentSlot == this.equipmentSlot) {
			map.put(EpicFightAttributes.WEIGHT.get(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.weight, Operation.ADDITION));
			map.put(EpicFightAttributes.STUN_ARMOR.get(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.stunArmor, Operation.ADDITION));
			map.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.moveSpeed, Operation.MULTIPLY_BASE));
			map.put(Attributes.ATTACK_SPEED, new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.attackSpeed, Operation.MULTIPLY_BASE));
			map.put(EpicFightAttributes.MAX_STAMINA.get(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.maxStamina, Operation.ADDITION));
			map.put(Attributes.ARMOR, new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.armor, Operation.ADDITION));
			map.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.armorToughness, Operation.ADDITION));
			map.put(ForgeMod.NAMETAG_DISTANCE.get(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.nametagDistance, Operation.MULTIPLY_BASE));
			map.put(ForgeMod.ENTITY_GRAVITY.get(), new AttributeModifier(ARMOR_MODIFIERS[equipmentSlot.getIndex()], "Armor modifier", this.entityGravity, Operation.MULTIPLY_BASE));
		}
		
        return map;
    }
	
	public static ArmorCapability.Builder builder() {
		return new ArmorCapability.Builder();
	}
	
	public static class Builder extends CapabilityItem.Builder {
		EquipmentSlotType equipmentSlot;
		double weight;
		double stunArmor;
		double moveSpeed;
		double attackSpeed;
		double maxStamina;
		double armor;
		double armorToughness;
		double nametagDistance;
		double entityGravity;
		
		protected Builder() {
			this.constructor = ArmorCapability::new;
			this.weight = -1.0D;
			this.stunArmor = -1.0D;
			this.moveSpeed = 0.0D;
			this.attackSpeed = 0.0D;
			this.maxStamina = 0.0D;
			this.armor = 0.0D;
			this.armorToughness = 0.0D;
			this.nametagDistance = 0.0D;
			this.entityGravity = 0.0D;
		}
		public Builder item(Item item) {
			if (item instanceof ArmorItem) {
				ArmorItem armorItem = (ArmorItem) item;
				IArmorMaterial armorMaterial = armorItem.getMaterial();
				
				this.equipmentSlot = armorItem.getSlot();
				Random random=new Random(1145141919810L+item.getClass().toString().hashCode()+this.equipmentSlot.getName().hashCode());
				if (this.weight < 0.0D) {
					if(ConfigManager.AUTO_ARMOR_WEIGHT_STUNARMOR.get()){
						this.weight = armorMaterial.getDefenseForSlot(this.equipmentSlot) * (3.0F + (random.nextFloat() * 0.2f - 0.1f));
					}else{
						this.weight = 0;
					}
				}
				
				if (this.stunArmor < 0.0D) {
					if(ConfigManager.AUTO_ARMOR_WEIGHT_STUNARMOR.get()){
						this.stunArmor = armorMaterial.getDefenseForSlot(this.equipmentSlot) * (0.55F + (random.nextFloat() * 0.10f - 0.05f));
					}else{
						this.stunArmor = 0;
					}
				}
			}
			
			return this;
		}

		public Builder weight(double weight) {
			this.weight = weight;
			return this;
		}
		
		public Builder stunArmor(double stunArmor) {
			this.stunArmor = stunArmor;
			return this;
		}
		public Builder moveSpeed(double moveSpeed) {
			this.moveSpeed = moveSpeed;
			return this;
		}
		public Builder attackSpeed(double attackSpeed) {
			this.attackSpeed = attackSpeed;
			return this;
		}
		public Builder maxStamina(double maxStamina) {
			this.maxStamina = maxStamina;
			return this;
		}
		public Builder armor(double armor) {
			this.armor = armor;
			return this;
		}
		public Builder armorToughness(double armorToughness) {
			this.armorToughness = armorToughness;
			return this;
		}
		public Builder nametagDistance(double nametagDistance) {
			this.nametagDistance = nametagDistance;
			return this;
		}
		public Builder entityGravity(double entityGravity) {
			this.entityGravity = entityGravity;
			return this;
		}
	}
}