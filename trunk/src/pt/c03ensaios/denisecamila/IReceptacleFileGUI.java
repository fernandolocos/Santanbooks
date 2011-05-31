package pt.c03ensaios.denisecamila;

import pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.denisecamila.IReceptacleFileGUI>")
public interface IReceptacleFileGUI extends IReceptacle{
    public void connect(IFileGUIComponent file);
}