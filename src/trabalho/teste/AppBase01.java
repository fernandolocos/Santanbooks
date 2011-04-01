package trabalho.teste;

import trabalho.impl.BaseConhecimento;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IObjetoConhecimento;

public class AppBase01
{
	public static void main(String[] args)
	{
        IBaseConhecimento base = new BaseConhecimento();
        
        IObjetoConhecimento obj = base.recuperaObjeto("tiranossauro");
        
        IDeclaracao decl = obj.primeira();
        while (decl != null) {
        	System.out.println(decl);
        	decl = obj.proxima();
        }
	}

}
