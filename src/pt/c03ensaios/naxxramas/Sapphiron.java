package pt.c03ensaios.naxxramas;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.KeyStroke;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTabbedPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.ComponentOrientation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Vector;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import anima.annotation.Component;
import anima.component.IRequires;
import anima.component.base.ComponentBase;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01chaveid.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01chaveid.s01base.inter.IResponder;
import pt.c01interfaces.s01chaveid.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Pergunta;
import pt.c01interfaces.s01chaveid.s02exemplo.impl.Responder;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.relu.ISearcher;

/**
 * Executes an analysis of a group of Enquirers and shows the results in
 * a user-friendly GUI.
 *
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.naxxramas.Sapphiron>",
    provides="<http://purl.org/dcc/pt.c03ensaios.naxxramas.ISapphiron>")
public class Sapphiron extends ComponentBase implements ISapphiron,
    IRequires<INaxxramas>, ISearcherReceptacle, IAnimalPixReceptacle,
    IEnquirerComponentReceptacle {
    private static final long serialVersionUID = 1286755027390528180L;
    // Graphic components.
    private JPanel jContentPane = null;
    private JPanel jPanelLeft = null;
    private JPanel jPanelRight = null;
    private JPanel jPanelTopRight = null;
    private JTabbedPane jTabbedPane = null;
    private JScrollPane jScrollPane = null;
    private JMenu jMenuFile = null;
    private JMenu jMenuHelp = null;
    private JMenu jMenuSapphiron = null;
    private JMenuItem jMenuItemImport = null;
    private JMenuItem jMenuItemExport = null;
    private JMenuItem jMenuItemTutorial = null;
    private JList jListAnimals = null;
    private JButton jbProcess = null;
    private JButton jbChangeEnquirer = null;
    private JComboBox jComboBox = null;
    private JLabel jlCurrentEnquirer = null;

    private DefaultListModel jListAnimalsModel = null;
    private LinkedList<ActionListener> jComboBoxListeners = null;
    private ActionListener jMenuItemImportListener = null;
    private ActionListener jMenuItemExportListener = null;

    private Vector<IEnquirerComponent> enquirers = null;
    private Vector<String> enquirerNames = null;
    private int currentEnquirer = 0;
    private Hashtable<String, AnimalStatistics> currentTest = null;
    private int questionCount = 0;

    private IBaseConhecimento base;
    private ISearcher searcher;
    private IAnimalPix animalPix;

    private ByteArrayOutputStream output;

    /**
     * This is the default constructor
     */
    public Sapphiron() {
        this.output = new ByteArrayOutputStream();

        System.setOut(new PrintStream(this.output));

        this.currentTest = new Hashtable<String, AnimalStatistics>();

        this.enquirers = new Vector<IEnquirerComponent>();

        this.enquirerNames = new Vector<String>();

        this.jComboBoxListeners = new LinkedList<ActionListener>();

        this.base = new BaseConhecimento();
    }
    
    @Override
    public void connect(INaxxramas arg0) {
        // Add menu items.
        arg0.add(getJMenuSapphiron());

        // Add the panel with icon.
        arg0.add("Sapphiron", getJContentPane(),
            "/pt/c03ensaios/naxxramas/images/sapphiron.gif");
    }

    @Override
    public void connect(ISearcher arg0) {
        this.searcher = arg0;

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (searcher != null) {
                    createOnlineInfoTab();
                }
            }
        };

        this.addActionListener(al);
    }

    @Override
    public void connect(IAnimalPix arg0) {
        this.animalPix = arg0;

        ActionListener al = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (animalPix != null) {
                    createAnimalImageTab();
                }
            }
        };

        this.addActionListener(al);
    }

    @Override
    public void connect(IEnquirerComponent arg0) {
        this.enquirers.add(arg0);
        String name;
        try {
            name = arg0.getContentName();
        } catch (ComponentNotSetException e) {
            this.enquirers.remove(arg0);
            return;
        }
        
        this.enquirerNames.add(name);
        this.currentEnquirer = this.enquirers.size() - 1;
        this.jlCurrentEnquirer.setText(name);
    }

    private void loadAnimals() {
        String nameList[] = this.base.listaNomes();
        Hashtable<String, Integer> questions = new Hashtable<String, Integer>();
        for (String name: nameList) {
            this.jListAnimalsModel.addElement(name);

            IObjetoConhecimento obj = this.base.recuperaObjeto(name);
            IDeclaracao d = obj.primeira();
            while (d != null) {
                String question = d.getPropriedade();
                Integer i = questions.get(question);
                if (i == null) {
                    questions.put(question, new Integer(1));
                    ++this.questionCount;
                }
                else {
                    i = new Integer(i.intValue() + 1);
                }
                d = obj.proxima();
            }
        }
    }

    private AnimalStatistics createAnimalStatistics(String animal) {
        try {
            this.output.flush();
            this.output = new ByteArrayOutputStream();
            System.setOut(new PrintStream(this.output));
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(null,
                    "Unable to flush buffered output.\n" +
                    "Program will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(JFrame.ERROR);
        }
        
        IRazuvious estatistica = new Razuvious();
        IResponder responder = new Responder(estatistica, animal);
        
        long time = 0, memory = 0;

        try {
            Runtime run = Runtime.getRuntime();
            IEnquirerComponent enquirer = null; 

            try {
                enquirer = this.enquirers.get(this.currentEnquirer);
                memory = run.totalMemory() - run.freeMemory();
                enquirer.start();
                memory = Math.abs(run.totalMemory() - run.freeMemory() - memory);
            } catch (InstantiationException e) {
                JOptionPane.showMessageDialog(null,
                    "Cannot create enquirer.\nProgram will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(JFrame.ERROR);
           } catch (IllegalAccessException e) {
                JOptionPane.showMessageDialog(null,
                    "Cannot access enquirer.\nProgram will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(JFrame.ERROR);
           }

            time = System.nanoTime();
            try {
                enquirer.connect(responder);
            } catch (ComponentNotSetException e) {
                JOptionPane.showMessageDialog(null,
                        "Enquirer was not set.\nProgram will exit.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(JFrame.ERROR);
            } catch (ComponentNotStartedException e) {
                JOptionPane.showMessageDialog(null,
                        "Enquirer was not started.\nProgram will exit.",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
                    System.exit(JFrame.ERROR);
            }
            time = System.nanoTime() - time;
            
            // Finds all questions that belong to the animal.
            IObjetoConhecimento obj = this.base.recuperaObjeto(animal);
            IDeclaracao d = obj.primeira();
            
            ArrayList<Pergunta> questions = new ArrayList<Pergunta>();
            while (d != null) {
                questions.add(new Pergunta(d.getPropriedade(), d.getValor()));
                d = obj.proxima();
            }
            
            return new AnimalStatistics(time, this.output.toString(),
                estatistica.repetiu(), memory, Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().freeMemory(),
                Runtime.getRuntime().availableProcessors(),
                estatistica.totalPerguntas(),
                estatistica.perguntasAnimal(questions));
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null,
                    "No Enquirer has been set.\nProgram will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(JFrame.ERROR);
        }

        return null;
    }

    private void createFlowTab(String animal) {
        JTextArea text = new JTextArea(this.currentTest.get(animal).getFlow());
        JScrollPane flow = new JScrollPane(text);

        this.jTabbedPane.add("Flow", flow);
    }

    private void createResourcesTab(String animal) {
        ResourcesTab resources = new ResourcesTab();
        resources.loadInfo(this.currentTest.get(animal).getMicroTime(), 
            this.currentTest.get(animal).getProcessors(), 
            this.currentTest.get(animal).getKbMemory(), 
            this.currentTest.get(animal).getFreeMemory(),
            this.currentTest.get(animal).getTotalMemory());

        this.jTabbedPane.add("Resources", resources);
    }

    private void createChartTab(String animal) {
        AnimalStatistics as = this.currentTest.get(animal);
        /* To avoid inconsistencies, only display the chart if
         * there were no repeated questions. */
        if (!as.repetiuPergunta()) {
            Slice askedQuestions, animalQuestions, otherQuestions;
            
            int askedQuestionsCount = as.getAskedQuestionsCount(),
                animalQuestionsCount = as.getAnimalQuestionsCount();
    
            animalQuestions = new Slice(
                (int)(animalQuestionsCount * 100 / this.questionCount),
                animalQuestionsCount,
                new Color(128, 198, 90), "Animal's asked questions");
            askedQuestions = new Slice(
                (int)((askedQuestionsCount - animalQuestionsCount)
                * 100 / this.questionCount),
                askedQuestionsCount - animalQuestionsCount,
                new Color(250, 97, 97), "Other questions");
            otherQuestions = new Slice(
                (int)((this.questionCount - askedQuestionsCount)
                * 100 / this.questionCount),
                this.questionCount - askedQuestionsCount,
                new Color(118, 164, 251), "Non-asked questions");
            
            Slice slices[] = { animalQuestions, askedQuestions, otherQuestions };
            
            PieChart pc = new PieChart(slices, new Dimension(250, 250), "All questions");
            
            PieChartTab pieChartTab = new PieChartTab(pc);
            
            this.jTabbedPane.add("Chart", pieChartTab);
            
            GoogleChartApi onlineChart = new GoogleChartApi(this.jTabbedPane, 
                    slices, pieChartTab);
            onlineChart.start();
        }
        else {
            JLabel label = new JLabel("The chart is only available when " +
                "there are no repeated questions.");
            label.setHorizontalAlignment(JLabel.CENTER);
            this.jTabbedPane.add("Chart", label);
        }
    }

    private void createOnlineInfoTab() {
        JPanel onlineInfo = new JPanel();

        onlineInfo.setLayout(new BorderLayout());

        onlineInfo.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        JLabel label = new JLabel("If you'd like to know more about this animal, " +
            "click here to see what these search engines have to say about it:");
                label.setHorizontalAlignment(JLabel.CENTER);

        onlineInfo.add(label, BorderLayout.NORTH);

        JPanel searchButtons = new JPanel();
        searchButtons.setLayout(new BoxLayout(searchButtons, BoxLayout.X_AXIS));

        Dimension horizontalGap = new Dimension(20, 0);
        searchButtons.add(Box.createHorizontalGlue());
        searchButtons.add(getWikipediaButton());
        searchButtons.add(new Box.Filler(horizontalGap, horizontalGap,
            horizontalGap));
        searchButtons.add(getGoogleButton());
        searchButtons.add(Box.createHorizontalGlue());

        onlineInfo.add(searchButtons, BorderLayout.CENTER);

        jTabbedPane.add("Online Info", onlineInfo);
    }

    private void createAnimalImageTab() {
        String animal = getCurrentAnimal(),
        title = "Image";
        JComponent animalImage;

        if (this.animalPix.hasImage(animal)) {
            animalImage = this.getAnimalImagePanel(animal);
        }
        else {
            animalImage = this.getAddAnimalImagePanel(animal);
        }

        jTabbedPane.add(title, animalImage);
    }

    private JScrollPane getAnimalImagePanel(String animal) {
        Image image = this.animalPix.getImage(animal);
        
        JLabel label = new JLabel(new ImageIcon(image));

        JScrollPane animalImage = new JScrollPane(label);

        return animalImage;
    }

    private JPanel getAddAnimalImagePanel(String animal) {
        JPanel addImage = new JPanel();
        
        addImage.setLayout(new BorderLayout());

        addImage.setBorder(BorderFactory.createEmptyBorder(100, 0, 100, 0));

        JLabel label = new JLabel("There's no image available for this " +
            "animal. Would you like to add one?");
        label.setHorizontalAlignment(JLabel.CENTER);

        addImage.add(label, BorderLayout.NORTH);

        JPanel addImageButtons = new JPanel();
        addImageButtons.setLayout(new BoxLayout(addImageButtons, BoxLayout.X_AXIS));

        Dimension horizontalGap = new Dimension(20, 0);
        addImageButtons.add(Box.createHorizontalGlue());
        addImageButtons.add(getAddFromDiskButton());
        addImageButtons.add(new Box.Filler(horizontalGap, horizontalGap,
            horizontalGap));
        addImageButtons.add(getAddFromWebButton());
        addImageButtons.add(Box.createHorizontalGlue());

        addImage.add(addImageButtons, BorderLayout.CENTER);

        return addImage;
    }

    private void clearPreviousTest() {
        /* Remove listeners so they won't be triggered during the set
         * up of the new values. */
        for (ActionListener al : this.jComboBoxListeners) {
            this.jComboBox.removeActionListener(al);
        }

        this.jComboBox.removeAllItems();
        this.jTabbedPane.removeAll();
        this.currentTest.clear();
    }

    private void processAnimals() {
        int selectedIndices[] = this.jListAnimals.getSelectedIndices();

        this.clearPreviousTest();

        for (int index: selectedIndices) {
            String animal = (String)this.jListAnimalsModel.get(index);

            this.jComboBox.addItem(animal);
            this.currentTest.put(animal, this.createAnimalStatistics(animal));
        }

        if (selectedIndices.length != 0) {
            for (ActionListener al : this.jComboBoxListeners) {
                this.jComboBox.addActionListener(al);
            }
            this.jComboBox.setSelectedIndex(0);
            this.jComboBox.setEnabled(true);
        }
    }
    
    private void processAnimals(int indices[], Hashtable<String, AnimalStatistics> test) {
        this.clearPreviousTest();

        this.jListAnimals.setSelectedIndices(indices);
        this.currentTest = test;

        for (int index: indices) {
            String animal = (String)this.jListAnimalsModel.get(index);
            this.jComboBox.addItem(animal);
        }

        if (indices.length != 0) {
            for (ActionListener al : this.jComboBoxListeners) {
                this.jComboBox.addActionListener(al);
            }
            this.jComboBox.setSelectedIndex(0);
            this.jComboBox.setEnabled(true);
        }
    }

    /**
     * This method initializes jbChangeEnquirer
     * 
     * @return javax.swing.JButton
     */
    private JButton getJbChangeEnquirer() {
        if (this.jbChangeEnquirer == null) {
            this.jbChangeEnquirer = new JButton();
            this.jbChangeEnquirer.setText("Change");
            this.jbChangeEnquirer.setSize(new Dimension(140, 15));
            this.jbChangeEnquirer.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    getNewEnquirer();
                }
            });
        }
        return this.jbChangeEnquirer;
    }

    private JButton getWikipediaButton() {
        JButton jWikipediaButton = new JButton();

        jWikipediaButton.setIcon(new ImageIcon(getClass().getResource(
            "/pt/c03ensaios/naxxramas/images/wikipedia.png")));
        jWikipediaButton.setToolTipText("Wikipedia");

        jWikipediaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (searcher != null) {
                    searcher.openBrowser(ISearcher.URL_WIKI, getCurrentAnimal());
                }
            }
        });

        return jWikipediaButton;
    }

    private JButton getGoogleButton() {
        JButton jGoogleButton = new JButton();

        jGoogleButton.setIcon(new ImageIcon(getClass().getResource(
            "/pt/c03ensaios/naxxramas/images/google.png")));
        jGoogleButton.setToolTipText("Google");

        jGoogleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (searcher != null) {
                    searcher.openBrowser(ISearcher.URL_GOOGLE, getCurrentAnimal());
                }
            }
        });

        return jGoogleButton;
    }

    private JButton getAddFromDiskButton() {
        JButton addFromDisk = new JButton();

        addFromDisk.setIcon(new ImageIcon(getClass().getResource(
            "/pt/c03ensaios/naxxramas/images/disk.png")));

        addFromDisk.setToolTipText("Add image from the disk");

        addFromDisk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (animalPix != null) {
                    JFileChooser chooser = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Image files", "png", "jpeg", "jpg", "gif", "bmp");
                    chooser.setFileFilter(filter);
                    String filePath = "";
                    int returnVal = chooser.showOpenDialog(null);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                       filePath = chooser.getSelectedFile().getAbsolutePath();
                       String animal = getCurrentAnimal();
                       if (animalPix.setImageFromDisk(filePath, animal)) {
                           jTabbedPane.setComponentAt(jTabbedPane.getSelectedIndex(),
                               getAnimalImagePanel(animal));
                       }
                    }
                }
            }
        });

        return addFromDisk;
    }

    private JButton getAddFromWebButton() {
        JButton addFromWeb = new JButton();

        addFromWeb.setIcon(new ImageIcon(getClass().getResource(
            "/pt/c03ensaios/naxxramas/images/web.png")));

        addFromWeb.setToolTipText("Add image from the web");

        addFromWeb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (animalPix != null) {
                    String s = (String)JOptionPane.showInputDialog(
                        null,
                        "Type in the URL from the picture you want to add.",
                        "New Picture",
                        JOptionPane.PLAIN_MESSAGE);

                    // User selected.
                    if ((s != null) && (s.length() > 0)) {
                        String animal = getCurrentAnimal();
                        if (animalPix.setImageFromWeb(s, animal)) {
                            jTabbedPane.setComponentAt(jTabbedPane.getSelectedIndex(),
                                getAnimalImagePanel(animal));
                        }
                    }
                }
            }
        });

        return addFromWeb;
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(getJPanelLeft(), BorderLayout.WEST);
            jContentPane.add(getJPanelRight(), BorderLayout.CENTER);
        }
        return jContentPane;
    }

    /**
     * This method initializes jMenuSapphiron    
     *     
     * @return javax.swing.JMenu    
     */
    private JMenu getJMenuSapphiron() {
        if (jMenuSapphiron == null) {
            jMenuSapphiron = new JMenu();
            jMenuSapphiron.setPreferredSize(new Dimension(65, 20));
            jMenuSapphiron.setMnemonic(KeyEvent.VK_S);
            jMenuSapphiron.setLocation(new Point(0, 0));
            jMenuSapphiron.setText("Sapphiron");
            jMenuSapphiron.add(getJMenuFile());
            jMenuSapphiron.add(getJMenuHelp());
        }
        return jMenuSapphiron;
    }

    /**
     * This method initializes jMenuFile    
     *     
     * @return javax.swing.JMenu    
     */
    private JMenu getJMenuFile() {
        if (jMenuFile == null) {
            jMenuFile = new JMenu();
            jMenuFile.setMnemonic(KeyEvent.VK_F);
            jMenuFile.setText("File");
            jMenuFile.add(getJMenuItemImport());
            jMenuFile.add(getJMenuItemExport());
        }
        return jMenuFile;
    }

    /**
     * This method initializes jMenuHelp    
     *     
     * @return javax.swing.JMenu    
     */
    private JMenu getJMenuHelp() {
        if (jMenuHelp == null) {
            jMenuHelp = new JMenu();
            jMenuHelp.setText("Help");
            jMenuHelp.setMnemonic(KeyEvent.VK_H);
            jMenuHelp.add(getJMenuItemTutorial());
        }
        return jMenuHelp;
    }

    /**
     * This method initializes jMenuItemImport    
     *     
     * @return javax.swing.JMenuItem    
     */
    private JMenuItem getJMenuItemImport() {
        if (jMenuItemImport == null) {
            jMenuItemImport = new JMenuItem();
            jMenuItemImport.setText("Import...");
            jMenuItemImport.setMnemonic(KeyEvent.VK_I);
            jMenuItemImport.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_I, ActionEvent.CTRL_MASK));
            jMenuItemImportListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    importFile();
                }
            };
            jMenuItemImport.addActionListener(jMenuItemImportListener);
        }
        return jMenuItemImport;
    }

    /**
     * This method initializes jMenuItemExport    
     *     
     * @return javax.swing.JMenuItem    
     */
    private JMenuItem getJMenuItemExport() {
        if (jMenuItemExport == null) {
            jMenuItemExport = new JMenuItem();
            jMenuItemExport.setText("Export...");
            jMenuItemExport.setMnemonic(KeyEvent.VK_E);
            jMenuItemExport.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_E, ActionEvent.CTRL_MASK));
            jMenuItemExportListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    exportFile();
                }
            };
            jMenuItemExport.addActionListener(jMenuItemExportListener);
        }
        return jMenuItemExport;
    }

    /**
     * This method initializes jMenuItemTutorial    
     *     
     * @return javax.swing.JMenuItem    
     */
    private JMenuItem getJMenuItemTutorial() {
        if (jMenuItemTutorial == null) {
            jMenuItemTutorial = new JMenuItem();
            jMenuItemTutorial.setMnemonic(KeyEvent.VK_T);
            jMenuItemTutorial.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_T, ActionEvent.CTRL_MASK));
            jMenuItemTutorial.setText("Tutorial");
            jMenuItemTutorial.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Steps to run a test:\n" +
                        "1) Check if the current Enquirer is the one you " +
                        "want to use.\nYou can change the current Enquirer " +
                        "at any moment using the \"Change\"\nbutton right " +
                        "next to the Enquirer's name.\n" +
                        "2) Select the animals from the list on the " +
                        "left side of the window.\n" +
                        "3) Click \"Process\".\n" +
                        "4) Use the combo box to select the animal which " +
                        "results you want to analyze.\n\n" +
                        "When you are done analyzing the results, you can" +
                        " choose to save them on a\nfile (Sapphiron > File > Export...) " +
                        "or run a new test by repeating steps 1 through 4.\n\n"+
                        "You can also see the statistics for a previously " +
                        "run test that has been saved\n(Sapphiron > File > Import...).",
                        "Tutorial", JOptionPane.INFORMATION_MESSAGE);
                }
            });
        }
        return jMenuItemTutorial;
    }

    /**
     * This method initializes jPanelLeft    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJPanelLeft() {
        if (jPanelLeft == null) {
            BorderLayout borderLayout = new BorderLayout(0, 10);
            borderLayout.setVgap(10);
            jPanelLeft = new JPanel();
            jPanelLeft.setLayout(borderLayout);
            jPanelLeft.setPreferredSize(new Dimension(170, 0));
            jPanelLeft.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 5),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
            jPanelLeft.add(getJScrollPane(), BorderLayout.CENTER);
            jPanelLeft.add(getJbProcess(), BorderLayout.SOUTH);
        }
        return jPanelLeft;
    }

    /**
     * This method initializes jListAnimals    
     *     
     * @return javax.swing.JList    
     */
    private JList getJListAnimals() {
        if (jListAnimals == null) {
            jListAnimalsModel = new DefaultListModel();
            jListAnimals = new JList();
            jListAnimals.setVisibleRowCount(15);
            jListAnimals.setSize(new Dimension(140, 550));
            jListAnimals.setModel(jListAnimalsModel);
            jListAnimals.setVisibleRowCount(15);
            jListAnimals.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            jListAnimals.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (jListAnimals.getSelectedIndex() == -1) {
                        jbProcess.setEnabled(false);
                    }
                    else {
                        jbProcess.setEnabled(true);
                    }
                }
            });

            this.loadAnimals();
        }
        return jListAnimals;
    }

    /**
     * This method initializes jScrollPane    
     *     
     * @return javax.swing.JScrollPane    
     */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setViewportView(getJListAnimals());
        }
        return jScrollPane;
    }

    /**
     * This method initializes jbProcess    
     *     
     * @return javax.swing.JButton    
     */
    private JButton getJbProcess() {
        if (jbProcess == null) {
            jbProcess = new JButton();
            jbProcess.setText("Process");
            jbProcess.setPreferredSize(new Dimension(140, 30));
            jbProcess.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            jbProcess.setEnabled(false);
            jbProcess.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    processAnimals();
                }
            });
        }
        return jbProcess;
    }

    /**
     * This method initializes jPanelRight    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJPanelRight() {
        if (jPanelRight == null) {
            jPanelRight = new JPanel();
            jPanelRight.setLayout(new BorderLayout());
            jPanelRight.add(getJTabbedPane(), BorderLayout.CENTER);
            jPanelRight.add(getJPanelTopRight(), BorderLayout.NORTH);
            jPanelRight.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 5, 10, 10),
                BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
        }
        return jPanelRight;
    }

    /**
     * This method initializes jPanelTopRight    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJPanelTopRight() {
        if (jPanelTopRight == null) {
            jPanelTopRight = new JPanel();
            GridBagLayout gbl = new GridBagLayout();
            jPanelTopRight.setLayout(gbl);
            jPanelTopRight.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

            GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 0.01, 1,
                GridBagConstraints.LINE_START, GridBagConstraints.NONE,
                new Insets(2, 2, 2, 2), 0, 0);

            jPanelTopRight.add(new JLabel("Current Enquirer:"), gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.8;
            gbc.anchor = GridBagConstraints.LINE_START;
            gbc.fill = GridBagConstraints.NONE;

            // Panel that ensures that the minimum label size will be fixed.
            JPanel currentEnquirer = new JPanel();
            currentEnquirer.setLayout(new BorderLayout());
            currentEnquirer.add(getJLCurrentEnquirer(), BorderLayout.CENTER);
            currentEnquirer.setMinimumSize(new Dimension(400, 20));
            currentEnquirer.setSize(new Dimension(400, 20));
            currentEnquirer.setPreferredSize(new Dimension(400, 20));
            jPanelTopRight.add(currentEnquirer, gbc);

            gbc.weightx = 0.1;
            gbc.gridx = 2;
            gbc.anchor = GridBagConstraints.LINE_END;
            gbc.fill = GridBagConstraints.NONE;
            jPanelTopRight.add(getJbChangeEnquirer(), gbc);

            gbc.weightx = 0.01;
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.anchor = GridBagConstraints.LINE_START;
            jPanelTopRight.add(new JLabel("Current animal:"), gbc);

            gbc.weightx = 0.8;
            gbc.gridx = 1;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.BOTH;
            jPanelTopRight.add(getJComboBox(), gbc);
        }
        return jPanelTopRight;
    }

    /**
     * This method initializes jlCurrentEnquirer    
     *     
     * @return javax.swing.JLabel   
     */
    private JLabel getJLCurrentEnquirer() {
        if (jlCurrentEnquirer == null) {
            jlCurrentEnquirer = new JLabel();
        }
        return jlCurrentEnquirer;
    }

    /**
     * This method initializes jTabbedPane    
     *     
     * @return javax.swing.JTabbedPane    
     */
    private JTabbedPane getJTabbedPane() {
        if (jTabbedPane == null) {
            jTabbedPane = new JTabbedPane();
            jTabbedPane.setBackground(new Color(225, 231, 236));
            jTabbedPane.setPreferredSize(new Dimension(200, 300));
        }
        return jTabbedPane;
    }

    /**
     * This method initializes jComboBox    
     *     
     * @return javax.swing.JComboBox    
     */
    private JComboBox getJComboBox() {
        if (jComboBox == null) {
            jComboBox = new JComboBox();
            jComboBox.setEnabled(false);          

            ActionListener al = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String animal = getCurrentAnimal();
                    if (animal != null) {
                        jTabbedPane.removeAll();
                        createFlowTab(animal);
                        createResourcesTab(animal);
                        createChartTab(animal);
                    }
                }
            };

            // Ensures that the default listener will be executed first.
            jComboBoxListeners.addLast(al);
        }
        return jComboBox;
    }

    @SuppressWarnings("unchecked")
    private void importFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "SPH Sapphiron file", "sph");
        chooser.setFileFilter(filter);
        String filePath = "";
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
           filePath = chooser.getSelectedFile().getAbsolutePath();
        }
        else {
            return;
        }

        if (!filePath.endsWith(".sph")) {
            filePath += ".sph";
        }

        FileInputStream fis = null;
        ObjectInputStream in = null;
        Hashtable<String, AnimalStatistics> loadedTest = null;
        
        try {
            fis = new FileInputStream(filePath);
            in = new ObjectInputStream(fis);
            loadedTest = (Hashtable<String, AnimalStatistics>)in.readObject();
            int indices[] = (int[])in.readObject();
            int currentEnquirer = ((Integer)in.readObject()).intValue();
            this.setCurrentEnquirer(currentEnquirer);
            in.close();

            this.processAnimals(indices, loadedTest);
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Cannot read file.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch(ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Object class not found.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        } catch (ClassCastException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error while loading data.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void exportFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "SPH Sapphiron file", "sph");
        chooser.setFileFilter(filter);
        String filePath = "";

        int returnVal = chooser.showSaveDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile().getAbsolutePath();
        }
        else {
            return;
        }
        
        if (!filePath.endsWith(".sph")) {
            filePath += ".sph";
        }
        
        FileOutputStream fos = null;
        ObjectOutputStream out = null;

        try {
            fos = new FileOutputStream(filePath);
            out = new ObjectOutputStream(fos);

            out.writeObject(currentTest.clone());
            out.writeObject(this.jListAnimals.getSelectedIndices());
            out.writeObject(new Integer(this.currentEnquirer));

            out.close();
            JOptionPane.showMessageDialog(null,
                    "File saved with success!",
                    "OK!", JOptionPane.INFORMATION_MESSAGE);
         } catch(NotSerializableException ex) {
                JOptionPane.showMessageDialog(null,
                        "Cannot serialize.\n" +
                        ex.toString(),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
         } catch(IOException ex) {
            JOptionPane.showMessageDialog(null,
                    "Cannot write file.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
         }
    }
    
    private void setCurrentEnquirer(int index) {
        this.currentEnquirer = index;
        this.jlCurrentEnquirer.setText(this.enquirerNames.get(index));
        this.clearPreviousTest();
    }

    private void getNewEnquirer() {
        String s = (String)JOptionPane.showInputDialog(
            null,
            "Select a new Enquirer.\n" +
            "This action will erase the processed data of any open tests.",
            "Change your Enquirer",
            JOptionPane.PLAIN_MESSAGE,
            null,
            this.enquirerNames.toArray(),
            this.enquirerNames.get(this.currentEnquirer));

        // User selected.
        if ((s != null) && (s.length() > 0)) {
            int index = this.enquirerNames.indexOf(s);
            this.setCurrentEnquirer(index);
        }
    }

    @Override
    public String getCurrentAnimal() {
        return (String)this.jComboBox.getSelectedItem();
    }

    @Override
    public void addActionListener(ActionListener al) {
        this.jComboBoxListeners.addFirst(al);
    }

    @Override
    public void addTab(String title, JComponent content) {
        this.jTabbedPane.add(title, content);
    }
}