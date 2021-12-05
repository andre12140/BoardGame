package boardGames.game.actions;

import boardGames.game.Player;
import boardGames.game.TileAction;

/**
 * Classe que contém uma implementação por omissão para todos os métodos.
 * Classes que a estendem só necessitam de definir os métodos cuja
 * implementação não é a por omissao:
 *   - têm que ser executadas determinadas ações quando entra.
 *   - o jogador só pode sair se se verificarem determinadas condições,
 *     ou se pretenda executar ações na saída.
 *   - o jogador fique sem jogar em determinadas condições.
 */
public abstract class TileActionAdapter implements TileAction {
	@Override
	public void enter(Player p) { }

	@Override
	public boolean leave(Player p)  { return true;  }

	@Override
	public boolean isRetainInTurn(Player p) { return false;	}
}
