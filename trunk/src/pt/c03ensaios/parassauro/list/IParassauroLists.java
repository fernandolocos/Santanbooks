package pt.c03ensaios.parassauro.list;

import java.util.ArrayList;

import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.optimizers.IOptimizer;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.parassauro.list.IParassauroLists>")
public interface IParassauroLists extends ISupports{
	public ArrayList<IEnquirerSupports> getEnquirerSupports();
	public ArrayList<IOptimizer> getOptimizers();
	public ArrayList<String> getEnquirerSupportsURIs();
	public ArrayList<String> getOptimizersURIs();
}