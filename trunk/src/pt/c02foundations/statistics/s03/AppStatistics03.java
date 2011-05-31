package pt.c02foundations.statistics.s03;

import pt.c02foundations.statistics.s01.IStatistics;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppStatistics03
{
    public static void main(String args[])
    {
        try {
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
            // First Component -- Statistics Component
            IStatistics componentStat = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.statistics.s01.StatisticsComponent>");
            
            // Second Component -- Visual Component
            IStatisticsReceptacle componentVisualStat = factory.createInstance(
                    "<http://purl.org/dcc/pt.c02foundations.statistics.s03.StatisticsGUIComponent>");
            
            // Connects both components
            componentVisualStat.connect(componentStat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
