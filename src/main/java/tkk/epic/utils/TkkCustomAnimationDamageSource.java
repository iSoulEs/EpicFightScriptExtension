package tkk.epic.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.vector.Vector3d;
import yesman.epicfight.api.utils.EpicFightDamageSource;
import yesman.epicfight.api.utils.ExtendedDamageSource;
import yesman.epicfight.particle.HitParticleType;

import java.util.HashMap;

public class TkkCustomAnimationDamageSource {

    public final DamageSourceCreate a;
    public boolean aReady=false;
    public final DamageSourceCreate b;
    public boolean bReady=false;
    public boolean isA;


    public TkkCustomAnimationDamageSource(){
        a=new DamageSourceCreate();
        b=new DamageSourceCreate();
        isA=true;
    }
    public DamageSourceCreate getDSC(){
        if(isA){
            return (aReady)?a:null;
        }else{
            return (bReady)?b:null;
        }
    }
    public DamageSourceCreate getReady(){
        if(isA){
            bReady=true;
            return b;
        }else{
            aReady=true;
            return a;
        }
    }

    public ExtendedDamageSource getDamageSource(ExtendedDamageSource ds){
        if(isA){
            if(aReady){return a.createForDamageSource(ds);}
        }else{
            if(bReady){return b.createForDamageSource(ds);}
        }
        return ds;
    }
    public float getDamage(float f){
        if(isA){
            if(aReady){return a.getDamage(f);}
        }else{
            if(bReady){return b.getDamage(f);}
        }
        return f;
    }
    public HitParticleType getHitParticleType(HitParticleType source){
        DamageSourceCreate temp=getDSC();
        if(temp==null){return source;}
        if(!temp.doHitParticle){return null;}
        if(temp.hitParticleType==null){return source;}
        return temp.hitParticleType;
    }
    public SoundEvent getHitSound(SoundEvent source){
        DamageSourceCreate temp=getDSC();
        if(temp==null){return source;}
        if(!temp.doHitSound){return null;}
        if(temp.hitSound==null){return source;}
        return temp.hitSound;
    }
    public void update(){
        if(isA){
            aReady=false;
            a.clear();
        }else {
            bReady=false;
            b.clear();
        }
        isA = !isA;
    }











    public static class DamageSourceCreate{
        public String damageTypeIn;
        public Entity damageSourceEntityIn;
        public Float impact;
        public float impactAdd=0.0f;
        public float impactScale=1.0f;
        public Float armorNegation;
        public float armorNegationAdd=0.0f;
        public float armorNegationScale=1.0f;
        public Boolean finisher;
        public ExtendedDamageSource.StunType stunType;
        public Boolean isBasicAttack;
        public Integer animationId;
        public Vector3d initialPosition;
        public HashMap tempdata=new HashMap();
        public boolean canDodge=true;

        public Float damage;
        public float damageAdd=0.0f;
        public float damageScale=1.0f;

        //如果false，则不渲染，否则渲染hitParticleType,如果hitParticleType==null 渲染动作默认的
        public boolean doHitParticle=true;
        public HitParticleType hitParticleType=null;

        public boolean doHitSound=true;
        public SoundEvent hitSound=null;

        public DamageSourceCreate(){

        }
        public void clear(){
            damageTypeIn=null;
            damageSourceEntityIn=null;
            impact=null;
            armorNegation=null;
            finisher=null;
            stunType=null;
            isBasicAttack=null;
            animationId=null;
            initialPosition=null;
            impactScale=1.0f;
            impactAdd=0.0f;
            armorNegationScale=1.0f;
            armorNegationAdd=0.0f;
            tempdata=new HashMap();
            damage=null;
            damageAdd=0.0f;
            damageScale=1.0f;

            doHitParticle=true;
            hitParticleType=null;

            doHitSound=true;
            hitSound=null;

            canDodge=true;


        }
        public TkkEpicCustomDamageSource createForDamageSource(ExtendedDamageSource ds){
            String dt=(damageTypeIn==null)?ds.getType():damageTypeIn;
            Entity dsei=(damageSourceEntityIn==null)?ds.getOwner():damageSourceEntityIn;
            float imp=(impact==null)?ds.getImpact():impact;
            float an=(armorNegation==null)?ds.getArmorNegation():armorNegation;
            boolean fin=(finisher==null)?ds.isFinisher():finisher;
            ExtendedDamageSource.StunType st=(stunType==null)?ds.getStunType():stunType;
            boolean iba=(isBasicAttack==null)?ds.isBasicAttack():isBasicAttack;
            int aid=(animationId==null)?ds.getAnimationId():animationId;
            Vector3d ip=initialPosition;
            if(ds instanceof EpicFightDamageSource){
                if(ip==null){ip=((EpicFightDamageSource) ds).getSourcePosition();}
            }
            imp*=impactScale;
            imp+=impactAdd;
            an*=armorNegationScale;
            an+=armorNegationAdd;
            return new TkkEpicCustomDamageSource(dt,dsei,imp,an,fin,st,iba,canDodge,aid,ip,tempdata);
        }
        public float getDamage(float damage){
            float dam=damage;
            if(this.damage!=null){dam=this.damage;}
            dam*=damageScale;
            dam+=damageAdd;
            return dam;
        }


    }
}
