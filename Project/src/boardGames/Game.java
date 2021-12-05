package boardGames;

import boardGames.game.BoardGame_Scores;
import boardGames.game.Player;
import boardGames.gui.GameFrame;
import boardGames.gui.GameFrame_Scores;
import boardGames.gui.Icons;
import boardGames.questions.Question;
import boardGames.questions.Questions;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by msousa on 12/10/2018.
 */
public class Game {
    public static String PATHNAME_PINS = "images\\pins";
    public static Dimension PINS_DIM = new Dimension( 27, 44 );
    public static Object[] pinsIcons =
            Icons.getImagesIcons( PATHNAME_PINS, PINS_DIM ).values().toArray();
    public static ImageIcon getPinIcon(int id) {
        return (ImageIcon) pinsIcons[id];
    }

    public static String PATHNAME_TILE = "images\\tiles";
    public static Dimension TILE_IMAGE_DIM = new Dimension( 60, 60 );
    public static Map<String, ImageIcon> tilesImage = Icons.getImagesIcons( PATHNAME_TILE, TILE_IMAGE_DIM );
    public static ImageIcon getTileIcon( String name ) { return tilesImage.get( name ); }

    public static String PATHNAME = "images\\dices\\dice_face_";
    public static Dimension DICE_DIM = new Dimension( 50, 50 );
    private static ImageIcon[] dicesImage = Icons.getNumberIcons( PATHNAME, DICE_DIM, 6 );
    public static ImageIcon getDiceIcon( int id ) { return dicesImage[id]; }

    Function<Question, Player> show;
    public static void main(String[] args ) throws IOException {

        BoardGame_Scores game = new BoardGame_Scores( 2, 8,
                "Bart", "Lisa", "Hommer", "Marge", "Xpto");
        GameFrame_Scores f = new GameFrame_Scores("Jogo de tabuleiro",
                                     Game::getPinIcon,
                                     Game::getTileIcon,
                                     Game::getDiceIcon,
                                     TILE_IMAGE_DIM,
                                     game);

        game.setShowQuestion( f::showQuestion );
        f.setVisible( true );
    }
}
