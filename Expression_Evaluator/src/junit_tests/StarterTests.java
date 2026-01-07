package junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.BinarySeqOperation;
import model.ConcatAll;
import model.FilterAll;
import model.IllegalOperationException;
import model.OccursWithin;
import model.Projection;
import model.SeqEvaluator;
import model.SeqOperation;
import model.SumsOfPrefixes;

public class StarterTests {

	/*
	 * Tests related to the Projection class.
	 */ 
	
	@Test
	public void test_projection_01() {
		int[] seq1a = {1, 3, 5};
		int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
		
		/*
		 * Project the 1st sequence to the 2nd sequence:
		 * 	The resulting sequence contains 
		 * 		all those elements from the 2nd sequence which also appear in the 1st sequence,
		 * 		while maintaining their order. 
		 */
		BinarySeqOperation binOp = new Projection(seq1a, seq2);
		SeqOperation op = binOp; // 2nd sequence may contain duplicates
		assertEquals("Projecting [1, 3, 5] to [2, 1, 6, 3, 1, 4, 5, 3] results in: [1, 3, 1, 5, 3]", op.toString());
		
		op = new Projection(seq2, seq1a); // 1st sequence may contain duplicates
		assertEquals("Projecting [2, 1, 6, 3, 1, 4, 5, 3] to [1, 3, 5] results in: [1, 3, 5]", op.toString());
		
		/* more examples on sequence projections */
		
		int[] seq1b = {1, 3, 5, 3}; 
		op = new Projection(seq1b, seq2);
		assertEquals("Projecting [1, 3, 5, 3] to [2, 1, 6, 3, 1, 4, 5, 3] results in: [1, 3, 1, 5, 3]", op.toString());
		
		op = new Projection(seq2, seq1b);
		assertEquals("Projecting [2, 1, 6, 3, 1, 4, 5, 3] to [1, 3, 5, 3] results in: [1, 3, 5, 3]", op.toString());
		
		/*
		 * You may want to also test:
		 * 	1) Projecting an empty seq1 [] over a non-empty seq2 should result in an empty sequence.
		 * 	2) Projecting a non-empty seq1 over an empty seq2 [] should result in an empty sequence.  
		 */
		
		int[] seq1c = {};
		op = new Projection(seq1c, seq2);
		assertEquals("Projecting [] to [2, 1, 6, 3, 1, 4, 5, 3] results in: []", op.toString());
		
		op = new Projection(seq2, seq1c);
		assertEquals("Projecting [2, 1, 6, 3, 1, 4, 5, 3] to [] results in: []", op.toString());
	}
	
	/*
	 * Tests related to the OccursWithin class.
	 */
	
	@Test
	public void test_occurs_within_01() {
		int[] seq1a = {1, 6, 1};
		int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
		
		/*
		 * Does the 1st sequence appear as part of the 2nd sequence? 
		 */
		BinarySeqOperation binOp = new OccursWithin(seq1a, seq2);
		SeqOperation op = binOp;
		assertEquals("[1, 6, 1] does not occur within [2, 1, 6, 3, 1, 4, 5, 3]", op.toString());
		
		int[] seq1b = {3, 1, 4, 5}; 
		op = new OccursWithin(seq1b, seq2);
		assertEquals("[3, 1, 4, 5] occurs within [2, 1, 6, 3, 1, 4, 5, 3]", op.toString());
		
		/*
		 * You may want to also test:
		 * 	1) An empty seq1 [] occurs within any seq2 (either empty or non-empty).
		 * 	2) A non-empty seq1 does not occur within an empty seq2 []. 
		 * 	3) A sequence does not occur within another shorter sequence.  
		 */
		int[] seq1c = {};
		op = new OccursWithin(seq1c, seq2);
		assertEquals("[] occurs within [2, 1, 6, 3, 1, 4, 5, 3]", op.toString());
		
		op = new OccursWithin(seq1c, seq1c);
		assertEquals("[] occurs within []", op.toString());
		
		op = new OccursWithin(seq2, seq1c);
		assertEquals("[2, 1, 6, 3, 1, 4, 5, 3] does not occur within []", op.toString());
		
		int[] seq1d = {2, 1, 6};
		op = new OccursWithin(seq2, seq1d);
		assertEquals("[2, 1, 6, 3, 1, 4, 5, 3] does not occur within [2, 1, 6]", op.toString());
		
	}
	
	/*
	 * Tests related to the SumsOfPrefixes class.
	 */
	
