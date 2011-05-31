package pt.c03ensaios.frango.app;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.naxxramas.ComponentNotSetException;
import pt.c03ensaios.naxxramas.IEnquirerComponent;
import pt.c03ensaios.pizza.IQuestionChooser;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.frango.app.EnquirerAdvanced>",
	    provides="<http://purl.org/dcc/pt.c03ensaios.naxxramas.IEnquirerComponent>")
public class EnquirerAdvanced extends ComponentBase implements IEnquirerComponent, IPossibleAnimalsHashReceptacle, IQuestionChooserReceptacle{
	private IBaseConhecimento baseConhecimento;
	private IPossibleAnimalsHash possibleAnimalsHash;
	private IQuestionChooser questionChooser;
	
	//overrride of IEnquirerComponent
	public String getContentName() throws ComponentNotSetException {
		return "pt.c03ensaios.frango.app.EnquirerAdvanced";
	}
	
	//overrride of IEnquirerComponent
	public void start() throws InstantiationException, IllegalAccessException {
		baseConhecimento = new BaseConhecimento();
	}

	//connect for IPossibleAnimalsHash
	public void connect(IPossibleAnimalsHash possibleAnimalsHash) {
		this.possibleAnimalsHash = possibleAnimalsHash;	
	}

	//connect for IQuestionChooser
	public void connect(IQuestionChooser questionChooser) {
		this.questionChooser = questionChooser;
	}
	
	//connect for this component
	public void connect(IResponder responder) {
		boolean encontrado = false;
		String pergunta;
		
		possibleAnimalsHash.clearPossibleAnimalsList();
		
		while(!encontrado){
			pergunta = questionChooser.BestQuestion(baseConhecimento, possibleAnimalsHash.getPossibleAnimalsArray());			
			
			possibleAnimalsHash.determinesPossibleAnimals(pergunta,responder.ask(pergunta));
			
			if(possibleAnimalsHash.getNumberOfAnimals()==1)
				encontrado = true;
		}

		if (responder.finalAnswer(possibleAnimalsHash.getPossibleAnimalsArray()[0])) {
			System.out.println("Oba! Acertei! - " + possibleAnimalsHash.getPossibleAnimalsArray()[0]);
		} else {
			System.out.println("fuem! fuem! fuem!");
		}		
	}
}
