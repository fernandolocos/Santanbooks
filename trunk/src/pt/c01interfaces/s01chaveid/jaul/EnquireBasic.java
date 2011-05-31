package pt.c01interfaces.s01chaveid.jaul;

import java.util.HashSet;
import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquireBasic implements IEnquirer {
	
	private Vector<Animal> animais;
	private HashSet<IObjetoConhecimento> possiveis;
	private HashSet<String> setQuestoes;
	
	public EnquireBasic() {
		IBaseConhecimento bc = new BaseConhecimento();
		
		String nomeAnimais[] = bc.listaNomes();
		animais = new Vector<Animal>(nomeAnimais.length);
		setQuestoes = new HashSet<String>();
		possiveis =   new HashSet<IObjetoConhecimento>();
		
		for (String nome: nomeAnimais) {
			Animal edmundo = new Animal(nome, bc.recuperaObjeto(nome));
			animais.add(edmundo);
			setQuestoes.addAll(edmundo.getQuestoes());
		}
		
	}

	@Override
	public void connect(IResponder responder) {
		possiveis.clear();
		possiveis.addAll(animais);
		int i = 1;
		for (String pergunta: setQuestoes) {
			String resposta = responder.ask(pergunta);
			if (resposta.equalsIgnoreCase("nao sei"))
				continue;
			predacao(pergunta, resposta);
			if (animais.size() < 2)
				break;
		}
		
		if (animais.size() == 0)
			System.out.println("Acho que você errou alguma resposta, não?!");
		else {
			Animal winner = possiveis.toArray(new Animal[0])[0];
			if (responder.finalAnswer(winner.getNome()))
				System.out.println("Eu sou bom nisso... é o " + winner.getNome() + "!");
			else {
				System.out.println("Opa! Esse eu não conhecia!!");
				
			}
		}

	}

	private void predacao(String pergunta, String resposta) {
		HashSet<IObjetoConhecimento> tmpPossiveis = new HashSet<IObjetoConhecimento>();
		tmpPossiveis.clear();
		for (IObjetoConhecimento animal: possiveis) {
			IDeclaracao decl = animal.primeira();
			while ( decl != null ) {
				if (decl.getPropriedade().equalsIgnoreCase(pergunta)) {
					if (decl.getValor().equalsIgnoreCase(resposta)) {
						tmpPossiveis.add(animal);
					}
				}
				decl = animal.proxima();
			}
		}
		possiveis.retainAll(tmpPossiveis);
	}


}
