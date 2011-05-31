package pt.c01interfaces.s01chaveid.jaul;

import java.util.Collection;
import java.util.HashSet;
import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;

public class Animal implements IObjetoConhecimento {
	
	private String nome;
	private Vector<IDeclaracao> decl;
	
	private int corrente = 0;
	
	public Animal(String nome, IObjetoConhecimento ioc) {
		super();
		this.nome = nome;
		this.decl = new Vector<IDeclaracao>();
		IDeclaracao d = ioc.primeira();
		while (d != null) {
			decl.add(d);
			d = ioc.proxima();
		}
	}

	@Override
	public IDeclaracao primeira() {
		IDeclaracao declRetorno = null;
		
		corrente = 0;
		
		if ((decl != null) && (decl.size() > 0))
			declRetorno = decl.get(corrente);
		
		return declRetorno;
	}

	@Override
	public IDeclaracao proxima() {
		IDeclaracao declRetorno = null;
		
		corrente++;
		
		if ((decl != null) && (decl.size() > 0) && (corrente < decl.size()))
			declRetorno = decl.get(corrente);
		
		return declRetorno;
	}
	
	public Collection<String> getQuestoes() {
		HashSet<String> quest = new HashSet<String>();
		for (IDeclaracao d: decl)
			quest.add(d.getPropriedade());
		return quest;
	}

	public String getNome() {
		return nome;
	}

}
