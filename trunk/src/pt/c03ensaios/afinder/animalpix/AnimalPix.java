package pt.c03ensaios.afinder.animalpix;

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import anima.annotation.Component;
import anima.component.base.ComponentBase;

@Component(id="<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.AnimalPix>",
        provides="<http://purl.org/dcc/pt.c03ensaios.afinder.animalpix.IAnimalPix>")
public class AnimalPix extends ComponentBase implements IAnimalPix{

	private static final long serialVersionUID = 6501384377734203873L;
	private Image img;
	private static final String ROOT = System.getProperty("user.dir") + "/pt/c03ensaios/afinder/animalpix/",
								DIRECTORY = "imagens/",
								EXTENSION = ".jpg",
								NOIMAGE = "notfound";
	
	public boolean hasImage (String animal){
		boolean imageFound = true;
		URL url = getClass().getResource(DIRECTORY + animal + EXTENSION);
		if (url == null)
			imageFound = false;
		
		return imageFound;
	}
	
	public Image getImage(String animal){
		img = null;
		URL url = getClass().getResource(DIRECTORY + animal + EXTENSION);
		if(url == null){
			url = getClass().getResource(DIRECTORY + NOIMAGE + EXTENSION);
		}
		if (url != null) { 
			img = new ImageIcon(url).getImage();
			MediaTracker tracker = new MediaTracker(new Container()
			);
            tracker.addImage(img,0);
            try{tracker.waitForID(0);}
            catch (InterruptedException e){}
			int imgH = img.getHeight(null), imgW = img.getWidth(null);
			
			if (imgH > 600 || imgW > 600) {
				if (imgH > imgW)
					img = img.getScaledInstance(-1, 600, Image.SCALE_SMOOTH);
				else
					img = img.getScaledInstance(600, -1, Image.SCALE_SMOOTH);
				tracker = new MediaTracker(new Container()
				);
	            tracker.addImage(img,0);
	            try{tracker.waitForID(0);}
	            catch (InterruptedException e){}
			}
		}
		return img;
	}
	
	public void showImageWindow(String animal) {
		new APixWindow(animal);
	}
	
	public boolean setImageFromDisk(String source, String animal) {
		boolean picAdded = true;
					
		try {
			File fileImage = new File(source);
			BufferedImage bufferedImage = ImageIO.read(fileImage);
			ImageIO.write(bufferedImage, "jpg", new File(ROOT + DIRECTORY + animal.toLowerCase() + EXTENSION));
    	} catch (Exception error){
    		picAdded = false;
    	}
    	
    	return picAdded;
	}
	
	public boolean setImageFromWeb(String source, String animal) {
		boolean picAdded = true;
		URL url;
				
		try {
			url = new URL(source);
			BufferedImage bufferedImage = ImageIO.read(url);
			ImageIO.write(bufferedImage, "jpg", new File(ROOT + DIRECTORY + animal.toLowerCase() + EXTENSION));
		} catch (IOException error) {
			picAdded = false;
		}
		
    	return picAdded;
	}
}