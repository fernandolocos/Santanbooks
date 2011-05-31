package pt.c03ensaios.lobo.impl;

import javax.swing.JOptionPane;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c03ensaios.lobo.inter.IAnimalRandomizer;
import pt.c03ensaios.lobo.inter.IOldResponder;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.lobo.impl.OldResponder>",
           provides="<http://purl.org/dcc/pt.c03ensaios.lobo.inter.IOldResponder>")
public class OldResponder extends ComponentBase implements IOldResponder {

    private static final String SIM = "sim";
    private static final String NAO = "nao";
    private static final String NAO_SEI = "nao sei";
    
    private static String HUMANO = "humano";
    private static String COMPUTADOR = "computador";
    
    
    private IBaseConhecimento bc;
    private IObjetoConhecimento objetoConhecimento;
    private IAnimalRandomizer sorteadorAnimal;
    private String jogador;
    public String animal;

    public OldResponder() {
    	this.bc = new BaseConhecimento();
    	//this.sorteadorAnimal = new AnimalRandomizer("animais-teste");
    	
    	Object[] respostas = {"JOGAR",
                			  "COMPUTADOR"};
    	
    	if (JOptionPane.showOptionDialog(null,
                this.formataPergunta("Deseja jogar ou deixa o computador adivinhar?"), 
                "Animals Game", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                respostas,
                respostas[0]) == JOptionPane.YES_OPTION) {
    		this.jogador = OldResponder.HUMANO;
    	}
    	else {
    		this.jogador = OldResponder.COMPUTADOR;

            String animal = this.sorteadorAnimal.novoAnimal();
            this.animal = animal;
            this.objetoConhecimento = this.bc.recuperaObjeto(animal);
    	}
    }
    
    
    /**
     * @param question
     */
    @Override
    public String ask(String question) {
    	
    	if (this.jogador.equals(OldResponder.HUMANO)) {
    		return this.askHuman(question);
    	}
    	else if (this.jogador.equals(OldResponder.COMPUTADOR)) {
    		return this.askComputer(question);
    	}    	
    	return null;
    }
    
    /**
     * @param question
     */
    private String askHuman(String question) {
        String resposta = OldResponder.NAO_SEI;
        Object[] respostas = {"Sim",
                              "Não",
                              "Não Sei"};
        
        switch (JOptionPane.showOptionDialog(null,
                                            this.formataPergunta(question), 
                                            "Animals Game", 
                                            JOptionPane.YES_NO_CANCEL_OPTION,
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            respostas,
                                            respostas[0])) {
            case JOptionPane.YES_OPTION:
                resposta = SIM;
                break;
            case JOptionPane.NO_OPTION:
                resposta = OldResponder.NAO;
                break;
            case JOptionPane.CANCEL_OPTION:
                resposta = OldResponder.NAO_SEI;
                break;
        }
        
        return resposta;
    }

    /**
     * @param question
     */
    private String askComputer(String question) {
    	IDeclaracao decl = this.objetoConhecimento.primeira();

        while (decl != null) {

            if (decl.getPropriedade().equals(question)) {
                return decl.getValor();
            }
            decl = this.objetoConhecimento.proxima();
        }

        return NAO_SEI;
    }
    
    /**
     * @param answer
     */
    @Override
    public boolean finalAnswer(String answer) {
    	
    	if (this.jogador.equals(OldResponder.HUMANO)) {
    		return this.finalAnswerHuman(answer);
    	}
    	else if (this.jogador.equals(OldResponder.COMPUTADOR)) {
    		return this.finalAnswerComputer(answer);
    	}
    	return false;
    }
    
    /**
     * @param answer
     */
    private boolean finalAnswerHuman(String answer) {
    	boolean resposta = false;
        Object[] respostas = {"Sim",
                              "Não"};
        
        if (JOptionPane.showOptionDialog(null,
                                        answer+" era o animal no qual você pensava?", 
                                        "Animals Game", 
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        respostas,
                                        respostas[0])  == JOptionPane.YES_OPTION) {
            resposta = true;
        }        
        return resposta;
    }
    
    /**
     * @param answer
     */
    private boolean finalAnswerComputer(String answer) {
    	return this.animal.equals(answer);	
    }
    
    /**
     * @param pergunta
     */
    private String formataPergunta(String pergunta) {
        String perguntaFormatada = pergunta;
        
        if (!perguntaFormatada.endsWith("?")) {
            perguntaFormatada = perguntaFormatada.concat("?");
        }
        
        perguntaFormatada = perguntaFormatada.substring(0, 1).toUpperCase().concat(perguntaFormatada.substring(1));
        
        return perguntaFormatada;
    }

    /**
     * 
     */
    public void newAnimal() {
        String novoAnimal = this.sorteadorAnimal.novoAnimal();
        this.animal = novoAnimal;
        this.objetoConhecimento = this.bc.recuperaObjeto(novoAnimal);
    }
}
