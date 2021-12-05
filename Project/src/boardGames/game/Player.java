package boardGames.game;

import java.util.function.IntUnaryOperator;

/**
 * Classe que contém a informação relativa a um jogador.
 * Um jogador tem um nome, o número de pontos acumulados e
 * os dados referentes ao jogo corrente:
 *    - Número do jogador no jogo.
 *    - Posição em que terminou o jogo ou ZERO se ainda não TERMINOU.
 *    - A quadricula em que se encontra, e a quadricula anterior.
 */

public class Player {
    private static int IN_TURN = 0;
    // << Variáveis de instância >>
    private final String name; // nome no jogador
    private int score;         // pontos acumulados
    private int numberOfGames; // número de jogos
    private int mediaPontos; // Ranking Total

    // Variáveis referentes ao jogo corrente
    private int id, ranking;
    private int position, previousPosition;
    //private int fileLine=0;
    public boolean saved = false;   //variavel para fazer saveScores
    public boolean tilePerguntaAux = false;
    public int tileForAux = 0;

    public Player( int id, String name ) {
        this.name = name;
        this.id = id;
    }

    public Player( String name, int g, int s , int mP) {
        this(0, name);
        numberOfGames= g;
        score = s;
        mediaPontos = mP;
    }

    public String getName()      { return this.name;                   }
    public int getId()           { return id;	                       }
    public void setId(int id)    { this.id= id;	                       }
    public int getScore()        { return score;	                   }
    public int getNumberOfGames(){ return numberOfGames;	           }
    public int getPosition()     { return position;                    }
    public int getMediaPontos()  { return mediaPontos;                 }
    public int getLastJump()     { return position - previousPosition; }
    //public void setfileLine(int line) { this.fileLine = line; }
    //public int getFileLine()     { return fileLine; }

    /**
     * Deve ser chamado quando o jogador termina o jogo.
     * @param ranking posição em que terminou o jogo
     * @param points função que converte em pontos a posição no ranking
     */
     public void finish( int ranking,
                         IntUnaryOperator points ) {
        ++numberOfGames;
        this.score += points.applyAsInt( ranking );
        this.ranking = ranking;
     }

    /**
     * Deve ser chamado no inicio do jogo.
     */
    public void start( ) {
        this.position = this.previousPosition = 0;
        this.ranking = IN_TURN;
    }
    /**
     * Deve ser chamado por cada jogada (UMA e SÒ UMA VEZ) para saber
     * se o jogador joga na próxima jogada.
     * @return true se ainda não terminou e não estiver retido na quadricula, false caso contrário.
     */
    public boolean inTurn() { return ranking == IN_TURN; }

    /**
     * Mover o jogador para a próxima quadricula.
     * Guarda a position anterior.
     * @param position quadricula para onde se deve mover
     */
    public void moveTo( int position ) {
       previousPosition = this.position;
       this.position = position;
    }

    public String toString() {
        return ranking == IN_TURN
                ? name + " in turn ( tile: " + position +" )"
                : ranking + "º - " + name;
    }
 }
