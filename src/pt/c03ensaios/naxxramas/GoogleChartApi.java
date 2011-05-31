package pt.c03ensaios.naxxramas;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * Creates a 3D pie chart using Google's API.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class GoogleChartApi extends Thread implements Runnable {
    private static final long serialVersionUID = 1044637805980654532L;

    private JTabbedPane tabControl;
    private Slice slices[];
    private JPanel equal;
    
    public GoogleChartApi(JTabbedPane parent, Slice slices[], JPanel equal) {
        this.tabControl = parent;
        this.slices = slices;
        this.equal = equal;
    }
    
    @Override
    public void run() {
        try {
            for(Slice c: this.slices) {
                System.out.println(c.getPercentage());
            }
            // Create a URL for the image's location
            URL url = new URL("http://chart.apis.google.com/chart?chs=500x300" +
              "&cht=p3&chco=80C65A,FA6161,76A4FB&chd=t:" +
              this.slices[0].getPercentage() + "," +
              this.slices[1].getPercentage() + "," +
              this.slices[2].getPercentage() + 
              "&chdl=Animal's+asked+questions+("+this.slices[0].getValue()+")" +
              "|Other+asked+questions+("+this.slices[1].getValue()+")+" + 
              "|Non-asked+questions+("+this.slices[2].getValue()+")" +
              "&chtt=All+questions");
            
            try {
                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con =
                 (HttpURLConnection) url.openConnection();
                con.setRequestMethod("HEAD");
                if(con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return;
                }
            }
            catch (Exception e) {
               return;
            }

            Image image = Toolkit.getDefaultToolkit().createImage(url);

            JLabel imageHolder = new JLabel();
            imageHolder.setIcon(new ImageIcon(image));
            
            int index = this.tabControl.indexOfComponent(this.equal);
            this.tabControl.setComponentAt(index, imageHolder);
        } catch (MalformedURLException e) {
            return;
        } catch (NullPointerException ex) {
            return;
        } catch (Exception ex) {
            return;
        }
    }
}
