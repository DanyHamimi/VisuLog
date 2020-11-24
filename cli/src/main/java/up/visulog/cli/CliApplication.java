package up.visulog.cli;
import up.visulog.gitrawdata.Commit;
import java.io.IOException;


public class CliApplication {

	public static void main(String[] args) throws IOException {
		Commit c = new Commit("toto","plage","22/02","23");
		c.getCommit();
		//c.CloneRep();

	}
}
