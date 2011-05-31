package pt.c03ensaios.naxxramas;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Provides methods to analyze Enquirers and the results they provide.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.naxxramas.ISapphiron>")
public interface ISapphiron extends ISupports {
    public String getCurrentAnimal();

    public void addActionListener(ActionListener al);

    public void addTab(String title, JComponent content);
}