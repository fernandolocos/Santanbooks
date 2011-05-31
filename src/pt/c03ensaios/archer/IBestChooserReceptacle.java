package pt.c03ensaios.archer;

import pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.archer.IBestChooserReceptacle>")
public interface IBestChooserReceptacle extends IReceptacle {
	public void connect(IBestChooser bestChooser);
}
