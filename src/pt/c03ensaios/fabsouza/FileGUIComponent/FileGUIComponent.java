package pt.c03ensaios.fabsouza.FileGUIComponent;

import anima.annotation.Component;
import anima.component.view.base.FrameComponentBase;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pt.c03ensaios.hayasida.Handler.IHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * @author Fabiani
 */

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>", 
			provides = { "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent>" }, 
			requires = { "<http://purl.org/dcc/pt.c03ensaios.hayasida.Handler.IHandler>" })
public class FileGUIComponent extends FrameComponentBase implements
		IFileGUIComponent {
	private static final long serialVersionUID = -3987352141266774722L;
	private JMenuBar menuBar = null;
	private JMenu menuArquivo = null;
	private JMenu menuAjuda = null;
	private JMenuItem menuItemAbrir = null;
	private JMenuItem menuItemNovo = null;
	private JMenuItem menuItemSalvar = null;
	private JMenuItem menuItemSalvarComo = null;
	private JMenuItem menuItemFechar = null;
	private JMenuItem menuItemSair = null;
	private JMenuItem menuItemSobre = null;
	private JPanel pnlFundo = null;
	private JPanel pnlList = null;
	private JScrollPane scrollList = null;
	private JLabel lblAnimal = null;
	private JLabel lblPergunta = null;
	private DefaultListModel model = null;
	private JList lstAnimal = null;
	private JRadioButton rbtnSim = null;
	private JRadioButton rbtnNao = null;
	private JButton btnAdd = null;
	private JButton btnDel = null;
	private JTextField txtAPergunta = null;
	private JFileChooser fileChooser = null;

	private Vector<String> pergunta = null;
	private Vector<String> resposta = null;
	private IHandler provider;
	private String strDir = null;
	private String strFile = null;
	private static final String DIRETORIO_RAIZ = "pt/c01interfaces/s01chaveid/s01base/bd/";
	private static final String RESPOSTA_SIM = "sim";
	private static final String RESPOSTA_NAO = "nao";

	/**
	 * This method initializes
	 * 
	 */
	public FileGUIComponent() {
		super();
		initialize();
		disableComponentes();
		disableMenuInicio();
		enableMenuEdicao();
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Conecta um IHandler ao objeto
	 * 
	 */
	public void connect(IHandler arquivo) {
		this.provider = arquivo;
	}

	public void showFrame() {
		this.setVisible(true);
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		this.setSize(new Dimension(400, 380));
		this.setTitle("FileGUIComponent");
		this.setContentPane(getJPanelFundo());
		this.setJMenuBar(getJJMenuBar());
	}

	/* ------------ INICIO MENU ------------ */

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getJMenuArquivo());
			menuBar.add(getJMenuAjuda());
		}
		return menuBar;
	}

	/**
	 * This method initializes jMenuArquivo
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuArquivo() {
		if (menuArquivo == null) {
			menuArquivo = new JMenu();
			menuArquivo.setText("Arquivo");
			menuArquivo.add(getJMenuItemNovo());
			menuArquivo.add(getJMenuItemAbrir());
			menuArquivo.add(getJMenuItemFechar());
			menuArquivo.addSeparator();
			menuArquivo.add(getJMenuItemSalvar());
			menuArquivo.add(getJMenuItemSalvarComo());
			menuArquivo.addSeparator();
			menuArquivo.add(getJMenuItemSair());
		}
		return menuArquivo;
	}

	/**
	 * This method initializes jMenuSair
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuAjuda() {
		if (menuAjuda == null) {
			menuAjuda = new JMenu();
			menuAjuda.setText("Ajuda");
			menuAjuda.add(getJMenuItemSobre());
		}
		return menuAjuda;
	}

	/**
	 * This method initializes jMenuItemAbrir
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemAbrir() {
		if (menuItemAbrir == null) {
			menuItemAbrir = new JMenuItem("Abrir...");
			menuItemAbrir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jMenuItemAbrirClicked();
				}
			});
		}
		return menuItemAbrir;
	}

	/**
	 * This method initializes jMenuItemFechar
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemFechar() {
		if (menuItemFechar == null) {
			menuItemFechar = new JMenuItem("Cancelar");
			menuItemFechar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jMenuItemFecharClicked();
				}
			});
		}
		return menuItemFechar;
	}

	/**
	 * This method initializes jMenuItemSair
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSair() {
		if (menuItemSair == null) {
			menuItemSair = new JMenuItem("Sair");
			menuItemSair.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jMenuItemSairClicked();
				}
			});
		}
		return menuItemSair;
	}

	/**
	 * This method initializes jMenuItemNovo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemNovo() {
		if (menuItemNovo == null) {
			menuItemNovo = new JMenuItem("Novo");
			menuItemNovo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jMenuItemNovoClicked();
				}
			});
		}
		return menuItemNovo;
	}

	/**
	 * This method initializes jMenuItemSalvar
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSalvar() {
		if (menuItemSalvar == null) {
			menuItemSalvar = new JMenuItem("Salvar");
			menuItemSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jMenuItemSalvarClicked();
				}
			});
		}
		return menuItemSalvar;
	}

	/**
	 * This method initializes jMenuItemSalvarComo
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSalvarComo() {
		if (menuItemSalvarComo == null) {
			menuItemSalvarComo = new JMenuItem("Salvar como...");
			menuItemSalvarComo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jMenuItemSalvarComoClicked();
				}
			});
		}
		return menuItemSalvarComo;
	}

	/**
	 * This method initializes jMenuItemSobre
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSobre() {
		if (menuItemSobre == null) {
			menuItemSobre = new JMenuItem("Sobre...");
			menuItemSobre.addActionListener(new Sobre());
		}
		return menuItemSobre;
	}

	/* ------------ FIM MENU ------------ */

	/* ---------- INICIO PANEL ---------- */

	/**
	 * This method initializes jPanelFundo
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanelFundo() {
		if (pnlFundo == null) {
			lblPergunta = new JLabel();
			lblPergunta.setBounds(new Rectangle(11, 35, 79, 16));
			lblPergunta.setText("Pergunta:");
			lblAnimal = new JLabel();
			lblAnimal.setBounds(new Rectangle(11, 12, 141, 16));
			lblAnimal.setText("lblAnimal");
			lblAnimal.setVisible(false);
			scrollList = new JScrollPane(getLstAnimal());
			pnlList = new JPanel();
			pnlList.setLayout(new BorderLayout());
			pnlList.setSize(370, 155);
			pnlList.setBounds(new Rectangle(11, 130, 370, 180));
			pnlList.add(scrollList, BorderLayout.CENTER);
			pnlFundo = new JPanel();
			pnlFundo.setLayout(null);
			pnlFundo.setSize(new Dimension(480, 400));
			pnlFundo.add(lblAnimal, null);
			pnlFundo.add(pnlList, null);
			pnlFundo.add(lblPergunta, null);
			pnlFundo.add(getRbtnSim(), null);
			pnlFundo.add(getRbtnNao(), null);
			pnlFundo.add(getBtnAdd(), null);
			pnlFundo.add(getTxtAPergunta(), null);
			pnlFundo.add(getBtnDel(), null);
		}
		return pnlFundo;
	}

	/**
	 * This method initializes lstAnimal
	 * 
	 * @return javax.swing.JList
	 */
	private JList getLstAnimal() {
		if (lstAnimal == null) {
			model = new DefaultListModel();
			lstAnimal = new JList(model);
			lstAnimal.setBounds(new Rectangle(11, 127, 370, 180));
			lstAnimal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			lstAnimal.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!lstAnimal.isSelectionEmpty())
						btnDel.setEnabled(true);
				}
			});
		}
		return lstAnimal;
	}

	/**
	 * This method initializes rbtnSim
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbtnSim() {
		if (rbtnSim == null) {
			rbtnSim = new JRadioButton();
			rbtnSim.setBounds(new Rectangle(7, 80, 56, 21));
			rbtnSim.setSelected(true);
			rbtnSim.setText("Sim");
			rbtnSim.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rbtnSimClicked();
				}
			});

		}
		return rbtnSim;
	}

	/**
	 * This method initializes rbtnNao
	 * 
	 * @return javax.swing.JRadioButton
	 */
	private JRadioButton getRbtnNao() {
		if (rbtnNao == null) {
			rbtnNao = new JRadioButton();
			rbtnNao.setBounds(new Rectangle(7, 100, 47, 24));
			rbtnNao.setText("Não");
			rbtnNao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					rbtnNaoClicked();
				}
			});
		}
		return rbtnNao;
	}

	/**
	 * This method initializes btnAdd
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAdd() {
		if (btnAdd == null) {
			btnAdd = new JButton();
			btnAdd.setBounds(new Rectangle(250, 95, 56, 26));
			btnAdd.setText("Add");
			btnAdd.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnAddClicked();
				}
			});
		}
		return btnAdd;
	}

	/**
	 * This method initializes txtAPergunta
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextField getTxtAPergunta() {
		if (txtAPergunta == null) {
			txtAPergunta = new JTextField();
			txtAPergunta.setBounds(new Rectangle(11, 55, 370, 20));
			txtAPergunta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		}
		return txtAPergunta;
	}

	/**
	 * This method initializes btnDel
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnDel() {
		if (btnDel == null) {
			btnDel = new JButton();
			btnDel.setBounds(new Rectangle(324, 95, 56, 26));
			btnDel.setText("Del");
			btnDel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					btnDelClicked();
					btnDel.setEnabled(false);
				}
			});
		}
		return btnDel;
	}

	/* ------------ FIM PANEL ------------ */

	/* --------- INICIO "EVENTOS" --------- */

	/**
	 * Abre um arquivo selecionado pelo usuário e insere as perguntas no JList.
	 * 
	 */
	private void jMenuItemAbrirClicked() {
		fileChooser = new JFileChooser(DIRETORIO_RAIZ);

		if (fileChooser.showOpenDialog(pnlFundo) == JFileChooser.CANCEL_OPTION)
			return;

		iniPergResp();

		strFile = fileChooser.getSelectedFile().getName();
		strDir = fileChooser.getSelectedFile().getPath();

		lblAnimal.setText(strFile);
		lblAnimal.setVisible(true);

		strDir = strDir.substring(0, strDir.indexOf(strFile));
		// tira a extensão ".txt" do nome do arquivo
		strFile = strFile.substring(0, strFile.indexOf(".txt"));

		provider.setDir(strDir);
		pergunta = provider.getPerguntas(strFile);
		resposta = provider.getRespostas(strFile);

		enableComponentes();
		enableMenuInicio(false);
		disableMenuEdicao();
		limparTudo();

		// adiciona perguntas e respostas no JList
		for (int i = 0; i < pergunta.size(); i++)
			model.addElement(pergunta.get(i) + " - " + resposta.get(i));

		setFocusPergunta();
	}

	/**
	 * Habilita os componentes para cadastrar um novo animal.
	 * 
	 */
	private void jMenuItemNovoClicked() {
		lblAnimal.setVisible(false);
		enableComponentes();
		enableMenuInicio(true);
		disableMenuEdicao();
		limparTudo();
		setFocusPergunta();
		iniPergResp();
	}

	/**
	 * Cancela a criação ou edição de um animal.
	 * 
	 */
	private void jMenuItemFecharClicked() {
		lblAnimal.setVisible(false);
		limparTudo();
		disableComponentes();
		disableMenuInicio();
		enableMenuEdicao();
		iniPergResp();
	}

	/**
	 * Sai da aplicação.
	 * 
	 */
	private void jMenuItemSairClicked() {
		System.exit(0);
	}

	/**
	 * Salva o animal que estiver sendo editado.
	 * 
	 */
	private void jMenuItemSalvarClicked() {
		try {
			// provider.setDir(strDir);
			if (!provider.exists(pergunta, resposta)) {
				provider.save(strFile, pergunta, resposta);
				jMenuItemFecharClicked();
				messageSucesso("Animal cadastrado com sucesso.");
			} else
				messageAtencao("Já existe um animal cadastrado com as mesmas perguntas e respostas.");
		} catch (Exception e) {
			messageErro(e.getMessage());
		}
	}

	/**
	 * Abre uma janela para o usuário escolher o nome e diretório do novo animal
	 * que quer salvar
	 * 
	 */
	private void jMenuItemSalvarComoClicked() {
		fileChooser = new JFileChooser(DIRETORIO_RAIZ);

		if (fileChooser.showSaveDialog(pnlFundo) == JFileChooser.CANCEL_OPTION)
			return;

		strFile = fileChooser.getSelectedFile().getName();
		strDir = fileChooser.getSelectedFile().getPath();
		strDir = strDir.substring(0, strDir.indexOf(strFile));

		try {
			provider.setDir(strDir);
			if (!provider.exists(pergunta, resposta)) {
				provider.save(strFile, pergunta, resposta);
				// desabilita os componentes
				jMenuItemFecharClicked();
				messageSucesso("Animal cadastrado com sucesso.");
			} else
				messageAtencao("Já existe um animal cadastrado com as mesmas perguntas e respostas.");
		} catch (Exception e) {
			messageErro(e.getMessage());
		}
	}

	/**
	 * Tira a seleção do radioButton "Não"
	 * 
	 */
	private void rbtnSimClicked() {
		rbtnNao.setSelected(false);
	}

	/**
	 * Tira a seleção do radioButton "Sim"
	 * 
	 */
	private void rbtnNaoClicked() {
		rbtnSim.setSelected(false);
	}

	/**
	 * Adiciona uma pergunta e respectiva resposta no JList.
	 * 
	 */
	private void btnAddClicked() {
		String strResposta = null, strPergunta = null;

		if (!txtAPergunta.getText().trim().equals("")
				&& (rbtnSim.isSelected() || rbtnNao.isSelected())) {
			if (rbtnSim.isSelected())
				strResposta = RESPOSTA_SIM;
			else
				strResposta = RESPOSTA_NAO;

			strPergunta = txtAPergunta.getText();
			// adiciona no JList
			model.addElement(strPergunta + " - " + strResposta);
			// adiciona aos vetores de perguntas e respostas
			pergunta.add(strPergunta);
			resposta.add(strResposta);
			limparPergunta();
		}
	}

	/**
	 * Apaga uma pergunta e respectiva resposta do JList.
	 * 
	 */
	private void btnDelClicked() {
		int intIndice = 0;
		if (!lstAnimal.isSelectionEmpty()) {
			intIndice = lstAnimal.getSelectedIndex();
			// remove do JList
			model.remove(intIndice);
			// remove dos vetores de perguntas e respostas
			pergunta.remove(intIndice);
			resposta.remove(intIndice);
			setFocusPergunta();
		}
	}

	/* --------- FIM "EVENTOS" --------- */

	/* --- INICIO MÉTODOS AUXILIARES --- */

	/**
	 * Desabilita os componentes de cadastro.
	 * 
	 */
	private void disableComponentes() {
		lblPergunta.setEnabled(false);
		txtAPergunta.setEnabled(false);
		lstAnimal.setEnabled(false);
		btnAdd.setEnabled(false);
		btnDel.setEnabled(false);
		rbtnNao.setEnabled(false);
		rbtnSim.setEnabled(false);
		txtAPergunta.setBorder(BorderFactory.createLineBorder(Color.lightGray));
	}

	/**
	 * Habilita os componentes de cadastro.
	 * 
	 */
	private void enableComponentes() {
		lblPergunta.setEnabled(true);
		txtAPergunta.setEnabled(true);
		lstAnimal.setEnabled(true);
		btnAdd.setEnabled(true);
		rbtnNao.setEnabled(true);
		rbtnSim.setEnabled(true);
		btnDel.setEnabled(false);
		txtAPergunta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	}

	/**
	 * Desabilita os itens de menu que não podem ser utilizados inicialmente.
	 * 
	 */
	private void disableMenuInicio() {
		menuItemFechar.setEnabled(false);
		menuItemSalvarComo.setEnabled(false);
		menuItemSalvar.setEnabled(false);
	}

	/**
	 * Habilita os itens de menu que não podem ser utilizados inicialmente.
	 * 
	 * @param novo
	 *            Indica se o usuário escolheu a opção "Novo" do menu. *
	 */
	private void enableMenuInicio(boolean novo) {
		menuItemFechar.setEnabled(true);
		menuItemSalvarComo.setEnabled(true);

		if (novo)
			menuItemSalvar.setEnabled(false);
		else
			menuItemSalvar.setEnabled(true);
	}

	/**
	 * Desabita os itens de menu referentes a edição.
	 * 
	 */
	private void disableMenuEdicao() {
		menuItemAbrir.setEnabled(false);
		menuItemNovo.setEnabled(false);
	}

	/**
	 * Habilita os itens de menu referentes a edição.
	 * 
	 */
	private void enableMenuEdicao() {
		menuItemAbrir.setEnabled(true);
		menuItemNovo.setEnabled(true);
	}

	/**
	 * Coloca o foco no textField de pergunta.
	 * 
	 */
	private void setFocusPergunta() {
		txtAPergunta.requestFocusInWindow();
	}

	/**
	 * Apaga pergunta.
	 * 
	 */
	private void limparPergunta() {
		txtAPergunta.setText("");
		setFocusPergunta();
	}

	/**
	 * Apaga qualquer dado inserido pelo usuário nos componentes.
	 * 
	 */
	private void limparTudo() {
		txtAPergunta.setText("");
		model.removeAllElements();
		rbtnSim.setSelected(true);
		rbtnNao.setSelected(false);
	}

	/**
	 * Inicializa vetores de perguntas e respostas.
	 * 
	 */
	private void iniPergResp() {
		pergunta = new Vector<String>();
		resposta = new Vector<String>();
	}

	/**
	 * Exibe mensagem de sucesso.
	 * 
	 */
	private void messageSucesso(String mensagem) {
		JOptionPane.showMessageDialog(pnlFundo, mensagem, "Sucesso",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Exibe mensagem de aviso.
	 * 
	 */
	private void messageAtencao(String mensagem) {
		JOptionPane.showMessageDialog(pnlFundo, mensagem, "Atenção",
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Exibe mensagem de erro.
	 * 
	 * @param erro
	 *            Sring contendo a mensagem de erro. *
	 */
	private void messageErro(String erro) {
		JOptionPane.showMessageDialog(pnlFundo, erro, "Erro",
				JOptionPane.ERROR_MESSAGE);
	}

	/* ----- FIM MÉTODOS AUXILIARES ----- */

	/* ------ INICIO JANELA SOBRE ------ */
	/**
	 * 
	 * @author Fabiani
	 * 
	 */
	private class Sobre implements ActionListener {
		private JFrame frmJanela;
		private JLabel lblNome;
		private JLabel lblVersao;
		private JLabel lblDireitos;
		private JButton btnOk;

		public void actionPerformed(ActionEvent e) {
			frmJanela = new JFrame();
			frmJanela.setVisible(true);

			lblNome = new JLabel("FileGUIComponent");
			lblVersao = new JLabel("Versão 1.0");
			lblDireitos = new JLabel("Copyright © 2011 Fabiani");

			btnOk = new JButton("Ok");
			btnOk.addActionListener(new Fechar());

			lblNome.setBounds(15, 5, 200, 25);
			frmJanela.getContentPane().add(lblNome);

			lblVersao.setBounds(15, 20, 200, 20);
			frmJanela.getContentPane().add(lblVersao);

			lblDireitos.setBounds(15, 45, 200, 20);
			frmJanela.getContentPane().add(lblDireitos);

			btnOk.setBounds(75, 80, 80, 25);
			frmJanela.getContentPane().add(btnOk);

			frmJanela.setSize(240, 150);
			frmJanela.setLayout(null);
			frmJanela.setTitle("Sobre");
			frmJanela.setResizable(false);
			frmJanela.setVisible(true);
		}

		class Fechar implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				frmJanela.setVisible(false);
				frmJanela = null;
			}
		}
	}
	/* ------- FIM JANELA SOBRE ------- */
}
