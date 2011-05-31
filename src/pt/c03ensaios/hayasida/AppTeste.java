package pt.c03ensaios.hayasida;

import java.util.Vector;

import pt.c03ensaios.hayasida.FileTxtHandler.IFileTxtHandler;
import pt.c03ensaios.hayasida.Handler.IHandler;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.context.exception.ContextException;
import anima.factory.exception.FactoryException;

public class AppTeste {
	
	public static void main(String args[])
	{
		try {		
			//Cria a fabrica
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
			//Cria o componente
			IHandler h = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.Handler>");
			
			//Muda diretório
			h.setDir("/home/cc2010/ra103984/Documents/");
			System.out.println("Diretorio: [" + h.getDir() + "]");
			
			//Extrai perguntas
			Vector<String> p = h.getPerguntas("teste");
			Vector<String> r = h.getRespostas("teste2");
			
			//Imprime no console
			System.out.println("------------------------------");
			if(p.size() == r.size()){
				for(int i = 0; i < p.size(); i++) System.out.println("" + i + ": [pergunta] " + p.get(i) + "  [resposta] " + r.get(i));
			}
			
			//Modifica uma das entradas
			p.remove(p.size() - 1);
			p.add("Modificado");
			
			//Salva em outro arquivo
			double n = Math.random()*1000;
			n = n - n%1;
			h.save("teste" + (int)n, p, r);
			
			//Testa existência
			System.out.println("------------------------------");
			if(h.exists("p01")) System.out.println("1: Existe!");
			else System.out.println("1: Nao existe");
			if(h.exists("pc01")) System.out.println("1.2: Existe!");
			else System.out.println("1.2: Nao existe");
			
			if(h.exists("p01", "r01")) System.out.println("2: Existe!");
			else System.out.println("2: Nao existe");
			if(h.exists("p01", "r331")) System.out.println("2.2: Existe!");
			else System.out.println("2.2: Nao existe");
			
			if(h.exists(p, r)) System.out.println("3: Existe!");
			else System.out.println("3: Nao existe");
				//Modifica uma das entradas
				p.remove(p.size() - 1);
				p.add("Modificado2");
			if(h.exists(p, r)) System.out.println("3.2: Existe!");
			else System.out.println("3.2: Nao existe");
			
			//Filtros
			String p1 = "p01";
			Vector<String> filtro1 = h.filterFilesByPergunta(p1);
			System.out.println("------------------------------");
			System.out.println("==> Perguntas com " + p1);
			printVString(filtro1);
			String p2 = "p02", r2 = "r02";
			Vector<String> filtro2 = h.filterFilesByPergunta(p2, r2);
			System.out.println("------------------------------");
			System.out.println("==> Perguntas com " + p2 + " e " + r2);
			printVString(filtro2);
			
        } catch (ContextException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FactoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        try {	
        	System.out.println("-----------------------------------\n");
        	System.out.println("Teste usando outro IFileTxtHandler");
			//Cria a fabrica
			IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
            
			//Cria o componente
			IHandler h = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.Handler>");
			IFileTxtHandler fh = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.hayasida.FileTxtHandler.FileTxtHandler>");
			h.connect(fh);
			
			//Muda diretório
			h.setDir("/home/cc2010/ra103984/Documents/");
			System.out.println("Diretorio: [" + h.getDir() + "]");
			
			//Extrai perguntas
			Vector<String> p = h.getPerguntas("teste");
			Vector<String> r = h.getRespostas("teste2");
			
			//Imprime no console
			System.out.println("------------------------------");
			if(p.size() == r.size()){
				for(int i = 0; i < p.size(); i++) System.out.println("" + i + ": [pergunta] " + p.get(i) + "  [resposta] " + r.get(i));
			}
			
			//Modifica uma das entradas
			p.remove(p.size() - 1);
			p.add("Modificado");
			
			//Salva em outro arquivo
			double n = Math.random()*1000;
			n = n - n%1;
			h.save("teste" + (int)n, p, r);
			
			//Testa existência
			System.out.println("------------------------------");
			if(h.exists("p01")) System.out.println("1: Existe!");
			else System.out.println("1: Nao existe");
			if(h.exists("pc01")) System.out.println("1.2: Existe!");
			else System.out.println("1.2: Nao existe");
			
			if(h.exists("p01", "r01")) System.out.println("2: Existe!");
			else System.out.println("2: Nao existe");
			if(h.exists("p01", "r331")) System.out.println("2.2: Existe!");
			else System.out.println("2.2: Nao existe");
			
			if(h.exists(p, r)) System.out.println("3: Existe!");
			else System.out.println("3: Nao existe");
				//Modifica uma das entradas
				p.remove(p.size() - 1);
				p.add("Modificado2");
			if(h.exists(p, r)) System.out.println("3.2: Existe!");
			else System.out.println("3.2: Nao existe");
			
			//Filtros
			String p1 = "p01";
			Vector<String> filtro1 = h.filterFilesByPergunta(p1);
			System.out.println("------------------------------");
			System.out.println("==> Perguntas com " + p1);
			printVString(filtro1);
			String p2 = "p02", r2 = "r02";
			Vector<String> filtro2 = h.filterFilesByPergunta(p2, r2);
			System.out.println("------------------------------");
			System.out.println("==> Perguntas com " + p2 + " e " + r2);
			printVString(filtro2);
			
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
