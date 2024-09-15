/*
 * CS1027B 250966670 rscully5
 * Rosaline Scully
 * March 29, 2024
 * This class makes a tree of QTreeNodes.
 */
public class QuadrantTree {

	//instance variable for the root of the tree
	private QTreeNode root;

	//constructor that builds the tree
	public QuadrantTree(int[][] thePixels) {

		root = buildTree(thePixels, 0,0,thePixels.length);
	}
	
	//private method that does the recursive work of building the tree
	private QTreeNode buildTree(int[][] pixels, int x, int y, int size) {
		QTreeNode R = new QTreeNode();
		R.setSize(size);

		if(R.getSize() == 1) {
			R.setColor(Gui.averageColor(pixels,0,0,size));
			R.setx(x);
			R.sety(y);
			return R;
		}
		else {
			R.setx(x);
			R.sety(y);
			int newSize = R.getSize() / 2;
			R.setColor(Gui.averageColor(pixels,0,0,size));

			int[][] Q1 = new int[newSize][newSize];
			int[][] Q2 = new int[newSize][newSize];
			int[][] Q3 = new int[newSize][newSize];
			int[][] Q4 = new int[newSize][newSize];


			for (int i = 0; i < newSize; i++) {
				for (int j = 0; j < newSize; j++) {
					Q1[i][j] = pixels[i][j];
					Q2[i][j] = pixels[i][j + newSize];
					Q3[i][j] = pixels[i + newSize][j];
					Q4[i][j] = pixels[i + newSize][newSize + j];

				}
			}

			R.setChild(buildTree(Q1, x, y, newSize), 0);
			R.getChild(0).setParent(R);
			R.setChild(buildTree(Q2, x + newSize, y , newSize), 1);
			R.getChild(1).setParent(R);
			R.setChild(buildTree(Q3, x, y + newSize, newSize), 2);
			R.getChild(2).setParent(R);
			R.setChild(buildTree(Q4, x + newSize, y + newSize, newSize), 3);
			R.getChild(3).setParent(R);
		}


		return R;

	}
	
	//returns the root of this QuadrantTree
	public QTreeNode getRoot() {
		return this.root;
	}

	/*
	 * Returns a list of all the QTreeNodes at a specific level.
	 */
	public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
		if (r == null) {
			return null;
		}
		if (r.isLeaf() || theLevel == 0) {
			return new ListNode<>(r);
		} else {

			ListNode<QTreeNode> childList1 = getPixels(r.getChild(0), theLevel - 1);
			ListNode<QTreeNode> childList2 = getPixels(r.getChild(1), theLevel - 1);
			ListNode<QTreeNode> childList3 = getPixels(r.getChild(2), theLevel - 1);
			ListNode<QTreeNode> childList4 = getPixels(r.getChild(3), theLevel - 1);

			ListNode<QTreeNode> currNode = childList1;

			while (currNode.getNext() != null) {
				currNode = currNode.getNext();
			}
			currNode.setNext(childList2);
			while (currNode.getNext() != null) {
				currNode = currNode.getNext();
			}
			currNode.setNext(childList3);
			while (currNode.getNext() != null) {
				currNode = currNode.getNext();
			}
			currNode.setNext(childList4);


			return childList1;
		}
	}

	/*
	 * Returns an object of the class Duple which contains
	 * a list of all the nodes in this Quadrant Tree which have
	 * a similar color to the one specified, at a level specified.
	 * And the number of nodes in the list.
	 */
	public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
		if(r == null) return null;

		if(r.isLeaf() || theLevel <= 0) {
			if(Gui.similarColor(r.getColor(), theColor)) {
				return new Duple(new ListNode<>(r),1);
			} else{
				return new Duple(null,0);
			}
		}
		ListNode<QTreeNode> similar = new ListNode<>(null);
		ListNode<QTreeNode> tail = similar;
		int count = 0;

		for (int i = 0; i < 4; i++) {
			Duple match = findMatching(r.getChild(i),theColor,theLevel-1);
			if(match.getFront() != null){
				ListNode<QTreeNode> curr = match.getFront();
				while(curr != null){
					ListNode<QTreeNode> newNode = new ListNode<>(curr.getData());
					tail.setNext(newNode);
					tail = newNode;
					curr = curr.getNext();

				}
				count += match.getCount();
			}
		}
		return new Duple(similar.getNext(),count);
    }

	/*
	 * Returns a QTreeNode at a specific level that represents a quadrant
	 * containing point (x,y) if the point exists. If not, it will return null.
	 */
	public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
		if (r == null) {
			return null;
		}

		if (r.getx() == x && r.gety() == y) {
			return r;
		}

		if (x < r.getx() + r.getSize() / 2 && y < r.gety() + r.getSize() / 2) {
			return findNode(r.getChild(0), theLevel - 1, x, y);
		} else if (x >= r.getx() + r.getSize() / 2 && y < r.gety() + r.getSize() / 2) {
			return findNode(r.getChild(1), theLevel - 1, x, y);
		} else if (x < r.getx() + r.getSize() / 2 && y >= r.gety() + r.getSize() / 2) {
			return findNode(r.getChild(2), theLevel - 1, x, y);
		} else {
			return findNode(r.getChild(3), theLevel - 1, x, y);
		}
	}


}
