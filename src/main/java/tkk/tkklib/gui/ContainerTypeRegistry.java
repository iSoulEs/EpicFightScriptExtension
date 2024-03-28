package tkk.tkklib.gui;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import tkk.tkklib.TkkGameLib;

public class ContainerTypeRegistry {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, TkkGameLib.MOD_ID);
    public static final RegistryObject<ContainerType<TestContainer>> obsidianFirstContainer = CONTAINERS.register("obsidian_first_container", () -> IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) -> new TestContainer(windowId, inv, new TestContainer.TestIIntArray())));


    public static void bindContainer(){
        ScreenManager.register(ContainerTypeRegistry.obsidianFirstContainer.get(), TestContainerGui::new);

    }
}
