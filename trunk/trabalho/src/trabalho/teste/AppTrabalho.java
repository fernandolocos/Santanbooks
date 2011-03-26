package trabalho.teste;

import trabalho.impl.EnquirerBasic;
import trabalho.impl.Responder;
import trabalho.inter.IEnquirer;
import trabalho.inter.IResponder;

public class AppTrabalho 
{

	public static void main(String[] args)
	{
		IEnquirer e = new EnquirerBasic();
		IResponder r = new Responder("tiranossauro");
       
		e.connect(r);
	}

}
