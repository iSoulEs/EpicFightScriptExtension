package tkk.epic.network;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.Level;
import tkk.epic.TkkEpic;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SPSpawnParticle {

    public ArrayList<Particle> particles=new ArrayList<>();
    public SPSpawnParticle() {

    }
    public SPSpawnParticle(PacketBuffer buf) {
        int size=buf.readInt();
        for(int i=0;i<size;i++){
            particles.add(fromBytesParticle(buf));
        }
    }
    public void addParticle(String id, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed){
        this.particles.add(new Particle(id,x,y,z,xSpeed,ySpeed,zSpeed));
    }
    public static SPSpawnParticle fromBytes(PacketBuffer buf) {
        SPSpawnParticle msg = new SPSpawnParticle(buf);
        return msg;
    }

    public static void toBytes(SPSpawnParticle msg, PacketBuffer buf) {
        buf.writeInt(msg.particles.size());
        for(Particle p:msg.particles){
            toBytesParticle(p,buf);
        }
    }

    public static void handle(SPSpawnParticle msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            World world = mc.player.level;
            for(Particle p:msg.particles){
                IParticleData ipd=p.getIParticleData();
                TkkEpic.LOGGER.log(Level.ERROR,"tkk spawnHandle "+ipd);
                if(ipd==null){continue;}
                world.addParticle(ipd,p.x,p.y,p.z,p.xSpeed,p.ySpeed,p.zSpeed);

            }

        });

        ctx.get().setPacketHandled(true);
    }
    public static Particle fromBytesParticle(PacketBuffer buf) {
        Particle msg = new Particle(buf.readUtf(),buf.readDouble(),buf.readDouble(),buf.readDouble(),buf.readDouble(),buf.readDouble(),buf.readDouble());
        return msg;
    }

    public static void toBytesParticle(Particle msg, PacketBuffer buf) {
        buf.writeUtf(msg.id);
        buf.writeDouble(msg.x);
        buf.writeDouble(msg.y);
        buf.writeDouble(msg.z);
        buf.writeDouble(msg.xSpeed);
        buf.writeDouble(msg.ySpeed);
        buf.writeDouble(msg.zSpeed);
    }

    private static class Particle{
        public final String id;
        public final double x;
        public final double y;
        public final double z;
        public final double xSpeed;
        public final double ySpeed;
        public final double zSpeed;

        private Particle(String id, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.z = z;
            this.xSpeed = xSpeed;
            this.ySpeed = ySpeed;
            this.zSpeed = zSpeed;
        }
        public final IParticleData getIParticleData() {
            ParticleType a= ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(id));
            if (a==null){
                return null;
            }
            try {
                IParticleData ipd=a.getDeserializer().fromCommand(a,new StringReader(""));
                return ipd;
            } catch (CommandSyntaxException e) {
                return null;
            }
        }

    }
}
