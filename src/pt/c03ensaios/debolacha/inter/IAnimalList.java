package pt.c03ensaios.debolacha.inter;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import java.util.List;

/**
 * Interface of a component that 
 * makes operations with List<String>
 * @author Douglas Afonso
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalList>")
public interface IAnimalList extends ISupports, List<String>{
	/**
	 * Creates an AnimalList from a String[]
	 * @param a String[]
	 */
	public void setList(String[] listaNomes);
			
	/**
	 * @param outra: other AnimalList.
	 * @return: common elements between this List and other one.
	 */
	public IAnimalList intersec (List<String> outra);
	
	/**
	 * @param outra: the other AnimalList
	 * @return: AnimalList that contents all the that exists
	 *	        in this AnimalList or in the other AnimalList.
	 */
	public IAnimalList uniao (List<String> outra);

	/** 
	 * @param other: other AnimalList.
	 * @return: An AnimalList that contents all the elements that exists
	 *          in 'other', but doesn't exist in this.
	 */
	public IAnimalList complementar(List<String> other);
}
