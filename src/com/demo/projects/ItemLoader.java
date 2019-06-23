package com.demo.projects;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ItemLoader {

	private static String MAIN_DELIM = "\\|";
	private static String HIRING_DELIM = ":";
	private static String PLATFORMS_DELIM = ":";
	private static String DATE_DELIM = "/";

	public static void main(String a[]) throws Exception {
		ItemLoader l = new ItemLoader();
		Item[] i = l.loadItems();
		// Movie m = (Movie) i[0];
		// System.out.println(m.getHireHistory()[0].getId());
		// System.out.println(m.getHireHistory()[0].getLateFee());
		// System.out.println(i[1].getId());
		// System.out.println(i[1].getDescription());
		// System.out.println(i[1].getTitle());
		// Game g = (Game) i[1];
		// System.out.println(g.getHireHistory()[0].getReturnDate().getFormattedDate());
		System.out.println(i.length);
	}

	public Item[] loadItems() throws IOException {
		BufferedReader reader = null;
		Item[] items = new Item[100];
		try {
			reader = new BufferedReader(new FileReader("items.dat"));
		} catch (FileNotFoundException ffe) {
			try {
				reader = new BufferedReader(new FileReader("items.dat_backup"));
				System.out.println("Loading from the backup file...");
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Could not locate file to load");
			}
		}
		String line = reader.readLine();
		int index = 0;
		while (line != null) {
			items[index++] = parseLine(line);
			line = reader.readLine();
		}
		reader.close();
		return items;
	}

	private Item parseLine(String line) {
		Scanner scanner = new Scanner(line);

		scanner.useDelimiter(MAIN_DELIM);
		String itemType = scanner.next();
		System.out.println(itemType);
		if (itemType.startsWith("M_")) {
			String id = itemType.substring(2);
			Movie movie = new Movie(id, scanner.next(), scanner.next(), scanner.next(),
					Boolean.getBoolean(scanner.next()));
			addHiringRecords(scanner, movie);
			return movie;
		} else {
			String id = itemType.substring(2);
			Game game = new Game(id, scanner.next(), scanner.next(), scanner.next(), getPlatforms(scanner.next()));
			addHiringRecords(scanner, game);
			return game;
		}
	}

	private String[] getPlatforms(String platforms) {
		String[] p = platforms.split(PLATFORMS_DELIM);
		return p;
	}

	private DateTime getDateTime(String date) {
		Scanner sc = new Scanner(date);
		sc.useDelimiter(DATE_DELIM);
		if (sc.hasNext()) {
			String day = sc.next();
			String month = sc.next();
			String year = sc.next();
			DateTime dt = new DateTime(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
			System.out.println(dt.getEightDigitDate());
			sc.close();
			return dt;
		}
		sc.close();
		return null;
	}

	private void addHiringRecords(Scanner scanner, Item item) {
		if (scanner.hasNext()) {
			// That means hiring records exist
			String record = scanner.next();
			while (record != null) {
				Scanner sc = new Scanner(record);
				sc.useDelimiter(HIRING_DELIM);
				String hiringId = sc.next();
				String[] hIds = hiringId.split("_");
				double rental = sc.nextDouble();
				double late = sc.nextDouble();
				HiringRecord hiringRecord = new HiringRecord(hIds[0], hIds[1], rental, getDateTime(sc.next()));
				if (sc.hasNext())
					hiringRecord.returnItem(getDateTime(sc.next()), late);
				item.addToHiringHistory(hiringRecord);
				if (scanner.hasNext()) {
					record = scanner.next();
					sc.close();
				} else {
					scanner.close();
					break;
				}
			}
		}
	}
}