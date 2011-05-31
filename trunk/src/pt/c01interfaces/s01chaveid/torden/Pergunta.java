package pt.c01interfaces.s01chaveid.torden;

import java.util.LinkedList;

public class Pergunta {
	private String pergunta;
	private LinkedList<String> listaSim;
	private LinkedList<String> listaNao;
	private int numAnimais;
	
	public Pergunta(String pergunta) {
		this.pergunta = pergunta;
		listaSim = new LinkedList<String>();
		listaNao = new LinkedList<String>();
		numAnimais = 0;
	}
	
	public String getString() {
		return pergunta;
	}
	
	public int getNumAnimais() {
		return numAnimais;
	}
	
	public LinkedList<String> getLista(String valor) {
		LinkedList<String> lista = null;
		if (valor.equalsIgnoreCase("sim"))
			lista = new LinkedList<String>(listaSim);
		else if (valor.equalsIgnoreCase("nao"))
			lista = new LinkedList<String>(listaNao);
		
		return lista;
	}

	public void addAnimal(String nomeAnimal, String valor) {
		if (valor.equalsIgnoreCase("sim")) {
			listaSim.add(nomeAnimal);
			numAnimais++;
		} else if (valor.equalsIgnoreCase("nao")) {
			listaNao.add(nomeAnimal);
			numAnimais++;
		}
	}
	
	public void removeAnimal(String nomeAnimal, String valor) {
		if (valor.equalsIgnoreCase("sim")) {
			listaSim.remove(nomeAnimal);
			numAnimais--;
		} else if (valor.equalsIgnoreCase("nao")) {
			listaNao.remove(nomeAnimal);
			numAnimais--;
		}
	}
	
}
