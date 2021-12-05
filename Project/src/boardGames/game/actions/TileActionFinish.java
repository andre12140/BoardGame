package boardGames.game.actions;

import boardGames.game.BaseGame;
import boardGames.game.Player;

public class TileActionFinish extends TileActionAdapter {
	private BaseGame game;
	public TileActionFinish(BaseGame game) {
		this.game = game;
	}

	@Override
	public void enter( Player p ) {
		game.finish( p );
	}

	@Override
	public String getName()       {
		return "finish";
	}
}
