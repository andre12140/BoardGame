package boardGames.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.IntFunction;
import javax.swing.*;

/**
 * Componente para visualizar os dados.
 * Contem:
 *  - Um titulo
 *  - Os dados
 *  - Um botão ao qual pode ser adicionada uma ação através
 *    do método addActionListener.
 */
public class DicesPanel extends JPanel {
	private static final int SEPARATOR = 5;
	private Box dicesPanel;
	private JButton rollButton;
	private IntFunction<ImageIcon> getDiceIcon;
	/**
	 * Construtor de um componente que contêm um determinado número de dados.
	 * Inicialmente os dados são todos colocados com o valor um.
	 * @param title titulo do componente
	 * @param numberOfDices número de dados
	 * @param getDiceIcon função para obter a imagem de um determinado valor de face
	 */
	public DicesPanel(String title, int numberOfDices, IntFunction<ImageIcon> getDiceIcon) {
		super( new BorderLayout(SEPARATOR, SEPARATOR));
		this.getDiceIcon = getDiceIcon;

		JLabel titleLabel = new JLabel( title );
		titleLabel.setFont( titleLabel.getFont().deriveFont(Font.BOLD|Font.ITALIC, 20F) );

		dicesPanel = Box.createHorizontalBox();
		Icon dice1 = getDiceIcon.apply( 1 );
		for (int n = 0; n < numberOfDices; ++n) 
			dicesPanel.add( new JLabel( dice1 ) );

		rollButton = new JButton( "roll" );
		
		add( titleLabel, BorderLayout.NORTH );
		add( dicesPanel, BorderLayout.CENTER);
		add( rollButton, BorderLayout.SOUTH );
	}

	/**
	 * Adicionar uma ação quando é premido o botão "rool".
	 * @param rollListener acão a adicionar
     */
	public void addActionListener( ActionListener rollListener ) {
		rollButton.addActionListener( rollListener );
	}

	/**
	 * Alterar o valor de determinado dado
	 * @param diceNumber número do dado a alterar o valor
	 * @param value valor que deve ser colocado na face do dado
     */
	public void setDiceValue( int diceNumber, int value ) {
		getDiceAt( diceNumber ).setIcon( getDiceIcon.apply(value) );
	}

	/**
	 * Método auxiliar para obter o componete que representa o dado dado o número do dado
	 * @param diceNumber número do dado
	 * @return o componente que representa o dado
	 */
	private JLabel getDiceAt( int diceNumber ) {
		return (JLabel)dicesPanel.getComponent( diceNumber );
	}
}

