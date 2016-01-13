package braun.florian.eightQueens.Boards;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

/**
 * 
 * A random Board in which only some of queens are placed 
 * on the board.
 * 
 * @author Florian Braun
 */
public class PartialRandomBoard extends RandomBoard {

	//The default percent of the queens that should be placed
	private static double DEFAULT_FILL = 0.5;
	
	/**
	 * Creates a board with 4 randomly placed queens
	 */
	public PartialRandomBoard(){
		this(Board.DEFAULT_QUEENS);
	}
	
	/**
	 * Creates a board with N number of queens, where half the queens are randomly placed.
	 * 
	 * @param queens The number of queens the board should have
	 */
	public PartialRandomBoard(int queens){
		this(queens, DEFAULT_FILL);
	}
	
	public PartialRandomBoard(int queens, double fill){
		this.queens = new int[queens];
		//set each queen to be off the board
		Arrays.fill(this.queens, -1);
		
		//this is the number of queens we need to add the the
		//board to achieve the fill ratio, rounded down
		int queensToAdd = (int)(fill * this.queens.length);
		
		//An array of where the value of each index is the index
		//we will use this array to determine which queens to randomly remove
		//to get to the fill level
		int[] indexes = new int[this.queens.length];
		for(int i = 0; i < indexes.length; i++){
			indexes[i] = i;
		}
		suffleArray(indexes);

		//now that we have a shuffled array, can remove the indexes
		for(int i = 0; i < queensToAdd; i++){
			//the row in which to place a queen
			int index = indexes[i];
			
			//assign the queen a random position
			this.queens[index] = randomPosition();
		}
	}
	
	/**
	 * A shuffling function for an integer array
	 * 
	 * @param array
	 */
	private static void suffleArray(int[] array){
		int index, temp;
		Random random = new Random();
		
		for(int i = array.length - 1; i > 0; i --){
			index = random.nextInt(i + 1);
			temp = array[index];
			array[index] = array[i];
			array[i] = temp;
		}
		
	}
	
}
