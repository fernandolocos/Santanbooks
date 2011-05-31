/**														*
 * @author Matheus Smythe Svolenski		RA: 103520		*
 * @author Renato Cesar Martins			RA: 073668		*
 * 														*
 * Projeto de MC302 - Parte 1						    */

package pt.c01interfaces.s01chaveid.afinder.impl;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;

public class EnquirerAdvanced implements IEnquirer {

	private IBaseConhecimento base;
	private IObjetoConhecimento obj;
	private IDeclaracao decl;
	private String[] base_animais;
	private Vector<String> base_perguntas;
	private Vector<Vector<String>> base_respostas;
	
	public EnquirerAdvanced (){
		
		base = new BaseConhecimento();
		base_animais = base.listaNomes();
		base_perguntas = new Vector<String>();
		base_respostas = new Vector<Vector<String>>();
		basePerguntas();
		baseRespostas();
	}	
	
	public void connect(IResponder responder) {
    	
		Vector<String> perguntas = new Vector<String>(base_perguntas);
		Vector<Vector<String>> respostas = cloneRespostas();
		Vector<String> animais = cloneAnimais();
		Vector<String> resp;
		
		/* Faz perguntas ao entrevistado ate que reste	*
		 * apenas um concorrente no vetor de animais	*/
		do {
			// Pergunta ao entrevistado
	    	String r = responder.ask(perguntas.firstElement());
	
			/* Checa a resposta da pergunta para cada animal,	*
			 * remove nao concorrentes, ou seja, aqueles que	*
	    	 * respondem a pergunta de forma diferente			*/
			resp = respostas.firstElement();
			for (int i = 0; i < animais.size(); i++) {
				// Remove os animais que tiveram respostas erradas
				if (!resp.elementAt(i).equalsIgnoreCase(r)){ 
					// Remove a coluna do animal que foi removido de cada pergunta
					for (int j = 0; j < perguntas.size(); j++) {
		    			Vector <String> resp_animais = new Vector<String>();
		    			resp_animais = respostas.elementAt(j);
		    			resp_animais.remove(i);
		    		}
					/* Remove o animal do vetor animais e decrementa	*
					 * i para que nao "pular" o proximo animal			*/
					animais.remove(i);
					i--;
				}
			}
			
			// Remove a pergunta ja feita do vetor de perguntas
			perguntas.remove(perguntas.firstElement());
			// Remove as respostas relacionadas a pergunta ja feita
			respostas.remove(respostas.firstElement());
    	} while (animais.size() > 1);
		
    	// Resposta final
    	responder.finalAnswer(animais.firstElement());
    }	
    

	/* Adiciona todas as perguntas ao vetor lista_perguntas	em	*
	 * forma decrescente de ocorrencias. A pergunta com	mais	*
	 * ocorrência será colocada na primeira posicao do vetor	*/
	private void basePerguntas () {
		
		int qnt, index;
		boolean index_ok;
		Vector<Integer> n_ocorrencias = new Vector<Integer>();
		
		for (int i = 0; i < base_animais.length; i++) {
			obj = base.recuperaObjeto(base_animais[i]);
				decl = obj.primeira();
			while (decl != null) {
				if (base_perguntas.indexOf(decl.getPropriedade()) == -1){
					qnt = getQuantidadePergunta(decl.getPropriedade());
					if (base_perguntas.size() != 0)
						for (index = base_perguntas.size(), index_ok = false; index > 0 && !index_ok; index--){
							if (qnt < n_ocorrencias.elementAt(index-1)){
								index_ok = true;
								index++;
							}
						}
					else
						index = 0;
					n_ocorrencias.add(index, qnt);
					base_perguntas.add(index, decl.getPropriedade());
				}
				decl = obj.proxima();
			}
		}

	}
	
	
	/* Adiciona todas as respostas de cada animal	*
	 * a cada pergunta na matriz lista_respostas	*/
	private void baseRespostas () {
    	
		Vector<String> resp;
    	for (int i = 0; i < base_perguntas.size(); i++) {
    		resp = new Vector<String>();
    		for (int j = 0; j < base_animais.length; j++) {
    			obj = base.recuperaObjeto(base_animais[j]);
            	decl = obj.primeira();
            	
            	boolean achou = false;
            	while (decl != null && !achou) {
            		if (base_perguntas.elementAt(i).equalsIgnoreCase(decl.getPropriedade())) {
            			resp.add(decl.getValor());
            			achou = true;
            		} else
            			decl = obj.proxima();
            	}
            	if (decl == null)
            		resp.add("nao sei");
    		}    		
        	base_respostas.add(resp);
        }
	}
	
	
	/* Retorna o numero de ocorrencias de uma pergunta na base	*/
	private int getQuantidadePergunta (String perg){
		
		IObjetoConhecimento o;
		IDeclaracao d;
		int qnt = 0;
		
		for (int i = 0; i < base_animais.length; i++){
			o = base.recuperaObjeto(base_animais[i]);
			d = o.primeira();
			while (d != null){
				if (d.getPropriedade().equalsIgnoreCase(perg))
					qnt++;
				d = o.proxima();
			}
		}
		
		return qnt;
	}
	
	
	/* Clona a matriz de respostas	*/
	private Vector<Vector<String>> cloneRespostas (){
		
		Vector<Vector<String>> r = new Vector<Vector<String>>();
		Vector<String> aux;
		
		for(int i = 0; i < base_respostas.size(); i++){
			aux = new Vector<String> (base_respostas.elementAt(i));
			r.add(aux);
		}
		
		return r;
	}
	
	
	/* Clona o array lista_animais em um vector	*/
	private Vector<String> cloneAnimais (){
		
		Vector<String> a = new Vector<String>();
		
		for (int i = 0; i < base_animais.length; i++)
			a.add(base_animais[i]);

		return a;
	}
	
	//END//
}