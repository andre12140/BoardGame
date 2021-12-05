package boardGames.game.actions;

import boardGames.game.BoardGame_Scores;
import boardGames.game.Player;

import java.util.function.Predicate;

public class TileIfAction extends TileActionAdapter {
    private Predicate<Player> correctAnswer;
    private BoardGame_Scores bg;
    public TileIfAction( Predicate <Player> correctAnswer, BoardGame_Scores game){
        this.correctAnswer= correctAnswer;
        bg = game;
    }
    @Override
    public String getName() {
        return "if";
    }

    @Override
    public void enter( Player p ) {
        if(correctAnswer.test( p )){
            bg.movePlayer(p, 7);
        }
    }
}