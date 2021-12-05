package boardGames.gui;

import java.awt.*;
import java.util.function.Function;
import java.util.function.IntFunction;

import javax.swing.*;

import boardGames.game.*;
import boardGames.questions.Question;

import static boardGames.gui.Utils.box;

/**
 * Janela para visualizar um jogo de tabuleira. Contém:
 *   - o tabuleiro de jogo no centro
 *   - os dados
 *   - os jogadores
 *   - o jogador corrente
 */
public class GameFrame extends JFrame implements GameListener {

	private IntFunction<ImageIcon> getPinIcon;
	private Function<String, ImageIcon> getTileIcon;
	private IntFunction<ImageIcon> getDiceIcon;

	protected TilesPanel boardPanel;
	protected DicesPanel dicesPanel;
	protected PinsPanel players;
	protected PinsPanel currentPlayer;
	
	public GameFrame(String title,
                     IntFunction<ImageIcon> getPinIcon,
					 Function<String, ImageIcon> getTileIcon,
					 IntFunction<ImageIcon> getDiceIcon,
					 Dimension tileImageDim,
                     BaseGame game ) {
		super( title );

		setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );

		this.getPinIcon = getPinIcon;
		this.getDiceIcon = getDiceIcon;
		this.getTileIcon = getTileIcon;
		Container cp = getContentPane();

		boardPanel = new TilesPanel( game.numberOfTilesPerLine(), tileImageDim,
									 game.numberOfPlayers(), getPinIcon );
		cp.add( boardPanel, BorderLayout.CENTER);
		
		dicesPanel = new DicesPanel( "Dices", game.numberOfDices(), getDiceIcon );
		dicesPanel.addActionListener( (e) -> game.roll() );
		
		players = new PinsPanel( "All Players", game.numberOfPlayers(), getPinIcon );
		currentPlayer = new PinsPanel( "Current Player", 1, getPinIcon );
				
		Box infoPanel = Box.createVerticalBox();
		infoPanel.add( box( dicesPanel ));
		infoPanel.add( Box.createVerticalStrut( 10 ));
		infoPanel.add( players );
		infoPanel.add( Box.createVerticalStrut( 10 ));
		infoPanel.add( currentPlayer );
		cp.add( box(infoPanel), BorderLayout.EAST );

		game.addGameListener( this );
		
		pack();
	}

	/*
	 * Métodos que implementam a interface GameListener.
     */

	@Override
	public void tileImageChanged( Tile t ) {
		int tilePosition= t.getNumber();
		boardPanel.setImage( tilePosition, getTileIcon.apply( t.getName() ) );
	}

	@Override
	public void playerEntered( Player p ) {
		boardPanel.putPin( p.getPosition(), p.getId() );
	}

	@Override
	public void playerExited( Player p ) {
		boardPanel.removePin( p.getPosition(), p.getId() );
	}

	@Override
	public void playerFocusGained( Player p ) {
		currentPlayer.setPin( 0, getPinIcon.apply( p.getId() ));
		currentPlayer.setName( 0, p.getName() );
	}

	@Override
	public void playerNameChanged(Player p) {
		players.setName( p.getId(), p.getName() );
	}

	@Override
	public void diceValueChanged(Dice d) {
		dicesPanel.setDiceValue(d.getNumber(), d.getValue());
	}

	@Override
	public void finishPlayer( BaseGame game ) {
		StringBuilder list = new StringBuilder();
		game.getRanking().forEach( (p) -> list.append( p + "\n" ) );
		JOptionPane.showMessageDialog(this, list, "Players", JOptionPane.INFORMATION_MESSAGE);
	}

	public int showQuestion( Question q, Player p ) {
		return JOptionPane.showOptionDialog (
				this, q.question(),
				"Questão para o jogador " + p.getName(),
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
				getPinIcon.apply( p.getId() ), q.answers(), null);
	}
}
