package pt.c01interfaces.s01chaveid.fabsouza;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerAdvanced implements IEnquirer
{
	 private String animal[];
	    private Vector <String> perguntaResp;
	    private Vector <String> resposta;    
	    private IObjetoConhecimento obj;
	    private IBaseConhecimento bc;
		
		{
	        bc = new BaseConhecimento();
	        animal = bc.listaNomes();
			
	        obj = bc.recuperaObjeto(animal[0]);
	        perguntaResp = new Vector<String>();
	        resposta = new Vector<String>();
		}
		
		public void connect(IResponder responder)
		{
			int i = 0;
			String resp;
			boolean acertei = false;
			IDeclaracao decl = obj.primeira();

			while (decl != null)
			{
				acertei = true;
				
				/*Verifica se a pergunta já foi respondida*/
				if (!perguntaResp.contains(decl.getPropriedade()))
				{
					/*Faz a pergunta e guarda a respostas para futuras consultas*/
		    		resp = responder.ask(decl.getPropriedade());
					perguntaResp.add(decl.getPropriedade());
					resposta.add(resp);
					
					if (resp.equalsIgnoreCase(decl.getValor()))
						decl = obj.proxima();					
					else 
						acertei = false;
				}						
				else 
					/*Verifica se a resposta guardada é igual a atual*/
					if (respostaIgual(decl.getPropriedade(), decl.getValor()))
						decl = obj.proxima();
					 else 
						 acertei = false;
				
				/*Se alguma resposta estiver diferente, tenta próximo animal*/
				if (!acertei)
					if (animal.length > i+1)
					{
						obj = bc.recuperaObjeto(animal[++i]);
						decl = obj.primeira();
					}
					else decl = null;
			}				

			acertei = responder.finalAnswer(animal[i]);						
		}
		
		/*Retorna true se as respostas forem iguais*/
		private boolean respostaIgual(String propriedade, String valor)
		{
			return (resposta.get(perguntaResp.indexOf(propriedade)).equalsIgnoreCase(valor));
		}
}
