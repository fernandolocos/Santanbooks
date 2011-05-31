package pt.c02foundations.fish.s00;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Draw a character-based Fish.
 * 
 * @author Andre Santanche
 *
 */
@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s00.Fish>",
           provides={"<http://purl.org/dcc/pt.c02foundations.fish.s00.IFish>"})
public class Fish extends ComponentBase implements IFish
{

    /**
     * Draw the fish.
     *      ___
     *   |\/  O\
     *   |/\___/
     */
    public String fishImage()
    {
        return "   ___\n" +
               "|\\/  O\\\n" +
               "|/\\___/";
    }

}
