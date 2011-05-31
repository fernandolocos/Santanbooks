package pt.c03ensaios.debolacha.app;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SpringLayout;

import pt.c03ensaios.debolacha.impl.AnimalFile;
import pt.c03ensaios.debolacha.inter.IAnimalFile;
import pt.c03ensaios.linnaeus.AnimalsDatabase;
import pt.c03ensaios.linnaeus.IAnimalsDatabase;
import pt.c03ensaios.lobo.impl.AnimalCreatorGUI;
import pt.c03ensaios.lobo.inter.IAnimalCreatorGUI;
import pt.c03ensaios.naxxramas.INaxxramas;
import pt.c03ensaios.naxxramas.Naxxramas;
import pt.c03ensaios.tochinhas2.impl.IQuestions;
import pt.c03ensaios.tochinhas2.impl.Questions;
import anima.component.IRequires;
import anima.context.exception.ContextException;
import anima.factory.IGlobalFactory;
import anima.factory.context.componentContext.ComponentContextFactory;
import anima.factory.exception.FactoryException;

public class AskEdit extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private SpringLayout layout;
	private Container contentPane;
	private JLabel message;
	private JButton yes;
	private JButton no;
	
	public AskEdit(){
		layout = new SpringLayout();
		contentPane =  getContentPane();
		contentPane.setLayout(layout);
		
		message = new JLabel("Deseja adiconar um novo animal à base de dados?");
		message.setFont(new Font("times new roman", Font.BOLD, 16));
		layout.putConstraint(SpringLayout.SOUTH, message, -30, SpringLayout.VERTICAL_CENTER, contentPane);
		layout.putConstraint(SpringLayout.HORIZONTAL_CENTER, message, 0, SpringLayout.HORIZONTAL_CENTER, contentPane);
		contentPane.add(message);
		
		yes = new JButton("Sim");
		yes.setSize(50, 30);
		yes.setFont(new Font("times new roman", Font.PLAIN, 14));
		layout.putConstraint(SpringLayout.NORTH, yes, 30, SpringLayout.SOUTH, message);
		layout.putConstraint(SpringLayout.EAST, yes, -50, SpringLayout.HORIZONTAL_CENTER, contentPane);
		contentPane.add(yes);
		yes.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				IGlobalFactory factory;
				try {
					factory = ComponentContextFactory.createGlobalFactory();
				

					factory.registerPrototype(AnimalCreatorGUI.class);
					IAnimalCreatorGUI creator = factory.createInstance(
						"<http://purl.org/dcc/pt.c03ensaios.lobo.impl.AnimalCreatorGUI>");
					
					factory.registerPrototype(AnimalFile.class);
					IAnimalFile file = factory.createInstance(
						"<http://purl.org/dcc/pt.c03ensaios.debolacha.impl.AnimalFile>");
					
					factory.registerPrototype(Questions.class);
					IQuestions question = factory.createInstance(
						"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.Questions>");
					
					IRequires<IAnimalsDatabase> questionReceptacle = question.queryReceptacle(
							"<http://purl.org/dcc/pt.c03ensaios.linnaeus.IAnimalsDatabase>");
					
					factory.registerPrototype(AnimalsDatabase.class);
					IAnimalsDatabase animals = factory.createInstance(
							"<http://purl.org/dcc/pt.c03ensaios.linnaeus.AnimalsDatabase>");
					
					IRequires<IQuestions> animalsReceptacle = animals.queryReceptacle(
							"<http://purl.org/dcc/pt.c03ensaios.tochinhas2.impl.IQuestions>");

					factory.registerPrototype(Naxxramas.class);
					INaxxramas naxx = factory.createInstance(
							"<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>");
					
					questionReceptacle.connect(animals);
					animalsReceptacle.connect(question);
					((AnimalFile) file).connect(animals);
					
					
					creator.connect(file);
					creator.connect(question);
					
					creator.initialize();
					creator.setTestMode(false);
					creator.connect(naxx);
					naxx.setVisible(true);
				} catch (ContextException e) {
					e.printStackTrace();
				} catch (FactoryException e) {
					e.printStackTrace();
				}
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
			
		no = new JButton("Não");
		no.setSize(50, 30);
		no.setFont(new Font("times new roman", Font.PLAIN, 14));
		layout.putConstraint(SpringLayout.NORTH, no, 30, SpringLayout.SOUTH, message);
		layout.putConstraint(SpringLayout.WEST, no, 50, SpringLayout.HORIZONTAL_CENTER, contentPane);
		contentPane.add(no);
		no.addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {				
			}

			@Override
			public void mousePressed(MouseEvent e) {				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
		});
		
		setSize(400, 180);
		setVisible(true);
	}
}
