package com.vojtamares.minecraft.plugin.onePlayerSleepingIsEnough.Listeners;

import com.vojtamares.minecraft.plugin.onePlayerSleepingIsEnough.OnePlayerSleepingIsEnough;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerSleepListener implements Listener
{

    private final OnePlayerSleepingIsEnough plugin;

    public PlayerSleepListener(OnePlayerSleepingIsEnough plugin)
    {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onPlayerSleeping(PlayerBedEnterEvent event)
    {
        if (this.isDay(event.getPlayer().getWorld().getTime()) && !event.getPlayer().getWorld().isThundering())
            return;

        if (!event.getPlayer().getWorld().getGameRuleValue(GameRule.DO_DAYLIGHT_CYCLE))
        {
            event.getPlayer().sendMessage(ChatColor.DARK_RED + "You can't sleep during infinite night");
            event.setCancelled(true);
            return;
        }

        Bukkit.broadcastMessage(event.getPlayer().getDisplayName() + ChatColor.BLUE + " is sleeping");

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncDelayedTask(this.plugin, new Runnable() {
            @Override
            public void run() {
                if (!event.getPlayer().isSleeping())
                    return;

                event.getPlayer().getWorld().setTime(0L);

                if (event.getPlayer().getWorld().hasStorm())
                    event.getPlayer().getWorld().setStorm(false);
            }
        }, 100L);
    }

    private boolean isDay(long time)
    {
        if (time > 23435 || time < 12535)
            return true;
        else
            return false;
    }

}
