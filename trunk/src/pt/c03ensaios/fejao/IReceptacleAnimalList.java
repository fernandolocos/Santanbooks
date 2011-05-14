package pt.c03ensaios.fejao;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalList>")
public interface IReceptacleAnimalList extends IReceptacle{
	public void connect(IAnimalList animalList);
}
