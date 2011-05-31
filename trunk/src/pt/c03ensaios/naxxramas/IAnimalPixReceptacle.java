package pt.c03ensaios.naxxramas;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import anima.component.IReceptacle;

/**
 * Enables the connection to a component that implements IAnimalPix.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public interface IAnimalPixReceptacle extends IReceptacle {
    public void connect(IAnimalPix arg0);
}
