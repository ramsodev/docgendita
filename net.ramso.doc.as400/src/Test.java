import net.ramso.doc.as400.generator.ProcessGenerator;
import net.ramso.doc.as400.utils.ConnectionManager;

import org.eclipse.core.resources.IFolder;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.Job;


public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ConnectionManager.createConnection("10.92.5.6", "JESCUDERO",
				"ESCUDERO", "ALMACEN");
		Job[] js = ConnectionManager.getConnection().getJobs(AS400.COMMAND);
//		RunExtractors.run("GDTEMP",true, false);
		IFolder folder = null;
		ProcessGenerator p = new ProcessGenerator(new String[]{"ALMACEN"}, folder);
		p.setSystem("10.92.5.6");
		p.setUser("JESCUDERO");
		p.setPassword("ESCUDERO");
		p.setLib("GDTEMP");
		p.process(null);
	}

}
