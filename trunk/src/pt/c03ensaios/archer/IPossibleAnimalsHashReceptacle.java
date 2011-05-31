package pt.c03ensaios.archer;


import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.archer.IPossibleAnimalsHashReceptacle>")
public interface IPossibleAnimalsHashReceptacle extends IReceptacle {
	public void connect(IPossibleAnimalsHash possibleAnimals);

}
