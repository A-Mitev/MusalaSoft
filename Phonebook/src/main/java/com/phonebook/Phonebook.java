package com.phonebook;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Phonebook {
	private static final String PHONEBOOK_TXT = "PhoneBook.txt";
	private static final String REGEX_LAST_SIX_DIGIT = "[0-9]+";
	private static final int REGULAR_PHONE_LENGTH = 10;
	private static final String REGEX_FOURTH_DIGIT = "[2-9]";

	public static void main(String[] args) throws FileNotFoundException {

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter name:");
		String inputName = scanner.next();
		System.out.println("Please enter number:");
		String inputPhone = scanner.next();
		addEntry(inputName, inputPhone);

		File phoneBook = new File(PHONEBOOK_TXT);
		if (!phoneBook.exists()) {
			throw new FileNotFoundException();
		}
		// polzvam TreeMap za da se nalivat sortirani po key (ime).
		TreeMap<String, String> peoples = new TreeMap<String, String>();
		String[] parts;
		BufferedReader br = new BufferedReader(new FileReader(phoneBook));
		try {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				parts = line.split(", ");
				String name = parts[0];
				String phoneNumber = parts[1];
				if (phoneValidation(phoneNumber)) {
					String normalPhone = phoneNumber.replaceFirst("0","+359");
					peoples.put(name, normalPhone);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n" + "Dobaveni v Map-a nomera " + "sled validirane i normalizirane:");
		for (Map.Entry<String, String> entry : peoples.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
	}

	public static boolean phoneValidation(String number) {
		if ((number.length() == REGULAR_PHONE_LENGTH)
				&& (number.startsWith("087") || (number.startsWith("088") || (number.startsWith("089"))))) {
			if ((number.substring(3, 4).matches(REGEX_FOURTH_DIGIT)
					&& (number.substring(4, 10).matches(REGEX_LAST_SIX_DIGIT)))) {
				
				// za proverka
				System.out.println("Validen nomer " + " " + number); 
				return true;
			} else {
				
				 // za proverka
				System.out.println("Nevalidna 4-ta cira ot nomera za nomer " + " " + number);
				return false;
			}
		} else {
			
			 // za proverka
			System.out.println("nevaliden nomer" + " " + number);
			return false;
		}
	}

	public static void addEntry(String name, String number) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(PHONEBOOK_TXT, true))) {
			String newEntry = name + ", "  + number;
			writer.newLine();
			writer.write(newEntry);
		} catch (IOException e) {
			e.printStackTrace();	
		}
	}
}
