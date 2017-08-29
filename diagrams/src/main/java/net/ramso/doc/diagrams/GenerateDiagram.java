/**
 * 
 */
package net.ramso.doc.diagrams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.ramso.doc.diagrams.as400.AS400Diagram;
import net.ramso.doc.diagrams.as400.AS400Object;
import net.ramso.doc.diagrams.tools.DiagramConstants;

/**
 * @author jjescudero
 *
 */
public class GenerateDiagram {
	private Options options;
	private File outdir;
	private String imageFormat;
	private File inFile;
	private String separator;
	private boolean headers;
	private boolean template;
	private String layout;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GenerateDiagram gen = new GenerateDiagram();
		try {

			if (gen.parse(args)) {
				gen.gen();
			}
		} catch (org.apache.commons.cli.ParseException | IOException ex) {
			System.out.println(ex.getMessage());
			new HelpFormatter().printHelp(GenerateDiagram.class.getCanonicalName(), gen.options);
		}
		System.exit(1);

	}

	private void gen() throws IOException, ParseException {
		if (template) {
			if (!outdir.exists()) {
				outdir.mkdirs();
			}
			File f = new File(outdir.getAbsolutePath() + File.separator + DiagramConstants.TEMPLATE);
			Path out = f.toPath();
			InputStream in = GenerateDiagram.class.getResourceAsStream("/" + DiagramConstants.TEMPLATE);
			Files.copy(in, out);
			in.close();
			System.out.println("Template generado en :" + f.getAbsolutePath());
		} else if (inFile.exists()) {
			ArrayList<AS400Object> objs = parseFile(inFile);
			AS400Diagram diagram = new AS400Diagram(objs);
			diagram.setLayout(layout);	
			diagram.setFileName(inFile.getName());
			diagram.run();
			
			diagram.save(outdir.getAbsolutePath(), imageFormat);
		} else {
			throw new org.apache.commons.cli.ParseException("El fichero de entrada no existe");
		}
	}

	private ArrayList<AS400Object> parseFile(File in) throws IOException {
		String line;
		BufferedReader br = null;
		ArrayList<AS400Object> objs = new ArrayList<AS400Object>();
		try {
			InputStream fis = new FileInputStream(in);
			InputStreamReader isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			if (headers)
				br.readLine();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(separator);
				AS400Object object = new AS400Object();
				object.setName(data[0].trim());
				object.setType(data[2].trim());
				object.setAttribute(data[3].trim());
				object.setLib(data[1].trim());
				object.setParent(data[4].trim());
				objs.add(object);
			}
		} finally {
			br.close();
		}
		return objs;
	}

	public boolean parse(String[] args) throws ParseException {
		options = new Options();
		Option o = new Option("outdir", true, "Directorio de destino");
		o.setRequired(true);
		o.setType(File.class);
		options.addOption(o);
		o = new Option("inFile", true, "Fichero CSV con los datos a procesar");
		o.setRequired(true);
		o.setType(File.class);
		options.addOption(o);
		o = new Option("imageFormat", true, "Formato de imagen del diagrama (PNG o SVG)");
		o.setRequired(false);
		o.setType(String.class);
		options.addOption(o);
		o = new Option("separator", true, "Caracter separador de campos para CSV. Por defecto es ','");
		o.setRequired(false);
		o.setType(String.class);
		options.addOption(o);
		o = new Option("layout", true,
				"Tipo de layout a usar. Valores posibles organic (por defecto), circular, tree_horizontal, tree_vertical");
		o.setRequired(false);
		o.setType(String.class);
		options.addOption(o);
		o = new Option("headers", "La primera linea del CSV es de cabeceras");
		o.setRequired(false);
		options.addOption(o);
		o = new Option("template", "Genera un template en el directorio de destino");
		o.setRequired(false);
		options.addOption(o);

		options.addOption("h", "help", false, "Imprime el mensaje de ayuda");

		DefaultParser parser = new DefaultParser();
		CommandLine cmdLine = parser.parse(options, args);

		if (cmdLine.hasOption("h")) {
			new HelpFormatter().printHelp(GenerateDiagram.class.getCanonicalName(), options);
			return false;
		}

		if (cmdLine.hasOption("outdir")) {
			outdir = (File) cmdLine.getParsedOptionValue("outdir");
		} else {
			throw new org.apache.commons.cli.ParseException("El directorio de salida es obligatorio");
		}
		if (cmdLine.hasOption("inFile")) {
			inFile = (File) cmdLine.getParsedOptionValue("inFile");
		} else {
			throw new org.apache.commons.cli.ParseException("El fichero de entrada es obligatorio");
		}
		imageFormat = DiagramConstants.EXPORT_PNG;
		if (cmdLine.hasOption("imageFormat")) {
			imageFormat = (String) cmdLine.getParsedOptionValue("imageFormat");
			imageFormat = imageFormat.toLowerCase();
			if (!imageFormat.equals(DiagramConstants.EXPORT_PNG) && !imageFormat.equals(DiagramConstants.EXPORT_SVG)) {
				throw new org.apache.commons.cli.ParseException(
						"El formato de imagen de salida solo puede ser PNG o SVG");
			}
		}
		separator = DiagramConstants.COMA;
		if (cmdLine.hasOption("separator")) {
			separator = (String) cmdLine.getParsedOptionValue("separator");
		}
		layout = DiagramConstants.LAYOUT_ORGANIC;
		if (cmdLine.hasOption("layout")) {
			layout = (String) cmdLine.getParsedOptionValue("layout");
			layout = layout.toLowerCase();
			switch (layout) {
			case DiagramConstants.LAYOUT_CIRCULAR:
			case DiagramConstants.LAYOUT_ORGANIC:
			case DiagramConstants.LAYOUT_TREE_H:
			case DiagramConstants.LAYOUT_TREE_V:
				break;
			default:
				throw new org.apache.commons.cli.ParseException("Tipo de layout no soportado");
			}
		}
		headers = false;
		if (cmdLine.hasOption("headers"))
			headers = true;
		template = false;
		if (cmdLine.hasOption("template"))
			template = true;
		return true;
	}

}
