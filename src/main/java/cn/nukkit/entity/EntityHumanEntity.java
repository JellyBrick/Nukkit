package cn.nukkit.entity;

import cn.nukkit.Player;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.NBTIO;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.nbt.tag.ListTag;

public abstract class EntityHumanEntity extends EntityCreature implements InventoryHolder {

    protected PlayerInventory inventory;

    public EntityHumanEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    public PlayerInventory getInventory() {
        return inventory;
    }

    @Override
    protected void initEntity() {
        this.inventory = new PlayerInventory(this);

        if (this.namedTag.contains("Inventory") && this.namedTag.get("Inventory") instanceof ListTag) {
            ListTag<CompoundTag> inventoryList = this.namedTag.getList("Inventory", CompoundTag.class);
            for (CompoundTag item : inventoryList.getAll()) {
                int slot = item.getByte("Slot");
                if (slot >= 0 && slot < 9) {
                    this.inventory.setHotbarSlotIndex(slot, item.contains("TrueSlot") ? item.getByte("TrueSlot") : -1);
                } else if (slot >= 100 && slot < 104) {
                    this.inventory.setItem(this.inventory.getSize() + slot - 100, NBTIO.getItemHelper(item));
                } else {
                    this.inventory.setItem(slot - 9, NBTIO.getItemHelper(item));
                }
            }
        }

        super.initEntity();
    }

    @Override
    public void saveNBT() {
        super.saveNBT();

        this.namedTag.putList(new ListTag<CompoundTag>("Inventory"));
        if (this.inventory != null) {
            for (int slot = 0; slot < 9; ++slot) {
                int hotbarSlot = this.inventory.getHotbarSlotIndex(slot);
                if (hotbarSlot != -1) {
                    Item item = this.inventory.getItem(hotbarSlot);
                    if (item.getId() != 0 && item.getCount() > 0) {
                        this.namedTag.getList("Inventory", CompoundTag.class).add(NBTIO.putItemHelper(item, slot).putByte("TrueSlot", hotbarSlot));
                        continue;
                    }
                }
                this.namedTag.getList("Inventory", CompoundTag.class).add(new CompoundTag()
                        .putByte("Count", 0)
                        .putShort("Damage", 0)
                        .putByte("Slot", slot)
                        .putByte("TrueSlot", -1)
                        .putShort("id", 0)
                );
            }

            int slotCount = Player.SURVIVAL_SLOTS + 9;
            for (int slot = 9; slot < slotCount; ++slot) {
                Item item = this.inventory.getItem(slot - 9);
                this.namedTag.getList("Inventory", CompoundTag.class).add(NBTIO.putItemHelper(item, slot));
            }

            for (int slot = 100; slot < 104; ++slot) {
                Item item = this.inventory.getItem(this.inventory.getSize() + slot - 100);
                if (item != null && item.getId() != Item.AIR) {
                    this.namedTag.getList("Inventory", CompoundTag.class).add(NBTIO.putItemHelper(item, slot));
                }
            }
        }
    }

    @Override
    public Item[] getDrops() {
        if (this.inventory != null) {
            return this.inventory.getContents().values().stream().toArray(Item[]::new);
        }
        return new Item[0];
    }

    public void attack(EntityDamageEvent source){
        if (!this.isAlive()) {
            return;
        }



        super.attack(source);

        if(!source.isCancelled()){

        }
    }
}
