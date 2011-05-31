package pt.c02foundations.fish.s06;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Draw a character-based Plant.
 * 
 * @author Andre Santanche
 *
 */
@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s06.Plant>",
           provides={"<http://purl.org/dcc/pt.c02foundations.fish.s06.IPlant>"})
public class Plant extends ComponentBase implements IPlant
{

    /**
     * Draw the plant.
     *   \\//
     *   \\//
     *    ||
     *    ||
     */
    public String plantImage()
    {
        return "   \\\\//\n" +
               "   \\\\//\n" +
               "    ||\n" +
               "    ||\n";
    }

}
