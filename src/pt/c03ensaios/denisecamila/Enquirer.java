package pt.c03ensaios.denisecamila;

import java.util.Arrays;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.afinder.animalpix.AnimalPix;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.debolacha.impl.AnimalList;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent;
import pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent;
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
import pt.c03ensaios.pizza.IQuestionChooser;
import pt.c03ensaios.pizza.QuestionChooser;
import pt.c03ensaios.relu.ISearcher;
import pt.c03ensaios.relu.Searcher;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class Enquirer implements IEnquirer{
   
    private IBaseConhecimento baseConhecimento;
    private IPossibleAnimalsHash possibleAnimalsHash;
    private IFinalGraphic finalGraphic;
    private IQuestionChooser questionChooser;
    private IFileGUIComponent fileGUI;
   
    public Enquirer(){

        baseConhecimento = new BaseConhecimento();
       
        try {
            IGlobalFactory factory;
            factory = ComponentContextFactory.createGlobalFactory();
            factory.registerPrototype(PossibleAnimalsHash.class);
            possibleAnimalsHash = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
            factory.registerPrototype(QuestionChooser.class);
            questionChooser = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.pizza.QuestionChooser>");
        } catch (ContextException e) {
            e.printStackTrace();
        } catch (FactoryException e) {
            e.printStackTrace();
        }
    }
   
    public void connect(IResponder responder) {
       
        String pergunta, animal = null;
       
        try {
            IGlobalFactory factory =
                ComponentContextFactory.createGlobalFactory();
           
            factory.registerPrototype(PossibleAnimalsHash.class);
            factory.registerPrototype(AnimalList.class);
            factory.registerPrototype(Questions.class);
            factory.registerPrototype(AnimalsDatabase.class);
            factory.registerPrototype(QuestionsHash.class);
            factory.registerPrototype(Searcher.class);
            factory.registerPrototype(AnimalPix.class);
            factory.registerPrototype(FileGUIComponent.class);
           
            /* Fabricates a PossibleAnimalHash object */
            possibleAnimalsHash = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
           
            /* Fabricates a FinalGraphic object */
            finalGraphic = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.denisecamila.FinalGraphic>");
            
           
            /* Instantiates 3 QuestionHashes: one for the questions whose answers are "Yes",
             * one for "No"s and the third one for "Don't Know"s. Connects the hashAnimals with them */
            IReceptacleQuestionsHash receptacleQuestionHash = (IReceptacleQuestionsHash)possibleAnimalsHash;
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
            IReceptacleAnimalList receptacleList = (IReceptacleAnimalList)possibleAnimalsHash;
            IAnimalList animalList = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");               
            receptacleList.connect(animalList);
           
            /* Instantiates and connects a Questions object */
            IReceptacleQuestions receptacleQuestions = (IReceptacleQuestions)possibleAnimalsHash;
            IQuestions questions = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");               
            receptacleQuestions.connect(questions);
           
            /* Instantiates and connects an AnimalDatabase object */
            IReceptacleAnimalsDatabase receptacleAnimalsDatabase = (IReceptacleAnimalsDatabase)possibleAnimalsHash;
            IAnimalsDatabase animalsDatabase = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");               
            receptacleAnimalsDatabase.connect(animalsDatabase);
           
            /* Instantiates and connects an Searcher object */
            IReceptacleSearcher receptacleSearcher = (IReceptacleSearcher)finalGraphic;
            ISearcher searcher = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");               
            receptacleSearcher.connect(searcher);
           
            /* Instantiates and connects an AnimalPix object */
            IReceptacleAnimalPix receptaclePix = (IReceptacleAnimalPix)finalGraphic;
            IAnimalPix pix = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");               
            receptaclePix.connect(pix);
            
            /* Instantiates and connects an Handler object to FileGUI object */
            fileGUI = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>");
            IHandlerReceptacle receptacleHandler = (IHandlerReceptacle)fileGUI;
            IHandler handler = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");               
            receptacleHandler.connect(handler);
           
            /* Instantiates and connects an FileGUI object */
            IReceptacleFileGUI receptacleFile = (IReceptacleFileGUI)finalGraphic;
            receptacleFile.connect(fileGUI);

        } catch (Exception e) {
            e.printStackTrace();
        }
       
        possibleAnimalsHash.clearPossibleAnimalsList();
               
        while(possibleAnimalsHash.getNumberOfAnimals()>1){   
            String[] animais = Arrays.copyOf(possibleAnimalsHash.getPossibleAnimalsList().toArray(), possibleAnimalsHash.getPossibleAnimalsList().toArray().length, String[].class);                       
            pergunta = questionChooser.BestQuestion(baseConhecimento, animais);               
            possibleAnimalsHash.determinesPossibleAnimals(pergunta,responder.ask(pergunta));           
        }
              
        animal = possibleAnimalsHash.getPossibleAnimalsArray()[0];
       
        if(responder.finalAnswer(animal))
            finalGraphic.exibe(animal);
        else
            finalGraphic.exibe(null);
    }
}
