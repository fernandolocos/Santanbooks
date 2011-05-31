package pt.c03ensaios.afinder.animalinfo;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent;
import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler;
import pt.c03ensaios.hayasida.Handler.IHandler;
import pt.c03ensaios.relu.ISearcher;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppAnimalInfo {

	public static void main(String[] args) {
		try{
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            IAnimalInfo aInfo = factory.createInstance(
                    	"<http://purl.org/dcc/pt.c03ensaios.afinder.animalinfo.AnimalInfo>");
            IAnimalPix aPix = factory.createInstance(
                		"<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");
            IFileGUIComponent fileGUI = factory.createInstance(
    					"<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>");
            IFileTxtHandler fileTxtHandler = factory.createInstance(
    					"<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.FileTxtHandler>");
            IHandler handler = factory.createInstance(
            			"<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.Handler>"); 
			ISearcher searcher = factory.createInstance(
						"<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");
			
			handler.connect(fileTxtHandler);
			fileGUI.connect(handler);
			aInfo.connect(aPix);
            aInfo.connect(fileGUI);
			aInfo.connect(searcher);
            aInfo.showFrame();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
	}
}