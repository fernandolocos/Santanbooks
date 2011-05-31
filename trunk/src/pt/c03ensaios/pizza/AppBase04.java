package pt.c03ensaios.pizza;

import anima.component.IRequires;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;
import pt.c03ensaios.tochinhas2.impl.*;
import pt.c03ensaios.debolacha.impl.AnimalList;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.fejao.*;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.fejao.etapa2.impl.PossibleAnimalsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.frango.QuestionsHash;
import pt.c03ensaios.linnaeus.*;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;


public class AppBase04
{
	
	public static void main(String[] args)
	{
		//ArrayList<String> animaisTeste = recuperaAnimais("animais-teste");
		IStatistics sa;
		IResponder ra;
		
		IQuestionChooser pizza = null;
		IPossibleAnimalsHash fejao1 = null;
		IAnimalsDatabase linnaeus = null;
		IQuestions tochinhas2 = null;
		
		//instancia um objeto do tipo linnaeus e
		//instancia um objeto do tipo tochinhas2
		try {
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
			
			factory.registerPrototype(AnimalList.class);
			factory.registerPrototype(QuestionsHash.class);
			factory.registerPrototype(Questions.class);
			factory.registerPrototype(AnimalsDatabase.class);
			factory.registerPrototype(PossibleAnimalsHash.class);
			factory.registerPrototype(QuestionChooser.class);
			//factory.registerPrototype(IQuestionsHash.class);
           
			//IQuestionsHash hashQuestions = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
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
		
		try{
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
//começo da modificação do jao
			fejao1 = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
   
		   /* Instantiates 3 QuestionHashes: one for the questions whose answers are "Yes",
			* one for "No"s and the third one for "Don't Know"s. Connects the hashAnimals with them*/
			IReceptacleQuestionsHash receptacleQuestionHash = (IReceptacleQuestionsHash) fejao1;
			IQuestionsHash hashAnswerYes = (factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
			IQuestionsHash hashAnswerNo = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
			IQuestionsHash hashAnswerDontKnow = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
			receptacleQuestionHash.connect(hashAnswerYes);
			receptacleQuestionHash.connect(hashAnswerNo);
			receptacleQuestionHash.connect(hashAnswerDontKnow);
		   
			/* Instantiates and connects an AnimalList object */
			IReceptacleAnimalList receptacleList = (IReceptacleAnimalList)fejao1;
			IAnimalList animalList = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");	            
			receptacleList.connect(animalList);
		   
			/* Instantiates and connects a Questions object */
			IReceptacleQuestions receptacleQuestions = (IReceptacleQuestions)fejao1;
			IQuestions questions = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");	            
			receptacleQuestions.connect(questions);
		   
			/* Instantiates and connects an AnimalDatabase object */
			IReceptacleAnimalsDatabase receptacleAnimalsDatabase = (IReceptacleAnimalsDatabase)fejao1;
			IAnimalsDatabase animalsDatabase = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");	            
			receptacleAnimalsDatabase.connect(animalsDatabase);
			
// fim da modificação do jao
		} catch (Exception e) {
	            e.printStackTrace();
		}
		
		for (int i=0; i<animales.length; i++){
		//pra cada animal na base efetua a busca
						
			System.out.println("Testando EnquierAdvanced com " + animales[i] + "...");
			sa = new Statistics();
			ra = new Responder(sa, animales[i]);

			//instancia objeto do nosso componente para obter melhores peguntas
			//instancia um novo fejao, hash contendo todos os animais possiveis
			try {
				IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
				
				pizza = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
				//pizza.SetMultithreadingState(true); // pro playing :)
				
				fejao1.clearPossibleAnimalsList();
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
			
			//escolhe e faz perguntas até que sobra apenas 1 animal
			do {
				//obtem melhor pergunta atraves do nosso componente enviando um objeto fejao
				String pregunta = pizza.BestQuestion(fejao1);
				//faz a pergunta 
				String resp = ra.ask(pregunta);
				//reduz o conjunto de animais apenas aos que respondem a pegunta do jeito que ela foi respondida
				fejao1.determinesPossibleAnimals(pregunta, resp);
			} while	(fejao1.getNumberOfAnimals() > 1);
	
			//quando sobrar só 1 animal responde ele -SEMPRE ACERTA-
			ra.finalAnswer(fejao1.getPossibleAnimalsArray()[0]);
			
			System.out.println("----------------------------------------------------------------------------------------");
        }		
	}
}
