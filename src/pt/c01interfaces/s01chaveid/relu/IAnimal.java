package pt.c01interfaces.s01chaveid.relu;

import java.util.LinkedList;

import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;


public interface IAnimal {
	
	public IObjetoConhecimento getObjetoConhecimento ();
	public String getNome ();
	public LinkedList<String> getPerguntas();
	public LinkedList<String> getRespostas();

}
