package pt.c01interfaces.s01chaveid.afinder.impl;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;

public class EnquirerBasic implements IEnquirer {

	private IBaseConhecimento base;
	private IObjetoConhecimento obj;
		
	public EnquirerBasic ()
	{
		base = new BaseConhecimento();
	}
	
    public void connect(IResponder responder)
    {
    	IDeclaracao decl;
    	String animais[];
    	Vector<String> pergunta = new Vector<String> ();
    	Vector<String> resposta = new Vector<String> ();
    	String p;
    	int i, j, n = 0;
    	int palpite = -1, n_perg = 0, perg_palp = 0;
    	boolean feita;
    	
    	animais = base.listaNomes();
    	obj = base.recuperaObjeto(animais[n]);
    	decl = obj.primeira();
    	
    	for(i = 0; obj != null; i++)
    	{
    		p = decl.getPropriedade();
    		for (j = 0, feita = false; j < pergunta.size() && !feita; j++)
    			if(p.equalsIgnoreCase(pergunta.elementAt(j)))
    				{
    					feita = true;
    					j--;
    				}
    		
    		if (!feita)
    		{
    			pergunta.add(p);
    			j = pergunta.size() - 1;
    			resposta.add(responder.ask(pergunta.elementAt(j)));
    		}
    		
    		if (!decl.getValor().equalsIgnoreCase(resposta.elementAt(j)))
    		{
    			
    			if (n < animais.length-1)
    			{
    				n_perg = 0;
    				obj = base.recuperaObjeto(animais[++n]);
    				decl = obj.primeira();
    			}
    			else
    				obj = null;
    		}
    		else {
    			n_perg++;
    			decl = obj.proxima();
    			if (decl == null)
    			{
    				if (perg_palp < n_perg)
    				{
    					palpite = n;
    					perg_palp = n_perg;
    				}
    				
    				if (n < animais.length-1)
        			{
    					n_perg = 0;
        				obj = base.recuperaObjeto(animais[++n]);
        				decl = obj.primeira();
        			}
        			else
        				obj = null;
        		}
    		}
       	}
    	
    	if (palpite != -1)
			responder.finalAnswer(animais[palpite]);
		else
			responder.finalAnswer("nao conheco");

    }	
}