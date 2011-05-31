package pt.c03ensaios.eric.optimizer;

import java.util.ArrayList;

import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c03ensaios.eric.enquirer.MyResponder;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.optimizers.IOptimizer;
import pt.c03ensaios.parassauro.list.ParassauroLists;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.eric.optimizer.NumQuestionsOptimizer>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaios.optimizers.IOptimizer>" })

	
public class NumQuestionsOptimizer extends ComponentBase implements IOptimizer {

	@Override
	public IEnquirerSupports getBest() {
		IGlobalFactory factory = null;

		IBaseConhecimento base = new BaseConhecimento();
		String[] NOMES = base.listaNomes();
		
		IEnquirerSupports retorno = null;
	
		int min=-1;
		int contporc;
		float porcent = 0;
		IEnquirerSupports atual = null;
	
		
		try {
			
			factory = ComponentContextFactory.createGlobalFactory();
			ParassauroLists para=factory.createInstance(
			"<http://purl.org/dcc/pt.c03ensaios.parassauro.list.ParassauroLists>");
			ArrayList<IEnquirerSupports> listaEnquirers = para.getEnquirerSupports();
			System.out.println(para.getEnquirerSupports());
			for(int i =0;i<listaEnquirers.size();i++){
				atual = listaEnquirers.get(i);
				int cont = 0;
				int numAnimals = 0;
				contporc = 0;
				porcent = 0;
				for(String nomeAnimal : NOMES){
					try{
				    	MyResponder resp = new MyResponder(nomeAnimal);
				    	atual.connect(resp);
				    	cont+=resp.getNumQuestions();
				    	numAnimals+=1;
				    	contporc+=(resp.itsOk())?1:0;
					}
					catch(Exception err){
					}
				}
				try{
					porcent = (float)contporc/numAnimals;
					System.out.println(atual+" qtd "+(float)cont/(numAnimals)+ " porcent "+porcent);
				}catch(Exception err){
					err.printStackTrace();
				}
				if((cont < min || min == -1)&& porcent>0.8){
					retorno = atual;
					min = cont;
				}
			}

		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public String getOPTType() {
		return "min number of questions";
	}

	@Override
	public String getOPTURI() {
		return "<http://purl.org/dcc/pt.c03ensaios.eric.optimizer.NumQuestionsOptimizer>";
	}
}
