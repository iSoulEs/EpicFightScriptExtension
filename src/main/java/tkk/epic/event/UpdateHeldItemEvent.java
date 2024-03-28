package tkk.epic.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.eventbus.api.Event;

public class UpdateHeldItemEvent extends Event {
    public final PlayerEntity player;
    public final ItemStack from;
    public final ItemStack to;
    public final Hand hand;
    public UpdateHeldItemEvent(PlayerEntity player, ItemStack from, ItemStack to, Hand hand){
        this.player = player;
        this.from = from;
        this.to = to;
        this.hand = hand;
    }

}
