package pt.c03ensaios.lobo.inter;

import javax.swing.JPanel;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;
import pt.c01interfaces.s01chaveid.s01base.inter.*;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.relu.ISearcher;

/**
 * Interface para um componente com interface gráfica o qual permite 
 * jogar de forma semelhante ao Jogo Animals: http://www.animalgame.com
 * Requer INaxxramas, IAnimalPix e ISearcher
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.lobo.inter.IResponderGraphics>")
public interface IResponderGraphics extends IResponder, ISupports, IReceptacleNaxxramas, IReceptacleAnimalPix, IReceptacleSearcher {
    
    /**
     * Recebe uma pergunta e retorna uma String com a resposta 
     * 
     * @param String question  Pergunta feita a um Responder
     * @return String  Resposta à pergunta feita
     */
    @Override
    public String ask(String question);
    
    /**
     * Recebe uma resposta final (nome de um animal), analisa e retorna se está correta 
     * 
     * @param String answer  Resposta final enviada ao responder para análise
     * @return boolean  Avaliação de resposta correta ou errada
     */
    @Override
    public boolean finalAnswer(String answer);
    
    /**
     * Exibe uma Janela contendo o painel que simula o jogo Animals 
     */
    public void showGameFrame();
    
    /**
     * Retorna um Painel que simula o jogo Animals
     * 
     * @return JPanel  Painel que simula o jogo Animals
     */
    public JPanel getGamePanel();
    
    /**
     * Conecta-se com o componente IAnimalPix 
     * 
     * @param IAnimalPix provider
     */
    public void connect(IAnimalPix provider);
    
    /**
     * Conecta-se com o componente ISearcher 
     * 
     * @param ISearcher provider
     */
    public void connect(ISearcher provider);
}
