package pt.c03ensaios.denisecamila;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pt.c03ensaios.afinder.animalpix.IAnimalPix;
import pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent;
import pt.c03ensaios.relu.ISearcher;
import anima.annotation.Component;
import anima.component.base.ComponentBase;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.denisecamila.FinalGraphic>",
		provides={"<http://purl.org/dcc/pt.c03ensaios.denisecamila.IFinalGraphic>"},
		requires={"<http://purl.org/dcc/pt.c03ensaios.relu.ISearcher>",
				  "<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.IFileGUIComponent>",
				  "<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>"})
public class FinalGraphic extends ComponentBase implements IFinalGraphic, IReceptacleSearcher, IReceptacleAnimalPix, IReceptacleFileGUI {

	private ISearcher searcher;
	private IAnimalPix pix;
	private IFileGUIComponent file;

	private JFrame janela;
	private JButton b1;
	private JButton b2;
	private JButton b3;

	static String animal;

	public void exibe(String animal) {

		janela = new JFrame();
		FinalGraphic.animal = animal;
		janela.getContentPane().setBackground(new Color(222, 222, 235));
		janela.setResizable(false);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		janela.setTitle("Animals");

		if (animal != null){
			janela.setSize(350, 210);
			animalEncontrado();
			janela.getContentPane().add(b1);
			janela.getContentPane().add(b3);

		}
		else{

			JLabel texto = new JLabel();
			texto.setText("<html><h2>Esse animal não foi encontrado :(</h2></html>");
			janela.add(texto);
			janela.setSize(350, 140);   
		}

		cadastreAnimal();
		janela.getContentPane().add(b2);
		janela.getContentPane().setLayout(new FlowLayout());      
		janela.setVisible(true);
	}

	private void animalEncontrado(){

		String animalUpper = animal.toUpperCase();

		JLabel texto = new JLabel();
		texto.setText("<html><h1>O animal encontrado foi: <br/><FONT COLOR=#27408B><p align=center>"
				+ animalUpper + "</center></p></FONT></h1></html>");

		janela.add(texto);

		b1 = new JButton("Saiba mais sobre " + animal.toLowerCase());

		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				searcher.openBrowser(ISearcher.URL_WIKI, FinalGraphic.animal);
			}});

		b3 = new JButton("FOTO");

		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pix.showImageWindow(FinalGraphic.animal);
			}
		});     
	}

	private void cadastreAnimal(){

		b2 = new JButton("Nos ajude! Cadastre um novo animal!");

		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try { 
					IGlobalFactory factory = ComponentContextFactory.createGlobalFactory();
					IFileGUIComponent file = factory.createInstance("<http://purl.org/dcc/pt.c03ensaios.fabsouza.FileGUIComponent.FileGUIComponent>");
					file.showFrame();
				} catch (ContextException e) {
					e.printStackTrace();
				} catch (FactoryException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void connect(ISearcher searcher) {
		this.searcher = searcher;
	}

	@Override
	public void connect(IAnimalPix pix) {
		this.pix = pix;
	}

	@Override
	public void connect(IFileGUIComponent file) {
		this.file = file;
	}

}