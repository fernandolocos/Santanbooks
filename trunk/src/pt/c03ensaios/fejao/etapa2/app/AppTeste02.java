package pt.c03ensaios.fejao.etapa2.app;

import java.util.ArrayList;
import java.util.List;

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
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;
import anima.component.InterfaceType;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

/* Realização de testes com os métodos de String e list do component PossibleAnimalsHash*/
public class AppTeste02 {

	public static void main(String[] args) {
		List<String> animalsList = new ArrayList<String>();
		String[] animalsArray = new String[0];
		IPossibleAnimalsHash hashAnimals = null;
		
		try {
			IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            factory.registerPrototype(PossibleAnimalsHash.class);
            factory.registerPrototype(AnimalList.class);
            factory.registerPrototype(Questions.class);
            factory.registerPrototype(AnimalsDatabase.class);
            factory.registerPrototype(QuestionsHash.class);
            
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

			hashAnimals.clearPossibleAnimalsList();

			animalsList = hashAnimals.getPossibleAnimalsList();
			System.out.print("GetAnimals com List:  [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsList.get(i)+", ");
			}
			System.out.println("]");

			animalsArray = hashAnimals.getPossibleAnimalsArray();
			System.out.print("GetAnimals com Array: [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsArray[i]+", ");
			}
			System.out.println("]");

			System.out.println(hashAnimals.getNumberOfAnimals());
			hashAnimals.removeAnimalsList(animalsList);
			System.out.println(hashAnimals.getNumberOfAnimals());
			animalsList = new ArrayList<String>();
			animalsList.add("abelha");
			animalsList.add("atum");
			animalsList.add("aranha");

			hashAnimals.setPossibleAnimalsList(animalsList);

			animalsList = hashAnimals.getPossibleAnimalsList();
			System.out.print("GetAnimals com List:  [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsList.get(i)+", ");
			}
			System.out.println("]");
			System.out.println(hashAnimals.getNumberOfAnimals());
			animalsList.add("baleia");
			animalsList.add("boi");
			hashAnimals.addAnimalsList(animalsList);

			//hashAnimals.setPossibleAnimalsArray(animalsArray);

			animalsList = hashAnimals.getPossibleAnimalsList();
			System.out.print("GetAnimals com List:  [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsList.get(i)+", ");
			}
			System.out.println("]");
			
			animalsArray = new String[3];
			animalsArray[0] = "boi";
			animalsArray[1] = "atum";
			animalsArray[2] = "aranha";
			hashAnimals.removeAnimalsArray(animalsArray);
			
			animalsArray = hashAnimals.getPossibleAnimalsArray();
			System.out.print("GetAnimals com Array: [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsArray[i]+", ");
			}
			System.out.println("]");

			hashAnimals.clearPossibleAnimalsList();
			System.out.println(hashAnimals.getNumberOfAnimals());
		} catch (ContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}



