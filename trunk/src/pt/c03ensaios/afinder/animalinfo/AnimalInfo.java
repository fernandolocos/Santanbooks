package pt.c03ensaios.afinder.animalinfo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pt.c01interfaces.s01chaveid.s01base.impl.BaseConhecimento;
import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent;
import pt.c03ensaios.relu.ISearcher;
import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component( id="<http://purl.org/dcc/pt.c03ensaios.afinder.animalinfo.AnimalInfo>",
        	provides="<http://purl.org/dcc/pt.c03ensaios.afinder.animalinfo.IAnimalInfo>",
        	requires={	"<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>",
        		 		"<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>",
        		 		"<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>" } )
public class AnimalInfo extends ComponentBase implements IAnimalInfo {
	
	private static final long serialVersionUID = 1598940871356473740L;
	private JFrame listFrame;
	private JList list;
	private IAnimalPix aPix;
	private IFileGUIComponent fileGUI;
	private ISearcher searcher;
	
	public void showFrame (){
		
		JFrame frame = new JFrame("AnimalInfo");
		JPanel textPanel, midPanel, lowPanel;
		JLabel title;
		JButton addTxt, viewImgs, searchGoogle, searchWiki;
    	
		addTxt = new JButton("Add or edit animal");
		addTxt.setPreferredSize(new Dimension (250, 50));
		addTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                txtButtonPressed();
            }
        });
		
		searchWiki = new JButton("Search an animal on Wikipedia");
		searchWiki.setPreferredSize(new Dimension (250,50));
		searchWiki.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                wikiButtonPressed();
            }
        });
		
		viewImgs = new JButton ("View or add animal pictures");
		viewImgs.setPreferredSize(new Dimension (250, 50));
		viewImgs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                imgButtonPressed();
            }
        });
		
		searchGoogle = new JButton("Search an animal on Google");
		searchGoogle.setPreferredSize(new Dimension (250,50));
		searchGoogle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                googleButtonPressed();
            }
        });
		
		title = new JLabel("<html><center><font size=6>Help increase the animal database!</font><br>" +
							"</br><font size=4>Need more information about a certain animal?<br>" +
							"</br> Click to search it on Wikipedia.<br>" +
							"</br>You can also look for pictures on Google.</font><center></html>");
				
    	textPanel = new JPanel(); 
    	textPanel.add(title);
    	midPanel = new JPanel();
		midPanel.add(addTxt);
		midPanel.add(searchWiki);
		lowPanel = new JPanel();
		lowPanel.add(viewImgs);
		lowPanel.add(searchGoogle);
		
		frame.add(textPanel, BorderLayout.NORTH);
		frame.add(midPanel, BorderLayout.CENTER);
		frame.add(lowPanel, BorderLayout.SOUTH);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.pack();
		frame.setLocation((int)(dim.getWidth()/2) - 250, (int)(dim.getHeight()/2) - 75);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
	}
	
	private void txtButtonPressed(){
        fileGUI.showFrame();
        
	}

    private void wikiButtonPressed(){
    	JButton okButton = animalList();
    	okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String animal = (String)list.getSelectedValue();
                if (animal != null) {
                	searcher.openBrowser(ISearcher.URL_WIKI, animal);
                }
                listFrame.setVisible(false);
            }
        });  
    }

    private void imgButtonPressed(){
    	JButton okButton = animalList();
    	okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String animal = (String)list.getSelectedValue();
                if (animal != null) {
                	aPix.showImageWindow(animal);
                }
                listFrame.setVisible(false);
            }
        });  
    	
    }

    private void googleButtonPressed(){
    	JButton okButton = animalList();
    	okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String animal = (String)list.getSelectedValue();
                if (animal != null) {
                	searcher.openBrowser(ISearcher.URL_GOOGLE, animal);
                }
                listFrame.setVisible(false);
            }
        });    	
    }
    
    @Override
    public void connect(IAnimalPix a) {
    	aPix = a;
    }
    
    @Override
    public void connect(ISearcher s) {
        searcher = s;
    }
    
    @Override
    public void connect(IFileGUIComponent f) {
    	fileGUI = f;
    }
    
    private JButton animalList(){
    	getList();
    	listFrame = new JFrame("List of Animals");
    	JLabel title = new JLabel("Choose the animal:");
    	title.setFont(new Font(null, Font.BOLD, 18));
    	JScrollPane scroll = new JScrollPane(list);
    	JButton okButton = new JButton("OK");
    	
    	listFrame.add(title, BorderLayout.NORTH);
    	listFrame.add(scroll, BorderLayout.CENTER);
    	listFrame.add(okButton, BorderLayout.SOUTH);
    	listFrame.setSize(250,300);
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		listFrame.setLocation((int)(dim.getWidth()/6), (int)(dim.getHeight()/2) - 120);
    	listFrame.setVisible(true);
    	
    	return okButton;
    }
    
    private void getList(){
		String a[] = new BaseConhecimento().listaNomes();
    	Vector<String> v = new Vector<String>();
    	
    	for(int i = 0; i < a.length; i++)
    		v.add(a[i]);
		list = new JList(v);
    }
    
}
