package com.toddydev.auth.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.toddydev.hyze.bukkit.utils.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Utils {

    public static ItemStack deserializeItem(String linha) {
        Material material = Material.getMaterial(linha.split(":")[0]);
        String display = linha.split(":")[1];
        Integer amount = Integer.parseInt(linha.split(":")[2]);
        int id = Integer.parseInt(linha.split(":")[3]);
        return new ItemCreator(material, display,amount).changeId(id).build();
    }

    public static void unregisterCommands(String... commands) {
        try {
            Field firstField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            firstField.setAccessible(true);

            CommandMap commandMap = (CommandMap) firstField.get(Bukkit.getServer());
            Field secondField = commandMap.getClass().getDeclaredField("knownCommands");
            secondField.setAccessible(true);

            HashMap<String, Command> knownCommands = (HashMap<String, Command>) secondField.get(commandMap);

            for (String command : commands) {
                if (knownCommands.containsKey(command)) {
                    knownCommands.remove(command);
                    List<String> aliases = new ArrayList<>();
                    for (String key : knownCommands.keySet()) {
                        if (!key.contains(":"))
                            continue;

                        String substr = key.substring(key.indexOf(":") + 1);
                        if (substr.equalsIgnoreCase(command)) {
                            aliases.add(key);
                        }
                    }
                    for (String alias : aliases) {
                        knownCommands.remove(alias);
                    }
                }
            }
        } catch (Exception e) {}
    }

    public static Location deserializeLocation(String line) {
        World world = Bukkit.getWorld(line.split(",")[0]);
        double x = Double.parseDouble(line.split(",")[1]);
        double y = Double.parseDouble(line.split(",")[2]);
        double z = Double.parseDouble(line.split(",")[3]);
        float yaw = Float.parseFloat(line.split(",")[4]);
        float pitch = Float.parseFloat(line.split(",")[5]);
        return new Location(world,x,y,z,yaw,pitch);
    }
    public static String serializeLocation(Location location) {
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch();
    }
}
