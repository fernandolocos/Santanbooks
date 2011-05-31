package pt.c03ensaios.fabsouza.FileManagerGUI;

import pt.c03ensaios.proj01.ITextFilesManager;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * @author Fabiani
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fabsouza.TxtFilesManager.IFileManagerNaxxramas>")
public interface IFileManager extends ISupports
{    
    public void connect(ITextFilesManager provider);
}
