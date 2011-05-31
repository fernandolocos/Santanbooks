package pt.c02foundations.fish.s00;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface for the Fish component that enables to trigger the fish drawing.
 * 
 * @author Andre Santanche
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c02foundations.fish.s00.IFish>")
public interface IFish extends ISupports
{
    /**
     * Draw the fish.
     */	
    public String fishImage();
}
