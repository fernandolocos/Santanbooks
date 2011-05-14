package pt.c01interfaces.s01chaveid.s02exemplo.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.EnquirerAdvanced;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.EnquirerBasic;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;

public class AppBase02
{
	public static final String DIRETORIO_RAIZ = "pt/c01interfaces/s01chaveid/s02exemplo/app/",
    						   EXTENSAO = ".txt";
	
	/**
	 * Le o arquivo nome.txt para recupar a lista de animais que sera usada para testes.
	 * @param nome	nome do arquivo.txt com a lista de animais
	 * @return lista com o nome dos animais para teste
	 */
	static private ArrayList<String> recuperaAnimais (String nome) {
		ArrayList<String> animaisTeste = new ArrayList<String>();

		try {
    		FileReader arquivo = new FileReader(DIRETORIO_RAIZ + nome + EXTENSAO);
    		BufferedReader formatado = new BufferedReader(arquivo);
    	    
    	    String linha = formatado.readLine();
    	    while (linha != null)
    	    {
    	    	animaisTeste.add(linha);
    	    	linha = formatado.readLine();
    	    }
    	    
    	    arquivo.close();
    	} catch (IOException erro) {
    		erro.printStackTrace();
    	}
    	
    	return animaisTeste;
	}
	
	public static void main(String[] args)
	{
		ArrayList<String> animaisTeste = recuperaAnimais("animais-teste");
		IStatistics sa, sb;
		IResponder ra, rb;
		IEnquirer ea, eb;
		String animal;
		int numPerguntas = 0;
		
		Iterator<String>it = animaisTeste.iterator();
		while(it.hasNext()) {
			animal = it.next();
			numPerguntas = 0;
			
			System.out.println("Testando EnquierBasic com " + animal + "...");
			sb = new Statistics();
			rb = new Responder(sb, animal);
			eb = new EnquirerBasic();
			eb.connect(rb);
			
			System.out.println("Testando EnquierAdvanced com " + animal + "...");
			sa = new Statistics();
			ra = new Responder(sa, animal);
			ea = new EnquirerAdvanced();
			ea.connect(ra);
			
			numPerguntas = sb.totalPerguntas() + sa.totalPerguntas();
			System.out.println("Total de perguntas (basic + advanced): " + numPerguntas);
			System.out.println("----------------------------------------------------------------------------------------");
        }		
	}
}
