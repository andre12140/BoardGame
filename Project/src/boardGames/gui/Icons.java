package boardGames.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class Icons {

	public static Image scaleImage( ImageIcon icon, Dimension d ) {
		Image img = icon.getImage();
		img = img.getScaledInstance((int)d.getWidth(),
				                    (int)d.getHeight(),
				                     Image.SCALE_AREA_AVERAGING);
		return img;
	}

	public static ImageIcon makeIcon( String filename,
									  Dimension d, String description ) {
		ImageIcon icon= new ImageIcon( filename );
		icon.setImage( scaleImage( icon, d ) );
		icon.setDescription( description );
		return icon;
	}
	
	public static Map<String, ImageIcon> getImagesIcons( String dir,
														 Dimension dim ) {
		Map<String, ImageIcon> icons = new HashMap<>();
		File f = new File( dir );
		if ( f.list() == null ) return icons;
		for ( String fn: f.list() ) {
			String description = fn.substring(fn.indexOf('_')+1, fn.lastIndexOf('.')).toLowerCase();
			icons.put(description, makeIcon( dir + "\\" + fn, dim, description));
		}
		return icons;
	}

	public static ImageIcon[] getNumberIcons(String pathname,
											 Dimension dim,
											 int numberOfIcons ) {
		ImageIcon [] icons = new ImageIcon[ numberOfIcons+1 ];
		for (int i=1; i < icons.length; ++i )
			icons[i] = makeIcon( pathname + i + ".png", dim, i + "" );
		return icons;
	}

}
