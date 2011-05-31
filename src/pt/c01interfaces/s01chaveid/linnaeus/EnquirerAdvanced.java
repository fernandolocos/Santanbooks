package pt.c01interfaces.s01chaveid.linnaeus;

import java.util.HashMap;
import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;


public class EnquirerAdvanced implements IEnquirer
{
    String lista[];
    IBaseConhecimento bc;
    Vector<String> excluidos, perguntas_relevantes;
    int qnt_animais;
	
	public EnquirerAdvanced()
	{
		bc = new BaseConhecimento();
		lista = bc.listaNomes();
		excluidos = new Vector<String>();
		qnt_animais = lista.length;
		perguntas_relevantes = perguntas_relev();
	}
	
	/**
	 * 
	 * Nome: perguntas_relev
	 * 
	 * Parâmetros: Nenhum
	 * 
	 * Função: Retorna um Vector ordenado decrescivamente com as perguntas que 
	 * mais aparecem no banco de dados. Da mais perguntada à menos perguntada.
	 * 
	 */
	private Vector<String> perguntas_relev() {
		IObjetoConhecimento animal;
		IDeclaracao decl;
		HashMap<String, Integer> perguntas = new HashMap<String, Integer>(100);
		for (String str : lista) {
			animal = bc.recuperaObjeto(str);
			decl = animal.primeira();
			while (decl != null) {
				String perg = decl.getPropriedade();
				perguntas.put(perg, perguntas.containsKey(perg) ? (perguntas.get(perg) + 1) : 1);
				decl = animal.proxima();
			}
		}
		Vector<String> pergus = new Vector<String>();
		for (int i = perguntas.size(); i > 0; i--) {
			String pergunta = HashExtraFunctions.getKey(perguntas, i, pergus);
			if (pergunta != null)
				pergus.add(pergunta);
		}
		return pergus;
	}
	
	@Override
	/**
	 * 
	 * Nome: connect
	 * 
	 * Parâmetros: responder: usuário que contém o conteúdo a ser adivinhado
	 * 
	 * Função: Função principal que inicia o programa.
	 * 
	 */
	public void connect(IResponder responder)
	{
		IObjetoConhecimento animal;
		IDeclaracao decl;
		boolean quebrar = false;
		excluidos.clear();
		
		/* Hash utilizado para armazenar perguntas já feitas para que não precisem ser feitas novamente */
		HashMap<String, String> perguntas = new HashMap<String, String>(100);
		
		int[] semelhantesNPerguntas = new int[50];
		String[] semelhantesNomes = new String[50];

		int ind = 0;
		String[] respostas = new String[2];
		
		/* Enquanto não tiver excluido pelo menos 2/3 dos animais,
		 * continuar perguntando as perguntas que mais aparecem
		 */
		for (int i = 0; excluidos.size() < qnt_animais - qnt_animais/3 && i < perguntas_relevantes.size(); i++) {
			String perg = perguntas_relevantes.elementAt(i);
			perguntas.put(perg, responder.ask(perg));
			excluir(perg, perguntas.get(perg));
		}

		/* Percorre a lista dos animais */
		for (String str : lista)
		{
			if (excluidos.contains(str))
				continue;
			quebrar = false;
			animal = bc.recuperaObjeto(str);
			decl = animal.primeira();
			int contPerguntas = 0;
			
			/* Verifica as perguntas do animal da lista, com o procurado */
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
				excluir(perg, perguntas.get(perg));
			}
			
			if (!quebrar) {
				semelhantesNPerguntas[ind] = contPerguntas;
				semelhantesNomes[ind] = str;
				ind++;
				respostas[0] = str;
			}
		}
		respostas = analisaOpcoes(ind, respostas, semelhantesNPerguntas, semelhantesNomes);
		exibeResultado(responder, respostas);

	}
	
	/**
	 * 
	 * Nome: excluir
	 * 
	 * Parâmetros: perg: pergunta em questão
	 *             resp: a resposta verdadeira do animal para a pergunta
	 *             
	 * Função: Percorre todos os animais e exclui das possibilidades aqueles
	 * que contenham resposta diferente da verdadeira para a dada pergunta.
	 * Faz isso adicionando tais animais à um vector, que faz o processo
	 * pulá-los no connect, sem fazer perguntas deles.
	 * 
	 */
	private void excluir(String perg, String resp) 
	{
		for (String str : lista)
		{
			if (excluidos.contains(str))
				continue;
			IObjetoConhecimento animal;
			IDeclaracao decl;
			animal = bc.recuperaObjeto(str);
			decl = animal.primeira();
			while (decl != null){
				if (decl.getPropriedade().equalsIgnoreCase(perg) && 
						  !decl.getValor().equalsIgnoreCase(resp)) {
					excluidos.add(str);
					decl = null;
				}
				decl = animal.proxima();
			}
		}
	}
	
	/**
	 * 
	 * Nome: analisaOpcoes
	 * 
	 * Parâmetros: ind: número de conteúdos semelhantes
	 *             respostas: última resposta encontrada na busca
	 *             semelhantesNPerguntas: vetor com o número de perguntas de cada conteúdo semelhante
	 *             semelhantesNomes: vetor com o nome de cada conteúdo semelhante
	 *             
	 * Função: Verifica qual o conteúdo quando há mais de um que se encaixa nas perguntas
	 * 
	 */
	public String[] analisaOpcoes(int ind, String[] respostas, int[] semelhantesNPerguntas, String[] semelhantesNomes){
		int nPerguntas = 0;
		if (ind > 1)
			for (int h = 0; h < ind; h++)
				if (semelhantesNPerguntas[h] > nPerguntas){
					nPerguntas = semelhantesNPerguntas[h];
					respostas[0] = semelhantesNomes[h];
					respostas[1] = semelhantesNomes[h] + " ; ";
				} else if (semelhantesNPerguntas[h] == nPerguntas){
					respostas[0] = "mais de uma opção";
					respostas[1] += semelhantesNomes[h] + " ; ";	
				}
		return respostas;
	}

	/**
	 * 
	 * Nome: exibeResultado
	 * 
	 * Parâmetros: responder: usuário que contém o conteúdo a ser adivinhado
	 *             respostas: [0] resposta encontrada na busca
	 *                        [1] respostas encontradas (quando ocorre)
	 *  
	 * Função: Exibe o resultado da busca: se foi encontrado, quando foi encontrada mais de uma opção, ou se não foi possível encontrar.
	 * 
	 */
	public void exibeResultado(IResponder responder, String[] respostas){
		responder.finalAnswer(respostas[0]);
		/*
		 * A parte abaixo seria responsável por mostrar o resultado na tela.
		if (responder.finalAnswer(respostas[0]))
			System.out.println("Estado: Encontrado ; Animal: " + respostas[0]);
		else if (respostas[0].equalsIgnoreCase("mais de uma opção"))
			System.out.println("Estado: Há mais de uma opção. O animal pode ser: " + respostas[1]);
		else
			System.out.println("Estado: Não Encontrado");
		*/
	}

}
