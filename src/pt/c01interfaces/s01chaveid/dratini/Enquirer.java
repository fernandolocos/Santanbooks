package pt.c01interfaces.s01chaveid.dratini;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public abstract class Enquirer implements IEnquirer {
	protected BaseConhecimento baseC = new BaseConhecimento();
	private No arvore;
	
	
	protected abstract No createTree();
	
	protected Vector<String> criarVetorAnimais(){
		String[] nomes = baseC.listaNomes();
		Vector<String> animais = new Vector<String>();
		for (int i=0; i<nomes.length; i++){
			animais.add(nomes[i]);
		}
		return animais;
	}
	
	protected Vector<Caracteristica> copia(Vector<Caracteristica> ca){
		Vector<Caracteristica> newC = new Vector<Caracteristica>();
		for (int i=0; i<ca.size(); i++){
			newC.add(ca.get(i));
		}
		return newC;
	}
	
	protected Vector<Vector<String>> separaPorCategoria(No no, Vector<String> animais){
		
		Vector<Vector<String>> result = new Vector<Vector<String>>();
		Vector<String> naos = new Vector<String>();
		Vector<String> sims = new Vector<String>(); 
		Vector<String> naoseis = new Vector<String>(); 
		Boolean possuiCaracteristica = false;
		
		for (int i=0; i<animais.size(); i++){
			String animal = animais.get(i);
			if (animal==null)
				break;
			IObjetoConhecimento obj = baseC.recuperaObjeto(animais.get(i));
			IDeclaracao decl = obj.primeira();
			while (decl != null){
				if (decl.getPropriedade().equalsIgnoreCase(no.getNome())){
					possuiCaracteristica = true;
					if (decl.getValor().equalsIgnoreCase("nao"))
						naos.add(animal);
					else if (decl.getValor().equalsIgnoreCase("sim"))
						sims.add(animal);
					break;
				}
				decl = obj.proxima();
			}
			if (!possuiCaracteristica)
				naoseis.add(animal);
			possuiCaracteristica=false;
		}
		result.add(naos);
		result.add(sims);
		result.add(naoseis);
		
		return result;
	}
	
	protected Vector<Caracteristica> criaListaCaracteristicas(Vector<String> nomes){
		if (nomes==null || nomes.size()<1)
			return null;
		boolean found = false;
		Vector<Caracteristica> caracteristicas = new Vector<Caracteristica>();
		for (int i=0; i<nomes.size(); i++){
			IObjetoConhecimento obj = baseC.recuperaObjeto(nomes.get(i));
			IDeclaracao declara = obj.primeira();
			while (declara != null){
				Caracteristica car = new Caracteristica(declara.getPropriedade());
				for (int j=0; j<caracteristicas.size(); j++){
					if (caracteristicas.get(j).getNome().equalsIgnoreCase(car.getNome())){
						found = true;
					}
				}
				if (!found){
					caracteristicas.add(car);
				}
				found = false;
				declara = obj.proxima();
			}
		}
		return caracteristicas;
	}
	
	
	
	
	
	protected void encontraAnimal(No tree, IResponder responder){
		if (tree.getNaos()==null&&tree.getNaoseis()==null&&tree.getSims()==null){
			System.out.println(tree.getNome());
			Boolean teste = responder.finalAnswer(tree.getNome());
			System.out.println("-"+teste.toString());
		}
		else
		{
			System.out.println(tree.getNome());
			String resposta;
			resposta = responder.ask(tree.getNome());
			System.out.println("-"+resposta);
			if (resposta.equalsIgnoreCase("sim"))
				encontraAnimal(tree.getSims(), responder);
			else if (resposta.equalsIgnoreCase("nao"))
				encontraAnimal(tree.getNaos(), responder);
			else
				encontraAnimal(tree.getNaoseis(), responder);
		}
	}
	
	public Enquirer(){
		arvore = createTree();
	}
	
	public void connect(IResponder responder) {
		encontraAnimal(arvore, responder);
		
	}

}
