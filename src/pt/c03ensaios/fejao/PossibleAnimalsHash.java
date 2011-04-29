package pt.c03ensaios.fejao;

import java.util.ArrayList;
import java.util.List;

import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.frango.QuestionsHash;

import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaiosfoundations.fejao.IPossibleAnimalsHash>" })
public class PossibleAnimalsHash extends ComponentBase implements IPossibleAnimalsHash {
	private List<String> animals = null;
	private IQuestionsHash hashAnswerYes;
	private IQuestionsHash hashAnswerNo;
	private IQuestionsHash hashAnswerDontKnow;
	
	public PossibleAnimalsHash(){
		try {
			// creates a global factory
			IGlobalFactory factory = ComponentContextFactory
					.createGlobalFactory();

			factory.registerPrototype(QuestionsHash.class);

			// instances the component based on its URI
			hashAnswerYes = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");

			hashAnswerNo = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");

			hashAnswerDontKnow = factory
					.createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public List<String> getPossibleAnimalsList() {
		return animals;
	}

	public void putPossibleAnimalsList(List<String> animals) {
		this.animals = animals;
	}

	public int getListSize() {
		int tamanho = 0;
		if(animals != null)
			tamanho = animals.size();
		return tamanho;
	}

	public void DeterminesPossibleAnimals(String question, String answer) {		
		if (answer.equalsIgnoreCase("sim")) {				
				animals = mergeList(hashAnswerYes.getAnimals(question), animals);				
		} else if (answer.equalsIgnoreCase("nao")) {
				animals = mergeList(hashAnswerNo.getAnimals(question), animals);
		} else if (answer.equalsIgnoreCase("nao sei")) {
				animals = mergeList(hashAnswerDontKnow.getAnimals(question), animals);
		}
	}
	
	private List<String> mergeList(List<String> l1, List<String> l2){
		List<String> mergeList = new ArrayList<String>();
		int size;
		
		if (l1.isEmpty()) {
			mergeList = l2;
		} else if (l2.isEmpty()) {
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
}
