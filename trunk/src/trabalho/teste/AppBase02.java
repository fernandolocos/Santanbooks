package trabalho.teste;

import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import trabalho.impl.Responder;

public class AppBase02 
{
	public static void main(String[] args)
	{
       IResponder resp = new Responder("tiranossauro");
       System.out.println(resp.ask("e um dinossauro"));
       System.out.println(resp.ask("teste"));
	}
	
}
