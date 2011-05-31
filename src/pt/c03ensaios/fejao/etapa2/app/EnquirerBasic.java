package pt.c03ensaios.fejao.etapa2.app;

import java.util.ArrayList;
import java.util.List;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
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
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;
import anima.component.IRequires;
import anima.component.InterfaceType;
import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class EnquirerBasic extends ComponentBase implements IEnquirer, IRequires<IPossibleAnimalsHash>{

	private IBaseConhecimento baseConhecimento;
	private static String[] listaNomes;
	private static List<String> listaPerguntas = new ArrayList<String>();
	private static IPossibleAnimalsHash hashAnimals = null;
	
	public EnquirerBasic() {
		baseConhecimento = new BaseConhecimento();
		listaNomes = baseConhecimento.listaNomes();

		// guarda uma lista de todas as perguntas
		listaPerguntas = new ArrayList<String>();

		for (int i = 0; (i < listaNomes.length); i++) {
			IObjetoConhecimento obj;
			obj = baseConhecimento.recuperaObjeto(listaNomes[i]);

			IDeclaracao decl = obj.primeira();

			while (decl != null) {
				if (!isQuestion(decl.getPropriedade())) {
					addQuestion(decl.getPropriedade());
				}
				decl = obj.proxima();
			}
		}
		
	}

	public void connect(IResponder responder) {
		boolean acertei, encontrado = false;
		String nomeAnimal = null;
		if(hashAnimals == null){
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
	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		}
        
		hashAnimals.clearPossibleAnimalsList();
		
		// utiliza a lista de perguntas que foi montada anteriormente e vai
		// perguntando ate os animais possiveis para a resposta seja somente 1
		for (int i = 0; ((i < listaPerguntas.size()) && (!encontrado)); i++){
			/*este exemplo vai no brutal force. Fazendo todas as perguntas.
			 * O ideal seria utilizar um metodo que escolha a melhor pergunta.
			 * Fizemos assm apenas para exemplificar o uso.*/
			String resposta = responder.ask((String)listaPerguntas.get(i));

			// determina uma nova lista de possiveis animais
			hashAnimals.determinesPossibleAnimals(listaPerguntas.get(i), resposta);
			
			// se hashAnimais = 1 retorna resultado
			if (hashAnimals.getNumberOfAnimals() == 1){
				nomeAnimal = hashAnimals.getPossibleAnimalsList().get(0);
				encontrado = true;
			}
		}

		if (nomeAnimal != null) {
			acertei = responder.finalAnswer(nomeAnimal);
		} else {
			acertei = responder.finalAnswer("nao sei");
		}

		if (acertei) {
			System.out.println("Oba! Acertei! - " + nomeAnimal);
		} else {
			System.out.println("fuem! fuem! fuem!");
		}
	}
	
	private void addQuestion(String question) {
		listaPerguntas.add(question);
	}

	private boolean isQuestion(String question) {
		return (listaPerguntas.contains(question));
	}

	public void connect(IPossibleAnimalsHash hashAnimals) {
		EnquirerBasic.hashAnimals = hashAnimals;
	}
}
