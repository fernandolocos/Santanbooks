package trabalho.teste;

import trabalho.impl.Enquirer;
import trabalho.impl.Responder;
import trabalho.inter.IEnquirer;
import trabalho.inter.IResponder;

public class AppBase03 
{
	public static void main(String[] args)
	{
       IEnquirer e = new Enquirer("tiranossauro");
       IResponder r = new Responder("tiranossauro");
       
       e.connect(r);
	}
}
