package pt.c03ensaios.afinder.animalpix;

import java.io.File;

public class ImageFileFilter extends javax.swing.filechooser.FileFilter
{
    String[] extensions;
    String description;

    public ImageFileFilter(String descr, String... exts)
    {
        extensions = new String[exts.length];
        for (int i = exts.length - 1; i >= 0; i--)
            extensions[i] = exts[i].toLowerCase();

        description = descr;
    }

    @Override
    public boolean accept(File f)
    {
    	boolean aceppt = false;

    	if (f.isDirectory())
              aceppt = true;
    	
    	for (String extension : extensions)
            if (f.getName().toLowerCase().endsWith(extension))
                return true;

        return aceppt;
    }

    @Override
    public String getDescription()
    {
        return description;
    }
}
