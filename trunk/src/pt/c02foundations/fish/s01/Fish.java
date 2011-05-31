package pt.c02foundations.fish.s01;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Draw a character-based Fish.
 * 
 * @author Andre Santanche
 *
 */
@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s01.Fish>",
           provides={"<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>"})
public class Fish extends ComponentBase implements IFish
{

    /**
     * Draw the fish.
     *      ....
     *   \\/ (o)\
     *   //\____/
     */
    public String fishImage()
    {
        return "   ....\n" +
               "\\\\/ (o)\\\n" +
               "//\\____/";
    }

}
