package pt.c02foundations.daysoflife.s02;

import pt.c02foundations.daysoflife.s01.IDaysLife;
import anima.component.IRequires;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppDaysLife02
{

    public static void main(String[] args)
    {
        try {
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();

            IDaysLife componentA = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.daysoflife.s01.DaysLife>");
            IRequires<IDaysLife> componentB = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.daysoflife.s02.Console>");
            
            componentB.connect(componentA);
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
