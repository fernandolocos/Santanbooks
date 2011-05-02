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
import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaiosfoundations.fejao.IPossibleAnimalsHash>" },
        requires={"<http://purl.org/dcc/pt.c03ensaios.frangoIQuestionsHash>"})
public class PossibleAnimalsHash extends ComponentBase implements IPossibleAnimalsHash, IRequires<IQuestionsHash>{
	private List<String> animals;
	private static IBaseConhecimento base;
	private static String[] listNames;
	private static IQuestionsHash hashAnswerYes;
	private static IQuestionsHash hashAnswerNo;
	private static IQuestionsHash hashAnswerDontKnow;
	private static List<String> listQuestions = new ArrayList<String>();
	private static HashMap<String, IResponder> responders = new HashMap<String, IResponder>();
	private static HashMap<String, IObjetoConhecimento> objs = new HashMap<String, IObjetoConhecimento>();
	private static Boolean inserted = false;
	private static int currentHash = 0;

	public PossibleAnimalsHash(){
		this.animals = new ArrayList<String>();
		base = new BaseConhecimento();
		listNames = base.listaNomes();

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
			} catch (Exception e) {
				e.printStackTrace();
			}

			listQuestions = new ArrayList<String>();

			insertAnimalsHash();
			inserted = true;
		}
	}
	
	public PossibleAnimalsHash(IQuestionsHash hashAnswerYes, IQuestionsHash hashAnswerNo, IQuestionsHash hashAnswerDontKnow){
		this.animals = new ArrayList<String>();
		base = new BaseConhecimento();
		listNames = base.listaNomes();

		if (inserted == false) {
			try {
				IGlobalFactory factory = ComponentContextFactory
						.createGlobalFactory();

				factory.registerPrototype(QuestionsHash.class);

				connect(hashAnswerYes);
				connect(hashAnswerNo);
				connect(hashAnswerDontKnow);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			listQuestions = new ArrayList<String>();
			insertAnimalsHash();
			inserted = true;
		}
	}
	
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
	
	public void insertAnimalsHash(){
		for (int i = 0; (i < listNames.length); i++) {
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
