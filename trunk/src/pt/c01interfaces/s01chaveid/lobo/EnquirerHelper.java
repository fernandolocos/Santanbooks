package pt.c01interfaces.s01chaveid.lobo;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;

/**
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
public class EnquirerHelper
{
	private IBaseConhecimento base;
	private String nomeAnimal;
	private IObjetoConhecimento obj;
	private IDeclaracao decl;
	private int numPerguntas;
	
	/**
	 * @param nomeAnimal	String com o nome do animal a que este elemento ser� referente
	 */
	public EnquirerHelper(String nomeAnimal)
	{
		this.nomeAnimal = nomeAnimal;
		base = new BaseConhecimento();
		obj = base.recuperaObjeto(nomeAnimal);
		int i;
		decl = obj.primeira();
		//contando o n�mero de perguntas respectivas ao animal
		for(i = 0; decl != null; i++)
		{
			decl = obj.proxima();
		}
		this.numPerguntas = i;
	}
	
	/**
	 * Retorna uma String com o nome do animal deste elemento
	 * @return	String com o nome do animal
	 */
	public String getNomeAnimal()
	{
		return this.nomeAnimal;
	}
	
	/**
	 * Retorna o n�mero total de perguntas referentes ao animal deste elemento
	 * @return	n�mero total de perguntas
	 */
	public int getNumPerguntas()
	{
		return this.numPerguntas;
	}
	
	/**
	 * Retorna uma String com a pergunta referente ao �ndice "index" dado
	 * @param index	�ndice da pergunta, referente ao animal deste elemento
	 * @return	String com a respectiva pergunta
	 */
	public String getPergunta(int index)
	{
		decl = obj.primeira();
		for(int i = 0; i < index; i++)
		{
			decl = obj.proxima();
		}
		return decl.getPropriedade();
	}
	
	/**
	 * Responde � pergunta, segundo o respectivo animal deste elemento
	 * 
	 * @param pergunta String da pergunta
	 * @return 	Retorna a resposta da pergunta ("sim" ou "nao") ou "nao sei", caso esta pergunta nao
	 * 			esteja dispon�vel referente ao animal deste elemento.
	 */
	public String ask(String pergunta)
	{
		decl = obj.primeira();
		while(decl != null)
		{
			if(decl.getPropriedade().equalsIgnoreCase(pergunta))
			{
				return decl.getValor();
			}
			else decl = obj.proxima();
		}
		return "Nao sei";
	}
}
