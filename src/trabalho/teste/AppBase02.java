package trabalho.teste;

import trabalho.impl.Responder;
import trabalho.inter.IResponder;

public class AppBase02 
{
	public static void main(String[] args)
	{
       IResponder resp = new Responder("tiranossauro");
       System.out.println(resp.ask("e um dinossauro"));
       System.out.println(resp.ask("teste"));
	}
	
}
