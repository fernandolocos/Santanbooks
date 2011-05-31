package pt.c03ensaios.bolo;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c03ensaios.analizers.IAnalizer;
import pt.c03ensaios.linnaeus.AnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalData;

import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

/**
 * Aplicação que utiliza dois componentes de outros alunos para encontrar
 * o animal com mais perguntas e o melhor enquirer. Então, utiliza o enquirer
 * para encontrar tal animal.
 * 
 * @author Eric Oakley
 *
 */
public class AppEncontraAnimal
{

	public static void main(String[] args)
	{
		IGlobalFactory factory = null;

        try {
			factory = ComponentContextFactory.createGlobalFactory();
		} catch (ContextException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IAnalizer analizer = factory.createInstance(
	            "<http://purl.org/dcc/pt.c03ensaios.eric.analizer.BestAnalizer>");
		AnimalsDatabase db = factory.createInstance(
				"<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
		
		System.out.println("Pesquisando melhor Enquirer, aguarde...");
		IEnquirer bestEnquirer = analizer.getBest();
		
		System.out.println("Obtendo o animal que possui mais perguntas...");
		IAnimalData[] animaisData = db.getAnimais();
		
		int IDanimal = 0;
		for (int i = 0; i < animaisData.length; i++) {
			if(animaisData[i].getNperguntas() >= animaisData[IDanimal].getNperguntas())
				IDanimal = i;
		}
		
		String animal = animaisData[IDanimal].getNome();
		     
		IBaseConhecimento bc = new BaseConhecimento();
		String[] a = bc.listaNomes();
		
        IResponder rb = new Responder(animal);
        
        bestEnquirer.connect(rb);
	}
}
