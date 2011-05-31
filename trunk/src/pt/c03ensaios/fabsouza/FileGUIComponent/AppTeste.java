package pt.c03ensaios.fabsouza.FileGUIComponent;

import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler;
import pt.c03ensaios.hayasida.Handler.IHandler;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;

public class AppTeste {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{

        try 
        {
            IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
            IFileTxtHandler fileTxtHandler = factory.createInstance(
            		"<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.FileTxtHandler>");
            
            IHandler handler = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.Handler>");   
            
            IFileGUIComponent fileGUI = factory.createInstance(
                    "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>");
            
            handler.connect(fileTxtHandler);
            fileGUI.connect(handler);
            fileGUI.showFrame();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }	
	}

}
