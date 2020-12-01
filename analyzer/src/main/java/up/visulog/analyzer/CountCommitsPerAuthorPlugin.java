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
        public String getResultsAsListe() {      // used for console output
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
            html.append("<meta charset=\"UTF-8\">");
            html.append("<script>");
            html.append("window.onload = function() {\n" +
                    "var chart = new CanvasJS.Chart(\"chartContainer\", {\n" +
                    "\tanimationEnabled: true,\n" +
                    "\ttitle: {\n" + "\t\ttext: \"Commits per author\"\n" + "\t},\n" +
                    "\tdata: [{\n" +
                    "\t\ttype: \"pie\",\n" +
                    "\t\tstartAngle: 240,\n" +
                    "\t\tindexLabel: \"{label} \",\n" +
                    "\t\tdataPoints: [");
            for (var item : commitsPerAuthor.entrySet()) {
                html.append("\t\t{ y: \"").append(item.getValue()).append("\" , label:\"").append(item.getKey()).append("\"  },\n");
            }

            html.deleteCharAt(html.length()-1); //delete last "\n"
            html.deleteCharAt(html.length()-1); //delete last ","

            html.append("\t\t] } ] });\n" +
                    "chart.render();\n" + "}\n" +
                    "</script>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div id=\"chartContainer\" style=\"height: 600px; width: 100%; margin: 0px auto;\"></div>\n" +
                    "<script src=\"../CanvasJS/canvasjs.min.js\"></script>\n" +
                    "</body>\n" +
                    "</html>");
            return html.toString();
        }

        @Override
        public String getResultAsHtmlBarDiagram() {
            StringBuilder html = new StringBuilder("<!DOCTYPE HTML>\n");
            html.append("<html>\n" +
                        "<head>\n" +
                        "<meta charset=\"UTF-8\">\n" +
                        "<script type=\"text/javascript\">\n" +
                        "window.onload = function () {\n" +
                        "\t\n" +
                        "var chart = new CanvasJS.Chart(\"chartContainer\", \n" +
                        "{\n" +
                        "    animationEnabled: true,\n" +
                        "    title:\n" +
                        "    {\n" +
                        "        text: \"Commits per author\"\n" +
                        "    },\n" + "    axisX: \n" +
                        "    {    \n" +
                        "        margin: 8,\n" +
                        "        interval: 1,\n" +
                        "        labelWrap: false,\n" +
                        "        labelFontSize: 14,\n" +
                        "        labelAutoFit: true,\n"+
                        "        labelPlacement: \"inside\",\n" +
                        "        tickPlacement: \"inside\"\n" +
                        "    },\n" +
                        "    axisY: \n" +
                        "    {\n" +
                        "        title: \"\",\n" +
                        "        titleFontSize: 10,\n" +
                        "        includeZero: true,\n" +
                        "        suffix: \"commits\"\n" +
                        "    },\n" +
                        "    data: \n" +
                        "    [\n" +
                        "        {\n" +
                        "            type: \"bar\",\n" +
                        "            axisYType: \"secondary\",\n" +
                        "            yValueFormatString: \"#,###.##commits\",\n" +
                        "            dataPoints: \n" +
                        "            [\t\n"
            );

            for (var item : commitsPerAuthor.entrySet()) {
                html.append("\t\t{ y: ").append(item.getValue()).append(", label:\"").append(item.getKey()).append("\"  },\n");
            }
            html.deleteCharAt(html.length()-1); //delete last "\n"
            html.deleteCharAt(html.length()-1); //delete last ","
            html.append("\t    ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "});\n" +
                        "\n" +
                        "chart.render();\n" +
                        "}\n" +
                        "</script>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<div id=\"chartContainer\" style=\"height: 600px; max-width: 920px; margin: 0px auto;\"></div>\n" +
                        "<script src=\"../CanvasJS/canvasjs.min.js\"></script>\n" +
                        "</body>\n" +
                        "</html>");

            return html.toString();
        }
    }
}
