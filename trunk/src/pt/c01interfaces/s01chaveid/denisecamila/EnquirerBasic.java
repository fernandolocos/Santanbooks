/* Denise Giubilei Santos Filha - ra102002
	Camila Pelizaro e Silva - ra101765 */

package pt.c01interfaces.s01chaveid.denisecamila;

import java.util.ArrayList;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer
{
    private IBaseConhecimento bc;
    private IDeclaracao decl;
    private IObjetoConhecimento obj;
    private String[] lista;
    private ArrayList<String> perguntas = new ArrayList<String>();
    private ArrayList<String> respostas = new ArrayList<String>();
    private int index = -1;
	
    public EnquirerBasic()
    {
    	bc = new BaseConhecimento();
    	lista = bc.listaNomes(); //lista com todos os animais da base
    }
	
    /* Método que retorna a primeira pergunta do próximo animal na lista*/
	 private IDeclaracao proximoCandidato()
	 {
	 	decl = null;
	 	if (index<lista.length - 1){
	 		index++;
	 		obj = bc.recuperaObjeto(lista[index]);
	 		decl = obj.primeira();
	 	}
	 	return decl;
	 }
	 
	 public void connect(IResponder responder)
	 {
		 decl = proximoCandidato();	
		 do{
			 String pergunta = decl.getPropriedade();
	         String resposta = decl.getValor();
	               
	         /* Se a pergunta já foi feita antes, a procura para verificação*/
	         if (perguntas.contains(pergunta)){           	
	          	if (respostas.get(perguntas.indexOf(pergunta)).equalsIgnoreCase(resposta))	
	           		decl = obj.proxima();
	           	else
	           		decl = proximoCandidato();
             }
	         else
	         {
	         	/*Se a pergunta nunca foi feita, a faz, e a guarda com a resposta correspondente*/
	        	 String respostaCerta = responder.ask(pergunta);
	        	 perguntas.add(pergunta);
	             respostas.add(respostaCerta);
	             
	        	 if (respostaCerta.equalsIgnoreCase(resposta))
	        		 decl = obj.proxima(); //Se a as respostas coincidem, continua checando o mesmo animal.         
		         else 
		          	decl = proximoCandidato(); //se não, pega o próximo na lista.		
	         }
	         
	     }while(decl != null); //enquanto houverem perguntas a serem feitas de animais na lista
		 
		 if(responder.finalAnswer(lista[index]))
	        System.out.println(lista[index]);
		 else 
    	   System.out.println("Animal não encontrado.");
    }
}
