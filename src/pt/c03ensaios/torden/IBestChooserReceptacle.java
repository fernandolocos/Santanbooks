package pt.c03ensaios.torden;

import pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser;
import anima.component.IReceptacle;

public interface IBestChooserReceptacle extends IReceptacle {
	
	public void connect(IBestChooser questionChooser);
	
}