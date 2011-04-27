package pt.c03ensaios.frango.appTest;

import java.util.ArrayList;

public class BancoRespostas {
	private ArrayList<String> perguntas, respostas;
	
	BancoRespostas(){
		perguntas = new ArrayList<String>();
		respostas = new ArrayList<String>();
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
