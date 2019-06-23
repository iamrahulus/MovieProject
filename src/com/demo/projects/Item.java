package com.demo.projects;
import java.util.StringTokenizer;

abstract public class Item {
	private String id;
	private String title;
	private String description;
	private String genre;
	private double rentalFee;
	private int count = 0;
	private HiringRecord [] hireHistory;
	private HiringRecord currentlyBorrowed;
	
	public Item(String id , String title , String genre , String description, double rentalFee) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.genre = genre;
		this.rentalFee = rentalFee;
		this.hireHistory = new HiringRecord[10]; //only the last 10 records
		for (int i = 0; i < hireHistory.length;i++) {
			hireHistory[i] = null;
		}
		currentlyBorrowed = null;
	}
	
	public double borrow(String memberId, DateTime borrowDate) {
		
		if(currentlyBorrowed != null) {
			return Double.NaN; // when movie cannot be borrowed
		}
		else {
			
			currentlyBorrowed = new HiringRecord(id, memberId, this.rentalFee, borrowDate);
			addToHiringHistory(currentlyBorrowed);
		}
		return this.rentalFee;
			
	}
	
//	public void setRentalFee (double rentalFee) {
//		this.rentalFee = rentalFee;
//	}
//	

	public abstract double returnItem(DateTime returnDate);
	
//	public double returnItem(DateTime returnDate) {    	
//
//		String[] memIdTokens = currentlyBorrowed.getId().split("_");
//		String memId = memIdTokens[2];		
//		
//		HiringRecord returnHiringRecord = new HiringRecord(currentlyBorrowed.getId(), memId, 
//				currentlyBorrowed.getRentalFee(), currentlyBorrowed.getBorrowDate());
//		
//		currentlyBorrowed.returnItem(returnDate, lateFee);
//    	// add to the hiring history
//    	super.addToHiringHistory(currentlyBorrowed);
//    	super.setCurrentlyBorrowed(null);
//				
//		return 0.0;
//    }	
	
	public void addToHiringHistory(HiringRecord hiringRecord) {
		
		if (hireHistory[0] == null) {
			hireHistory[0] = hiringRecord;			
		}
		else {
			int last = 0;
			int i;
			int movePos;
			
			while (last < hireHistory.length && hireHistory[last] != null) {
				last++;
			}
			// the array is full
			if (last == hireHistory.length) {
				movePos = last - 1;
			}
			// array not full
			else {
				movePos = last;
			}
			
			for (i = movePos; i > 0; i--) {
				hireHistory[i] = hireHistory[i - 1]; 
			}
			hireHistory[0] = hiringRecord;
		}
		
		
//		for (int p = 0 ; p < hireHistory.length ; p++) {
//			if (hireHistory[p] == null) { //if there is an empty index
//				System.out.println("p = " + p);
//				hireHistory[p] = hiringRecord;
//				hireHistoryFull = false;
//				break;
//			}
//		} 
//		if (hireHistoryFull == true) {
//			int j;
//		/*
//		 * if there are more than 10 records, delete the oldest one, insert the new one
//		 * and maintain in order of most recent 	
//		 */
//			for (j = 1; j < hireHistory.length; j++) {
//				hireHistory[j - 1] = hireHistory[j];
//			}// end of loop
//			//new currentlyBorrowed should be assigned to the 9th index
//			hireHistory[j - 1] = currentlyBorrowed; 
//		}
		
		for (int t = 0; t < hireHistory.length; t++) {
			if (hireHistory[t] != null) {
				System.out.println("t: " + t);
				System.out.println("hireHistory[t].getRetDate(): " + hireHistory[t].getReturnDate());
			}
		}
	}
	
	public HiringRecord getCurrentlyBorrowed() {
		return currentlyBorrowed;		
	}
	
	public String getId() {
		return id;		
	}
	
	public String getTitle() {
		return title;		
	}
	
	public String getGenre() {
		return genre;		
	}
	
	public String getDescription() {
		return description;		
	}
	
	public HiringRecord[] getHireHistory() {
		return hireHistory;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	public int getCount() {
		return count;
	}
	public void setCurrentlyBorrowed(HiringRecord currentlyBorrowed) {
		this.currentlyBorrowed = currentlyBorrowed;
	}
//	public void addIHistoryRecord(HiringRecord currentlyBorrowed) {
//		hireHistory[count] = currentlyBorrowed;
//		count++;
//	}
//	public HiringRecord getHistoryRecord() {
//		return hireHistory[count - 1];
//	}
	
	
	public String getDetails() {
		String details = new String();
		details += String.format("%-25s%s\n", "ID:" , this.id);
		details += String.format("%-25s%s\n", "Title:" , this.title);
		details += String.format("%-25s%s\n", "Genre:" , this.genre);
		details += String.format("%-25s%s\n", "Description:" , this.description);
		details += String.format("%-25s%s%s\n", "Standard Fee: " , "$", this.rentalFee);
		
//		if (hireHistory[0] != null) {
//			details += String.format("BORROWING RECORD\n");
//			details += String.format("-----------------------------------------\n");
//			
//			for (int i = 0; i < hireHistory.length; i++) {
//				if (hireHistory[i] == null) {
//					break;
//				}
//				else {
//					System.out.println("i: " + i);
//					details += hireHistory[i].getDetails();
//					details += String.format("-----------------------------------------\n");
//				}
//			}			
//		}
//		
		return details.toString();
	}
  
}