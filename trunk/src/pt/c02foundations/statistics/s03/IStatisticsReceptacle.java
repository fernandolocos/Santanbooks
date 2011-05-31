package pt.c02foundations.statistics.s03;

import pt.c02foundations.statistics.s01.IStatistics;
import anima.annotation.ComponentReceptacle;
import anima.component.IReceptacle;

@ComponentReceptacle("<http://purl.org/dcc/pt.c02foundations.statistics.s01.IStatistics>")
public interface IStatisticsReceptacle extends IReceptacle
{
    public void connect(IStatistics provider);
}
