package com.tenjava.entries.UltraFTW.t3;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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

    // amount of game ticks per second, for scheduler
    int ticksPerSec = 20;

    // amount of seconds per minute
    int secsPerMin = 60;

    // the current poor guy getting... whatever, you know what I mean
    Player currentVictim;

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

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
        if (cmd.getName().equalsIgnoreCase("lor") || cmd.getName().equalsIgnoreCase("lackofrepitition"))
        {
            if (args.length == 2)
            {
                // coming up next

            }

            else if (args.length == 1)
                if (!(sender instanceof Player) && getAction(args[0]) != null)
                    doAction(getAction(args[0]), (Player) sender); // run it on themself. made this for me for testing

            return true;
        }
        return false;
    }

    /*@EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        BlockFace direction = getDirection(event.getFrom().getYaw());
        BlockFace toCheck = getDirection(event.getTo().getYaw());

        tell(event.getPlayer(), direction.toString());
        tell(event.getPlayer(), event.getPlayer().getUniqueId().toString());
        tell(event.getPlayer(), currentVictim.toString());



        if (event.getPlayer().getUniqueId().equals(currentVictim) && checkDirection(direction, toCheck))
        {
            // spawn creeper if on certain action
            tell(event.getPlayer(), "hello");
        }
    }*/

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
        Player[] players = Bukkit.getOnlinePlayers();
        UUID player = players[(randomInt(0, players.length, false))].getUniqueId();  // pick player using randomInt and the players list
        currentVictim = Bukkit.getPlayer(player);
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
        currentVictim = player;
        switch (action)
        {
            /*case CREEP_SCARE:
                getLogger().info("creepscare");
                BlockFace[] directions = // possible direction
                {
                    BlockFace.NORTH,
                    BlockFace.EAST,
                    BlockFace.SOUTH,
                    BlockFace.WEST
                };
                BlockFace direction = directions[Math.round(player.getLocation().getYaw()) & 0x3]; // calculate direction
                break;*/

            case GRAVITY:
                Block block1 = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() + 2, player.getLocation().getBlockZ());
                Material block1mat = block1.getType();
                if (block1.getType() != Material.AIR)
                {
                    player.getWorld().spawnFallingBlock(block1.getLocation(), block1mat, (byte) 0);
                }
                break;
            case LIGHTNING:
                player.getWorld().strikeLightning(player.getLocation());
                break;
        }
    }

    /*private boolean checkDirection(BlockFace direction, BlockFace toCheck)
    {
        switch (direction)
        {
            case NORTH:
                if (toCheck == BlockFace.WEST && toCheck == BlockFace.EAST)
                    return true;
                else
                    return false;
            case SOUTH:
                if (toCheck == BlockFace.WEST && toCheck == BlockFace.EAST)
                    return true;
                else
                    return false;
            case EAST:
                if (toCheck == BlockFace.NORTH && toCheck == BlockFace.SOUTH)
                    return true;
                else
                    return false;
            case WEST:
                if (toCheck == BlockFace.NORTH && toCheck == BlockFace.SOUTH)
                    return true;
                else
                    return false;

        }
        return false;
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
    }*/

    private void tell(Player p, String message)
    {
        p.sendMessage(ChatColor.GOLD + "[LOR] " + ChatColor.GRAY + message);
    }

    public Action getAction(String msg)
    {
        // I hate Java's switch statement

        if (msg.toLowerCase() == "lightning" || msg.toLowerCase() == "storm")
            return Action.LIGHTNING;

        else if (msg.toLowerCase() == "gravity" || msg.toLowerCase() == "fallingblocks")
            return Action.GRAVITY;

        /*else if (msg.toLowerCase() == "jumpscare" || msg.toLowerCase() == "creeper")
            return Action.CREEP_SCARE;

        else if (msg.toLowerCase() == "diamond" || msg.toLowerCase() == "diamonds" || msg.toLowerCase() == "orefire")
            return Action.HOT_DIAMONDS;*/

        else
            return null;
    }
}
