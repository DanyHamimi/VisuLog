package up.visulog.config;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Configuration {

    public enum HtmlReportType
    {
        List,
        Cicle,
        Bar,
        None
    }

    private final Path gitPath;
    private final Map<String, PluginConfig> plugins;
    private final HtmlReportType HtmlType;

    public Configuration(Path gitPath, Map<String, PluginConfig> plugins, HtmlReportType HtmlType) {
        this.gitPath = gitPath;
        this.plugins = Map.copyOf(plugins);
        this.HtmlType = HtmlType;
    }

    public Path getGitPath() {
        return gitPath;
    }

    public HtmlReportType getHtmlType() {
        return HtmlType;
    }

    public Map<String, PluginConfig> getPluginConfigs() {
        return plugins;
    }
}
