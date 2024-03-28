package tkk.epic.event;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class SpellKeybindingPressEvent  extends Event {
    public final int spellId;
    public final boolean enable;
    public final ServerPlayerEntity player;
    public final boolean forward;
    public final boolean backward;
    public final boolean left;
    public final boolean right;

    public SpellKeybindingPressEvent(ServerPlayerEntity player,int id,boolean isEnable, boolean forward,boolean backward,boolean left,boolean right){
        this.player = player;
        this.spellId = id;
        this.enable = isEnable;
        this.forward=forward;
        this.backward = backward;
        this.left = left;
        this.right = right;
    }
}
