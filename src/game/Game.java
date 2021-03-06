package game;

import game.player.Player;
import java.util.Observable;



public class Game extends Observable implements Runnable {

	public static final int CONSECUTIVE_MARKS_TO_WIN = 4;
	
	private Board board = new Board();
	private Player[] players = new Player[2];
	private int currentPlayer = 0;


	public Game(Player player1, Player player2) {
		players[0] = player1;
		players[1] = player2;
	}
	
	
	public int getColumnHeigth(Column column) {
		return board.getColumnHeight(column);
	}
	
	
	public boolean isColumnFull(Column column) {
		return board.isColumnFull(column);
	}
	
	
	public void run() {
		while (board.getEnding() == Ending.NOT_ENDED) {
			doMoveFor(players[currentPlayer]);
		}
		setChanged();
		notifyObservers(board.getEnding());
	}
	
	
	public synchronized Board getBoardState() {
		return new Board(board);
	}
	
	
	public synchronized Ending getEnding() {
		return board.getEnding();
	}
	
	
	private void doMoveFor(Player player) {
		Move move = player.getMove(board);
		synchronized (this) {
			board.doMove(move);
			switchCurrentPlayer();
		}
		setChanged();
		notifyObservers(move);
	}
	
	
	private void switchCurrentPlayer() {
		currentPlayer = currentPlayer == 0 ? 1 : 0;
	}
	
	
	// all possible endings of a game
	public enum Ending {
		
		NOT_ENDED, X_WINS, O_WINS, DRAW;
	
		// way to pass by value (enums are classes and thus normally passed by reference)
		public Ending copy() { 
			if (this == NOT_ENDED) {
				return NOT_ENDED;
			}
			if (this == X_WINS) { 
				return X_WINS;
			}
			if (this == O_WINS) {
				return O_WINS;
			} else { 
				return DRAW;
			}
		}
		
		
		public String toString() {
			if (this == NOT_ENDED) {
				return "not ended";
			}
			if (this == X_WINS) {
				return "Player X won";
			}
			if (this == O_WINS) {
				return "Player O won";
			} else { 
				return "It was a draw";
			}
		}
	}

}
