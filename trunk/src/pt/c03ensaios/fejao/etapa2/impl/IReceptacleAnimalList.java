package pt.c03ensaios.fejao.etapa2.impl;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import anima.component.IReceptacle;

public interface IReceptacleAnimalList extends IReceptacle{
	public void connect(IAnimalList animalList);
}
