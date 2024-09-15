/*
 * CS1027B rscully5 Assignment 3
 * March 11, 2024
 * This class finds the best path for Freddy to take
 * given a file of a map.
 * 
 */


public class FrogPath {
	//instance variable pond
	private Pond pond;
	
	/*
	 * Private helper method that checks if there are any surrounding
	 * alligators to a cell. The cell that you want to check is inputed
	 * and if a gator is found surrounding that cell, it returns true.
	 * Otherwise, it returns false.
	 */
	private boolean gatorCheck(Hexagon cell) {
		Hexagon neighbour;
		boolean found = false;
		for(int i = 0; i < 6; i++) {
			neighbour = cell.getNeighbour(i);
			if(neighbour == null) continue;
			if(neighbour.isAlligator())found = true;
		}
		return found;
	}
	
	/*Constructor given the filename. If there are any issues with opening a file,
	 * this constructor will print catch the error and print out a message.
	 * 
	 */
	public FrogPath(String filename) {
		try {
			pond = new Pond(filename);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/*
	 * This method inputs a Hexagon cell and finds the next best cell to go to 
	 * based on the priorities given in the assignment. It first checks the surrounding
	 * cells, then if on a lilypad cell, it will check cells two spaces away as well.
	 * If the cell two spaces away is in a straight line, it will bump it up higher on the
	 * priority list. It creates a ArrayUniquePriorityQueue, so each Hexagon can only be 
	 * in the priority list once. Returns the next best Hexagon cell that Freddy should go to.
	 */
	public Hexagon findBest(Hexagon currCell) {
		ArrayUniquePriorityQueue<Hexagon> prioQueue = new ArrayUniquePriorityQueue<Hexagon>();
		
		for(int i = 0; i < 6; i++) {
			double prio = 0.0;
			Hexagon neighbour = currCell.getNeighbour(i);
			if(neighbour == null) continue;
			if(neighbour.isMarked()) continue;
			else if(neighbour.isMudCell()) continue;
			else if(gatorCheck(neighbour) == true) {
				if(neighbour.isReedsCell()) {
					prio = 10.0;
					prioQueue.add(neighbour, prio);
					continue;
				}else continue;
			}else if(neighbour instanceof FoodHexagon) {
				int flies = ((FoodHexagon) neighbour).getNumFlies();
				if(flies == 3) prio = 0.0;
				else if (flies == 2) prio = 1.0;
				else if (flies == 1) prio = 2.0;
				else prio = 6.0;
			}else if(neighbour.isLilyPadCell()) prio = 4.0;
			else if(neighbour.isEnd()) prio = 3.0;
			else if(neighbour.isReedsCell()) prio = 5.0;
			else if(neighbour.isWaterCell()) prio = 6.0;
			
			prioQueue.add(neighbour, prio);
		}
		
		if(!currCell.isLilyPadCell()) {
			if(prioQueue.isEmpty()) return null;
			else return prioQueue.removeMin();
		}
		
		for(int j = 0; j < 6; j++) {
			double prioNext = 0.0;
			Hexagon neighbour = currCell.getNeighbour(j);
			if(neighbour == null) continue;
			for(int x = 0; x < 6; x++) {
				Hexagon neighbourNext = neighbour.getNeighbour(x);
				if(neighbourNext == null) continue;
				else if(neighbourNext.isMarked()) continue;
				else if(neighbourNext.isAlligator()) continue;

				if(gatorCheck(neighbourNext) == true) {
					if(neighbourNext.isReedsCell()) {
						prioNext = 11.0;
						if(j == x) prioNext -= 0.5;
						prioQueue.add(neighbourNext, prioNext);
						continue;
					}else continue;
				}
				else if(neighbourNext.isMudCell()) continue;
				else if(neighbourNext instanceof FoodHexagon) {
					int flies = ((FoodHexagon) neighbourNext).getNumFlies();
					if(flies == 3) prioNext = 1.0;
					else if (flies == 2) prioNext = 2.0;
					else if (flies == 1) prioNext = 3.0;
					else prioNext = 7.0;
				}else if(neighbourNext.isLilyPadCell()) prioNext = 5.0;
				else if(neighbourNext.isEnd()) prioNext = 4.0;
				else if(neighbourNext.isReedsCell()) prioNext = 6.0;
				else if(neighbourNext.isWaterCell()) prioNext = 7.0;
				
				if(x == j)prioNext -= 0.5;
				
				prioQueue.add(neighbourNext, prioNext);
			}
			
		}
		
		if(prioQueue.isEmpty()) return null;
		else return prioQueue.removeMin();
		
	}
	
	/*
	 * This method finds the best path for Freddy to take on a map
	 * by repeatedly invoking "findBest" and putting previous spots
	 * Freddy has been in a stack in case Freddy has to backtrack.
	 * It returns a string of all the IDs of spots Freddy has been to
	 * or returns "No solution" if Freddy did not make it to Fran.
	 */
	public String findPath() {
		ArrayStack<Hexagon> s = new ArrayStack<Hexagon>();
		s.push(pond.getStart());
		pond.getStart().markInStack();
		
		int fliesEaten = 0;
		String str = "";
		
		while(!s.isEmpty()) {
			Hexagon curr = s.peek();
			str += curr.getID() + " ";
			
			if(curr.isEnd()) break;
			if(curr instanceof FoodHexagon) {
				fliesEaten += ((FoodHexagon) curr).getNumFlies();
				((FoodHexagon) curr).removeFlies();
			}
			
			Hexagon next = findBest(curr);
			
			if(next == null) {
				s.pop();
				curr.markOutStack();
			}else {
				s.push(next);
				next.markInStack();
			}
		}
		
		if(s.isEmpty()) str = "No solution";
		else str += "ate " + fliesEaten + " flies";
		
		return str;
	}
	
public static void main(String[] args) {
		
		FrogPath p = new FrogPath("pond7.txt");
		
		p.findPath();
		
	}

}
