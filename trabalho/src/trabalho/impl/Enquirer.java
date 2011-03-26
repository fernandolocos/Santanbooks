package trabalho.impl;

import java.util.Vector;
import trabalho.impl.BaseConhecimento;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IEnquirer;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class Enquirer implements IEnquirer 
{
	private String[] animais;
	private IObjetoConhecimento[] obj;
	private Vector<String> v;
	//private Vector<String> valor;
	
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
		v = new Vector<String>();
	}
	
	public void connect(IResponder responder) 
	{    
		// leio o primeiro animais da lista, e faço todas as perguntas e vou armazenando em um vetor,
		// todas as vezes que pergunta verifica se já não foi inserida no vetor em caso positivo não pergunta
		// caso achar um animais que responder todas as perguntas corretamente retorna ele
		
		boolean achou = false;
		
		// percorrendo todos os animais
		for(int i = 0; i < obj.length; i++)
		{		
		    IDeclaracao decl = obj[i].primeira();
		    while(decl != null && v.contains(decl.getPropriedade()))
		    	decl = obj[i].proxima();
		    if(decl != null)
		    {	
		    	v.add(decl.getPropriedade());
		    	//System.out.println(decl.getPropriedade());
			    // corre todas as perguntas e verifica se todas as respostas são iguais, se forem decl == null
			    // se não existir a pergunta neste animal responder retorna não sei dando erro no equals
			    while(decl != null && responder.ask(decl.getPropriedade()).equalsIgnoreCase(decl.getValor()))
			    {
			    	//System.out.println(decl.getPropriedade());
			    	decl = obj[i].proxima();
			    	// enquanto a pergunta existir dentro do vetor, passa para a proxima pergunta
			    	while(decl != null && v.contains(decl.getPropriedade()))
			    		decl = obj[i].proxima();
			    	if(decl != null)v.add(decl.getPropriedade());
			    }
			    
			    if(decl == null)
			    {
			    	achou = true;
			    	System.out.println(animais[i]);
			    	responder.finalAnswer(animais[i]);
			    	break;
			    }
		    }
		}
		if(!achou)
	    	System.out.println("nao sei");
		//System.out.println(v.toString());
	}

}
