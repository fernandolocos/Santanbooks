package pt.c01interfaces.s01chaveid.lobo;

import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
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
public class EnquirerAdvanced implements IEnquirer
{
	private Vector<EnquirerHelper> enqHelper;
	private Vector<Question> perguntas;
	private Vector<String> perguntasJaFeitas;
	
	public EnquirerAdvanced()
	{
		enqHelper = new Vector<EnquirerHelper>();
		perguntas = new Vector<Question>();
		perguntasJaFeitas = new Vector<String>();
		
		String[] listaAnimais = new BaseConhecimento().listaNomes();
		
		//adicionando todos os animais à lista
		for(int i = 0; i < listaAnimais.length; i++)
			enqHelper.add(new EnquirerHelper(listaAnimais[i]));
		
		//criando a lista da ordem de preferência das perguntas
		ordenaListaPerguntas();
	}
	
	/**
	 * Método para (re)formular a ordem de perguntas a serem feitas
	 */
	private void ordenaListaPerguntas()
	{
		perguntas.clear();
		Hashtable<String,Question> perguntasTemp = new Hashtable<String, Question>();
		Vector<String> perguntasAux = new Vector<String>();
		String perguntaAtual;
		//percorrendo todos animais
		for(int i = 0; i < enqHelper.size(); i++)
		{
			//percorrendo todas as perguntas respectivas ao animal de índice "i"
			for(int j = 0; j < enqHelper.get(i).getNumPerguntas(); j++)
			{
				perguntaAtual = enqHelper.get(i).getPergunta(j);
				//verificando se a pergunta já foi feita
				if(!perguntasJaFeitas.contains(perguntaAtual))
				{
					if(perguntasTemp.containsKey(perguntaAtual))
					{
						//adicionando frequência e a resposta do animal à pergunta
						perguntasTemp.get(perguntaAtual).add(enqHelper.elementAt(i).ask(perguntaAtual));
					}
					else
					{
						//adicionando uma nova pergunta
						perguntasTemp.put(perguntaAtual, new Question(perguntaAtual));
						perguntasTemp.get(perguntaAtual).add(enqHelper.elementAt(i).ask(perguntaAtual));
						perguntasAux.add(perguntaAtual);
					}
				}
			}
		}
		//passando as perguntas de "Hashtable" para "Vector", e atualizando o número de perguntas
		int n = perguntasAux.size();
		for(int i = 0; i < n; i++)
		{
			Question temp = perguntasTemp.get(perguntasAux.get(i));
			temp.setNumPerguntas(n);
			perguntas.add(temp);
		}
		//ordenando as perguntas por ordem de preferência
		Comparator<Question> comparador = new Comparador();
		Collections.sort(perguntas, comparador);
	}
	
	@Override
	public void connect(IResponder responder)
	{
		boolean reset;
		String perguntaAtual, perguntaRespondida;
		//percorrendo todas as perguntas (na ordem de preferencia)
		for(int k = 0; k < perguntas.size() && enqHelper.size() > 1; k++)
		{
			reset = false;
			perguntaAtual = perguntas.get(k).getPergunta();
			perguntasJaFeitas.add(perguntaAtual);
			perguntaRespondida = responder.ask(perguntaAtual);
			//percorrendo todos os animais
			for(int i = 0; i < enqHelper.size() && enqHelper.size() > 1; i++)
			{
				//se a resposta deste animal for diferente da resposta do Responder...
				if(!perguntaRespondida.equalsIgnoreCase(enqHelper.get(i).ask(perguntaAtual)))
				{
					//removendo o animal da lista de animais possíveis
					enqHelper.remove(i);
					i--;
					reset = true;
				}
			}
			//reordenando a ordem das perguntas (caso pelo menos algum animal tenha sido removido da lista)
			//(após remover determinados animais, pode ser que certas perguntas já não tenham mais preferência)
			if(reset)
			{
				ordenaListaPerguntas();
				k = 0;
			}
		}
		//pergunta final ao Responder
		if(responder.finalAnswer(enqHelper.get(0).getNomeAnimal())) System.out.println("Oba! Acertei! O animal era o(a) " + enqHelper.get(0).getNomeAnimal());
        else System.out.println("Que pena..! Errei! Pensei que o animal fosse (a)o " + enqHelper.get(0).getNomeAnimal());
	}
}
