package pt.c03ensaios.lobo.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.BoxLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.lobo.inter.IResponderGraphics;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.relu.ISearcher;
import pt.c03ensaios.relu.Searcher;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Componente com interface gráfica  o qual permite 
 * jogar de forma semelhante ao Jogo Animals: http://www.animalgame.com
 * Requer INaxxramas, IAnimalPix e ISearcher
 * 
 * @author Rodrigo Lobo da Costa - RA 104012
 * @author Fabio Beranizo Fontes Lopes - RA 104864
 *
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.lobo.impl.ResponderGraphics>",
           provides="<http://purl.org/dcc/pt.c03ensaios.lobo.inter.IResponderGraphics>",
           requires={"<http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>",
                     "<http://purl.org/dcc/pt.c03ensaios.afinder.IAnimalPix>",
                     "<http://purl.org/dcc/pt.c03ensaios.relu.ISearcher>"})
public class ResponderGraphics extends ComponentBase implements IResponderGraphics {
    
    private static final String SIM = "sim";
    private static final String NAO = "nao";
    private static final String NAO_SEI = "nao sei";
    
    private String animal;
    
    private String respostaAtual;
    
    private Vector<String> perguntasRespondidas;
    
    private INaxxramas frame;
    private IAnimalPix animalpix;
    private ISearcher searcher;
    
    
    private JPanel painel;
    private JPanel painelLabel;
    private JPanel painelBotoes;
    private JPanel painelMenu;
    
    private JLabel dica;
    private JTextPane pergunta;
    private JLabel imagemAnimal;
    private JLabel pesquisaAnimal;
    
    private JButton sim;
    private JButton nao;
    private JButton nao_sei;
    
    
    /**
     * Construtor da classe ResponderGraphics
     * Inicializa as variáveis de operação e a interface gráfica
     */
    public ResponderGraphics() {
        this.respostaAtual = null;
        this.perguntasRespondidas = new Vector<String>();
        
        this.visual();
    }
    
    /**
     * Inicializa a interface gráfica
     */
    private void visual() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        this.painel = new JPanel();
        this.painelLabel = new JPanel();
        this.painelBotoes = new JPanel();
        this.painelMenu = new JPanel();
        
