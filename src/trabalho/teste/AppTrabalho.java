package trabalho.teste;

import trabalho.impl.EnquirerAdvanced;
import trabalho.impl.Responder;
import trabalho.inter.IEnquirer;
import trabalho.inter.IResponder;

public class AppTrabalho 
{

	public static void main(String[] args)
	{
		IEnquirer e = new EnquirerAdvanced();
		IResponder r = new Responder("cachorro");
       
		e.connect(r);
	}

}
