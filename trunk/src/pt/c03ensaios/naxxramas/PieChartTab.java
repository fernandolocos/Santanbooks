package pt.c03ensaios.naxxramas;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * Creates the content for the chart tab in the Sapphiron component.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class PieChartTab extends JPanel {
    private static final long serialVersionUID = 5424733792418579776L;
    
    private PieChart chart2d;
    private JPanel jpChart;
    
    public PieChartTab(PieChart chart2d) {
        super();
        this.chart2d = chart2d;
        initialize();
    }
    
    private void initialize() {
        this.setSize(400, 300);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10),
            BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));

        Dimension vFillerDimension = new Dimension(10, 10);

        this.add(new Box.Filler(vFillerDimension,
                vFillerDimension, vFillerDimension));
        this.add(this.getJpChart(), null);
        this.add(new Box.Filler(vFillerDimension,
                vFillerDimension, vFillerDimension));
    }

    /**
     * This method initializes jpChart    
     *     
     * @return javax.swing.JPanel    
     */
    private JPanel getJpChart() {
        if (jpChart == null) {
            jpChart = new JPanel();
            jpChart.setLayout(new BoxLayout(getJpChart(), BoxLayout.X_AXIS));
            Dimension hFillerDimension = new Dimension(35, 10);
            jpChart.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
            this.chart2d.setAlignmentX(Component.CENTER_ALIGNMENT);
            jpChart.add(this.chart2d, null);
            jpChart.add(new Box.Filler(hFillerDimension,
                    hFillerDimension, hFillerDimension));
        }

        return jpChart;
    }
}