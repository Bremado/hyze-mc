package com.toddydev.lava.recipe;

import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class Recipes {

    public static void loadRecipes() {
        ItemStack sopa = new ItemCreator(Material.MUSHROOM_SOUP, "§aSopa").build();
        ShapelessRecipe cocoa = new ShapelessRecipe(sopa);
        ShapelessRecipe cactus = new ShapelessRecipe(sopa);

        cactus.addIngredient(Material.BOWL);
        cactus.addIngredient(1, Material.CACTUS);

        cocoa.addIngredient(Material.BOWL);
        cocoa.addIngredient(Material.INK_SACK, 3);

        Bukkit.addRecipe(cocoa);
        Bukkit.addRecipe(cactus);
    }
}
