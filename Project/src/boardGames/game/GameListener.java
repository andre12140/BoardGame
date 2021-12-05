package boardGames.game;

/**
 * Definição das ações a executar quando qualquer elemento do jogo muda, tal como:
 *   - uma quadricula.
 *   - o valor dum dado.
 *   - o jogador que vai jogar a proxima jogada.
 *   - o nome de um jogador.
 *   - o jogador terminou o jogo.
 *   - o jogador entra ou sai duma quadicula.
 */
public interface GameListener {
	/**
	 * A imagem da quadrícula cujo número é o identificador do Tile foi alterada.
	 * @param t objecto que representa a quadricula a mudar.
     */
	void tileImageChanged( Tile t );
	/**
	 * O valor do dado cujo número é o número do Dice foi alterado.
	 * @param d objecto que representa a dado que mudou de valor.
	 */
	void diceValueChanged( Dice d );
	/**
	 * O jogador corrente foi alterado.
	 * @param currentPlayer objecto que representa o novo jogador corrente.
	 */
	void playerFocusGained ( Player currentPlayer );
	/**
	 * O nome do jogador cujo número é o identificador do Player foi alterado.
	 * @param p objecto que representa a jogador que mudou de nome.
	 */
	void playerNameChanged( Player p );
	/**
	 * Saiu da quadricula um jogador.
	 * @param p objecto que representa o jogador que saiu da quadricula.
	 */
	void playerExited( Player p );
	/**
	 * Entrou na quadricula um jogador.
	 * @param p objecto que representa o jogador que entrou da quadricula.
	 */
	void playerEntered( Player p );
	/**
	 * Um jogador terminou o jogo.
	 * @param game objecto que representa o jogo.
	 */
	void finishPlayer( BaseGame game );
}
