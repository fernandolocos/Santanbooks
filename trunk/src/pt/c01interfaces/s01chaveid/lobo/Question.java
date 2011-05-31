package pt.c01interfaces.s01chaveid.lobo;

/**
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
public class Question
{
	private String pergunta;
	private int frequencia, numSim, numNao, numPerguntas;
	
	public Question(String pergunta)
	{
		this.pergunta = pergunta;
		frequencia = numSim = numNao = 0;
	}
	
	public void setNumPerguntas(int numPerguntas)
	{
		this.numPerguntas = numPerguntas;
	}
	
	/**
	 * Incrementa a frequ�ncia desta pergunta, e tamb�m a quantidade de respostas
	 * "Sim" ou "Nao" � mesma
	 * @param resp	Resposta � pergunta
	 */
	public void add(String resp)
	{
		frequencia++;
		if(resp.equalsIgnoreCase("Sim")) numSim++;
		else numNao++;
	}
	
	/**
	 * Retorna uma String com a pergunta a qual este elemento se refere
	 * @return	Pergunta a qual este elemento se refere
	 */
	public String getPergunta()
	{
		return pergunta;
	}
	
	/**
	 * Retorna a frequ�ncia de ocorr�ncia desta pergunta
	 * @return	Frequ�ncia de ocorr�ncia
	 */
	public int getFrequencia()
	{
		return frequencia;
	}
	
	/**
	 * Retorna a prefer�ncia desta pergunta (para ser usado no sort da lista)
	 * @return	Prefer�ncia desta pergunta
	 */
	public int preferencia()
	{
		return (int)(23*frequencia+(numPerguntas/(Math.abs(numSim - numNao)+1)))*13;
	}
}
