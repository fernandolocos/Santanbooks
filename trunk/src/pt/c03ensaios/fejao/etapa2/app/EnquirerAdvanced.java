package pt.c03ensaios.fejao.etapa2.app;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.debolacha.impl.AnimalList;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.fejao.etapa2.impl.PossibleAnimalsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.frango.QuestionsHash;
import pt.c03ensaios.linnaeus.AnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.pizza.IQuestionChooser;
import pt.c03ensaios.pizza.QuestionChooser;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.InterfaceType;
import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fejao.app.EnquirerAdvanced>", 
		provides = { "<http://purl.org/dcc/pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer>" },
		requires={"<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>"})
public class EnquirerAdvanced extends ComponentBase implements IEnquirer, IRequires<IPossibleAnimalsHash>{

	private static IPossibleAnimalsHash hashAnimals = null;

	public EnquirerAdvanced() {
		
	}

	public void connect(IResponder responder) {
		boolean correct, found = false;
		String animalName = null;
		IQuestionChooser questionChooser = null;

		try {
			IGlobalFactory factory = 
				ComponentContextFactory.createGlobalFactory();

			factory.registerPrototype(PossibleAnimalsHash.class);
			factory.registerPrototype(QuestionsHash.class);
			factory.registerPrototype(AnimalList.class);
            factory.registerPrototype(Questions.class);
            factory.registerPrototype(AnimalsDatabase.class);
			factory.registerPrototype(QuestionChooser.class);

			if(hashAnimals == null){
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
			
			questionChooser =  factory.createInstance(
					"<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");;

		} catch (Exception e) {
			e.printStackTrace();
		}

		hashAnimals.clearPossibleAnimalsList();
		
		String question, answer;
		while(!found){
			/* seleciona a melhor pergunta */
			question = questionChooser.BestQuestion(hashAnimals);
			answer = responder.ask(question);

			// determina uma nova lista de possiveis animais
			hashAnimals.determinesPossibleAnimals(question, answer);

			// se hashAnimais = 1 retorna resultado
			if (hashAnimals.getNumberOfAnimals() == 1){
				animalName = hashAnimals.getPossibleAnimalsList().get(0);
				found = true;
			}
		}

		if (animalName != null) {
			correct = responder.finalAnswer(animalName);
		} else {
			correct = responder.finalAnswer("nao sei");
		}

		if (correct) {
			System.out.println("Oba! Acertei! - " + animalName);
		} else {
			System.out.println("fuem! fuem! fuem!");
		}
	}

	public void connect(IPossibleAnimalsHash hashAnimals) {
		EnquirerAdvanced.hashAnimals = hashAnimals;
	}
}
