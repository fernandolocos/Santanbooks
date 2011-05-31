package pt.c03ensaios.afinder.animalinfo;

import pt.c03ensaios.relu.ISearcher;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.relu.ISearcher>")
public interface IReceptacleSearcher extends IReceptacle {
	
	public void connect(ISearcher s);
}