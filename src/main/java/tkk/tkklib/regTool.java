package tkk.tkklib;

import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventListener;
import tkk.epic.js.JsContainer;

public class regTool {
    public static boolean regListener(String id, Event event, int priority, JsContainer listener){
        return regListener(id,event,priority,new JsTool.jsListener(listener));
    }
    public static boolean regListener(String id, Event event, int priority, IEventListener listener){
        return TkkJsEventManager.regListenerList(id,event,priority,listener);
    }
    public static boolean unregListener(String id){
        return TkkJsEventManager.unregListenerList(id);
    }


}
