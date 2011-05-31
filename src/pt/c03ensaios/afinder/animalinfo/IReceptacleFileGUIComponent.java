package pt.c03ensaios.afinder.animalinfo;

import pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>")
public interface IReceptacleFileGUIComponent extends IReceptacle{
	
	public void connect(IFileGUIComponent f);
}