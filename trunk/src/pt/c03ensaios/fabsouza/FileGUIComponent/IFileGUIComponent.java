package pt.c03ensaios.fabsouza.FileGUIComponent;

import anima.annotation.ComponentInterface;

/**
 * @author Fabiani
 */

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent>")
public interface IFileGUIComponent extends IFileReceptacle
{    
    public void showFrame();
}
