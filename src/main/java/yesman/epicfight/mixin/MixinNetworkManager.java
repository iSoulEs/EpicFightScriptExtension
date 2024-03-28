package yesman.epicfight.mixin;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.TimeoutException;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.*;
import net.minecraft.network.play.server.SDisconnectPacket;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import tkk.epic.TkkEpic;

import javax.annotation.Nullable;
import java.net.SocketAddress;
import java.util.Queue;

@Mixin(value = NetworkManager.class)
public abstract class MixinNetworkManager extends SimpleChannelInboundHandler<IPacket<?>> {

    @Shadow private PacketDirection receiving;
    @Shadow private Queue queue;
    @Shadow private Channel channel;
    @Shadow private SocketAddress address;
    @Shadow private INetHandler packetListener;
    @Shadow private ITextComponent disconnectedReason;
    @Shadow private boolean encrypted;
    @Shadow private boolean disconnectionHandled;
    @Shadow private int receivedPackets;
    @Shadow private int sentPackets;
    @Shadow private float averageReceivedPackets;
    @Shadow private float averageSentPackets;
    @Shadow private int tickCount;
    @Shadow private boolean handlingFault;
    @Shadow private java.util.function.Consumer<NetworkManager> activationHandler;
    /**
     * @author
     * @reason
     */
    @Overwrite
    public void exceptionCaught(ChannelHandlerContext p_exceptionCaught_1_, Throwable p_exceptionCaught_2_) {
        TkkEpic.LOGGER.log(Level.ERROR,"tkkk "+p_exceptionCaught_2_);
        p_exceptionCaught_2_.printStackTrace();
        if (p_exceptionCaught_2_ instanceof SkipableEncoderException) {
            TkkEpic.LOGGER.debug("Skipping packet due to errors", p_exceptionCaught_2_.getCause());
        } else {
            boolean flag = !this.handlingFault;
            this.handlingFault = true;
            if (this.channel.isOpen()) {
                if (p_exceptionCaught_2_ instanceof TimeoutException) {
                    TkkEpic.LOGGER.debug("Timeout", p_exceptionCaught_2_);
                    this.disconnect(new TranslationTextComponent("disconnect.timeout"));
                } else {
                    TkkEpic.LOGGER.log(Level.ERROR,"tkk "+p_exceptionCaught_2_);
                    p_exceptionCaught_2_.printStackTrace();
                    ITextComponent itextcomponent = new TranslationTextComponent("disconnect.genericReason", " tkk Internal Exception: " + p_exceptionCaught_2_);
                    if (flag) {
                        TkkEpic.LOGGER.debug("Failed to sent packet", p_exceptionCaught_2_);
                        this.send(new SDisconnectPacket(itextcomponent), (p_211391_2_) -> {
                            this.disconnect(itextcomponent);
                        });
                        this.setReadOnly();
                    } else {
                        TkkEpic.LOGGER.debug("Double fault", p_exceptionCaught_2_);
                        this.disconnect(itextcomponent);
                    }
                }

            }
        }
    }
    @Shadow public abstract void disconnect(ITextComponent p_150718_1_);
    @Shadow public abstract void send(IPacket<?> p_201058_1_, @Nullable GenericFutureListener<? extends Future<? super Void>> p_201058_2_);
    @Shadow public abstract void setReadOnly();
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, IPacket<?> msg) throws Exception {

    }
}
