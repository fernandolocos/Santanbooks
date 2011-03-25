package trabalho.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IObjetoConhecimento;

public class BaseConhecimento implements IBaseConhecimento
{
	public static final String DIRETORIO_RAIZ = "trabalho/base/",
	                           EXTENSAO = ".txt";
	
    /* (non-Javadoc)
	 * @see trabalho.impl.IBaseConhecimento#recuperaObjeto(java.lang.String)
	 */
    public IObjetoConhecimento recuperaObjeto(String nome)
    {
		Vector<IDeclaracao> vdecl = new Vector<IDeclaracao>();

		try {
    		FileReader arquivo = new FileReader(DIRETORIO_RAIZ + nome + EXTENSAO);
    		BufferedReader formatado = new BufferedReader(arquivo);
    	    
    	    String linha = formatado.readLine();
    	    while (linha != null)
    	    {
    	    	IDeclaracao decl = montaDeclaracao(linha);
    	    	
    	    	if (decl != null)
    	    		vdecl.add(decl);
    	    	
    	    	linha = formatado.readLine();
    	    }
    	    
    	    arquivo.close();
    	} catch (IOException erro) {
    		erro.printStackTrace();
    	}
    	
    	IObjetoConhecimento obj = new ObjetoConhecimento(vdecl.toArray(new IDeclaracao[0]));
    	
    	return obj;
    }
    
    private IDeclaracao montaDeclaracao(String linha)
    {
    	String propriedade = null,
    	       valor = null;
    	
        if (linha != null) {
        	char clinha[] = linha.toCharArray();
        	int p = 0;

        	if (clinha[p] == '\"') {
	    		propriedade = "";
	    		p++;
	    		while (p < clinha.length && clinha[p] != '\"') {
	    			propriedade += clinha[p];
	    			p++;
	    		}
	    		p++;
	    		
	    		if (p < clinha.length) {
	    			while (p < clinha.length && clinha[p] != '\"') 
	        			p++;
	    			if (p < clinha.length) {
	    				valor = "";
	    				p++;
	    	    		while (p < clinha.length && clinha[p] != '\"') {
	    	    			valor += clinha[p];
	    	    			p++;
	    	    		}
	    			}
	    		}
	    	}
        }
        
    	IDeclaracao decl = null;
    	if (propriedade != null && valor != null)
    		decl = new Declaracao(propriedade, valor);
    	
    	return decl;
    }
    
    public String[] listaNomes()
    {
    	String[] v = new String[8];
    	v[0] = "beija-flor";
    	v[1] = "cachorro";
    	v[2] = "canarinho";
    	v[3] = "gato";
    	v[4] = "jacare";
    	v[5] = "macaco";
    	v[6] = "tigre";
    	v[7] = "tiranossauro";
    	
    	return v;
    }
}
