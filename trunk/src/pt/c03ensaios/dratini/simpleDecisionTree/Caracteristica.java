package pt.c03ensaios.dratini.simpleDecisionTree;

public class Caracteristica {
	private String nome;
	private double entropia;
	private int sims;
	private int naos;
	private int naoseis;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public double getEntropia() {
		return entropia;
	}
	public void setEntropia(double entropia) {
		this.entropia = entropia;
	}
	public int getSims() {
		return sims;
	}
	public void setSims(int sims) {
		this.sims = sims;
	}
	public int getNaos() {
		return naos;
	}
	public void setNaos(int naos) {
		this.naos = naos;
	}
	public int getNaoseis() {
		return naoseis;
	}
	public void setNaoseis(int naoseis) {
		this.naoseis = naoseis;
	}
	public Caracteristica(String nome) {
		super();
		this.nome = nome;
		this.sims = 0;
		this.naos = 0;
		this.naoseis = 0;
	}
}
