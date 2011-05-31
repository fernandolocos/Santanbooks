package pt.c03ensaios.naxxramas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * Creates a 2D pie chart using Java's drawing tools.
 *
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class PieChart extends JComponent {
    private static final long serialVersionUID = 1L;
    private Slice slices[];
    private Dimension dimension;
    private String title;
    private int arcs[];

    public PieChart(Slice[] slices, Dimension size, String title) {
        this.slices = slices;
        this.dimension = size;
        this.arcs = this.calculateArcs();
        this.title = title;
    }

    private int[] calculateArcs() {
        int totalArc = 0, slicesNumber = this.slices.length,
            arcs[] = new int[slicesNumber];

        for (int i = 0; i < slicesNumber; ++i) {
            arcs[i] = (int)(this.slices[i].getPercentage() * 360 / 100);
            totalArc += arcs[i];
        }
        
        if (totalArc < 360) {
            int fix = 360 - totalArc;
    
            for (int i = 0; fix > 0;) {
                if (arcs[i] != 0) {
                    arcs[i] += 1;
                    --fix;
                }
    
                i = i == slicesNumber - 1 ? 0 : i + 1;
            }
        }
        
        return arcs;
    }

    @Override
    public void paint(Graphics g) {
         int startAngle = 0, slicesNumber = this.slices.length,
             y = 70, w = (int)this.dimension.getWidth(),
             h = (int)this.dimension.getHeight();

        g.setColor(Color.BLACK);
        Font current = this.getFont();

        g.setFont(new Font(current.getName(), Font.BOLD, current.getSize() + 5));
        g.drawString(this.title, (int)w / 2, 20);
        g.setFont(current);

        for (int i = 0; i < slicesNumber; ++i) {
            int arc = arcs[i];
            Slice s = this.slices[i];

            g.setColor(s.getColor());

            if (arc != 0) {
                g.fillArc(0, 40, w, h, startAngle, arc);
                startAngle += arc;
            }

            g.fillRect(w + 20, y, 10, 10);
            g.setColor(Color.BLACK);
            g.drawString(
                s.getLabel() + " (" + s.getValue() + ")",
                w + 35, y + 10);
            y += 40;
        }
    }
}