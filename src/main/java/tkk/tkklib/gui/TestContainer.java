package tkk.tkklib.gui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkHooks;
import tkk.tkklib.TkkGameLib;

import javax.annotation.Nullable;

public class TestContainer extends Container {
    public static TestIIntArray testIIntArray=new TestIIntArray();
    public static Inventory inventory=new Inventory(1);
    public static void openForPlayer(ServerPlayerEntity player){
        NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() { // from class: noppes.npcs.NoppesUtilServer.3
            @Nullable
            public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                return new TestContainer(p_createMenu_1_,p_createMenu_2_,testIIntArray);
            }

            public ITextComponent getDisplayName() {
                return new TranslationTextComponent("gui." + TkkGameLib.MOD_ID + ".first_container");
            }
        }, (PacketBuffer packerBuffer) -> {
        });
    }

    private TestIIntArray intArray;
    protected TestContainer(int id, PlayerInventory playerInventory,  TestIIntArray intArray) {
        super(ContainerTypeRegistry.obsidianFirstContainer.get(), id);
        this.intArray = intArray;
        addDataSlots(this.intArray);
        //ObsidianFirstContainerTileEntity obsidianFirstContainerTileEntity = (ObsidianFirstContainerTileEntity) world.getTileEntity(pos);
        this.addSlot(new Slot(inventory, 0, 80, 32));
        layoutPlayerInventorySlots(playerInventory, 8, 84);
    }

    /*
    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }

     */

    @Override
    public boolean stillValid(PlayerEntity p_75145_1_) {
        return true;
    }

    private int addSlotRange(IInventory inventory, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(inventory, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IInventory inventory, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(inventory, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(IInventory inventory, int leftCol, int topRow) {
        // Player inventory
        addSlotBox(inventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(inventory, 0, leftCol, topRow, 9, 18);
    }

    public IIntArray getIntArray() {
        return intArray;
    }

    public static class TestIIntArray implements IIntArray {
        int i = 0;

        @Override
        public int get(int index) {
            return i;
        }

        @Override
        public void set(int index, int value) {
            i = value;
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
