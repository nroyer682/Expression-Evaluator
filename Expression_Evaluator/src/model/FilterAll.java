package model;

public class FilterAll extends SeqEvaluator {

	public FilterAll(int maxOperations) {
		super(maxOperations);
	}
	
	public boolean[] getFilter() {
		boolean[] filter = new boolean[this.noo];
		for (int i = 0; i < this.noo; i++) {
			if (this.operations[i] instanceof OccursWithin) {
				filter[i] = ((OccursWithin) this.operations[i]).occursWithin();
			}
		}
		return filter;
	}
	
	public int getNumberOfIncompatibleOperations() {
		int noi = 0; // number of incompatible operations
		for (int i = 0; i < this.noo; i++) {
			if (!(this.operations[i] instanceof OccursWithin)) {
				noi++;
			}
		}
		return noi;
	}
	
	public String getEvaluation() {
		String list = "";
		for (int i = 0; i < this.noo; i++) {	
			if (this.getFilter()[i] == false) {
				list += "_";
			}
			else {
				list += this.getFilter()[i];
			}
			if (i < this.noo - 1) {
				list += ", ";
			}
		}
		return list;
	}

	public String toString() {
		/*
		 * The result of FilterAll indicates the resulting value of each added operation.
		 * For example: 
		 * 	- 1st added operation results in true 	(because seq1 occurs within seq4).
		 * 	- 2nd added operation results in _ 		(i.e., filtered out, because seq2 does not occur within seq4).
		 * 	- 3rd added operation results in true 	(because seq3 occurs within seq4). 
		 */
		String result = null;
		if (this.getNumberOfIncompatibleOperations() == 0) {
			result = String.format("Filter result is: %s", this.getEvaluation());
		}
		else {
			result = String.format("Filter cannot be evaluated due to %d incompatile operations.", this.getNumberOfIncompatibleOperations());
		}
		return result;
	}
}
