package pt.c03ensaios.dratini.componentsBasedEnquirer;

import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;
import pt.c01interfaces.s01chaveid.dratini.Responder;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.pizza.IQuestionChooser;
public class teste
{
	public static void main(String[] args)
	{
        Responder responder = new Responder();
        responder.carregarAnimal("leao");
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
		        
		        ((IEnquirerSupports)enq).connect(responder);
		        
			} catch (ContextException e) {
				e.printStackTrace();
			} catch (FactoryException e) {
				e.printStackTrace();
			}   
        
	}
}
