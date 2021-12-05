package boardGames.gui;

import boardGames.game.BaseGame;
import boardGames.game.*;
import boardGames.game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

public class GameFrame_Scores extends GameFrame {

    String[] file ={"New game", "Save Scores", "Exit"};
    ActionListener[] fileActions = {this::newGame, this::saveScore, this::exitGame};
    String[] information ={"players", "all players", "top10"};
    ActionListener[] informationActions = {this::players, this::allplayers, this::top10};

    BoardGame_Scores game;
    JMenuBar mb;

    public GameFrame_Scores(String title, IntFunction<ImageIcon> getPinIcon,
                            Function<String, ImageIcon> getTileIcon, IntFunction<ImageIcon> getDiceIcon,
                            Dimension tileImageDim, BoardGame_Scores game) {
        super(title, getPinIcon, getTileIcon, getDiceIcon, tileImageDim, game);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                game.saveScores();
                System.exit(0);
            }
        });
        this.game = game;
        mb = new JMenuBar();
        setJMenuBar( mb );
        mb.add( menuFile(file, fileActions) );
        mb.add( menuInformation(information, informationActions));
        mb.add( menuChangename(getPinIcon));
    }

    private JMenu menuFile(String[] file, ActionListener[] fileActions) {
        JMenu m = new JMenu("File");
        int i=0;
        for(ActionListener a : fileActions){
            JMenuItem mi = new JMenuItem(file[i]);
            mi.addActionListener(a);
            m.add(mi);
            i++;
        }
        return m;
    }

    private JMenu menuInformation(String[] information, ActionListener[] informationActions ) {
        JMenu m = new JMenu("Information");
        int i=0;
        for(ActionListener a : informationActions){
            JMenuItem mi = new JMenuItem(information[i]);
            mi.addActionListener(a);
            m.add(mi);
            i++;
        }
        return m;
    }

    private JMenu menuChangename(IntFunction<ImageIcon> getPinIcon){
        JMenu m = new JMenu("Change players name");
        int aux=0;
        for(Player p : game.getPlayers() ){
            final int index=aux;
            JMenuItem mi = new JMenuItem(p.getName(), getPinIcon.apply(p.getId()));
            mi.addActionListener(e -> {
                Player p1 = game.getPlayers().get(index);
                JPanel aux1 = new JPanel(new BorderLayout());
                JTextArea textArea = new JTextArea();
                JLabel label = new JLabel("New name?");
                Image ic = Icons.scaleImage(getPinIcon.apply(p1.getId()), new Dimension(10,15));
                ImageIcon icon = new ImageIcon(ic);
                label.setIcon(icon);
                textArea.setEditable(true);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.requestFocus();
                textArea.requestFocusInWindow();
                aux1.add(scrollPane, BorderLayout.SOUTH);
                aux1.add(label, BorderLayout.NORTH);
                JOptionPane.showMessageDialog(mb, aux1, "Change Name", JOptionPane.PLAIN_MESSAGE);
                String info = textArea.getText();
                if(info.length()>1) {
                    game.changePlayer(p1.getName(), info);
                }
            });
            m.add( mi );
            aux++;
        }
        return  m;
    }

    // Actions dos menuItems
    private void exitGame(ActionEvent actionEvent) {
        game.saveScores();
        System.exit(0);
    }

    private void saveScore(ActionEvent actionEvent) {
        game.saveScores();
    }

    private void newGame(ActionEvent actionEvent) {
        newGameFunct();
    }

    public void newGameFunct(){
        List<Player> lp =game.getPlayers();
        for(Player p : lp){
            int aux = p.getPosition();
            movePlayerNewGame(p,-aux);
            p.start();
        }
    }
    public void movePlayerNewGame(Player player, int jump) {
        int from = player.getPosition();
        int to = from + jump;
        game.playerExited(player);
        player.moveTo(to);
        game.playerEntered(player);
    }

    StringBuilder sb;
    private void top10(ActionEvent actionEvent) {
        try {
            ArrayList<Integer> ints = lerFich();
            String[] as = sb.toString().split("\r\n|\r|\n");
            ArrayList<Integer> aux = new ArrayList<>(ints);
            Collections.sort(ints,Collections.reverseOrder());
            int x=0;
            StringBuilder sb1 = new StringBuilder();
            sb1.append("Name of Player-Number of games-Points-Mean\n");
            while(x<10 && x<ints.size()){
                for(int i=0;i<ints.size();i++){
                    if(ints.get(x).compareTo(aux.get(i))==0){
                        aux.set(i,-1);
                        sb1.append(as[i]+"\n");
                        break;
                    }
                }
                x++;
            }
            JOptionPane.showMessageDialog(mb, sb1.toString(), "Top 10", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Integer> lerFich() throws IOException {
        ArrayList<Integer> ints = new ArrayList<>();
        sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("Scores.txt"))) {
            String line;
            String[] parts;
            while ((line = br.readLine()) != null) {
                parts=line.split("-");
                ints.add(Integer.parseInt(parts[parts.length-1]));
                sb.append(line+"\n");
            }
        }
        return ints;
    }

    private void allplayers(ActionEvent actionEvent) {
        sb = new StringBuilder();
        sb.append("Name of Player-Number of games-Points-Mean\n");
        try (BufferedReader br = new BufferedReader(new FileReader("Scores.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(mb, sb.toString(), "All Players", JOptionPane.INFORMATION_MESSAGE);
    }

    private void players(ActionEvent actionEvent) {
        List<Player> players = game.getPlayers();
        StringBuilder sb = new StringBuilder();
        for(Player p : players){
            sb.append(p+"\n");
        }
        JOptionPane.showMessageDialog(mb, sb.toString(), "Players", JOptionPane.INFORMATION_MESSAGE);
    }
}
