package up.visulog.config;

import java.nio.file.*;
//import java.io.*;
import java.util.*;
import java.io.InputStream;
import java.util.Properties.*;


// TODO: define what this type should be (probably a Map: settingKey -> settingValue)
public class PluginConfig {
    private Properties prop;

    public PluginConfig() {
        this.prop = null;
    }

    public PluginConfig(String ConfigPath) {
        try
        {
            InputStream file = Files.newInputStream(Path.of(ConfigPath));
            this.prop = new Properties();
            this.prop.load(file);
        }
        catch (Exception e)
        {
            this.prop = null;
            System.out.println("Error: can't load file: " + ConfigPath);
        }
    }

    public String getValue (String KeyValue) {
        if (this.prop != null)
        {
            return this.prop.getProperty(KeyValue);
        }
        return null;
    }
}
