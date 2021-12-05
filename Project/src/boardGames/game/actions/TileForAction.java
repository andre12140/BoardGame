package boardGames.game.actions;

import boardGames.game.Player;

public class TileForAction  extends TileActionAdapter {
    @Override
    public String getName() {
        return "for";
    }

    @Override
    public boolean leave(Player p){
        if(p.tileForAux == 3){
            return true;
        }
        p.tileForAux++;
        return false;
    }
}

