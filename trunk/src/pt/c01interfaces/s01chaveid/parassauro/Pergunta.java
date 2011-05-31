package pt.c01interfaces.s01chaveid.parassauro;

import java.util.HashSet;

import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;

/**
 * Esta classe representa uma pergunta e armazena os nomes de animais que a
 * possuem. Há, para isso, listas conforme as respostas dos animais para esta
 * pergunta. Se o animal possui esta pergunta, então seu nome deve ser
 * armazenado nas listas "sim" ou "nao", conforme seja sua resposta.
 */
public class Pergunta implements Comparable<Pergunta>{
	
	/** Texto da pergunta */
	private String enunciado;
	/** Lista de animais que tenham esta pergunta e com resposta "sim" */
	private HashSet<String> sim;
	/** Lista de animais que tenham esta pergunta e com resposta "nao" */
	private HashSet<String> nao;
	
	/**
	 * Constrói a estrutura da pergunta.
	 * 
	 * @param enunciado O texto da pergunta.
	 */
	public Pergunta(String enunciado){
		this.enunciado=enunciado;
		sim=new HashSet<String>();
		nao=new HashSet<String>();
	}
	
	/**
	 * Retorna o texto da pergunta.
	 * 
	 * @return o texto da pergunta.
	 */
	public String getEnunciado(){
		return this.enunciado;
	}
	
	/**
	 * Retorna a lista de animas com resposta "sim" para esta pergunta.
	 * 
	 * @return um {@link HashSet} com a lista de animas com resposta "nao" para
	 * esta pergunta.
	 */
	public HashSet<String> getSim(){
		return this.sim;
	}
	
	/**
	 * Retorna a lista de animas com resposta "nao" para esta pergunta.
	 * 
	 * @return um {@link HashSet} com a lista de animas com resposta "nao" para
	 * esta pergunta.
	 */
	public HashSet<String> getNao(){
		return this.nao;
	}
	
	/**
	 * Insere um animal para esta pergunta, caso este a possua em seu rol de
	 * perguntas retornando true. Caso não a possua, não insere e retorna false.
	 * 
	 * @param animal Nome do animal.
	 * @param declaracao {@link IDeclaracao} para (1) checar se a propriedade
	 * é igual esta pergunta e (2) saber em qual lista inserir o animal, se for.
	 * @return true se o animal foi inserido, e false caso contrário.
	 */
	public boolean insertDeclaracao(String animal,IDeclaracao declaracao){
		if(declaracao.getPropriedade().equals(this.enunciado)){
			if(declaracao.getValor().equals("sim"))
				sim.add(animal);
			else
				nao.add(animal);
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna o número de animais que têm esta pergunta.
	 * 
	 * @return o número de animais que têm esta pergunta.
	 */
	public int size(){
		return this.sim.size()+this.nao.size();
	}
	
	/**
	 * Compara duas perguntas para ordenação. O objetivo é que uma pergunta
	 * com mais animais seja considerada "menor" de forma que seja posta antes
	 * de uma pergunta com menos animais. Assim, as perguntas que possuem mais
	 * animais, vão ser perguntadas antes, pois esperamos diminuir o número de
	 * possíveis animais. 
	 */
	@Override
	public int compareTo(Pergunta o){
		return (this.size()==o.size()?0:(this.size()<o.size()?1:-1));
	}
	
	/**
	 * Remove o animal da pergunta.
	 * 
	 * @param animal Nome do animal a ser removido.
	 */
	public void removeAnimal(String animal){
		if(!this.nao.remove(animal))
			this.sim.remove(animal);
	}
}