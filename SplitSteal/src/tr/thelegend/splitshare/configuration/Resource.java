package tr.thelegend.splitshare.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Resource extends YamlConfiguration {
 
    private final File file;
 
    public Resource(Plugin plugin, String name) {
     
        file = new File(plugin.getDataFolder(), name);
     
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
     
        if (!file.exists()) {
            plugin.saveResource(name, true);
        }
     
    }
 
    public void load() {
     
        try {
            super.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
     
    }
 
    public void save() {
     
        try {
            super.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
     
    }
 
    public File getFile() { return file; }
 
}