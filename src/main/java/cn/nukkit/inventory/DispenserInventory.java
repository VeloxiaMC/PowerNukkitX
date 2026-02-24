package cn.nukkit.inventory;


import cn.nukkit.block.blockentity.BlockEntityDispenser;
import cn.nukkit.block.blockentity.BlockEntityNameable;


public class DispenserInventory extends EjectableInventory {
    public DispenserInventory(BlockEntityDispenser blockEntity) {
        super(blockEntity, InventoryType.DISPENSER, 9);
    }

    @Override
    public BlockEntityDispenser getHolder() {
        return (BlockEntityDispenser) super.getHolder();
    }

    @Override
    public BlockEntityNameable getBlockEntityInventoryHolder() {
        return getHolder();
    }
}
