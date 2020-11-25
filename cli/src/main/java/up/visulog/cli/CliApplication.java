package up.visulog.cli;
import up.visulog.gitrawdata.Commit;
import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.util.LinkedHashMap;
import java.io.FileWriter;

// Useful for add Config Option next time
import java.nio.file.FileSystems;
import java.util.HashMap;
// import java.util.Option; not run actually for the moment

public class CliApplication {
	public static void recursiveDelete(File file) {
		if (!file.exists())
			return;
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				recursiveDelete(f);
			}
		}
		file.delete();
	}
	public static String getResultAsHtmlDiv(LinkedHashMap<String, Integer> InfoCom) {
	    StringBuilder html = new StringBuilder("<!DOCTYPE HTML><html><head>");
		html.append("<script src=\"../CanvasJS/canvasjs.min.js\"></script>");
		html.append("<script type=\"text/javascript\">");
		html.append("window.onload = function () { var chart = new CanvasJS.Chart(\"chartContainer\", {title:{ text: \"Commits per author:\"},data: [{type: \"pie\",dataPoints: [");
		for (var item : InfoCom.entrySet()) {
			html.append("{ label: \"").append(item.getKey()).append("\" , y:").append(item.getValue()).append("  },");
		}
		html.append("] } ] }); chart.render(); } </script> </head> <body> <div id=\"chartContainer\" style=\"height: 600px; width: 100%;\"></div> </body> </html>");
		try {
			File myObj = new File("index.html"); // create an html file
			if (myObj.createNewFile()) {
				System.out.println("HTML File created: " + myObj.getName());
				System.out.println("The html file has been created in the client folder.");
			} else {
				System.out.println("File already exists.");
			}
			FileWriter myWriter = new FileWriter("index.html");// write inside the html file
			myWriter.write(html.toString());
			myWriter.close();
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return html.toString();
	}
	public static void main(String[] args) throws IOException {
		String folder = "datagit";
		recursiveDelete(new File(folder));
		File indexhtml= new File("index.html");
		indexhtml.delete();
		Commit com = new Commit("test","test","00/00","0");
		com.CloneRep("https://gaufre.informatique.univ-paris-diderot.fr/filipsudol/visulog");
		com.getCommit();
		com.printCommit();
		LinkedHashMap<String , Integer> InfoCom =  com.gethmap();
		getResultAsHtmlDiv(InfoCom);
		
	}
}
