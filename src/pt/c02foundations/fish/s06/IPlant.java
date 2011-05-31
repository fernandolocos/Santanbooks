package pt.c02foundations.fish.s06;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface for the Plant component that enables to trigger the plant drawing.
 * 
 * @author Andre Santanche
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c02foundations.fish.s06.IPlant>")
public interface IPlant extends ISupports
{
    /**
     * Draw the plant.
     */	
    public String plantImage();
}
