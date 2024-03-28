package yesman.epicfight.api.forgeevent;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.Map;
import java.util.function.Function;

public class WeaponCapabilityPresetRegistryEvent extends Event implements IModBusEvent {
	private final Map<String, Function<Item, CapabilityItem.Builder>> typeEntry;
	
	public WeaponCapabilityPresetRegistryEvent(Map<String, Function<Item, CapabilityItem.Builder>> typeEntry) {
		this.typeEntry = typeEntry;
	}
	public Map<String, Function<Item, CapabilityItem.Builder>> getTypeEntry() {
		return this.typeEntry;
	}
}