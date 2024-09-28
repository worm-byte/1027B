/**
 This class defines a grid of characters which can be manipulated to remove rows or columns
 of characters that have 3 or more of the same character in a row.
 */

/**
 * 
 */
public class LetterCrush {
	//private character grid
    private char [][] grid;
    //this static character will be used often throughout my code
    public static final char EMPTY = ' ';

    //Constructor that fills the grid with a given characters
    //accepts parameters of width, height, and a string of letters to fill the grid
    public LetterCrush (int width, int height, String initial) {
        grid = new char [height][width];
        int initialIndex = 0;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = EMPTY;
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (initialIndex<initial.length()) {
                    grid[row][col] = initial.charAt(initialIndex);
                    initialIndex++;
                }else {
                    return;
                }
            }
        }


    }
    //a visual representation of the current grid if you print it
    //returns a string visual of the grid
    public String toString() {
        StringBuilder currGrid = new StringBuilder();
        currGrid.append("LetterCrush\n");
        int rowIndex = 0;

        for(int row = 0; row < grid.length; row++) {
            currGrid.append("|");
            for(int col = 0; col < grid[0].length; col++) {
                currGrid.append(grid[row][col]);
            }


            currGrid.append("|" + rowIndex + "\n");
            rowIndex++;

        }

        currGrid.append("+");
        for (int col = 0; col < grid[0].length; col++) {
            currGrid.append(col);
        }

        currGrid.append("+");

        return currGrid.toString();
    }
    //checks the whole grid to see if there is a a character in the grid with an empty space below it
    //returns false if there isn't a stable character, and true if everything looks stable
    public boolean isStable() {
        for (int row = grid.length-1; row > 0; row--) {
            for (int col = 0; col < grid[0].length-1; col++) {
                if (grid[row][col] == EMPTY) {
                    if (grid[row-1][col] != EMPTY)
                        return false;
                }
            }
        }
        return true;
    }
    //starting from the bottom, this will move a character down if there is an empty space below it
    public void applyGravity() {
        for (int row = grid.length-1; row > 0; row--) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == EMPTY) {
                    grid[row][col] = grid[row-1][col];
                    grid[row-1][col] = EMPTY;

                }
            }
        }
    }
    //removes a Line object. returns true if the line was removed successfully, and false if not
    //only lines that fit within the grid are removed
    public boolean remove(Line theLine) {
        int [] start = theLine.getStart();
        int [] end = new int[2];
        int lengthLine = theLine.length();


        if(theLine.isHorizontal()) {
            end[0] = start[0];
            end[1] = start[1] + (lengthLine);

        }else {
            end[0] = start[0] + (lengthLine);
            end[1] = start[1];
        }

        if (start[0] >= 0 && start[0]<= grid.length &&
                start[1] >= 0 && start[1] <= grid[0].length &&
                end[0]>= 0 && end[0] <= grid.length &&
                end[1] >=0 && end[1] <= grid[0].length) {
            if (theLine.isHorizontal()) {
                for (int col = start[1]; col < end[1]; col++) {
                    grid[start[0]][col] = EMPTY;
                }
            }else {
                for (int row = start[0]; row < end[0];row++) {
                    grid[row][start[1]] = EMPTY;
                }
            }
            return true;
        }else {
            return false;
        }

    }
    
    //returns a string visual of the grid with the line highlighted in lower case letters
    public String toString(Line theLine) {
        String currGrid = "CrushLine\n";
        int rowIndex = 0;

        for(int row = 0; row < grid.length; row++) {
            currGrid+="|";
            for(int col = 0; col < grid[0].length; col++) {
                if (theLine.inLine(row,col)) {
                    currGrid += Character.toLowerCase(grid[row][col]);
                }else {
                    currGrid += grid[row][col];
                }

            }

            currGrid += "|" + rowIndex + "\n";
            rowIndex++;

        }

        currGrid += "+";
        for (int col = 0; col < grid[0].length; col++) {
            currGrid += col;
        }

        currGrid += "+";

        return currGrid;
    }
    //will find the longest line on the grid and return it
    public Line longestLine() {

        Line longLine = new Line(grid.length-1,0,true,1);
        int largest = 0;
        for (int i = grid.length-1; i >= 0; i--) {
            char letter = grid[i][0];
            int adjacent = 1;
            for (int j = 1; j < grid[0].length; j++) {
                if (grid[i][j] == letter && letter != EMPTY) {
                    adjacent++;
                    if (adjacent>largest) {
                        largest = adjacent;
                        longLine = new Line(i,j-adjacent+1,true,adjacent);
                    }
                }else {
                    letter = grid[i][j];
                    adjacent = 1;
                }

            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            char letter = grid[grid.length-1][j];
            int adjacent = 1;
            for (int i = grid.length-2; i>=0; i--) {
                if(grid[i][j] == letter && letter != EMPTY) {
                    adjacent++;
                    if (adjacent>largest) {
                        largest = adjacent;
                        longLine = new Line(i,j,false,adjacent);
                    }
                }else {
                    letter = grid[i][j];
                    adjacent = 1;
                }
            }
        }

        if (longLine.length()>2) {
            return longLine;
        }else {
            return null;
        }
    }

    //this will continue to find the longest lines, remove them,
    //and shift characters down until no more lines of 3 or more are found
    //kind of like in actual candy crush
	public void cascade() {
	Line longest;
	while (longestLine() != null) {
		longest = longestLine();
		remove(longest);
		while (!isStable()) {
			applyGravity();
		}
	}


}

}
