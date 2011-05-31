package pt.c01interfaces.s01chaveid.hayasida;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.hayasida.EnquirerBasic;
import pt.c01interfaces.s01chaveid.hayasida.Responder;

public class App
{
	public static void main(String[] args)
	{
		IEnquirer eb = new EnquirerBasic();
        IResponder rb = new Responder("tigre");
        eb.connect(rb);

	}

}
