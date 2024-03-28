package tkk.epic.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.vector.Vector3d;
import yesman.epicfight.api.utils.ExtendedDamageSource;

import java.util.HashMap;

public class TkkEpicCustomDamageSource extends EntityDamageSource implements ExtendedDamageSource {
    public float impact;
    public float armorNegation;
    public boolean finisher;
    public boolean canDodge;
    public StunType stunType;
    public boolean isBasicAttack;
    public int animationId;
    public Vector3d initialPosition;
    public HashMap tempdata;
    public TkkEpicCustomDamageSource(String damageTypeIn, Entity damageSourceEntityIn,float impact,float armorNegation,boolean finisher,StunType stunType,boolean isBasicAttack,boolean canDodge,int animationId,Vector3d initialPosition,HashMap hashMap){
        super(damageTypeIn,damageSourceEntityIn);
        this.impact=impact;
        this.armorNegation=armorNegation;
        this.finisher=finisher;
        this.stunType=stunType;
        this.isBasicAttack=isBasicAttack;
        this.animationId=animationId;
        this.initialPosition=initialPosition;
        this.tempdata=hashMap;
        this.canDodge=canDodge;
    }

    public void setCanDodge(boolean b){canDodge=b;}

    public boolean isCanDodge(){return canDodge;}

    @Override
    public void setImpact(float amount) {
        this.impact = amount;
    }

    @Override
    public void setArmorNegation(float amount) {
        this.armorNegation = amount;
    }

    @Override
    public void setStunType(StunType stunType) {
        this.stunType = stunType;
    }

    @Override
    public void setFinisher(boolean flag) {
        this.finisher = flag;
    }

    @Override
    public void setInitialPosition(Vector3d initialPosition) {
        this.initialPosition = initialPosition;
    }

    @Override
    public float getImpact() {
        return this.impact;
    }

    @Override
    public float getArmorNegation() {
        return this.armorNegation;
    }

    @Override
    public StunType getStunType() {
        return this.stunType;
    }

    @Override
    public Entity getOwner() {
        return super.getEntity();
    }

    @Override
    public String getType() {
        return super.msgId;
    }

    @Override
    public boolean isBasicAttack() {
        return isBasicAttack;
    }

    @Override
    public boolean isFinisher() {
        return this.finisher;
    }

    @Override
    public int getAnimationId() {
        return this.animationId;
    }

    @Override
    public Vector3d getSourcePosition() {
        return this.initialPosition != null ? this.initialPosition : super.getSourcePosition();
    }
}
