package fi.riissanen.gwent.game.ui;

import fi.riissanen.gwent.game.GameSystem;
import fi.riissanen.gwent.game.cards.Card;
import fi.riissanen.gwent.game.cards.Hand;
import fi.riissanen.gwent.game.cards.UnitCard;
import fi.riissanen.gwent.game.combat.UnitType;
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
     * play_unit [card] [row] = Plays a unit card on a specific row
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
                Hand currentHand = gameSys.getHand();
                for (int i = 0; i < currentHand.getCardCount(); i++) {
                    Card card = currentHand.getCard(i);
                    if (card instanceof UnitCard) {
                        int strength = ((UnitCard) card).getUnit().getStrength();
                        System.out.println(i + " : " + strength);
                    }
                }
                break;
            case "show_board" :
                int count = gameSys.getBoard().getUnitCount(true);
                System.out.println("Friendly: " + count);
                break;
            case "play_unit" :
                int index = Integer.parseInt(args[0]);
                int row = Integer.parseInt(args[1]);
                Hand hand = gameSys.getHand();
                Card card = hand.getCard(index);
                gameSys.playCard(hand, card, UnitType.values()[row], true);
                break;
            default :
                System.out.println("Invalid command");
                break;
        }
        
    }
}
