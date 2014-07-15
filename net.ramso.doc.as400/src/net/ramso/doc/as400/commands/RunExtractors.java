package net.ramso.doc.as400.commands;

import net.ramso.doc.as400.utils.ConnectionManager;

public class RunExtractors {

	public static String DSPFCMD = "DSPFD FILE(*USRLIBL/*ALL) TYPE(*BASATR) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPFCSTCMD = "DSPFD FILE(*USRLIBL/*ALL) TYPE(*CST) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPFACCCMD = "DSPFD FILE(*USRLIBL/*ALL) TYPE(*ACCPTH) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPFFDCMD = "DSPFFD FILE(*USRLIBL/*ALL) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPDBRCMD = "DSPDBR FILE(*USRLIBL/*ALL) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPOBJDCMD = "DSPOBJD OBJ(*USRLIBL/*ALL) OBJTYPE(*ALL) ASPDEV(*) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPPGMREFCMD = "DSPPGMREF PGM(*USRLIBL/*ALL) OBJTYPE(*ALL) OUTPUT(*OUTFILE) OUTFILE(";
	public static String DSPFFILE = "DSPFD";
	public static String DSPFCSTFILE = "DSPFDCST";
	public static String DSPFACCFILE = "DSPFDACC";
	public static String DSPFFDFILE = "DSPFFD";
	public static String DSPDBRFILE = "DSPDBR";
	public static String DSPOBJDFILE = "DSPOBJD";
	public static String DSPPGMREFFILE = "DSPPGMREF";
	public static String LIB = "GDTEMP";

	public static boolean run(String lib, boolean create, boolean delete){
		LIB = lib;
		boolean status = true;
		if(create){
			ConnectionManager.crtLib(lib);
		}
		status = ConnectionManager.runCmd(createCommand(DSPFCMD,DSPFFILE, lib));
		status = ConnectionManager.runCmd(createCommand(DSPFCSTCMD,DSPFCSTFILE, lib));
		status = ConnectionManager.runCmd(createCommand(DSPFACCCMD,DSPFACCFILE, lib));
		status = ConnectionManager.runCmd(createCommand(DSPFFDCMD,DSPFFDFILE, lib));
		status = ConnectionManager.runCmd(createCommand(DSPDBRCMD,DSPDBRFILE, lib));
		status = ConnectionManager.runCmd(createCommand(DSPOBJDCMD,DSPOBJDFILE, lib));
		status = ConnectionManager.runCmd(createCommand(DSPPGMREFCMD,DSPPGMREFFILE, lib));
		if(delete){
			ConnectionManager.dltLib(lib);
		}
		return status;
	}

	private static String createCommand(String cmd, String file, String lib) {
		return cmd + lib.trim().toUpperCase() + "/" + file + ")";
	}
}
