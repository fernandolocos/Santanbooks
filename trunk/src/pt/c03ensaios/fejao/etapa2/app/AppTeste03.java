package pt.c03ensaios.fejao.etapa2.app;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c03ensaios.frango.app.Responder;

public class AppTeste03 {

	public static void main(String[] args) {

        IBaseConhecimento bc = new BaseConhecimento();
        String[] listaNomes = bc.listaNomes();

        IEnquirer eb = new EnquirerBasic();
        IEnquirer ea = new EnquirerAdvanced();
        IResponder ra;
        for (int i = 0; i < listaNomes.length; i++) {
            System.out.print("Basic: ");
            ra = new Responder(listaNomes[i]);
            eb.connect(ra);
        }
        
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        for (int i = 0; i < listaNomes.length; i++) {
            System.out.print("Advanced: ");
            ra = new Responder(listaNomes[i]);
            ea.connect(ra);
        }	
	}


}
