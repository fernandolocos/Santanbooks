package trabalho.teste;

import trabalho.impl.BaseConhecimento;
import trabalho.impl.EnquirerAdvanced;
import trabalho.impl.EnquirerBasic;
import trabalho.impl.Responder;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IEnquirer;
import trabalho.inter.IResponder;

public class AppTrabalho 
{

	public static void main(String[] args)
	{
		IBaseConhecimento bc = new BaseConhecimento();
		String[] animais = bc.listaNomes();
		
		for(int i = 0; i < animais.length; i++){
			
			System.out.println("Animal pensado: "+animais[i]);
			System.out.print("EnquirerBasic: ");
			IEnquirer e = new EnquirerBasic();
			IResponder r = new Responder(animais[i]);
			e.connect(r);
			
			System.out.print("EnquirerAdvanced: ");
			IEnquirer ea = new EnquirerAdvanced();
			IResponder ra = new Responder(animais[i]);
			ea.connect(ra);
			System.out.print("\n");
		}
		
	}

}
