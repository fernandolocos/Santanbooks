package pt.c03ensaios.relu.ProgramaCompleto;

import java.util.List;


import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;
import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.afinder.animalpix.AnimalPix;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.debolacha.impl.AnimalList;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.denisecamila.FinalGraphic;
import pt.c03ensaios.denisecamila.IFinalGraphic;
import pt.c03ensaios.denisecamila.IReceptacleAnimalPix;
import pt.c03ensaios.denisecamila.IReceptacleFileGUI;
import pt.c03ensaios.denisecamila.IReceptacleSearcher;
import pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.fejao.etapa2.impl.PossibleAnimalsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.frango.QuestionsHash;
import pt.c03ensaios.hayasida.Handler.IHandler;
import pt.c03ensaios.hayasida.Handler.IHandlerReceptacle;
import pt.c03ensaios.linnaeus.AnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.optimizers.IEnquirerSupports;
import pt.c03ensaios.pizza.IQuestionChooser;
import pt.c03ensaios.pizza.QuestionChooser;
import pt.c03ensaios.relu.ISearcher;
import pt.c03ensaios.relu.Searcher;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.relu.ProgramaCompleto.Enquirer>")
public class Enquirer extends ComponentBase implements IEnquirer, IEnquirerSupports {
	
	private IResponder responder;
	private IPossibleAnimalsHash hash;
	private IQuestionChooser chooser;
	private IGlobalFactory factory;
	private IFinalGraphic graphic;
	private FileGUIComponent gui;
	private String[] animais;
	private IBaseConhecimento base = new BaseConhecimento();


	@Override
	public void connect(IResponder responder) {
		this.responder = responder;
		try {
            factory = ComponentContextFactory.createGlobalFactory();
            factory.registerPrototype(PossibleAnimalsHash.class);
            hash = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
            factory.registerPrototype(QuestionChooser.class);
            chooser = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
           
            factory.registerPrototype(PossibleAnimalsHash.class);
            factory.registerPrototype(AnimalList.class);
            factory.registerPrototype(Questions.class);
            factory.registerPrototype(AnimalsDatabase.class);
            factory.registerPrototype(QuestionsHash.class);
            factory.registerPrototype(Searcher.class);
            factory.registerPrototype(AnimalPix.class);
            factory.registerPrototype(FileGUIComponent.class);
           
            hash = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
           
            graphic = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.denisecamila.FinalGraphic>");
            
           
            IReceptacleQuestionsHash receptacleQuestionHash = (IReceptacleQuestionsHash)hash;
            IQuestionsHash hashAnswerYes = (factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
            IQuestionsHash hashAnswerNo = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
            IQuestionsHash hashAnswerDontKnow = factory
                    .createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
            receptacleQuestionHash.connect(hashAnswerYes);
            receptacleQuestionHash.connect(hashAnswerNo);
            receptacleQuestionHash.connect(hashAnswerDontKnow);
           
            /* Instantiates and connects an AnimalList object */
            IReceptacleAnimalList receptacleList = (IReceptacleAnimalList)hash;
            IAnimalList animalList = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");               
            receptacleList.connect(animalList);
           
            IReceptacleQuestions receptacleQuestions = (IReceptacleQuestions)hash;
            IQuestions questions = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");               
            receptacleQuestions.connect(questions);
           
            IReceptacleAnimalsDatabase receptacleAnimalsDatabase = (IReceptacleAnimalsDatabase)hash;
            IAnimalsDatabase animalsDatabase = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");               
            receptacleAnimalsDatabase.connect(animalsDatabase);
           
            IReceptacleSearcher receptacleSearcher = (IReceptacleSearcher)graphic;
            ISearcher searcher = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");               
            receptacleSearcher.connect(searcher);
           
            IReceptacleAnimalPix receptaclePix = (IReceptacleAnimalPix)graphic;
            IAnimalPix pix = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");               
            receptaclePix.connect(pix);
            
            gui = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>");
            IHandlerReceptacle receptacleHandler = (IHandlerReceptacle)gui;
            IHandler handler = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");               
            receptacleHandler.connect(handler);
           
            IReceptacleFileGUI receptacleFile = (IReceptacleFileGUI)graphic;
            receptacleFile.connect(gui);
		} catch (ContextException e) {
			e.printStackTrace();
		} catch (FactoryException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		hash.setPossibleAnimalsArray(base.listaNomes());
		hash.clearPossibleAnimalsList();
		animais = hash.getPossibleAnimalsArray();
		while(hash.getNumberOfAnimals() > 1){
			hash.determinesPossibleAnimals(chooser.BestQuestion(base, animais), this.responder.ask(chooser.BestQuestion(base, animais)));
			animais = hash.getPossibleAnimalsArray();
			
		}
		System.out.println(hash.getPossibleAnimalsList());
		if(responder.finalAnswer(hash.getPossibleAnimalsList().get(0))){
			graphic.exibe(hash.getPossibleAnimalsList().get(0));
		}
	}

}
