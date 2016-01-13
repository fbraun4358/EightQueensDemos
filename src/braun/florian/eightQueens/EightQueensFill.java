package braun.florian.eightQueens;

import braun.florian.eightQueens.Boards.Board;
import braun.florian.eightQueens.Boards.PartialRandomBoard;

/**
 * In this example, we will look at trying to find 
 * any possible solutions to a partially filled board. 
 * To begin, the board will have some random distribution 
 * of queens.
 * 
 * We first check if the placement already violates the 8 
 * queens problem and if not, we try DFS with backtracking 
 * to fill in the remaining queens.
 * 
 * Running this a few times shows that in the standard 8
 * queens setup, the search space is huge, on a random board
 * where each queen is limited to its own row there are still
 * 8^8 possible positionings, of these only 92 are solutions.
 * 
 * @author Florian Braun
 *
 */
public class EightQueensFill {
	
	//to count the number of solutions
	private static long solutionCount = 0;
	
	public static void main(String[] args) {
		Board board = new PartialRandomBoard();
		
		System.out.println("The current board is:");
		System.out.println(board);
		
		//check to see if the board is a partial solution already, if not
		//we can skip searching for any possible solutions
		if(board.isPartialSolution()){
			solveDFSPartial(board, 0);
		}
		
		System.out.printf("There are %d different solutions to this problem.\n", solutionCount);
	}

	private static void solveDFSPartial(Board board, int queen) {
		int numberQueens = board.numberQueens();
		
		//terminating case
		if(queen == numberQueens){
			solutionCount++;
		} else {
			//since we only want to move any queens not placed, we need to
			//see if the queen is already placed
			int queenPosition = board.getPositionOfQueen(queen);
			
			//if the queen is not placed, we must search all possible placements
			//if the queen is already placed, we can skip onto the next queen
			if(queenPosition == -1){
				
				//do the same DFS as we did for the simple puzzle
				for(int i = 0; i < numberQueens; i++){
					board.setPositionOfQueen(queen, i);
					
					if(board.isSolution(queen)){
						solveDFSPartial(board, queen + 1);
					}
				}
				
				//the last thing we want to do is reset the queen off the board
				//further iterations may look at this queen again
				board.removeQueen(queen);
				
			} else {
				//if the queen is already set, we just need to make sure the
				//placement of the queen with the preceding queens
				//does not cause a problem
				if(board.isSolution(queen)){
					solveDFSPartial(board, queen + 1);
				}
			}
			
		}
	}
}
