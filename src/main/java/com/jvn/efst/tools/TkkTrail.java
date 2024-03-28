package com.jvn.efst.tools;

import yesman.epicfight.api.collider.Collider;

public class TkkTrail{
    public ParticleTrail particleTrail1;
    public boolean ready1;
    public float x1,y1,z1,ex1,ey1,ez1;
    public int r1,g1,b1,a1, lifetime1;
    public int trailType1;
    public boolean isValue1;

    public ParticleTrail particleTrail2;
    public boolean ready2;
    public float x2,y2,z2,ex2,ey2,ez2;
    public int r2,g2,b2,a2, lifetime2;
    public int trailType2;
    public boolean isValue2;

    public boolean isOne;
    public Collider WeaponCollider=null;

    public TkkTrail(){
        isOne=true;

        this.x1 = 0;
        this.y1 = 0;
        this.z1 = 0;
        this.ex1 = 0;
        this.ey1 = 0;
        this.ez1 = 0;
        this.r1 = 0;
        this.g1 = 0;
        this.b1 = 0;
        this.a1 = 0;
        this.trailType1=0;
        this.lifetime1 = 0;
        this.isValue1=false;
        this.ready1=false;
        this.particleTrail1=new ParticleTrail();

        this.x2 = 0;
        this.y2 = 0;
        this.z2 = 0;
        this.ex2 = 0;
        this.ey2 = 0;
        this.ez2 = 0;
        this.r2 = 0;
        this.g2 = 0;
        this.b2 = 0;
        this.a2 = 0;
        this.trailType2=0;
        this.lifetime2 = 0;
        this.isValue2=false;
        this.ready2=false;
        this.particleTrail2=new ParticleTrail();
    }
    //在渲染刀光前调用
    public void update(){
        if(isOne){
            ready1=false;
            this.particleTrail1=new ParticleTrail();
        }else {
            ready2=false;
            this.particleTrail2=new ParticleTrail();
        }
        isOne=!isOne;
    }

    public final Trail getTrail(){
        return new Trail(getX(),getY(),getZ(),getEX(),getEY(),getEZ(),getR(),getG(),getB(),getA(),getLifetime());
    }

    public final void setWeaponCollider(Collider collider){
        this.WeaponCollider=collider;
    }

    public final boolean isReady(){return isOne?ready1:ready2;}
    public final boolean isValue(){return isOne?isValue1:isValue2;}

    public final float getX(){
        float temp= isOne?x1:x2;
        if(!isValue() && WeaponCollider!=null){temp*=WeaponCollider.outerAABB.minX;}
        return temp;
    }
    public final float getY(){
        float temp= isOne?y1:y2;
        if(!isValue() && WeaponCollider!=null){temp*=WeaponCollider.outerAABB.minY;}
        return temp;
    }
    public final float getZ(){
        float temp= isOne?z1:z2;
        if(!isValue() && WeaponCollider!=null){temp*=WeaponCollider.outerAABB.minZ;}
        return temp;
    }
    public final float getEX(){
        float temp= isOne?ex1:ex2;
        if(!isValue() && WeaponCollider!=null){temp*=WeaponCollider.outerAABB.minX;}
        return temp;
    }
    public final float getEY(){
        float temp= isOne?ey1:ey2;
        if(!isValue() && WeaponCollider!=null){temp*=WeaponCollider.outerAABB.minY;}
        return temp;
    }
    public final float getEZ(){
        float temp= isOne?ez1:ez2;
        if(!isValue() && WeaponCollider!=null){temp*=WeaponCollider.outerAABB.minZ;}
        return temp;
    }

    public final int getR(){return isOne?r1:r2;}
    public final int getG(){return isOne?g1:g2;}
    public final int getB(){return isOne?b1:b2;}
    public final int getA(){return isOne?a1:a2;}
    public final int getLifetime(){return isOne?lifetime1:lifetime2;}
    public final int getTrailType(){return isOne?trailType1:trailType2;}
    public final ParticleTrail getParticleTrail(){
        if(isOne){
            particleTrail1.WeaponCollider=this.WeaponCollider;
            return particleTrail1;
        }else{
            particleTrail2.WeaponCollider=this.WeaponCollider;
            return particleTrail2;
        }
    }

    public final void addParticleTrail(ParticleTrail.trail trail){
        if(!isOne){
            particleTrail1.getList().add(trail);
        }else{
            particleTrail2.getList().add(trail);
        }
    }
    public final void setReady(boolean b){
        if(!isOne){
            ready1=b;
        }else {
            ready2=b;
        }
    }
    public final void setIsValue(boolean b){
        if(!isOne){
            isValue1=b;
        }else {
            isValue2=b;
        }
    }

    public final void setX(float value){
        if(!isOne){
            x1=value;
        }else {
            x2=value;
        }
    }
    public final void setY(float value){
        if(!isOne){
            y1=value;
        }else {
            y2=value;
        }
    }
    public final void setZ(float value){
        if(!isOne){
            z1=value;
        }else {
            z2=value;
        }
    }
    public final void setEX(float value){
        if(!isOne){
            ex1=value;
        }else {
            ex2=value;
        }
    }
    public final void setEY(float value){
        if(!isOne){
            ey1=value;
        }else {
            ey2=value;
        }
    }
    public final void setEZ(float value){
        if(!isOne){
            ez1=value;
        }else {
            ez2=value;
        }
    }

    public final void setR(int value){
        if(!isOne){
            r1=value;
        }else {
            r2=value;
        }
    }
    public final void setG(int value){
        if(!isOne){
            g1=value;
        }else {
            g2=value;
        }
    }
    public final void setB(int value){
        if(!isOne){
            b1=value;
        }else {
            b2=value;
        }
    }
    public final void setA(int value){
        if(!isOne){
            a1=value;
        }else {
            a2=value;
        }
    }
    public final void setLifetime(int value){
        if(!isOne){
            lifetime1=value;
        }else {
            lifetime2=value;
        }
    }
    public final void setTrailType(int value){
        if(!isOne){
            trailType1=value;
        }else {
            trailType2=value;
        }
    }

}
