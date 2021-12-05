package boardGames.game;

/**
 * Created by msousa on 12/4/2018.
 */

import java.util.Random;

/**
 * Classe aninhada que contém a informação relativa a um dado. Um dado tem
 * um número que o identifica e o valor que está na face.
 */
public class Dice {
    public static Random rd = new Random();
    public final static int MAX_VALUE = 6;
    private int number;
    private int value = nextValue();
    public Dice( int number ) {	this.number = number;      }
    public int rool()         { return value= nextValue(); }
    public int getNumber()    { return number;             }
    public int getValue()     { return value;              }
    private static int nextValue(){
        return rd.nextInt(MAX_VALUE) + 1;
    }
}
