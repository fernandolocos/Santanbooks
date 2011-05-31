package pt.c03ensaios.denisecamila;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.denisecamila.IReceptacleAnimalPix>")
public interface IReceptacleAnimalPix extends IReceptacle{
    public void connect(IAnimalPix pix);
}
