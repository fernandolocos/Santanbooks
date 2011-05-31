package pt.c01interfaces.s01chaveid.s02exemplo.impl;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class EnquirerBasic implements IEnquirer
{
    IObjetoConhecimento obj;
	
	public EnquirerBasic()
	{
        IBaseConhecimento bc = new BaseConhecimento();
		
		obj = bc.recuperaObjeto("tiranossauro");
	}
	
	
	@Override
	public void connect(IResponder responder)
	{
		IDeclaracao decl = obj.primeira();
		
		while (decl != null && responder.ask(decl.getPropriedade()).equalsIgnoreCase(decl.getValor()))
			decl = obj.proxima();
		
		boolean acertei = false;
		
		if (decl == null)
			acertei = responder.finalAnswer("tiranossauro");
		else
			acertei = responder.finalAnswer("nao conheco");
		
		if (acertei)
			System.out.println("Oba! Acertei!");
		else
			System.out.println("fuem! fuem! fuem!");

	}

}
