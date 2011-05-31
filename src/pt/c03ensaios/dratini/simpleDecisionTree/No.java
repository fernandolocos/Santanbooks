package pt.c03ensaios.dratini.simpleDecisionTree;


public class No {
	private String nome;
	private No sims;
	private No naos;
	private No naoseis;
	public No getSims() {
		return sims;
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public No(String nome) {
		super();
		this.nome = nome;
		this.naos = null;
		this.sims = null;
		this.naoseis = null;
		
	}

	public No getNaos() {
		return naos;
	}

	public void setNaos(No naos) {
		this.naos = naos;
	}

	public No getNaoseis() {
		return naoseis;
	}

	public void setNaoseis(No naoseis) {
		this.naoseis = naoseis;
	}

	public void setSims(No sims) {
		this.sims = sims;
	}
	
	

}
