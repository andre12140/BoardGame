package boardGames.gui;


import java.awt.*;

import java.util.function.IntFunction;

import javax.swing.*;

/**
 * Componente para visualizar os jogadores.
 * Cada player tem um nome e um pin.
 */
public class PinsPanel extends JPanel {
	private static final int NAME_LENGTH = 8;
	private static final int SEPARATOR = 5;

	private JPanel pins;
    /**
     * Método auxiliar para obter o componente que representa o pin
     * dado o identificador
     * @param id identificador do pin
     * @return o componente que representa o pin
     */
    private Pin getPinAt(int id ) {
        return (Pin) pins.getComponent(id);
    }

	/**
	 * Classe aninhada para visualizar UM Pin.
	 * Tem um nome e o pin que representa.
	 */
	public static class Pin extends JPanel {
		private JLabel pinComponent;
		private JTextField nameComponent;

		public Pin( Icon pin ) {
			pinComponent = new JLabel( pin );
			nameComponent = new JTextField( NAME_LENGTH );
			nameComponent.setEditable( false );
			nameComponent.setBorder( null );

            add( pinComponent );
			add( nameComponent );
		}
		
        public void setName( String name ) { nameComponent.setText( name );   }
		public void setPin( Icon pin )     { pinComponent.setIcon( pin );	    }
	}

	/**
	 * Construtor de um componente que contêm um determinado número de jogadores.
	 * @param title titulo do componente
	 * @param numberOfPins número de pins
     * @param getPinIcon função para obter a imagem de um determinado pin
	 */
	public PinsPanel(String title,
					 int numberOfPins,
					 IntFunction<ImageIcon> getPinIcon ) {
	    super( new BorderLayout(SEPARATOR, SEPARATOR));
        JLabel titleComponent = new JLabel( title );
		titleComponent.setFont( titleComponent.getFont().deriveFont(Font.BOLD|Font.ITALIC, 20F) );
		
		pins = new JPanel( new GridLayout( 0, 2, SEPARATOR, SEPARATOR ) );
		for (int i= 0; i < numberOfPins; ++i)
			pins.add( new Pin( getPinIcon.apply( i ) ) );
		
		add( titleComponent, BorderLayout.NORTH );
		add( pins, BorderLayout.SOUTH );
	}

	/**
	 * Alterar o nome dum determinado pin
	 * @param id identificação do pin
	 * @param name nome do pin
	 */
	public void setName(int id, String name ) { getPinAt( id ).setName( name ); }

	/**
	 * Alterar a imagem do pin
	 * @param id identificador do pin
	 * @param pin imagem a colocar
	 */
	public void setPin(int id, ImageIcon pin ){ getPinAt( id ).setPin( pin ); }

}

