package pt.c03ensaios.torden;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

import pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.optimizers.IEnquirerSupports;

/**
 * Just a "componentized" Enquirer. Must be connected to the required components
 * before it becomes ready to be used.
 * 
 * @author Waldir Rodrigues de Almeida
 *
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.torden.ComponentEnquirer>",
        provides = {"<http://purl.org/dcc/pt.c03ensaios.torden.IComponentEnquirer>"},
        requires = {"<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimals>",
					"<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser>",
					"<http://purl.org/dcc/pt.c03ensaios.torden.IQuestionMemory>"})
public class ComponentEnquirer extends ComponentBase 
								implements IComponentEnquirer, 
									IEnquirerSupports,
									IBestChooserReceptacle,
									IPossibleAnimalsHashReceptacle,
									IQuestionMemoryReceptacle {

	private IBaseConhecimento bc;
	private IPossibleAnimalsHash animalsHash; // fejao's
	private IBestChooser questionChooser; // dratini's
	private IQuestionMemory questionMemory;
	
	public ComponentEnquirer() {
		super();
	}
	
	/*
	 * Checks if the essential components were connected to the Enquirer.
	 */
	private boolean checkEssentialConnects() {
		return (animalsHash != null && questionChooser != null);
	}
	
	@Override
	public void connect(IResponder responder) {
		
		String question, answer;
		
		if (checkEssentialConnects()) {
			
			animalsHash.clearPossibleAnimalsList();
			
			if (questionMemory != null) {
				questionMemory.clear();
			}
			
			while (animalsHash.getNumberOfAnimals() != 1) {
				question = questionChooser.selectBestQuestion((BaseConhecimento) bc, 
								animalsHash.getPossibleAnimalsList());
				answer = responder.ask(question);
				
				// This is optional
				if (questionMemory != null) {
					questionMemory.putNewAnsweredQuestion(question, answer);
				}
				
				animalsHash.determinesPossibleAnimals(question, answer);
			}
			
			String finalAnswer = animalsHash.getPossibleAnimalsList().get(0);
			
			if (responder.finalAnswer(finalAnswer))
				System.out.println("CORRETO, " + finalAnswer);
			else
				System.out.println("INCORRETO, " + finalAnswer);
			
		} else {
			System.out.println("Lacking connection to essential components.");
		}
		
		
	}

	@Override
	public void connect(IQuestionMemory questionMemory) {
		this.questionMemory = questionMemory;
	}

	@Override
	public void connect(IBestChooser questionChooser) {
		this.questionChooser = questionChooser;
	}

	@Override
	public void connect(IPossibleAnimalsHash animalsHash) {
		this.animalsHash = animalsHash;
    	IBaseConhecimento bc = new BaseConhecimento();
    	String[] nomes = bc.listaNomes();
		this.animalsHash.addAnimalsArray(nomes);
	}

}
