package pt.c03ensaios.debolacha.app;

/*Used components*/
import pt.c03ensaios.dratini.simpleDecisionTree.EntropyDecisionTree;
import pt.c03ensaios.dratini.simpleDecisionTree.IDecisionTree;
import pt.c03ensaios.lobo.impl.OldResponder;
import pt.c03ensaios.lobo.inter.IOldResponder;

/*GlobalFactory*/
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class Game {
	public Game(){
		IGlobalFactory factory;
		try {
			factory = ComponentContextFactory.createGlobalFactory();

			/*Instances an EntropyDecisionTree*/
			factory.registerPrototype(EntropyDecisionTree.class);
			IDecisionTree tree = factory.createInstance(
				"<http://purl.org/dcc/pt.c03ensaios.dratini.simpleDecisionTree.EntropyDecisionTree>");
		
			/*Instances an OldResponder*/
			factory.registerPrototype(OldResponder.class);
			IOldResponder responder = factory.createInstance(
					"<http://purl.org/dcc/pt.c03ensaios.lobo.impl.OldResponder>");
			
			String animal = tree.findLeafByName(responder);
			/*If the animal was correct, shows it*/
			if(animal != null)
				new ShowAnimal(animal);
				
			else{
				new AskEdit();
			}
		
		} catch (ContextException e) {
			/*Context management exception*/
			e.printStackTrace();
		} catch (FactoryException e) {
			/*GLobalFactory exception*/
			e.printStackTrace();
		}
	}
}
