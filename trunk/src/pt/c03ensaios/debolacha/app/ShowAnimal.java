package pt.c03ensaios.debolacha.app;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.relu.Searcher;

import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class ShowAnimal extends JFrame{
	
	private JPanel shownPanel;
	private JLabel animalPicture;
	private ImageIcon animalImage;
	private JButton imageButton;
	private JTextArea animalMessage;
	private JTextArea wikiLink;
	
	private IAnimalPix animalPix;
	private String animal;
	private IGlobalFactory factory;
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor. Creates three panels aligned vertically:
	 * The first one contains an image, the second one, a message, and, the third one, a link.
	 * @param animal
	 * @throws ContextException
	 * @throws FactoryException
	 */
	public ShowAnimal(String animal) throws ContextException, FactoryException{
		super();
		
		/*Instances some attributes*/
		this.animal = animal; 
		factory = ComponentContextFactory.createGlobalFactory();
		
		/*Builds the frame*/
		shownPanel = new JPanel();
        
        image();
		
		text();
		
		link();
		
		/*Set some properties*/
		add(shownPanel);
		if(animalPix.hasImage(animal))
			setSize(animalImage.getIconWidth()+50, animalImage.getIconHeight()+110);
		else setSize(250, 300);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
	}
	
	/**
	 * Shows the animal.
	 * @param contentPane
	 */
	public void image(){
		animalPix = factory.createInstance(
				"<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>");
		if(animalPix.hasImage(animal)){
			animalImage = new ImageIcon(animalPix.getImage(animal));
			
			animalPicture = new JLabel(animalImage);
			animalPicture.setSize(animalImage.getIconWidth(), animalImage.getIconHeight());
			animalPicture.setBorder(new EtchedBorder(EtchedBorder.RAISED));

	        shownPanel.add(animalPicture);
		}
		else{
			imageButton = new JButton("Adicione uma imagem!");
			imageButton.setSize(100, 200);
			shownPanel.add(imageButton);
			imageButton.addMouseListener(new MouseListener(){

				@Override
				public void mouseClicked(MouseEvent arg0) {
					addImage();
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
				}

				@Override
				public void mousePressed(MouseEvent arg0) {
				}

				@Override
				public void mouseReleased(MouseEvent arg0) {	
				}
			});
		}
		
	}
	
	/**
	 * Writes a message.
	 * @param contentPane
	 */
	public void text(){
        animalMessage = new JTextArea("Seu animal é "+animal+"!");
        
        animalMessage.setSize(100,50);
        animalMessage.setBackground(new Color(238,238,238));
        animalMessage.setFont(new Font("times new roman", Font.PLAIN,18));
		
        shownPanel.add(animalMessage);
	}
	
	/**
	 * Opens the Wikipedia page about the animal.
	 * @param contentPane
	 */
	public void link(){
		wikiLink = new JTextArea("Saiba mais sobre ele clicando aqui.");
		
		wikiLink.setSize(100,50);
		wikiLink.setBackground(new Color(238,238,238));
        wikiLink.setFont(new Font("times new roman", Font.PLAIN,12));

        shownPanel.add(wikiLink);
		
		wikiLink.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				Searcher wiki = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.relu.Searcher>");
				wiki.openBrowser("http://pt.wikipedia.org/wiki/",animal);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {	
			}
		});
	}

	/**
	 * Allows the user to add an image related to the animal.
	 */
	private void addImage(){
		animalPix.showImageWindow(animal);
	}
}
