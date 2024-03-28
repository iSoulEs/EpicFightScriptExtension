package tkk.epicrelic.attribute;

import com.google.common.collect.Multimap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tkk.epicrelic.capability.EnrCapabilityProvider;
import tkk.epicrelic.capability.IEnrCapability;
import yesman.epicfight.world.entity.ai.attribute.EpicFightAttributes;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static yesman.epicfight.api.utils.math.Formulars.WIGHT_START;

public class EnrAttributeManager {
    /*
    力量：每级提升百分之0.5攻击力，0.5穿甲，穿甲本来就是百分比
    敏捷：每级降低百分之0.8重力，并提升百分之0.37移动速度，减少百分之1.7名称渲染距离。交互速度若干。
    体质：每级提升1点生命。
    毅力：每级别提升0.5盔甲,0.5击退抗性,0.5盔甲韧性,0.5击晕抗性,百分之1重力和降低百分之0.5移动速度
    技艺：每级别提升0.008/1重量的体力，和0.005/1重量的攻击速度，并每级减少百分之0.3移动速度


    体制改成
    生命0.668
    力量
    攻击力0.01
    敏捷改成
    重力-0.0116
    速度0.006
    多加一个造成固定攻击力提升0.05每级




    */

    //生命
    public static final UUID HEALTH_UUID=UUID.fromString("6DA888EE-DFAF-43C3-9F48-2269CE075FDF");
    //盔甲
    public static final UUID ARMOR_UUID=UUID.fromString("D525613D-C2E8-D05C-692D-146F1E2F4064");
    //盔甲韧性
    public static final UUID ARMOR_TOUGHNESS_UUID=UUID.fromString("866F7CCB-DEE9-3754-47BE-52A7BA92B894");
    //击退抗性
    public static final UUID KNOCKBACK_RESISTANCE_UUID=UUID.fromString("49A198A6-ECE8-EBDF-31E8-3B2C51FB2CC8");
    //击晕抗性
    public static final UUID STUN_ARMOR_UUID=UUID.fromString("2CD20B8D-635C-063F-505F-1ED0F1255E7F");
    //攻击1
    public static final UUID ATTACK_DAMAGE_UUID1=UUID.fromString("761A7184-063D-7FC7-D082-EA7B66DFC952");
    //攻击2
    public static final UUID ATTACK_DAMAGE_UUID2=UUID.fromString("73EE0DD4-90A7-3F22-BE6A-6B2A536AC7EA");
    //穿甲
    public static final UUID ARMOR_NEGATION_UUID=UUID.fromString("5EECB3B7-C928-9ADE-2131-29C868161371");
    //-重力 1
    public static final UUID ENTITY_GRAVITY_UUID1=UUID.fromString("AFB2301D-DC1C-2792-9B69-993C367BC764");
    //体力
    public static final UUID STAMINA_UUID=UUID.fromString("58A451D4-A333-D6DF-E20B-2573B3B3B50E");
    //移速 1
    public static final UUID MOVEMENT_SPEED_UUID1=UUID.fromString("E7AD2A2B-9C96-C006-3BEB-E96CDF6E6B1E");
    //名称渲染距离
    public static final UUID NAMETAG_DISTANCE_UUID=UUID.fromString("4A885794-BF72-F8AD-6FE4-5D8B646F8D0C");
    //+重力 2
    public static final UUID ENTITY_GRAVITY_UUID2=UUID.fromString("0CB12AAB-E74D-C1CF-81B4-F8F90E82EA12");
    //-移速 2
    public static final UUID MOVEMENT_SPEED_UUID2=UUID.fromString("A369C2D6-7861-14EE-4F07-92C286CAAA5C");
    //攻速
    public static final UUID ATTACK_SPEED_UUID=UUID.fromString("61E9CDFA-8A8A-824D-E2D2-8BFC8518DEA9");
    //-移速 3
    public static final UUID MOVEMENT_SPEED_UUID3=UUID.fromString("AA2D9533-EE41-1256-F74C-37519836CB44");


