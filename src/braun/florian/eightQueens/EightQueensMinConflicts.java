package braun.florian.eightQueens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import braun.florian.eightQueens.Boards.Board;
import braun.florian.eightQueens.Boards.RandomBoard;

/**
 * In this example we look at moving queens on a random board
 * until we arrive at a solution. The idea here is to find a single
 * solution quickly.
 * 
 * Of course a simple way to solve the problem would be to remove
 * all the queens from the board and then place them in a known
 * solution pattern for the given board. For the N Queens problem
 * this is easy since we know solutions for many values of N. But
 * here we can illustrate the idea of a CSP on N Queens.
 * 
 * We will look at the min-conflicts algorithm. The idea here is
 * that looking at the N queens problems as a CSP, instead of trying
 * to find the smallest number of moves to make, we just try and
 * find some set of moves as quickly as possible.
 * 
 * For the 8 queens problem, the 92 solutions are known. But if we
 * have a CSP where no solutions are known and we wanted to find the 
 * minimum number of moves we need to make from the beginning state,
 * to a solution we would first have to find all solutions, then
 * find the least number of moves from the current state to the 
 * goal state. In the second step would be trivial since we can move
 * each queen independently.
 * 
 * The min-conflicts algorithm is good at finding a solution quickly
 * to difficult problems, but not all solutions.
 * 
 * min-conflict works best when the initial state is fairly close to
 * a solution. One good way to achieve this is to populate the initial
 * state with a greedy algorithm that has some level of randomness
 * and can do random assignments when no assignment is possible under
 * the rules of the CSP. For this example we will just use a simple
 * random setup.
 * 
 * 
 * @author Florian Braun
 *
 */
public class EightQueensMinConflicts {

	//to keep track of the number of moves we need to make
	//to find a solution
	private static long numberOfMoves = 0;
	private static Random random = new Random(); //to allow for random selection with ties
	
	public static void main(String[] args) {
		RandomBoard board = new RandomBoard();
		System.out.println("The initial board is:");
		System.out.println(board);
		
		boolean isSolution = solveAsMinConflicts(board, 1000);
		
		if(isSolution){
			System.out.printf("To a solution we needed %d moves.\n", numberOfMoves);
		} else {
			System.out.println("The maximum number of iterations was reached");
		}
		
		System.out.println("The final board is:");
		System.out.println(board);
	}

	/**
	 * The min-conflicts algorithm is fairly simple. We take a board
	 * with positioning of the queens already. From there the steps are
	 * strait forward:
	 * 1. pick a random queen
	 * 2. find the space in the queens row with the fewest conflicts, if two 
	 * or more spaces tie, pick at random from them.
	 * 3. move the queen to the selected spot
	 * 4. Repeat until a solution is found or the max number of
	 * iterations is done 
	 * 
	 * 
	 * @param board the board for which to solve the problem
	 * @param limit the maximum number of moves allowed
	 * @return true if a solution was found
	 */
	private static boolean solveAsMinConflicts(RandomBoard board, long limit) {
		
		while(!board.isSolution() && numberOfMoves < limit){
			int queen = board.randomQueen();//The queen to move
			
			//we need to find the spaces in the queens row that have the fewest conflicts
			List<Integer> possiblePositions = minConflicts(board, queen);
			
			//select a random position of the min conflict positions
			int moveTo = possiblePositions.get( random.nextInt( possiblePositions.size() ) );
			
			board.setPositionOfQueen(queen, moveTo);
			numberOfMoves++;
		}
		
		return board.isSolution();
	}

	/**
	 * Finds the spaces in a queens row that have the fewest conflict.
	 * We want a list because we want to randomly choose any space that
	 * has the minimum number of conflicts.
	 * 
	 * @param board the board on which to check conflicts
	 * @param queen the queen for which to check the conflicts
	 * @return a list of the locations in that queens row with
	 * the fewest conflicts
	 */
	private static List<Integer> minConflicts(Board board, int queen) {
		List<Integer> positions = new ArrayList<>();
		int initial = board.getPositionOfQueen(queen);
		int minConflicts = Integer.MAX_VALUE;
		
		for(int i = 0; i < board.numberQueens(); i++){
			//move the queen to the current position in it's row
			board.setPositionOfQueen(queen, i);
			
			//get the number of conflicts with the queen at the current position
			int conflicts = numberOfConflicts(board, queen);
			
			if(conflicts < minConflicts){
				minConflicts = conflicts;
				positions.clear();
				positions.add(i);
				
			} else if(conflicts == minConflicts){
				positions.add(i);
			}
		}
		
		//put the queen back to it's initial position
		board.setPositionOfQueen(queen, initial);
		
		return positions;
	}

	/**
	 * We count the number of conflicts that the queen has. We count
	 * each direction only once, so if two queens attack on the same
	 * diagonal, this is only counted once. Since the queens are
	 * limited to their own row the max number of conflicts is limited
	 * to 3, the two diagonals and the vertical direction.
	 * 
	 * @param board the board on which to count the conflicts
	 * @param queen the queen for which to count the conflicts
	 * @return the number of conflicts for that queen
	 */
	private static int numberOfConflicts(Board board, int queen) {
		int conflicts = 0, queenPosition = board.getPositionOfQueen(queen);
		boolean major = false, minor = false, vert = false;
		
		for(int i = 0; i < board.numberQueens(); i++){
			
			//we don't want to check against the queen itself.
			if(i != queen){
				int otherQueen = board.getPositionOfQueen(i);
				//check the vertical
				if(!vert && otherQueen == queenPosition){
					conflicts++;
					vert = true;
				}
				
				//check one diag
				if(!major && ( otherQueen == queenPosition - (queen - i)) ){
					conflicts++;
					major = true;
				}
				
				//check the other
				if(!minor && ( otherQueen == queenPosition + (queen - i)) ){
					conflicts++;
					minor = true;
				}
			}
		}
		
		return conflicts;
	}
}
