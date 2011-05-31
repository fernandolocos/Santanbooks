package pt.c03ensaios.hayasida.FileTxtHandler;

import java.io.File;
import java.util.Vector;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/** Interface for a FileTxtHandler Component that has basic operations to manage .txt files in a directory. 
 * @author Roberto K. Hayasida Junior
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler>")

public interface IFileTxtHandler extends ISupports{
	public void setDir(String dir);
	public String getDir();
	public Vector<String> open(String filename);
	public void save(Vector<String> text, String filename);
	public Vector<File> listTxtFiles();
	public Vector<String> listTxtFilesNames();
}

