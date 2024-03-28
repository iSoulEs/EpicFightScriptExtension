package tkk.epic.event;

import net.minecraft.util.DamageSource;
import net.minecraftforge.eventbus.api.Event;
import tkk.epic.patch.TkkCustomNpcPatch;
import yesman.epicfight.api.utils.AttackResult;

public class CNpcTryHurtEvent extends Event {
    public TkkCustomNpcPatch patch;
    public AttackResult attackResult;
    public float amount;
    public final DamageSource damageSource;
    public CNpcTryHurtEvent(TkkCustomNpcPatch patch,AttackResult attackResult,DamageSource damageSource,float amount){
        this.patch=patch;
        this.attackResult=attackResult;
        this.damageSource=damageSource;
        this.amount=amount;
    }



}
