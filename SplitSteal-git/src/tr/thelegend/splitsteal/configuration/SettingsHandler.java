package tr.thelegend.splitsteal.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import tr.thelegend.splitsteal.Main;
import tr.thelegend.splitsteal.Util;

public class SettingsHandler {
	
	private Container container;
	private Util util;
	private Main plugin;
	
	public SettingsHandler(Container container, Main plugin) {
		this.container=container;
		this.plugin=plugin;
		this.util=plugin.getUtil();
	}
	
	public String getNoPerm() {
		return util.hex(container.noPerm);
	}
	
	public String getOnlyInGamme() {
		return util.hex(container.onlyInGame);
	}
	
	public String playerNotFound() {
		return util.hex(container.playerNotFound);
	}
	
	public ArrayList<String> getCommandsList() {
		ArrayList<String> cmds=new ArrayList<String>();
		for (String s:container.commands) {
			cmds.add(util.hex(s));
		}
		return cmds;
	}
	
	public String getSetupCreateUsage() {
		return util.hex(container.setupCreateUsage);
	}
	
	public String getNotUniqueName() {
		return util.hex(container.notUniqueName);
	}
	
	public ArrayList<String> getArenaCreated() {
		ArrayList<String> lst=new ArrayList<String>();
		for (String s:container.arenaCreated) {
			lst.add(util.hex(s));
		}
		return lst;
	}
	
	public String getArenaNotFound() {
		return util.hex(container.arenaDoesntExists);
	}
	
	public String getPos1Set() {
		return util.hex(container.pos1Set);
	}
	
	public String getPos1Usage() {
		return util.hex(container.setPos1Usage);
	}
	
	public String getPos2Set() {
		return util.hex(container.pos2Set);
	}
	
	public String getPos2Usage() {
		return util.hex(container.setPos2Usage);
	}
	
	public String getSetResult1Usage() {
		return util.hex(container.setResult1Usage);
	}
	
	public String getSetResult2Usage() {
		return util.hex(container.setResult2Usage);
	}
	
	public String getResult1Set() {
		return util.hex(container.result1Set);
	}
	
	public String getResult2Set() {
		return util.hex(container.result2Set);
	}
	
	public String getInvalidBlock() {
		return util.hex(container.invalidBlock);
	}
	
	public String getArenaUsage() {
		return util.hex(container.arenaUsage);
	}
	
	public ArrayList<String> getArenaCheck() {
		ArrayList<String> lst=new ArrayList<String>();
		for (String s:container.arenaCheck) {
			lst.add(util.hex(s));
		}
		return lst;
	}
	
	public String getSet() {
		return util.hex(container.set);
	}
	
	public String getNotSet() {
		return util.hex(container.notSet);
	}
	
	public String getActive() {
		return util.hex(container.active);
	}
	
	public String getNotActive() {
		return util.hex(container.notActive);
	}
	
	public String getDeleteUsage() {
		return util.hex(container.deleteUsage);
	}
	
	public String getDeleted() {
		return util.hex(container.deleted);
	}
	
	public String getSendUsage() {
		return util.hex(container.sendUsage);
	}
	
	public String getInvalidNumber() {
		return util.hex(container.invalidNumber);
	}
	
	public String getArenaOccupied() {
		return util.hex(container.arenaOccupied);
	}
	
	public Sound getTeleportSound() {
		return container.teleportSound.equals("none") ? null : Sound.valueOf(container.teleportSound);
	}
	
	public String getMenuTitle() {
		return util.hex(container.menuTitle);
	}
	
	public Integer getMenuSize() {
		return container.menuSize;
	}
	
