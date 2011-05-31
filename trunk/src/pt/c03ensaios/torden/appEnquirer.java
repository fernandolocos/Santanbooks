package pt.c03ensaios.torden;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.component.InterfaceType;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

/**
 * Aplicação exemplo para o ComponentEnquirer.
 * 
 * @author Waldir
 */
public class appEnquirer {
	
	public static void main(String[] args) {
	
	    try {
	        IGlobalFactory factory = 
	            ComponentContextFactory.createGlobalFactory();
	        
	        IComponentEnquirer enquirer = factory.createInstance("<http://purl.org/dcc/" +
	        		"pt.c03ensaios.torden.ComponentEnquirer>");
	        
	        IPossibleAnimalsHash animalsHash = factory.createInstance(
	                  "<http://purl.org/dcc/pt.c03ensaios.fejao." +
	                  "PossibleAnimalsHash>");
	        
	        animalsHashConnects(animalsHash);
	                
	        IBestChooser chooser = factory.createInstance(
	                  "<http://purl.org/dcc/pt.c03ensaios.dratini." +
	                  "pickBestCharacteristic.PickBestCharacteristic>",
	                  "<http://purl.org/dcc/pt.c03ensaios.dratini." +
	                  "pickBestCharacteristic.IBestChooser>");
	        
	        IQuestionMemory questionMemory = factory.createInstance(
	        		"<http://purl.org/dcc/" +
	        		"pt.c03ensaios.torden.QuestionMemory>");
	        
	        
            IPossibleAnimalsHashReceptacle requiredAnimalsHash = 
            		enquirer.queryInterface("<http://purl.org/dcc/pt.c03ensaios.fejao." +
      	                  "IPossibleAnimals>",
      	                  InterfaceType.REQUIRED);
            requiredAnimalsHash.connect(animalsHash);
            
            IBestChooserReceptacle requiredChooser = 
        		enquirer.queryInterface("<http://purl.org/dcc/pt.c03ensaios.dratini." +
        				"pickBestCharacteristic.IBestChooser>",
  	                  	InterfaceType.REQUIRED);
            requiredChooser.connect(chooser);
            
            IQuestionMemoryReceptacle requiredQuestionMemory = 
        		enquirer.queryInterface("<http://purl.org/dcc/pt.c03ensaios.torden." +
        				"IQuestionMemory>",
  	                  	InterfaceType.REQUIRED);
            requiredQuestionMemory.connect(questionMemory);
	        
	        
		    String[] listaNomes = new BaseConhecimento().listaNomes();
		    
		    //for (String animal : listaNomes) {
		    //	enquirer.connect(new Responder(animal));
		    //	printAnsweredQuestions(questionMemory);
		    //}
		    
		    String animal = listaNomes[(int)(Math.random()*(listaNomes.length - 1))];
		    enquirer.connect(new Responder(animal));
		    printAnsweredQuestions(questionMemory);
		    
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    

	}
	
	private static void animalsHashConnects(IPossibleAnimalsHash hashAnimals) 
									throws ContextException, FactoryException {
		
		
        IGlobalFactory factory = 
            ComponentContextFactory.createGlobalFactory();
        
        
        /* Fabricates a PossibleAnimalHash object*/
        hashAnimals = factory.createInstance(
        		"<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>",
        		"<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimalsHash>");
        
        /* Instantiates 3 QuestionHashes: one for the questions whose answers are "Yes",
         * one for "No"s and the third one for "Don't Know"s. Connects the hashAnimals with them*/
        IQuestionsHash hashAnswerYes = (factory
				.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
        IQuestionsHash hashAnswerNo = factory
				.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
        IQuestionsHash hashAnswerDontKnow = factory
				.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
        IReceptacleQuestionsHash receptacleQuestionHash = //(IReceptacleQuestionsHash)hashAnimals;
        	hashAnimals.queryInterface("<http://purl.org/dcc/pt.c03ensaios.frango.IQuestionsHash>", InterfaceType.REQUIRED);
        receptacleQuestionHash.connect(hashAnswerYes);
        receptacleQuestionHash.connect(hashAnswerNo);
        receptacleQuestionHash.connect(hashAnswerDontKnow);
        
        /* Instantiates and connects an AnimalList object */
        IAnimalList animalList = factory.createInstance(
		"<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");
        IReceptacleAnimalList receptacleList = //(IReceptacleAnimalList)hashAnimals;
        	hashAnimals.queryInterface("<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalList>", InterfaceType.REQUIRED);
    	receptacleList.connect(animalList);
        
        /* Instantiates and connects a Questions object */
    	IQuestions questions = factory.createInstance(
		"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");	
        IReceptacleQuestions receptacleQuestions = //(IReceptacleQuestions)hashAnimals;
        	hashAnimals.queryInterface("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>", InterfaceType.REQUIRED);
        receptacleQuestions.connect(questions);
        
        /* Instantiates and connects an AnimalDatabase object */
        IAnimalsDatabase animalsDatabase = factory.createInstance(
		"<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
        IReceptacleAnimalsDatabase receptacleAnimalsDatabase = //(IReceptacleAnimalsDatabase)hashAnimals;
        	hashAnimals.queryInterface("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>", InterfaceType.REQUIRED);
        receptacleAnimalsDatabase.connect(animalsDatabase);

	}
	
	private static void printAnsweredQuestions(IQuestionMemory questionMemory) {
    	System.out.println("As perguntas e respostas foram:");
    	String[][] pairs = questionMemory.getAllAnsweredQuestions();
    	for (int i = 0; i < pairs.length; i++) {
    		System.out.println(pairs[i][0] + " ---> " + pairs[i][1]);
    	}
	}
	
}
