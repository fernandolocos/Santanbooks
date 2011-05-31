package pt.c03ensaios.parassauro.enquirer;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import anima.context.exception.ContextException;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class TesteEnquirer{
	public static void main(String[] args) throws ContextException,FactoryException{
		teste("<http://purl.org/dcc/pt.c01interfaces.s01chaveid.parassauro.EnquirerAdvanced>");
	}
	
	public static void teste(String URI)throws ContextException,FactoryException{
		IStatistics sa;
		IResponder ra;
		IEnquirerSupports ea=ComponentContextFactory.createGlobalFactory().createInstance(URI);
		
		for(String animal:new BaseConhecimento().listaNomes()){
			System.out.println("Testando EnquierAdvanced com " + animal + "...");
			
			sa = new Statistics();
			ra = new Responder(sa, animal);
			ea.connect(ra);
			
			System.out.println("----------------------------------------------------------------------------------------");
        }
	}
}