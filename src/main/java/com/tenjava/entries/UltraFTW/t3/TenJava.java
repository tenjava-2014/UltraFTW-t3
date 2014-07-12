package com.tenjava.entries.UltraFTW.t3;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TenJava extends JavaPlugin implements Listener
{
    // saves code/typing
    private String startMessage = "UltraFTW's 2014 ten.java plugin was ";

    // current online players (NOT CONTINUALLY UPDATED; THIS UPDATED WHEN IT PLEASES)
    public List<UUID> players = new ArrayList<UUID>();

    // amount of game ticks per second, for scheduler
    int ticksPerSec = 20;

    // amount of seconds per minute
    int secsPerMin = 60;

    @Override
    public void onEnable()
    {
        getLogger().info(startMessage + "enabled!");
    }

    @Override
    public void onDisable()
    {
        getLogger().info(startMessage + "enabled!");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        players.add(event.getPlayer().getUniqueId());
        JavaPlugin plugin = this;

        // will
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                // do cool stuff
            }
        }.runTaskLater(this, pickTime());
    }

    public int pickTime()
    {
        Random random = new Random();
        int randTime = random.nextInt();

        int ticksPerMin = secsPerMin*ticksPerSec;

        int min = 3*ticksPerMin;
        int max = 25*ticksPerMin;

        return new Random().nextInt(max - min + 1) + min;
    }

    public UUID pickPlayer()
    {
        /*for (UUID player : players)
        {

        }*/

        return null; // only temporarily so I can commit/push this
    }

    public static Action randomAction()
    {
        List<Action> actions = Collections.unmodifiableList(Arrays.asList(com.tenjava.entries.UltraFTW.t3.Action.values()));
        Random rand = new Random();
        return actions.get(rand.nextInt(actions.size()));
    }
}
