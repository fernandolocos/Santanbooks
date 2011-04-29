package pt.c03ensaios.fejao;

import java.util.List;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>", 
		provides = { "<http://purl.org/dcc/pt.c03ensaiosfoundations.fejao.IPossibleAnimalsHash>" })
public class PossibleAnimalsHash extends ComponentBase implements IPossibleAnimalsHash {
	private List<String> animals = null;
	
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
}
