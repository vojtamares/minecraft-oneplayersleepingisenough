package com.vojtamares.minecraft.plugin.onePlayerSleepingIsEnough;

import com.vojtamares.minecraft.plugin.onePlayerSleepingIsEnough.Listeners.PlayerSleepListener;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class OnePlayerSleepingIsEnough extends JavaPlugin
{

    public static Server server;

    @Override
    public final void onEnable()
    {
        new PlayerSleepListener(this);
        server = this.getServer();
    }

}
