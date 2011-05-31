package pt.c03ensaios.fejao.etapa3.impl;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class Janela extends JFrame{
	private static final long serialVersionUID = 1L;
	private PlanoFundo pnlFundo = new PlanoFundo();
	private static int xDoMouse = 0;
	private static int yDoMouse = 0;

    public Janela(){
       this.setSize(800,700);                       
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setLayout(new BorderLayout());
       this.add(pnlFundo, BorderLayout.CENTER);
       this.addMouseListener(
          new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
            	xDoMouse = e.getX(); 
            	yDoMouse = e.getY();
            	if(e.getClickCount() > 0)  
            		System.out.println("x: "+xDoMouse+" y: "+yDoMouse);
            }
          }
        );
       this.setVisible(true);
    }

    public static void main(String[] args){
    		new Janela();
    }
}