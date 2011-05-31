package pt.c01interfaces.s01chaveid.marcelobarretto;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;

public class Animal {
	private String nome;
	private Vector<IDeclaracao> atributos;
		
	public Animal(String nome){
		this.nome = nome;
		atributos = new Vector<IDeclaracao>();
	}
	
	public void adicionarAtributo(IDeclaracao declaracao){
		atributos.add(declaracao);
	}
	
	public int numeroAtributos(){
		return atributos.size();
	}
	
	public String getNome(){
		return nome;
	}
	
	public String getPropriedade(){
		return atributos.get(0).getPropriedade();
	}
	
	public boolean temAtributo(IDeclaracao declaracao){
		boolean temAtributo = false;
		for(int i=0;i<atributos.size();i++){
			IDeclaracao decl = atributos.get(i);
			if(decl.getPropriedade().equalsIgnoreCase(declaracao.getPropriedade())){
				if(decl.getValor().equalsIgnoreCase(declaracao.getValor())){
					temAtributo = true;
					atributos.remove(i); //Para nao ser perguntado novamente
				}else
					break;
			} 				
		}
		
		return temAtributo;
	}
}
