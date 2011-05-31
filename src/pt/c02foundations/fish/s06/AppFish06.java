package pt.c02foundations.fish.s06;

import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

/**
 * Application to show the use of the Plant component. Here the main application
 * instantiates and call the component service. 
 * 
 * @author Andre Santanche
 *
 */
public class AppFish06
{

    public static void main(String[] args)
    {
        try {
            // creates a global factory
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();

            // instances the component based on its URI
            IPlant component = (IPlant)factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.fish.s06.Plant>");
            
            // uses the component
            System.out.println(component.plantImage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
