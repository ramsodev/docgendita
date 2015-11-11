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
		ConnectionManager.createConnection("DEV710.RZKH.DE", "OBTPGM01",
				"ESCUDERO", "OBTGRP1 OBTGRP2");
		Job[] js = ConnectionManager.getConnection().getJobs(AS400.COMMAND);
//		RunExtractors.run("OBTPGM011",true, false);
		IFolder folder = null;
		ProcessGenerator p = new ProcessGenerator(new String[]{"OBTGRP1","OBTGRP2"}, folder);
		p.setSystem("10.92.5.6");
		p.setUser("JESCUDERO");
		p.setPassword("ESCUDERO");
		p.setLib("OBTPGM011");
		p.process(null);
	}

}
