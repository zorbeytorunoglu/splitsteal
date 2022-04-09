package tr.thelegend.splitshare.listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import tr.thelegend.splitshare.Main;
import tr.thelegend.splitshare.objects.SSArena;

public class Choose implements Listener {
	
	private Main plugin;
	public Choose(Main plugin) {
		this.plugin=plugin;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		if (e.getClickedBlock()==null) return;
		
		if (e.getClickedBlock().getType()==Material.AIR) return;
		
		if (plugin.getArenas().isEmpty()) return;
		
		if (!plugin.getUtil().inArena(e.getPlayer())) return;
		
		if (plugin.getUtil().isChoiceButton(e.getClickedBlock())==null) return;
		
		e.setCancelled(true);
		
		plugin.getUtil().openMenu(e.getPlayer());
		
	}
	
	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		
		if (e.getClickedInventory()==null) return;
		
		if (e.getCurrentItem()==null) return;
		
		if (!e.getView().getTitle().equals(plugin.getSettingsHandler().getMenuTitle())) return;
		
		e.setCancelled(true);
		
		if (e.getCurrentItem().getType()==Material.AIR) return;

		if (e.getCurrentItem().getItemMeta()==null) return;
		
		if (plugin.getArenas().isEmpty()) return;
		
		if (!plugin.getUtil().inArena((Player)e.getWhoClicked())) return;
		
		Player player=(Player)e.getWhoClicked();
		
		NamespacedKey key=new NamespacedKey(plugin,"function");
		if (!e.getCurrentItem().getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING)) return;
		
		String function=e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
		
		SSArena arena=plugin.getUtil().getArena(player);
		
		switch (function) {
		case "none":
			return;
		case "back":
			player.closeInventory();
			return;
		case "steal":
			if (!plugin.getSettingsHandler().isChangeOfHeartEnabled()) {
				if (plugin.getUtil().isFirst(arena, player)) {
					if (arena.getFirstPlayerChoice()!=null) {
						e.setCancelled(true);
						player.closeInventory();
						player.sendMessage(plugin.getSettingsHandler().getNoChangeOfHeart());
						if (!plugin.getSettingsHandler().getNoChangeOfHeartSound().equals("none")) 
							player.playSound(player.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getNoChangeOfHeartSound()), 2F, 1F);
						return;
					}
				} else {
					if (arena.getSecondPlayerChoice()!=null) {
						e.setCancelled(true);
						player.closeInventory();
						player.sendMessage(plugin.getSettingsHandler().getNoChangeOfHeart());
						if (!plugin.getSettingsHandler().getNoChangeOfHeartSound().equals("none")) 
							player.playSound(player.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getNoChangeOfHeartSound()), 2F, 1F);
						return;
					}
				}
			}
			if (!plugin.getSettingsHandler().getStealSound().equals("none"))
				player.playSound(player.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getStealSound()), 2F, 1F);
			player.closeInventory();
			player.sendMessage(plugin.getSettingsHandler().getChosenSteal());
			if (plugin.getUtil().isFirst(arena, player)) {
				arena.setFirstPlayerChoice(function);
				return;
			} else {
				arena.setSecondPlayerChoice(function);
				return;
			}
			
		case "split":
			if (!plugin.getSettingsHandler().isChangeOfHeartEnabled()) {
				if (plugin.getUtil().isFirst(arena, player)) {
					if (arena.getFirstPlayerChoice()!=null) {
						e.setCancelled(true);
						player.closeInventory();
						player.sendMessage(plugin.getSettingsHandler().getNoChangeOfHeart());
						if (!plugin.getSettingsHandler().getNoChangeOfHeartSound().equals("none")) 
							player.playSound(player.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getNoChangeOfHeartSound()), 2F, 1F);
						return;
					}
				} else {
					if (arena.getSecondPlayerChoice()!=null) {
						e.setCancelled(true);
						player.closeInventory();
						player.sendMessage(plugin.getSettingsHandler().getNoChangeOfHeart());
						if (!plugin.getSettingsHandler().getNoChangeOfHeartSound().equals("none")) 
							player.playSound(player.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getNoChangeOfHeartSound()), 2F, 1F);
						return;
					}
				}
			}
			if (!plugin.getSettingsHandler().getSplitSound().equals("none"))
				player.playSound(player.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getSplitSound()), 2F, 1F);
			player.closeInventory();
			player.sendMessage(plugin.getSettingsHandler().getChosenSplit());
			if (plugin.getUtil().isFirst(arena, player)) {
				arena.setFirstPlayerChoice(function);
				return;
			} else {
				arena.setSecondPlayerChoice(function);
				return;
			}
			
			default: return;
			
		}
		
	}
	
}
