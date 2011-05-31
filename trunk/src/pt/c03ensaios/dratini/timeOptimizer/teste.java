package pt.c03ensaios.dratini.timeOptimizer;

import pt.c03ensaios.optimizers.IOptimizer;
import pt.c03ensaios.parassauro.list.IParassauroLists;
import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;


public class teste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IGlobalFactory factory;
		try {
			factory = ComponentContextFactory.createGlobalFactory();
			IParassauroLists para = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.parassauro.list.ParassauroLists>");
			factory.registerPrototype(AverageTimeOptimizer.class);
			IRequires<IParassauroLists> opt = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.AverageTimeOptimizer>");
			opt.connect(para);
			System.out.println(((IOptimizer)opt).getBest());
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}

}
