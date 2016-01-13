package braun.florian.eightQueens;

import braun.florian.eightQueens.Boards.Board;

/**
 * This is a simple example of the N queens problem. We use 8 queens 
 * as in the classic problem on a normal chess board. We start out by 
 * having a board where every queen is in the first column.
 * 
 * We will use DFS with backtracking to find all the possible solutions to the 8 queens problem. 
 * 
 * 
 * @author Florian Braun
 */
public class EightQueensSimple {
	
	//to count the number of solutions
	private static long solutionCount = 0;
	
	public static void main(String[] args) {
		//A board representing the queens, default 8 queens
		Board board = new Board();
		
		solveDFS(board, 0);
		
		System.out.printf("There are %d different solutions to the %d queens problem:\n", solutionCount, board.numberQueens());
	}

	private static void solveDFS(Board board, int queen) {
		int numberQueens = board.numberQueens();
		
		//terminating case
		if(queen == numberQueens){
			solutionCount++;
		} else {
			
			for(int i = 0; i < numberQueens; i++){
				board.setPositionOfQueen(queen, i);
				
				if(board.isSolution(queen)){
					solveDFS(board, queen + 1);
				}
			}
			
		}
	}
	
}
