package pt.c03ensaios.fejao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.frango.QuestionsHash;
import pt.c03ensaios.frango.appTest.Responder;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaiosfoundations.fejao.IPossibleAnimalsHash>" },
        requires={"<http://purl.org/dcc/pt.c03ensaios.frango.IQuestionsHash>"}, 
        		"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalDatabase>"})
public class PossibleAnimalsHash extends ComponentBase implements IPossibleAnimalsHash, IRecptacleQuestionsHash, IReceptacleAnimalsDatabase{
	private List<String> animals;
	private static IAnimalsDatabase base;
	//private static IBaseConhecimento base;
	//private static String[] listNames;
	private static IQuestionsHash hashAnswerYes;
	private static IQuestionsHash hashAnswerNo;
	private static IQuestionsHash hashAnswerDontKnow;
	private static List<String> listQuestions = new ArrayList<String>();
	private static HashMap<String, IResponder> responders = new HashMap<String, IResponder>();
	private static HashMap<String, IObjetoConhecimento> objs = new HashMap<String, IObjetoConhecimento>();
	private static Boolean inserted = false;
	private static int currentHash = 0;
	
	/**
	 * Class constructor. Instantiates 3 objects of the type QuestionsHash and
	 * initializes them.
	 */
	public PossibleAnimalsHash(){
		this.animals = new ArrayList<String>();
		//base = new BaseConhecimento();
		//listNames = base.listaNomes();

		if (inserted == false) {
			try {
				IGlobalFactory factory = ComponentContextFactory
						.createGlobalFactory();
				factory.registerPrototype(QuestionsHash.class);
				hashAnswerYes = (factory
						.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
				hashAnswerNo = factory
						.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
				hashAnswerDontKnow = factory
						.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
				base = factory
						.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalDatabase>");
			} catch (Exception e) {
				e.printStackTrace();
			}

			listQuestions = new ArrayList<String>();

			insertAnimalsHash();
			inserted = true;
		}
	}
	
	/**
	 * Alternative class constructor. Receives 3 objects of the type QuestionsHash and connects them.
	 * 
	 * @param hashAnswerYes IQuestionsHash object which contains questions and animals whose answers are "Yes".
	 * @param hashAnswerNo IQuestionsHash object which contains questions and animals whose answers are "No".
	 * @param hashAnswerDontKnow IQuestionsHash object which contains questions and animals whose answers are "Don't Know".
	 */
	public PossibleAnimalsHash(IQuestionsHash hashAnswerYes, 
			IQuestionsHash hashAnswerNo, IQuestionsHash hashAnswerDontKnow){
		this.animals = new ArrayList<String>();
		//base = new BaseConhecimento();
		//listNames = base.listaNomes();

		if (inserted == false) {
			try {
				IGlobalFactory factory = ComponentContextFactory
						.createGlobalFactory();

				factory.registerPrototype(QuestionsHash.class);

				connect(hashAnswerYes);
				connect(hashAnswerNo);
				connect(hashAnswerDontKnow);
				
				base = factory
				.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalDatabase>");
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			listQuestions = new ArrayList<String>();
			inserted = true;
		}
	}
	
	/**
	 * Connects an IQuestionHash object and sets it to the local arguments.
	 * The first time it is called, connects the hash of answers "Yes". Iterates internally and the next two times
	 * it is called, connects the hash of answers "No" and the hash of answers "Don't Know", respectively. If called after
	 * hash of answers "Don't Know" has been already connected, restarts the process, from the hash of answers "Yes".
	 * 
	 * @param hash IQuestionsHash object to be connected.
	 */
	public void connect(IQuestionsHash hash){
		switch(currentHash % 3){
			case 0: PossibleAnimalsHash.hashAnswerYes = hash;
					break;
			case 1: PossibleAnimalsHash.hashAnswerNo = hash;
					break;
			case 2: PossibleAnimalsHash.hashAnswerDontKnow = hash;
					break;
			default: break;
		}
		currentHash++;
	}

	/**
	 * Connects an IAnimalsDatabase object and sets it to the local arguments.
	 * Method that connects the base of the interface IAnimalsDatabase.
	 * 
	 * @param base IAnimalsDatabase object to be connected.
	 */
	public void connect(IAnimalsDatabase base) {
		PossibleAnimalsHash.base = base;	
	}
	
	/**
	 * @return IQuestionsHash object containing questions and the animals whose answers
	 * to these questions are "Yes"
	 */
	public IQuestionsHash getQuestionsHashYes(){
		return hashAnswerYes;
	}
	
	/**
	 * @return IQuestionsHash object containing questions and the animals whose answers
	 * to these questions are "No"
	 */
	public IQuestionsHash getQuestionsHashNo(){
		return hashAnswerNo;
	}
	
	/**
	 * @return IQuestionsHash object containing questions and the animals whose answers
	 * to these questions are "Don't Know"
	 */
	public IQuestionsHash getQuestionsHashDontKnow(){
		return hashAnswerDontKnow;
	}
	
	/**
     * Get a list of possible animals in the hash 
     * @return list of Strings, containing the names of the possible animals 
     */
	public List<String> getPossibleAnimalsList() {
		return animals;
	}
	
	/**
     * Get an array of possible animals in the hash 
     * @return array of Strings, containing the names of the possible animals 
     */
	public String[] getPossibleAnimalsArray() {
		return (String[])animals.toArray();
	}

	/**
     * Sets a new list of possible animals
     * @param animals list of Strings, containing names of animals
     */
	public void setPossibleAnimalsList(List<String> animals) {
		this.animals = animals;
	}
	
	/**
     * Sets a new list of possible animals
     * @param animals array of Strings, containing names of animals
     */
	public void setPossibleAnimalsArray(String[] animals) {
		clearPossibleAnimalsList();
		for(int i = 0; i < animals.length; i++){
			this.animals.add(animals[i]);			
		}
	}
	
	/**
	 * Resets the list of possible animals, so that all animals in the QuestionHashes
	 * become possible again.
	 */
	public void clearPossibleAnimalsList(){
		this.animals = new ArrayList<String>();
	}

	/**
     * Adds new animals to the list of possible animals, preserving the current animals
     * @param animals list of Strings, containing names of animals
     */
	public void addAnimalsList(List<String> animals){
		addAnimalsArray((String[])animals.toArray());
	}
	
	/**
     * Adds new animals to the list of possible animals, preserving the current animals
     * @param animals array of Strings, containing names of animals
     */
	public void addAnimalsArray(String[] animals){
		for(int i = 0; i < animals.length; i++){
			if(!this.animals.contains(animals[i])) 
				this.animals.add(animals[i]);
		}
	}
	
	/**
     * Removes animals from the list of possible animals, if animals received exist
     * @param animals list of Strings, containing names of animals
     */
	public void removeAnimalsList(List<String> animals){
		removeAnimalsArray((String[])animals.toArray());
	}
	
	/**
     * Removes animals from the list of possible animals, if animals received exist
     * @param animals array of Strings, containing names of animals
     */
	public void removeAnimalsArray(String[] animals){
		for(int i = 0; i < animals.length; i++){
			if(this.animals.contains(animals[i])) 
				this.animals.remove(animals[i]);
		}
	}
	
	/**
     * Gets the size of the list of possible animals, 
     * which is the number of possible animals.
     *  
     * @return number of animals. 
     */
	public int getNumberOfAnimals() {
		int size = 0;
		if(animals != null)
			size = animals.size();
		return size;
	}

	/**
     * Determines the possible animals that are previously possible and which also answers 
     * the question received with the same answer received by parameter.
     * 
     * @param question String of the question asked 
     * @param answer String of the answer given to the question
     */
	public void determinesPossibleAnimals(String question, String answer) {		
		if (answer.equalsIgnoreCase("sim")) {
				animals = mergeList(hashAnswerYes.getAnimals(question), animals);				
		} else if (answer.equalsIgnoreCase("nao")) {
				animals = mergeList(hashAnswerNo.getAnimals(question), animals);
		} else if (answer.equalsIgnoreCase("nao sei")) {
				animals = mergeList(hashAnswerDontKnow.getAnimals(question), animals);
		}
	}
	
	/**
	 * Receives two lists of Strings, and returns the items that both lists contain
	 * 
	 * @param l1 list of Strings
	 * @param l2 list of Strings
	 * @return list of Strings containing the l1 and l2 merged
	 */
	private List<String> mergeList(List<String> l1, List<String> l2){
		List<String> mergeList = new ArrayList<String>();
		int size;
		
		if (l1 == null || l1.isEmpty()) {
			mergeList = l2;
		} else if (l2 == null || l2.isEmpty()) {
			mergeList = l1;
		} else {
			if(l1.size() > l2.size())
				size = l1.size();
			else
				size = l2.size();
	
			for (int i = 0; i < size; i++) {
				if (l1.size() > l2.size()) {
					if (l2.contains(l1.get(i))) {
						mergeList.add(l1.get(i));
					}
				} else {
					if (l1.contains(l2.get(i))) {
						mergeList.add(l2.get(i));
					}
				}
			}
		}
		return mergeList;
	}
	
	/**
	 * Inserts the animals to the 3 hashes of answers, from the knowledge base.
	 */
	private void insertAnimalsHash(){
		for (int i = 0; (i < listNames.lenght); i++) {
			IObjetoConhecimento obj;
			obj = base.recuperaObjeto(listNames[i]);
			objs.put(listNames[i], obj);

			IDeclaracao decl = obj.primeira();

			while (decl != null) {
				if (!listQuestions.contains(decl.getPropriedade())) {
					insertAnswerHash(decl.getPropriedade());
					listQuestions.add(decl.getPropriedade());
				}
				decl = obj.proxima();
			}
		}
    }
	
	/**
	 * Inserts the questions and properties of the animals in the 3 hashes of answers,
	 * according to the properties of the animals. 
	 */
	private void insertAnswerHash(String question) {
		for (int i = 0; i < listNames.length; i++) {
			IResponder responder = responders.get(listNames[i]);
			if (responder == null) {
				responder = new Responder(listNames[i]);
				responders.put(listNames[i], responder);
			}

			String answer = responder.ask(question);

			if (answer.equalsIgnoreCase("sim")) {
				hashAnswerYes.putAnimal(question, listNames[i]);
			} else if (answer.equalsIgnoreCase("nao")) {
				hashAnswerNo.putAnimal(question, listNames[i]);
			} else if (answer.equalsIgnoreCase("nao sei")) {
				hashAnswerDontKnow.putAnimal(question, listNames[i]);
			}
		}
	}
}
