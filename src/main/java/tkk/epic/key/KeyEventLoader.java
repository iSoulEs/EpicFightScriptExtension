package tkk.epic.key;

import com.google.common.collect.Maps;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.util.Map;

public class KeyEventLoader {
    public static Map<String,TkkKeyBinding> map = Maps.newHashMap();

    @SubscribeEvent
    public static void key(InputEvent.KeyInputEvent e){
        InputMappings.Input input = InputMappings.Type.KEYSYM.getOrCreate(e.getKey());
        for (TkkKeyBinding key:map.values().toArray(new TkkKeyBinding[0])){
            if(e.getAction()!=1){continue;}
            if(input.getValue()!=key.keyBinding.getKey().getValue()){continue;}
            if(!key.keyBinding.getKeyModifier().equals(KeyModifier.NONE) && !key.keyBinding.getKeyModifier().equals(KeyModifier.getActiveModifier())){continue;}
            if(key.isPressed()){
                key.pressStart();
            }
        }
    }
    @SubscribeEvent
    public static void tick(TickEvent.ClientTickEvent e){
        for (TkkKeyBinding key:map.values().toArray(new TkkKeyBinding[0])){
            if(key.isKeyDown()){
                key.lastKeyDown=true;
                key.pressTick();
            }else{
                if(key.lastKeyDown) {
                    key.lastKeyDown = false;
                    key.pressOver();
                }
            }
        }
    }
    public static boolean registerKeyBinding(String id,TkkKeyBinding key){
        if(map.containsKey(id)){return false;}
        map.put(id,key);
        ClientRegistry.registerKeyBinding(key.keyBinding);
        return true;
    }
}
