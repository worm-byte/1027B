/**
 This class defines a line and can test different attributes of the line.
 */

/**
 * 
 */
public class Line {
	//private variables arrays
	private int[]start,end;
	
	//constructor that puts the start and ending coordinates of the line in start and end array
	//accepts parameters row, col, horizontal, length
	public Line(int row, int col, boolean horizontal, int length) {
		start = new int[] {row,col};
		
		if(horizontal == true) {
			end = new int[] {row,col+length-1};
		}else {
			end = new int[] {row+length-1,col};
		}
		
	}
	//copies the start array and returns a copy
	public int[] getStart() {
		int[] copyStart = new int[start.length];
		for (int i = 0; i < start.length ; i++) {
			copyStart[i]=start[i];
		}
		return copyStart;
	}
	
	//calculates the length of the line
	public int length() {
		int difference;
		if (start[0] == end[0]) {
			difference = Math.abs(end[1]-start[1]+1);
			return difference;
		} else {
			difference = Math.abs(end[0]-start[0]+1);
			return difference;
		}
	}
	
	//tests if the line is horizontal or not
	public boolean isHorizontal() {
		if (start[0] == end[0]) {
			return true;
		} else {
			return false;
		}
	}
	
	//tests if a point provided is in the line or not
	public boolean inLine(int row,int col) {
		if (row >= start[0] && row <= end[0] && col >= start[1] && col <= end[1]) {
			return true;
		}else {
			return false;
		}
	}
	
	//returns a string that states the starting and ending points of the line
	public String toString() {
		return "Line:[" + start[0] + "," + start[1] + "]->[" + end[0] + "," + end[1] + "]";
	}

}
