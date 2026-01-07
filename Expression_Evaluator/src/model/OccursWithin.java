package model;

public class OccursWithin extends BinarySeqOperation {

	public OccursWithin(int[] seq1, int[] seq2) {
		super(seq1, seq2);
	}
	
	public boolean occursWithin() {
		int i = 0;
		boolean found = false;
		if (this.seq1.length == 0) {
			return true;
		}
		for (; !found && i < this.seq2.length; i++) {
			if (this.seq2[i] == this.seq1[0] && this.seq2.length - i >= this.seq1.length) {
				found = true;
				for (int j = 1; found && j < this.seq1.length; j++) {
					if (this.seq1[j] != this.seq2[i + j]) {
						found = false;
					}
				}
			}
		}
		return found;
	}
	
	public String toString() {
		String result = null;
		if (this.occursWithin()) {
			result = String.format("%s occurs within %s", this.getSeq(this.seq1), this.getSeq(this.seq2));
		}
		else {
			result = String.format("%s does not occur within %s", this.getSeq(this.seq1), this.getSeq(this.seq2));
		}
		return result;
	}

}
