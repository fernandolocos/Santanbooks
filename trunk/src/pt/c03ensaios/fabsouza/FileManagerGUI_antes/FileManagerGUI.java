package pt.c03ensaios.fabsouza.FileManagerGUI_antes;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;

import javax.swing.JComboBox;

import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.proj01.ITextFilesManager;

@Component(id = "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileManagerGUI.FileManagerGUI>",
 provides = {"<http://purl.org/dcc/pt.c03ensaios.fabsouza.TxtFilesManager.IFileManagerNaxxramas>"},
 requires = {"<http://purl.org/dcc/pt.c03ensaios.proj01.ITextFilesManager>",
			 "http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>"})

public class FileManagerGUI extends ComponentBase implements IFileManager, IRequires<INaxxramas> 
{
	private JPanel pnlFundo = null;
	private JTextArea txtAConteudo = null;
	private JScrollPane ScrollPanev = null;
	private JButton btnAbrir = null;
	private JButton btnApagar = null;
	private JButton btnSalvar = null;
	private JComboBox cboLista = null;
	private JFileChooser fileChooser = null;
	private ITextFilesManager provider = null;

	/**
	 * This method initializes
	 * 
	 */
	public FileManagerGUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 */
	private void initialize() {
		pnlFundo = getPnlFundo();
	}

	public void connect(ITextFilesManager provider) {
		this.provider = provider;
		addCboLista();
	}
	
	public void connect(INaxxramas arg0) {		
		arg0.add("FilesTxtManager", pnlFundo);
	}

	private void addCboLista() {
		String[] strLista = provider.listaDeArquivos();
		DefaultComboBoxModel model = new DefaultComboBoxModel(strLista);
		cboLista.setModel(model);
		cboLista.setSelectedIndex(0);
	}

	/**
	 * This method initializes pnlFundo
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPnlFundo() {
		if (pnlFundo == null) {
			pnlFundo = new JPanel();
			pnlFundo.setLayout(null);
			pnlFundo.add(getScrollPanev(), null);
			pnlFundo.add(getBtnAbrir(), null);
			pnlFundo.add(getBtnApagar(), null);
			pnlFundo.add(getBtnSalvar(), null);
			pnlFundo.add(getCboLista(), null);
		}
		return pnlFundo;
	}

	/**
	 * This method initializes txtAConteudo
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTxtAConteudo() {
		if (txtAConteudo == null) {
			txtAConteudo = new JTextArea();
		}
		return txtAConteudo;
	}

	/**
	 * This method initializes ScrollPanev
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getScrollPanev() {
		if (ScrollPanev == null) {
			ScrollPanev = new JScrollPane();
			ScrollPanev.setBounds(new Rectangle(15, 30, 380, 231));
			ScrollPanev.setViewportView(getTxtAConteudo());
		}
		return ScrollPanev;
	}

	/**
	 * This method initializes btnEditar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnAbrir() {
		if (btnAbrir == null) {
			btnAbrir = new JButton();
			btnAbrir.setText("Abrir");
			btnAbrir.setBounds(new Rectangle(15, 320, 80, 26));
			btnAbrir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						btnAbrirClicked();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnAbrir;
	}

	/**
	 * This method initializes btnApagar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnApagar() {
		if (btnApagar == null) {
			btnApagar = new JButton();
			btnApagar.setBounds(new Rectangle(115, 320, 80, 26));
			btnApagar.setText("Apagar");
			btnApagar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						btnApagarClicked();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnApagar;
	}

	/**
	 * This method initializes btnSalvar
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBtnSalvar() {
		if (btnSalvar == null) {
			btnSalvar = new JButton();
			btnSalvar.setText("Salvar");
			btnSalvar.setBounds(new Rectangle(315, 320, 80, 26));
			btnSalvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {						
							btnSalvarClicked();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return btnSalvar;
	}

	/**
	 * This method initializes cboLista
	 * 
	 * @return javax.swing.JComboBox
	 */
	private JComboBox getCboLista() {
		if (cboLista == null) {
			cboLista = new JComboBox();
			cboLista.setBounds(new Rectangle(15, 276, 380, 25));
			cboLista.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						cboListaSelected();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
		}
		return cboLista;
	}

	private void cboListaSelected() throws Exception {
		try {
			provider.setArquivo((String) cboLista.getSelectedItem());
		} catch (Exception e) {
			messageErro(e.getMessage());
		}
	}

	private void btnApagarClicked() {

		if (provider.apaga()) {
			messageSucesso("Arquivo apagado com sucesso.");
			addCboLista();
		} else
			messageErro("O arquivo não pode ser apagado.");
	}

	private void btnSalvarClicked(){		
		fileChooser = new JFileChooser(provider.getPasta());

		if (fileChooser.showSaveDialog(pnlFundo) == JFileChooser.CANCEL_OPTION)
			return;

		String strFile = fileChooser.getSelectedFile().getName();
		String strDir = fileChooser.getSelectedFile().getPath();
		strDir = strDir.substring(0, strDir.indexOf(strFile));

		try {
			provider.setPasta(new File(strDir));
			provider.criaArquivo(strFile);
			provider.insereConteudo(txtAConteudo.getText());
			messageSucesso("Arquivo criado com sucesso.");

			addCboLista();
			limpaTxtA();
		} catch (Exception e) {
			messageErro(e.getMessage());
		}
	}

	private void btnAbrirClicked() {
		String conteudo = "";
		FileReader in = null;
		int i;

		try {
			in = new FileReader(provider.getPasta() + "/"
					+ (provider.getArquivo().getName()));
			i = in.read();

			while (i != -1) {
				conteudo = conteudo + (char) i;
				i = in.read();
			}

			txtAConteudo.setText(conteudo);
		} catch (Exception e) {
			messageErro(e.getMessage());
		}
	}

	private void limpaTxtA() {
		txtAConteudo.setText("");
	}

	private void messageSucesso(String mensagem) {
		JOptionPane.showMessageDialog(pnlFundo, mensagem, "Sucesso",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void messageErro(String mensagem) {
		JOptionPane.showMessageDialog(pnlFundo, mensagem, "Erro",
				JOptionPane.ERROR_MESSAGE);
	}
}
