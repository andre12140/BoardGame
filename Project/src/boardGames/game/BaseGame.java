package boardGames.game;

import boardGames.game.actions.TileActionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.IntUnaryOperator;

/**
 * Classe que implementa um jogo de tabuleiro.
 * O jogo � constitu�do por um tabuleiro quadrado, dados e no m�ximo 5 jogadores.
 * As QUADRICULAS s�o de diferentes tipos e numeradas a partir do 0.
 * Os DADOS t�m 6 faces, com valores de 1 a 6.
 * O jogo desenvolvesse por TURNOS. Em cada turno o jogador atual lan�a os dados e avan�a o
 * n�mero de quadriculas correspondente � soma do valor dos dados.
 * � poss�vel que v�rios jogadores partilhem a mesma quadricula.
 * O tipo da QUADRICULA em que se encontra e a de destino determina a a��o a aplicar ao jogador.
 *
 * No in�cio do jogo os jogadores encontram-se todos na quadricula 0.
 * Quando um jogador termina o jogo � colocado numa lista dos que j� terminaram.
 */
public abstract class BaseGame implements GameListener {
    public static TileActionAdapter NO_ACTION = new TileActionAdapter() {
        public String getName() { return "empty"; }
    };

    /**
     * Classe interna para representar cada quadricula. Cont�m
     * - O n�mero da Quadricula
     * - A a��o a executar caso um jogador entre,
     *   saia ou esteja retido na quadricula.
     */
    protected class GameTile implements Tile {
        private final int id;
        private TileAction action = NO_ACTION;

        public GameTile(int id) {
            this.id = id;
        }

        public int getNumber() {
            return id;
        }

        public String getName() {
            return action.getName();
        }

        public void setAction(TileAction a) {
            this.action = a;
        }

        public boolean isRetain(Player player) {
            return action.isRetainInTurn(player);
        }

        public boolean playerLeave(Player player) {
            if (action.leave(player)) {
                playerExited(player);
                return true;
            }
            return false;
        }

        public void playerEnter(Player player) {
            playerEntered(player);
            action.enter(player);
        }
    }

    public static final int MAX_OF_PLAYERS = 5;
    /**
     * Vari�veis de inst�ncia da classe BoardGame_Scores
     */
    public GameTile[] tiles; //private
    private int numberOfTilesPerLine;
    private int numberOfTiles;
    private Dice[] dices;
    private Player[] players;
    protected List<GameListener> listeners = new ArrayList<>();

    // Var�veis de UM jogo
    protected Player currentPlayer;
    protected int playersInTurn;
    protected final List<Player> finishPlayers = new ArrayList<>();

    /**
     * Inicia o tabuleiro quadrado dada a dimens�o do quadrado, o n�mero de dados a rolar,
     * e os 5 primeiros jogadores.
     *
     * @param numberOfDices        n�mero de dados a rolar.
     * @param numberOfTilesPerLine dimensa� do quadrado.
     * @param playerNames          nomes dos jogadores.
     */
    public BaseGame(int numberOfDices, int numberOfTilesPerLine, int tilesExtra, String ... playerNames) {
        this.numberOfTilesPerLine = numberOfTilesPerLine;
        this.numberOfTiles = numberOfTilesPerLine*numberOfTilesPerLine;
        makeDices(numberOfDices);
        makeTiles(numberOfTiles+tilesExtra);
        makePlayers(playerNames);
    }

    public final int numberOfDices()   {
        return dices.length;
    }

    public final int numberOfPlayers() {
        return players.length;
    }

    public final int numberOfTilesPerLine() {
        return numberOfTilesPerLine;
    }

    public final int numberOfTiles() {
        return numberOfTiles;
    }

    public Collection<Player> getRanking() {
        return finishPlayers;
    }

    public List<Player> getPlayers() {
        return Arrays.asList(players);
    }

