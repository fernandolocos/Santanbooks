package pt.c03ensaios.eric;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c03ensaios.eric.analizer.BestAnalizer;
import pt.c03ensaios.eric.enquirer.EnquirerBasic;
import pt.c03ensaios.eric.enquirer.MyResponder;
import pt.c03ensaios.eric.optimizer.NumQuestionsOptimizer;

public class Test {
	public static void main(String args[]){

//teste do analizer
		BestAnalizer bt = new BestAnalizer();
		System.out.println("INICIO DO TESTE DO ANALIZER");
		//System.out.println("\n> COMPONENTE ESCOLHIDO: "+bt.getBest()+"\n");
		//System.out.println("\n> COMPONENTE ESCOLHIDO: "+bt.getBest("least_average_time_wrapper"));
		System.out.println("\n> COMPONENTE ESCOLHIDO: "+bt.getBest("min number of questions"));
		System.out.println("FIM DO TESTE DO ANALIZER");
		
/*
//teste do optimizer
		System.out.println("INICIO DO TESTE DO OPTIMIZER");
		NumQuestionsOptimizer opt = new NumQuestionsOptimizer();
		System.out.println("\n> O OBJETO SELECIONADO FOI: "+opt.getBest()+"\n");
		System.out.println("FIM DO TESTE DO OPTIMIZER");
		
		

//teste do enquirer
		System.out.println("INICIO DO TESTE DO ENQUIRER");
		IEnquirer enquirer3 = new EnquirerBasic();
		MyResponder r = new MyResponder("ornitorrinco");
		enquirer3.connect(r);
		System.out.println("acertou? > "+r.itsOk()+", qtd perguntas > "+r.getNumQuestions());
		System.out.println("FIM DO TESTE DO ENQUIRER");
*/
	}
}
