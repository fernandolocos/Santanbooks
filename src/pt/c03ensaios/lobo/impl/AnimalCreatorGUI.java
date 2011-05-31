package pt.c03ensaios.lobo.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import pt.c03ensaios.debolacha.inter.IAnimalFile;
import pt.c03ensaios.lobo.inter.IAnimalCreatorGUI;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

/**
 * Graphics User Interface component that provides simplicity at creating new
 * Animal files, which will be added to the Animal Database.
 * This component is a GUI for IAnimalFile.
 * Requires IAnimalFile and IQuestion.
 * 
 * @author <b>Rodrigo Lobo da Costa - RA 104012</b>
 * @author <b>Fabio Beranizo Fontes Lopes - RA 104864</b>
 *
 */
@Component( id=       "<http://purl.org/dcc/pt.c03ensaios.lobo.impl.AnimalCreatorGUI>",
			provides= "<http://purl.org/dcc/anima.component.view.base.IFrameComponent>",
			requires={"<http://purl.org/dcc/pt.c03ensaios.debolacha.inter.IAnimalFile>",
					  "<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>",
					  "<http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>"})
public class AnimalCreatorGUI extends ComponentBase implements IAnimalCreatorGUI
{
	private static final String TAG = "AnimalCreatorGUI";
	private boolean testMode = true;
	
	private IAnimalFile animalFile = null;
	private IQuestions questionsComponent = null;
	
	private JPanel mainPanel = null,
				   panelCenter = null,
				   panelNorth = null,
				   panelSouth = null,
				   panelWest = null,
				   panelEast = null,
				   panelQuestions = null;
	
	private JScrollPane scrollPaneAnimalQuestions = null;
	private JLabel labelAnimalName = null;
	private JTextField textFieldAnimalName = null,
					   textFieldStatus = null;
	
	private JButton buttonAddQuestion = null,
					buttonRemoveQuestion = null,
					buttonFinish = null;
	
	private Vector<JRadioButton[]> radioButtons;
	private Vector<ButtonGroup> buttonGroups;
	private Vector<JComboBox> comboBoxes;
	private Vector<JButton> removeButtons;
	private Vector<String> availableQuestions, allQuestions;

	public AnimalCreatorGUI()
	{
		super();
		
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
		
		availableQuestions = new Vector<String>();
		comboBoxes = new Vector<JComboBox>();
		buttonGroups = new Vector<ButtonGroup>();
		radioButtons = new Vector<JRadioButton[]>();
		removeButtons = new Vector<JButton>();
	}
	
	/**
	 * Sets the test mode true or false.
	 * If true, the animal will never be added to the Data Base, even if its name and properties are
	 * available (this is useful for testing). If false, the animal can be added to the Data Base normally.
	 * Default value: true
	 * @param value true: sets the test mode on; false: sets the test mode off
	 */
	public void setTestMode(boolean value)
	{
		this.testMode = value;
	}
	
	/**
	 * Creates and returns the JPanel for the Main Panel, with all it's components
	 * @return Main Panel
	 */
	private JPanel getMainPanel()
	{
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(getPanelNorth(), BorderLayout.NORTH);
		mainPanel.add(getPanelSouth(), BorderLayout.SOUTH);
		mainPanel.add(getPanelCenter(), BorderLayout.CENTER);
		return mainPanel;
	}
	
	/**
	 * Creates and returns a JPanel that contains the JLabel for the animal's name and the
	 * JTextField for the animal's name
	 * @return North Panel
	 */
	private JPanel getPanelNorth()
	{
		panelNorth = new JPanel();
		panelNorth.setLayout(new BorderLayout());
		panelNorth.add(getTextFieldAnimalName(), BorderLayout.CENTER);
		panelNorth.add(getLabelAnimalName(), BorderLayout.WEST);
		return panelNorth;
	}
	
