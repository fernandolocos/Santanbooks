package pt.c01interfaces.s01chaveid.afinder.exemplo;

import java.util.Random;

import pt.c01interfaces.s01chaveid.afinder.impl.*;
import pt.c01interfaces.s01chaveid.s01base.impl.*;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;

public class AppBase01
{

	public static void main(String[] args)
	{
		IBaseConhecimento bc = new BaseConhecimento();
		String[] a = bc.listaNomes();
		Random r = new Random();
		int index;
		IEnquirer eb = new EnquirerBasic();
		IEnquirer ea = new EnquirerAdvanced();
        IResponder rb;
        
        for (int i = 1 + r.nextInt(25); i != 0; i--)
        {
        	index = r.nextInt(a.length);
        	System.out.println("ANIMAL PROCURADO: " + a[index]);
          	rb = new Responder(a[index]);
           	System.out.print(" - BASIC : ");
            eb.connect(rb);	
           	System.out.print(" - ADVANCED : ");
           	ea.connect(rb);
           	System.out.println();
        }
	}
}
