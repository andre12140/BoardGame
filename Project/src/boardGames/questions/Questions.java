package boardGames.questions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Classe Exemplo.
 */

public class Questions{
    private ArrayList<Question> questions;

    public Question getQuestion() {
        Random r = new Random();
        return questions.get((r.nextInt(questions.size())));
    }

    public void readQuestions() throws IOException {
        boolean flag = false;
        ArrayList<Question> questions = new ArrayList<>();
        Question aux;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.equals("")){
                    String x = sb.substring(3);
                    String[] linesAux = x.split("\r\n|\r|\n");
                    ArrayList<String> answers = new ArrayList<>();
                    String correctAnswareAux = sb.substring(1,2);
                    int indexOfCorrectAnsware = 0; //que parvoice

                    if(linesAux.length>1){ //pergunta com varias opcoes

                        for(int i=1; i<linesAux.length ; i++){
                            answers.add(linesAux[i].substring(0,1));
                            if(correctAnswareAux.compareTo(linesAux[i].substring(0,1)) == 0){
                                indexOfCorrectAnsware=i-1;
                            }
                        }

                    } else { //pergunta de sim ou nao
                        answers.add("Yes");
                        answers.add("No");
                        if(correctAnswareAux.compareTo("Y")==0){
                            indexOfCorrectAnsware=0;
                        } else {
                            indexOfCorrectAnsware=1;
                        }
                    }

                    final int Aux420 = indexOfCorrectAnsware;//que parvoice
                    aux = new Question() {
                        public String question() { return x; }
                        public String[] answers() { return answers.toArray(new String[0]);}
                        public int correctAnswer(){ return Aux420; }
                    };

                    questions.add(aux);
                    flag = true;
                }
                sb.append(line).append("\n");
                if(flag){
                    sb = new StringBuilder();
                    flag=false;
                }
            }
        }
        this.questions = questions;
    }
}