	/**
	 * Creates and returns a JPanel that contains three JButtons: AddQuestion, RemoveQuestion and Finish
	 * @return South Panel
	 */
	private JPanel getPanelSouth()
	{
		panelSouth = new JPanel();
		panelSouth.setLayout(new GridLayout(1, 3, 25, 0));
		panelSouth.add(getButtonAddQuestion());
		panelSouth.add(getButtonRemoveQuestion());
		panelSouth.add(getButtonFinish());
		return panelSouth;
	}
	
	/**
	 * Creates and returns a JPanel that contains the JScrollPane for the animal's questions and answers
	 * and the JTextField that displays animal creating status
	 * @return Center Panel
	 */
	private JPanel getPanelCenter()
	{
		panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(getScrollPaneAnimalQuestions(), BorderLayout.CENTER);
		panelCenter.add(getTextFieldStatus(), BorderLayout.SOUTH);
		return panelCenter;
	}
	
	/**
	 * Creates and returns a JPanel that contains the JPanel for the comboBoxes and removeQuestion buttons,
	 * and the JPanel for the RadioButtons
	 * @return Questions Panel
	 */
	private JPanel getPanelQuestions()
	{				
		panelQuestions = new JPanel();
		panelQuestions.setLayout(new BoxLayout(panelQuestions, BoxLayout.X_AXIS));
		panelQuestions.add(getPanelWest());
		panelQuestions.add(getPanelEast());
		return panelQuestions;
	}
	
	/**
	 * Creates and returns a JPanel that contains the removeQuestion buttons and the comboBoxes
	 * @return West Panel
	 */
	private JPanel getPanelWest()
	{
		panelWest = new JPanel();
		panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
		return panelWest;
	}
	
	/**
	 * Creates and returns a JPanel that contains the radioButtons for the answers
	 * @return East Panel
	 */
	private JPanel getPanelEast()
	{
		panelEast = new JPanel();
		panelEast.setLayout(new BoxLayout(panelEast, BoxLayout.Y_AXIS));
		return panelEast;
	}
	
