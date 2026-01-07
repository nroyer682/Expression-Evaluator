package model;

public abstract class SeqOperation {
	protected int[] seq1;
	
	SeqOperation(int[] seq1) {
		this.seq1 = seq1;
	}
	
	public String getSeq(int[] seq) {
		String seqList = "[";
		for (int i = 0; i < seq.length; i++) {
			seqList += seq[i];
			if (i < seq.length - 1) {
				seqList += ", ";
			}
		}
		seqList += "]";
		return seqList;
	}
	
	public abstract String toString();
	
	
}
