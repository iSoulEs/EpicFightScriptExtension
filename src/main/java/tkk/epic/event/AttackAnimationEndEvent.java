package tkk.epic.event;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.List;


public class AttackAnimationEndEvent extends Event{

    private final List<LivingEntity> attackedEntity;
    private final int animationId;
    private final LivingEntityPatch patch;
    private final LivingEntity entity;

    public AttackAnimationEndEvent(LivingEntityPatch patch, List<LivingEntity> attackedEntity, int animationId){
        this.animationId=animationId;
        this.attackedEntity=attackedEntity;
        this.patch=patch;
        this.entity=(LivingEntity) patch.getOriginal();
    }



    public List<LivingEntity> getHitEntity() {
        return this.attackedEntity;
    }

    public int getAnimationId() {
        return this.animationId;
    }


    public LivingEntity getEntity() {
        return entity;
    }

    public LivingEntityPatch getPatch() {
        return patch;
    }
}
