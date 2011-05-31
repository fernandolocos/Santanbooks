package pt.c02foundations.fish.s01;

import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

/**
 * Application to show the use of the Fish component. Here the main application
 * instantiates and call the component service. 
 * 
 * @author Andre Santanche
 *
 */
public class AppFish01
{

    public static void main(String[] args)
    {
        try {
            // creates a global factory
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();

            // instances the component based on its URI
            IFish component = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.fish.s01.Fish>");
            
            // uses the component
            System.out.println(component.fishImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
