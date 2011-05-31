package pt.c03ensaios.archer;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.archer.IPlayPet>")
public interface IPlayPet extends ISupports{
     public void run();
}
