package pt.c03ensaios.lobo.inter;

import pt.c03ensaios.debolacha.inter.IAnimalFile;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalFile>")
public interface IReceptacleAnimalFile extends IReceptacle
{
	public void connect(IAnimalFile provider);
}