	public HashMap<Integer,ItemStack> getMenuItems() {
		HashMap<Integer,ItemStack> items=new HashMap<>();
		Set<String> set=container.menuItemsSet.getKeys(false);
		for (String s:set) {
			ItemStack item=new ItemStack(Material.valueOf(plugin.getResource().getString("menus.items."+s+".material")),
					plugin.getResource().getInt("menus.items."+s+".amount"));
			ItemMeta meta=item.getItemMeta();
			meta.setDisplayName(util.hex(plugin.getResource().getString("menus.items."+s+".display_name")));
			List<String> lore=plugin.getResource().getStringList("menus.items."+s+".lore");
			ArrayList<String> colored=new ArrayList<String>();
			for (String s1:lore) {
				colored.add(util.hex(s1));
			}
			meta.setLore(colored);
			NamespacedKey key=new NamespacedKey(plugin,"function");
			meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, plugin.getResource().getString("menus.items."+s+".function"));
			item.setItemMeta(meta);
			items.put(plugin.getResource().getInt("menus.items."+s+".slot"), item);	
		}
		return items;
	}
	
	public boolean isStartTitleEnabled() {
		return container.startTitleEnabled;
	}
	
	public String getStartTitle() {
		return util.hex(container.startTitle);
	}
	
	public String getStartSubtitle() {
		return util.hex(container.startSubtitle);
	}
	
	public boolean isStartMessageEnabled() {
		return container.startMsgEnabled;
	}
	
	public ArrayList<String> getStartMessage() {
		ArrayList<String> msg=new ArrayList<String>();
		for (String s:container.startMsg) {
			msg.add(util.hex(s));
		}
		return msg;
	}
	
	public int getSSTimeLimit() {
		return container.ssEndIn;
	}
	
	public Sound getMenuOpenSound() {
		return container.menuOpenSound.equals("none") ? null : Sound.valueOf(container.menuOpenSound);
	}
	
	public String getButtonAdded() {
		return util.hex(container.butttonAdded);
	}
	
	public String getStealSound() {
		return container.stealSound;
	}
	
	public String getSplitSound() {
		return container.splitSound;
	}
	
	public String getChosenSplit() {
		return util.hex(container.splitChosen);
	}
	
	public String getChosenSteal() {
		return util.hex(container.stealChosen);
	}
	
	public boolean isChangeOfHeartEnabled() {
		return container.changeOfHeart;
	}
	
	public String getNoChangeOfHeart() {
		return util.hex(container.noChangeOfHeart);
	}
	
	public String getNoChangeOfHeartSound() {
		return container.noChangeOfHeartSound;
	}
	
	public Material getSplitMaterial() {
		return Material.valueOf(container.splitBlock);
	}
	
	public Material getStealMaterial() {
		return Material.valueOf(container.stealBlock);
	}
	
	public String getResultSplitSound() {
		return container.resultSplitSound;
	}
	
	public String getResultStealSound() {
		return container.resultStealSound;
	}
	
	public String getSetSpawnUsage() {
		return util.hex(container.setSpawnUsage);
	}
	
	public String getSpawnSet() {
		return util.hex(container.spawnSet);
	}
	
	public List<String> getCommandWhitelist() {
		return container.commandWhitelist;
	}
	
	public String getCommandBlocked() {
		return util.hex(container.commandBlocked);
	}
	
	public String getYouCantDoIt() {
		return util.hex(container.youCantDoIt);
	}
	
	public String getCanceledNoChoice() {
		return util.hex(container.canceledNoChoice);
	}
	
	public String getCanceledNoOneChose() {
		return util.hex(container.noChoice);
	}
	
	public int getCountdownStartUnder() {
		return container.countdownStart;
	}
	
	public String getCountdownSound() {
		return container.countdownSound;
	}
	
	public String getArenaNotComplete() {
		return util.hex(container.arenaNotComplete);
	}
	
	public String getHeChosedSplit() {
		return util.hex(container.choseSplit);
	}
	
	public String getHeChosedSteal() {
		return util.hex(container.choseSteal);
	}
	
	public String getBothSteal() {
		return util.hex(container.bothSteal);
	}
	
	public String getBothSplit() {
		return util.hex(container.bothSplit);
	}
	
	public String getYouStole() {
		return util.hex(container.youStole);
	}
	
	public String getHeStole() {
		return util.hex(container.heStole);
	}
	
}
