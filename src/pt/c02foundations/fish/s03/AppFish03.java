package pt.c02foundations.fish.s03;

import pt.c02foundations.fish.s01.IFish;
import anima.component.IRequires;
import anima.component.InterfaceType;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppFish03
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            IFish componentFish = factory.createInstance(
                      "<http://purl.org/dcc/pt.c02foundations.fish.s01.Fish>");
            
            IAquarium componentAquarium = factory.createInstance(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s03.Aquarium>",
                       "<http://purl.org/dcc/pt.c02foundations.fish.s03.IAquarium>");
            
            IRequires<IFish> requiredAquarium = componentAquarium.queryInterface(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>",
                       InterfaceType.REQUIRED);
            requiredAquarium.connect(componentFish);
            
            componentAquarium.drawAquarium();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
