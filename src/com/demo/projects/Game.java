package com.demo.projects;
public class Game extends Item {
	private String[] platforms;
	private boolean extended;
	private static final int RENTAL_PERIOD = 22;
	private static final int RENTAL_FEE = 20;

	public Game(String id, String title, String genre, String description, String[] platforms) {
		super("G_" + id, title, genre, description, RENTAL_FEE);
		this.platforms = platforms;
		extended = false;
	}

	public String[] getPlatforms() {
		return this.platforms;
	}

	public boolean getExtended() {
		return extended;
	}

	public void setExtended(boolean extended) {
		this.extended = extended;
	}

	public int getRentalPeriod() {
		return RENTAL_PERIOD;
	}

	@Override
	public double borrow(String memberId, DateTime borrowDate) {
		return super.borrow(memberId, borrowDate);
	}

	@Override
	public double returnItem(DateTime returnDate) {
		double lateFee = 0;
		int diffDays = 0;
		int weeks = 0;
		HiringRecord currentlyBorrowed = super.getCurrentlyBorrowed();

		if (currentlyBorrowed == null) {
			System.out.println("The game is not on loan.");
		} else {
			diffDays = DateTime.diffDays(returnDate, currentlyBorrowed.getBorrowDate());

			if (diffDays < 0) {
				System.out.println("Borrow date should be equal or less than return date.");
			}
		}

		if (currentlyBorrowed == null || diffDays < 0) {
			return Double.NaN;
		}

		weeks = (int) Math.ceil(1 / 7) * diffDays;
		
		System.out.println("no of weeks in Game return class: " + weeks);

		if (diffDays > RENTAL_PERIOD) {
			if (diffDays - RENTAL_PERIOD < 7) {
				lateFee = diffDays - RENTAL_PERIOD;
			} else {
				lateFee = diffDays += (weeks) * 5;

			}
			if (extended) {
				lateFee /= 2;
			}

		}

		currentlyBorrowed.returnItem(returnDate, lateFee);
		super.setCurrentlyBorrowed(null);
		return lateFee;
	}

	private String getPlatformsInString() {
		String platformsInString = "";

		for (int i = 0; i < platforms.length; i++) {
			if (platforms[i] == null) {
				break;
			}
			
			platformsInString += platforms[i] + ", ";
			
		}
		return platformsInString.substring(0, platformsInString.length()-2);
	}

	@Override
	public String getDetails() {
		String onLoan;

		if (super.getCurrentlyBorrowed() != null && extended == false) {
			onLoan = "YES";
		} else if (extended) {
			onLoan = "EXTENDED";
		} else {
			onLoan = "NO";
		}

		String details = new String();
		details += super.getDetails();

		details += String.format("%-25s%s\n", "Platforms: ", getPlatformsInString());
		details += String.format("%-25s%s\n", "Rental Period: ", RENTAL_PERIOD + " days");
		details += String.format("%-25s%s\n", "On loan: ", onLoan);

		HiringRecord[] hireHistory = super.getHireHistory();

		if (hireHistory[0] != null) {
			details += String.format("BORROWING RECORD\n");
			details += String.format("-----------------------------------------\n");

			for (int i = 0; i < hireHistory.length; i++) {
				if (hireHistory[i] == null) {
					break;
				} else {
					// System.out.println("i: " + i);
					details += hireHistory[i].getDetails();
					details += String.format("-----------------------------------------\n");
				}
			}
		}

		return details.toString();

	}
	
}
