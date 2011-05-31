package pt.c03ensaios.fabsouza.FileNaxxramasGUI;

import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler;
import pt.c03ensaios.hayasida.Handler.IHandler;
import pt.c03ensaios.naxxramas.INaxxramas;
import anima.component.IRequires;
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
            
            IRequires<INaxxramas> FileNaxxramasRequires = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileNaxxramasGUI.FileNaxxramasGUI>");
            
            IFileNaxxramas fileNaxxramas = (FileNaxxramasGUI)FileNaxxramasRequires;
            
            INaxxramas requiredFrame = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");            
            
            handler.connect(fileTxtHandler);
            fileNaxxramas.connect(handler);
            FileNaxxramasRequires.connect(requiredFrame);
            requiredFrame.setSize(415, 415);            
            requiredFrame.setVisible(true);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }	
	}

}
