package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.File;  
import java.io.IOException;
import java.io.FileWriter;

public class CountCommitsPerAuthorPlugin implements AnalyzerPlugin {
    private final Configuration configuration;
    private Result result;

    public CountCommitsPerAuthorPlugin(Configuration generalConfiguration) {
        this.configuration = generalConfiguration;
    }

    static Result processLog(List<Commit> gitLog) {
        var result = new Result();
        for (var commit : gitLog) {
            var nb = result.commitsPerAuthor.getOrDefault(commit.author, 0);
            result.commitsPerAuthor.put(commit.author, nb + 1);
        }
        return result;
    }

    @Override
    public void run() {
        result = processLog(Commit.parseLogFromCommand(configuration.getGitPath()));
    }

    @Override
    public Result getResult() {
        if (result == null) run();
        return result;
    }

    static class Result implements AnalyzerPlugin.Result {
        private final Map<String, Integer> commitsPerAuthor = new HashMap<>();

        Map<String, Integer> getCommitsPerAuthor(){
            return commitsPerAuthor;
        }

        @Override
        public String getPluginName(){
            return "CommitsPerAuthor";
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }
        
        @Override
        public String getResultsAsListe() {
            String Ret = "";
            for (var item : commitsPerAuthor.entrySet())
            {
                Ret = Ret + item.getKey() + " = " + String.valueOf(item.getValue()) + "\n";
            }
            return Ret;
        }

        @Override
        public String getResultAsHtmlList() {
            StringBuilder html = new StringBuilder("<!DOCTYPE HTML><html><head><title>Commits count per author</title></head><body><div>Commits per author: <ul>");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("<li>").append(item.getKey()).append(": ").append(item.getValue()).append("</li>");
            }
            html.append("</ul></div></body></html>");
            return html.toString();
        }

        @Override
        public String getResultAsHtmlCycleDiagram() {
            StringBuilder html = new StringBuilder("<!DOCTYPE HTML><html><head>");
            html.append("<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>");
            html.append("<script type=\"text/javascript\">");
            html.append("window.onload = function () { var chart = new CanvasJS.Chart(\"chartContainer\", {title:{ text: \"Commits per author:\"},data: [{type: \"pie\",dataPoints: [");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("{ label: \"").append(item.getKey()).append("\" , y:").append(item.getValue()).append("  },");
            }
            html.append("] } ] }); chart.render(); } </script> </head> <body> <div id=\"chartContainer\" style=\"height: 600px; width: 100%;\"></div> </body> </html>");
            return html.toString();
        }
    }
}
