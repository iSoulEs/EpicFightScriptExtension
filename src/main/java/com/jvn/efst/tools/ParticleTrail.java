package com.jvn.efst.tools;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import yesman.epicfight.api.collider.Collider;

import java.util.ArrayList;
import java.util.List;

public class ParticleTrail {
    public final List<trail> list;
    public Collider WeaponCollider=null;
    public ParticleTrail(){
        list=new ArrayList<>();
    }
    public final void clear(){
        list.clear();
    }
    public final void add(trail obj){
        list.add(obj);
    }
    public final List<trail> getList(){
        return list;
    }




    public static class trail{
        public final float x,y,z,ex,ey,ez;
        public final boolean isValue;

        public final ResourceLocation particle;
        public final String args;
        public final float xSpaceBetween,ySpaceBetween,zSpaceBetween;
        public final float xSpeed,ySpeed,zSpeed;
        public final double xDist,yDist,zDist;
        public final int count;

        public IParticleData iParticleData;

        public trail(float x, float y, float z, float ex, float ey, float ez, boolean isValue, ResourceLocation particle, String args, float xSpaceBetween, float ySpaceBetween, float zSpaceBetween, float xSpeed, float ySpeed, float zSpeed, double xDist, double yDist, double zDist, int count) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.ex = ex;
            this.ey = ey;
            this.ez = ez;
            this.isValue = isValue;

            this.particle = particle;
            this.args = args;
            this.xSpaceBetween = xSpaceBetween;
            this.ySpaceBetween = ySpaceBetween;
            this.zSpaceBetween = zSpaceBetween;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
            this.zSpeed = zSpeed;
            this.xDist = xDist;
            this.yDist = yDist;
            this.zDist = zDist;
            this.count = count;
        }

        public final Vector3d getStart(Collider WeaponCollider){
            if(!isValue && WeaponCollider!=null){
                return new Vector3d(this.x*WeaponCollider.outerAABB.minX,this.y*WeaponCollider.outerAABB.minY,this.z*WeaponCollider.outerAABB.minZ);
            }else{
                return new Vector3d(this.x,this.y,this.z);
            }
        }
        public final Vector3d getEnd(Collider WeaponCollider){
            if(!isValue && WeaponCollider!=null){
                return new Vector3d(this.ex*WeaponCollider.outerAABB.minX,this.ey*WeaponCollider.outerAABB.minY,this.ez*WeaponCollider.outerAABB.minZ);
            }else{
                return new Vector3d(this.ex,this.ey,this.ez);
            }
        }

        public final IParticleData getIParticleData() throws Exception {
            if(iParticleData!=null){return iParticleData;}
            ParticleType a= Registry.PARTICLE_TYPE.getOptional(particle).orElse(null);
            if (a==null){
                throw new Exception("§ccom.jvn.efst.tools.ParticleTrail.trail.getIParticleData Error:§f unknown particle "+particle);
            }
            try {
                IParticleData ipd=a.getDeserializer().fromCommand(a,new StringReader(args));
                iParticleData=ipd;
                return ipd;
            } catch (CommandSyntaxException e) {
                throw new Exception("§ccom.jvn.efst.tools.ParticleTrail.trail.getIParticleData Error:§f "+particle+" arg:"+args+" exception:"+e);
            }
        }



    }


}
