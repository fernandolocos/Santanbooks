package pt.c01interfaces.s01chaveid.bolo;

import java.util.Random;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;

public class AppBase02
{

	public static void main(String[] args)
	{
		IBaseConhecimento bc = new BaseConhecimento();
		String[] a = bc.listaNomes();
		Random r = new Random();
		
		IEnquirer eb = new EnquirerAdvanced();
        IResponder rb;
        for (int i = 1 + r.nextInt(25); i != 0; i--)
        {
        	rb = new Responder(a[r.nextInt(a.length)]);
            eb.connect(rb);	
        }
	}
}
