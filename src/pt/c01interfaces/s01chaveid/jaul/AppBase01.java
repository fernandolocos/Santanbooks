package pt.c01interfaces.s01chaveid.jaul;

import java.util.HashSet;
import java.util.Set;

import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class AppBase01 implements IResponder
{
	
	public static void main(String[] args)
	{
		
		
		AppBase01 app = new AppBase01();
		
		EnquireAdvanced teste = new EnquireAdvanced();
		teste.connect(app);
		
		
	}

	/*
"e um mamifero", "sim"
"tem quatro patas", "sim"
"sabe latir", "nao"
"gosta de beber leite", "nao"
"ele voa", "nao"
 */
	
	@Override
	public String ask(String question) {
		if (question.equalsIgnoreCase("e um mamifero"))
			return "sim";
		else if (question.equalsIgnoreCase("tem quatro patas"))
			return "sim";
		else if (question.equalsIgnoreCase("ele voa"))
			return "nao";
		else if (question.equalsIgnoreCase("sabe latir"))
			return "nao";
		else if (question.equalsIgnoreCase("gosta de beber leite"))
			return "nao";
		else return "nao sei";
	}

	@Override
	public boolean finalAnswer(String answer) {
		System.out.println(answer);
		return answer.equalsIgnoreCase("tigre");
	}

}
