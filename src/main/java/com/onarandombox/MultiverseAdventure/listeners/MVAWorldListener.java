package com.onarandombox.MultiverseAdventure.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.world.WorldListener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

import com.onarandombox.MultiverseAdventure.MultiverseAdventure;

public class MVAWorldListener extends WorldListener {
    private static final List<String> passes = new ArrayList<String>();

    @Override
    public void onWorldLoad(WorldLoadEvent event) {
        final WorldLoadEvent fevent = event;
        if (!passes.contains(event.getWorld().getName())) {
            MultiverseAdventure.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(MultiverseAdventure.getInstance(), new Runnable() {
                @Override
                public void run() {
                    MultiverseAdventure.getInstance().getAdventureWorldsManager().tryEnableWorld(fevent.getWorld().getName());
                }
            }, 20);
        }
        else {
            MultiverseAdventure.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(MultiverseAdventure.getInstance(), new Runnable() {
                @Override
                public void run() {
                    MultiverseAdventure.getInstance().getAdventureWorldsManager().tryEnableWorld(fevent.getWorld().getName(), true); // Without reset here
                }
            }, 20);
            passes.remove(event.getWorld().getName());
        }
    }

    @Override
    public void onWorldUnload(WorldUnloadEvent event) {
        MultiverseAdventure.getInstance().getAdventureWorldsManager().disableWorld(event.getWorld().getName());
    }

    public static void addPass(String worldname) {
        passes.add(worldname);
    }
}
