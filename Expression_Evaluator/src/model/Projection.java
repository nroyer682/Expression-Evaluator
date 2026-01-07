package model;

public class Projection extends BinarySeqOperation {

	public Projection(int[] seq1, int[] seq2) {
		super(seq1, seq2);
	}
	
	public int[] getProjection() {
		int count = 0;
		int[] indices = new int[this.seq2.length];
		
		for (int i = 0; i < this.seq2.length; i++) {
			boolean found = false;
			for (int j = 0; !found && j < this.seq1.length; j++) {
				if (this.seq2[i] == this.seq1[j]) {
					found = true;
					indices[count] = i;
					count++;
				}
			}
		}
		int[] projection = new int[count];
		for (int i = 0; i < count; i++) {
			projection[i] = this.seq2[indices[i]];
		}
		return projection;
	}
	
	public String toString() {
		return String.format("Projecting %s to %s results in: %s", this.getSeq(this.seq1), this.getSeq(this.seq2), this.getSeq(this.getProjection()));
	}

}
