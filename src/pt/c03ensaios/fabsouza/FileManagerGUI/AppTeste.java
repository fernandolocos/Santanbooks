package pt.c03ensaios.fabsouza.FileManagerGUI;

import anima.component.IRequires;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import pt.c03ensaios.debolacha.inter.IAnimalList;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.proj01.*;
import pt.c03ensaios.tochinhas2.impl.IQuestions;

public class AppTeste {

	/**
	 * @param args
	 * @throws Exception 
	 */

	public static void main(String[] args) 
	{
        try 
        {
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
            IAnimalListReceptacle TFManager = factory.createInstance(
            	"<http://purl.org/dcc/pt.c03ensaios.proj01.TextFilesManager>");  
            
            IRequires<INaxxramas> FMRNaxxramas = factory.createInstance(
            	"<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileManagerGUI.FileManagerGUI>"); 
            
            INaxxramas requiredFrame = factory.createInstance(
            	"<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");      
            
            
            /*---------------------------------------------------------------*/
            
            IRequires<IQuestions> TFMRQuestion = TFManager.queryReceptacle(
			"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");
            
            IQuestions myQuestions = factory.createInstance(
    			"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");
            
            IAnimalsDatabase dbAnimals = factory.createInstance(
            	"<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
            
            IRequires<IAnimalsDatabase> TFMRAnimalsdb = myQuestions.queryReceptacle(
    			"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
        
            IAnimalList animalList = factory.createInstance(
        		"<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalList>");
            
            /*---------------------------------------------------------------*/
            
            
            TFMRAnimalsdb.connect(dbAnimals);
            TFMRQuestion.connect(myQuestions);
            TFManager.connect(animalList);
            
            // cast para acessar os métodos de FileManagerGUI
            IFileManager fileManager = (FileManagerGUI)FMRNaxxramas;
            // cast para conectar um TextFilesManager ao FileManagerGUI
            ITextFilesManager txtManipulador = (ITextFilesManager)TFManager;
            
            fileManager.connect(txtManipulador);
            
            FMRNaxxramas.connect(requiredFrame);
            requiredFrame.setSize(430, 450);            
            requiredFrame.setVisible(true);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }	
	}

}
