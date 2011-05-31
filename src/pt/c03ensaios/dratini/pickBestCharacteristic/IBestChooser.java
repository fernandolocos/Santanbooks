package pt.c03ensaios.dratini.pickBestCharacteristic;

import java.util.List;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c03ensaios.gnrpack.IBestQuestionChooser;
import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface that finds the best question to be asked based on a certain group of animals.
 * @author Kamila Galvani
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser>")

public interface IBestChooser extends IQuestionChooser, IBestQuestionChooser, ISupports {
	/**
	 * Selects the best question
	 * @param base object of type BaseConhecimento
	 * @param animals vector with the names of the animals in the subgroup for which a question is needed
	 * @return string with question
	 */
	public abstract String selectBestQuestion(BaseConhecimento base, String[] animals);
	
	/**
	 * Selects the best question
	 * @param base object of type BaseConhecimento
	 * @param animals list with the names of the animals in the subgroup for which a question is needed
	 * @return string with question
	 */
	public abstract String selectBestQuestion(BaseConhecimento base, List<String> animals);

}
