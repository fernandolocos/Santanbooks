package pt.c03ensaios.dratini.componentsBasedEnquirer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.pizza.IQuestionChooser;


/**
 * Enquirer that uses pizza's, frango's and debolacha's components.
 * 
 * @author Kamila Galvani
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.componentsBasedEnquirer.ComponentsBasedEnquirer>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaios.optimizers.IEnquirerSupports>"})

public class ComponentsBasedEnquirer extends ComponentBase 
	implements IEnquirer, IEnquirerSupports, IRequires<IQuestionChooser>,
				IReceptacleQuestionsHashs, IReceptacleAnimalLists{
	BaseConhecimento base = new BaseConhecimento();
	String[] animals;
	IAnimalList possible; //debolacha's
	IAnimalList aux;
	String question = null;
	IQuestionsHash questionHashSim; //frango's
	IQuestionsHash questionHashNao;
	IQuestionsHash questionHashTalvez;
	IQuestionChooser questionChooser; //pizza's
	
	public ComponentsBasedEnquirer(){
		animals = base.listaNomes();
	}

	@Override
	public void connect(IResponder responder) {
		initializeLists();
		String answer;
	       
        while(possible.size()>1){
        	
        	int s = possible.size();
    		String[] newAnimals = new String[s];
    		for (int i=0; i<s; i++){
    			newAnimals[i] = possible.get(i);
    		}
        	
	        question = questionChooser.BestQuestion(base, newAnimals);
	        //System.out.println(question);
	        
	        answer = responder.ask(question);
	        //System.out.println("-"+answer);
	        
	        List<String> aux2;
	        
	        if (answer.equalsIgnoreCase("sim"))
	        	aux2 =  questionHashSim.getAnimals(question);
	        else if (answer.equalsIgnoreCase("nao"))
	        	aux2 =  questionHashNao.getAnimals(question);
	        else 
	        	aux2 =  questionHashTalvez.getAnimals(question);

	        aux.clear();
	        for (int j=0; j<aux2.size(); j++){
	        	aux.add(aux2.get(j));
	        }
        
	        possible = possible.intersec(aux);
        }
        //System.out.println(possible.get(0));
        /*boolean result = */responder.finalAnswer(possible.get(0));
        //System.out.println(result);
        
	}
	
	private void initializeLists(){
		possible.clear();
	    for (int i=0; i<animals.length; i++){
        	possible.add(animals[i]);
        }
	}
	
	
	private void initializeHashs(){      
        Set<String> questions = new HashSet<String>();
        
        
        for (int i=0; i<animals.length; i++){
			IObjetoConhecimento obj = base.recuperaObjeto(animals[i]);
			IDeclaracao decl = obj.primeira();
			while (decl != null){
				questions.add(decl.getPropriedade());
				decl = obj.proxima();
			}
		}
        boolean found=false;
        for (int i=0; i<animals.length; i++){
			IObjetoConhecimento obj = base.recuperaObjeto(animals[i]);
			IDeclaracao decl = obj.primeira();
			Iterator<String> itr = questions.iterator();
			String now;
			while (itr.hasNext()){
				now = itr.next().toString();
				decl = obj.primeira();
				found = false;
				while ((decl != null) && (!found)){
					if (decl.getPropriedade().equalsIgnoreCase(now)){
						found = true;
						if (decl.getValor().equalsIgnoreCase("nao"))
							questionHashNao.putAnimal(decl.getPropriedade(), animals[i]);
						else if (decl.getValor().equalsIgnoreCase("sim"))
							questionHashSim.putAnimal(decl.getPropriedade(), animals[i]);
					}
					decl = obj.proxima();
				}
				if (!found){
					questionHashTalvez.putAnimal(now, animals[i]);
				}
			}
        }
	}

	@Override
	public void connect(IQuestionChooser arg0) {
		this.questionChooser = arg0;
	}

	@Override
	public void connect(IQuestionsHash hashSim, IQuestionsHash hashNao,
			IQuestionsHash hashTalvez) {
		this.questionHashNao = hashNao;
		this.questionHashSim = hashSim;
		this.questionHashTalvez = hashTalvez;
		initializeHashs();
	}

	@Override
	public void connect(IAnimalList lista1, IAnimalList lista2) {
		this.possible = lista1;
		this.aux = lista2;
		initializeLists();
	}
}
