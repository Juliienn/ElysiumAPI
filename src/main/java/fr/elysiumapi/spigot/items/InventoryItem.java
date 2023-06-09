package fr.elysiumapi.spigot.items;

import fr.elysiumapi.spigot.player.ElysiumPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class InventoryItem extends ItemBuilder{

    public InventoryItem(ItemStack item, String name) {
        super(item, name);
    }

    public InventoryItem(ItemStack item, String name, boolean glowing) {
        super(item, name, glowing);
    }

    public InventoryItem(ItemStack item, String name, List<String> lores) {
        super(item, name, lores);
    }

    public InventoryItem(ItemStack item, String name, List<String> lores, boolean glowing) {
        super(item, name, lores, glowing);
    }

    public void setItem(Inventory inventory, int slot){
        inventory.setItem(slot, getItem());
    }

    public abstract void onClick(InventoryClickEvent event);

    @Override
    public void action(ElysiumPlayer elysiumPlayer){
        
    }
    @Override
    public void action(Player player){

    }
    @Override
    public void actionEntity(Player player, Entity entity){

    }
    @Override
    public void actionTarget(Player player, Player target){

    }
}