package pt.c03ensaios.fejao.etapa2.impl;

import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import anima.component.IReceptacle;

public interface IReceptacleAnimalsDatabase extends IReceptacle{
	public void connect(IAnimalsDatabase AnimalsDatabase);
}
