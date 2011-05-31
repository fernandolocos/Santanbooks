package pt.c01interfaces.s01chaveid.tochinhas2.teste;

import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c01interfaces.s01chaveid.tochinhas2.impl.*;

public class AppTeste {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IEnquirer eb = new EnquirerBasic();
        Responder rb = new Responder("tigre");
        eb.connect(rb);
	}

}
