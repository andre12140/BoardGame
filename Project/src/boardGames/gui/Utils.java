package boardGames.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by msousa on 12/11/2018.
 */
public class Utils {
    public static JPanel box( Component c ) {
        JPanel box = new JPanel(  new FlowLayout( FlowLayout.LEFT ) );
        box.add( c );
        return box;
    }

    public static JLabel newJLabel( Dimension dim ) {
        JLabel l = new JLabel();
        l.setPreferredSize( dim );
        return l;
    }
}