	@Test
	public void test_sums_of_prefixes_01() {
		int[] seq1 = {1};
		/*
		 * `seq1` has prefixes: [], [1]
		 * Produce another sequence where each element is the sum of each prefix. 
		 */
		SeqOperation op = new SumsOfPrefixes(seq1);
		
		assertEquals("Sums of prefixes of [1] is: [0, 1]", op.toString());
		
		int[] seq2 = {1, 6};
		/*
		 * `seq2` has prefixes: [], [1], [1, 6]
		 * Produce another sequence where each element is the sum of each prefix. 
		 */
		op = new SumsOfPrefixes(seq2);
		assertEquals("Sums of prefixes of [1, 6] is: [0, 1, 7]", op.toString());
		
		int[] seq3 = {1, 6, 1};
		/*
		 * `seq3` has prefixes: [], [1], [1, 6], [1, 6, 1]
		 * Produce another sequence where each element is the sum of each prefix. 
		 */
		op = new SumsOfPrefixes(seq3);
		assertEquals("Sums of prefixes of [1, 6, 1] is: [0, 1, 7, 8]", op.toString());
	}
	
	/*
	 * Tests related to the ConcatAll class.
	 */
	
	@Test
	public void test_contact_all_01() {
		/*
		 * Create a ConcatAll evaluator which can hold
		 * 	no more than 10 sequence operations.
		 */
		SeqEvaluator evaluator = new ConcatAll(10);
		
		int[] seq1 = {1, 3, 5};
		int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
		int[] seq3 = {7, 8};
		try {
			/* 
			 * Add the 1st operation as a projection, which  
			 * 	takes `seq1` and `seq2` as inputs and results in another sequence.
			 * 
			 * Assume that when "op:projection" is specified, 
			 * 	both the second and third arguments are always non-null.
			 */
			evaluator.addOperation("op:projection", seq1, seq2);
			
			/* 
			 * Add the 2nd operation as a sum of prefixes, which  
			 * 	takes `seq1` as input and results in another sequence.
			 * 
			 * Assume that when "op:sumsOfPrefixes" is specified, 
			 * 	the third argument is always null.
			 */
			evaluator.addOperation("op:sumsOfPrefixes", seq1, null);
			
			/*
			 * Add the 3rd operation.
			 */
			evaluator.addOperation("op:projection", seq3, seq2);
			
			/*
			 * The result of ConcatAll is the concatenation of the resulting sequences from the added operations.
			 * For example: 
			 * 	- 1st added operation (projection) results in [1, 3, 1, 5, 3].
			 * 	- 2nd added operation (sum of prefixes) results in [0, 1, 4, 9].
			 * 	- 3rd added operation (projection) results in [].
			 * 	- The concatenation of these three resulting sequences is as shown in the expected string below. 
			 */
			assertEquals("Concat([1, 3, 1, 5, 3], [0, 1, 4, 9], []) = [1, 3, 1, 5, 3, 0, 1, 4, 9]", evaluator.toString());
		}
		catch(IllegalOperationException e) {
			fail();
		}
	}
	
	@Test
	public void test_contact_all_02() {
		/*
		 * Create a ConcatAll evaluator which can hold
		 * 	no more than 10 sequence operations.
		 */
		SeqEvaluator evaluator = new ConcatAll(10);
		
		int[] seq1 = {1, 3, 5};
		int[] seq2 = {2, 1, 6, 3, 1, 4, 5, 3};
		int[] seq3 = {7, 8};
		try {
			/* 
			 * Add the 1st operation which results in another sequence.
			 */
			evaluator.addOperation("op:projection", seq1, seq2);
			
			/*
			 * Add the 2nd operation which does NOT result in another sequence.
			 * This operation is incompatible with ConcatAll.
			 */
			evaluator.addOperation("op:occursWithin", seq1, seq3);
			
			/* 
			 * Add the 3rd operation which results in another sequence.
			 */
			evaluator.addOperation("op:sumsOfPrefixes", seq1, null);
			
			/*
			 * Add the 4th operation which does NOT result in another sequence.
			 * This operation is incompatible with ConcatAll.
			 */
			evaluator.addOperation("op:occursWithin", seq3, seq2);
			
			/*
			 * ConcatAll can only function when each of the added operation results in a sequence.
			 * Otherwise, report how many such incompatible operations (each of which not resulting in a sequence) there are.  
			 */
			assertEquals("Concat cannot be evaluated due to 2 incompatile operations.", evaluator.toString());
		}
		catch(IllegalOperationException e) {
			fail();
		}
	}
	
	/*
	 * Tests related to the FilterAll class.
	 */
	
