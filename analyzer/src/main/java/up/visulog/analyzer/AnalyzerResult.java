package up.visulog.analyzer;

import up.visulog.config.Configuration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AnalyzerResult {
    public List<AnalyzerPlugin.Result> getSubResults() {
        return subResults;
    }

    private final List<AnalyzerPlugin.Result> subResults;

    public AnalyzerResult(List<AnalyzerPlugin.Result> subResults) {
        this.subResults = subResults;
    }

    @Override
    public String toString() {
        return subResults.stream().map(AnalyzerPlugin.Result::getResultsAsListe).reduce("", (acc, cur) -> acc + "\n" + cur);
    }

    public void SaveReports(Configuration.HtmlReportType HtmlType) {

        if (Configuration.HtmlReportType.None == HtmlType)
        {
            return;
        }

        for(AnalyzerPlugin.Result PluginResult : subResults){
            try {
                String FileName = PluginResult.getPluginName() + ".html";
                File FileReport = new File(FileName);
                if (FileReport.exists()) {
                    FileReport.delete();
                }
                FileReport.createNewFile();
                
                FileWriter myWriter = new FileWriter(FileName);
                if (Configuration.HtmlReportType.Bar == HtmlType){
                    myWriter.write(PluginResult.getResultAsHtmlBarDiagram());
                } else if (Configuration.HtmlReportType.Cicle == HtmlType){
                    myWriter.write(PluginResult.getResultAsHtmlCycleDiagram());
                } else if (Configuration.HtmlReportType.List == HtmlType){
                    myWriter.write(PluginResult.getResultAsHtmlList());
                } else {
                    throw new IllegalArgumentException("Unknown argument: " + HtmlType);
                }
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }
}
