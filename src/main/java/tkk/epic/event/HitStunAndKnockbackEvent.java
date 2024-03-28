package tkk.epic.event;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

public class HitStunAndKnockbackEvent extends Event {
    public final LivingEntity hitEntity;
    public final LivingEntityPatch<?> hitentitypatch;
    public StaticAnimation hitAnimation;
    public float extendStunTime;
    public float knockBackAmount;
    public final ExtendedDamageSource extendedDamageSource;
    public final LivingHurtEvent sourceEvent;
    public final ExtendedDamageSource.StunType stunType;
    public HitStunAndKnockbackEvent(LivingHurtEvent event, LivingEntity hitEntity, LivingEntityPatch<?> hitentitypatch, StaticAnimation hitAnimation, float extendStunTime, float knockBackAmount, ExtendedDamageSource extendedDamageSource, ExtendedDamageSource.StunType stunType) {
        this.hitEntity = hitEntity;
        this.hitentitypatch = hitentitypatch;
        this.hitAnimation = hitAnimation;
        this.extendStunTime = extendStunTime;
        this.knockBackAmount = knockBackAmount;
        this.extendedDamageSource = extendedDamageSource;
        this.sourceEvent=event;
        this.stunType=stunType;
    }
}
