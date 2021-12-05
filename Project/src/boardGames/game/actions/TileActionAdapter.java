package boardGames.game.actions;

import boardGames.game.Player;
import boardGames.game.TileAction;

/**
 * Classe que cont�m uma implementa��o por omiss�o para todos os m�todos.
 * Classes que a estendem s� necessitam de definir os m�todos cuja
 * implementa��o n�o � a por omissao:
 *   - t�m que ser executadas determinadas a��es quando entra.
 *   - o jogador s� pode sair se se verificarem determinadas condi��es,
 *     ou se pretenda executar a��es na sa�da.
 *   - o jogador fique sem jogar em determinadas condi��es.
 */
public abstract class TileActionAdapter implements TileAction {
	@Override
	public void enter(Player p) { }

	@Override
	public boolean leave(Player p)  { return true;  }

	@Override
	public boolean isRetainInTurn(Player p) { return false;	}
}
