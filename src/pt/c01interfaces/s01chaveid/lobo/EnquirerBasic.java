package pt.c01interfaces.s01chaveid.lobo;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

/**
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
public class EnquirerBasic implements IEnquirer
{
	private boolean reset;
	private Vector<EnquirerHelper> enqHelper;
	private Vector<String> perguntasFeitas;
	private String perguntaAtual, perguntaRespondida;
	
	public EnquirerBasic()
	{
		perguntasFeitas = new Vector<String>();
		enqHelper = new Vector<EnquirerHelper>();
		
		String[] listaAnimais = new BaseConhecimento().listaNomes();
		//adicionando todos os animais à lista
		for(int i = 0; i < listaAnimais.length; i++)
		{
			enqHelper.add(new EnquirerHelper(listaAnimais[i]));
		}
	}
	
	public void connect(IResponder responder)
	{
		for(int i = 0; i < enqHelper.size() && enqHelper.size() > 1; i++)
		{
			reset = false;
			for(int j = 0; j < enqHelper.elementAt(i).getNumPerguntas() && enqHelper.size() > 1; j++)
			{
				perguntaAtual = enqHelper.elementAt(i).getPergunta(j);
				//verificando se a pergunta atual ja foi feita anteriormente
				if(!perguntasFeitas.contains(perguntaAtual))
				{
					//adicionando a pergunta atual às perguntas já feitas
					perguntasFeitas.add(perguntaAtual);
					perguntaRespondida = responder.ask(perguntaAtual);
					for(int k = 0; k < enqHelper.size() && enqHelper.size() > 1; k++)
					{
						//comparando as respostas de cada enqHelper e do Responder
						if(!enqHelper.elementAt(k).ask(perguntaAtual).equalsIgnoreCase(perguntaRespondida))
						{
							//se a resposta for diferente da do Responder, remove o elemento
							enqHelper.remove(k);
							reset = true;
							j = -1;
							k--;
						}
					}
				}
			}
			if (reset) i = -1;
		}
		//pergunta final ao Responder
        if(responder.finalAnswer(enqHelper.elementAt(0).getNomeAnimal())) System.out.println("Oba! Acertei! O animal era o(a) " + enqHelper.elementAt(0).getNomeAnimal());
        else System.out.println("Que pena..! Errei!");
        
	}
}
