package pt.c03ensaios.fejao.etapa3.impl;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PlanoFundo extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	Image img = new ImageIcon ( getClass ( ).getResource ( "img/arca_noe_1.jpg" ) ).getImage ( );  
	  
    public void paintComponent ( Graphics g )  
    {  
        super.paintComponent ( g );  
        int x = ( this.getWidth ( ) - img.getWidth ( null ) ) / 2;  
        int y = ( this.getHeight ( ) - img.getHeight ( null ) ) / 2;  
        g.drawImage ( img , x , y , this );  
    }  
}
