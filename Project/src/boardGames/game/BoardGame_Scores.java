package boardGames.game;

import boardGames.questions.Question;
import boardGames.questions.Questions;
import boardGames.game.actions.*;

import java.io.*;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntBiFunction;

/*
 * Classe exemplo.
 */


public class BoardGame_Scores extends BaseGame {

     public BoardGame_Scores(int numberOfDices, int numberOfTilesPerLine, String ... playerNames) {
        super(numberOfDices, numberOfTilesPerLine, 0, playerNames);
        putActions();

         try { //ler do ficheiro as questoes
             qs.readQuestions();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public Player serchPlayers(String n, int id) throws IOException {
        //ler do ficheiro e comparar com o nome se encontrar criar
        //novo player com os dados do ficheiro
        Player p = null;
        try (BufferedReader br = new BufferedReader(new FileReader("Scores.txt"))) {
            String line;
            String[] parts;
            while ((line = br.readLine()) != null) {
                parts=line.split("-");
                if(parts[0].equals(n)){
                    p = new Player(n,Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),Integer.parseInt(parts[3]));
                    p.setId(id);
                    break;
                }
            }
        }
        return p;
    }

    //daria para fazer de outra forma que a primeira vez que se le-se o
    //ficheiro gravar a linha que foi lida no player depois era fazer
    //update a essa linha e se nao tivesse linha significava que se
    //tinha de fazer append ao ficheiro
    public void saveScores(){ //alterar para substituir os valores e nao fazer append
        List<Player> players = getPlayers();
        try {
            String[] parts;
            File file = new File("Scores.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            String newText = "";
            boolean flag=false;
            while((line = reader.readLine()) != null) {
                for (Player p : players) {
                    parts = line.split("-");
                    if (parts[0].equals(p.getName())) {
                        newText += parts[0]+"-"+p.getNumberOfGames()+
                                "-"+p.getScore()+"-"+
                                mediaPontos(p.getNumberOfGames(),p.getScore())+"\n";
                        flag=true;
                        p.saved=true;
                        break;
                    }
                }
                if(!flag) {
                    newText += line + "\n";
                }
            }
            reader.close();

            for (Player p : players) {
                if(!p.saved) {
                    newText += p.getName()+"-"+p.getNumberOfGames()+"-"+p.getScore()+
                            "-"+mediaPontos(p.getNumberOfGames(),p.getScore())+"\n";
                }
                p.saved=false;
            }
            FileWriter writer = new FileWriter("Scores.txt");
            newText = newText.substring(0,newText.length()-1);
            writer.write(newText);
            writer.close();
        }
        catch (IOException ioe)
        {
            //ioe.printStackTrace();
            //em principio nao deve encontrar ficheiro se vier para aqui
        }
    }

    public int mediaPontos(int numJogos,int pontos){
         if(numJogos==0){
             return 0;
         }
        return pontos/numJogos;
    }

    /**
     * Métodos que devem ser redefinidos para acrescentar
     * ações ou modificar o comportamento:
     ****************/
    @Override
    protected Player getPlayer( int i, String n ) {
        Player p = null;
        try {
            p = serchPlayers(n,i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(p!=null){
            return p;
        }
        return new Player( i, n );
    }

    @Override
    protected boolean isValidPosition(int to) {
        return to == numberOfTiles() - 1 ||
                to < numberOfTiles() - numberOfDices();
    }

    @Override
    protected IntUnaryOperator getPointsOfRanking() {
        return (ranking) -> numberOfPlayers() - ranking;
    }

    @Override
    protected void putActions() {
        setAction(0, new TileActionStart());
        setAction(63, new TileActionFinish(this));
        for (int i =5; i<63; i+=9)
            setAction(i, new TileWhileAction(this::putQuestion));
        for (int i =9; i<63; i+=9)
            setAction(i, new TilePerguntaAction(this::putQuestion,this));
        setAction(6, new TileIfAction(this::putQuestion,this));
        setAction(31, new TileIfAction(this::putQuestion,this));
        setAction(42, new TileForEachAction(this::putQuestion,this));
        setAction(58, new TileThrowAction(this::putQuestion,this));
        setAction(37, new TileForAction());
        setAction(21, new TileForeverAction());
    }

    /**
     * Método auxiliar de teste para forçar que o jogador se mova
     * caso o para determinada quadricula, salto passe pela quadricula.
     * @param test    numero da quadicula que se quer testar
     * @param oldJump salto que o jogador iria fazer
     * @param player  jogador a mover.
     * @return o salto necessário para ir para a quadricula que se quer testar
     */
    private int getJumpTest(int test, int oldJump, Player player) {
        if (player.getPosition() < test &&
                player.getPosition() + oldJump > test)
            oldJump = test - player.getPosition();
        return oldJump;
    }

    @Override
    protected int rollDices() {
        int jump= super.rollDices();
        // TESTE de DETERMINADA AÇÂO -
        // Código para forçar a ação da casa 5
        //jump = getJumpTest(37, jump, currentPlayer);

        return jump;
    }

    Questions qs= new Questions();
    ToIntBiFunction<Question, Player> showQuestion;
    public void setShowQuestion(ToIntBiFunction<Question, Player> showQuestion) {
        this.showQuestion = showQuestion;
    }
    private boolean putQuestion( Player p ){
        Question q = qs.getQuestion();
        return showQuestion.applyAsInt( q, p ) == q.correctAnswer();
    }
}
