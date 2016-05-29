package cn.nukkit.item;


import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockWater;
import cn.nukkit.level.Level;

public class ItemGlassBottle extends Item {

    public ItemGlassBottle() {
        this(0, 1);
    }

    public ItemGlassBottle(int meta) {
        this(meta, 1);
    }

    public ItemGlassBottle(int meta, int count) {
        super(GLASS_BOTTLE, meta, count, "Glass Bottle");
    }

    @Override
    public boolean onActivate(Level level, Player player, Block block, Block target, int face, double fx, double fy, double fz) {
        if (target instanceof BlockWater) {
            if (this.getCount() > 1) {
                if (player.getInventory().canAddItem(Item.get(Item.POTION))) {
                    this.setCount(this.getCount() - 1);
                    player.getInventory().addItem(Item.get(Item.POTION));
                }
            } else {
                player.getInventory().setItemInHand(Item.get(Item.POTION));
            }

            return true;
        }

        return false;
    }
}