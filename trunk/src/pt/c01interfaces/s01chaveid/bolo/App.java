package pt.c01interfaces.s01chaveid.bolo;

import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.bolo.EnquirerBasic;
import pt.c01interfaces.s01chaveid.bolo.EnquirerAdvanced;

public class App
{
	public static void main(String[] args)
	{
		IEnquirer eb = new EnquirerBasic();
        IResponder rb = new Responder("tigre");
        eb.connect(rb);

        IEnquirer ea = new EnquirerAdvanced();
        IResponder ra = new Responder("tigre");
        ea.connect(ra);
	}

}
