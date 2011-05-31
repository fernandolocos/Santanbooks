package pt.c01interfaces.s01chaveid.marcelobarretto;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.impl.Declaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer {

	public EnquirerBasic(){
		this(new BaseConhecimento());
	}
	
	public EnquirerBasic(IBaseConhecimento base){
		this.base = base;
		listaAnimais = new Vector<Animal>();
		String[] nomesAnimais = this.base.listaNomes();
		for(int i=0;i<nomesAnimais.length;i++){
			Animal animal = new Animal(nomesAnimais[i]);
			IObjetoConhecimento objeto = base.recuperaObjeto(nomesAnimais[i]);
			IDeclaracao decl = objeto.primeira();
			while(decl != null){
				animal.adicionarAtributo(decl);
				decl = objeto.proxima();
			}
			listaAnimais.add(animal);
		}
		
	}
	@Override
	public void connect(IResponder responder) {
		animalMaisProvavel = getAnimalMaisProvavel();
		while (animalMaisProvavel != ""  && !achei){
			String pergunta = listaAnimais.get(indexMaisProvavel(animalMaisProvavel)).getPropriedade();
			String valor = responder.ask(pergunta);
			IDeclaracao declaracao = new Declaracao(pergunta, valor);
			atualizarListaAnimais(declaracao);			
		}
		
		if(achei){
			responder.finalAnswer(animalMaisProvavel);
		} else {
			responder.finalAnswer("nao conheco");
		}
	}
	
	//Remove do vetor de animais todos que não tiverem o atributo passado
	private void atualizarListaAnimais(IDeclaracao decl){
		for(int i=0;i<listaAnimais.size();i++){
			Animal animal = listaAnimais.get(i);
			if( ! animal.temAtributo(decl)){
				listaAnimais.remove(i);	
			}
		}
		animalMaisProvavel = getAnimalMaisProvavel();
	}
	
	private String getAnimalMaisProvavel(){
		int menor = listaAnimais.get(1).numeroAtributos();
		String maisProvavel = "";
		for(int i=0;i<listaAnimais.size();i++){
			if(listaAnimais.get(i).numeroAtributos()<menor){
				menor = listaAnimais.get(i).numeroAtributos();
				maisProvavel = listaAnimais.get(i).getNome();
			}
		}
		if(menor == 0){//Algum animal ja corresponde a todos os atributos
			achei = true;
		}
		
		return maisProvavel;
	}
	
	private int indexMaisProvavel(String nome){
		int i=0;
		for(int j=0;j<listaAnimais.size();j++){
			if(listaAnimais.get(j).getNome().equalsIgnoreCase(nome)){
				i = j;
				break;
			}
		}
		return i;
	}
	
	private IBaseConhecimento base;
	private Vector<Animal> listaAnimais;
	private String animalMaisProvavel;
	private boolean achei = false;
}
