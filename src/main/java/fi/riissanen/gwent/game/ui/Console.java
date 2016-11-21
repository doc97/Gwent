package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.UnitCard;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Daniel
 */
public class Console implements Runnable {

    private Thread thread;
    private BufferedReader br;
    private GameSystem gameSys;
    private String input, cmd;
    private String[] args;
    private boolean running;
    private final String helpMsg = "COMMANDS\n"
                        + "-----------\n"
                        + "show_board - Shows info about the current board "
                        + "state\n"
                        + "show_hand - Shows the cards in your hand\n"
                        + "play_card [card index] - Plays a card"
                        + "from your hand. \"card index\" is the index of the "
                        + "card in your hand\n"
                        + "help - Shows this message";
    
    public void start(GameSystem gameSys) {
        this.gameSys = gameSys;
        running = true;
        br = new BufferedReader(new InputStreamReader(System.in));
        args = new String[2];
        thread = new Thread(this, "Gwent Console");
        thread.start();
    }
    
    public void stop() {
        running = false;
        thread.interrupt();
    }
    
    @Override
    public void run() {
        while (running) {
            reset();
            listen();
            parse();
            execute();
        }
    }
    
    private void reset() {
        input = "";
        cmd = "";
        args[0] = "";
        args[1] = "";
    }
    
    private void listen() {
        System.out.print("> ");
        do {
            try {
                while (!br.ready() && running) {
                    Thread.sleep(200);
                }
                if (!running) {
                    return;
                }
                
                input = br.readLine();
            } catch (InterruptedException | IOException e) {
                System.out.println();
                return;
            }
        } while (input.equals(""));
    }
    
    /**
     * [cmd] [args...]
     * show_hand = Prints out your current hand
     * show_board = Prints out the current board state
     * play_card [card] = Plays a card
     *
    */
    private void parse() {
        String[] parsed = input.split(" ", 3);
        cmd = parsed[0];
        for (int i = 1; i < parsed.length; i++) {
            args[i - 1] = parsed[i];
        }
    }
    
    private void execute() {
        if (cmd.equals("")) {
            return;
        }
        switch (cmd) {
            case "show_hand" :
                gameSys.getPlayer().handStatus();
                break;
            case "show_board" :
                gameSys.getBoard().status();
                break;
            case "play_card" :
                int index = Integer.parseInt(args[0]);
                Card card = gameSys.getPlayer().getCard(index);
                if (card != null) {
                    gameSys.stageCard(card);
                    if (card instanceof UnitCard) {
                        int rowIndex = -1;
                        int[] indices = ((UnitCard) card).getUnit().getTypeIndices();
                        
                        // If card is a UnitCard then it must have at least one
                        // unit type
                        if (indices.length == 1) {
                            rowIndex = indices[0];
                        } else if (indices.length > 1) {
                            do {
                                rowIndex = promptRowIndex(indices);
                            } while (!validateRowIndex(indices, rowIndex));
                        }
                        gameSys.playCard(rowIndex);
                    } else {
                        gameSys.unstageCard();
                    }
                } else {
                    System.out.println(
                            "Player does not have a card with index: " + index);
                }
                
                break;
            case "help" :
                System.out.println(helpMsg);
                break;
            default :
                System.out.println("Invalid command, try \"help\".");
                break;
        }
    }
    
    private boolean validateRowIndex(int[] indices, int index) {
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] == index) {
                return true;
            }
        }
        return false;
    }
    
    private int promptRowIndex(int[] indices) {
        String availableRows = "";
        for (int i = 0; i < indices.length; i++) {
            availableRows += "" + indices[i];
            if (i < indices.length - 1) {
                availableRows += ", ";
            }
        }

        System.out.println("Row ( " + availableRows + " )?");
        reset();
        listen();
        return Integer.parseInt(input);
    }
}
