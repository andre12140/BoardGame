package boardGames.gui;

import java.awt.*;
import java.util.function.IntFunction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import static boardGames.gui.Utils.box;
import static boardGames.gui.Utils.newJLabel;

/**
 * Componente para visualizar o tabuleiro de jogo.
 * Contém uma matriz quadrada de quadrículas (componentes TilePanel).
 */
public class TilesPanel extends JPanel {
	public static Dimension PINS_DIM = new Dimension( 9, 15 );
	private static ImageIcon[] pinsIcon;
	/**
	 * Classe aninhada para visualizar UMA quadrícula.
	 * Uma quadrícula tem:
	 *   - um número no canto direito
	 *   - os pinos dos jogadores que se encontram na quadícula - no fundo
	 *   - uma imagem ao centro.
	 */
	public static class Tile extends JPanel {
		private JLabel imageComponent;
		private JPanel pinsPanel;
		public Tile(int number,	Dimension tileDim, int numberOfPins ){
			super( new BorderLayout()  );
			setBorder( new EtchedBorder( EtchedBorder.LOWERED ) );

			JLabel numberComponent = new JLabel( number + "" );

			imageComponent = newJLabel( tileDim );

			pinsPanel = new JPanel( new GridLayout( 1, 0) );
			pinsPanel.setBorder( new EmptyBorder(3,2,2,2));
			for ( int i= 0; i < numberOfPins; ++i)
				pinsPanel.add( newJLabel( PINS_DIM ) );

			add( box(numberComponent), BorderLayout.EAST );
			add( imageComponent, BorderLayout.CENTER );
			add( pinsPanel, BorderLayout.SOUTH );
		}

		public void removePin( int pinId ) { getPinAt( pinId ).setIcon( null );          }
		public void putPin( int pinId )    { getPinAt( pinId ).setIcon( pinsIcon[pinId] ); }

		public void changeImageTo( Icon icon ) {
			imageComponent.setIcon( icon );
		}

		private JLabel getPinAt(int index ) {
			return (JLabel)pinsPanel.getComponent( index );
		}

	}

	/**
	 * Construtor de um tabuleiro de jogo quadrado.
	 * @param numberOfLinesColumns número de linhas e quadriculas por linha
	 * @param tileDim  Dimensaão de cada quadricula.
	 * @param numberOfPins número de pinos
	 * @param getPinIcon função para obter a imagem de um determinado pin
     */
    public TilesPanel(int numberOfLinesColumns, Dimension tileDim,
                      int numberOfPins, IntFunction<ImageIcon> getPinIcon ) {
		pinsIcon = new ImageIcon[ numberOfPins ];
		for ( int i= 0; i < pinsIcon.length; ++i )
			pinsIcon[i] = new ImageIcon( Icons.scaleImage( getPinIcon.apply( i ), PINS_DIM ) );
		setLayout( new GridLayout( numberOfLinesColumns, numberOfLinesColumns ) );
	    for ( int i = 0; i < numberOfLinesColumns*numberOfLinesColumns; ++i )
			add( new Tile( i, tileDim, numberOfPins ) );
	}

	/**
	 * Colocar em determinada quadricula uma imagem.
	 * @param tileNumber número da quadricula a colocar a imagem
	 * @param icon imagem a colocar
     */
	public void setImage( int tileNumber, Icon icon ) {
		if ( isValidPostion(tileNumber) )
			getTileAt(tileNumber).changeImageTo(icon );
	}

	/**
	 * Retirar duma determinada quadricula um determinado pin.
	 * @param tileNumber número da quadricula a retirar o pin
	 * @param pinNumber número do pin a retirar
     */
	public void removePin( int tileNumber, int pinNumber ) {
    	if ( isValidPostion(tileNumber) )
			getTileAt(tileNumber).removePin( pinNumber );
	}
	/**
	 * Colocar numa determinada quadricula um determinado pin.
	 * @param tileNumber número da quadricula a colocar o pin
	 * @param pinNumber número do pin a colocar
	 */
	public void putPin( int tileNumber, int pinNumber )   {
		if ( isValidPostion( tileNumber ) )
			getTileAt(tileNumber).putPin( pinNumber );
	}

	/**
	 * Método auxiliar para obter o componete que representa a quadricula dado o número da quadricula
	 * @param tileNumber número da quadricula
	 * @return o componente que representa a quadricula
     */
	private Tile getTileAt( int tileNumber ) {
		return (Tile)getComponent( tileNumber );
	}

	/**
	 * Método auxiliar para verificar se existe a quadricula
	 * @param tileNumber número da quadricula
	 * @return true caso a quadicula exista
	 */
	private boolean isValidPostion( int tileNumber ) {
		return tileNumber>= 0 &&  tileNumber < getComponentCount();
	}
}

