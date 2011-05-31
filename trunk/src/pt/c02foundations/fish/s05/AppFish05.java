package pt.c02foundations.fish.s05;

import pt.c02foundations.fish.s01.IFish;
import anima.component.IRequires;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppFish05
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            IFish componentFish = factory.createInstance(
                      "<http://purl.org/dcc/pt.c02foundations.fish.s01.Fish>");
            
            IRequires<IFish> componentAquarium = factory.createInstance(
                      "<http://purl.org/dcc/pt.c02foundations.fish.s05.VisualAquarium>");
            
            componentAquarium.connect(componentFish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
