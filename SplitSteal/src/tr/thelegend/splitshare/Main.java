package tr.thelegend.splitshare;

import java.util.ArrayList;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import tr.thelegend.splitshare.commands.Splitshare;
import tr.thelegend.splitshare.configuration.Container;
import tr.thelegend.splitshare.configuration.Menu;
import tr.thelegend.splitshare.configuration.Resource;
import tr.thelegend.splitshare.configuration.SettingsHandler;
import tr.thelegend.splitshare.listeners.Choose;
import tr.thelegend.splitshare.listeners.Protection;
import tr.thelegend.splitshare.objects.SSArena;

public class Main extends JavaPlugin {
	
	private Resource config;
	private Resource data;
	
	private SettingsHandler settingsHandler;
	private Container container;
	
	private ArrayList<SSArena> arenas;
	
	private static Main plugin;
	private static Util util;
	private static Menu menu;
	
	private Economy econ=null;
	
	public void onEnable() {
		
		plugin=this;
		util=new Util(this);
		config=new Resource(this,"config.yml");
		config.load();
		data=new Resource(this,"data.yml");
		data.load();
		container=new Container(config);
		settingsHandler=new SettingsHandler(container, plugin);
		menu=new Menu(this);
		
		arenas=util.loadArenas(data);
		
		getCommand("splitshare").setExecutor(new Splitshare(this));
		getServer().getPluginManager().registerEvents(new Choose(this), this);
		getServer().getPluginManager().registerEvents(new Protection(this), this);
		
		if (!setupEconomy()) {
			getServer().getLogger().severe("[Split/Steal] We need Vault and a proper economy to work. Disabling.");
			this.setEnabled(false);
		}
		
	}
	
	public void onDisable() {
		
		util.saveArenas(arenas, data);
		
	}
	
	public Resource getResource() {
		return config;
	}
	
	public Main getPlugin() {
		return plugin;
	}

	public SettingsHandler getSettingsHandler() {
		return settingsHandler;
	}
	
	public ArrayList<SSArena> getArenas() {
		return arenas;
	}
	
	public Util getUtil() {
		return util;
	}
	
	public Resource getData() {
		return data;
	}

	public Menu getMenu() {
		return menu;
	}
	
	public Economy getEconomy() {
		return econ;
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
}
