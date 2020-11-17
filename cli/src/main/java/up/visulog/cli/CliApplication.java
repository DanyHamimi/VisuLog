package up.visulog.cli;
import up.visulog.gitrawdata.Commit;

public class CliApplication {

	public static void main(String[] args){
		Commit c = new Commit("toto","plage","22/02","23");
		c.getCommit();

	}
}
