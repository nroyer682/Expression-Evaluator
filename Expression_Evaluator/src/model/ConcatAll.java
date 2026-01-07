package model;

public class ConcatAll extends SeqEvaluator {

	public ConcatAll(int maxOperations) {
		super(maxOperations);
	}
	
	public String getEvaluation() {
		String list = "[";
		boolean isEmpty = false;
		for (int i = 0; i < this.noo; i++) {
			SeqOperation o = this.operations[i];
			if (i > 0 && i < this.noo - 1 && !isEmpty) {
				list += ", ";
			}
			if (o instanceof Projection) {
				Projection p = (Projection) o;
				list += p.getSeq(p.getProjection()).substring(1, p.getSeq(p.getProjection()).length() - 1);
				if (p.getProjection().length == 0) {
					isEmpty = true;
				}
				else {
					isEmpty = false;
				}
			}
			if (o instanceof SumsOfPrefixes) {
				SumsOfPrefixes s = (SumsOfPrefixes) o;
				list += s.getSeq(s.getSums()).substring(1, s.getSeq(s.getSums()).length() - 1);
				if (s.getSums().length == 0) {
					isEmpty = true;
				}
				else {
					isEmpty = false;
				}
			}
			
		}
		list += "]";
		return list;
	}
	
	public int getNumberOfIncompatibleOperations() {
		int noi = 0; // number of incompatible operations
		for (int i = 0; i < this.noo; i++) {
			if (this.operations[i] instanceof OccursWithin) {
				noi++;
			}
		}
		return noi;
	}
	
	public String toString() {
		String result = null;
		if (this.getNumberOfIncompatibleOperations() == 0) {
			String list = "";
			for (int i = 0; i < this.noo; i++) {
				if (this.operations[i] instanceof Projection) {
					Projection p = (Projection) this.operations[i];
					list += p.getSeq(p.getProjection());
				}
				else if (this.operations[i] instanceof SumsOfPrefixes) {
					SumsOfPrefixes s = (SumsOfPrefixes) this.operations[i];
					list += s.getSeq(s.getSums());
				}
				if (i < this.noo - 1) {
					list += ", ";
				}
			}
			result = String.format("Concat(%s) = %s", list, this.getEvaluation());
		}
		else {
			result = String.format("Concat cannot be evaluated due to %d incompatile operations.", this.getNumberOfIncompatibleOperations());
		}
		return result;
		
	}

}
