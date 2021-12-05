package boardGames.game.actions;

import boardGames.game.BaseGame;
import boardGames.game.Player;
import boardGames.game.TileAction;
import boardGames.game.BoardGame_Scores;

import java.util.function.Function;
import java.util.function.Predicate;

public class TilePerguntaAction extends TileActionAdapter{
    private Predicate<Player> correctAnswer;
    private BoardGame_Scores bg;

    public TilePerguntaAction( Predicate <Player> correctAnswer, BoardGame_Scores game){
        this.correctAnswer= correctAnswer;
        bg = game;
    }
    @Override
    public String getName() {
        return "prog";
    }

    @Override
    public boolean leave( Player p) {
        if(bg.getPlayers().get(p.getId()).tilePerguntaAux || correctAnswer.test( p )) {
            bg.getPlayers().get(p.getId()).tilePerguntaAux = false;
            return true;
        }
        bg.getPlayers().get(p.getId()).tilePerguntaAux = true;
        return false;
    }
}
