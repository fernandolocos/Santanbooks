package pt.c03ensaios.eric.enquirer;

import java.util.HashMap;


import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;

public class MyResponder implements IResponder {

	private IBaseConhecimento base = new BaseConhecimento();
	private IObjetoConhecimento animal;
    private HashMap<String,String> basePerguntas = new HashMap<String,String>();
    private String nomeAnimal;
    private int numOfQuestions = 0;
    private boolean ok = false;
    
    public MyResponder(String animal){
    	this.nomeAnimal = animal;
    	try{
			this.animal = base.recuperaObjeto(animal);
			for(IDeclaracao pergunta = this.animal.primeira(); pergunta != null; pergunta = this.animal.proxima()){
				this.basePerguntas.put(pergunta.getPropriedade(), pergunta.getValor());
			}
    	}
    	catch(Exception err){
    		err.printStackTrace();
    	}
	}

	@Override
	public String ask(String question) {
		// TODO Auto-generated method stub
		this.numOfQuestions += 1;
        if (this.basePerguntas.get(question)==null)
                return "nao sei";
        return this.basePerguntas.get(question);
	}

	@Override
	public boolean finalAnswer(String answer) {
		// TODO Auto-generated method stub
		boolean v = answer.equalsIgnoreCase(this.nomeAnimal);
		this.ok = v;
		return v;
	}
	
	public int getNumQuestions(){
		return numOfQuestions;
	}
	
	public boolean itsOk(){
		return this.ok;
	}

}
