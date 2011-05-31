package pt.c03ensaios.frango.app;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.naxxramas.IEnquirerComponent;
import pt.c03ensaios.naxxramas.IEnquirerComponentReceptacle;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.pizza.IQuestionChooser;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class Main {

	public static void main(String[] args) {
		IGlobalFactory factory = null;
		try {
			factory = ComponentContextFactory.createGlobalFactory();

			// creating the instances of naxxramas package
			IRequires<INaxxramas> sapphironNaxxramas = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.naxxramas.Sapphiron>");

			INaxxramas requiredFrame = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");

			sapphironNaxxramas.connect(requiredFrame);

			IEnquirerComponentReceptacle sapphironEnquirer = (IEnquirerComponentReceptacle) sapphironNaxxramas;

			// creating our enquirer
			IEnquirerComponent enquirerAdvanced = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.app.EnquirerAdvanced>");

			// creating the necessary components and making casts of the our
			// enquirer to an interface IPossibleAnimalsHashReceptacle and
			// IQuestionChooser for the connect receive an parameter of type
			// IPossibleAnimalsHash and IQuestionChooser, respectively
			IPossibleAnimalsHash possibleAnimalsHash = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");

			// Instantiates 3 QuestionHashes: one for the questions whose
			// answers are "Yes",
			// one for "No"s and the third one for "Don't Know"s. Connects the
			// hashAnimals with them
			IReceptacleQuestionsHash receptacleQuestionHash = (IReceptacleQuestionsHash) possibleAnimalsHash;
			IQuestionsHash hashAnswerYes = (factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
			IQuestionsHash hashAnswerNo = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
			IQuestionsHash hashAnswerDontKnow = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
			receptacleQuestionHash.connect(hashAnswerYes);
			receptacleQuestionHash.connect(hashAnswerNo);
			receptacleQuestionHash.connect(hashAnswerDontKnow);

			// Instantiates and connects an AnimalList object
			IReceptacleAnimalList receptacleList = (IReceptacleAnimalList) possibleAnimalsHash;
			IAnimalList animalList = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");
			receptacleList.connect(animalList);

			// Instantiates and connects a Questions object
			IReceptacleQuestions receptacleQuestions = (IReceptacleQuestions) possibleAnimalsHash;
			IQuestions questions = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");
			receptacleQuestions.connect(questions);

			// Instantiates and connects an AnimalDatabase object
			IReceptacleAnimalsDatabase receptacleAnimalsDatabase = (IReceptacleAnimalsDatabase) possibleAnimalsHash;
			IAnimalsDatabase animalsDatabase = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
			receptacleAnimalsDatabase.connect(animalsDatabase);

			IPossibleAnimalsHashReceptacle enquiererPossibleAnimalsHashReceptacle = (IPossibleAnimalsHashReceptacle) enquirerAdvanced;
			enquiererPossibleAnimalsHashReceptacle.connect(possibleAnimalsHash);

			IQuestionChooser questionChooser = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
			IQuestionChooserReceptacle enquiererQuestionChooserReceptacle = (IQuestionChooserReceptacle) enquirerAdvanced;
			enquiererQuestionChooserReceptacle.connect(questionChooser);

			// making the connection of our enquirer with the Sapphiron component
			sapphironEnquirer.connect(enquirerAdvanced);

			requiredFrame.setVisible(true);
		} catch (ContextException e) {
			System.err.println(e.getMessage());
		} catch (FactoryException e) {
			System.err.println(e.getMessage());
		}
	}
}
