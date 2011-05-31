package pt.c01interfaces.s01chaveid.fejao.impl;

import java.util.Vector;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IEnquirer;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

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
		String s;
		boolean achou = false, correto = false; //flag para quando achar o animal correto, flag para perguntas corretas até o momento
		int pos;
		
		// percorrendo todos os animais (ou até achar)
		for(int i = 0; i < obj.length && !achou; i++)
		{		
		    IDeclaracao decl = obj[i].primeira();
		    correto = true;
		    while(decl != null && correto){ //percorre as declarações do animal
		    	if(propriedade.contains(decl.getPropriedade())){ //se a pergunta ja foi feita anteriormente
		    		pos = propriedade.indexOf(decl.getPropriedade()); //acha a posição no vetor de perguntas
		    		if(!valor.get(pos).equalsIgnoreCase(decl.getValor())) //testa no vetor de valores se é igual à propriedade
		    			correto = false; //indica que nao esta mais correto
		    	}
		    	else{
		    		s = responder.ask(decl.getPropriedade());
		    		propriedade.add(decl.getPropriedade()); //adiciona a pergunta no vetor de propriedades
		    		valor.add(s); //adiciona a resposta no vetor de propriedades
		    		if(!s.equalsIgnoreCase(decl.getValor()))
		    			correto = false;
		    	}
		    	if(correto) decl = obj[i].proxima();
		    }
		    
		    if(decl == null){
		    	achou = true;
		    	correto = responder.finalAnswer(animais[i]);
		    }
		}
	}

}
