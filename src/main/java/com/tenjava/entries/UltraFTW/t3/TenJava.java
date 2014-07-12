package com.tenjava.entries.UltraFTW.t3;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TenJava extends JavaPlugin implements Listener
{
    // saves code/typing
    private String startMessage = "UltraFTW's 2014 ten.java plugin was ";
    private List players = new ArrayList();

    @Override
    public void onEnable()
    {
        getLogger().info(startMessage + "enabled!");

        //for ()
    }

    @Override
    public void onDisable()
    {
        getLogger().info(startMessage + "enabled!");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        playerleave(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent event)
    {
        playerleave(event.getPlayer().getUniqueId());
    }

    private void playerleave(UUID player)
    {

    }
}
