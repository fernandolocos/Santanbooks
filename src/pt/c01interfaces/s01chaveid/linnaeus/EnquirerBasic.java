package pt.c01interfaces.s01chaveid.linnaeus;

import java.util.HashMap;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;


public class EnquirerBasic implements IEnquirer
{
    String lista[];
    IBaseConhecimento bc;
	
	public EnquirerBasic()
	{
		bc = new BaseConhecimento();
		lista = bc.listaNomes();
	}
	
	
	@Override
	public void connect(IResponder responder)
	{
		IObjetoConhecimento animal;
		IDeclaracao decl;
		boolean quebrar = false;
		HashMap<String, String> perguntas = new HashMap<String, String>(200);
		
		int[] semelhantesNPerguntas = new int[50];
		String[] semelhantesNomes = new String[50];

		int i, ind = 0, nPerguntas = 0;
		String resposta = new String();
		String respostas = new String();

		for (i = 0; i < lista.length; i++)
		{
			quebrar = false;
			animal = bc.recuperaObjeto(lista[i]);
			decl = animal.primeira();
			int contPerguntas = 0;
			while (decl != null && !quebrar){
				String perg = decl.getPropriedade();
				String resp = decl.getValor();
				if (!perguntas.containsKey(perg))
					perguntas.put(perg,responder.ask(perg));
				if (perguntas.get(perg).equalsIgnoreCase(resp)){
					decl = animal.proxima();
					contPerguntas++;
				}
				else 
					quebrar = true;
			}
			if (!quebrar) {
				semelhantesNPerguntas[ind] = contPerguntas;
				semelhantesNomes[ind] = lista[i];
				resposta = lista[i];
				ind++;
			}
		}
		
		if (ind > 1)
			for (int h = 0; h < ind; h++)
				if (semelhantesNPerguntas[h] > nPerguntas){
					nPerguntas = semelhantesNPerguntas[h];
					resposta = semelhantesNomes[h];
					respostas = semelhantesNomes[h] + " ; ";
				} else if (semelhantesNPerguntas[h] == nPerguntas){
					resposta = "mais de uma opção";
					respostas += semelhantesNomes[h] + " ; ";	
				}
			
		responder.finalAnswer(resposta);
				
		/*
		 * A parte abaixo seria responsável pela saída.
		 */
		//if (responder.finalAnswer(resposta))
			//System.out.println("Estado: Encontrado ; Animal: " + resposta);
		//else if (resposta.equalsIgnoreCase("mais de uma opção"))
		//	System.out.println("Estado: Há mais de uma opção. O animal pode ser: " + respostas);
		//else
		//	System.out.println("Estado: Não Encontrado");
	}

}
