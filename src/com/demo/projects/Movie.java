package com.demo.projects;
/*
* Class: Movie
* Description: The class represents pre-conditions 
* if the movie can be loaned or returned
* Author: Labiba Islam - 3694372
*/
public class Movie extends Item {
//	private String id;
//	private String title;
//	private String genre;
//	private String description;
	private boolean isNewRelease;
//	private HiringRecord currentlyBorrowed;
//	private HiringRecord[] hireHistory = new HiringRecord[10];
	private final double NEW_RELEASE_SURCHARGE = 2.00;
	//public boolean onLoan;
	
	
	public Movie(String id, String title, String genre, String description, boolean isNewRelease)
	{		
		
//		this.id = "M_" + id;
//		this.title=title;
//		this.genre=genre;
//		this.description=description;
		super("M_" + id, title, genre, description, isNewRelease? 5.00: 3.00);
		this.isNewRelease=isNewRelease;
//		this.currentlyBorrowed=null;
//		for (int i = 0; i < hireHistory.length;i++) {
//			hireHistory[i] = null;
//		}
		
	}
//	public String getId() {
//		return id;
//	}
//	public String getTitle() {
//		return title;
//	}
//	public String getGenre() {
//		return genre;
//	}
//	public String getDescription() {
//		return description;
//	}
	public boolean getIsNewRelease() {
		return isNewRelease;
	}
//	public HiringRecord getCurrentlyBorrowed() {
//		return currentlyBorrowed;
//	}
	
	@Override
	public double borrow(String memberId, DateTime borrowDate) {
//		
//		
//		if (isNewRelease == true) {
//			rentalFee = 5.00;
//		}else {
//			rentalFee = 3.00;
//		}
//		super.setRentalFee(rentalFee);
		
		return super.borrow(memberId, borrowDate);			
	}
	
	@Override
	public double returnItem(DateTime returnDate) {
    	
		HiringRecord currentlyBorrowed = super.getCurrentlyBorrowed();
		int diffDays = 0;
		
		
		if (currentlyBorrowed == null) {
    		System.out.println("The movie is not on loan.");
    	}
		else {
			diffDays = DateTime.diffDays(returnDate, currentlyBorrowed.getBorrowDate());
//	    	System.out.println("Difference  in days is " + diffDays);
	    	if(diffDays < 0) {
	    		System.out.println("Borrow date should be equal or less than return date.");
	    	}
		}    	
		
    	if (currentlyBorrowed == null || diffDays < 0) {
    		return Double.NaN;
    	//	System.out.println("The movie is not on loan.");
    	}
    	
    	double totalFee = 0;
    	double lateFee = 0;
    	//late fee for a movie is 50% of the rental fee for every day past the due date.
    	if (isNewRelease == false) {
    		if (diffDays > 7) {
    			lateFee = ((currentlyBorrowed.getRentalFee()) * 0.5 * (diffDays - 7)); //calculating late fee for weekly rentals
    			totalFee = 3.00 + lateFee;
    		}
    		else {
    			totalFee = 3.0;
    		}
    	}	
    	else {
			if (diffDays > 2) {
    			lateFee = ((currentlyBorrowed.getRentalFee()) * 0.5 * (diffDays - 2));//calculating late fee for new release rentals
    			totalFee = 5.0 + lateFee;
			}
			else {
				totalFee = 5.0;
			}
    	}
    	
    	currentlyBorrowed.returnItem(returnDate, lateFee);
    	// add to the hiring history
//    	super.addToHiringHistory(currentlyBorrowed);
    	super.setCurrentlyBorrowed(null);
    	
    	return lateFee;
    }	
    
	@Override
	public String getDetails() {
		String details = new String();
//		details += String.format("%-25s%s\n", "ID:" , getId());
//		details += String.format("%-25s%s\n", "Title:" , getTitle());
//		details += String.format("%-25s%s\n", "Genre:" , getGenre());
//		details += String.format("%-25s%s\n", "Description:" , getDescription());
//		details += String.format("%-25s%s\n", "Standard Fee" , isNewRelease ? "$5.00" : "$3.00");
		
		details += super.getDetails();
		details += String.format("%-25s%s\n\n", "On loan:" , (super.getCurrentlyBorrowed() != null ? "YES" : "NO"));
		details += String.format("%-25s%s\n", "Movie Type:" , isNewRelease ? "New Release" : "Weekly");
		details += String.format("%-25s%s\n\n", "Rental Period:" , isNewRelease ? "2 days" : "7 days");
		
		HiringRecord[] hireHistory = super.getHireHistory();
		
		if (hireHistory[0] != null) {
			details += String.format("BORROWING RECORD\n");
			details += String.format("-----------------------------------------\n");
			
			for (int i = 0; i < hireHistory.length; i++) {
				if (hireHistory[i] == null) {
					break;
				}
				else {
//					System.out.println("i: " + i);
					details += hireHistory[i].getDetails();
					details += String.format("-----------------------------------------\n");
				}
			}			
		}
		
		return details.toString();
    }
	
	
	public String toString() {
		String fee = isNewRelease ? "5.0" : "3.0";
		String type = isNewRelease ? "NR" : "WK";
		String loanStatus = null != getCurrentlyBorrowed() ? "Y" : "N";
	
		return super.toString() + ":" + fee + ":" + type + ":" + loanStatus;
  }
}
	


