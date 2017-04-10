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


import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public enum AUPhoneNumberProvider implements PhoneNumberProvider {
	INSTANCE;

	// For now just weight the aussie prefixes
	private static String[] PREFIXES = new String[] { "02", "02", "03", "03",
			"04", "04", "04", "04", "07", "08" };

	private static final int NUMBER_LENGTH = 10;
	private static final int NUMBER_MIN = 10000000;
	private static final int NUMBER_MAX = 100000000;

	private Random random = new Random();
	private String[] numberList = new String[1];

	@Override
	public Country getCountry() {
		return Country.AU;
	}

	@Override
	public void initialize(String filePath) {
		// Nothing to do here.
	}

	@Override
	public void generateNumbers(int count) {
		// Build a dictionary to make sure they are unique
		Set<String> numberSet = new HashSet<String>(count);
		if (numberList.length > 0) {
			numberSet.addAll(Arrays.asList(numberList));
		}

		for (int i = 0; i < count; i++) {
			String candidate = PREFIXES[random.nextInt(PREFIXES.length)]
					+ (random.nextInt(NUMBER_MAX - NUMBER_MIN) + NUMBER_MIN);

			if (numberSet.contains(candidate)) {
				i--;
			} else {
				numberSet.add(candidate);
			}
		}

		// Copy the unique keys to the array
		numberList = numberSet.toArray(numberList);
	}

	@Override
	public int getNumberLength() {
		return NUMBER_LENGTH;
	}

	@Override
	public String getNumber() {
		if (numberList.length == 0) {
			throw new IllegalStateException("The generator is not Initialized");
		}

		return numberList[random.nextInt(numberList.length)];
	}

	@Override
	public String[] getNumberList() {
		return (String[]) numberList.clone();
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.infinitegraph.demo.ighunter.util.PhoneNumberProvider#createNumbers
	 * (int)
	 */
	@Override
	public String[] createNumbers(int count) {
		// TODO Auto-generated method stub
		return null;
	}
}
