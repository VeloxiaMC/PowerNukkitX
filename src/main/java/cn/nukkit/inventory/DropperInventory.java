package cn.nukkit.inventory;


import cn.nukkit.block.blockentity.BlockEntityDropper;
import cn.nukkit.block.blockentity.BlockEntityNameable;


public class DropperInventory extends EjectableInventory {


    public DropperInventory(BlockEntityDropper blockEntity) {
        super(blockEntity, InventoryType.DROPPER, 9);
    }

    @Override
    public BlockEntityDropper getHolder() {
        return (BlockEntityDropper) super.getHolder();
    }

    @Override
    public BlockEntityNameable getBlockEntityInventoryHolder() {
        return getHolder();
    }
}
