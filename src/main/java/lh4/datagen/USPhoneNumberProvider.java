/*
COPYRIGHT AND DISCLAIMER NOTICE
=========================================

The following copyright and disclaimer notice applies to all files 
included in this application

Objectivity, Inc. grants you a nonexclusive copyright license to use all
programming code examples from which you can generate similar function
tailored to your own specific needs.

All sample code is provided by Objectivity, Inc. for illustrative 
purposes only. These examples have not been thoroughly tested under all 
conditions. Objectivity, Inc., therefore, cannot guarantee or imply 
reliability, serviceability, or function of these programs.

All programs contained herein are provided to you "AS IS" without any
warranties or indemnities of any kind. The implied warranties of 
non-infringement, merchantability and fitness for a particular purpose 
are expressly disclaimed.
 */
package lh4.datagen;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public enum USPhoneNumberProvider implements PhoneNumberProvider {
	INSTANCE;

	private static final int NUMBER_LENGTH = 10;
	private static final int NUMBER_MIN = 1000000;
	private static final int NUMBER_MAX = 10000000;

	private Random random = new Random();
	private List<String> numberList;
	private Map<String, List<Integer>> numberMap = new HashMap<String, List<Integer>>(
			300);
	private Map<String, List<String>> areaCodesByState = new HashMap<String, List<String>>(
			60);
	private List<String> areaCodeList = new ArrayList<String>();

	@Override
	public Country getCountry() {
		return Country.US;
	}

	@Override
	public void initialize(String areaCodeFilePath) {
		try {
			BufferedReader fileReader = new BufferedReader(new FileReader(
					areaCodeFilePath));

			String nameEntry;
			while ((nameEntry = fileReader.readLine()) != null) {
				String[] areaCodes = nameEntry.split(",");

				if (areaCodes.length >= 2) {
					String stateCode = areaCodes[0].trim();
					if (areaCodesByState.containsKey(stateCode) == false) {
						// For each state, add a list of codes for that state
						List<String> areaCodeListLocal = new ArrayList<String>();
						areaCodesByState.put(stateCode, areaCodeList);

						for (int i = 1; i < areaCodes.length; i++) {
							// For each code, add it to the state list and the
							// number list map
							String areaCode = areaCodes[i].trim();
							areaCodeListLocal.add(areaCode);
							areaCodeList.add(areaCode);
							if (numberMap.containsKey(areaCode) == false) {
								numberMap.put(areaCode,
										new ArrayList<Integer>());
							}
						}

					}
				}
			}
			fileReader.close();

			// areaCodeList = new ArrayList<String>(numberMap.keySet().size());
			numberList = new ArrayList<String>(5000000);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void generateNumbers(int count) {
		int areaCodeCount = areaCodeList.size();
		while (count > 0) {
			// Randomly choose an area code
			String areaCode = areaCodeList.get(random.nextInt(areaCodeCount));
			int suffix = generateSuffixForAreaCode(areaCode);
			numberList.add(areaCode + suffix);
			count--;
		}
	}

	@Override
	public String[] createNumbers(int count) {
		List<String> newList = new ArrayList<String>(count);
		int areaCodeCount = areaCodeList.size();
		while (count > 0) {
			// Randomly choose an area code
			String areaCode = areaCodeList.get(random.nextInt(areaCodeCount));
			int suffix = generateSuffixForAreaCode(areaCode);
			numberList.add(areaCode + suffix);
			newList.add(areaCode + suffix);
			count--;
		}
		return newList.toArray(new String[0]);
	}

	@Override
	public int getNumberLength() {
		return NUMBER_LENGTH;
	}

	@Override
	public String getNumber() {
		// Get a random number from the list
		if (numberList.size() == 0) {
			throw new IllegalStateException(
					"No numbers exist,  generator not initialized");
		}
		return numberList.get(random.nextInt(numberList.size()));
	}

	@Override
	public String[] getNumberList() {
		return (String[]) numberList.toArray(new String[0]);
	}

	@Override
	public String[] getNumberRange(String searchKey) {
		String[] range = new String[2];
		int searchKeyLength = searchKey.length();
		if (searchKeyLength == NUMBER_LENGTH) {
			range[0] = searchKey;
			range[1] = searchKey;
		} else {
			StringBuilder sbLowRange = new StringBuilder();
			StringBuilder sbHighRange = new StringBuilder();
			sbLowRange.append(searchKey);
			sbHighRange.append(searchKey);
			for (int i = 0; i < NUMBER_LENGTH - searchKeyLength; i++) {
				sbLowRange.append('0');
				sbHighRange.append('9');
			}
			range[0] = sbLowRange.toString();
			range[1] = sbHighRange.toString();
		}
		return range;
	}

	public void writeNumberList(String numberListFilePath) {
		BufferedWriter fileWriter;

		try {
			fileWriter = new BufferedWriter(new FileWriter(numberListFilePath));

			for (String number : numberList) {
				fileWriter.write(number);
				fileWriter.write('\n');
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int generateSuffixForAreaCode(String areaCode) {
		int suffix = 0;
		List<Integer> suffixList = numberMap.get(areaCode);

		// Generate a suffix that doesn't exist yet
		while (suffix == 0) {
			suffix = random.nextInt(NUMBER_MAX - NUMBER_MIN) + NUMBER_MIN;
			if (suffixList.contains(suffix) == false) {
				suffixList.add(suffix);
			} else {
				suffix = 0;
			}
		}

		return suffix;
	}

	@SuppressWarnings("unused")
	private String generateNumberForState(String stateCode) {
		String result;

		if (areaCodesByState.containsKey(stateCode)) {
			System.out.println("Invalid US State Code: " + stateCode);
			return null;
		}

		// Get the list of area codes for the state
		List<String> stateAreaCodes = null;
		stateAreaCodes = areaCodesByState.get(stateCode);

		// Get an area code and generate a number
		String areaCode = stateAreaCodes.get(random.nextInt(stateAreaCodes
				.size()));
		int suffix = generateSuffixForAreaCode(areaCode);

		// Generate and store result
		result = areaCode + suffix;
		numberList.add(result);

		return result;
	}

}