        this.dica = new JLabel();
        this.dica.setFont(new Font("arial", Font.BOLD, 16));
        this.dica.setHorizontalTextPosition(JLabel.CENTER);
        this.dica.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        this.pergunta = new JTextPane();
        this.pergunta.setBackground(null);
        this.pergunta.setEditable(false);
        this.pergunta.setBorder(null);
        this.pergunta.setFont(new Font("arial", Font.BOLD, 32));
        StyledDocument doc = this.pergunta.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        
        this.pergunta.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        
        this.imagemAnimal = new JLabel("Visualizar Animal");
        this.imagemAnimal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.imagemAnimal.setFont(new Font("arial", Font.BOLD, 18));
        this.imagemAnimal.setForeground(Color.BLUE);
        this.imagemAnimal.setVisible(false);
        this.imagemAnimal.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) {
                    animalpix.showImageWindow(animal);
                }
            }
            public void mouseEntered(MouseEvent event) {
                return;
            }
            public void mouseExited(MouseEvent event) {
                return;
            }
            public void mouseReleased(MouseEvent event) {
                return;
            }
            public void mousePressed(MouseEvent event) {
                return;
            }
        });
        
        this.pesquisaAnimal = new JLabel("Pesquisar Animal");
        this.pesquisaAnimal.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.pesquisaAnimal.setFont(new Font("arial", Font.BOLD, 18));
        this.pesquisaAnimal.setForeground(Color.BLUE);
        this.pesquisaAnimal.setVisible(false);
        this.pesquisaAnimal.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) {
                    searcher.openBrowser(Searcher.URL_WIKI, animal);
                }
            }
            public void mouseEntered(MouseEvent event) {
                return;
            }
            public void mouseExited(MouseEvent event) {
                return;
            }
            public void mouseReleased(MouseEvent event) {
                return;
            }
            public void mousePressed(MouseEvent event) {
                return;
            }
        });
        
        
        this.sim = new JButton("Sim");
        this.sim.setPreferredSize(new Dimension(120, 60));
        this.sim.setFont(new Font("calibri", Font.BOLD, 18));
        this.sim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                respostaAtual = ResponderGraphics.SIM;
            }
        });
        
        this.nao = new JButton("Não");
        this.nao.setPreferredSize(new Dimension(120, 60));
        this.nao.setFont(new Font("calibri", Font.BOLD, 18));
        this.nao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                respostaAtual = ResponderGraphics.NAO;
            }
        });
        
        this.nao_sei = new JButton("Não sei");
        this.nao_sei.setPreferredSize(new Dimension(120, 60));
        this.nao_sei.setFont(new Font("calibri", Font.BOLD, 18));
        this.nao_sei.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                respostaAtual = ResponderGraphics.NAO_SEI;
            }
        });

        this.painelLabel.setLayout(new BoxLayout(this.painelLabel, BoxLayout.PAGE_AXIS));
        this.painelLabel.add(Box.createRigidArea(new Dimension(0, 10)));
        this.painelLabel.add(this.dica);
        this.painelLabel.add(Box.createRigidArea(new Dimension(0, 5)));
        this.painelLabel.add(this.pergunta);
        this.painelLabel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        this.painelBotoes.setLayout(new BoxLayout(this.painelBotoes, BoxLayout.X_AXIS));
        this.painelBotoes.add(Box.createHorizontalGlue());
        this.painelBotoes.add(this.sim);
        this.painelBotoes.add(Box.createHorizontalGlue());
        this.painelBotoes.add(this.nao);
        this.painelBotoes.add(Box.createHorizontalGlue());
        this.painelBotoes.add(this.nao_sei);
        this.painelBotoes.add(Box.createHorizontalGlue());
        
        this.painelMenu.setLayout(new BorderLayout());
        this.painelMenu.add(this.imagemAnimal, BorderLayout.WEST);
        this.painelMenu.add(this.pesquisaAnimal, BorderLayout.EAST);
        
        this.painel.setLayout(new BorderLayout(10, 10));
        
        this.painel.add(this.painelLabel, BorderLayout.NORTH);
        this.painel.add(this.painelBotoes, BorderLayout.CENTER);
        this.painel.add(this.painelMenu, BorderLayout.SOUTH);
        
        this.painel.setPreferredSize(new Dimension(500, 280));
    }
    
    /**
     * Recebe uma pergunta e retorna uma String com a resposta 
     * 
     * @param String question  Pergunta feita a um Responder
     * @return String  Resposta à pergunta feita
     */
    @Override
    public String ask(String question) {
        this.respostaAtual = null;
        this.perguntasRespondidas.add(question);
        
        this.dica.setText("Pergunta #"+this.perguntasRespondidas.size()+":");
        this.pergunta.setText(this.formataPergunta(question));
       
        //Espera uma resposta do usuário
        while (this.respostaAtual == null) {
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        return this.respostaAtual;
    }

    
    /**
     * Recebe uma resposta final (nome de um animal), analisa e retorna se está correta 
     * 
     * @param String answer  Resposta final enviada ao responder para análise
     * @return boolean  Avaliação de resposta correta ou errada
     */
    @Override
    public boolean finalAnswer(String answer) {
        this.respostaAtual = null;
        boolean finalAnswer;
        
        //Se a resposta foi nula o Enquirer não soube encontrar o animal
        if (answer == null) {
            this.pergunta.setText("fuem! fuem! fuem! Nao sei qual é o animal...");
            finalAnswer = false;
        }
        else {
            this.pergunta.setText(this.formataPergunta("A criatura na qual você pensava era um(a) "+answer));
            this.nao.setVisible(false);
            this.nao_sei.setText("Não");
            
            while (this.respostaAtual == null) {
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            finalAnswer = (this.respostaAtual == ResponderGraphics.SIM);
            
            if (finalAnswer) {
                this.animal = answer;
                this.pergunta.setText("Oba! Acertei! O animal era o(a) "+answer+"! " +
                					  "Agora que tal descobrir um pouco mais sobre o(a) "+answer+
                					  " clicando em uma das opções abaixo?");
                this.painelBotoes.setVisible(false);
                this.imagemAnimal.setVisible(true);
                this.pesquisaAnimal.setVisible(true);
            }
            else {
            	this.pergunta.setText("fuem! fuem! fuem! Nao sei qual é o animal...");
            }
        }
        this.painelBotoes.setVisible(false);
        this.dica.setText("Fim de Jogo");
        return finalAnswer;
    }

    
    /**
     * Formata adequadamente para visualização em interface uma questão
     * 
     * @param String pergunta  Pergunta a ser formatada
     * @return String  Pergunta após formatação 
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
     * Retorna um Painel que simula o jogo Animals
     * 
     * @return JPanel  Painel que simula o jogo Animals
     */
    @Override
    public JPanel getGamePanel() {
        return this.painel;
    }
    /**
     * Exibe uma Janela contendo o painel que simula o jogo Animals 
     */
    public void showGameFrame() {
        this.frame.setVisible(true);
    }
    
    /**
     * Conecta-se com o componente ISearcher 
     * 
     * @param INaxxramas provider
     */
    public void connect(INaxxramas provider) {
        this.frame = provider;
        
        if (this.frame == null) {
            System.err.println("Erro no connect com o INaxxramas");
            JOptionPane.showMessageDialog(null, "Erro no connect com o INaxxramas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.frame.add("Animals Game", this.getGamePanel());
    }

    /**
     * Conecta-se com o componente IAnimalPix 
     * 
     * @param IAnimalPix provider
     */
    @Override
    public void connect(IAnimalPix provider) {
        this.animalpix = provider;
        
        if (this.animalpix == null) {
            System.err.println("Erro no connect com o IAnimalPix");
            JOptionPane.showMessageDialog(null, "Erro no connect com o IAnimalPix.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Conecta-se com o componente ISearcher 
     * 
     * @param ISearcher provider
     */
    @Override
    public void connect(ISearcher provider) {
        this.searcher = provider;
        
        if (this.searcher == null) {
            System.err.println("Erro no connect com o ISearcher");
            JOptionPane.showMessageDialog(null, "Erro no connect com o ISearcher.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