    public static final UUID INTERACT_SPEED_UUID=UUID.fromString("D51B0852-0152-EF45-EF49-761A44D1EFC7");
    public static final UUID SKILL_CD_SPEED_UUID=UUID.fromString("424513E6-98FD-F2B5-272C-FF62C9775DEE");
    public static final UUID COST_REDUCTION_UUID=UUID.fromString("5FCE1B15-F588-8C08-627A-86449BA0A68B");

    public static final Set<PlayerEntity> NEED_UPDATE= new HashSet();

    public static void reg(){
        MinecraftForge.EVENT_BUS.register(EnrAttributeManager.class);
    }
    @SubscribeEvent
    public static void specialSpawn(EntityJoinWorldEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            syncAttribute((PlayerEntity) event.getEntity());
            //healer((LivingEntity) event.getEntity());
        }
    }
    @SubscribeEvent
    public static void onLivingEquipmentChangeEvent(LivingEquipmentChangeEvent event){
        if(event.getEntity() instanceof PlayerEntity){
            NEED_UPDATE.add((PlayerEntity) event.getEntity());
        }
    }
    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent tickEvent){
        if(tickEvent.phase!= TickEvent.Phase.END){return;}
        if(NEED_UPDATE.contains(tickEvent.player)){
            syncAttribute(tickEvent.player);
            NEED_UPDATE.remove(tickEvent.player);
        }
        double allLoad=getAttribute(tickEvent.player, EnrAttribute.WILLPOWER.get())*0.5;
        attributeStrengthLoad(tickEvent.player,getAttribute(tickEvent.player, EnrAttribute.STRENGTH.get())+allLoad,getAttribute(tickEvent.player,EnrAttribute.STRENGTH_LOAD.get()));
        attributeVitalityLoad(tickEvent.player,getAttribute(tickEvent.player, EnrAttribute.VITALITY.get())+allLoad,getAttribute(tickEvent.player,EnrAttribute.VITALITY_LOAD.get()));
        attributeDexterityLoad(tickEvent.player,getAttribute(tickEvent.player, EnrAttribute.DEXTERITY.get())+allLoad,getAttribute(tickEvent.player,EnrAttribute.DEXTERITY_LOAD.get()));
        attributeWillpowerLoad(tickEvent.player,getAttribute(tickEvent.player, EnrAttribute.WILLPOWER.get())+allLoad,getAttribute(tickEvent.player,EnrAttribute.WILLPOWER_LOAD.get()));
        attributeMentalityLoad(tickEvent.player,getAttribute(tickEvent.player, EnrAttribute.MENTALITY.get())+allLoad,getAttribute(tickEvent.player,EnrAttribute.MENTALITY_LOAD.get()));
        attributeTechniqueLoad(tickEvent.player,getAttribute(tickEvent.player, EnrAttribute.TECHNIQUE.get())+allLoad,getAttribute(tickEvent.player,EnrAttribute.TECHNIQUE_LOAD.get()));
    }
    public static void attributeStrengthLoad(LivingEntity livingEntity,double maxLoad,double load){
        double excess=load-maxLoad;
        if(excess>0){
            if(excess > 4){
                livingEntity.addEffect(new EffectInstance(Effect.byId(4),10,2,false,false));
            }else{
                livingEntity.addEffect(new EffectInstance(Effect.byId(4),10,0,false,false));
            }
        }
    }
    public static void attributeVitalityLoad(LivingEntity livingEntity,double maxLoad,double load){
        double excess=load-maxLoad;
        if(excess>0){
            if(excess > 4){
                livingEntity.addEffect(new EffectInstance(Effect.byId(18),10,2,false,false));
            }else{
                livingEntity.addEffect(new EffectInstance(Effect.byId(18),10,0,false,false));
            }
        }
    }
    public static void attributeDexterityLoad(LivingEntity livingEntity,double maxLoad,double load){
        double excess=load-maxLoad;
        if(excess>0){
            if(excess > 4){
                livingEntity.addEffect(new EffectInstance(Effect.byId(2),10,2,false,false));
            }else{
                livingEntity.addEffect(new EffectInstance(Effect.byId(2),10,0,false,false));
            }
        }
    }
    public static void attributeWillpowerLoad(LivingEntity livingEntity,double maxLoad,double load){
        double excess=load-maxLoad;
        if(excess>0){
            if(excess > 4){
                livingEntity.addEffect(new EffectInstance(Effect.byId(15),40,2,false,false));
                if(!livingEntity.hasEffect(Effect.byId(20))){livingEntity.addEffect(new EffectInstance(Effect.byId(20),40,1,false,false));};
            }else{
                livingEntity.addEffect(new EffectInstance(Effect.byId(15),40,0,false,false));
            }
        }
    }
    public static void attributeMentalityLoad(LivingEntity livingEntity,double maxLoad,double load){
        double excess=load-maxLoad;
        if(excess>0){
            if(excess > 4){
                livingEntity.addEffect(new EffectInstance(Effect.byId(15),40,2,false,false));
                if(!livingEntity.hasEffect(Effect.byId(20))){livingEntity.addEffect(new EffectInstance(Effect.byId(20),40,1,false,false));};
            }else{
                livingEntity.addEffect(new EffectInstance(Effect.byId(9),10,0,false,false));
            }
        }
    }
    public static void attributeTechniqueLoad(LivingEntity livingEntity,double maxLoad,double load){
        double excess=load-maxLoad;
        if(excess>0){
            if(excess > 4){
                livingEntity.addEffect(new EffectInstance(Effect.byId(18),10,2,false,false));
                livingEntity.addEffect(new EffectInstance(Effect.byId(2),10,2,false,false));
            }else{
                livingEntity.addEffect(new EffectInstance(Effect.byId(17),10,0,false,false));
            }
        }
    }
    public static double getAttribute(LivingEntity entity,Attribute attribute){
        return entity.getAttribute(attribute).getValue();
    }
    public static void syncAttribute(PlayerEntity player){
        IEnrCapability capability=player.getCapability(EnrCapabilityProvider.TKK_CAPABILITY).orElse(null);
        if(capability==null){return;}
        //先让基础属性同步，然后再附加属性
        capability.sync();
        //力量
        double strength=getAttribute(player,EnrAttribute.STRENGTH.get());
        //体质
        double vitality=getAttribute(player,EnrAttribute.VITALITY.get());
        //敏捷
        double dexterity=getAttribute(player,EnrAttribute.DEXTERITY.get());
        //毅力
        double willpower=getAttribute(player,EnrAttribute.WILLPOWER.get());
        //神力
        double mentality=getAttribute(player,EnrAttribute.MENTALITY.get());
        //技艺
        double technique=getAttribute(player,EnrAttribute.TECHNIQUE.get());

        //重量
        double weight=Math.max(getAttribute(player,EpicFightAttributes.WEIGHT.get())-WIGHT_START,0);


        //setUUIDAttribute(player,)

        //力量
        setUUIDAttribute(player,Attributes.ATTACK_DAMAGE,ATTACK_DAMAGE_UUID1,0.01*strength,AttributeModifier.Operation.MULTIPLY_TOTAL);
        setUUIDAttribute(player,EpicFightAttributes.ARMOR_NEGATION.get(),ARMOR_NEGATION_UUID,0.005*strength,AttributeModifier.Operation.ADDITION);
        //敏捷
        setUUIDAttribute(player, ForgeMod.ENTITY_GRAVITY.get(),ENTITY_GRAVITY_UUID1,-0.0116*dexterity,AttributeModifier.Operation.MULTIPLY_TOTAL);
        setUUIDAttribute(player, Attributes.MOVEMENT_SPEED,MOVEMENT_SPEED_UUID1,0.0035*dexterity,AttributeModifier.Operation.MULTIPLY_TOTAL);
        setUUIDAttribute(player, ForgeMod.NAMETAG_DISTANCE.get(),NAMETAG_DISTANCE_UUID,-0.007*dexterity,AttributeModifier.Operation.MULTIPLY_TOTAL);
        setUUIDAttribute(player, Attributes.ATTACK_DAMAGE,ATTACK_DAMAGE_UUID2,0.05*dexterity,AttributeModifier.Operation.ADDITION);
        //体质
        setUUIDAttribute(player, Attributes.MAX_HEALTH,HEALTH_UUID,0.668*vitality,AttributeModifier.Operation.ADDITION);
        //毅力
        setUUIDAttribute(player, Attributes.ARMOR,ARMOR_UUID,0.35*willpower,AttributeModifier.Operation.ADDITION);
        setUUIDAttribute(player, Attributes.ARMOR_TOUGHNESS,ARMOR_TOUGHNESS_UUID,0.2*willpower,AttributeModifier.Operation.ADDITION);
        setUUIDAttribute(player, Attributes.KNOCKBACK_RESISTANCE,KNOCKBACK_RESISTANCE_UUID,0.5*willpower,AttributeModifier.Operation.ADDITION);
        setUUIDAttribute(player, ForgeMod.ENTITY_GRAVITY.get(),ENTITY_GRAVITY_UUID2,0.02*willpower,AttributeModifier.Operation.MULTIPLY_TOTAL);
        setUUIDAttribute(player, Attributes.MOVEMENT_SPEED,MOVEMENT_SPEED_UUID2,-0.005*willpower,AttributeModifier.Operation.MULTIPLY_TOTAL);
        //技艺（适应）
        setUUIDAttribute(player, EpicFightAttributes.MAX_STAMINA.get(),STAMINA_UUID,technique*0.017 *weight,AttributeModifier.Operation.ADDITION);
        setUUIDAttribute(player, Attributes.ATTACK_SPEED,ATTACK_SPEED_UUID,technique*0.00014 *weight,AttributeModifier.Operation.ADDITION);
        setUUIDAttribute(player, Attributes.MOVEMENT_SPEED,MOVEMENT_SPEED_UUID3,-0.003*technique,AttributeModifier.Operation.MULTIPLY_TOTAL);





    }
    private static void setUUIDAttribute(LivingEntity entity, Attribute attribute, UUID uuid,double d,AttributeModifier.Operation operation){
        if(entity.getAttribute(attribute).getModifier(uuid)!=null && entity.getAttribute(attribute).getModifier(uuid).getAmount()==d){return;}
        entity.getAttribute(attribute).removeModifier(uuid);
        entity.getAttribute(attribute).addTransientModifier(new AttributeModifier(uuid,"enr:extra_attribute",d,operation));
    }
    private static void healer(LivingEntity entity){
        double now=entity.getHealth();
        double max=entity.getAttribute(Attributes.MAX_HEALTH).getValue();
        if(now<max){entity.setHealth((float) max);}

    }
    public static class clientEvent{
        public static void reg(){
            MinecraftForge.EVENT_BUS.register(clientEvent.class);
        }
        @SubscribeEvent
        public static void itemTooltip(ItemTooltipEvent event) {
            if(event.getPlayer()==null){
                return;
            }
            boolean hasStrength=false;





            EquipmentSlotType[] a=new EquipmentSlotType[]{EquipmentSlotType.CHEST,EquipmentSlotType.FEET,EquipmentSlotType.HEAD,EquipmentSlotType.LEGS,EquipmentSlotType.MAINHAND,EquipmentSlotType.OFFHAND};
            for(int i=0;i<a.length;i++){
                Multimap b=event.getItemStack().getAttributeModifiers(a[i]);
                if(b.containsKey(EnrAttribute.STRENGTH_LOAD.get())){hasStrength=true;}
            }
            if(hasStrength){
                event.getToolTip().add(new TranslationTextComponent(I18n.get("tooltip.enr.strength",event.getPlayer().getAttribute(EnrAttribute.STRENGTH_LOAD.get()).getValue(),getAttribute(event.getPlayer(), EnrAttribute.STRENGTH.get())+getAttribute(event.getPlayer(), EnrAttribute.WILLPOWER.get())*0.5)));
            }
            event.getToolTip().add(new TranslationTextComponent(""));

        }


    }

}
