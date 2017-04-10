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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;


public class DataGenerator {
	private class DataGeneratorParams {
		@Parameter(names = { "-ccode" }, converter = CountryCodeConverter.class, 
                validateWith = CountryCodeValidator.class, 
                description = "Country code for locale-specific data.  Default=US")
		public Country country = Country.US;

		@Parameter(names = "-ds", description = "Data Settings Properties file")
		public String propFile = "./config/LinkHunterData.properties";
	}

	private DataGeneratorParams appParams = new DataGeneratorParams();

	private static DataSettings ds;

	private BetterRandom rand = new BetterRandom();

	private Map<Integer, Integer> accountUserMap = null;

	public static void main(String[] args) {
		System.out.println("Starting LinkHunter data Generation...");
		DataGenerator dataGen = new DataGenerator(args);

		dataGen.generatePeople();
		dataGen.generatePhones();
		dataGen.generatePhoneCallData();
        dataGen.generateAddresses();

        System.out.println("Finished data generation!");
	}
//
//	Addresses
//	Bank Accounts
//	Bank Account Transactions
//	Business
//	Credit Cards
//	Credit Card Transactions
//	People
//	Phones
//	Phone Calls
//	Vehicles
//	Withdrawal Transactions
//
	public DataGenerator(String[] args) {
		new JCommander(appParams, args);

		// HunterSettings.INSTANCE.setCountry(appParams.country);

		ds = new DataSettings(appParams.country);
		ds.loadProperties(appParams.propFile);
	}
//
//	Addresses
//
	public void generateAddresses() {
		System.out.print("Generating addresses...");

		File outputFile = new File(ds.getAddressesDataFileName());
		PrintWriter outputWriter;
		try {
			outputWriter = new PrintWriter(outputFile);
            // write header
            outputWriter.println("index,street_number,street_name,city,state,"
                    + "zip,geo_latitude,geo_longitude,person_index");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR : cannot open " + outputFile.getPath()
					+ " for writing");
			e.printStackTrace();
			return;
		}

		AddressGenerator addressGenerator = new AddressGenerator(
				ds.getStreetNamesFileName(), ds.getZipCodesFileName());

		for (int i = 1; i <= ds.numPeople; i++) {
			AddressGenerator.CityName city = addressGenerator.generateCity();
			outputWriter.println(String.format(DataSettings.ADDRESS_ENTRY_FMT,
					++ds.numAddresses,							//	address index
					addressGenerator.generateStreetNumber(),
					addressGenerator.generateStreet(),
					city.city,
					city.state,
					city.zip,
					city.geoLat,
					city.geoLong,
					i											//	person index
			));
		}
		outputWriter.close();

		// Now write a few hundred lat/long values to a file to be used for
		// "unknown" phone targets
		File latLongOutputFile = new File(ds.getLatLongDataFileName());
		PrintWriter outputWriter1;
		try {
			outputWriter1 = new PrintWriter(latLongOutputFile);
            // write header
            outputWriter1.println("geo_latitude,geo_longitude");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR : cannot open "
					+ latLongOutputFile.getPath() + " for writing");
			e.printStackTrace();
			return;
		}

		AddressGenerator.CityName city;
		List<AddressGenerator.CityName> cities = addressGenerator.getCities();
		for (int i = 0; i < ds.getNumValue(DataSettings.SAMPLE_LATLONG_ENTRIES); i++) {
			city = cities.get(rand.nextInt(cities.size()));
			outputWriter1.println(String.format("%s %s",
					city.geoLat,
					city.geoLong
			));
		}
		outputWriter1.close();

		System.out.println("...Done");
		System.out.println("Written " + ds.numAddresses + " addresses "
				+ " to " + outputFile.getPath());
	}

