package trabalho.impl;

import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;

public class Declaracao implements IDeclaracao
{
    private String propriedade;
    private String valor;
    
    public Declaracao(String propriedade, String valor)
    {
    	this.propriedade = propriedade;
    	this.valor = valor;
    }
    
    /* (non-Javadoc)
	 * @see trabalho.impl.IDeclaracao#getPropriedade()
	 */
    public String getPropriedade()
    {
    	return propriedade;
    }
    
    /* (non-Javadoc)
	 * @see trabalho.impl.IDeclaracao#getValor()
	 */
    public String getValor()
    {
    	return valor;
    }
    
    public String toString()
    {
    	return "(propriedade: " + propriedade + ", valor: " + valor + ")";
    }
}
