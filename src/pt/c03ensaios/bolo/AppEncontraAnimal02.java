package pt.c03ensaios.bolo;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c03ensaios.analizers.IAnalizer;
import pt.c03ensaios.linnaeus.AnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalData;
import pt.c03ensaios.tochinhas2.impl.Questions;

import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

/**
 * Aplicacao que utiliza tres componentes de outros alunos para encontrar
 * o animal com mais perguntas e o melhor enquirer. Entao, utiliza o enquirer
 * para encontrar tal animal.
 * 
 * @author Eric Oakley
 *
 */
public class AppEncontraAnimal02
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

		IAnalizer analizer = factory.createInstance( // componente do Eric
	            "<http://purl.org/dcc/pt.c03ensaios.eric.analizer.BestAnalizer>");
		AnimalsDatabase db = factory.createInstance( // componente do Oscar
				"<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
		Questions questions = factory.createInstance( // componente do Davi
			"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");

		questions.connect(db);
		
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
		
		System.out.println("Todas as perguntas do animal " + animal + ":");
		
		String[] animals = new String[1]; animals[0] = animal;
		Vector<String> perguntas = questions.getQuestions(animals);
		for (int i = 0; i < perguntas.size(); i++) {
			System.out.println(perguntas.get(i));
		}
		
		System.out.println();
		
        IResponder rb = new Responder(animal);
        
        bestEnquirer.connect(rb);
	}
}
