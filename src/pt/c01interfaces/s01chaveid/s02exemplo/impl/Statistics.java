package pt.c01interfaces.s01chaveid.s02exemplo.impl;

import java.util.ArrayList;
import java.util.Iterator;

import pt.c01interfaces.s01chaveid.s01base.inter.IStatistics;

public class Statistics implements IStatistics {

	ArrayList <Pergunta> perguntas;
		
	public Statistics() {
		perguntas = new ArrayList<Pergunta>();
	}

	public void addInfo(int numeroPergunta, String pergunta, String resposta) {
		Pergunta p = null;
		boolean naoExiste = true;
				
		Iterator<Pergunta>it = perguntas.iterator();
		while(it.hasNext()) {
			p = it.next();
            if (p.getPergunta() == pergunta) {
            	naoExiste = false;
            	p.incQuantidadeVezes();
            	break;
            }
        }
		
		if (naoExiste){
			p = new Pergunta(pergunta, resposta);
		}
		
		perguntas.add(numeroPergunta, p);
	}
	
	public String toString() {
		StringBuffer resposta = new StringBuffer();
		
		Iterator<Pergunta>it = perguntas.iterator();
		while(it.hasNext()) {
            resposta.append(it.next().toString());
            resposta.append("\n");
        }
		// resposta.deleteCharAt(resposta.length()-1);
		
		resposta.append("Total de perguntas: ");
		resposta.append(perguntas.size());
		
		return resposta.toString();
	}

	public boolean repetiu() {
		boolean repetiu = false;
		
		Iterator<Pergunta>it = perguntas.iterator();
		while(it.hasNext()) {
			if (it.next().repetiu()){
				repetiu = true;
				break;
			}				
        }
		
		return repetiu;
	}

	public int totalPerguntas() {
		
		return perguntas.size();
	}	
}
