package pt.c03ensaios.fejao.app;

import java.util.ArrayList;
import java.util.List;

import pt.c03ensaios.fejao.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.PossibleAnimalsHash;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

/* Realização de testes com os métodos de String e list do component PossibleAnimalsHash*/
public class AppTeste02 {

	public static void main(String[] args) {
		List<String> animalsList = new ArrayList<String>();
		String[] animalsArray = new String[0];
		
		IGlobalFactory factory;
		try {
			factory = ComponentContextFactory.createGlobalFactory();


			factory.registerPrototype(PossibleAnimalsHash.class);

			IPossibleAnimalsHash hashAnimals = factory.createInstance(
			"<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");

			hashAnimals.clearPossibleAnimalsList();

			animalsList = hashAnimals.getPossibleAnimalsList();
			System.out.print("GetAnimals com List:  [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsList.get(i)+", ");
			}
			System.out.println("]");

			animalsArray = hashAnimals.getPossibleAnimalsArray();
			System.out.print("GetAnimals com Array: [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsArray[i]+", ");
			}
			System.out.println("]");

			System.out.println(hashAnimals.getNumberOfAnimals());
			hashAnimals.removeAnimalsList(animalsList);
			System.out.println(hashAnimals.getNumberOfAnimals());
			animalsList = new ArrayList<String>();
			animalsList.add("abelha");
			animalsList.add("atum");
			animalsList.add("aranha");

			hashAnimals.setPossibleAnimalsList(animalsList);

			animalsList = hashAnimals.getPossibleAnimalsList();
			System.out.print("GetAnimals com List:  [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsList.get(i)+", ");
			}
			System.out.println("]");
			System.out.println(hashAnimals.getNumberOfAnimals());
			animalsList.add("baleia");
			animalsList.add("boi");
			hashAnimals.addAnimalsList(animalsList);

			//hashAnimals.setPossibleAnimalsArray(animalsArray);

			animalsList = hashAnimals.getPossibleAnimalsList();
			System.out.print("GetAnimals com List:  [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsList.get(i)+", ");
			}
			System.out.println("]");
			
			animalsArray = new String[3];
			animalsArray[0] = "boi";
			animalsArray[1] = "atum";
			animalsArray[2] = "aranha";
			hashAnimals.removeAnimalsArray(animalsArray);
			
			animalsArray = hashAnimals.getPossibleAnimalsArray();
			System.out.print("GetAnimals com Array: [");
			for(int i = 0; i < hashAnimals.getNumberOfAnimals(); i++){
				System.out.print(" "+animalsArray[i]+", ");
			}
			System.out.println("]");

			hashAnimals.clearPossibleAnimalsList();
			System.out.println(hashAnimals.getNumberOfAnimals());
		} catch (ContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

}



