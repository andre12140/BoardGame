package boardGames.game.actions;

import boardGames.game.Player;
import boardGames.game.TileAction;

import java.util.function.Predicate;

/**
 * Created by msousa on 12/14/2018.
 */
public class TileWhileAction extends TileActionAdapter{
    private Predicate<Player> correctAnswer;

    public TileWhileAction( Predicate <Player> correctAnswer ){
        this.correctAnswer= correctAnswer;
    }
    @Override
    public String getName() {
        return "while";
    }

    @Override
    public boolean leave( Player p ) {
        return correctAnswer.test( p );
    }
}
