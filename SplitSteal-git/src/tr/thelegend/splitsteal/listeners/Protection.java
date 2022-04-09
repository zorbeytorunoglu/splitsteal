package tr.thelegend.splitsteal.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tr.thelegend.splitsteal.Main;
import tr.thelegend.splitsteal.objects.SSArena;

public class Protection implements Listener {
	
	private Main plugin;
	public Protection(Main plugin) {
		this.plugin=plugin;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		
		if (e.getPlayer().isOp()) return;
		
		if (!plugin.getUtil().inArena(e.getPlayer())) return;
		
		e.setCancelled(true);
		e.getPlayer().sendMessage(plugin.getSettingsHandler().getYouCantDoIt());
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer().isOp()) return;
		if (!plugin.getUtil().inArena(e.getPlayer())) return;
		
		e.setCancelled(true);
		e.getPlayer().sendMessage(plugin.getSettingsHandler().getYouCantDoIt());
	}
	
	@EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
            Player whoWasHit = (Player) e.getEntity();
            Player whoHit = (Player) e.getDamager();
            
            if (plugin.getUtil().inArena(whoHit) || plugin.getUtil().inArena(whoWasHit)) e.setCancelled(true);
        }
        return;
    }
	
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (e.getPlayer().isOp()) return;
		if (!plugin.getUtil().inArena(e.getPlayer())) return;
		
		boolean ok=false;
		for (String s:plugin.getSettingsHandler().getCommandWhitelist()) {
			if (e.getMessage().startsWith(s)) ok=true;
		}
		
		if (!ok) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(plugin.getSettingsHandler().getCommandBlocked());
		}
		return;
		
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (e.getPlayer().isOp()) return;
		if (!plugin.getUtil().inArena(e.getPlayer())) return;
		e.setCancelled(true);
		e.getPlayer().sendMessage(plugin.getSettingsHandler().getYouCantDoIt());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if (!plugin.getUtil().inArena(e.getPlayer())) return;
		
		SSArena arena=plugin.getUtil().getArena(e.getPlayer());
		
		plugin.getUtil().cancelSplitSteal(arena);
		
	}
	
}
