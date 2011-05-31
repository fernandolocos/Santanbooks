package pt.c03ensaios.naxxramas;

import javax.swing.JMenu;
import javax.swing.JPanel;

import anima.annotation.ComponentInterface;
import anima.component.ISupports;

/**
 * Provides methods to graphical user interface administration. Allows 
 * integration of graphical components in a single frame.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
@ComponentInterface("<http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>")
public interface INaxxramas extends ISupports {
    public void add(String title, JPanel newTab);

    public void add(String title, JPanel newTab, String iconPath);

    public void add(JMenu newMenu);

    public void setSize(int width, int height);

    public void setVisible(boolean b);
}
