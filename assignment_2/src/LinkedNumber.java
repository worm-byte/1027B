

/**
 * CS1027B Assignment 2 rscully5
 * February 18, 2024
 * This class makes a double linked number of any base between 2 - 16.
 * You can do things to the number, like getters, test if it's
 * valid for the base number, see if it equals another Linked Number, see 
 * a string representation of it, convert to a different base, add a digit, or 
 * remove a digit.
 */
public class LinkedNumber {
	
	//instance variables
	private int base;
	private DLNode<Digit> front;
	private DLNode<Digit> rear;
	
	//private method that converts a decimal number from a Linked Number to a non-decimal value in string form.
	private String decToNonDec(int newBase) {
		int val = Integer.parseInt(this.toString());
		
		StringBuilder newNum = new StringBuilder();
		while(val != 0) {
			int remainder = val%newBase;
			val = val/newBase;
			
			if(remainder == 10) {
				newNum.insert(0,"A");
			}
			else if(remainder == 11) {
				newNum.insert(0,"B");
			}
			else if(remainder == 12) {
				newNum.insert(0,"C");
			}
			else if(remainder == 13) {
				newNum.insert(0,"D");
			}
			else if(remainder == 14) {
				newNum.insert(0,"E");
			}
			else if(remainder == 15) {
				newNum.insert(0,"F");
			}
			else {
				newNum.insert(0,remainder);
			}
			
		}
		return newNum.toString();
	}
	
	//private method that converts a non-decimal number from a Linked Number to a decimal value in string form.
	private String nonDecToDec() {
		int val = 0;
		
		DLNode<Digit> currNode = rear;
		
		for(int p = 0; p < this.getNumDigits(); p++) {
			int valueNode = currNode.getElement().getValue();
			val += valueNode*Math.pow(this.base,p);
			currNode = currNode.getPrev();
		}
		
		String n = String.valueOf(val);
		return n;
	}
	
	//private method that converts a non-number from a Linked Number to a non-decimal value in string form.
	private String nonDecToNonDec(int newBase) {
		LinkedNumber temp = new LinkedNumber(nonDecToDec(),10);
		return temp.decToNonDec(newBase);
		
	}
	
	//constructor that takes a string value of a number, and the base
	public LinkedNumber(String num, int baseNum) throws LinkedNumberException{
		base = baseNum;
		
		if (num.isEmpty()) {
			throw new LinkedNumberException("no digits given");
		}else {
			char[] numArr = num.toCharArray();
			for (char digitOfNum:numArr) {
				Digit digit = new Digit(digitOfNum);
				DLNode<Digit> newNode = new DLNode<>(digit);
				if(front == null) {
					front = newNode;
					rear = newNode;
				}else {
					rear.setNext(newNode);
					newNode.setPrev(rear);
					rear = newNode;
				}
				
			}
		}
		
    }
	
	//overloaded constructor that will assume the number entered is decimal
	public LinkedNumber(int num) throws LinkedNumberException{
		base = 10;
		
		String numString = String.valueOf(num);
		if (numString.isEmpty()) {
			throw new LinkedNumberException("no digits given");
		}else {
			char[] numArr = numString.toCharArray();
			for (char digitOfNum:numArr) {
				Digit digit = new Digit(digitOfNum);
				DLNode<Digit> newNode = new DLNode<>(digit);
				if(front == null) {
					front = newNode;
					rear = newNode;
				}else {
					rear.setNext(newNode);
					newNode.setPrev(rear);
					rear = newNode;
				}
				
			}
		}
		
	}
	
	//checks if a number is actually valid for the base provided
	//will return either false or true
	public boolean isValidNumber() {
		DLNode<Digit> currNode = front;
		
		while(currNode != null) {
			Digit currData = currNode.getElement();
			int value = currData.getValue();
			
			if(value < 0 || value >= base) {
				return false;
			}
			
			currNode = currNode.getNext();
		}
		
		return true;
	}
	
	//returns the base
	public int getBase() {
		return base;
	}
	
	//returns the front node
	public DLNode<Digit> getFront(){
		return front;
	}
	
	//returns the rear node
	public DLNode<Digit> getRear(){
		return rear;
	}
	
	//returns the number of digits in the LinkedNumber
	public int getNumDigits() {
		int numNodes = 0;
		DLNode<Digit> currNode = front;
		while(currNode != null) {
			numNodes++;
			currNode = currNode.getNext();
		}
		
		return numNodes;
	}
	
