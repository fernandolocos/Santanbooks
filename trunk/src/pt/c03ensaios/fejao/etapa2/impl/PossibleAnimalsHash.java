package pt.c03ensaios.fejao.etapa2.impl;

import java.util.ArrayList;
import java.util.List;

import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.linnaeus.IAnimalData;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.InterfaceType;
import anima.component.base.ComponentBase;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaiosfoundations.fejao.IPossibleAnimalsHash>" },
		requires={"<http://purl.org/dcc/pt.c03ensaios.frango.IQuestionsHash>",
					"<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalList>",
					"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>",
                    "<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>"})
public class PossibleAnimalsHash extends ComponentBase implements IPossibleAnimalsHash, IReceptacleQuestionsHash, IReceptacleAnimalList, IReceptacleAnimalsDatabase, IReceptacleQuestions{
	private List<String> animals;
	private static IAnimalList animalList;
	private static IQuestionsHash hashAnswerYes;
	private static IQuestionsHash hashAnswerNo;
	private static IQuestionsHash hashAnswerDontKnow;
	private static boolean inserted = false;
	private static int currentHash = 0;
	private static IQuestions questions;
	private static IAnimalsDatabase animalsDatabase;
	
	/**
	 * Class constructor with no arguments
	 */
	public PossibleAnimalsHash(){
		animals = new ArrayList<String>();
		inserted = false;
		currentHash = 0;
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
	 * Connects an IAnimalList object and sets it to the local arguments.
	 * Method that connects the interface IAnimalList.
	 * 
	 * @param animalList IAnimalList object to be connected.
	 */
	public void connect(IAnimalList animalList) {
		PossibleAnimalsHash.animalList = animalList;
	}
	
	/**
	 * Connects an IAnimalsDatabase object and sets it to the local arguments.
	 * 
	 * @param animalsDatabase IAnimalsDatabase object to be connected.
	 */
	public void connect(IAnimalsDatabase animalsDatabase) {
		PossibleAnimalsHash.animalsDatabase = animalsDatabase;
	}

	/**
	 * Connects an IAnimalsDatabase object and sets it to the local arguments.
	 * 
	 * @param animalsDatabase IAnimalsDatabase object to be connected.
	 */
	public void connect(IQuestions questions) {
		PossibleAnimalsHash.questions = questions;
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
		return (String[])animals.toArray(new String[0]);
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
	public void setPossibleAnimalsArray(String[] animals){
		this.animals = new ArrayList<String>();
		for(int i = 0; i < animals.length; i++){
			this.animals.add(animals[i]);			
		}
	}
	
	/**
	 * Resets the list of possible animals, so that all animals in the QuestionHashes
	 * become possible again.
	 */
	public void clearPossibleAnimalsList(){
		if(!inserted){
			insertAnimalsHash();
			inserted = true;
		}
		setPossibleAnimalsArray(animalsDatabase.getNomes());
	}

	/**
     * Adds new animals to the list of possible animals, preserving the current animals
     * @param animals list of Strings, containing names of animals
     */
	public void addAnimalsList(List<String> animals){
		addAnimalsArray((String[])animals.toArray(new String[0]));
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
		removeAnimalsArray((String[])animals.toArray(new String[0]));
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
				animals = intersectList(hashAnswerYes.getAnimals(question), animals);				
		} else if (answer.equalsIgnoreCase("nao")) {
				animals = intersectList(hashAnswerNo.getAnimals(question), animals);
		} else if (answer.equalsIgnoreCase("nao sei")) {
				animals = intersectList(hashAnswerDontKnow.getAnimals(question), animals);
		}
	}
	
	/**
	 * Receives two lists of Strings, and returns the items that both lists contain
	 * 
	 * @param l1 list of Strings
	 * @param l2 list of Strings
	 * @return list of Strings containing the l1 and l2 intersected
	 */
	private List<String> intersectList(List<String> l1, List<String> l2){
		List<String> mergeList = new ArrayList<String>();
		
		if (l1 == null || l1.isEmpty()) {
			mergeList = l2;
		} else if (l2 == null || l2.isEmpty()) {
			mergeList = l1;
		} else {
			String[] list1 = (String[])l1.toArray(new String[l1.size()]);
			animalList.clear();
			animalList.setList(list1);
			animalList = animalList.intersec(l2);
			mergeList.addAll(animalList);
		}
		return mergeList;
	}
	
	/**
	 * Inserts the animals to the 3 hashes of answers, from the knowledge base.
	 */
	private void insertAnimalsHash(){
        IRequires<IAnimalsDatabase> connectQuestions = 
        	questions.queryInterface("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>", InterfaceType.REQUIRED);
        IRequires<IQuestions> connectDatabase = 
        	animalsDatabase.queryInterface("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>", InterfaceType.REQUIRED);
        connectQuestions.connect(animalsDatabase);
        connectDatabase.connect(questions);
        for (String question : animalsDatabase.getPerguntas()){
            for (IAnimalData animal : animalsDatabase.getAnimais()) {
                
            	String answer = animal.getResposta(question);

                if (answer.equalsIgnoreCase("sim")) {
                    hashAnswerYes.putAnimal(question, animal.getNome());
                } else if (answer.equalsIgnoreCase("nao")) {
                    hashAnswerNo.putAnimal(question, animal.getNome());
                } else if (answer.equalsIgnoreCase("nao sei")) {
                    hashAnswerDontKnow.putAnimal(question, animal.getNome());
                }
            }
        }
    }
}
