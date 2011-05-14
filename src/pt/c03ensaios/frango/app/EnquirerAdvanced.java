package pt.c03ensaios.frango.app;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.fejao.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.PossibleAnimalsHash;
import pt.c03ensaios.pizza.IQuestionChooser;
import pt.c03ensaios.pizza.QuestionChooser;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class EnquirerAdvanced implements IEnquirer {
	private IBaseConhecimento baseConhecimento;
	private IPossibleAnimalsHash possibleAnimalsHash;
	private IQuestionChooser questionChooser;
	
	public EnquirerAdvanced(){
		IGlobalFactory factory;
		
		baseConhecimento = new BaseConhecimento();
		try {
			// instances the component based on its URI
			factory = ComponentContextFactory.createGlobalFactory();
			//PossibleAnimalsHash
			factory.registerPrototype(PossibleAnimalsHash.class);
			possibleAnimalsHash = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
			//QuestionChooser
			factory.registerPrototype(QuestionChooser.class);
			questionChooser = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(IResponder responder) {
		boolean encontrado = false;
		String pergunta;
		
		possibleAnimalsHash.setPossibleAnimalsArray(baseConhecimento.listaNomes());
				
		while(!encontrado){
			pergunta = questionChooser.BestQuestion(baseConhecimento, possibleAnimalsHash.getPossibleAnimalsArray());			
			
			possibleAnimalsHash.determinesPossibleAnimals(pergunta,responder.ask(pergunta));
			
			if(possibleAnimalsHash.getNumberOfAnimals()==1)
				encontrado = true;
		}

		if (responder.finalAnswer(possibleAnimalsHash.getPossibleAnimalsArray()[0])) {
			System.out.println("Oba! Acertei! - " + possibleAnimalsHash.getPossibleAnimalsArray()[0]);
		} else {
			System.out.println("fuem! fuem! fuem!");
		}		
	}
}