	/**
	 * Creates and returns the JButton that adds a new question for the animal
	 * @return AddQuestion Button
	 */
	private JButton getButtonAddQuestion()
	{
		buttonAddQuestion = new JButton("Adicionar pergunta");
		buttonAddQuestion.setFont(new Font("arial", Font.BOLD, 12));
		buttonAddQuestion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				addQuestion(null, "sim");
				textFieldStatus.setForeground(Color.BLACK);
				textFieldStatus.setText("Pergunta adicionada; número total de perguntas: " + comboBoxes.size());
			}
		});
		return buttonAddQuestion;
	}
	
	/**
	 * Creates and returns the JButton that removes the last question in the list
	 * @return removeQuestion Button
	 */
	private JButton getButtonRemoveQuestion()
	{
		buttonRemoveQuestion = new JButton("Remover última");
		buttonRemoveQuestion.setFont(new Font("arial", Font.BOLD, 12));
		buttonRemoveQuestion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				removeQuestion();
			}
		});
		return buttonRemoveQuestion;
	}
	
	/**
	 * Creates and returns the JButton that adds the animal to the Database
	 * @return Finish Button
	 */
	private JButton getButtonFinish()
	{
		buttonFinish = new JButton("Criar animal");
		buttonFinish.setForeground(Color.BLUE);
		buttonFinish.setFont(new Font("arial", Font.BOLD, 12));
		buttonFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				finishAnimal();
			}
		});
		return buttonFinish;
	}
	
	/**
	 * Creates a JButton with the especified index, and returns it.
	 * When clicked, the question with this button's index is removed from the list
	 * @param index Index of this button in the question list
	 * @return removeQuestion Button
	 */
	private JButton createRemoveButton(Integer index)
	{
		JButton b = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/pt/c03ensaios/lobo/img/iconeX.png"))));
		b.setName(index.toString());
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JButton b = (JButton) e.getSource();
				Integer index = Integer.parseInt(b.getName());
				removeQuestion(index);
			}
		});
		return b;
	}
	
	/**
	 * Creates a pair of JRadioButtons ("Sim" and "Nao"), sets the selected button with the given
	 * String answer, and returns it
	 * @param answer the selected button ("Sim" or "Nao")
	 * @return pair of radioButtons, as an array
	 */
	private JRadioButton[] createRadioButtons(String answer)
	{
		JRadioButton[] b = new JRadioButton[2];
		b[0] = new JRadioButton();
		b[0].setText("Sim");
		b[0].setActionCommand("sim");
		b[1] = new JRadioButton();
		b[1].setText("Não");
		b[1].setActionCommand("nao");
		if(answer.equalsIgnoreCase("sim")) b[0].setSelected(true);
		else b[1].setSelected(true);
		return b;
	}
	
	/**
	 * Creates a ButtonGroup with the specified pair of JRadioButtons, and returns it
	 * @param b pair of JRadioButtons ("Sim" or "Nao")
	 * @return buttonGroup with the pair of JRadioButtons
	 */
	private ButtonGroup createButtonGroup(JRadioButton[] b)
	{
		ButtonGroup bg = new ButtonGroup();
		bg.add(b[0]);
		bg.add(b[1]);
		return bg;
	}
	
	/**
	 * Creates a new JComboBox with all the possible questions, and sets the selection
	 * with the given question. If question is null, the first possible question is selected
	 * @param question selection of the new comboBox
	 * @return comboBox
	 */
	private JComboBox createComboBox(String question)
	{
		JComboBox cb = new JComboBox(availableQuestions);
		cb.setEditable(true);
		if(question != null) cb.setSelectedItem(question);
		cb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				updateAvailableQuestions();
			}
		});
		return cb;
	}
	
	/**
	 * Creates and returns the JLabel labeled "Nome do Animal: "
	 * @return label
	 */
	private JLabel getLabelAnimalName()
	{
		labelAnimalName = new JLabel("Nome do Animal: ");
		labelAnimalName.setFont(new Font("Arial", Font.BOLD, 16));
		return labelAnimalName;
	}
	
	/**
	 * Creates and returns the JTextField which is the input of the animal's name
	 * @return animal name textField
	 */
	private JTextField getTextFieldAnimalName()
	{
		textFieldAnimalName = new JTextField(animalFile.getName());
		textFieldAnimalName.setFont(new Font("Arial", Font.BOLD, 14));
		textFieldAnimalName.setEditable(true);
		return textFieldAnimalName;
	}
	
	/**
	 * Creates and returns the JTextField that shows the status in the editor
	 * @return Status textField
	 */
	private JTextField getTextFieldStatus()
	{
		textFieldStatus = new JTextField("Clique em 'Criar animal' para adicionar o novo animal");
		textFieldStatus.setHorizontalAlignment(JTextField.CENTER);
		textFieldStatus.setEditable(false);
		textFieldStatus.setFont(new Font("arial", Font.PLAIN, 14));
		textFieldStatus.setForeground(Color.BLACK);
		return textFieldStatus;
	}
	
	/**
	 * Creates and returns the JScrollPane that contains the questions' JPanel
	 * @return animal questions scrollPane
	 */
	private JScrollPane getScrollPaneAnimalQuestions()
	{
		scrollPaneAnimalQuestions = new JScrollPane();
		scrollPaneAnimalQuestions.setViewportView(getPanelQuestions());
		return scrollPaneAnimalQuestions;
	}
	
	/**
	 * Updates the available questions that may be selected in the comboBoxes
	 */
	private void updateAvailableQuestions()
	{
		availableQuestions.clear();
		for(int i = 0; i < allQuestions.size(); i++)
		{
			availableQuestions.add(allQuestions.get(i));
		}
		for(Integer i = 0; i < comboBoxes.size(); i++)
		{
			removeButtons.get(i).setName(i.toString());
			String question = (String)comboBoxes.get(i).getSelectedItem();
			if(allQuestions.contains(question)) availableQuestions.remove(question);
		}
	}
	
	/**
	 * Synchronizes the GUI's questions with the AnimalFile's questions and answers.
	 * This method must be called in the initialization
	 */
	private void initQuestions()
	{
		Enumeration<String> e = animalFile.getAnimal().keys();
		while(e.hasMoreElements())
		{
			String question = e.nextElement();
			addQuestion(question, animalFile.getAnimal().get(question));
		}
		updateAvailableQuestions();
	}
	
	/**
	 * Adds a new question to the list, and sets the selected question and the selected answer based
	 * in the parameters. If the parameter question is null, the first available question is selected.
	 * The status TextField will show a message saying that a question was added, and the total number
	 * of questions in the list
	 * @param question The question that will be selected in the comboBox
	 * @param answer The answer that will be selected in the RadioButtons
	 */
	private void addQuestion(String question, String answer)
	{		
		Integer index = comboBoxes.size();
		JComboBox newComboBox = createComboBox(question);
		JButton newRemoveButton = createRemoveButton(index);
		JRadioButton[] newButtons = createRadioButtons(answer);
		ButtonGroup newButtonGroup = createButtonGroup(newButtons);
		
		removeButtons.add(newRemoveButton);
		comboBoxes.add(newComboBox);
		radioButtons.add(newButtons);
		buttonGroups.add(newButtonGroup);
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BorderLayout());
		p1.add(newComboBox, BorderLayout.CENTER);
		p1.add(newRemoveButton, BorderLayout.WEST);
		p1.setMaximumSize(new Dimension(1200, 30));
		p1.setMinimumSize(new Dimension(300, 30));
		panelWest.add(p1);
		
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2));
		p2.add(newButtons[0]);
		p2.add(newButtons[1]);
		p2.setMaximumSize(new Dimension(100, 30));
		p2.setMinimumSize(new Dimension(100, 30));
		panelEast.add(p2);
		
		updateAvailableQuestions();
		mainPanel.validate();
	}
	
	/**
	 * Removes the last question from the list
	 * The status TextField will show a message with the last removed question, and the total
	 * number of questions in the list.
	 * If there's only one question in the list, that question won't be removed, and a error
	 * message will be shown in the status TextField
	 */
	private void removeQuestion()
	{
		int n = comboBoxes.size();
		removeQuestion(n - 1);
	}
	
	/**
	 * Removes from the list the question with the specified index.
	 * The status TextField will show a message with the last removed question, and the total
	 * number of questions in the list.
	 * If there's only one question in the list, that question won't be removed, and a error
	 * message will be shown in the status TextField
	 * @param index Index of the question that will be removed
	 */
	private void removeQuestion(int index)
	{
		int n = comboBoxes.size();
		if(n > 1 && index < n)
		{
			textFieldStatus.setForeground(Color.BLACK);
			textFieldStatus.setText("Pergunta '" + comboBoxes.get(index).getSelectedItem() + "' removida; número total de perguntas: " + (comboBoxes.size() - 1));
			
			JButton oldRemoveButton = removeButtons.get(index);
			JComboBox oldComboBox = comboBoxes.get(index);
			JRadioButton[] oldRadioButtons = radioButtons.get(index);
			ButtonGroup oldButtonGroup = buttonGroups.get(index);
			
			panelWest.remove(index);
			panelEast.remove(index);
			
			removeButtons.remove(oldRemoveButton);
			comboBoxes.remove(oldComboBox);
			buttonGroups.remove(oldButtonGroup);
			radioButtons.remove(oldRadioButtons);
			
			updateAvailableQuestions();
			mainPanel.validate();
		}
		else
		{
			//mensagem de erro ao remover a pergunta
			textFieldStatus.setForeground(Color.RED);
			textFieldStatus.setText("Não é possível remover esta pergunta");
		}
			
	}
	
	/**
	 * Finishes the animal: the animal in the GUI will be added to the DataBase.<br><br>
	 * 
	 * If the text field for the animal's name is empty, the animal won't be added, and an error
	 * message will be shown in the GUI's status TextField;<br><br>
	 * 
	 * If there is no question in the list, the animal won't be added, and an error message will
	 * be shown in the GUI's status TextField;<br><br>
	 * 
	 * If the animal's name already exists in the DataBase, the animal won't be added, and an
	 * error message will be shown in the GUI's status TextField;<br><br>
	 * 
	 * If the animal's questions and answers matches with any animal in the DataBase, the animal
	 * won't be added, and an error message will be shown in the GUI's status TextField;<br><br>
	 * 
	 * If the Test Mode is set "true", the animal won't be added, and an message will be shown in
	 * the GUI's status TextField (if Test Mode is set "false", the animal will be added normally).
	 */
	private void finishAnimal()
	{
		if(textFieldAnimalName.getText() == null)
		{
			System.out.println(TAG + " - textFieldAnimalName = null");
		}
		else if(textFieldAnimalName.getText().equals(""))
		{
			textFieldStatus.setForeground(Color.RED);
			textFieldStatus.setText("Erro ao criar o animal: O animal deve ter um nome");
		}
		else if(comboBoxes.size() <= 0)
		{
			textFieldStatus.setForeground(Color.RED);
			textFieldStatus.setText("Erro ao criar o animal: O animal deve ter pelo menos uma pergunta referente a ele");
		}
		else
		{
			animalFile.setName(textFieldAnimalName.getText());
			
			//removendo todas as propriedades
			animalFile.removeAllProperties();
			
			//inserindo as propriedades
			for(int i = 0; i < comboBoxes.size(); i++)
			{
				animalFile.addProperty((String) comboBoxes.get(i).getSelectedItem(),
									   buttonGroups.get(i).getSelection().getActionCommand());
			}		
			if(!animalFile.availableName())
			{
				//erro: nome já existente
				textFieldStatus.setForeground(Color.RED);
				textFieldStatus.setText("Erro ao criar o animal: Já existe um animal com este nome");
			}
			else if(!animalFile.availableProperties())
			{
				//erro: propriedades já existentes
				textFieldStatus.setForeground(Color.RED);
				textFieldStatus.setText("Erro ao criar o animal: Já existe um animal com exatamente estas propriedades");
			}
			else
			{
				if(testMode)
				{
					//modo de teste: o animal não será inserido à base de dados
					textFieldStatus.setForeground(new Color(0, 150, 0));
					textFieldStatus.setText("MODO DE TESTE: este animal pode ser inserido à base");
					System.out.println(TAG + " - MODO DE TESTE: este animal pode ser inserido à base, porém o modo de teste\n" +
							"deve ser desativado antes (deve ser chamado o método 'setTestMode(false)' logo após o método\n" +
							"'initialize()' deste componente.");
				}
				else
				{
					//sucesso ao adicionar
					textFieldStatus.setForeground(new Color(0, 150, 0));
					textFieldStatus.setText("Animal criado com sucesso!");
					animalFile.setDirectory("pt/c03ensaios/lobo");
					animalFile.baseInsert();
				}
			}
		}
	}
	
	/**
	 * Initializes this component and it's JFrame.
	 * This method must be called after connecting with an IAnimalFile and an IQuestion.
	 */
	public void initialize()
	{
		mainPanel = getMainPanel();
		allQuestions = questionsComponent.getQuestions();
		if(allQuestions == null) System.err.println(TAG + " - allQuestions = null");
		initQuestions();
	}

	@Override
	public void connect(IAnimalFile provider)
	{
		this.animalFile = provider;
		if(animalFile == null) System.err.println(TAG + " - erro no connect com o IAnimalFile");
	}

	@Override
	public void connect(IQuestions provider)
	{
		this.questionsComponent = provider;
		if(questionsComponent == null) System.err.println(TAG + " - erro no connect com o IQuestions");
	}
	
	@Override
	public void connect(INaxxramas provider)
	{
		provider.add(TAG, mainPanel);
	}
}
