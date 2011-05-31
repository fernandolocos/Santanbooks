package pt.c03ensaios.linnaeus.impl;

import anima.annotation.Component;
import anima.component.base.ComponentBase;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.linnaeus.receptacles.IReceptaclePossibleAnimals;
import pt.c03ensaios.linnaeus.receptacles.IReceptacleQuestionChooser;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.pizza.IQuestionChooser;

/**
 * This component unites three others components in order to 
 * discover an animal making the lesser number of questions 
 * as possible.
 * 
 * @author Oscar dos Santos Esgalha Neto
 * @author Wesley Tetsuya Schabert Takiguti Ide
 *
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.linnaeus.impl.EnquirerAdvanced>", 
		provides = "<http://purl.org/dcc/pt.c03ensaios.optimizers.IEnquirerSupports>")
public class EnquirerAdvanced extends ComponentBase implements IEnquirerSupports, IReceptacleAnimalsDatabase, IReceptacleQuestionChooser, IReceptaclePossibleAnimals 
{
    IAnimalsDatabase db;
    IPossibleAnimalsHash excluder;
    IQuestionChooser qustgetter;
	
	@Override
	public void connect(IAnimalsDatabase AnimalsDatabase) {
		db = AnimalsDatabase;
	}

	@Override
	public void connect(IQuestionChooser QuestionChooser) {
		qustgetter = QuestionChooser;
	}

	@Override
	public void connect(IPossibleAnimalsHash PossibleAnimals) {
		excluder = PossibleAnimals;
	}
	
	@Override
	/**
	 * 
	 * Nome: connect
	 * 
	 * Parâmetros: responder: usuário que contém o conteúdo a ser adivinhado
	 * 
	 * Função: Função principal que inicia o programa.
	 * 
	 */
	public void connect(IResponder responder)
	{
		// Adiciona todos os animais da base para o "excluidor"
		IBaseConhecimento bc = new BaseConhecimento();
		excluder.clearPossibleAnimalsList();
		excluder.addAnimalsArray(db.getNomes());
		// Enquanto houverem mais de um animal poss�vel, pegar a melhor
		// pergunta poss�vel (via component QuestionChooser) e depois
		// enviar essa pergunta ao excluidor
		while(excluder.getNumberOfAnimals() > 1) {
			String perg = qustgetter.BestQuestion(bc, excluder.getPossibleAnimalsArray());
			excluder.determinesPossibleAnimals(perg, responder.ask(perg));
		}
		// A resposta � o �nico animal que sobrou
		responder.finalAnswer(excluder.getPossibleAnimalsArray()[0]);
	}

}
