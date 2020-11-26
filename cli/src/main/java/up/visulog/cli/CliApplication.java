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
import java.net.URL; 
import java.net.HttpURLConnection;
import java.net.MalformedURLException; 

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

	public static int getResponseCode(String urlString) throws MalformedURLException, IOException {
		URL u = new URL(urlString); 
		HttpURLConnection huc =  (HttpURLConnection)  u.openConnection(); 
		huc.setRequestMethod("GET"); 
		huc.connect(); 
		return huc.getResponseCode();
	}

	public static boolean isValidURL(String url) throws MalformedURLException{ 
        try { 
            new URL(url).toURI(); 
            return true; 
        } 
          
        catch (Exception e) { 
            return false; 
        } 
	} 

	public static boolean check_all_url(String url) throws MalformedURLException, IOException  {
		if(url.contains("gitlab.com") || url.contains("github.com")){
			if(isValidURL(url) && getResponseCode(url) != 404){
				return true;
			}
		}
		return false;
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
	public static  String getResultAsHtmlBarDiagram(LinkedHashMap<String, Integer> InfoCom) {
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

		for (var item : InfoCom.entrySet()) {
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

	public static String getResultAsHtmlCycleDiagram(LinkedHashMap<String, Integer> InfoCom) {
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
		for (var item : InfoCom.entrySet()) {
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
		if(check_all_url(args[0])){
			com.CloneRep(args[0]);
		}else{
			com.CloneRep("https://gaufre.informatique.univ-paris-diderot.fr/filipsudol/visulog/");
		}
		com.getCommit();
		com.printCommit();
		LinkedHashMap<String , Integer> InfoCom =  com.gethmap();
		if(args[1].equals("cicle")){
			getResultAsHtmlCycleDiagram(InfoCom);
		}else if(args[1].equals("bar")){
			getResultAsHtmlBarDiagram(InfoCom);
		}else{
			getResultAsHtmlDiv(InfoCom);
		}
		
	}
}
