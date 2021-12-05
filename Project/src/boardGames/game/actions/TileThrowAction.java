package boardGames.game.actions;

import boardGames.game.BoardGame_Scores;
import boardGames.game.Player;

import java.util.function.Predicate;

public class TileThrowAction extends TileActionAdapter {
    private Predicate<Player> correctAnswer;
    private BoardGame_Scores bg;

    public TileThrowAction( Predicate <Player> correctAnswer, BoardGame_Scores game){
        this.correctAnswer= correctAnswer;
        bg = game;
    }
    @Override
    public String getName() {
        return "throw";
    }

    @Override
    public void enter( Player p ) {
        if(!(correctAnswer.test( p ))){
            bg.movePlayer(p,-p.getPosition());
        }
    }
}
