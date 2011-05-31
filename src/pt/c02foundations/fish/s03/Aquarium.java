package pt.c02foundations.fish.s03;

import pt.c02foundations.fish.s01.IFish;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.fish.s03.Aquarium>",
           provides={"<http://purl.org/dcc/pt.c02foundations.fish.s03.IAquarium>"},
           requires={"<http://purl.org/dcc/pt.c02foundations.fish.s01.IFish>"})
public class Aquarium extends ComponentBase implements IAquarium, IRequires<IFish>
{
    private IFish theFish;
    
    public void connect(IFish theFish)
    {
        this.theFish = theFish;
    }

    public void drawAquarium()
    {
        System.out.println("+----------------+");
        System.out.println("|                |");
        if (theFish != null)
            System.out.println(theFish.fishImage());
        System.out.println("|                |");
        System.out.println("+----------------+");
    }
}
