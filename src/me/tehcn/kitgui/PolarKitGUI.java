package me.tehcn.kitgui;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Locale;

public class PolarKitGUI extends JavaPlugin implements Listener {

    public static Inventory kits;

    @Override
    public void onEnable() {
        ConsoleMessage messager = new ConsoleMessage();
        messager.send(colorize(polarTag() + "Loading event listener loaded"));
        this.getServer().getPluginManager().registerEvents(this, this);
        messager.send(colorize(polarTag() + "Event listener loaded"));
        messager.send(colorize(polarTag() + "Loading GUI..."));
        createInventory();
        messager.send(colorize(polarTag() + "Finished loading GUI"));
        messager.send(colorize(polarTag() + "Enabled successfully"));
        messager.send(colorize(polarTag() + "Using Polar-KitGUI version " + getDescription().getVersion()));
    }

    @Override
    public void onDisable() {
        ConsoleMessage messager = new ConsoleMessage();
        messager.send(polarTag() + "Thanks for using Polar-KitGUI");
    }

    private void createInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, ChatColor.AQUA + "Polar Kits");
        ItemStack item = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta meta = item.getItemMeta();


        meta.setDisplayName(ChatColor.GRAY + "Noob Kit");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.GOLD + "Click here to get the kit");
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(3, item);

        item.setType(Material.DIRT);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GRAY + "Caveman Kit");
        item.setItemMeta(meta);
        inv.setItem(2, item);

        item.setType(Material.IRON_BLOCK);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Iron Kit");
        item.setItemMeta(meta);
        inv.setItem(4, item);

        item.setType(Material.DIAMOND_BLOCK);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Diamond Kit");
        item.setItemMeta(meta);
        inv.setItem(5, item);

        kits = inv;
    }

    private String colorize(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
    private String polarTag() {
        return colorize("&f[&bPolar-KitGUI&f] ");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        String noConsoleMessage = ChatColor.translateAlternateColorCodes('&', "Not runnable from console");
        if (command.getName().equalsIgnoreCase("kits")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(noConsoleMessage);
                return true;
            }
            Player player = (Player) sender;
            player.openInventory(kits);
            return true;
        }
        return true;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().toLowerCase(Locale.ROOT).contains("kits")) return;
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().getItemMeta() == null) return;

        Player player = (Player) event.getWhoClicked();

        event.setCancelled(true);
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;

        if (event.getSlot() == 2) {
            // caveman kit
            player.closeInventory();
            player.updateInventory();

            ItemStack[] items = {
                    new ItemStack(Material.STICK),
                    new ItemStack(Material.FLINT_AND_STEEL),
                    new ItemStack(Material.WOODEN_HOE)
            };
            giveKit(player, items, "Caveman", ChatColor.DARK_GRAY);
            return;
        }

        if (event.getSlot() == 3) {
            // noob kit
            player.closeInventory();
            player.updateInventory();

            ItemStack[] items = {
                    new ItemStack(Material.STONE_SWORD),
                    new ItemStack(Material.STONE_PICKAXE),
                    new ItemStack(Material.STONE_AXE),
                    new ItemStack(Material.STONE_SHOVEL),
                    new ItemStack(Material.LEATHER_HELMET),
                    new ItemStack(Material.LEATHER_CHESTPLATE),
                    new ItemStack(Material.LEATHER_LEGGINGS),
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.COOKED_BEEF, 64),
                    new ItemStack(Material.OAK_LOG, 64),
                    new ItemStack(Material.GOLDEN_APPLE, 8)
            };
            giveKit(player, items, "Noob", ChatColor.GRAY);
            return;
        }
        if (event.getSlot() == 4) {
            // iron kit
            if (!player.hasPermission("kits.iron") || !player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this kit");
                return;
            }
            player.closeInventory();
            player.updateInventory();

            ItemStack[] items = {
                    new ItemStack(Material.IRON_SWORD),
                    new ItemStack(Material.IRON_PICKAXE),
                    new ItemStack(Material.IRON_AXE),
                    new ItemStack(Material.IRON_SHOVEL),
                    new ItemStack(Material.IRON_HELMET),
                    new ItemStack(Material.IRON_CHESTPLATE),
                    new ItemStack(Material.IRON_LEGGINGS),
                    new ItemStack(Material.IRON_BOOTS),
                    new ItemStack(Material.COOKED_BEEF, 64),
                    new ItemStack(Material.OAK_LOG, 64),
                    new ItemStack(Material.GOLDEN_APPLE, 8)
            };
            giveKit(player, items, "Iron", ChatColor.WHITE);

            return;
        }
        if (event.getSlot() == 5) {
            // diamond kit
            if (!player.hasPermission("kits.diamond") || !player.isOp()) {
                player.sendMessage(ChatColor.RED + "You do not have permission to use this kit");
                return;
            }
            player.closeInventory();
            player.updateInventory();

            ItemStack[] items = {
                    new ItemStack(Material.DIAMOND_SWORD),
                    new ItemStack(Material.DIAMOND_PICKAXE),
                    new ItemStack(Material.DIAMOND_AXE),
                    new ItemStack(Material.DIAMOND_SHOVEL),
                    new ItemStack(Material.DIAMOND_HELMET),
                    new ItemStack(Material.DIAMOND_CHESTPLATE),
                    new ItemStack(Material.DIAMOND_LEGGINGS),
                    new ItemStack(Material.DIAMOND_BOOTS),
                    new ItemStack(Material.COOKED_BEEF, 64),
                    new ItemStack(Material.OAK_LOG, 64),
                    new ItemStack(Material.GOLDEN_APPLE, 8)
            };
            giveKit(player, items, "Diamond", ChatColor.AQUA);

            return;
        }
    }

    private void giveKit(Player player, ItemStack[] items, String kitName, ChatColor kitColor) {
        for (int i = 0; i < items.length; i++) {
            ItemStack item = items[i];
            ItemMeta meta = item.getItemMeta();
            if (!item.getType().toString().contains("_")) {
                meta.setDisplayName(kitColor + kitName + " " + WordUtils.capitalizeFully(item.getType().toString()));
            }
            else {
                meta.setDisplayName(kitColor + kitName + " " + WordUtils.capitalizeFully(item.getType().toString().split("_")[1]));
            }
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(kitColor + "Part of the " + kitName + " kit");
            meta.setLore(lore);
            item.setItemMeta(meta);
            if (!(item.getType() == Material.OAK_LOG) || !(item.getType() == Material.GOLDEN_APPLE) || !(item.getType() == Material.COOKED_BEEF)) {
                item.addUnsafeEnchantment(Enchantment.DURABILITY, 3);
            }
            player.getInventory().addItem(item);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPlayedBefore()) {
            event.setJoinMessage(ChatColor.AQUA + player.getDisplayName() + " has joined");
            return;
        }
        event.setJoinMessage(ChatColor.AQUA + player.getDisplayName() + " has joined for the first time!");
        player.performCommand("/kits noob");
    }
}
