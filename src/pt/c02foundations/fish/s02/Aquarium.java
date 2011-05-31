package pt.c02foundations.fish.s02;

import pt.c02foundations.fish.s01.IFish;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s02.Aquarium>",
           requires={"<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>"})
public class Aquarium extends ComponentBase implements IRequires<IFish>
{
    private IFish theFish;
    
    public void connect(IFish theFish)
    {
        this.theFish = theFish;
        drawAquarium();
    }

    public void drawAquarium()
    {
        System.out.println("+----------------+");
        System.out.println("|                |");
        if (theFish != null)
            System.out.println(theFish.fishImage());
        System.out.println("|                |");
        System.out.println("+----------------+\n");
    }
}
