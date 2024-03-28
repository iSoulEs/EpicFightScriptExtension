package reascer.wom.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.client.particle.*;
import reascer.wom.particle.WOMEpicFightParticle;
import yesman.epicfight.main.EpicFightMod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = EpicFightMod.MODID, value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD)
/* loaded from: 旧版[付费的史诗战斗mod]WeaponsOfMinecraft-1.6.jar:reascer/wom/events/ClientModBusEvent.class */
public class ClientModBusEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onParticleRegistry(final ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ParticleManager particleEngine = mc.particleEngine;
        particleEngine.register(WOMEpicFightParticle.ANTITHEUS_HIT.get(), new AntitheusHitParticle.Provider());
        particleEngine.register(WOMEpicFightParticle.ANTITHEUS_CUT.get(), AntitheusCutParticle.Provider::new);
        particleEngine.register(WOMEpicFightParticle.ANTITHEUS_PUNCH_HIT.get(), new AntitheusPunchHitParticle.Provider());
        particleEngine.register(WOMEpicFightParticle.ANTITHEUS_PUNCH.get(), AntitheusPunchParticle.Provider::new);
        particleEngine.register(WOMEpicFightParticle.KATANA_SHEATHED_HIT.get(), new KatanaSheathedHitParticle.Provider());
        particleEngine.register(WOMEpicFightParticle.KATANA_SHEATHED_CUT.get(), KatanaSheathedCutParticle.Provider::new);
    }
}
