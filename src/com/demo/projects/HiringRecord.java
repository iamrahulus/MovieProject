package com.demo.projects;

/*
* Class: HiringRecord
* Description: The class represents a single hiring record for
* any type of item that can be hired.
* Author: Labiba Islam - 3694372
*/
public class HiringRecord {

	private String id;
	private double rentalFee;
	private double lateFee;
	private DateTime borrowDate;
	private DateTime returnDate;

	public HiringRecord(String mvId, String memberId, double rentalFee, DateTime borrowDate) {
		this.id = mvId + "_" + memberId + "_" + borrowDate.getEightDigitDate();
		this.rentalFee = rentalFee;
		this.borrowDate = borrowDate;
		this.returnDate = null;
		this.lateFee = 0.0;
	}

	// public HiringRecord (String id, String memberId, DateTime borrowDate,
	// double fee)
	// {
	// this.id = id;
	// this.memberId = memberId;
	// this.borrowDate = borrowDate;
	// this.fee = fee;
	// }

	public String getId() {
		return id;
	}

	public double getRentalFee() {
		return rentalFee;
	}

	public double getLateFee() {
		return lateFee;
	}

	public DateTime getBorrowDate() {
		return borrowDate;
	}

	public DateTime getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(DateTime returnDate) {
		this.returnDate = returnDate;
	}

	public double returnItem(DateTime returnDate, double lateFee) { // throws
																	// Exception
																// {
		if (lateFee < 0) {
			// throw new Exception("Invalid Late Fee");//validation for late fee
			// should not be less than zero
			System.out.println("Invalid Late Fee");
		}
		this.returnDate = returnDate;
		this.lateFee = lateFee;
		return lateFee;
	}

	public String getDetails() {
		String details = new String();
		details += String.format("\n%-25s%s\n", "Hire Id: ", this.id); // format
																		// for
																		// movie
																		// currently
																		// on
																		// hire
		details += String.format("%-25s%s\n", "Borrow Date: ", this.borrowDate.getFormattedDate()); // format
																									// for
																									// movie
																									// that
																									// has
																									// been
																									// returned

		if (returnDate != null) {
			details += String.format("%-25s%s\n", "Return Date: ", returnDate.getFormattedDate());
			details += String.format("%-25s%s\n", "Fee:", "$" + rentalFee);
			details += String.format("%-25s%s\n", "Late Fee:", "$" + lateFee);
			details += String.format("%-25s%s\n", "Total Fees:", "$" + (lateFee + rentalFee));
		}
		return details.toString();
	}

	public String toString() {
		if (returnDate == null) {
			return id + ":" + borrowDate.getEightDigitDate() + "Invalid:Invalid:Invalid";
		} else {
			return id + ":" + borrowDate.getEightDigitDate() + ":" + returnDate.getEightDigitDate() + ":" + rentalFee
					+ ":" + lateFee;
		}

	}
}