    /**
     * Adiciona um listener.
     * Informa o listener:
     * - quais s�o as imagens que est�o nas quadriculas
     * - quais s�o os jogadores que est�o em jogo
     * - quais s�o os valores que est�o nos dados
     * - qual � o jogador corrente
     *
     * @param l listener que quer ser informado de todas as modifica��es que est�o
     *          a ocorrer no jogo
     */
    public void addGameListener(GameListener l) {
        listeners.add(l);
        for (Dice d : dices)
            l.diceValueChanged(d);
        for (GameTile t : tiles)
            l.tileImageChanged(t);
        for (Player p : players) {
            l.playerNameChanged(p);
            l.playerEntered(p);
        }
        l.playerFocusGained(currentPlayer);
    }

    /**
     * Caso esteja no fim do jogo troca o jogador cujo nome � passado por par�metro para outro jogador.
     * Procura o jogador cujo nome � passado por par�metro, troca o jogador, e
     * informa os listeners desta mudan�a.
     *
     * @param name    nome do jogador a mudar
     * @param newName nome do novo jogador
     */
    public void changePlayer( String name, String newName ) {
        if ( playersInTurn != 0 ) return;
        Player p = getPlayer( name );
        if ( p!= null ) {
            players[ p.getId() ] = p = getPlayer(p.getId(), newName );
            playerNameChanged( p );
            if ( p.getId() == currentPlayer.getId() ) {
                currentPlayer = p;
                playerFocusGained(p);
            }
        }
    }

    /**
     * Executa as a��es duma jogada:
     * - Rodar os dados.
     * - Pedir ao jogador corrente para se mover para a nova quadricula.
     * - Obter o jogador que jogar� na pr�xima jogada(jogador corrente).
     */
    public void roll() {
        if (finishPlayers.size() == players.length) return;
        // Rolar os dados
        int jump = rollDices();

        // Mover o jogador corrente
        movePlayer(currentPlayer, jump);

        // Actualizar o jogador corrente
        turnPlayer();
    }

    /**
     * Chamado sempre que se quer mover o jogador de um determinado n�mero de posi��es.
     * � obtida a posi��o da quadricula para onde o jogador se deve mover e caso seja poss�vel
     * o salto � pedido ao jogador para se mover para a nova quadricula.
     * O JOGADOR N�O SE PODE MOVER caso a posi��o j� n�o se encontre no tabuleiro de jogo ou
     * o jogador fique impossibilitado de terminar o jogo (tendo em conta o n�mero minimo
     * do salto - todos os dados a UM).
     *
     * @param player jogador a mover
     * @param jump   n�mero de posi��es a mover
     */
    public boolean movePlayer(Player player, int jump) {
        int from = player.getPosition();
        int to = from + jump;
        if (isValidPosition(to)) {
            if (tiles[from].playerLeave(player)) {
                player.moveTo(to);
                tiles[to].playerEnter(player);
            }
            return true;
        }
        return false;
    }

    /**
     * Chamado sempre que um jogador termina o jogo.
     * O jogador �:
     * - adicionado � lista dos jogadores que terminaram;
     * - retirado dos jogadores que est�o a jogar;
     * - informado que terminou e a posi��o em que terminou;
     * - actualizado o points.
     * Informa os listeners que o jogadpor terminou o jogo.
     *
     * @param p jogador que terminou o jogo
     */
    public void finish( Player p ) {
        finishPlayers.add(p);
        --playersInTurn;
        int ranking = finishPlayers.size();
        p.finish( ranking, getPointsOfRanking() );
        finishPlayer(this );
    }

    /*
     * Tiles methods
     */
    private void makeTiles( int numberOfTiles ) {
        tiles = new GameTile[numberOfTiles];
        for (int i = 0; i < tiles.length; ++i)
            tiles[i] = new GameTile(i);
    }

    protected final void setAction(int tileIndex, TileAction a) {
        tiles[tileIndex].setAction(a);
        tileImageChanged( tiles[tileIndex] );
    }

    /*
	 * Dices methods
     ****************/
    private void makeDices(int numberOfDice) {
        dices = new Dice[numberOfDice];
        for (int i = 0; i < dices.length; ++i)
            dices[i] = new Dice(i);
    }

