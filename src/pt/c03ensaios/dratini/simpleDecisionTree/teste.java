package pt.c03ensaios.dratini.simpleDecisionTree;

import pt.c01interfaces.s01chaveid.dratini.Responder;
public class teste
{
	public static void main(String[] args)
	{
        Responder responder = new Responder();
        responder.carregarAnimal("leao");
        EntropyDecisionTree enq = new EntropyDecisionTree();
        String st = enq.findLeafByName(responder);
        System.out.println(st);
	}
}
