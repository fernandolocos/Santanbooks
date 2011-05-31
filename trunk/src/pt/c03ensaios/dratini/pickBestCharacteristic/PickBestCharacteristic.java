package pt.c03ensaios.dratini.pickBestCharacteristic;

import java.util.List;

import anima.annotation.Component;
import anima.component.base.ComponentBase;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;

/**
 * Selects the best question to be asked next by finding the one 
 * that best divides the animals in three groups.
 * 
 * @author Kamila Galvani
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.PickBestCharacteristic>",
           provides = {"<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.IQuestionChooser>", 
						"<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser>"})


public class PickBestCharacteristic extends ComponentBase implements IQuestionChooser, IBestChooser{
	CharacteristicsListComponent chars = new CharacteristicsListComponent();
	public PickBestCharacteristic(){
	}
	
	public String selectBestQuestion(BaseConhecimento BaseC, String[] animais){
		chars.resetCharacteristics();
		for (int i=0; i<animais.length; i++){
			chars.sumAnswers2Characteristics(animais[i]);
		}
		chars.calculateEntropy();
		Caracteristica cara = chars.selectBestEntropy();
		return cara.getNome();
	}

	@Override
	public String selectBestQuestion(BaseConhecimento base, List<String> animals) {
		int s = animals.size();
		String[] newAnimals = new String[s];
		for (int i=0; i<s; i++){
			newAnimals[i] = animals.get(i);
		}
		return selectBestQuestion(base, newAnimals);
	}
}
