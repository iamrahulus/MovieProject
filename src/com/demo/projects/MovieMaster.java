package com.demo.projects;

import java.io.IOException;
/*
* Class: MovieMaster
* Description: this class uses an array to store and manage movies
* Author: Labiba Islam - 3694372
*/
import java.util.Scanner;
import java.util.StringTokenizer;

public class MovieMaster {
	Scanner input = new Scanner(System.in);
	String userInput, itemId, title, genre, description, newRelease, itemType;
	String memberId;
	int returnDays, rentalFee;
	int advanceBorrowDays;
	DateTime currentDate = new DateTime();
	boolean exitingProgram = false;

	// Movie[] items = new Movie[100];
	Item[] items = new Item[100];

	public static void main(String ap[]){
		MovieMaster app = new MovieMaster();
	}
	
	public MovieMaster() {
		init();
		menuSystem();
	}

	public void init() {
//		for (int i = 0; i < items.length; i++) {
//			items[i] = null;
//		}
		ItemLoader loader = new ItemLoader();
		try {
			items = loader.loadItems();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void menuSystem() {

		do {
			System.out.println("***Movie Master System Menu***");
			System.out.printf("%-25s%s\n", "Add Item", "A");
			System.out.printf("%-25s%s\n", "Borrow Item", "B");
			System.out.printf("%-25s%s\n", "Return Item", "C");
			System.out.printf("%-25s%s\n", "Display Item", "D");
			System.out.printf("%-25s%s\n", "Seed Data", "E");
			System.out.printf("%-25s%s\n", "Exit Program", "X");
			System.out.println("Enter selection: ");
			userInput = input.nextLine();

			switch (userInput) {
			case "A":
			case "a":
				addItem();
				break;
			case "B":
			case "b":
				borrowItem();
				break;
			case "C":
			case "c":
				returnItem();
				break;
			case "D":
			case "d":
				getDetail();
				break;
			case "E":
			case "e":
				if (items[0] == null) {
					seedData();
				} else {
					System.out.println("Error- items already exists!");
				}
				break;
			case "X":
			case "x":
				System.out.println("Exiting from program...");
				exitingProgram = true;
				saveItems();
				break;
			}
		} while (exitingProgram == false);
	}
	
	public void saveItems(){
		try {
			System.out.println("Saving...");
			DelimitedFileWriter writer = new DelimitedFileWriter();
			writer.persistToFile(items);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void seedData() {
		items[0] = new Movie("AQP", "A Quiet Place", "Horror", "If they hear you they hunt you", false);// movie
																										// not
																										// borrowed
		items[1] = new Movie("FMG", "Forever My Girl", "Romance", "Find your way back home", false);
		items[2] = new Movie("WWM", "Wonder Woman", "Action", "The future of justice begins with her", false);
		items[3] = new Movie("BBD", "Baby Driver", "Music", "All you need is one killer track", false);
		items[4] = new Movie("RMG", "Rampage", "Science fiction", "Big meets bigger", false);
		items[5] = new Movie("AIW", "Avengers:Infinity War", "Science Fiction", "Infinity", true);
		items[6] = new Movie("LLL", "La La Land", "Romance", "Here's to the fools who dream", true);
		items[7] = new Movie("DRS", "Doctor Strange", "Action", "The impossibilities are endless", true);
		items[8] = new Movie("TFP", "The Florida Project", "Drama", "Glorius and Gorgeous", true);
		items[9] = new Movie("BLk", "Blockers", "Comedy", "Parents can be such", true);
//		items[10] = new Game("IGA", "Injustice Gods Among Us", "Fighting", "What if our heros?",
//				new String[] { "Xbox 360, PS4" });
//		items[11] = new Game("SKY", "Skyrim", "Role-Playing", "Go on an epic adventure",
//				new String[] { "Xbox 360, PS4" });
//		items[12] = new Game("ESO", "Elder Scrolls Online", "MMORPG", "Epic questing",
//				new String[] { "Xbox 360, PS4" });
//		items[13] = new Game("WIT", "Witcher", "Action-RPG", "Hunt monsters with swords",
//				new String[] { "Xbox 360, PS4" });

	}// seed data ends

	public void getDetail() {
		for (int p = 0; p < items.length; p++) {
			if (items[p] != null) {
				System.out.println(items[p].getDetails());
			} else {
				break;
			}
		}
	} // getDetail ends

	public void addItem() {

		// takes in movie id,check if it exists
		// if it exists then print out error
		// if it doesn't exist do everything else.

		boolean correctIdEntered = true;
		int p = 0;

		System.out.println("Enter your Id: ");
		itemId = input.nextLine();

		if (itemId.length() != 3) {
			System.out.println("The id that you entered is invalid. Please enter a 3 digit id");
		} else {
			for (p = 0; p < items.length; p++) {
				if (items[p] != null) {
					// System.out.println("items[p].getId(): " +
					// items[p].getId());
					String updaterdItemId = "";

					if (items[p] instanceof Movie) {
						updaterdItemId = "M_" + itemId;
					} else {
						updaterdItemId = "G_" + itemId;
					}

					if (items[p].getId().equals(updaterdItemId)) { // to check
																	// if the
																	// same id
																	// is
																	// entered
																	// again
						System.out.println("Error - Id for " + updaterdItemId + " already exists in the system.");
						correctIdEntered = false;
						break;
					}
				} else {
					break;
				}
			}

			if (correctIdEntered == true) {
				System.out.println("Enter your Title: ");
				title = input.nextLine();
				System.out.println("Enter your Genre: ");
				genre = input.nextLine();
				System.out.println("Enter your Description: ");
				description = input.nextLine();

				System.out.println("Movie or Game (M/G): ");
				itemType = input.nextLine();

				if (itemType.equals("M")) {

					System.out.println("Is the movie a new release? Enter Y for yes and N for no: ");
					newRelease = input.nextLine();

					do {
						if (newRelease.isEmpty()) {
							return;
						}
						if (!newRelease.equals("Y") && !newRelease.equals("N")) {
							System.out.println("Error! You must enter Y or N");
							newRelease = input.nextLine();
						}

					} while (!newRelease.equals("Y") && !newRelease.equals("N"));

					items[p] = new Movie(itemId, title, genre, description, newRelease.equals("Y") ? true : false);
					System.out.println("New movie added successfully for the movie entitled: " + title);
				} else if (itemType.equals("G")) {
					System.out.println("Enter Game Platforms: ");
					String platforms = input.nextLine();
					int tokenIdx = 0;
					String[] platformArray = new String[100];

					StringTokenizer platformTokens = new StringTokenizer(platforms, ",");
					while (platformTokens != null && platformTokens.hasMoreTokens()) {
						platformArray[tokenIdx++] = platformTokens.nextToken().trim();
					}

					items[p] = new Game(itemId, title, genre, description, platformArray);
					System.out.println("New game added successfully for the game entitled: " + title);
				} else {
					System.out.println("Incorrect selection for Movie or Game (M/G). No item will be added!");
				}
			}
		}
	} // addItem ends

	public void borrowItem() {
		System.out.println("Enter id: ");
		itemId = input.nextLine();

		int p = 0;
		String updatedId = "";
		boolean idNotFound = false;

		while (items[p] != null) {

			// System.out.println("in while: item id: " + items[p].getId());
			if (items[p] instanceof Movie) {
				updatedId = "M_" + itemId;
			} else {
				updatedId = "G_" + itemId;
			}

			if (items[p].getId().equals(updatedId)) {
				idNotFound = false;
				// System.out.println("inside if updatedId: " + updatedId + " |
				// in item id: " + items[p].getId());
				break;
			} else {
				// System.out.println("else: ");
				idNotFound = true;
			}
			p++;
		}

		if (idNotFound == true) {
			System.out.println("The item with ID " + itemId + ", not found");
		} else {
			if (items[p].getCurrentlyBorrowed() != null) {
				System.out.println("The item with ID" + itemId + " is currently on loan");
			} else {
				System.out.println("Enter member Id: ");
				memberId = input.nextLine();
				System.out.println("Advance borrow (days): ");
				// advanceBorrowDays = input.nextInt();
				String advanceBorrowDays = input.nextLine();

				DateTime borrowDate = null;
				if (Integer.parseInt(advanceBorrowDays) > 0) {
					borrowDate = new DateTime(Integer.parseInt(advanceBorrowDays));
				} else {
					borrowDate = new DateTime();
				}

				DateTime dueDate = null;

				if (items[p] instanceof Movie) {
					dueDate = new DateTime(borrowDate, ((Movie) items[p]).getIsNewRelease() ? 2 : 7);
				} else {
					System.out.println("Extened Borrowing (Y/N)?: ");
					String extendedBorrowingOption = input.nextLine();

					((Game) items[p]).setExtended(extendedBorrowingOption.equalsIgnoreCase("Y") ? true : false);
					dueDate = new DateTime(borrowDate, ((Game) items[p]).getRentalPeriod());
				}

				double rentalFee = items[p].borrow(memberId, borrowDate);

				if (rentalFee != Double.NaN) {

					System.out.println("The item " + items[p].getTitle() + " costs $" + rentalFee + " and is due on "
							+ dueDate.getFormattedDate());
				} else {
					System.out.println("Error in borrowing. Check program..");
				}
			}
		}
	} // borrowItem ends here

	public void returnItem() {
		System.out.println("Enter id: ");
		itemId = input.nextLine();
		// to check if mvId exists

		int p = 0;
		String updatedId = "";
		boolean idNotFound = false;

		while (items[p] != null) {
			if (items[p] instanceof Movie) {
				updatedId = "M_" + itemId;
			} else {
				updatedId = "G_" + itemId;
			}

			if (items[p].getId().equals(updatedId)) {
				idNotFound = false;
				break;
			} else {
				idNotFound = true;
			}
			p++;
		}
		if (idNotFound == true) {
			System.out.println("The item with ID " + itemId + ", not found");
		} else {
			if (items[p].getCurrentlyBorrowed() == null) {
				System.out.println("The item with ID " + itemId + " is NOT currently on loan");
			} else {

				String nodInLoanString;

				System.out.println("Enter number of days on loan: ");
				nodInLoanString = input.nextLine();
				DateTime returnDate = new DateTime(Integer.parseInt(nodInLoanString));
				// DateTime dueDate = new DateTime(returnDate,
				// items[p].getIsNewRelease() ? 2 : 7);

				double lateFee = items[p].returnItem(returnDate);

				if (lateFee != Double.NaN) {
					// double totalFee = items[p].returnItem(new
					// DateTime(returnDays));
					System.out.println("The total fee payable is $" + lateFee);
				} else {
					System.out.println("Error in returning. Check program..");
				}
			}
		}
	}// end of returnItem
}

// int p = 0;
// while (programs[p] != null && p < programs.length &&
// !programs[p].getId().equals(mvId))
// if (programs[p] != null) {
// p++;

// }

// double rentalFee = 0;
//
// if (programs[p] != null && programs[p].getId().equals(mvId) &&
// programs[p].onLoan() == true) {
// System.out.println("Enter number of days on loan: ");
// returnDays = input.nextInt();
// rentalFee = programs[p].returnItem(new DateTime(returnDays));
// System.out.println("The total fee payable is $" + rentalFee);
// }
//
// else if (programs[p] != null && !programs[p].getId().equals(mvId)) {
// System.out.println("Error- The item with id number: " + mvId + ", not
// found.");
// } else {
// System.out.println("The item with id: " + mvId + " is NOT currently on
// loan.");
// }
// } // returnItem ends here
// }
