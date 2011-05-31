package pt.c02foundations.fish.s03;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c02foundations.fish.s03.IAquarium>")
public interface IAquarium extends ISupports
{
    public void drawAquarium();
}
