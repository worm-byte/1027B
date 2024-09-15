/*
 * CS1027B 250966670 rscully5
 * Rosaline Scully
 * March 29, 2024
 * This class makes a QTreeNode with setters 
 * and getters.
 */


public class QTreeNode {
	
	//instance variables for a QTreeNode
	private int x,y;
	private int size;
	private int color;
	private QTreeNode parent;
	private QTreeNode[] children;

	//default constructor if no values are given
	public QTreeNode() {
		this.parent = null;
		this.children = new QTreeNode[4];
		for(int i = 0; i < 4; i++) {
			children[i] = null;
		}
		this.x = 0;
		this.y = 0;
		this.size = 0;
		this.color = 0;
		
	}
	
	//second constructor if values are given
	public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
		this.parent = null;
		this.children = theChildren;
		this.x = xcoord;
		this.y = ycoord;
		this.size = theSize;
		this.color = theColor;
		
		
	}
	
	/*
	 * Checks if a point can be found on the quadrants
	 * represented by this object. Returns true or false.
	 */
	public boolean contains(int xcoord, int ycoord) {
		if(xcoord >= this.x && xcoord <= this.x+this.size-1 && ycoord >= this.y && ycoord <= this.y+this.size-1) {
			return true;
		}else return false;
	}
	
	//returns the value of x coordinate in top left corner of quadrant.
	public int getx() {
		return this.x;
	}
	
	//returns the value of y coordinate in top left corner of quadrant.
	public int gety() {
		return this.y;
	}
	
	//returns the size of the this quadrant
	public int getSize() {
		return this.size;
	}
	
	//returns the average color of pixels in this quadrant
	public int getColor() {
		return this.color;
	}
	
	//returns the parent of this node
	public QTreeNode getParent() {
		return this.parent;
	}
	
	//returns the children of this node
	public QTreeNode getChild(int index) throws QTreeException{
		if(index < 0 || index > 3) throw new QTreeException("Index out of bounds");
		if(children == null) throw new QTreeException("There are no children");
		return children[index];
	}
	
	//sets the x coordinate of the quadrant for this node
	public void setx(int newx) {
		this.x = newx;
	}
	
	//sets the y coordinate of the quadrant for this node
	public void sety(int newy) {
		this.y = newy;
	}
	
	//sets the size of the quadrant for this node
	public void setSize(int newSize) {
		this.size = newSize;
	}
	
	//sets the color of the quadrant for this node
	public void setColor(int newColor) {
		this.color = newColor;
	}
	
	//sets the parent of this node
	public void setParent(QTreeNode newParent) {
		this.parent = newParent;
	}
	
	//sets the children of this node
	public void setChild(QTreeNode newChild, int index)throws QTreeException {
		if(index < 0 || index > 3) throw new QTreeException("Index out of bounds");
		children[index] = newChild;
		
	}
	
	/*
	 * Checks if a node is a leaf (meaning no children) or not.
	 * Returns true or false.
	 */
	public boolean isLeaf() {
		if(children == null) return true;
		
		for(QTreeNode child:children) {
			if(child != null) return false;
		}
		
		return true;
	}

}
