package pt.c03ensaios.naxxramas;

import pt.c03ensaios.relu.ISearcher;
import anima.component.IReceptacle;

/**
 * Enables the connection to a component that implements ISearcher.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public interface ISearcherReceptacle extends IReceptacle {
    public void connect(ISearcher arg0);
}