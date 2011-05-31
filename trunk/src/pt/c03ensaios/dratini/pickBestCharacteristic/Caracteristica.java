package pt.c03ensaios.dratini.pickBestCharacteristic;

import pt.c03ensaios.dratini.characteristicsList.ICaracteristica;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Represents an animal's characteristic
 * 
 * @author Kamila Galvani
 */
@Component(id = "<http://purl.org/dcc/pt.c03ensaios.dratini.CaracteristicaComponent.Caracteristica>",
        provides = {"<http://purl.org/dcc/pt.c03ensaios.dratini.CaracteristicaComponent.ICaracteristica>"})

public class Caracteristica extends ComponentBase implements ICaracteristica{
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
