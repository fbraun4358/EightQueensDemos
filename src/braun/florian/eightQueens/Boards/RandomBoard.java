package braun.florian.eightQueens.Boards;

import java.util.Random;

/**
 * 
 * A Board class where the starting positions of the queens are randomized
 *
 * @author Florian Braun
 */
public class RandomBoard extends Board {

	
	private Random generator;
	
	/**
	 * Creates a board with the default number of queens 
	 * randomly placed in each of their corresponding rows
	 */
	public RandomBoard(){
		this(Board.DEFAULT_QUEENS);
	}
	
	/**
	 * Creates a custome sized board with each queen randomly 
	 * places within its corresponding row.
	 * @param n the number of queens the custom board should have
	 */
	public RandomBoard(int n){
		super(n);
		this.generator = new Random();
		
		for(int i = 0; i < n; i++){
			int position = randomPosition();
			setPositionOfQueen(i, position );
		}
	}
	
	protected int randomPosition(){
		return generator.nextInt(this.queens.length);
	}
	
	public int randomQueen() {
		return randomPosition();
	}
	
}
