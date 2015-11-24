import java.beans.PropertyVetoException;
import java.io.IOException;

import net.ramso.doc.as400.generator.ProcessGenerator;
import net.ramso.doc.as400.utils.ConnectionManager;

import org.eclipse.core.resources.IFolder;

import com.ibm.as400.access.AS400;
import com.ibm.as400.access.AS400SecurityException;
import com.ibm.as400.access.ErrorCompletingRequestException;
import com.ibm.as400.access.Job;


public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			ConnectionManager.createConnection("DEV710.RZKH.DE", "OBTPGM01",
					"ESCUDERO", "OBTGRP1 OBTGRP2");
		} catch (AS400SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorCompletingRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
