package braun.florian.eightQueens.Boards;

import java.util.ArrayList;

public class BoardState extends ArrayList<Integer> {
	private static final long serialVersionUID = 4506400939768350013L;

	public BoardState(){
		super();
	}
	
	protected BoardState(int[] queens){
		super(queens.length);
		
		for(int i = 0; i < queens.length; i++){
			this.add(queens[i]);
		}
	}
}
