package pt.c03ensaios.denisecamila;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.denisecamila.IFinalGraphic>")
public interface IFinalGraphic extends ISupports{
   
    public void exibe (String animal);

}