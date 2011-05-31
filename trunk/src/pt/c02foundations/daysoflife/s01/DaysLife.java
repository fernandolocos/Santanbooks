package pt.c02foundations.daysoflife.s01;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c02foundations.daysoflife.s01.DaysLife>",
           provides="<http://purl.org/dcc/pt.c02foundations.s01.daysoflife.IDaysLife>")
public class DaysLife extends ComponentBase implements IDaysLife
{

    public int computeDays(int age)
    {
        return age * 365;
    }

}