//
//	People
//
	public void generatePeople() {
		System.out.print("Generating people...");

		File outputFile = new File(ds.getPeopleDataFileName());
		PrintWriter outputWriter;
		try {
			outputWriter = new PrintWriter(outputFile);
            // write header
            outputWriter.println("index,first_name,middle_name,last_name,"
                    + "date_of_birth,sex");

		} catch (FileNotFoundException e) {
			System.err.println("ERROR : cannot open " + outputFile.getPath()
					+ " for writing");
			e.printStackTrace();
			return;
		}

		// Create the name generators
		NameGenerator maleGenerator = new NameGenerator();
		maleGenerator.generateListFromStats(ds.getMaleNamesFileName());

		NameGenerator femaleGenerator = new NameGenerator();
		femaleGenerator.generateListFromStats(ds.getFemaleNamesFileName());

		NameGenerator surnameGenerator = new NameGenerator();
		surnameGenerator.generateListFromStats(ds.getSurnamesFileName());

		ds.numPeople = ds.getNumValue(DataSettings.NUMBER_OF_PEOPLE);
		for (int i = 1; i <= ds.numPeople; i++) {
			// Generate sex (70% male)
			boolean ownerIsMale = true;
			if (rand.nextInt(10) <= 2) {
				ownerIsMale = false;
			}

			// Generate name(s)
			String firstName;
			String middleName;
			String surname = surnameGenerator.generateName();

			if (ownerIsMale) {
				firstName = maleGenerator.generateName();
				middleName = maleGenerator.generateName();
			} else {
				firstName = femaleGenerator.generateName();
				middleName = femaleGenerator.generateName();
			}

			// Generate DOB, between 18 and 75 years back plus some number of
			// days
			// Dates will be stored electronically as long

			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.YEAR,
					-1
							* (rand.nextInt(
									ds.getNumValue(DataSettings.MIN_PERSON_AGE_YEARS),
									ds.getNumValue(DataSettings.MAX_PERSON_AGE_YEARS))));
			cal.add(Calendar.DAY_OF_YEAR, -1 * rand.nextInt(365));

			// Write details to file
			outputWriter.println(String.format(DataSettings.PERSON_ENTRY_FMT,
					i,									//	person index
					firstName,							//	first name
					middleName,							//	middle name
					surname,							//	last name
					cal.getTimeInMillis(),				//	DOB
					(ownerIsMale == true) ? 'M' : 'F'	//	sex
			));

		}

		outputWriter.close();

		System.out.println("...Done");
		System.out.println("Written " + ds.numPeople + " people " + " to "
				+ outputFile.getPath());
	}
//
//	Phones
//
	public void generatePhones() {
		System.out.print("Generating phones...");

		File outputFile = new File(ds.getPhonesDataFileName());
		PrintWriter outputWriter;
		try {
			outputWriter = new PrintWriter(outputFile);
            // write header
            outputWriter.println("index,phone_number,person_index");
		} catch (FileNotFoundException e) {
			System.err.println("ERROR : cannot open " + outputFile.getPath()
					+ " for writing");
			e.printStackTrace();
			return;
		}

		PhoneNumberProvider phoneNumberGenerator = PhoneNumberProviderFactory
				.getProvider(appParams.country);
		phoneNumberGenerator.initialize(ds.getAreaCodesFileName());

		for (int i = 1; i <= ds.numPeople; i++) {
			String[] numberList = phoneNumberGenerator
					.createNumbers(rand.nextInt(ds
							.getNumValue(DataSettings.MAX_PHONES_PER_PERSON)) + 1);
			for (int j = 0; j < numberList.length; j++) {
				outputWriter.println(String.format(DataSettings.PHONE_ENTRY_FMT,
						++ds.numPhones,		//	phone index
						numberList[j],		//	phone number
						i					//	person index
				));
			}
		}

		// Save the number of phones created as targets meaning they have an
		// owner associated with them.
		ds.numOwnedPhones = ds.numPhones;

		// Generate an additional number of phone numbers which have no owner
		// (a.k.a. burners)
		int additionalPhones = (ds.numPhones * 4) / 3;
		String[] additionalPhoneList = phoneNumberGenerator
				.createNumbers(additionalPhones);

		for (int i = 0; i < additionalPhones; i++) {
			outputWriter.println(String.format(DataSettings.PHONE_ENTRY_FMT,
					++ds.numPhones, additionalPhoneList[i], 0));
		}

		outputWriter.close();
		System.out.println("...Done");
		System.out
				.println("Number of phones with owners: " + ds.numOwnedPhones);
		System.out.println("Written " + ds.numPhones + " phones " + " to "
				+ outputFile.getPath());

	}
