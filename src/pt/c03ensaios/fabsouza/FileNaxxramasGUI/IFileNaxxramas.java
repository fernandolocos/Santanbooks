package pt.c03ensaios.fabsouza.FileNaxxramasGUI;

import pt.c03ensaios.hayasida.Handler.IHandler;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * @author Fabiani
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileNaxxramasGUI.IFileNaxxramasGUI>")
public interface IFileNaxxramas extends ISupports
{    
    public void connect(IHandler provider);
}
