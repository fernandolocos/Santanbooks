package pt.c01interfaces.s01chaveid.frango;

import java.util.Vector;

public class BancoRespostas {
	private Vector<String> perguntas, respostas;
	
	BancoRespostas(){
		perguntas = new Vector<String>();
		respostas = new Vector<String>();
	}
	
	public void setResposta(String pergunta, String resposta){
		perguntas.add(pergunta);
		respostas.add(resposta);
	}
	
	public String getResposta(String pergunta){
		String resposta = null;
		
		for (int i = 0; i < perguntas.size(); i++) {
			if (perguntas.get(i).equalsIgnoreCase(pergunta)){
				resposta = respostas.get(i);
			}	
		}
		
		return resposta;
	}
	
}
