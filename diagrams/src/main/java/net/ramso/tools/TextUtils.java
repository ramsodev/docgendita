package net.ramso.tools;

public class TextUtils {

	public static String clean(String input) {
		String output = input.replaceAll("[^0-9a-zA-Z_.]", "_");
		return output;
	}

	public static String cleanText(String input) {
		return input.replaceAll("\\p{Cntrl}", "");
	}

	public static String cleanTextUrl(String input) {
		String output = input.replaceAll("[^0-9a-zA-Z_.:/]", "_");
		return output;
	}

	public static String createMultilineString(String[] text) {
		String string = "";
		for (int i = 0; i < text.length; i++) {
			string += text[i];
			if (i < text.length - 1) {
				string += "\n";
			}
		}
		return string;
	}
}
