package tr.thelegend.splitsteal;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import tr.thelegend.splitsteal.configuration.Resource;
import tr.thelegend.splitsteal.objects.SSArena;

public class Util {
	
	private Main plugin;
	
	public Util(Main plugin) {
		this.plugin=plugin;
	}
	
	public String hex(String message) {
        Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String hexCode = message.substring(matcher.start(), matcher.end());
            String replaceSharp = hexCode.replace('#', 'x');
           
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder("");
            for (char c : ch) {
                builder.append("&" + c);
            }
            
            message = message.replace(hexCode, builder.toString());
            matcher = pattern.matcher(message);
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }
	
	public boolean doesExists(String name) {
		
		if (plugin.getArenas().isEmpty()) return false;
		
		for (SSArena arena:plugin.getArenas()) {
			if (arena.getName().equals(name)) return true;
		}
		
		return false;
		
	}
	
	public SSArena getArena(String name) {
		
		return plugin.getArenas().stream().filter(arena -> arena.getName().equals(name)).findAny().orElse(null);
		
	}
	
	public ArrayList<SSArena> loadArenas(Resource data) {
		
		ArrayList<SSArena> list=new ArrayList<SSArena>();
		
		if (!data.getFile().exists()) return list;
		
		ConfigurationSection arenas=data.getConfigurationSection("arenas");
		
		if (arenas==null) return list;
		
		Set<String> set=data.getConfigurationSection("arenas").getKeys(false);
		
		if (set.isEmpty()) return list;
		
		for (String s:set) {
			
			SSArena arena=new SSArena(s);
			
			Location pos1=data.getLocation("arenas."+s+".pos1");
			
			Location pos2=data.getLocation("arenas."+s+".pos2");
			
			Location res1=data.getLocation("arenas."+s+".result1");
			
			Location res2=data.getLocation("arenas."+s+".result2");
			
			Location spawn=data.getLocation("arenas."+s+".spawn");
			
			if (data.contains("arenas."+s+".buttons")) {
				ArrayList<Location> buttons=new ArrayList<>();
				Set<String> bset=data.getConfigurationSection("arenas."+s+".buttons").getKeys(false);
				for (String s1:bset) {
					Location loc=data.getLocation("arenas."+s+".buttons."+s1);
					buttons.add(loc);
				}
				arena.setChooseButtons(buttons);
			}
			
			arena.setPos1(pos1);
			arena.setPos2(pos2);
			arena.setResultSpot1(res1);
			arena.setResultSpot2(res2);
			arena.setSpawn(spawn);
			
			arena.setOccupied(false);
			
			list.add(arena);
			
		}
		
		return list;
		
	}
	
	public void saveArenas(ArrayList<SSArena> list, Resource data) {
		if (list==null) return;
		if (list.isEmpty()) return;
		for (SSArena arena:list) {
			data.set("arenas."+arena.getName()+".pos1", arena.getPos1());
			data.set("arenas."+arena.getName()+".pos2", arena.getPos2());
			data.set("arenas."+arena.getName()+".result1", arena.getResultSpot1());
			data.set("arenas."+arena.getName()+".result2", arena.getResultSpot2());
			data.set("arenas."+arena.getName()+".spawn", arena.getSpawn());
			if (!arena.getChooseButtons().isEmpty()) {
				
				for (int i=0; i < arena.getChooseButtons().size(); i++) {
					
					data.set("arenas."+arena.getName()+".buttons."+i, arena.getChooseButtons().get(i));
					
				}
				
			}
		}
		data.save();
	}
	
