package pt.c01interfaces.s01chaveid.relu;

import java.util.LinkedList;

import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;

public class Animal implements IAnimal {

	private String nome;
	private IObjetoConhecimento obj;
	private LinkedList<String> perguntas = new LinkedList<String>();
	private LinkedList<String> respostas = new LinkedList<String>();
	
	public Animal (String animal, IBaseConhecimento bc)
	{
		nome = animal;
		obj = bc.recuperaObjeto(animal);		
		geraListaDeDeclaracoes();
	}

	private void geraListaDeDeclaracoes(){
		IDeclaracao decl = obj.primeira();
		while(decl != null){
			perguntas.add(decl.getPropriedade());
			respostas.add(decl.getValor());
			decl = obj.proxima();
		}
	}
	
	
	public LinkedList<String> getPerguntas() {
		return perguntas;
	}

	public LinkedList<String> getRespostas() {
		return respostas;
	}

	public IObjetoConhecimento getObjetoConhecimento() {
		return obj;
	}
	public String getNome() {
		return nome;
	}


}
