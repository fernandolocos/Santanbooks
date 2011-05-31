package pt.c03ensaios.archer;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalList;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleAnimalsDatabase;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestions;
import pt.c03ensaios.fejao.etapa2.impl.IReceptacleQuestionsHash;
import pt.c03ensaios.frango.IQuestionsHash;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.lobo.inter.IResponderGraphics;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.relu.ISearcher;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

/**
 * Unir os componentes para criar o Jogo.
 * 
 * @author Edson D. Mano
 * @author Ivan Filho
 *
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.archer.PlayPet>",
           provides={"<http://purl.org/dcc/pt.c03ensaios.archer.IPlayPet>"},
           requires={"<http://purl.org/dcc/pt.c03ensaios.fejao.IPossibleAnimalsHash>",
                    "<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser>",
                    "<http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>",
                    "<http://purl.org/dcc/pt.c03ensaios.lobo.inter.IResponderGraphics>"})
                    
public class PlayPet extends ComponentBase implements IPlayPet, IPossibleAnimalsHashReceptacle, 
IBestChooserReceptacle, INaxxramasReceptacle, IResponderGraphicsReceptacle{

    private IPossibleAnimalsHash possibleAnimals; 
    private IBestChooser bestChooser; 
    private INaxxramas managerGraphics;
    private IResponderGraphics responder;
    private IAnimalPix animalPix;
    private ISearcher searcher;
    
    private BaseConhecimento base = new BaseConhecimento();
    private String animals[] = base.listaNomes();
    
    
     public void connect(IPossibleAnimalsHash possibleAnimals)
     {
            this.possibleAnimals = possibleAnimals;
     }
     
     public void connect(IBestChooser bestChooser)
     {
            this.bestChooser = bestChooser;
     }
     
     public void connect(INaxxramas managerGraphics)
     {
            this.managerGraphics = managerGraphics;
     }
     
     public void connect(IResponderGraphics responder)
     {
            this.responder = responder;
     }
     
     public void run() {
        // Criado devido a modificao do componente
         try { 
             IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
             
             IReceptacleQuestionsHash receptacleQuestionHash = (IReceptacleQuestionsHash)possibleAnimals;
             IQuestionsHash hashAnswerYes = (factory
                        .createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>"));
             IQuestionsHash hashAnswerNo = factory
                        .createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
             IQuestionsHash hashAnswerDontKnow = factory
                        .createInstance("<http://purl.org/dcc/pt.c03ensaios.frango.QuestionsHash>");
             receptacleQuestionHash.connect(hashAnswerYes);
             receptacleQuestionHash.connect(hashAnswerNo);
             receptacleQuestionHash.connect(hashAnswerDontKnow);
             
             /// Instantiates and connects an AnimalList object
             IReceptacleAnimalList receptacleList = (IReceptacleAnimalList)possibleAnimals;
             IAnimalList animalList = factory.createInstance(
                     "<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");                
             receptacleList.connect(animalList);
             
              // Instantiates and connects a Questions object
             IReceptacleQuestions receptacleQuestions = (IReceptacleQuestions)possibleAnimals;
             IQuestions questions = factory.createInstance(
                     "<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");                
             receptacleQuestions.connect(questions);
             
             IReceptacleAnimalsDatabase receptacleAnimalsDatabase = (IReceptacleAnimalsDatabase)possibleAnimals;
             IAnimalsDatabase animalsDatabase = factory.createInstance(
                     "<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");                
             receptacleAnimalsDatabase.connect(animalsDatabase);
             
             // Receptacle do ResponderGraphics
             IAnimalPix pix = factory.createInstance(
            		 "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");
             ISearcher searcher = factory.createInstance(
            		 "<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");
             
             responder.connect(pix);
             responder.connect(searcher);
             
             possibleAnimals.clearPossibleAnimalsList();
             // Fim definicoes
                           
                managerGraphics.add("Jogo", responder.getGamePanel());
                managerGraphics.setVisible(true);
                
                possibleAnimals.setPossibleAnimalsArray(animals);
                String bestQuestion = bestChooser.selectBestQuestion(base, animals);
                String answer;
                
                while(animals.length > 1) {
                    answer = responder.ask(bestQuestion);
                    possibleAnimals.determinesPossibleAnimals(bestQuestion, answer);
                    animals = possibleAnimals.getPossibleAnimalsArray();
                    bestQuestion = bestChooser.selectBestQuestion(base, animals);
                }
                
                boolean animal = responder.finalAnswer(animals[0]);
                
         } catch (Exception e) {
                e.printStackTrace();
         }
    }

}
