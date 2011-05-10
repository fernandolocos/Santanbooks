package pt.c03ensaios.fejao;

import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fejao.IReceptacleAnimalsDatabase>")
public interface IReceptacleAnimalsDatabase extends IReceptacle{
	public void connect(IAnimalsDatabase base);
}
