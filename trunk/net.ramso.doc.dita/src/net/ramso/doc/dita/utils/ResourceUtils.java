/**
 * 
 */
package net.ramso.doc.dita.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

/**
 * @author ramso
 */
public class ResourceUtils {
	/**  */
	private final static ResourceUtils	save							= new ResourceUtils();
	private static final String			DITA_FILE_EXTENSION				= "dita";
	private static final String			ALTERNATE_DITA_FILE_EXTENSION	= "ditamap";
	private static final String			UTF8							= "UTF-8";

	/**  */
	private ResourceUtils() {
	}

	/**
	 * @return the instance to use when you want to save a DDL Document
	 */
	public static ResourceUtils getInstance() {
		return save;
	}

	/**
	 * Will check to see if the document can be saved as an eclipse resource
	 * 
	 * @return true if the document can be saved as an Eclipse Resource
	 */
	private IFile shouldSaveAsResource(String filename) {
		if (filename != null) {
			Path thePath = new Path(filename);
			IFile theFile = ResourcesPlugin.getWorkspace().getRoot()
					.getFileForLocation(thePath);
			return theFile;
		}
		else {
			return null;
		}
	}

	public IFile saveDitaFileAsResource(StringWriter out, String filename) {
		IPath thePath = new Path(filename);
		if ((thePath.getFileExtension() == null)
				|| (!(thePath.getFileExtension().equalsIgnoreCase(
						DITA_FILE_EXTENSION) || thePath.getFileExtension()
						.equalsIgnoreCase(ALTERNATE_DITA_FILE_EXTENSION)))) {
			thePath = thePath.addFileExtension(DITA_FILE_EXTENSION);
		}
		IFile theFile = ResourcesPlugin.getWorkspace().getRoot().getFile(
				thePath);
		OutputStream file = null;
		try {
			file = new ByteArrayOutputStream();
			// Force to save in utf8
			String encoding = UTF8;
			if (encoding != null && !encoding.equals("")) {
				file.write(out.toString().getBytes(encoding));
			}
			else {
				file.write(out.toString().getBytes());
			}
			saveDocumentAsResource(theFile, file);
		}
		catch (FileNotFoundException e) {
		}
		catch (IOException e) {
		}
		finally {
			if (file != null) {
				try {
					file.close();
				}
				catch (IOException e1) {
				}
			}
		}
		return theFile;
	}

	/**
	 * Will save a DDL Document for you
	 * 
	 * @param document
	 *            , the document to save
	 */
	public void saveDitaFile(StringWriter out, String filename) {
		OutputStream file = null;
		try {
			if (shouldSaveAsResource(filename) == null) {
				File newFile = new File(filename);
				if (newFile.exists() && !newFile.canWrite()
						&& !makeCheckOut(filename)) {
					return;
				}
				file = new FileOutputStream(newFile);
				// Force to save in utf8
				String encoding = UTF8;
				if (encoding != null && !encoding.equals("")) {
					file.write(out.toString().getBytes(encoding));
				}
				else {
					file.write(out.toString().getBytes());
				}
			}
			else {
				file = new ByteArrayOutputStream();
				// Force to save in utf8
				String encoding = UTF8;
				if (encoding != null && !encoding.equals("")) {
					file.write(out.toString().getBytes(encoding));
				}
				else {
					file.write(out.toString().getBytes());
				}
				saveDocumentAsResource(shouldSaveAsResource(filename), file);
			}
		}
		catch (FileNotFoundException e) {
		}
		catch (IOException e) {
		}
		finally {
			if (file != null) {
				try {
					file.close();
				}
				catch (IOException e1) {
				}
			}
		}
	}

	/**
	 *@param IFile
	 *            theFile that will be written out
	 *@param Writer
	 *            the String Writer that will be writen out
	 */
	private void saveDocumentAsResource(IFile theFile, OutputStream writer) {
		if (theFile == null) {
			return;
		}
		
		InputStream input = null;
		try {
			// read the bytes in the outputStreamWriter
			input = new ByteArrayInputStream(((ByteArrayOutputStream) writer)
					.toByteArray());
			if (theFile.exists()) {
				if (theFile.isReadOnly()) {
					// Check out the file - ME TODO
					if (true) {
						theFile.setContents(input, true, true, null);
					}
				}
				else {
					theFile.setContents(input, true, true, null);
				}
			}
			else {
				theFile.create(input, false, null);
			}
			theFile.setCharset(UTF8, null);
		}
		catch (Exception e) {
		}
		finally {
			try {
				input.close();
			}
			catch (Exception e) {
			}
		}
	}

	/**
	 * @return true if the file should be checked out
	 */
	private boolean makeCheckOut(String fileName) {
		return false;
	}
}
