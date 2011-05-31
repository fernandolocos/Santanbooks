package pt.c03ensaios.denisecamila;

import pt.c03ensaios.relu.ISearcher;
import anima.annotation.ComponentInterface;
import anima.component.IReceptacle;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.denisecamila.IReceptacleSearcher>")
public interface IReceptacleSearcher extends IReceptacle{
    public void connect(ISearcher searcher);
}