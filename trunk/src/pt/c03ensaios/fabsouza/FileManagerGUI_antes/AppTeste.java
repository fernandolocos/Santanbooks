package pt.c03ensaios.fabsouza.FileManagerGUI_antes;

import anima.component.IRequires;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.proj01.*;

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
            
            ITextFilesManager textfilemanager = factory.createInstance(
            		"<http://purl.org/dcc/pt.c03ensaios.proj01.TextFilesManager>");
            
            IRequires<INaxxramas> FileManagerRequires = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileManagerGUI.FileManagerGUI>");
            
            INaxxramas requiredFrame = factory.createInstance(
            "<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");            
            
            IFileManager fileManager = (FileManagerGUI)FileManagerRequires;
            fileManager.connect(textfilemanager);
            FileManagerRequires.connect(requiredFrame);
            requiredFrame.setSize(430, 450);            
            requiredFrame.setVisible(true);
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }	
	}

}
