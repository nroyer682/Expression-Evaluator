package model;

public abstract class SeqEvaluator {
	protected int maxOperations;
	protected SeqOperation[] operations;
	protected int noo; // number of operations
	
	public SeqEvaluator(int maxOperations) {
		this.maxOperations = maxOperations;
		this.operations = new SeqOperation[this.maxOperations];
		this.noo = 0;
	}

	public void addOperation(String operation, int[] seq1, int[] seq2) throws IllegalOperationException {
		/* 
		 * Add the 1st operation as a projection, which  
		 * 	takes `seq1` and `seq2` as inputs and results in another sequence.
		 * 
		 * Assume that when "op:projection" is specified, 
		 * 	both the second and third arguments are always non-null.
		 */
		
		if (operation.contains("projection")) {
			this.operations[this.noo] = new Projection(seq1, seq2);
			this.noo++;
		}
		else if (operation.contains("sumsOfPrefixes")) {
			this.operations[this.noo] = new SumsOfPrefixes(seq1);
			this.noo++;
		}
		else if (operation.contains("occursWithin")) {
			this.operations[this.noo] = new OccursWithin(seq1, seq2);
			this.noo++;
		}
		else {
			throw new IllegalOperationException("Invalid operation.");
		}
	}
	
	public abstract int getNumberOfIncompatibleOperations();
}
