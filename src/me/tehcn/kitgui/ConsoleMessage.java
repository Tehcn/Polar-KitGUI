package me.tehcn.kitgui;

import org.bukkit.ChatColor;

import static org.bukkit.Bukkit.getServer;

public class ConsoleMessage {
    public void send(String m) {
        getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', m));
    }
}
