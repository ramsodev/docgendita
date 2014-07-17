package net.ramso.doc.dita.utils;


public class TextUtils {

	private static String SEARCH = "\\¨º-~#@|!\"·$%&/()?'¡¿[^`]+}{¨´>< ;,:áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ+";
	

	public static String clean(String input) {
		String output = input;
		for (int i = 0; i < SEARCH.length(); i++) {
			output = output.replace(SEARCH.charAt(i), "_".toCharArray()[0]);
		}
		return output;
	}
}