    /**
     * Rola todos os dados e soma os novos valores.
     */
    protected int rollDices() {
        int sum = 0;
        for (Dice dice : dices) {
            sum += dice.rool();
            diceValueChanged(dice);
        }
        return sum;
    }

    /*
	 * Players methods
     ****************/
    private void makePlayers(String[] playerNames) {
        players = new Player[Math.min(playerNames.length, MAX_OF_PLAYERS)];
        for (int i = 0; i < players.length; ++i)
            players[i] = getPlayer(i, playerNames[i]);
        playersInTurn=players.length;
        currentPlayer = players[0];
    }

    /**
     * Procura o jogador que deve jogar a pr�xima jogada.
     */
    private void turnPlayer() {
        if (playersInTurn == 0) return;
        do {
           int currentPlayerIndex = (currentPlayer.getId() + 1) % players.length;
           currentPlayer = players[currentPlayerIndex];
        } while ( !currentPlayer.inTurn() ||
                  (tiles[currentPlayer.getPosition()].isRetain(currentPlayer) && playersInTurn!=1) );
        playerFocusGained(currentPlayer);
        if ( playersInTurn == 1 )
            finish( currentPlayer );
    }

    /**
     * Obter o  jogador dado o nome.
     * @param playerName no do jogador
     * @return o jogador com nome playerNome ou null caso n�o exista.
     */
    protected final Player getPlayer( String playerName ) {
        for ( Player p: players )
            if (p.getName().equalsIgnoreCase(playerName))
                return p;
        return null;
    }

    /**
     * M�todos que devem ser redefinidos caso se queira acrescentar a��es ou modificar o
     * comportamento:
     ****************/
    protected abstract boolean isValidPosition(int to);

    protected abstract IntUnaryOperator getPointsOfRanking( );

    protected abstract void putActions();

    protected abstract Player getPlayer(int i, String n);
    /**
     * M�todo que informam os LISTENERS de MUDAN�AS nos elementos do JOGO:
     * - QUADRICULA
     * - JOGADOR
     * - DADO
     ****************/

    /**
     * M�todos que DEVEM ser invocados SEMPRE que o jogador SAI ou ENTRA
     * na quadricula, GANHA o focus, MUDA de nome ou TERMINA o jogo.
     * Para que os listeners sejam informados.
     *
     * @param player jogador que saiu ou entrou
     */
    @Override
    public void playerExited(Player player) {
        for (GameListener v : this.listeners)
            v.playerExited(player);
    }

    @Override
    public void playerEntered(Player player) {
        for (GameListener v : listeners)
            v.playerEntered(player);
    }

    @Override
    public void playerFocusGained(Player p) {
        for (GameListener v : listeners)
            v.playerFocusGained(p);
    }

    @Override
    public void playerNameChanged(Player p) {
        for (GameListener l : listeners) {
            l.playerNameChanged(p);
            if ( p.getId() == currentPlayer.getId() ) {
                currentPlayer= p;
                l.playerFocusGained(p);
            }
        }
    }

    /**
	 * M�todo que DEVE ser invocado SEMPRE QUE O VALOR DE UM DADO � ALTERADA,
	 * Para que os listeners sejam informados.
	 * @param dice dado que foi alterada
	 */
	public void diceValueChanged(Dice dice ) {
		for (GameListener v : listeners)
			v.diceValueChanged(dice);
	}

    /**
     * M�todo que DEVE ser invocado SEMPRE QUE UMA QUADRICULA � ALTERADA,
     * Para que os listeners sejam informados.
     * @param tile quadricula que foi alterada
     */
    public void tileImageChanged(Tile tile) {
        for (GameListener v : listeners)
            v.tileImageChanged(tile);
    }

    /**
     * M�todo que DEVE ser invocado SEMPRE QUE UM JOGADOR TERMINA UM JOGO,
     * Para que os listeners sejam informados.
     * @param g
     */
    public void finishPlayer(BaseGame g) {
        for (GameListener v : listeners)
            v.finishPlayer(g);
    }
}
