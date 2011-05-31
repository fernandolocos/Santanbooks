package pt.c02foundations.fish.s07;

import pt.c02foundations.fish.s01.IFish;
import pt.c02foundations.fish.s03.IAquarium;
import pt.c02foundations.fish.s04.IFishReceptacle;
import pt.c02foundations.fish.s06.IPlant;
import anima.component.InterfaceType;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppFish07
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            IFish componentFish = factory.createInstance(
                      "<http://purl.org/dcc/pt.c02foundations.fish.s01.Fish>");
            IPlant componentPlant = factory.createInstance(
                      "<http://purl.org/dcc/pt.c02foundations.fish.s06.Plant>");
            
            IAquarium componentAquarium = factory.createInstance(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s07.BigAquarium>",
                       "<http://purl.org/dcc/pt.c02foundations.fish.s03.IAquarium>");
            
            IFishReceptacle requiredFish = componentAquarium.queryInterface(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>",
                       InterfaceType.REQUIRED);
            requiredFish.connect(componentFish);

            IPlantReceptacle requiredPlant = componentAquarium.queryInterface(
                       "<http://purl.org/dcc/pt.c02foundations.fish.s06.IPlant>",
                       InterfaceType.REQUIRED);
            requiredPlant.connect(componentPlant);
            
            componentAquarium.drawAquarium();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
