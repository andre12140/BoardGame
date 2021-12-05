package boardGames.game;

/**
 * Defeni��o:
 *   - das a��es a executar numa quadricula quando entra e quando sai.
 * Verifica se:
 *   - tem condi��es para sair
 *   - est� retido
 */
public interface TileAction {
	/**
	 * Obter o nome da a��o
	 * @return o nome da a��o
     */
	String getName();

	/**
	 * A��es a executar quando o jogador entra na quadricula.
	 * @param p jogador que entrou na quadricula
     */
	void enter(Player p);

	/**
	 * Verifica se o jogador pode sair da quadricula e executa as
	 * a��es correspondentes � sa�da.
	 * @param p jogador que quer sair da quadricula
	 * @return true caso o jogador possa sair, false caso contr�rio.
     */
	boolean leave(Player p);

	/**
	 * Verifica se o jogador joga no pr�ximo turno.
	 * Caso o jogador n�o jogue durante N turnos, por cada vez que
	 * este m�todo � chamado � considerado que o jogador n�o jogou
	 * UM turno.
	 * @param p jogador a verificar
	 * @return true se o jogador n�o est� em jogo, false caso contr�rio.
     */
	boolean isRetainInTurn(Player p);
}