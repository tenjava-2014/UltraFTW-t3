package com.tenjava.entries.UltraFTW.t3;

import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class TenJava extends JavaPlugin implements Listener
{
    // saves code/typing
    private String startMessage = "UltraFTW's 2014 ten.java plugin was ";

    // current online players
    public List<UUID> players = new ArrayList<UUID>();

    // amount of game ticks per second, for scheduler
    int ticksPerSec = 20;

    // amount of seconds per minute
    int secsPerMin = 60;

    // the current poor guy getting... whatever, you know what I mean
    UUID currentVictim;

    @Override
    public void onEnable() // when the plugin first starts
    {
        getLogger().info(startMessage + "enabled!"); // tell the server owner a nice message
        getServer().getPluginManager().registerEvents(this, this); // register this as listener
    }

    @Override
    public void onDisable()
    {
        getLogger().info(startMessage + "disabled!"); // say goodbye!
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        players.add(event.getPlayer().getUniqueId());
        JavaPlugin plugin = this;

        // will run after a random amount of time, and will affect a random player
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                doAction(pickAction(), Bukkit.getPlayer(pickPlayer())); // run the action
            }
        }.runTaskLater(this, pickTime()); // run the action on the player after a random amount of time
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        BlockFace direction = getDirection(event.getPlayer().getLocation().getYaw());
        BlockFace opposite = getOppositeDirection(direction);

        if (event.getPlayer().getUniqueId().equals(currentVictim) && getDirection(event.getPlayer().getLocation().getYaw()).equals(opposite))
        {
            // spawn creeper if on certain action
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        players.remove(event.getPlayer().getUniqueId());
    }

    public int pickTime()
    {
        Random random = new Random();

        int ticksPerMin = secsPerMin*ticksPerSec;

        return randomInt(3*ticksPerMin, 25*ticksPerMin, true);
    }

    private int randomInt(int min, int max, boolean inclusive)
    {
        int include = 0; // cheesy way of making inclusive work with least amount of code

        if (inclusive)
            include = 1;

        return new Random().nextInt(max - min + include) + min;
    }

    public UUID pickPlayer()
    {
        UUID player = players.get(randomInt(0, players.size(), false));  // pick player using randomInt and the players list
        currentVictim = player;
        return player;
    }

    public static Action pickAction()
    {
        List<Action> actions = Collections.unmodifiableList(Arrays.asList(com.tenjava.entries.UltraFTW.t3.Action.values()));
        Random rand = new Random();
        return actions.get(rand.nextInt(actions.size()));
    }

    public void doAction(Action action, Player player) // run a given action on a given player
    {
        switch (action)
        {
            case CREEP_SCARE:
                BlockFace[] directions = // possible direction
                {
                    BlockFace.NORTH,
                    BlockFace.EAST,
                    BlockFace.SOUTH,
                    BlockFace.WEST
                };
                BlockFace direction = directions[Math.round(player.getLocation().getYaw()) & 0x3]; // calculate direction
                break;
        }
    }

    private BlockFace getOppositeDirection(BlockFace direction)
    {
        switch (direction)
        {
            case NORTH:
                return BlockFace.SOUTH;
            case SOUTH:
                return BlockFace.NORTH;
            case EAST:
                return BlockFace.WEST;
            case WEST:
                return BlockFace.EAST;

        }
        return null;
    }

    private BlockFace getDirection(float yaw)
    {
        BlockFace[] directions = // possible direction
        {
           BlockFace.NORTH,
           BlockFace.EAST,
           BlockFace.SOUTH,
           BlockFace.WEST
        };

        return directions[Math.round(yaw) & 0x3];
    }
}
