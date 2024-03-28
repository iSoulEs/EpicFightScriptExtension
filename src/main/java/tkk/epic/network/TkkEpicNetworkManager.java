package tkk.epic.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import tkk.epic.TkkEpic;

public class TkkEpicNetworkManager {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(TkkEpic.MODID, "network_manager"), () -> "1", "1"::equals, "1"::equals);

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToClient(MSG message, PacketDistributor.PacketTarget packetTarget) {
        INSTANCE.send(packetTarget, message);
    }
    public static <MSG> void sendNearby(World level, BlockPos pos, int range, MSG msg) {
        INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(pos.getX(), pos.getY(), pos.getZ(), range, level.dimension())), msg);
    }
    public static <MSG> void sendToAll(MSG message) {
        sendToClient(message, PacketDistributor.ALL.noArg());
    }

    public static <MSG> void sendToAllPlayerTrackingThisEntity(MSG message, Entity entity) {
        sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayerEntity player) {
        sendToClient(message, PacketDistributor.PLAYER.with(() -> player));
    }

    public static <MSG> void sendToAllPlayerTrackingThisEntityWithSelf(MSG message, ServerPlayerEntity entity) {
        sendToPlayer(message, entity);
        sendToClient(message, PacketDistributor.TRACKING_ENTITY.with(() -> entity));
    }

    public static void registerPackets() {
        int id = 0;
        INSTANCE.registerMessage(id++, CPTkkSpellPress.class, CPTkkSpellPress::toBytes, CPTkkSpellPress::fromBytes, CPTkkSpellPress::handle);
        INSTANCE.registerMessage(id++, CPTkkSpellUp.class, CPTkkSpellUp::toBytes, CPTkkSpellUp::fromBytes, CPTkkSpellUp::handle);
        INSTANCE.registerMessage(id++, SPTkkSpellUpdata.class, SPTkkSpellUpdata::toBytes, SPTkkSpellUpdata::fromBytes, SPTkkSpellUpdata::handle);
        INSTANCE.registerMessage(id++, SPTkkSpellIconRegister.class, SPTkkSpellIconRegister::toBytes, SPTkkSpellIconRegister::fromBytes, SPTkkSpellIconRegister::handle);
        INSTANCE.registerMessage(id++, SPTkkTrailUpdate.class, SPTkkTrailUpdate::toBytes, SPTkkTrailUpdate::fromBytes, SPTkkTrailUpdate::handle);
        INSTANCE.registerMessage(id++, SPParticleTrailAdd.class, SPParticleTrailAdd::toBytes, SPParticleTrailAdd::fromBytes, SPParticleTrailAdd::handle);
        INSTANCE.registerMessage(id++, SPNoSpamChat.class,SPNoSpamChat::toBytes,SPNoSpamChat::fromBytes,SPNoSpamChat::handle);
        INSTANCE.registerMessage(id++, CPVerifyAnimations.class,CPVerifyAnimations::toBytes,CPVerifyAnimations::fromBytes,CPVerifyAnimations::handle);
        INSTANCE.registerMessage(id++, SPVerifyAnimations.class,SPVerifyAnimations::toBytes,SPVerifyAnimations::fromBytes,SPVerifyAnimations::handle);
        INSTANCE.registerMessage(id++, SPSpawnParticle.class,SPSpawnParticle::toBytes,SPSpawnParticle::fromBytes,SPSpawnParticle::handle);
        INSTANCE.registerMessage(id++, SPPlaySound.class,SPPlaySound::toBytes,SPPlaySound::fromBytes,SPPlaySound::handle);
        INSTANCE.registerMessage(id++, SPTkkAttackSpeedModifiers.class,SPTkkAttackSpeedModifiers::toBytes,SPTkkAttackSpeedModifiers::fromBytes,SPTkkAttackSpeedModifiers::handle);
        INSTANCE.registerMessage(id++, CPUploadingFile.class,CPUploadingFile::toBytes,CPUploadingFile::fromBytes,CPUploadingFile::handle);
        INSTANCE.registerMessage(id++, SPUploadingFile.class,SPUploadingFile::toBytes,SPUploadingFile::fromBytes,SPUploadingFile::handle);
        INSTANCE.registerMessage(id++, SPCustomCollider.class,SPCustomCollider::toBytes,SPCustomCollider::fromBytes,SPCustomCollider::handle);

    }
}
