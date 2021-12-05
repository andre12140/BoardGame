package boardGames.game.actions;

import boardGames.game.BoardGame_Scores;
import boardGames.game.Player;

import java.util.function.Predicate;

public class TileForEachAction  extends TileActionAdapter {
    private Predicate<Player> correctAnswer;
    private BoardGame_Scores bg;

    public TileForEachAction( Predicate <Player> correctAnswer, BoardGame_Scores game){
        this.correctAnswer= correctAnswer;
        bg = game;
    }
    @Override
    public String getName() {
        return "foreach";
    }

    @Override
    public void enter( Player p ) {
        int jump=0;
        while (!(correctAnswer.test( p ))){
            jump+=7;
            if((p.getPosition()-jump<0)){
                bg.movePlayer(p,-p.getPosition());
                return;
            }
        }
        bg.movePlayer(p,-jump);
    }

}
