package tkk.epic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import yesman.epicfight.api.animation.Animator;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.EntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;

import java.util.function.Supplier;

public class SPTkkAttackSpeedModifiers {
    private int entityId;
    public Float preDelaySet;
    public Float preDelayAdd;
    public Float preDelayScale;
    public Float contactSet;
    public Float contactAdd;
    public Float contactScale;
    public Float recoverySet;
    public Float recoveryAdd;
    public Float recoveryScale;

    public SPTkkAttackSpeedModifiers(){}
    public SPTkkAttackSpeedModifiers(LivingEntityPatch patch){
        Animator animator=patch.getAnimator();
        this.entityId=patch.getOriginal().getId();
        this.preDelaySet=animator.preDelay_attackSpeedModifiers_set;
        this.preDelayAdd=animator.preDelay_attackSpeedModifiers_add;
        this.preDelayScale=animator.preDelay_attackSpeedModifiers_scale;
        this.contactSet=animator.contact_attackSpeedModifiers_set;
        this.contactAdd=animator.contact_attackSpeedModifiers_add;
        this.contactScale=animator.contact_attackSpeedModifiers_scale;
        this.recoverySet=animator.recovery_attackSpeedModifiers_set;
        this.recoveryAdd=animator.recovery_attackSpeedModifiers_add;
        this.recoveryScale=animator.recovery_attackSpeedModifiers_scale;
    }

    public static SPTkkAttackSpeedModifiers fromBytes(PacketBuffer buf) {
        SPTkkAttackSpeedModifiers msg = new SPTkkAttackSpeedModifiers();
        msg.entityId=buf.readInt();
        if(buf.readBoolean()){msg.preDelaySet=buf.readFloat();}
        if(buf.readBoolean()){msg.preDelayAdd=buf.readFloat();}
        if(buf.readBoolean()){msg.preDelayScale=buf.readFloat();}
        if(buf.readBoolean()){msg.contactSet=buf.readFloat();}
        if(buf.readBoolean()){msg.contactAdd=buf.readFloat();}
        if(buf.readBoolean()){msg.contactScale=buf.readFloat();}
        if(buf.readBoolean()){msg.recoverySet=buf.readFloat();}
        if(buf.readBoolean()){msg.recoveryAdd=buf.readFloat();}
        if(buf.readBoolean()){msg.recoveryScale=buf.readFloat();}
        return msg;
    }

    public static void toBytes(SPTkkAttackSpeedModifiers msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);

        buf.writeBoolean(msg.preDelaySet!=null);
        if(msg.preDelaySet!=null){buf.writeFloat(msg.preDelaySet);}
        buf.writeBoolean(msg.preDelayAdd!=null);
        if(msg.preDelayAdd!=null){buf.writeFloat(msg.preDelayAdd);}
        buf.writeBoolean(msg.preDelayScale!=null);
        if(msg.preDelayScale!=null){buf.writeFloat(msg.preDelayScale);}

        buf.writeBoolean(msg.contactSet!=null);
        if(msg.contactSet!=null){buf.writeFloat(msg.contactSet);}
        buf.writeBoolean(msg.contactAdd!=null);
        if(msg.contactAdd!=null){buf.writeFloat(msg.contactAdd);}
        buf.writeBoolean(msg.contactScale!=null);
        if(msg.contactScale!=null){buf.writeFloat(msg.contactScale);}

        buf.writeBoolean(msg.recoverySet!=null);
        if(msg.recoverySet!=null){buf.writeFloat(msg.recoverySet);}
        buf.writeBoolean(msg.recoveryAdd!=null);
        if(msg.recoveryAdd!=null){buf.writeFloat(msg.recoveryAdd);}
        buf.writeBoolean(msg.recoveryScale!=null);
        if(msg.recoveryScale!=null){buf.writeFloat(msg.recoveryScale);}
    }

    public static void handle(SPTkkAttackSpeedModifiers msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.player.level.getEntity(msg.entityId);

            if (entity != null) {
                EntityPatch<?> patch = entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
                if(patch instanceof LivingEntityPatch){
                    Animator animator=((LivingEntityPatch) patch).getAnimator();
                    animator.attackSpeedModifiers=true;
                    animator.preDelay_attackSpeedModifiers_set=msg.preDelaySet;
                    animator.preDelay_attackSpeedModifiers_add=msg.preDelayAdd;
                    animator.preDelay_attackSpeedModifiers_scale=msg.preDelayScale;
                    animator.contact_attackSpeedModifiers_set=msg.contactSet;
                    animator.contact_attackSpeedModifiers_add=msg.contactAdd;
                    animator.contact_attackSpeedModifiers_scale=msg.contactScale;
                    animator.recovery_attackSpeedModifiers_set=msg.recoverySet;
                    animator.recovery_attackSpeedModifiers_add=msg.recoveryAdd;
                    animator.recovery_attackSpeedModifiers_scale=msg.recoveryScale;

                }
            }
        });

        ctx.get().setPacketHandled(true);
    }

}
