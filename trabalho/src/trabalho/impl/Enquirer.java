package trabalho.impl;

import trabalho.impl.BaseConhecimento;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IEnquirer;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class Enquirer implements IEnquirer 
{
	private String[] animais;
	IObjetoConhecimento[] obj;
	
	public Enquirer()
	{
		IBaseConhecimento bc = new BaseConhecimento();
		animais = bc.listaNomes();
		obj = new IObjetoConhecimento[animais.length];
		
		// criando um vetor de objetos com todos os animais
		for(int i = 0; i < animais.length; i++)
		{
			obj[i] =  bc.recuperaObjeto(animais[i]);
		}
		//obj = bc.recuperaObjeto(animais);
	}
	
	public void connect(IResponder responder) 
	{    
		// leio o primeiro animais da lista, e faço todas as perguntas e vou armazenando em um vetor,
		// todas as vezes que pergunta verifica se já não foi inserida no vetor em caso positivo não pergunta
		// caso achar um animais que responder todas as perguntas corretamente retorna ele
		
		boolean acertei = false;
		
		// percorrendo todos os animais
		for(int i = 0; i < obj.length; i++)
		{				
			acertei = false;
		    IDeclaracao decl = obj[i].primeira();
		    
		    // corre todas as perguntas e verifica se todas as respostas são iguais, se forem decl == null
		    while(decl != null && responder.ask(decl.getPropriedade()).equalsIgnoreCase(decl.getValor()))
		    	decl = obj[i].proxima();
		    
		    if(decl == null)
		    	acertei = responder.finalAnswer(animais[i]);
		    
		    if(acertei)
		    {
		    	System.out.println(animais[i]);
		    	break;
		    }	    	
		}
		if(!acertei)
	    	System.out.println("nao sei");
	}

}
