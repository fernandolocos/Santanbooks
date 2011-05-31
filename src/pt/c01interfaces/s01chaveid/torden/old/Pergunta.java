package pt.c01interfaces.s01chaveid.torden.old;

import java.util.LinkedList;

/*
 * Classe que representa uma pergunta.
 */
public class Pergunta implements Comparable<Pergunta> {
	private String propriedade;
	// O numero de animais que tem a pergunta. 
	private int peso;
	// Listas de referencias de animais por valor da propriedade.
	private LinkedList<Animal> simList;
	private LinkedList<Animal> naoList;
	
	public Pergunta(String propriedade) {
		this.propriedade = propriedade;
		simList = new LinkedList<Animal>();
		naoList = new LinkedList<Animal>();
		peso = 0;
	}
	
	public String getPropriedade() {
		return propriedade;
	}
	
	public int getPeso() {
		return peso;
	}
	
	/*
	 * Retorna uma referencia para a lista com o valor especificado.
	 */
	public LinkedList<Animal> getList(String valor) {
		LinkedList<Animal> lista = null;
		if (valor.equalsIgnoreCase("sim")) {
			lista = simList;
		} else {
			lista = naoList;
		}
		
		return lista;
	}
	
	/*
	 * Insere um animal na lista especificada.
	 */
	public void insereAnimal(Animal animal, String valor) {
		if (valor.equalsIgnoreCase("sim"))
			simList.add(animal);
		else
			naoList.add(animal);
	}
	
	public void incrementaPeso() {
		peso++;
	}

	@Override
	/*
	 * Implementacao da ordem natural de comparacao da classe.
	 * Ordena do maior peso para o menor.
	 */
	public int compareTo(Pergunta arg) {
		return arg.peso - peso;
	}
	
	/*
	 * TEMP ???
	 */
	public String toString() {
		return propriedade + "; " + peso;
	}
	
}
