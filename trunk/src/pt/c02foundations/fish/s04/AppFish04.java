package pt.c02foundations.fish.s04;

import pt.c02foundations.fish.s01.IFish;
import pt.c02foundations.fish.s03.IAquarium;
import anima.component.InterfaceType;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppFish04
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            IFish componentFish = factory.createInstance(
                      "<http://purl.org/dcc/pt.c02foundations.fish.s01.Fish>");
            
            IAquarium componentAquarium = factory.createInstance(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s04.Aquarium>",
                       "<http://purl.org/dcc/pt.c02foundations.fish.s03.IAquarium>");
            
            IFishReceptacle requiredAquarium = componentAquarium.queryInterface(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>",
                       InterfaceType.REQUIRED);
            requiredAquarium.connect(componentFish);
            
            componentAquarium.drawAquarium();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
