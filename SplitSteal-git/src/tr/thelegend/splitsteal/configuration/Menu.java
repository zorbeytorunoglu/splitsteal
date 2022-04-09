package tr.thelegend.splitsteal.configuration;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import tr.thelegend.splitsteal.Main;

public class Menu {
	
	private Main plugin;
	private Inventory menu;
	
	public Menu(Main plugin) {
		this.plugin=plugin;
		this.menu=loadMenu();
	}
	
	private Inventory loadMenu() {
		Inventory inv=Bukkit.getServer().createInventory(null, plugin.getSettingsHandler().getMenuSize(), plugin.getSettingsHandler().getMenuTitle());
		HashMap<Integer, ItemStack> items=plugin.getSettingsHandler().getMenuItems();
		for (Integer i:items.keySet()) {
			inv.setItem(i, items.get(i));
		}
		return inv;
	}
	
	public Inventory getMenu() {
		return menu;
	}

}
