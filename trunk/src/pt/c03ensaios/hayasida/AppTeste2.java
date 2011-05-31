package pt.c03ensaios.hayasida;

import java.io.File;
import java.util.Vector;
import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.context.exception.ContextException;
import anima.factory.exception.FactoryException;

public class AppTeste2 {
	
	public static void main(String args[])
	{
		try {		
			//Cria a fabrica
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
			//Cria o componente
			IFileTxtHandler h = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.FileTxtHandler>");
			
			//Muda diretório
			h.setDir("/home/cc2010/ra103984/Documents/");
			System.out.println("Diretorio: [" + h.getDir() + "]");
			
			//Extrai perguntas
			Vector<String> text = h.open("teste");
			
			//Imprime no console
			System.out.println("------------------------------");
			printVString(text);
			
			//Modifica uma das entradas
			text.remove(text.size() - 3);
			text.add("Linha Modificada");
			
			//Salva em outro arquivo
			double n = Math.random()*1000;
			n = n - n%1;
			h.save(text, "testeTXT." + (int)n);
			
			//Listas
			Vector<String> fileNames = h.listTxtFilesNames();
			System.out.println("------------------------------");
			printVString(fileNames);

			Vector<File> files = h.listTxtFiles();
			System.out.println("------------------------------");
			for(int i = 0; i < files.size(); i++){
				System.out.println("Arquivo: [nome:] " + files.get(i).getName() + "  [path] " + files.get(i).getAbsolutePath());
			}
			
        } catch (ContextException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FactoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
	}

	public static void printVString(Vector<String> text){
		for(int i = 0; i < text.size(); i++){
			System.out.println(text.get(i));
		}
	}
	

}
