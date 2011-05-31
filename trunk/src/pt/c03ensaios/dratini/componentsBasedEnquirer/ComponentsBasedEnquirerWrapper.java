package pt.c03ensaios.dratini.componentsBasedEnquirer;

import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.pizza.IQuestionChooser;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.componentsBasedEnquirer.ComponentsBasedEnquirerWrapper>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaios.optimizers.IEnquirerSupports>"})


public class ComponentsBasedEnquirerWrapper extends ComponentBase implements IEnquirerSupports {
	private IEnquirerSupports enquirer;
	
	public ComponentsBasedEnquirerWrapper() {
		IGlobalFactory factory;
			try {
				factory = ComponentContextFactory.createGlobalFactory();
				IRequires<IQuestionChooser> enq = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.dratini.componentsBasedEnquirer.ComponentsBasedEnquirer>");
		        IQuestionChooser qc = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
		        enq.connect(qc);
		        
		        IAnimalList possible = factory.createInstance(
	            "<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");
		        
		        IAnimalList aux = factory.createInstance(
	            "<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");

		        ((IReceptacleAnimalLists)enq).connect(possible, aux);
		        
		        IQuestionsHash questionHashSim = factory.createInstance(
	            "<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
		        
		        IQuestionsHash questionHashNao = factory.createInstance(
	            "<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
		        
		        IQuestionsHash questionHashTalvez = factory.createInstance(
	            "<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
		  
		        ((IReceptacleQuestionsHashs)enq).connect(questionHashSim, questionHashNao, questionHashTalvez);
		        
		        enquirer = (IEnquirerSupports)enq;
		        
			} catch (ContextException e) {
				e.printStackTrace();
			} catch (FactoryException e) {
				e.printStackTrace();
			}   
	}

	@Override
	public void connect(IResponder responder) {
		enquirer.connect(responder);
	}
}
