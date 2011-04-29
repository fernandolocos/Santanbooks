package pt.c03ensaios.fejao;

import java.util.List;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Interface responsible for the control of animals still have a chance to be the animal sought
 * @author Fernando Costa e João Scalett
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimals>")
public interface IPossibleAnimalsHash extends ISupports{

	public void putPossibleAnimalsList(List<String> animals);
	
	public List<String> getPossibleAnimalsList();
	
	public int getListSize();
}
