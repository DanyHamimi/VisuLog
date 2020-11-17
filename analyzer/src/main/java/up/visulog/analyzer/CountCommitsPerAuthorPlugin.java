package up.visulog.analyzer;

import up.visulog.config.Configuration;
import up.visulog.gitrawdata.Commit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.File;  
import java.io.IOException;
import java.io.FileWriter;

import java.text.SimpleDateFormat;
import java.util.Date;

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

        Map<String, Integer> getCommitsPerAuthor() {
            return commitsPerAuthor;
        }

        @Override
        public String getResultAsString() {
            return commitsPerAuthor.toString();
        }

        @Override
        public String getResultAsHtmlDiv() {
            StringBuilder html = new StringBuilder("<!DOCTYPE HTML><html><head>");
            html.append("<script src=\"https://canvasjs.com/assets/script/canvasjs.min.js\"></script>");
            html.append("<script type=\"text/javascript\">");
            html.append("window.onload = function () { var chart = new CanvasJS.Chart(\"chartContainer\", {title:{ text: \"Commits per author:\"},data: [{type: \"pie\",dataPoints: [");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("{ label: \"").append(item.getKey()).append("\" , y:").append(item.getValue()).append("  },");
            }
            html.append("] } ] }); chart.render(); } </script> </head> <body> <div id=\"chartContainer\" style=\"height: 600px; width: 100%;\"></div> </body> </html>");
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yy_HH'h'mm");
	            Date date = new Date();
                File myObj = new File(dateFormat.format(date)+"_visulog.html"); // create an html file
                if (myObj.createNewFile()) {
                    System.out.println("HTML File created: " + myObj.getName());
                    System.out.println("The html file has been created in the client folder.");
                } else {
                    System.out.println("File already exists.");
                }
                FileWriter myWriter = new FileWriter(dateFormat.format(date)+"_visulog.html");// write inside the html file
                myWriter.write(html.toString());
                myWriter.close();
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
            return html.toString();
        }
    }
}
