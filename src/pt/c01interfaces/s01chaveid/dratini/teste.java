package pt.c01interfaces.s01chaveid.dratini;

import pt.c01interfaces.s01chaveid.dratini.Responder;
import pt.c03ensaios.dratini.simpleDecisionTree.EntropyDecisionTree;
public class teste
{
	public static void main(String[] args)
	{
        Responder responder = new Responder();
        responder.carregarAnimal("zumbi");
        EntropyDecisionTree enq = new EntropyDecisionTree();
        enq.connect(responder);
	}
}
