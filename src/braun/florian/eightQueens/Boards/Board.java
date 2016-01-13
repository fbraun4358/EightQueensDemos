package braun.florian.eightQueens.Boards;

import java.util.Arrays;
import java.util.List;

/**
 *
 * The board class represents a chess board with N number of
 * queens on the board.
 * 
 * The board is represented by an N length array. Each index represents
 * a queen in it's own row and the value of the array at that index
 * represents the column the queen is sitting in in that row.
 * 
 * The board is initialized with all queens sitting in column zero.
 * 
 * This is an efficient representation of the chess board. Instead of
 * having to store an NxN 2D array, we can simply store it
 * as a single N length array
 * 
 * Since a solution to the N queens problem explicitly prohibits
 * two or more queens in the same row or column we can assume each
 * queen is limited to moving in only one row.
 * 
 * @author Florian Braun
 * 
 */
public class Board {

	//The default number of queens a board will have
	protected static final int DEFAULT_QUEENS = 8;
	private static final int OFF_BOARD_INDEX = -1;
	
	//The array to represent the board and the queens positions
	protected int[] queens;
	
	/**
	 * The default Constructor, Initializes a board with the default
	 * number of queens, this is 8
	 */
	public Board(){
		this(DEFAULT_QUEENS);
	}
	
	/**
	 * A constructor for creating a custom sized board
	 * 
	 * @param n the number of queens the board should hold
	 */
	public Board(int n){
		this.queens = new int[n];
	}
	
	/**
	 * @param queen the queen to move, zero based
	 * @param position the row of the board to which to move the queen, zero based. If the
	 * position is outside of the bounds 0 - N-1 then it is treated as removing the queen 
	 * from the board.
	 */
	public void setPositionOfQueen(int queen, int position){
		if(position < 0 || position >= queens.length){
			position = OFF_BOARD_INDEX;
		}
		this.queens[queen] = position;
	}
	
	/**
	 * Returns the position of the given queen, if the queen is not placed
	 * on the board, returns -1
	 * 
	 * @param queen the queen for which to get the position
	 * @return the position of the given queen
	 */
	public int getPositionOfQueen(int queen){
		return this.queens[queen];
	}
	
	/**
	 * @param queen the queen to remove from the board
	 * @return the position of the queen before it was removed
	 */
	public int removeQueen(int queen){
		int lastPosition = this.queens[queen];
		
		this.queens[queen] = OFF_BOARD_INDEX;
		
		return lastPosition;
	}
	
	/**
	 * Sets the position of all queens to -1
	 */
	public void removeAllQueens(){
		Arrays.fill(this.queens, -1);
	}
	
	/**
	 * @return true - if the current board, disregarding any unplaced queens 
	 * does not violate the N queens problem. Else returns false. An empty
	 * board will return true.
	 */
	public boolean isPartialSolution(){
		for(int i = 0; i < this.queens.length; i++){
			if(!isPartialSolution(i)){
				return false;
			}
		}
		
		return true;
	}

	/**
	 * @param queen the queen up to which to check if the board is a partial solution
	 * @return true - if the board up to and including the given queen constitutes a solution, 
	 * else false. An empty board will return true;
	 */
	private boolean isPartialSolution(int queen) {
		if(queen == -1){
			return true;
		} else {
			return isSolution(queen, false);
		}
	}
	
	/**
	 * @return true if the current queen placement if a solution
	 * to the N queens problem
	 */
	public boolean isSolution(){
		//we check for every queen if the current setup up to that queen is 
		//a solution
		for(int i = 0; i < this.queens.length; i++){
			if(!isSolution(i)){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks to see if the board is in a state of partial solution 
	 * from the first queen to the given queen
	 * 
	 * @param queen the last queen to check
	 * @return if the board is in a state of partial solution from 
	 * the first queen to the given queen
	 */
	public boolean isSolution(int queen){
		return isSolution(queen, true);
	}
	
	private boolean isSolution(int queen, boolean checkFilled) {

		int queenPosition = queens[queen];
		
		for(int i = 0; i < queen; i++){
			int otherQueen = queens[i];
			
			if( otherQueen == OFF_BOARD_INDEX && checkFilled){
				return false;
			}
			if(otherQueen == queenPosition){
				//If the current and the previous queen are in the same column
				return false;
			}
			else if (otherQueen == queenPosition - (queen - i)){
				//if they are on the major diagonal
				return false;
			}
			else if( otherQueen == queenPosition + (queen - i)){
				//they are on the minor diagonal
				return false;
			}
		}
				
		return true;
	}

	/**
	 * Finds the index of the last queen that is placed on the board
	 * 
	 * @return the last queen that is placed or -1 if no queens are placed
	 */
	public int lastQueen(){
		//Iterate backward through the array to find the last queen that is placed
		for(int i = this.queens.length - 1; i > -1; i--){
			
			//the position of the current queen being looked at
			int position = this.queens[i];
			
			//if the position is not negative, return it
			if(position > -1 ){
				return i;
			}
		}
		
		return -1;
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("[");
		sb.append(queens[0]);
		
		for(int i = 1 ; i < queens.length; i++){
			sb.append(", ");
			sb.append(queens[i]);
		}
		
		sb.append(']');
		
		return sb.toString();
	}

	public List<Integer> getCurrentState() {
		return new BoardState(queens);
	}

	public int numberQueens() {
		return queens.length;
	}
	
}
