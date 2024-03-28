package tkk.epic.event;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.HurtEvent;

public class GuardIsBlockableEvent  extends Event{
    public final SkillContainer container;
    public final CapabilityItem itemCapability;

    public final DamageSource damageSource;
    public final float amount;
    public final LivingEntity entity;





    public float knockback;
    public float impact;
    public boolean advanced;

    public float consumeAdd=0.0f;
    public float consumeScale=1.0f;
    public float consumeAddScale=1.0f;

    public boolean canBlock;

    public final boolean isActiveGuard;

    public GuardIsBlockableEvent(SkillContainer container, CapabilityItem itemCapability, HurtEvent.Pre event, float knockback, float impact, boolean advanced,boolean canBlock,boolean isActiveGuard){
        this.canBlock=canBlock;
        this.container = container;
        this.itemCapability = itemCapability;
        this.knockback = knockback;
        this.impact = impact;
        this.advanced = advanced;
        this.isActiveGuard=isActiveGuard;
        this.damageSource=event.getDamageSource();
        this.amount=event.getAmount();
        this.entity=event.getPlayerPatch().getOriginal();

    }
    public GuardIsBlockableEvent(SkillContainer container, CapabilityItem itemCapability,DamageSource damageSource,float amount,LivingEntity entity, float knockback, float impact, boolean advanced,boolean canBlock,boolean isActiveGuard){
        this.canBlock=canBlock;
        this.container = container;
        this.itemCapability = itemCapability;
        this.knockback = knockback;
        this.impact = impact;
        this.advanced = advanced;
        this.isActiveGuard=isActiveGuard;
        this.damageSource=damageSource;
        this.amount=amount;
        this.entity=entity;

    }
    public float getConsume(float source){
        return source*consumeScale + consumeAdd*consumeAddScale;
    }

}
