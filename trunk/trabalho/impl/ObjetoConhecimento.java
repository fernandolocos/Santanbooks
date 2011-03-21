package trabalho.impl;

import trabalho.inter.IDeclaracao;
import trabalho.inter.IObjetoConhecimento;


public class ObjetoConhecimento implements IObjetoConhecimento
{
    private IDeclaracao declaracoes[];
    private int corrente = 0;
    
    public ObjetoConhecimento(IDeclaracao declaracoes[])
    {
    	this.declaracoes = declaracoes;
    }
    
    /* (non-Javadoc)
	 * @see trabalho.impl.IObjetoConhecimento#primeira()
	 */
    public IDeclaracao primeira()
    {
    	IDeclaracao priDecl = null;
    	
    	corrente = 0;
    	
    	if (declaracoes != null && declaracoes.length > 0)
    		priDecl = declaracoes[0];
    	
    	return priDecl;
    }
    
    /* (non-Javadoc)
	 * @see trabalho.impl.IObjetoConhecimento#proxima()
	 */
    public IDeclaracao proxima()
    {
    	IDeclaracao proxDecl = null;
    	
    	if (declaracoes != null && corrente < declaracoes.length-1) {
    		corrente++;
    		proxDecl = declaracoes[corrente];
    	}
    	
    	return proxDecl;
    }
}
