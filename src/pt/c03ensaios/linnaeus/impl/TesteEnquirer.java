package pt.c03ensaios.linnaeus.impl;

import java.util.Random;

import anima.component.IRequires;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.linnaeus.receptacles.IReceptaclePossibleAnimals;
import pt.c03ensaios.linnaeus.receptacles.IReceptacleQuestionChooser;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.pizza.IQuestionChooser;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;

public class TesteEnquirer {
	public static void main(String[] args)
	{
		// Declaração de algumas variáveis
		IAnimalsDatabase db;
		IQuestions qust;
		IPossibleAnimalsHash excluder;
		IQuestionChooser qustgetter;
		IStatistics sa;
		IResponder ra;
		IEnquirerSupports ea;
		String animal;
		Random randomizator = new Random();  
		int numPerguntas = 0;
		try {
			// Instância da fábrica
            IGlobalFactory factory = 
                ComponentContextFactory.createGlobalFactory();
            
            // Instanciação dos components
            db = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>",
                    "<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
            
            qust = factory.createInstance(
                	"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>",
                	"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
            
            excluder = factory.createInstance(
                	"<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>",
        			"<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimals>");
            
            qustgetter = factory.createInstance(
                	"<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>",
					"<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestionChooser>");
            
            IRequires<IAnimalsDatabase> connectQuestions = qust.queryReceptacle(
            	"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
            
            IRequires<IQuestions> connectDatabase = db.queryReceptacle(
        		"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
            
            IReceptacleQuestionsHash receptacleQuestionHash = (IReceptacleQuestionsHash)excluder;
            IQuestionsHash hashAnswerYes = (factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
            IQuestionsHash hashAnswerNo = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
            IQuestionsHash hashAnswerDontKnow = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
            
            IReceptacleAnimalList receptacleList = (IReceptacleAnimalList)excluder;
            IAnimalList animalList = factory.createInstance(
            		"<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");	            
            
            IReceptacleQuestions receptacleQuestions = (IReceptacleQuestions)excluder;	            
            
            IReceptacleAnimalsDatabase receptacleAnimalsDatabase = (IReceptacleAnimalsDatabase)excluder;	            
            
            // Conecção dos componetns entre si
            
            connectQuestions.connect(db);
            connectDatabase.connect(qust);
            receptacleQuestionHash.connect(hashAnswerYes);
            receptacleQuestionHash.connect(hashAnswerNo);
            receptacleQuestionHash.connect(hashAnswerDontKnow);
            receptacleList.connect(animalList);
            receptacleQuestions.connect(qust);
            receptacleAnimalsDatabase.connect(db);
            
            
            // Escolher um animal aleatório
        	animal = db.getNomes()[randomizator.nextInt(db.getNAnimais()-1)];
    		numPerguntas = 0;
    	
    		// Avisar qual animal será testado, criar Statistics e Responder
    		System.out.println("Testando com " + animal + "...");
    		
    		sa = new Statistics();
    		ra = new Responder(sa, animal);
    		
    		// Instanciar o component Enquirer e fazer suas conecções necessárias
    		ea = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.impl.EnquirerAdvanced>");
            IReceptacleAnimalsDatabase receptacleAnimalsDatabaseEnq = (IReceptacleAnimalsDatabase)ea;	
            IReceptacleQuestionChooser receptacleQuestionChooserEnq = (IReceptacleQuestionChooser)ea;
            IReceptaclePossibleAnimals receptaclePossibleAnimalsEnq = (IReceptaclePossibleAnimals)ea;
    		receptacleAnimalsDatabaseEnq.connect(db);
    		receptacleQuestionChooserEnq.connect(qustgetter);
    		receptaclePossibleAnimalsEnq.connect(excluder);
    		
    		// Conectar o enquirer no responder
    		ea.connect(ra);
    		
    		// Mostrar quantas perguntas foram feitas
    		numPerguntas = sa.totalPerguntas();
    		System.out.println("Total de perguntas (advanced): " + numPerguntas);
    		System.out.println("----------------------------------------------------------------------------------------");
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
}
