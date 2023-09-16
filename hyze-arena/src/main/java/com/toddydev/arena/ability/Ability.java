package com.toddydev.arena.ability;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public abstract class Ability implements Listener {

    private String name;
    private List<String> description;
    private ItemStack icon;

    private Integer cooldown;
    private Integer price;

    private ItemStack item;

    public void setDescription(List<String> list) {
        this.description = list;

        ItemMeta meta = icon.getItemMeta();

        List<String> lore = new ArrayList<>();

        for (String descriptionL : description) {
            lore.add(descriptionL);
        }

        meta.setDisplayName("§a" + name);
        meta.setLore(lore);

        icon.setItemMeta(meta);
    }

}
