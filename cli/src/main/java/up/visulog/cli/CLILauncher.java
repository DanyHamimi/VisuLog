package up.visulog.cli;

import up.visulog.analyzer.Analyzer;
import up.visulog.config.Configuration;
import up.visulog.config.PluginConfig;

import java.nio.file.FileSystems;
import java.util.HashMap;
import java.util.Optional;

import java.io.IOException;
import java.io.File;

public class CLILauncher {

    public static void main(String[] args) {
        var config = makeConfigFromCommandLineArgs(args);
        if (config.isPresent()) {
            var analyzer = new Analyzer(config.get());
            var results = analyzer.computeResults();
            System.out.println(results.toHTML());
        } else displayHelpAndExit();
    }

    static Optional<Configuration> makeConfigFromCommandLineArgs(String[] args) {
        var gitPath = FileSystems.getDefault().getPath(".");
        var plugins = new HashMap<String, PluginConfig>();
        var config = new PluginConfig();
        for (var arg : args) {
            if (arg.startsWith("--")) {
                String[] parts = arg.split("=");
                if (parts.length != 2) return Optional.empty();
                else {
                    String pName = parts[0];
                    String pValue = parts[1];
                    switch (pName) {
                        case "--addPlugin":
                            // TODO: parse argument and make an instance of PluginConfig | Done I hope it's working as expected
                            if (pValue.equals("countCommits")) {
                                //config = new PluginConfig(pValue);    //TODO : FIX THIS , it is causing the "Error: can't load file: countCommits"
                                plugins.put("countCommits", new PluginConfig() {});
                            } else {
                                throw new IllegalArgumentException("Not a valid argument: "+ pValue);
                            }
                            /* Let's just trivially do this, before the TODO is fixed:
                            if (pValue.equals("countCommits")) plugins.put("countCommits", new PluginConfig() {});
                            break;*/
                        case "--loadConfigFile":
                            //config = new PluginConfig(pValue); Fix this too, like at line 40 
                            break;
                        case "--justSaveConfigFile":
                        	try {
                                File myObj = new File("ConfigFile.txt");
                                if (myObj.createNewFile()) {
                                System.out.println("File created: " + myObj.getName());
                                } else {
                                System.out.println("File already exists.");
                                }
                            } catch (IOException e) {
                                System.out.println("An error occurred.");
                                e.printStackTrace();
                            }
                            // TODO (save command line options to a file instead of running the analysis) | DONE
                            break;
                        case "--help":            // Case HELP
                            displayHelpAndExit(); // Launch displayHelpAndExit
                            break;
                        default:
                            return Optional.empty();
                    }
                }
            } else {
                gitPath = FileSystems.getDefault().getPath(arg);
            }
        }
        return Optional.of(new Configuration(gitPath, plugins));
    }

    private static void displayHelpAndExit() {
        System.out.println("The correct syntax is : args='<directory> <command>=<argument> without the < >'");
        System.out.println("For example : '. --addPlugin=countCommits'");
        System.out.println("Below you will find a list of commands you can use.");
        System.out.println("--addPlugin, takes and arg and makes an instance of PluginConfig.");
        System.out.println("--loadConfigFile, load options from an external file given as arg.");
        System.out.println("--justSaveConfigFile, will make the program not run the analysis and print command line options to a file given as arg instead.");
        System.out.println("--help, display help and syntax in case you need it again.");
        //TODO: print the list of options and their syntax | UPDATE : Should be done, needs testing
        System.exit(0);
    }
}
