package com.toddydev.skywars.serializer;

import com.toddydev.skywars.Instance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Serializer {

    public String serialize(Location location) {
        String world = location.getWorld().getName();
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        return world + "," + x + "," + y + "," + z + "," + yaw + "," + pitch;
    }

    public Location deserialize(String string) {
        World world = Bukkit.getWorld(string.split(",")[0]);
        double x = Double.parseDouble(string.split(",")[1]);
        double y = Double.parseDouble(string.split(",")[2]);
        double z = Double.parseDouble(string.split(",")[3]);
        float yaw = Float.parseFloat(string.split(",")[4]);
        float pitch = Float.parseFloat(string.split(",")[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
