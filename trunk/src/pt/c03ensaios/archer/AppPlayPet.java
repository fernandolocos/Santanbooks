package pt.c03ensaios.archer;

import pt.c03ensaios.dratini.pickBestCharacteristic.IBestChooser;
import pt.c03ensaios.fejao.etapa2.impl.IPossibleAnimalsHash;
import pt.c03ensaios.lobo.inter.IResponderGraphics;
import pt.c03ensaios.naxxramas.INaxxramas;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppPlayPet {

    public static void main(String[] args)
    {
        try {
          IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();         
          
          IBestChooser cbestChooser = factory.createInstance(
        	"<http://purl.org/dcc/pt.c03ensaios.dratini.pickBestCharacteristic.PickBestCharacteristic>");
          INaxxramas cmanagerGraphics = factory.createInstance(
        	"<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");
          IResponderGraphics cresponder = factory.createInstance(
 		 	"<http://purl.org/dcc/pt.c03ensaios.lobo.impl.ResponderGraphics>");
          IPossibleAnimalsHash possibleAnimals = factory.createInstance(
			"<http://purl.org/dcc/pt.c03ensaios.fejao.PossibleAnimalsHash>");
          
          IPlayPet componentPlayPet = factory.createInstance(
                 "<http://purl.org/dcc/pt.c03ensaios.archer.PlayPet>",
                 "<http://purl.org/dcc/pt.c03ensaios.archer.IPlayPet>");
                   
          // Receptaculo        
          IBestChooserReceptacle requiredbestChooser = 
        	  (IBestChooserReceptacle)componentPlayPet;
          requiredbestChooser.connect(cbestChooser);
                
          INaxxramasReceptacle requiredmanagerGraphics = 
        	  (INaxxramasReceptacle)componentPlayPet;
          requiredmanagerGraphics.connect(cmanagerGraphics);
                
          IResponderGraphicsReceptacle requiredresponder = 
        	  (IResponderGraphicsReceptacle)componentPlayPet;
          requiredresponder.connect(cresponder);
          
          IPossibleAnimalsHashReceptacle requiredpossibleAnimals = 
        	  (IPossibleAnimalsHashReceptacle)componentPlayPet;
          requiredpossibleAnimals.connect(possibleAnimals);
                
          componentPlayPet.run();
       } catch (Exception e) {
          e.printStackTrace();
       }
       
    }
}
