package model;

public class SumsOfPrefixes extends SeqOperation {
	
	public SumsOfPrefixes(int[] seq1) {
		super(seq1);
	}
	
	public int[][] getPrefixes() {
		int[][] prefixes = new int[this.seq1.length + 1][];
		
		for (int i = 0; i <= this.seq1.length; i++) {
			prefixes[i] = new int[i];
			for (int j = 0; j < i; j++) {
				prefixes[i][j] = this.seq1[j];
			}
		}
		return prefixes;
	}
	
	public int[] getSums() {
		int[] sums = new int[this.getPrefixes().length];
		sums[0] = 0;
		for (int i = 1, k = 1; i < this.getPrefixes().length; i++) {
			for (int j = 0; j < this.getPrefixes()[i].length; j++) {
				sums[k] += this.getPrefixes()[i][j];
			}
			k++;
		}
		return sums;
	}

	
	public String toString() {
		return String.format("Sums of prefixes of %s is: %s", this.getSeq(this.seq1), this.getSeq(this.getSums()));
	}

}
