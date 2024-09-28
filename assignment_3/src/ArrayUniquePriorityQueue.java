/*
 * This class can create a UniquePriorityQueue 
 * using an array.It implements UniquePriorityQueueADT
 * and can be used with any data type.
 * 
 */


public class ArrayUniquePriorityQueue<T> implements UniquePriorityQueueADT<T>{
	//private instance variable
	private T[] queue;
	private double[] priority;
	private int count;

	/*
	 * Private helper method to expand the capacity of the queue by 5.
	 */
	private T[] expandCapacity(T[] orgArray) {
		T[] largerList = (T[]) new Object[orgArray.length + 5];
		for(int i = 0; i < orgArray.length; i++) {
			largerList[i] = orgArray[i];
		}
		return largerList;
	}

	/*
	 * Private helper method to expand the capacity of priority by 5.
	 */
	private double[] expandCapacity(double[] orgArray) {
		double[] largerList = new double[orgArray.length + 5];
		for (int i = 0; i < orgArray.length; i++) {
			largerList[i] = orgArray[i];
		}

		return largerList;
	}
	
	//constructor that initializes the variables
	public ArrayUniquePriorityQueue() {
		this.queue = (T[])new Object[10];
		this.priority = new double[10];
		this.count = 0;		
	}
	
	/*
	 * This method adds a data type and it's priority to the queue.
	 * It shifts the arrays accordingly depending on the priority given.
	 */
			
	@Override
	public void add(T data, double prio) {
		for(int i = 0; i < count; i++) {
			if(queue[i].equals(data)) return;
		}

		if(count == queue.length) {
			queue = expandCapacity(queue);
			priority = expandCapacity(priority);
		}
		
		int i = 0;
		while(i < count && prio >= priority[i]) {
			i++;
		}

		for(int j = count; j > i ; j--) {
				priority[j] = priority[j-1];
				queue[j] = queue[j-1];
			}

		
		priority[i] = prio;
		queue[i] = data;
		count++;
	}
	
	/*
	 * This method checks if a specific data inputed can be found in 
	 * the queue. Returns true or false.
	 */
	@Override
	public boolean contains(T data) {
		for(int i = 0; i < count; i++) {
			if(queue[i].equals(data)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * This method peeks at the first value of the queue but does
	 * not remove it. It returns the value of the first element.
	 * If the queue is empty, it will throw an exception.
	 */
	@Override
	public T peek() throws CollectionException{
		if(count == 0)throw new CollectionException("PQ is empty");
		else return queue[0];
	}
	/*
	 * This method removes the first value of the queue and shifts 
	 * all values over. It returns the value of the first element.
	 * If the queue is empty, it will throw an exception.
	 */
	@Override
	public T removeMin()throws CollectionException {
		if(count == 0) throw new CollectionException("PQ is empty");
		
		T min = queue[0];
		
		for(int i = 1; i < count; i++) {
			queue[i-1] = queue[i];
			priority[i-1] = priority[i];
		}
		
		count--;
		return min;
	}
	
	/*
	 * This method updates the priority of a data item in the queue
	 * given the data item to update and the new priority.
	 * If the queue is empty of the item is not found, it will throw an exception.
	 * It updates the position of the data item in the queue based on it's 
	 * new priority.
	 */
	@Override
	public void updatePriority(T data, double newPrio) throws CollectionException{
		if(count == 0) throw new CollectionException("PQ is empty");
		
		boolean found = false;
		int j = 0;
		
		for(int i = 0; i < count ; i++) {
			if(queue[i].equals(data)) {
				found = true;
				j = i;
				break;
			}
		}
		
		if (!found) throw new CollectionException("Item not found in PQ");



		for(int x = j + 1; x < count ; x++) {
			queue[x-1] = queue[x];
			priority[x-1] = priority[x];
		}
		count--;
		add(data,newPrio);
		
	}
	
	//checks if the queue is empty. returns true or false.
	@Override
	public boolean isEmpty() {
        return count == 0;
	}
	
	//returns the size of the queue
	@Override
	public int size() {
		return count;
	}
	
	//returns the length of the array
	public int getLength() {
		return queue.length;
	}
	
	/*
	 * Returns a string representation of the queue.
	 * If the queue is empty, it will return "The PQ is empty"
	 */
	@Override
	public String toString() {
		if(count == 0) return "The PQ is empty";
		String str = "";
		int i;
		for(i = 0; i < count - 1; i++) {
			str += queue[i] + " ";
			str += "[" + priority[i] + "], ";
		}
		str += queue[i] + " " + "[" + priority[i] + "]";
		return str;
	}

}