//
//	Phone Calls
//
	public void generatePhoneCallData() {
		System.out.println("Generating call data...");

		int numberOfTargets = ds.numOwnedPhones;
		int callsPerTarget = ds.getNumValue(DataSettings.CALLS_PER_TARGET);
		int callsPerFile = ds.getNumValue(DataSettings.CALLS_PER_FILE);

		// Get target phone numbers
		PhoneNumberProvider phoneNumberGenerator = PhoneNumberProviderFactory
				.getProvider(appParams.country);
		String[] targetNumbers = phoneNumberGenerator.getNumberList();

		// Get the timestamp generator to generate times over the last N months
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.MONTH,
				-ds.getNumValue(DataSettings.CALL_AGE_IN_MONTHS));
		Date startDate = cal.getTime();
		TimestampGenerator tsGenerator = new TimestampGenerator(startDate, now);

		int outputFileCounter = 0;
		int fileCallCounter = callsPerFile;
		File outputFile = null;
		PrintWriter outputWriter = null;

		// System.out.println("Writing " + DataSettings.NUMBER_OF_CALLS +
		// " calls to " + DataSettings.NUMBER_OF_CALLS / callsPerFile +
		// " files...");

		for (int targetIndex = 0; targetIndex < numberOfTargets; targetIndex++) {
			if (fileCallCounter >= callsPerFile) {
				if (outputFile != null) {
					System.out.println("Written " + fileCallCounter + " calls "
							+ " to " + outputFile.getPath());
					outputWriter.close();
				}
				outputFile = new File(
						ds.getPhoneCallDataFileName(outputFileCounter++));
				try {
					outputWriter = new PrintWriter(outputFile);
                    // write header
                    outputWriter.println("from_number,to_number,call_time,"
                            + "call_duration,from_latitude,from_longitude,"
                            + "to_latitude,to_longitude");
				} catch (FileNotFoundException e) {
					System.err.println("ERROR : cannot open "
							+ outputFile.getPath() + " for writing");
					e.printStackTrace();
					return;
				}
				fileCallCounter = 0;
			}

			// Generate some number of possible call recipients (endpoints)
			String[] endpoints = new String[rand.nextInt(
					ds.getNumValue(DataSettings.MIN_CALL_RECIPIENTS),
					ds.getNumValue(DataSettings.MAX_CALL_RECIPIENTS))];
			for (int endpointIndex = 0; endpointIndex < endpoints.length;) {
				String endpoint = phoneNumberGenerator.getNumber();
				if (endpoint != targetNumbers[targetIndex]) {
					endpoints[endpointIndex++] = endpoint;
				}
			}

			// Sort the numbers in the endpoint array to speed up ingest. This
			// allows the ingest code
			// to cache the last used endpoint in case it has been assigned
			// again to the same target.
			int[] endpointNumbers = new int[callsPerTarget];
			for (int i = 0; i < callsPerTarget; i++) {
				endpointNumbers[i] = rand.nextInt(endpoints.length);
			}
			Arrays.sort(endpointNumbers);

			// Write the targets calls to these random endpoints
			for (int i = 0; i < callsPerTarget
					&& fileCallCounter < callsPerFile; i++) {
				outputWriter.println(String.format(DataSettings.CALL_ENTRY_FMT,
						targetNumbers[targetIndex],									//	from/to
						endpoints[endpointNumbers[i]],								//	to/from
						// endpoints[endpointSelector.nextInt(endpoints.length)],
						tsGenerator.generateTimestampAsLong(),						//	timestamp
						rand.nextInt(												//	duration
								ds.getNumValue(DataSettings.MIN_CALL_DURATION),
								ds.getNumValue(DataSettings.MAX_CALL_DURATION)),
						(24.396308 + rand.nextDouble() * (49.384358 - 24.396308)),	//	from lat
						(-66.885444 - rand.nextDouble() * (124.848974 - 66.885444)),//	from lon
						(24.396308 + rand.nextDouble() * (49.384358 - 24.396308)),	//	to lat
						(-66.885444 - rand.nextDouble() * (124.848974 - 66.885444))	//	to lon
				));
				fileCallCounter++;
				ds.numCalls++;
			}

		}

		System.out.println("Written " + fileCallCounter + " calls " + " to "
				+ outputFile.getPath());
		outputWriter.close();

		System.out.println("...Done");
	}

}