	//returns a string representation of LinkedNumber
	public String toString() {
		StringBuilder numString = new StringBuilder();

		DLNode<Digit> currNode = front;
		
		while(currNode != null) {
			numString.append(currNode.getElement().toString());
			currNode = currNode.getNext();
		}
		String num = numString.toString();
		return num;
		
	}
	
	//checks if one LinkedNumber is equal to another in terms of value
	//returns false or true
	public boolean equals(LinkedNumber other) {
		if (this.base != other.base) {
			return false;
		}
		
		DLNode<Digit> currNode = this.front;
		DLNode<Digit> currNodeOther = other.front;
		
		while(currNode != null && currNodeOther != null) {
			int value = currNode.getElement().getValue();
			int valueOther = currNodeOther.getElement().getValue();
			
			if(value != valueOther) {
				return false;
			}
			
			currNode = currNode.getNext();
			currNodeOther = currNodeOther.getNext();
		}
		
		if(currNode != null || currNodeOther != null) {
			return false;
		}
		
		return true;
	}
	
	//converts a LinkedNumber to a different LinkedNumber according to newBase given
	//returns a new LinkedNumber
	public LinkedNumber convert(int newBase) throws LinkedNumberException {
		if (this.isValidNumber() == false) {
			throw new LinkedNumberException("cannot convert invalid number");
		}
		
		if(newBase > 16 || newBase < 2) {
			throw new LinkedNumberException("please enter a base between 2 and 16");
		}
		
		if (this.base == 10 && newBase != 10) {
			LinkedNumber newNum = new LinkedNumber(decToNonDec(newBase),newBase);
			return newNum;
		}
		else if(newBase == 10) {
			LinkedNumber newNum = new LinkedNumber(nonDecToDec(),newBase);
			return newNum;
		}else {
			LinkedNumber newNum = new LinkedNumber(nonDecToNonDec(newBase),newBase);
			return newNum;
		}
			
	}
	
	//adds a node to the LinkedNumber
	public void addDigit(Digit digit, int position) throws LinkedNumberException{
		int numNodes = this.getNumDigits();
		
		if(position < 0 || position > numNodes) {
			throw new LinkedNumberException("invalid position");
		}
		
		DLNode<Digit> newNode = new DLNode<>(digit);
		DLNode<Digit> currNode = rear;
		
		if (numNodes == 0) {
			front = rear = newNode;
			return;
		}
		
		if (position == 0) {
			newNode.setNext(rear.getNext());
			newNode.setPrev(rear);
			rear.setNext(newNode);
			rear = newNode;
		}
		else if(position == numNodes) {
			newNode.setNext(front);
			newNode.setPrev(front.getPrev());
			front.setPrev(newNode);
			front = newNode;
		}
		else {
			for (int p = 0; p < position-1; p++) {
				currNode = currNode.getPrev();
			}
			
			newNode.setNext(currNode);
			newNode.setPrev(currNode.getPrev());
			currNode.getPrev().setNext(newNode);
			currNode.setPrev(newNode);
		}
	}
	
	//removes a node from LinkedNumber
	//returns the value of the node removed as part of the whole number in decimal form
	public int removeDigit(int position) throws LinkedNumberException{
		int numNodes = this.getNumDigits();
		
		if(position < 0 || position >= numNodes) {
			throw new LinkedNumberException("invalid position");
		}
		
		int value = 0;
		
		DLNode<Digit> currNode = rear;

		if(position == 0){
			int temp = currNode.getElement().getValue();
			value += temp*Math.pow(this.base,0);
		}
		
		for(int p = 0; p < position; p++) {
			currNode = currNode.getPrev();

			if (p == position - 1) {
				int temp = currNode.getElement().getValue();
				value += temp*Math.pow(this.base,p+1);
			}
		}
		
		
		if(position == 0) {
			rear = rear.getPrev();
			if (rear != null) {
				rear.setNext(null);
			} else {
				front = null;
			}
		}
		else if(position == numNodes-1) {
			front = front.getNext();
			if (front != null) {
				front.setPrev(null);
			} else {
				rear = null;
			}
		}else {
			
			DLNode<Digit> nextNode = currNode.getNext();
		    DLNode<Digit> prevNode = currNode.getPrev();
		    prevNode.setNext(nextNode);
		    nextNode.setPrev(prevNode);
		}
		
		return value;
	}

}
