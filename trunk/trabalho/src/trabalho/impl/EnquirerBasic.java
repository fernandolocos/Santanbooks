package trabalho.impl;

import java.util.Vector;

import trabalho.impl.BaseConhecimento;
import trabalho.inter.IBaseConhecimento;
import trabalho.inter.IDeclaracao;
import trabalho.inter.IEnquirer;
import trabalho.inter.IObjetoConhecimento;
import trabalho.inter.IResponder;

public class EnquirerBasic implements IEnquirer 
{
	private String[] animais;
	private IObjetoConhecimento[] obj;
	private Vector<String> propriedade;
	private Vector<String> valor;
	
	public EnquirerBasic()
	{
		IBaseConhecimento bc = new BaseConhecimento();
		animais = bc.listaNomes();
		obj = new IObjetoConhecimento[animais.length];
		
		// criando um vetor de objetos com todos os animais
		for(int i = 0; i < animais.length; i++)
		{
			obj[i] =  bc.recuperaObjeto(animais[i]);
		}
		propriedade = new Vector<String>();
		valor = new Vector<String>();
	}
	
	public void connect(IResponder responder) 
	{    
		IDeclaracao decl = null;
		String s;
		boolean achou = false; //flag para quando achar o animal correto
		boolean acertei = false; //flag para setar quando animal for o procurado
		boolean correto;		//flag para perguntas corretas até o momento
		int pos;
		
		// percorrendo todos os animais (ou até achar)
		for(int i = 0; i < obj.length && !achou; i++)
		{		
		    decl = obj[i].primeira();
		    correto = true;
		    while(decl != null && correto){ //percorre as declarações do animal
		    	if(propriedade.contains(decl.getPropriedade())){ //se a pergunta ja foi feita anteriormente
		    		pos = propriedade.indexOf(decl.getPropriedade()); //acha a posição no vetor de perguntas
		    		if(!valor.get(pos).equalsIgnoreCase(decl.getValor())) //testa no vetor de valores se a resposta está correta 
		    			correto = false; //indica que nao esta mais correto
		    	}
		    	else{
		    		s = responder.ask(decl.getPropriedade());
		    		propriedade.add(decl.getPropriedade()); //adiciona a pergunta no vetor de propriedades
		    		valor.add(s); //adiciona a resposta no vetor de valores
		    		if(!s.equalsIgnoreCase(decl.getValor()))
		    			correto = false;
		    	}
		    	if(correto) decl = obj[i].proxima();
		    }
		    
		    if(decl == null)
		    {
		    	achou = true;
		    	acertei = responder.finalAnswer(animais[i]);
		    	System.out.println(animais[i]);
		    	if (acertei)break;
		    }
		}
		
		if(!achou)
	    	System.out.println("nao sei");
	}

}
