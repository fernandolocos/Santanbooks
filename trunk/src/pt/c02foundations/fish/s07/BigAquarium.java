package pt.c02foundations.fish.s07;

import pt.c02foundations.fish.s01.IFish;
import pt.c02foundations.fish.s03.IAquarium;
import pt.c02foundations.fish.s04.IFishReceptacle;
import pt.c02foundations.fish.s06.IPlant;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s07.BigAquarium>",
           provides={"<http://purl.org/dcc/pt.c02foundations.fish.s03.IAquarium>"},
           requires={"<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>",
                     "<http://purl.org/dcc/pt.c02foundations.fish.s06.IPlant>"})
public class BigAquarium extends ComponentBase implements IAquarium, IFishReceptacle, IPlantReceptacle
{
    private IFish theFish;
    private IPlant thePlant;
    
    public void connect(IFish theFish)
    {
        this.theFish = theFish;
    }
    
    public void connect(IPlant thePlant)
    {
        this.thePlant = thePlant;
    }

    public void drawAquarium()
    {
        System.out.println("+----------------+");
        System.out.println("|                |");
        if (theFish != null)
            System.out.println(theFish.fishImage());
        System.out.println("|                |");
        System.out.println("|                |");
        if (thePlant != null)
            System.out.println(thePlant.plantImage());
        System.out.println("|                |");
        System.out.println("+----------------+");
    }
}
