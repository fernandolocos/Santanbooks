package pt.c03ensaios.afinder.animalpix;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.FocusEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class APixWindow extends JFrame {
	
	private static final long serialVersionUID = 4180529118164093383L;
	private AnimalPix aPix;
	private JPanel diskPanel, webPanel;
	private JFrame window;
	private JLabel imageLabel;
    private JButton searchButton, diskButton, webButton;
    private TextField urlText, diskText;
    private Image img;
	private String animal, path, url;
	
    public APixWindow(String name) {
    	animal = name;
    	window = new JFrame("AnimalPix - " + animal.toUpperCase());
        aPix = new AnimalPix();
      
        //SETTINGS FOR THE "IMAGE" PANEL
        img = aPix.getImage(animal);
        if(img != null)
        	imageLabel = new JLabel(new ImageIcon(img));
        else
        	imageLabel = new JLabel();
        //SETTINGS FOR THE "IMAGE" PANEL
        
        
        //SETTINGS FOR THE "ADD IMAGE FROM DISK" PANEL
        diskPanel = new JPanel();
        diskPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        diskButton = new JButton("Add image from disk");
        diskButton.setPreferredSize(new Dimension(200, 30));
        diskButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                diskButtonPressed();
                window.setVisible(false);
                window.remove(imageLabel);
                img = aPix.getImage(animal);
                if(img != null)
                	imageLabel = new JLabel(new ImageIcon(img));
                else
                	imageLabel = new JLabel();
                new APixWindow(animal);
            }
        });
        diskText = new TextField(39);
        diskText.setText("Type the source path or click to search");
        diskText.addFocusListener(new java.awt.event.FocusListener() {
        	public void focusGained (FocusEvent e){
        		if (path == null){
        			diskText.setText("");
        			diskText.setSelectionStart(0);
        		}
        	}
        	public void focusLost (FocusEvent e){
        		if (diskText.getText().equalsIgnoreCase(""))
        			diskText.setText("Type the source path or click to search");
        		else
        			path = diskText.getText();
        	}
        });
        searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(79, 23));
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                searchButtonPressed();
                diskText.setText(path);
            }
        });
        diskPanel.add(diskText);
        diskPanel.add(searchButton);
        diskPanel.add(diskButton);
        //END OF SETTINGS FOR THE "ADD IMAGE FROM DISK" PANEL
        
        
        //SETTINGS FOR THE "ADD IMAGE FROM INTERNET" PANEL
        webPanel = new JPanel();
        webPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        urlText = new TextField(51);
        urlText.setText("Type the url");
        urlText.addFocusListener(new java.awt.event.FocusListener() {
        	public void focusGained (FocusEvent e){
        		if (url == null || url.equalsIgnoreCase("")){
        			urlText.setText("");
        			urlText.setSelectionStart(7);
        		}
        	}
        	public void focusLost (FocusEvent e){
        		if (urlText.getText().equalsIgnoreCase(""))
        			urlText.setText("Type the url");
        		else
        			url = urlText.getText();
        	}
        });
        webButton = new JButton("Add image from internet");
        webButton.setPreferredSize(new Dimension(200, 30));
        webButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                netButtonPressed();
                window.setVisible(false);
                window.remove(imageLabel);
                img = aPix.getImage(animal);
                if(img != null)
                	imageLabel = new JLabel(new ImageIcon(img));
                else
                	imageLabel = new JLabel();
                new APixWindow(animal);
                }
        });
        webPanel.add(urlText);
        webPanel.add(webButton);
        //END OF SETTINGS FOR THE "ADD IMAGE FROM INTERNET" PANEL
        
        
        //SETTINGS FOR THE FRAME
        window.setIconImage(aPix.getImage("icon"));
        window.add(imageLabel, BorderLayout.NORTH);
        window.add(diskPanel, BorderLayout.CENTER);
        window.add(webPanel, BorderLayout.SOUTH);
        window.pack();
        if (img != null)
        	window.setSize(730, img.getHeight(null) + 110 );
        else
        	window.setSize(730, 110);
        diskButton.requestFocusInWindow();
        window.setLocation(10, 10);
        window.setResizable(false);
        window.setVisible(true);
        window.setAlwaysOnTop(true);
        window.setAlwaysOnTop(false);
        
        //END OF SETTINGS FOR THE FRAME
    
    }
    
    private void searchButtonPressed(){
    	JFileChooser chooser = new JFileChooser();
    	chooser.setFileFilter(new ImageFileFilter("Image Files", "jpg", "gif", "png"));
		
    	if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				path = chooser.getSelectedFile().getAbsolutePath();
    	}
   
    }
    
    /* If clicked, a window will open to user to add an image.
	 * After add the image, it will be shown on the screen.
	 */
    private void diskButtonPressed() {
    	boolean picAdded = aPix.setImageFromDisk(path, animal);
    	if (picAdded)
    		JOptionPane.showMessageDialog(new JFrame(), "Image successfully set.", "Info",
    									JOptionPane.INFORMATION_MESSAGE);
    	else
    		JOptionPane.showMessageDialog(new JFrame(), "Error setting the image. Try again.", "Error",
    									JOptionPane.ERROR_MESSAGE);
    }
   
    private void netButtonPressed() {
    	boolean picAdded = aPix.setImageFromWeb(url, animal);
    	if (picAdded)
    		JOptionPane.showMessageDialog(new JFrame(), "Image successfully set.", "Info",
    									JOptionPane.INFORMATION_MESSAGE);
    	else
    		JOptionPane.showMessageDialog(new JFrame(), "Error setting the image. Try again.", "Error",
    									JOptionPane.ERROR_MESSAGE);
    	
    }
    
}
