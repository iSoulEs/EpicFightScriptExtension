package tkk.epicrelic.capability;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import tkk.epicrelic.Enr;

public class EnrCapabilityEventLoader {

    public static void reg(){
        CapabilityManager.INSTANCE.register(IEnrCapability.class, new Capability.IStorage<IEnrCapability>() {
            @Override
            public INBT writeNBT(Capability<IEnrCapability> capability, IEnrCapability instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IEnrCapability> capability, IEnrCapability instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, () -> null);
        MinecraftForge.EVENT_BUS.register(EnrCapabilityEventLoader.class);
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if(!(event.getObject() instanceof ServerPlayerEntity)){return;}
        event.addCapability(new ResourceLocation(Enr.MODID, "enr_data"), new EnrCapabilityProvider((LivingEntity) event.getObject()));

    }
    @SubscribeEvent
    public static void cloneEvent(PlayerEvent.Clone event) {
        //event.getOriginal().reviveCaps();
        IEnrCapability oldCap = (IEnrCapability)event.getOriginal().getCapability(EnrCapabilityProvider.TKK_CAPABILITY).orElse(null);

        if (oldCap != null) {
            IEnrCapability newCap = (IEnrCapability)event.getPlayer().getCapability(EnrCapabilityProvider.TKK_CAPABILITY).orElse(null);
            newCap.deserializeNBT(oldCap.serializeNBT());
        }
        event.getPlayer().getCapability(EnrCapabilityProvider.TKK_CAPABILITY).orElse(null).sync();
        //event.getOriginal().invalidateCaps();
    }
    //仅逻辑端,不需要同步
    /*
    @SubscribeEvent
    public static void onStartTracking(PlayerEvent.StartTracking event) {
        if(event.getTarget().getCapability(EnrCapabilityProvider.TKK_CAPABILITY,null).orElse(null)!=null){
            TkkEpic.LOGGER.log(Level.ERROR,"2kk2 onStartTracking同步");
        }
    }
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if(event.getPlayer().getCapability(EnrCapabilityProvider.TKK_CAPABILITY,null).orElse(null)!=null){
            TkkEpic.LOGGER.log(Level.ERROR,"2kk2 onPlayerLoggedIn同步");

        }
    }

     */
}
