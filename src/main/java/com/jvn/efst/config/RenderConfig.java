package com.jvn.efst.config;

import com.google.common.collect.Maps;
import com.jvn.efst.tools.Trail;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.function.Function;


public class RenderConfig {
    public static Map<String, Trail> TrailItem = Maps.newHashMap();
    public static final Map<String, Function<ItemStack,Trail>> SpecialTrailItem = Maps.newHashMap();
    static {
        /*
        TrailItem.put("minecraft:diamond_sword",new  Trail(0,0,-0.1f,0,0,-1.0f,0,249,255,140));
        TrailItem.put("minecraft:golden_sword",new  Trail(0,0,-0.1f,0,0,-1.0f,255,255,51,140));

        TrailItem.put("epicaddon:elucidator",new  Trail(0,0,-0.18f,0,0,-1.47f,0,249,255,140));
        TrailItem.put("epicaddon:dark_repulsor",new  Trail(0,0,-0.18f,0,0,-1.47f,0,249,255,140));
        TrailItem.put("epicaddon:lambent_light",new  Trail(0,0,-0.12f,0,0,-1.75f,204,0,255,150));

        TrailItem.put("epicaddon:anneal_blade",new  Trail(0,0,-0.17f,0,0,-1.27f,80,249,255,150));

        TrailItem.put("epicfight:katana",new  Trail(0,0,-0.2f,0,-0.2f,-1.6f,255,30,30,120));
        TrailItem.put("epicfight:netherite_greatsword",new Trail(0,0,-0.17f,0,-0f,-2.15f,138,4,226,180));
        TrailItem.put("epicaddon:destiny",new Trail(0,0,-0.23f,0,0,-2.25f,255,255,51,180));
        */
    }

    public static void AddSpecial(String id,Function<ItemStack,Trail> func){
        SpecialTrailItem.put(id,func);
    }

    public static Trail getItemTrailRaw(String n){
        return TrailItem.get(n);
    }

    public static Trail getItemTrail(ItemStack itemStack){
        if(itemStack.isEmpty()) return null;
        String n = itemStack.getItem().getRegistryName().toString();
        Function<ItemStack,Trail> getter = SpecialTrailItem.get(n);
        if(getter != null) return getter.apply(itemStack);
        else return getItemTrailRaw(n);
    }


}
