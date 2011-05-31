package pt.c03ensaios.dratini.componentsBasedEnquirer;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import anima.component.IReceptacle;

public interface IReceptacleAnimalLists extends IReceptacle{
	public void connect(IAnimalList lista1, IAnimalList lista2);
}
