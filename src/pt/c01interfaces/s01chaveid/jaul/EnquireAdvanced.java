package pt.c01interfaces.s01chaveid.jaul;

import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquireAdvanced implements IEnquirer {

	private TabelaQuestoes tabela;
	private HashSet<String> animais;
	
	public EnquireAdvanced() {
		IBaseConhecimento bc = new BaseConhecimento();

		tabela = new TabelaQuestoes(bc);
		animais = new HashSet<String>();
		String[] nomes = bc.listaNomes();
		for (String nome: nomes)
			animais.add(nome);
		
	}

	@Override
	public void connect(IResponder responder) {
		HashSet<String> respostas = new HashSet<String>(animais);
		TreeSet<Questao> tsQuestoes = tabela.getQuestoes();
		Iterator<Questao> itQuestoes = tsQuestoes.descendingIterator();
		int i = 0;
		
		while (itQuestoes.hasNext()) {
			Questao pergunta = itQuestoes.next();
			System.out.println("Questao apareceu " + pergunta.getCount() + " vezes!");
			String resposta = responder.ask(pergunta.getTexto());
			if (resposta.equalsIgnoreCase("nao sei"))
				continue;
			HashSet<String> respTabela = (HashSet<String>) tabela.getPossiveis(pergunta.getTexto(), resposta);
			if (respTabela != null)
				respostas.retainAll(respTabela);
			System.out.println("Restaram " + respostas.size() + " animais!\n");

			i++;
			if (respostas.size() < 2)
				break;
		}
		String winner = respostas.toArray(new String[0])[0];
		System.out.println(i + " tentativas!");
		if (responder.finalAnswer(winner))
			System.out.println("Eu sou bom nisso... é o " + winner + "!");
		else {
			System.out.println("Opa! Esse eu não conhecia!!" + winner);
			
		}

	}

}
