package pt.c03ensaios.fabsouza.FileGUIComponent;

import pt.c03ensaios.hayasida.Handler.IHandler;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

/**
 * @author Fabiani
 */

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.IFileReceptacle>")
public interface IFileReceptacle extends IReceptacle
{
    public void connect(IHandler provider);
}
