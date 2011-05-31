package pt.c03ensaios.lobo.app;

import pt.c01interfaces.s01chaveid.lobo.EnquirerAdvanced;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.debolacha.impl.AnimalFile;
import pt.c03ensaios.linnaeus.AnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.lobo.impl.AnimalCreatorGUI;
import pt.c03ensaios.lobo.inter.IAnimalCreatorGUI;
import pt.c03ensaios.lobo.inter.IResponderGraphics;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.naxxramas.Naxxramas;
import pt.c03ensaios.relu.ISearcher;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;
import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class AppAnimalCreatorGUI
{	
	public static void main(String[] args)
	{
		try
		{
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
			
			factory.registerPrototype(AnimalFile.class);
			factory.registerPrototype(Questions.class);
			factory.registerPrototype(AnimalCreatorGUI.class);
			factory.registerPrototype(AnimalsDatabase.class);
			factory.registerPrototype(Naxxramas.class);
			
			IEnquirer enquirer = new EnquirerAdvanced();

            IResponderGraphics responderRequires = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.lobo.impl.ResponderGraphics>");
            IAnimalPix animalpix = factory
            .createInstance("<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");

            ISearcher searcher = factory
            .createInstance("<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");
            
			AnimalFile animalFile = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalFile>");
			
			IQuestions questions = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>",
														  "<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
			IRequires<IAnimalsDatabase> cQuestions = questions.queryReceptacle("<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
			
			IAnimalCreatorGUI animalCreatorGUI = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.lobo.impl.AnimalCreatorGUI>");
			
			IAnimalsDatabase database = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>",
	                										   "<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
			IRequires<IQuestions> cDatabase = database.queryReceptacle("<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
			
			INaxxramas naxx = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");
			
			animalFile.setName("Tiranossauro");
			animalFile.addProperty("ele voa", "nao");
			animalFile.addProperty("ele nada", "nao");
			animalFile.addProperty("e carnivoro", "sim");
			animalFile.addProperty("e um dinossauro", "sim");
			
			responderRequires.connect(animalpix);
			responderRequires.connect(searcher);
			cQuestions.connect(database);
            cDatabase.connect(questions);
            animalFile.connect(database);
            
			
			animalCreatorGUI.connect(animalFile);
			animalCreatorGUI.connect(questions);
			animalCreatorGUI.initialize();
			//animalCreatorGUI.setTestMode(false); //<-----
			
			responderRequires.connect(naxx);
			animalCreatorGUI.connect(naxx);
			naxx.setSize(600, 400);
			naxx.setVisible(true);
			enquirer.connect(responderRequires);
			
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		}
	}

}
