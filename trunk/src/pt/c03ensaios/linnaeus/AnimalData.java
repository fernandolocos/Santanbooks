package pt.c03ensaios.linnaeus;

import java.util.HashMap;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalData>",
        provides={"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalData>"})
        
public class AnimalData extends ComponentBase implements IAnimalData {
	private String nome;
	private HashMap<String, String> perguntas;
	private int nperguntas;
	
	public AnimalData(String nome) {
		this.nome = nome;
		IBaseConhecimento bc = new BaseConhecimento();
		IObjetoConhecimento obj = bc.recuperaObjeto(nome);
		
		NPerguntas(obj);
		perguntas = new HashMap<String, String>(nperguntas);
		
		preencheHash(obj);
	}
	
	private void NPerguntas(IObjetoConhecimento animal) {
		int nperguntas = 0;
		IDeclaracao decl = animal.primeira();
		do { 
			nperguntas++; 
			decl = animal.proxima();
		} while (decl  != null);
	}
	
	private void preencheHash(IObjetoConhecimento animal) {
		IDeclaracao decl = animal.primeira();
		do {
			perguntas.put(decl.getPropriedade(), decl.getValor());
		} while ((decl = animal.proxima()) != null);
	}

	public String getNome() {
		return nome;
	}

	public int getNperguntas() {
		return nperguntas;
	}
	
	public String getResposta(String pergunta) {
		String resposta = perguntas.get(pergunta);
		return resposta == null ? "nao sei" : resposta;
	}
	
	public String[] getPerguntas() {
		return perguntas.keySet().toArray(new String[perguntas.keySet().size()]);
	}
	
}
