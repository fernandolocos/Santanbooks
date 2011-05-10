package pt.c03ensaios.linnaeus;

import java.util.HashMap;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c03ensaios.tochinhas2.impl.IQuestions;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>",
        provides={"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>"})

public class AnimalsDatabase extends ComponentBase implements IAnimalsDatabase, IRequires<IQuestions> {
	
	private HashMap<String, AnimalData> animais;
	private IQuestions perguntas;
	
	public AnimalsDatabase() {
		IBaseConhecimento bc = new BaseConhecimento();
		String[] nomes = bc.listaNomes();
		animais = new HashMap<String, AnimalData>(nomes.length);
		for (int i = 0; i < nomes.length; i++)
			animais.put(nomes[i], new AnimalData(nomes[i]));
	}
	
	public int getNAnimais() {
		return animais.size();
	}
	
	public IAnimalData[] getAnimais() {
		return animais.values().toArray(new IAnimalData[getNAnimais()]);
	}
	
	public String[] getPerguntas() {
		return perguntas.getQuestions().toArray(new String[perguntas.getQuestions().size()]);
	}

	public IAnimalData getAnimal(String nome) {
		return animais.get(nome);
	}

	public void connect(IQuestions perguntas) {
		this.perguntas = perguntas;
	}
}
