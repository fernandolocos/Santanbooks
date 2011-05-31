package pt.c03ensaios.afinder.animalpix;

import java.util.Scanner;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppAnimalPix{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        try{
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            IAnimalPix ex = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");
            
            Scanner keyboard = new Scanner(System.in);
        	String name;
        	System.out.print("Digite o nome do animal (ou \"fim\"): ");
        	name = keyboard.nextLine();
        	while (!name.equalsIgnoreCase("fim")){
        		ex.showImageWindow(name);
        		System.out.print("Digite o nome do animal (ou \"fim\"): ");
        		name = keyboard.nextLine();
        	}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
} 