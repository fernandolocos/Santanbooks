package pt.c02foundations.statistics.s01;

import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppStatistics01
{
    public static void main(String args[])
    {
        try {
            // creates a local context to operate using Java language
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
            // creates a component of a given class, with a given name
            // and a given interface respectively
            IStatistics componentStat = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.statistics.s01.StatisticsComponent>");

            System.out.println("inserido valor: " + 50.0f);
            componentStat.insertValue(50.0f);
            System.out.println("inserido valor: " + 70.0f);
            componentStat.insertValue(70.0f);
            System.out.println("inserido valor: " + 30.0f);
            componentStat.insertValue(30.0f);
        
            System.out.println("-- somatorio: " + componentStat.sum());
            System.out.println("-- media: " + componentStat.average());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