	@Test
	public void test_filter_all_01() {
		/*
		 * Create a FilterAll evaluator which can hold
		 * 	no more than 10 sequence operations.
		 */
		SeqEvaluator evaluator = new FilterAll(10);
		
		int[] seq1 = {1, 6, 3, 1};
		int[] seq2 = {7, 8};
		int[] seq3 = {4, 5, 3};
		int[] seq4 = {2, 1, 6, 3, 1, 4, 5, 3};
		try {
			/* 
			 * Add the 1st operation, which  
			 * 	takes `seq1` and `seq2` as inputs and does not result in a sequence.
			 * 
			 * Assume that when "op:occursWithin" is specified, 
			 * 	both the second and third arguments are always non-null.
			 */
			evaluator.addOperation("op:occursWithin", seq1, seq4);
			
			/* 
			 * Add the 2nd operation.
			 */
			evaluator.addOperation("op:occursWithin", seq2, seq4);
			
			/*
			 * Add the 3rd operation.
			 */
			evaluator.addOperation("op:occursWithin", seq3, seq4);
			
			/*
			 * The result of FilterAll indicates the resulting value of each added operation.
			 * For example: 
			 * 	- 1st added operation results in true 	(because seq1 occurs within seq4).
			 * 	- 2nd added operation results in _ 		(i.e., filtered out, because seq2 does not occur within seq4).
			 * 	- 3rd added operation results in true 	(because seq3 occurs within seq4). 
			 */
			assertEquals("Filter result is: true, _, true", evaluator.toString());
		}
		catch(IllegalOperationException e) {
			fail();
		}
	}
	
	@Test
	public void test_filter_all_02() {
		/*
		 * Create a FilterAll evaluator which can hold
		 * 	no more than 10 sequence operations.
		 */
		SeqEvaluator evaluator = new FilterAll(10);
		
		int[] seq1 = {1, 6, 3, 1};
		int[] seq2 = {7, 8};
		int[] seq3 = {4, 5, 3};
		int[] seq4 = {2, 1, 6, 3, 1, 4, 5, 3};
		try {
			/* 
			 * Add the 1st operation which results in a true/false value.
			 */
			evaluator.addOperation("op:occursWithin", seq1, seq4);
			
			/*
			 * Add the 2nd operation which does NOT result in a true/false value.
			 * This operation is incompatible with FilterAll.
			 */
			evaluator.addOperation("op:projection", seq1, seq3);
			
			/*
			 * Add the 3rd operation which does NOT result in a true/false value.
			 * This operation is incompatible with FilterAll.
			 */
			evaluator.addOperation("op:sumsOfPrefixes", seq1, null);
			
			/* 
			 * Add the 4th operation which results in a true/false value.
			 */
			evaluator.addOperation("op:occursWithin", seq2, seq4);
			
			/*
			 * FilterAll can only function when each of the added operation results in a true/false value.
			 * Otherwise, report how many such incompatible operations (each of which not resulting in a true/false value) there are.  
			 */
			assertEquals("Filter cannot be evaluated due to 2 incompatile operations.", evaluator.toString());
		}
		catch(IllegalOperationException e) {
			fail();
		}
	}
	
	/*
	 * More tests related to the ContactAll and FilterAll classes.
	 */
	
	@Test
	public void test_contact_all_03() {
		/*
		 * Create a ContactAll evaluator which can hold
		 * 	no more than 10 sequence operations.
		 */
		SeqEvaluator evaluator = new ConcatAll(10);
		
		int[] seq1 = {1, 6, 3, 1};
		int[] seq2 = {7, 8};
		try {
			/* 
			 * It is assumed that the operation to add can only be one of the following:
			 * 	- op:occursWithin
			 * 	- op:projection
			 * 	- op:sumsOfPrefixes
			 */
			evaluator.addOperation("op:someInvalidOp1", seq1, seq2);
			fail();
		}
		catch(IllegalOperationException e) {
			
		}
	}
	
	@Test
	public void test_filter_all_03() {
		/*
		 * Create a FilterAll evaluator which can hold
		 * 	no more than 10 sequence operations.
		 */
		SeqEvaluator evaluator = new FilterAll(10);
		
		int[] seq1 = {1, 6, 3, 1};
		int[] seq2 = {7, 8};
		try {
			/* 
			 * It is assumed that the operation to add can only be one of the following:
			 * 	- op:occursWithin
			 * 	- op:projection
			 * 	- op:sumsOfPrefixes
			 */
			evaluator.addOperation("op:someInvalidOp2", seq1, seq2);
			fail();
		}
		catch(IllegalOperationException e) {
			
		}
	}
}
