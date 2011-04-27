package trabalho.teste;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import trabalho.impl.EnquirerBasic;
import trabalho.impl.Responder;

public class AppBase03 
{
	public static void main(String[] args)
	{
       IEnquirer e = new EnquirerBasic();
       IResponder r = new Responder("tiranossauro");
       
       e.connect(r);
	}
}
