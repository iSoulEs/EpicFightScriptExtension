package tkk.epic.capability;

import net.minecraft.entity.Entity;
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
import tkk.epic.TkkEpic;

public class CapabilityEventLoader {

    public static void reg(){
        CapabilityManager.INSTANCE.register(ITkkCapability.class, new Capability.IStorage<ITkkCapability>() {
            @Override
            public INBT writeNBT(Capability<ITkkCapability> capability, ITkkCapability instance, Direction side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<ITkkCapability> capability, ITkkCapability instance, Direction side, INBT nbt) {
                instance.deserializeNBT((CompoundNBT) nbt);
            }
        }, () -> null);
        MinecraftForge.EVENT_BUS.register(CapabilityEventLoader.class);
    }

    @SubscribeEvent
    public static void attachEntityCapability(AttachCapabilitiesEvent<Entity> event) {
        if(!(event.getObject() instanceof ServerPlayerEntity)){return;}
        event.addCapability(new ResourceLocation(TkkEpic.MODID, "tkk_data"), new TkkCapabilityProvider((ServerPlayerEntity) event.getObject()));

    }
    @SubscribeEvent
    public static void cloneEvent(PlayerEvent.Clone event) {
        //event.getOriginal().reviveCaps();
        ITkkCapability oldCap = (ITkkCapability)event.getOriginal().getCapability(TkkCapabilityProvider.TKK_CAPABILITY).orElse(null);

        if (oldCap != null) {
            ITkkCapability newCap = (ITkkCapability)event.getPlayer().getCapability(TkkCapabilityProvider.TKK_CAPABILITY).orElse(null);
            newCap.deserializeNBT(oldCap.serializeNBT());
        }

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