	public void startSplitShare(SSArena arena) {
		
		Player p1=arena.getFirstPlayer();
		Player p2=arena.getSecondPlayer();
		
		p1.teleport(arena.getPos1());
		
		if (plugin.getSettingsHandler().getTeleportSound()!=null)
			p1.playSound(p1.getLocation(), plugin.getSettingsHandler().getTeleportSound(), 2F, 1F);
		
		p2.teleport(arena.getPos2());
		
		if (plugin.getSettingsHandler().getTeleportSound()!=null)
			p2.playSound(p2.getLocation(), plugin.getSettingsHandler().getTeleportSound(), 2F, 1F);
		
		if (plugin.getSettingsHandler().isStartTitleEnabled()) {
			p1.sendTitle(plugin.getSettingsHandler().getStartTitle()
					.replace("%player%", p1.getName()).replace("%amount%", arena.getAmount()+"")
					, plugin.getSettingsHandler().getStartSubtitle()
					.replace("%player%", p1.getName()).replace("%amount%", arena.getAmount()+"")
					, 10, 70, 20);
			p2.sendTitle(plugin.getSettingsHandler().getStartTitle()
					.replace("%player%", p1.getName()).replace("%amount%", arena.getAmount()+""),
					plugin.getSettingsHandler().getStartSubtitle()
					.replace("%player%", p1.getName()).replace("%amount%", arena.getAmount()+""),
					10, 70, 20);
		}
		
		if (plugin.getSettingsHandler().isStartMessageEnabled()) {
			for (String s:plugin.getSettingsHandler().getStartMessage()) {
				p1.sendMessage(s.replace("%seconds%", plugin.getSettingsHandler().getSSTimeLimit()+"")
						.replace("%amount%", arena.getAmount()+"")
						.replace("%player%", p2.getName()));
				p2.sendMessage(s.replace("%seconds%", plugin.getSettingsHandler().getSSTimeLimit()+"")
						.replace("%amount%", arena.getAmount()+"")
						.replace("%player%", p2.getName()));
			}
		}
		
		new BukkitRunnable() {
			int countdown=plugin.getSettingsHandler().getSSTimeLimit();
			@Override
			public void run() {
				
				if (arena.getFirstPlayer()==null || arena.getSecondPlayer()==null) {
					cancel();
					return;
				}
				
				if (countdown==0) {
					endSplitSteal(arena);
					cancel();
					return;
				}
				
				if (countdown<4) {
					
					if (!plugin.getSettingsHandler().getCountdownSound().equals("none")) {
						p1.playSound(p1.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getCountdownSound()), 2F, 1F);
						p2.playSound(p1.getLocation(), Sound.valueOf(plugin.getSettingsHandler().getCountdownSound()), 2F, 1F);
					}
					
				}
				
				countdown--;
				
			}
		}.runTaskTimer(plugin, 5L, 20L);
		
	}
	
	public void endSplitSteal(SSArena arena) {
		if (arena.getFirstPlayer()==null || arena.getSecondPlayer()==null) return;
		Player p1=arena.getFirstPlayer();
		Player p2=arena.getSecondPlayer();
		
		String p1choice=arena.getFirstPlayerChoice();
		String p2choice=arena.getSecondPlayerChoice();
		
		if (p1choice==null || p2choice==null) {
			if (p1choice==null && p2choice!=null) {
				p2.sendMessage(plugin.getSettingsHandler().getCanceledNoChoice().replace("%player%", p1.getName()));
			} else if (p2choice==null && p1choice!=null) {
				p1.sendMessage(plugin.getSettingsHandler().getCanceledNoChoice().replace("%player%", p2.getName()));
			} else if (p1choice==null && p2choice==null) {
				p1.sendMessage(plugin.getSettingsHandler().getCanceledNoOneChose());
				p2.sendMessage(plugin.getSettingsHandler().getCanceledNoOneChose());
			}
			if (arena.getSpawn()!=null) {
				arena.getFirstPlayer().teleport(arena.getSpawn());
				arena.getSecondPlayer().teleport(arena.getSpawn());
			}
			cancelSplitSteal(arena);
			return;
		}
		
		reward(arena);
		
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if (arena.getSpawn()!=null) {
					arena.getFirstPlayer().teleport(arena.getSpawn());
					arena.getSecondPlayer().teleport(arena.getSpawn());
				}
				resetArena(arena);
			}
			
		},20L*5);
		
	}
	
	public void resetArena(SSArena arena) {
		
		arena.getResultSpot1().getBlock().setType(Material.AIR);
		arena.getResultSpot2().getBlock().setType(Material.AIR);
		
		arena.setAmount(0);
		arena.setFirstPlayer(null);
		arena.setSecondPlayer(null);
		arena.setOccupied(false);
		arena.setSecondPlayerChoice(null);
		arena.setFirstPlayerChoice(null);
		
	}
	
	public void cancelSplitSteal(SSArena arena) {
		
		resetArena(arena);
		
		if (arena.getSpawn()!=null) {
			arena.getFirstPlayer().teleport(arena.getSpawn());
			arena.getSecondPlayer().teleport(arena.getSpawn());
		}
		
	}
	
	public boolean isArenaComplete(SSArena arena) {
		
		return arena.getSpawn()!=null && !arena.getChooseButtons().isEmpty() &&
				arena.getPos1()!=null && arena.getPos2()!=null && arena.getResultSpot1()!=null &&
				arena.getResultSpot2()!=null ? true : false;
		
	}
	
	public void reward(SSArena arena) {
		
		if (arena.getFirstPlayerChoice().equals("steal") && arena.getSecondPlayerChoice().equals("steal")) {
			
			if (!plugin.getSettingsHandler().getResultStealSound().equals("none")) {
				arena.getSecondPlayer().playSound(arena.getSecondPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultStealSound()), 2F, 1F);
				arena.getFirstPlayer().playSound(arena.getFirstPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultStealSound()), 2F, 1F);
			}
			
			arena.getResultSpot1().getBlock().setType(plugin.getSettingsHandler().getStealMaterial());
			arena.getResultSpot2().getBlock().setType(plugin.getSettingsHandler().getStealMaterial());
			
			spawnFireworks(arena.getResultSpot1(), Color.RED, 0);
			spawnFireworks(arena.getResultSpot2(), Color.RED, 0);
			
			arena.getFirstPlayer().sendMessage(plugin.getSettingsHandler().getBothSteal().replace("%amount%", arena.getAmount()+""));
			arena.getSecondPlayer().sendMessage(plugin.getSettingsHandler().getBothSteal().replace("%amount%", arena.getAmount()+""));
			return;
		}
		
		if (arena.getFirstPlayerChoice().equals("split") && arena.getSecondPlayerChoice().equals("split")) {
			
			if (!plugin.getSettingsHandler().getResultSplitSound().equals("none")) {
				arena.getSecondPlayer().playSound(arena.getSecondPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultSplitSound()), 2F, 1F);
				arena.getFirstPlayer().playSound(arena.getFirstPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultSplitSound()), 2F, 1F);
			}
			
			spawnFireworks(arena.getResultSpot1(), Color.LIME, 0);
			spawnFireworks(arena.getResultSpot2(), Color.LIME, 0);
			
			arena.getResultSpot1().getBlock().setType(plugin.getSettingsHandler().getSplitMaterial());
			arena.getResultSpot2().getBlock().setType(plugin.getSettingsHandler().getSplitMaterial());
			
			int amount=arena.getAmount();
			
			int half=amount/2;
			
			arena.getFirstPlayer().sendMessage(plugin.getSettingsHandler().getBothSplit().replace("%amount%", half+""));
			arena.getSecondPlayer().sendMessage(plugin.getSettingsHandler().getBothSplit().replace("%amount%", half+""));
			
			plugin.getEconomy().depositPlayer(arena.getFirstPlayer(), half);
			plugin.getEconomy().depositPlayer(arena.getSecondPlayer(), half);
			
			return;
		}
		
		if (arena.getFirstPlayerChoice().equalsIgnoreCase("split") && arena.getSecondPlayerChoice().equals("steal")) {
			
			if (!plugin.getSettingsHandler().getResultSplitSound().equals("none"))
				arena.getSecondPlayer().playSound(arena.getSecondPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultSplitSound()), 2F, 1F);
			
			if (!plugin.getSettingsHandler().getResultStealSound().equals("none"))
				arena.getFirstPlayer().playSound(arena.getFirstPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultStealSound()), 2F, 1F);
			
			spawnFireworks(arena.getResultSpot1(), Color.LIME, 0);
			spawnFireworks(arena.getResultSpot2(), Color.RED, 0);
			
			arena.getResultSpot1().getBlock().setType(plugin.getSettingsHandler().getSplitMaterial());
			arena.getResultSpot2().getBlock().setType(plugin.getSettingsHandler().getStealMaterial());
			
			arena.getSecondPlayer().sendMessage(plugin.getSettingsHandler().getYouStole().replace("%amount%", arena.getAmount()+"").replace("%player%", arena.getFirstPlayer().getName()));
			
			arena.getFirstPlayer().sendMessage(plugin.getSettingsHandler().getHeStole().replace("%player%", arena.getSecondPlayer().getName()).replace("%amount%", arena.getAmount()+""));
			
			plugin.getEconomy().depositPlayer(arena.getSecondPlayer(), arena.getAmount());
			
			return;
			
		}
		
		if (arena.getFirstPlayerChoice().equals("steal") && arena.getSecondPlayerChoice().equals("split")) {
			
			if (!plugin.getSettingsHandler().getResultStealSound().equals("none"))
				arena.getSecondPlayer().playSound(arena.getSecondPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultStealSound()), 2F, 1F);
			
			if (!plugin.getSettingsHandler().getResultSplitSound().equals("none"))
				arena.getFirstPlayer().playSound(arena.getFirstPlayer().getLocation(), Sound.valueOf(plugin.getSettingsHandler().getResultSplitSound()), 2F, 1F);
			
			spawnFireworks(arena.getResultSpot1(), Color.RED, 0);
			spawnFireworks(arena.getResultSpot2(), Color.LIME, 0);
			
			arena.getResultSpot1().getBlock().setType(plugin.getSettingsHandler().getStealMaterial());
			arena.getResultSpot2().getBlock().setType(plugin.getSettingsHandler().getSplitMaterial());
			
			arena.getFirstPlayer().sendMessage(plugin.getSettingsHandler().getYouStole().replace("%amount%", arena.getAmount()+"").replace("%player%", arena.getSecondPlayer().getName()));
			
			arena.getSecondPlayer().sendMessage(plugin.getSettingsHandler().getHeStole().replace("%player%", arena.getFirstPlayer().getName()).replace("%amount%", arena.getAmount()+""));
			
			plugin.getEconomy().depositPlayer(arena.getFirstPlayer(), arena.getAmount());
			
			return;
			
		}
		
		return;
		
	}
	
	public void openMenu(Player player) {
		
		player.openInventory(plugin.getMenu().getMenu());
		
		if (plugin.getSettingsHandler().getMenuOpenSound()!=null) player.playSound(player, plugin.getSettingsHandler().getMenuOpenSound(), 2F, 1F);
		
	}
	
	public SSArena isChoiceButton(Block block) {
		
		if (plugin.getArenas().isEmpty()) return null;
		
		if (!block.getType().toString().endsWith("_BUTTON")) return null;
		
		return plugin.getArenas().stream().filter(arena -> arena.getChooseButtons().contains(block.getLocation())).findAny().orElse(null);
		
	}
	
	public boolean inArena(Player o) {
		
		if (plugin.getArenas().isEmpty()) return false;
		
		for (SSArena arena:plugin.getArenas()) {
			
			if (arena.getFirstPlayer()==o || arena.getSecondPlayer()==o) return true;
			
		}
		
		return false;
		
	}
	
	public SSArena getArena(Player o) {
		if (plugin.getArenas().isEmpty()) return null;
		for (SSArena arena:plugin.getArenas()) {
			if (arena.getFirstPlayer()==o || arena.getSecondPlayer()==o) return arena;
		}
		return null;
	}
	
	public Player getOtherPlayer(SSArena arena, Player player) {
		return arena.getFirstPlayer()==player ? arena.getSecondPlayer() : arena.getFirstPlayer();
	}
	
	public boolean isFirst(SSArena arena, Player player) {
		return arena.getFirstPlayer()==player ? true : false;
	}
	
	public void deleteArena(SSArena arena) {
		
		plugin.getArenas().remove(arena);
		
		plugin.getData().set("arenas."+arena.getName(), null);
		
		arena=null;
		
		plugin.getData().save();
		
	}
	
	public void spawnFireworks(Location location, Color color, int amount){
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
        
        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(color).flicker(true).build());
       
        fw.setFireworkMeta(fwm);
        fw.detonate();
       
        for(int i = 0;i<amount; i++){
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }
	
}
