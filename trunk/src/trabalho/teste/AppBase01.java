package trabalho.teste;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;

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
