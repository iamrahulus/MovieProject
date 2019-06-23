package com.demo.projects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class DelimitedFileWriter {

	public DelimitedFileWriter() throws IOException {
		File mainFile = new File("items.dat");
		File backupFile = new File("items.dat_backup");
		if (!mainFile.exists())
			mainFile.createNewFile();
		if (!backupFile.exists())
			backupFile.createNewFile();
	}

	public void persistToFile(Item[] items) throws FileNotFoundException {
		PrintWriter pw1 = new PrintWriter(new FileOutputStream("items.dat", false));
		PrintWriter pw2 = new PrintWriter(new FileOutputStream("items.dat_backup", false));
		String record = null;
		int i = 0;
		while(items[i] != null){
			Item item = items[i];
			System.out.println(item.getId());
			if (item instanceof Movie) {
				record = persistMovie((Movie) item);
			} else if (item instanceof Game){
				record = persistGame((Game) item);
			} else{
				System.out.println(item.getClass());
			}
			pw1.write(record);
			pw2.write(record);
			pw1.println();
			pw2.println();
			i++;
		}
		pw1.flush();
		pw2.flush();
		pw1.close();
		pw2.close();
	}

	private String persistGame(Game item) {
		StringBuffer sb = new StringBuffer();
		sb.append(item.getId() + "|");
		sb.append(item.getTitle() + "|");
		sb.append(item.getGenre() + "|");
		sb.append(item.getDescription() + "|");
		String[] p = item.getPlatforms();
		for (int i = 0; i < p.length - 1; ++i) {
			sb.append(p[i] + ":");
		}
		sb.append(p[p.length - 1] + "|");
		sb.append(getHiringRecords(item));
		return sb.toString();
	}

	private String persistMovie(Movie item) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append(item.getId() + "|");
		sb.append(item.getTitle() + "|");
		sb.append(item.getGenre() + "|");
		sb.append(item.getDescription() + "|");
		sb.append(item.getIsNewRelease() + "|");
		String hiringRecords = getHiringRecords(item);
		if (hiringRecords != null || hiringRecords.length() != 0)
			sb.append(hiringRecords);
		return sb.toString();
	}

	private String getHiringRecords(Item item) {
		// TODO Auto-generated method stub
		HiringRecord[] hiringRecords = item.getHireHistory();
		
		if (hiringRecords == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < hiringRecords.length - 1; ++i) {
			if (hiringRecords[i] == null)
				return sb.toString();
			createHireRecord(sb, hiringRecords[i]);
			sb.append("|");
		}
		createHireRecord(sb, hiringRecords[hiringRecords.length - 1]);
		return sb.toString();
	}

	private StringBuffer createHireRecord(StringBuffer sb, HiringRecord hiringRecord) {
		// TODO Auto-generated method stub
		sb.append(hiringRecord.getId() + ":");
		sb.append(hiringRecord.getRentalFee() + ":");
		sb.append(hiringRecord.getLateFee() + ":");
		sb.append(hiringRecord.getBorrowDate().getFormattedDate() + ":");
		if (hiringRecord.getReturnDate() != null) {
			sb.append(hiringRecord.getReturnDate().getFormattedDate());
		}
		return sb;
	}
}