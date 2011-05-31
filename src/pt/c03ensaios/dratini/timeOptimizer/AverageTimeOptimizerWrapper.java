package pt.c03ensaios.dratini.timeOptimizer;

import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.optimizers.IOptimizer;
import pt.c03ensaios.parassauro.list.IParassauroLists;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.AverageTimeOptimizerWrapper>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaios.optimizers.IOptimizer>"})


public class AverageTimeOptimizerWrapper extends ComponentBase implements IOptimizer  {
	private AverageTimeOptimizer opt;
	
	public AverageTimeOptimizerWrapper() {
		IGlobalFactory factory;
		
		
		
		try {
			factory = ComponentContextFactory.createGlobalFactory();
			IParassauroLists para = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.parassauro.list.ParassauroLists>");
			factory.registerPrototype(AverageTimeOptimizer.class);
			opt = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.dratini.timeOptimizer.AverageTimeOptimizer>");
			opt.connect(para);
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
		
	
	//LeastWorseTimeOptimizer opt = new LeastWorseTimeOptimizer();
    //System.out.println(opt.getBestInt());
}

	

	@Override
	public IEnquirerSupports getBest() {
		return opt.getBest();
	}

	@Override
	public String getOPTType() {
		return opt.getOPTType()+"_wrapper";
	}

	@Override
	public String getOPTURI() {
		return opt.getOPTURI();
	}
	}

	
