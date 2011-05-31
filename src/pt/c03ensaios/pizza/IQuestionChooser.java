package pt.c03ensaios.pizza;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Returns the best question to be asked to a IResponder so the given animal set 
 * is roughly (the nearest possible) reduced by the half.
 *
 * @author Giuliano Roberto Pinheiro
 * @author Lucas Dermonde Gonçalves
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.pizza.IQuestionChooser>")
public interface IQuestionChooser extends ISupports, pt.c03ensaios.gnrpack.IBestQuestionChooser {
	/**
	 * Sets the multithreading state of the component, if it got any. The default is false.
	 * @param value corresponding to the state of multithreading (default false).
	 */
	public void SetMultithreadingState(boolean value);
	/**
	 * @remark Use this method only before using the component. (Pizza-specific).
	 * @return boolean value indicating wether the component is using multithreading, when disponible.
	 */
	public boolean IsUsingMultithreading();
	/** 
	 * @param Vector<IQuestion> containing the questions to be processed.
	 * @param int telling how many animals are being considered.
	 * @return String with the best question to be asked.
	 */
	public String BestQuestion(Vector<IQuestion> questions, int animal_count);
	
	/**
	 * @param BaseConhecimento, already instanciated, that access the considered database.
	 * @param String array with the universe-set of animals being considered.
	 * @return String with the best question to be asked.
	 */
	public String BestQuestion(IBaseConhecimento base, String[] animals);
	
	/**
	 * @param PossibleAnimalsHash, already instanciated.
	 * @return String with the best question to be asked.
	 */
	public String BestQuestion (IPossibleAnimalsHash hash);
}
