/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.clearinvcreative;

import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.MetricsLite;

/**
 *
 * @author daboross
 */
public class ClearInvCreativePlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        MetricsLite metrics = null;
        try {
            metrics = new MetricsLite(this);
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Unable to create Metrics", ex);
        }
        if (metrics != null) {
            metrics.start();
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("ClearInvCreative doesn't know about the command /" + cmd.getName());
        return true;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        Player p = evt.getPlayer();
        if (p.getGameMode() == GameMode.CREATIVE) {
            getLogger().log(Level.INFO, "Clearing {0}''s inventory.", p.getName());
            PlayerInventory i = p.getInventory();
            i.clear();
            i.setArmorContents(new ItemStack[4]);
        }
    }
}
