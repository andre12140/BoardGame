package boardGames.game.actions;

import boardGames.game.Player;

public class TileForeverAction  extends TileActionAdapter {
    private Player playerPreso;

    @Override
    public String getName() {
        return "forever";
    }

    @Override
    public void enter( Player p ) {
        playerPreso = p;
    }

    @Override
    public boolean leave(Player p){
        return playerPreso.getId() != p.getId();
    }
}
