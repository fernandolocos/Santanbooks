package pt.c03ensaios.pizza;

//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
import anima.component.IRequires;
//import pt.c01interfaces.s01chaveid.s01base.impl.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
//import pt.c01interfaces.s01chaveid.pizza.EnquirerAdvanced;
//import pt.c01interfaces.s01chaveid.s02exemplo.impl.EnquirerBasic;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;
//import pt.c03ensaios.pizza.*;
import pt.c03ensaios.tochinhas2.impl.*;
import pt.c03ensaios.fejao.*;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.PossibleAnimalsHash;
import pt.c03ensaios.linnaeus.*;
//import anima.annotation.Component;
//import anima.component.IRequires;
//import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;


public class AppBase02
{
	public static void main(String[] args)
	{
		
		//ArrayList<String> animaisTeste = recuperaAnimais("animais-teste");
		IStatistics sa;
		IResponder ra;
		IQuestionChooser qc = null;

		IAnimalsDatabase linnaeus = null;
		IQuestions tochinhas2 = null;
		
		//instancia um objeto do tipo linnaeus e
		//instancia um objeto do tipo tochinhas2
		try {
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
           factory.registerPrototype(Questions.class);
           factory.registerPrototype(AnimalsDatabase.class);
           
//           IQuestionsHash hashQuestions = factory.createInstance(
//                      "<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
           linnaeus = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>"); 
           tochinhas2 = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
		IRequires<IAnimalsDatabase> connectQuestions = tochinhas2.queryReceptacle("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
		IRequires<IQuestions> connectDatabase = linnaeus.queryReceptacle("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
		
		connectQuestions.connect(linnaeus);
		connectDatabase.connect(tochinhas2);
		
		//usa linnaeus para obter todos animais da base
		String animales[] = linnaeus.getNomes();
		
		for (int i=0; i<animales.length; i++){
		//pra cada animal na base efetua a busca
						
			System.out.println("Testando EnquierAdvanced com " + animales[i] + "...");
			sa = new Statistics();
			ra = new Responder(sa, animales[i]);
			//instancia objeto do nosso componente para obter melhores peguntas
			//qc = new QuestionChooser();
			
			//instancia um novo fejao, hash contendo todos os animais possiveis
			//IPossibleAnimalsHash fejao1 = new PossibleAnimalsHash(); 
			IPossibleAnimalsHash fejao1 = null;
			try {
	            IGlobalFactory factory = 
	            	ComponentContextFactory.createGlobalFactory();
	            
	           factory.registerPrototype(PossibleAnimalsHash.class);
	           factory.registerPrototype(QuestionChooser.class);
	           //factory.registerPrototype(IQuestionsHash.class);
	           
//	           IQuestionsHash hashQuestions = factory.createInstance(
//	                      "<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
	           qc = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
	           fejao1 = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			
			//escolhe e faz perguntas até que sobra apenas 1 animal
			do {
			//obtem melhor pergunta atraves do nosso componente enviando um objeto fejao
			String pregunta = qc.BestQuestion(fejao1);
			//faz a pergunta 
			String resp = ra.ask(pregunta);
			//reduz o conjunto de animais apenas aos que respondem a pegunta do jeito que ela foi respondida
			fejao1.determinesPossibleAnimals(pregunta, resp);
			}while	(fejao1.getNumberOfAnimals() > 1);

			//quando sobrar só 1 animal responde ele -SEMPRE ACERTA-
			ra.finalAnswer(fejao1.getPossibleAnimalsArray()[0]);
			
			System.out.println("----------------------------------------------------------------------------------------");
        }		
	}
}
