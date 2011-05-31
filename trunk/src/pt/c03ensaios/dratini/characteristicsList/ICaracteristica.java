package pt.c03ensaios.dratini.characteristicsList;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface for a Characteristic that has name, entropy, and the number of each possible answer
 * @author Kamila Galvani
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.dratini.caracteristicaComponent.ICaracteristica>")

public interface ICaracteristica extends ISupports{

	public String getNome();
	public void setNome(String nome);
	public double getEntropia();
	public void setEntropia(double entropia);
	public int getSims();
	public void setSims(int sims);
	public int getNaos();
	public void setNaos(int naos);
	public int getNaoseis();
	public void setNaoseis(int naoseis);
}
