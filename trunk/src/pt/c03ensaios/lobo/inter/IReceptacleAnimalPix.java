package pt.c03ensaios.lobo.inter;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c03ensaios.afinder.IAnimalPix>")
public interface IReceptacleAnimalPix extends IReceptacle
{
	public void connect(IAnimalPix provider);
}
