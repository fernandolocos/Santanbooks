package pt.c03ensaios.relu.ProgramaCompleto;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Statistics;

public class Application {
	public static void main(String[] args) {
		IBaseConhecimento base = new BaseConhecimento();
		IEnquirer enquirer = new Enquirer();
		int index = (Double.valueOf(Math.random()*100)).intValue()%base.listaNomes().length;
		String animal = base.listaNomes()[index];
		System.out.println("Procurando o animal "+ animal.toUpperCase());
		System.out.println("------------------------------------------");
		IResponder responder = new Responder(new Statistics(), animal);
		enquirer.connect(responder);
		System.out.println("------------------------------------------");
	}

}
