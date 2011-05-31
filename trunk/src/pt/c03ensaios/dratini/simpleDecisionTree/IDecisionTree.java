package pt.c03ensaios.dratini.simpleDecisionTree;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
/**
 * Interface to create a simple decision tree that has an element in each leaf.
 * @author Kamila Galvani
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.dratini.simpleDecisionTree.IDecisionTree>")

public interface IDecisionTree extends ISupports {
	/**
	 * Generates the tree
	 */
	public void createTree();
	/**
	 * Asks questions to find the animal
	 * @param responder Object that implements interface IResponder
	 */
	public void connect(IResponder responder);
	/**
	 * Asks questions to find the animal
	 * @param responder Object that implements interface IResponder
	 */
	public String findLeafByName(IResponder responder);
}
