package pt.c03ensaios.naxxramas;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import anima.annotation.Component;
import anima.component.view.base.FrameComponentBase;

/**
 * Administers graphical user interfaces, integrating graphical components in
 * a single frame.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
@Component(id="<http://purl.org/dcc/pt.c03ensaios.naxxramas.Naxxramas>",
        provides="<http://purl.org/dcc/pt.c03ensaios.naxxramas.INaxxramas>")
public class Naxxramas extends FrameComponentBase implements INaxxramas {
    private static final long serialVersionUID = -916814740236275500L;
    // Graphic components.
    private JTabbedPane jContentTabs = null;
    private JMenuBar jMenuItemsBar = null;

    /**
     * This is the default constructor
     */
    public Naxxramas() {
        super();
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Cannot display window due to a look and feel error.\n" +
                e.getMessage() +
                "\nProgram will exit.",
                "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(ERROR);
        } catch (InstantiationException e) {
            JOptionPane.showMessageDialog(null,
                "Cannot display window due to a look and feel error.\n" +
                e.getMessage() +
                "\nProgram will exit.",
                "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(ERROR);
        } catch (IllegalAccessException e) {
            JOptionPane.showMessageDialog(null,
                "Cannot display window due to a look and feel error.\n" +
                e.getMessage() +
                "\nProgram will exit.",
                "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(ERROR);
        } catch (UnsupportedLookAndFeelException e) {
            JOptionPane.showMessageDialog(null,
                "Cannot display window due to a look and feel error.\n" +
                e.getMessage() +
                "\nProgram will exit.",
                "ERROR", JOptionPane.ERROR_MESSAGE);
                System.exit(ERROR);
        }
        
        this.initialize();
    }


    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(800, 550);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(
            getClass().getResource("/pt/c03ensaios/naxxramas/images/naxxramas.png")));
        this.setJMenuBar(getJMenuItemsBar());
        this.setContentPane(this.getJContentTabs());
        this.setTitle("Naxxramas");
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private JTabbedPane getJContentTabs() {
        if(this.jContentTabs == null) {
            this.jContentTabs = new JTabbedPane();
        }
        return this.jContentTabs;
    }
    
    /**
     * This method initializes jMenuItemsBar
     *     
     * @return javax.swing.JMenuBar    
     */
    private JMenuBar getJMenuItemsBar() {
        if (jMenuItemsBar == null) {
            jMenuItemsBar = new JMenuBar();
            jMenuItemsBar.setPreferredSize(new Dimension(18, 25));
        }
        return jMenuItemsBar;
    }

    @Override
    public void add(JMenu newMenu) {
        try {
            this.getJMenuItemsBar().add(newMenu);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,
                "Cannot add menu item.\nProgram will exit.",
                "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(ERROR);
        }
    }

    @Override
    public void add(String title, JPanel newTab) {
        try {
            this.getJContentTabs().addTab(title, newTab);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Cannot add tab item.\nProgram will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(ERROR);
        }
    }

    @Override
    public void add(String title, JPanel newTab, String iconPath) {
        try {
            URL imgURL = getClass().getResource(iconPath);
            if (imgURL == null) {
                throw new FileNotFoundException();
            }
            this.getJContentTabs().addTab(title, new ImageIcon(imgURL), newTab);
        }
        catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "Image icon not found.\nProgram will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(ERROR);
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Cannot add tab item.\nProgram will exit.",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            System.exit(ERROR);
        }
    }
}
