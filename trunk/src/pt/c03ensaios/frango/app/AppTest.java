package pt.c03ensaios.frango.app;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class AppTest {

	public static void main(String[] args) {
		String animalName;

		BaseConhecimento bc = new BaseConhecimento();
		String namesList[] = bc.listaNomes();
		
		//IEnquirer eb = new EnquirerBasic();
		IEnquirer ea = (IEnquirer) new EnquirerAdvanced();
		IResponder responder = null;

		// runs all the animals for the enquirer basic and advanced
		for (int i = 0; i < namesList.length; i++) {
			animalName = namesList[i];
			//System.out.print("Basic:    ");
			//enquirerTest(eb, responder, animalName);
			System.out.print("Advanced: ");
			enquirerTest(ea, responder, animalName);
		}
	}

	public static void enquirerTest(IEnquirer enquirer, IResponder responder, String animalName) {		
		responder = new Responder(animalName);

		enquirer.connect(responder);
	}
}
