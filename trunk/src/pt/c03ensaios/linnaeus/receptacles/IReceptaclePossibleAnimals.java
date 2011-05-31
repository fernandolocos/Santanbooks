package pt.c03ensaios.linnaeus.receptacles;

import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.linnaeus.receptacles.IReceptaclePossibleAnimals>")
public interface IReceptaclePossibleAnimals extends IReceptacle{
	public void connect(IPossibleAnimalsHash PossibleAnimals);
}
