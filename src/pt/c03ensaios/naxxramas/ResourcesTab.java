package pt.c03ensaios.naxxramas;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JProgressBar;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;

/**
 * Creates the content for the resources tab in the Sapphiron component.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class ResourcesTab extends JPanel {
    private static final long serialVersionUID = -1729402991457678152L;
    private JPanel jpExecutionTime = null;
    private JLabel jlExecutionTime = null;
    private JLabel jlTimeIcon = null;
    private JPanel jpProcessors = null;
    private JLabel jlProcessors = null;
    private JLabel jlProcessorsIcon = null;
    private JPanel jpEnquirerMemory = null;
    private JPanel jpProgressBar = null;
    private JLabel jlEnquirerMemomryIcon = null;
    private JLabel jlEnquirerMemomry = null;
    private JPanel jpJVMMemory = null;
    private JLabel jlJVMMemory = null;
    private JLabel jlJVMMemoryIcon = null;
    private JProgressBar jpbJVMMemory = null;
    private JLabel jlPercentage = null;

    /**
     * This is the default constructor
     */
    public ResourcesTab() {
        super();
        initialize();
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.setSize(400, 300);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

        Dimension vFillerDimensionBig = new Dimension(10, 80),
                  vFillerDimension = new Dimension(10, 30),
                  vFillerDimensionSmall = new Dimension(10, 10);

        this.add(new Box.Filler(vFillerDimensionBig, vFillerDimensionBig,
                vFillerDimensionBig));
        this.add(getJpExecutionTime(), null);
        this.add(new Box.Filler(vFillerDimension, vFillerDimension,
                vFillerDimension));
        this.add(getJpProcessors(), null);
        this.add(new Box.Filler(vFillerDimension, vFillerDimension,
                vFillerDimension));
        this.add(getJpEnquirerMemory(), null);
        this.add(new Box.Filler(vFillerDimension, vFillerDimension,
                vFillerDimension));
        this.add(getJpJVMMemory(), null);
        this.add(new Box.Filler(vFillerDimensionSmall, vFillerDimensionSmall,
                vFillerDimensionSmall));
        this.add(getJpProgressBar(), null);
    }

    /**
     * This method initializes jpExecutionTime    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJpExecutionTime() {
        if (jpExecutionTime == null) {
            jlTimeIcon = new JLabel();
            jlTimeIcon.setText("");
            jlTimeIcon.setIcon(new ImageIcon(getClass().getResource("/pt/c03ensaios/naxxramas/images/time.png")));
            jlExecutionTime = new JLabel();
            jlExecutionTime.setText("Execution time: {x} microseconds");
            jpExecutionTime = new JPanel();
            jpExecutionTime.setLayout(new BoxLayout(getJpExecutionTime(), BoxLayout.X_AXIS));
            jpExecutionTime.add(jlTimeIcon, null);
            Dimension hFillerDimension = new Dimension(10, 10);
            jpExecutionTime.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
            jpExecutionTime.add(jlExecutionTime, null);
        }
        return jpExecutionTime;
    }

    /**
     * This method initializes jpProcessors    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJpProcessors() {
        if (jpProcessors == null) {
            jlProcessorsIcon = new JLabel();
            jlProcessorsIcon.setText("");
            jlProcessorsIcon.setIcon(new ImageIcon(getClass().getResource("/pt/c03ensaios/naxxramas/images/processor.png")));
            jlProcessors = new JLabel();
            jlProcessors.setText("Number of processors: {x}");
            jpProcessors = new JPanel();
            jpProcessors.setLayout(new BoxLayout(getJpProcessors(), BoxLayout.X_AXIS));
            jpProcessors.add(jlProcessorsIcon, null);
            Dimension hFillerDimension = new Dimension(10, 10);
            jpProcessors.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
            jpProcessors.add(jlProcessors, null);
        }
        return jpProcessors;
    }

    /**
     * This method initializes jpEnquirerMemory    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJpEnquirerMemory() {
        if (jpEnquirerMemory == null) {
            jlEnquirerMemomry = new JLabel();
            jlEnquirerMemomry.setText("Memory used by enquirer: {x}");
            jlEnquirerMemomryIcon = new JLabel();
            jlEnquirerMemomryIcon.setText("");
            jlEnquirerMemomryIcon.setIcon(new ImageIcon(getClass().getResource("/pt/c03ensaios/naxxramas/images/memory.png")));
            jpEnquirerMemory = new JPanel();
            jpEnquirerMemory.setLayout(new BoxLayout(getJpEnquirerMemory(), BoxLayout.X_AXIS));
            jpEnquirerMemory.add(jlEnquirerMemomryIcon, null);
            Dimension hFillerDimension = new Dimension(10, 10);
            jpEnquirerMemory.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
            jpEnquirerMemory.add(jlEnquirerMemomry, null);
        }
        return jpEnquirerMemory;
    }

    /**
     * This method initializes jpJVMMemory    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJpJVMMemory() {
        if (jpJVMMemory == null) {
            jlJVMMemoryIcon = new JLabel();
            jlJVMMemoryIcon.setText("");
            jlJVMMemoryIcon.setIcon(new ImageIcon(getClass().getResource("/pt/c03ensaios/naxxramas/images/coffee.png")));
            jlJVMMemory = new JLabel();
            jlJVMMemory.setText("Total JVM used memory: {used}/{total}");
            jpJVMMemory = new JPanel();
            jpJVMMemory.setLayout(new BoxLayout(getJpJVMMemory(), BoxLayout.X_AXIS));
            jpJVMMemory.add(jlJVMMemoryIcon, null);
            Dimension hFillerDimension = new Dimension(10, 10);
            jpJVMMemory.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
            jpJVMMemory.add(jlJVMMemory, null);
        }
        return jpJVMMemory;
    }

    /**
     * This method initializes jpProgressBar  
     *  
     * @return javax.swing.JPanel   
     */
    private JPanel getJpProgressBar() {
        if (jpProgressBar == null) {
            jpbJVMMemory = new JProgressBar();
            jpbJVMMemory.setSize(new Dimension(100, 15));
            jpbJVMMemory.setPreferredSize(new Dimension(100, 15));
            jlPercentage = new JLabel();
            jlPercentage.setText("0%");
            jpProgressBar = new JPanel();
            jpProgressBar.setLayout(new BoxLayout(getJpProgressBar(), BoxLayout.X_AXIS));
            Dimension hFillerDimensionBig = new Dimension(50, 10),
                      hFillerDimension = new Dimension(10, 10);
            
            jpProgressBar.add(new Box.Filler(hFillerDimensionBig,
                    hFillerDimensionBig, hFillerDimensionBig));
            jpProgressBar.add(jpbJVMMemory, null);
            jpProgressBar.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
            jpProgressBar.add(jlPercentage, null);
            jpProgressBar.add(new Box.Filler(hFillerDimensionBig,
                    hFillerDimensionBig, hFillerDimensionBig));
        }
        return jpProgressBar;
    }

    public void loadInfo(double time, int numProcessors, long enquirerMemory, 
            long freeMemory, long totalMemory) {
        this.jlExecutionTime.setText("Execution time: " + time + " microseconds");

        this.jlProcessors.setText("Number of processors: " + numProcessors);

        this.jlEnquirerMemomry.setText("Memory used by enquirer: " +
            enquirerMemory + "kB");

        this.jlJVMMemory.setText("Total JVM used memory: " +
            (totalMemory - freeMemory) + "MB / " + totalMemory + "MB");

        int memoryPercentage = (int)(100 - freeMemory * 100 / totalMemory);
        this.jpbJVMMemory.setValue(memoryPercentage);
        this.jlPercentage.setText(memoryPercentage + "%");
        
    }
}
