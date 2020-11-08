package up.visulog.analyzer;

import java.lang.module.Configuration;

public interface AnalyzerPlugin {
    interface Result {
        String getPluginName();
        String getResultAsString();
        String getResultsAsListe();
        String getResultAsHtmlList();
        String getResultAsHtmlCycleDiagram();
        //String getResultAsHtmlBarDiagram();
    }

    /**
     * run this analyzer plugin
     */
    void run();

    /**
     *
     * @return the result of this analysis. Runs the analysis first if not already done.
     */
    Result getResult();
}
