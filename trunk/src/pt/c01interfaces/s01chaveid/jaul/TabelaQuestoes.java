package pt.c01interfaces.s01chaveid.jaul;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.TreeSet;

import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;

public class TabelaQuestoes {
	
	private TreeSet<Questao> questoes;
	private Hashtable<String, Integer> contagem = new Hashtable<String, Integer>();
	private Hashtable<String, ArrayList<HashSet<String>>> respostas = 
				new Hashtable<String, ArrayList<HashSet<String>>>();

	public TabelaQuestoes(IBaseConhecimento base) {
		
		String animais[] = base.listaNomes();
		for (String nome: animais) {
			Animal paca = new Animal(nome, base.recuperaObjeto(nome));
			IDeclaracao decl = paca.primeira();
			while (decl != null) {
				String qi = decl.getPropriedade();
				
				// contagem de questoes
				if (!contagem.containsKey(qi)) {
					contagem.put(qi, new Integer(1));
				} else {
					Integer qtd = contagem.get(qi);
					qtd = qtd + 1;
					contagem.put(qi, qtd);
				}
				
				// mapeamento de respostas
				if (respostas.containsKey(qi)) {
					ArrayList<HashSet<String>> resps = respostas.get(qi);
					if (decl.getValor().equalsIgnoreCase("sim"))
						resps.get(0).add(nome);
					if (decl.getValor().equalsIgnoreCase("nao"))
						resps.get(1).add(nome);
					respostas.put(qi, resps);
				} else {
					ArrayList<HashSet<String>> resps = new ArrayList<HashSet<String>>();
					HashSet<String> hsTem = new HashSet<String>(),
									hsNumTem = new HashSet<String>();
					if (decl.getValor().equalsIgnoreCase("sim"))
						hsTem.add(nome);
					if (decl.getValor().equalsIgnoreCase("nao"))
						hsNumTem.add(nome);
					resps.add(0, hsTem);
					resps.add(1, hsNumTem);
					respostas.put(qi, resps);
				}
				decl = paca.proxima();
			}
		}
		
	}
	
	public TreeSet<Questao> getQuestoes() {
		TreeSet<Questao> ts = new TreeSet<Questao>();
		Set<String> quests = contagem.keySet();
		for(String q: quests) {
			Questao quest = new Questao(q, contagem.get(q));
			ts.add(quest);
		}
		return ts;
	}
	
	public Set<String> getPossiveis(String pergunta, String resposta) {
		HashSet<String> set = null;
		if (resposta.equalsIgnoreCase("sim"))
			set = respostas.get(pergunta).get(0);
		if (resposta.equalsIgnoreCase("nao"))
			set = respostas.get(pergunta).get(1);
		return set;
	}
	

}
