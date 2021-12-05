package boardGames.game;

/**
 * Defenição:
 *   - das ações a executar numa quadricula quando entra e quando sai.
 * Verifica se:
 *   - tem condições para sair
 *   - está retido
 */
public interface TileAction {
	/**
	 * Obter o nome da ação
	 * @return o nome da ação
     */
	String getName();

	/**
	 * Ações a executar quando o jogador entra na quadricula.
	 * @param p jogador que entrou na quadricula
     */
	void enter(Player p);

	/**
	 * Verifica se o jogador pode sair da quadricula e executa as
	 * ações correspondentes à saída.
	 * @param p jogador que quer sair da quadricula
	 * @return true caso o jogador possa sair, false caso contrário.
     */
	boolean leave(Player p);

	/**
	 * Verifica se o jogador joga no próximo turno.
	 * Caso o jogador não jogue durante N turnos, por cada vez que
	 * este método é chamado é considerado que o jogador não jogou
	 * UM turno.
	 * @param p jogador a verificar
	 * @return true se o jogador não está em jogo, false caso contrário.
     */
	boolean isRetainInTurn(Player p);
}