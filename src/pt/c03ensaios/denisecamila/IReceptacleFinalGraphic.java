package pt.c03ensaios.denisecamila;

import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.denisecamila.IReceptacleSearcher>")
public interface IReceptacleFinalGraphic extends IReceptacle{
    public void connect(IFinalGraphic finalGraphic);
}