package boardGames.questions;

import boardGames.game.Player;

import java.util.function.Function;

/**
 * Created by msousa on 12/6/2016.
 */
public interface Question {
    String question();
    String[] answers();
    int correctAnswer();
}
