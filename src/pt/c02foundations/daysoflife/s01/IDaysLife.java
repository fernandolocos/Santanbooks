package pt.c02foundations.daysoflife.s01;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c02foundations.daysoflife.s01.IDaysLife>")
public interface IDaysLife extends ISupports
{
    public int computeDays(int age);
}
