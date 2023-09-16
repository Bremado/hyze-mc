package com.toddydev.lava.utils;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationUtils {

    @Getter @Setter
    private Location lobby;

    public Location deserialize(String line) {
        World world = Bukkit.getWorld(line.split(",")[0]);
        double x = Double.parseDouble(line.split(",")[1]);
        double y = Double.parseDouble(line.split(",")[2]);
        double z = Double.parseDouble(line.split(",")[3]);
        float yaw = Float.parseFloat(line.split(",")[4]);
        float pitch = Float.parseFloat(line.split(",")[5]);
        return new Location(world,x,y,z,yaw,pitch);
    }

    public String serialize(Location location)  {
        return location.getWorld().getName()+","+location.getX()+","+location.getY()+","+location.getZ()+","+location.getYaw()+","+location.getPitch();
    }
}
