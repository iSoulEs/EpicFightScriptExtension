package tkk.epic.network;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import noppes.npcs.entity.EntityCustomNpc;
import tkk.epic.patch.TkkCustomNpcPatch;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.ClientAnimator;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class SPTkkNpcUpdata {
    private int entityId;
    private String modelPath;
    private String modelNamespace;
    private float scaleX;
    private float scaleY;
    private float scaleZ;
    private int animSize;
    private String[] key;
    private int[][] animator;
    private int defaultAnimSize;
    private String[] defaultKey;
    private int[][] defaultAnimator;

    public SPTkkNpcUpdata() {
    }

    public SPTkkNpcUpdata(EntityCustomNpc npc) {
        this.entityId=npc.getId();
        TkkCustomNpcPatch patch = (TkkCustomNpcPatch) npc.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
        this.modelNamespace=patch.model.getNamespace();
        this.modelPath=patch.model.getPath();
        this.scaleX = patch.scaleX;
        this.scaleY = patch.scaleY;
        this.scaleZ = patch.scaleZ;
        this.animSize=patch.animationIdList.size();
        key=new String[this.animSize];
        animator=new int[this.animSize][];
        Set<Map.Entry<String,int[]>> set=patch.animationIdList.entrySet();
        int i=0;
        for (Map.Entry<String,int[]> entry : set) {
            key[i]=entry.getKey();
            animator[i]=entry.getValue();
            i+=1;
        }
        this.defaultAnimSize=patch.defaultAnimationList.size();
        defaultKey=new String[this.animSize];
        defaultAnimator=new int[this.animSize][];
        Set<Map.Entry<LivingMotions, StaticAnimation>> setB=patch.defaultAnimationList.entrySet();
        int j=0;
        for (Map.Entry<LivingMotions,StaticAnimation> entry : setB) {
            defaultKey[j]=entry.getKey().name();
            defaultAnimator[j]=new int[]{entry.getValue().getNamespaceId(),entry.getValue().getId()};
            j+=1;
        }


    }

    public static SPTkkNpcUpdata fromBytes(PacketBuffer buf) {
        SPTkkNpcUpdata msg = new SPTkkNpcUpdata();
        msg.entityId=buf.readInt();
        msg.modelNamespace=buf.readUtf();
        msg.modelPath=buf.readUtf();
        msg.animSize=buf.readInt();
        msg.defaultAnimSize=buf.readInt();
        msg.scaleX=buf.readFloat();
        msg.scaleY=buf.readFloat();
        msg.scaleZ=buf.readFloat();
        msg.key=new String[msg.animSize];
        msg.animator=new int[msg.animSize][];
        for(int i=0;i<msg.animSize;i++){
            msg.key[i]=buf.readUtf();
            msg.animator[i]=new int[]{buf.readInt(),buf.readInt()};
        }
        msg.defaultKey=new String[msg.defaultAnimSize];
        msg.defaultAnimator=new int[msg.defaultAnimSize][];
        for(int i=0;i<msg.defaultAnimSize;i++){
            msg.defaultKey[i]=buf.readUtf();
            msg.defaultAnimator[i]=new int[]{buf.readInt(),buf.readInt()};
        }
        return msg;
    }

    public static void toBytes(SPTkkNpcUpdata msg, PacketBuffer buf) {
        buf.writeInt(msg.entityId);
        buf.writeUtf(msg.modelNamespace);
        buf.writeUtf(msg.modelPath);
        buf.writeInt(msg.animSize);
        buf.writeInt(msg.defaultAnimSize);
        buf.writeFloat(msg.scaleX);
        buf.writeFloat(msg.scaleY);
        buf.writeFloat(msg.scaleZ);
        for(int i=0;i<msg.animSize;i++){
            buf.writeUtf(msg.key[i]);
            buf.writeInt(msg.animator[i][0]);
            buf.writeInt(msg.animator[i][1]);
        }
        for(int i=0;i<msg.defaultAnimSize;i++){
            buf.writeUtf(msg.defaultKey[i]);
            buf.writeInt(msg.defaultAnimator[i][0]);
            buf.writeInt(msg.defaultAnimator[i][1]);
        }
    }

    public static void handle(SPTkkNpcUpdata msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.player.level.getEntity(msg.entityId);
            TkkCustomNpcPatch patch = (TkkCustomNpcPatch) entity.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY, null).orElse(null);
            patch.model= new ResourceLocation(msg.modelNamespace,msg.modelPath);
            patch.scaleX = msg.scaleX;
            patch.scaleY = msg.scaleY;
            patch.scaleZ = msg.scaleZ;
            patch.animationIdList.clear();
            int i=0;
            while(i<msg.animSize){
                patch.animationIdList.put(msg.key[i],new int[]{msg.animator[i][0],msg.animator[i][1]});
                i++;
            }
            patch.syncAnimator();
            for (int y=0;i<msg.defaultAnimSize;i++) {
                patch.defaultAnimationList.put(LivingMotions.valueOf(msg.defaultKey[y]), EpicFightMod.getInstance().animationManager.findAnimationById(msg.defaultAnimator[y][0],msg.defaultAnimator[y][1]));
            }
            ClientAnimator animator = patch.getClientAnimator();
            animator.resetMotions();
            animator.resetCompositeMotion();

            HashMap<LivingMotions, StaticAnimation> dal=(HashMap<LivingMotions, StaticAnimation>)patch.defaultAnimationList;
            for (Map.Entry<LivingMotions, StaticAnimation> entry : dal.entrySet()) {
                animator.addLivingAnimation(entry.getKey(), entry.getValue());
            }
            animator.setCurrentMotionsAsDefault();

            patch.initAnimator(animator);
        });
        ctx.get().setPacketHandled(true);
    }
}
