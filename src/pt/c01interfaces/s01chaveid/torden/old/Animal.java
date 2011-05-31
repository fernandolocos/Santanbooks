package pt.c01interfaces.s01chaveid.torden.old;

/*
 * Classe que representa um dos animais da base.
 */
public class Animal implements Comparable<Animal> {
	private String nome;
	private int pergRestantes;
	
	public Animal(String nome) {
		this.nome = nome;
		this.pergRestantes = 0;
	}
	
	public void incrementaPerguntas() {
		pergRestantes++;
	}
	
	public void decrementaPerguntas() {
		pergRestantes--;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getPergRestantes() {
		return pergRestantes;
	}
	
	@Override
	/* Implementacao da ordem natural de comparacao para a PQ.
	 * O Animal com menor pergRestantes e' o prioritario.
	 * 
	 */
	public int compareTo(Animal arg) {
		return pergRestantes - arg.pergRestantes;
	}
	
	/*
	 * TEMP ???
	 */
	public String toString() {
		return(nome + "; " + pergRestantes);
	}

}
