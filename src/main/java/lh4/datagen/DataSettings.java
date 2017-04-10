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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author mmaagdenberg
 * 
 */
public class DataSettings {
	public static final String GRAPH_SCOPE_NAME = "LinkHunterGraph";

	private static final String SOURCE_DATA_DIR = "SOURCE_DATA_DIR";
	private static final String SOURCE_DATA_DIR_DEF = "./sourcedata";
	private static final String SOURCE_DATA_LOCATION = "/sourcedata/";
	private static final String GENERATED_DATA_DIR = "GENERATED_DATA_DIR";
	private static final String GENERATED_DATA_DIR_DEF = "./gendata";

	private static final String MALE_NAMES_DATA_FILE = "dist.male.first.txt";
	private static final String FEMALE_NAMES_DATA_FILE = "dist.female.first.txt";
	private static final String SURNAMES_DATA_FILE = "dist.all.last.txt";

	private static final String US_AREA_CODES_FILE = "us.areacodes.bystate.txt";
	private static final String STREET_NAMES_DATA_FILE = "us.street.names.txt";
	private static final String CITY_NAMES_DATA_FILE = "us.cities.txt";
	private static final String ZIP_CODES_DATA_FILE = "us.zip.codes.txt";
	private static final String BUSINESSES_DATA_FILE = "us.businesses.txt";
	private static final String BANK_NAMES_DATA_FILE = "us.banks.txt";

	private static final String LAT_LONG_DATA_FILE = "latlongs.csv";

	private static final String PEOPLE_FILE_NAME = "people.csv";
	private static final String VEHICLES_FILE_NAME = "vehicles.txt";
	private static final String PHONES_FILE_NAME = "phones.csv";
	private static final String CCARDS_FILE_NAME = "creditcards.txt";
	private static final String BANKACCOUNTS_FILE_NAME = "bankaccounts.txt";
	private static final String ADDRESSES_FILE_NAME = "addresses.csv";
	private static final String BUSINESSES_FILE_NAME = "businesses.txt";
	private static final String PHONECALL_FILE_FORMAT = "phonecalls%d.csv";
	private static final String CCARDTX_FILE_FORMAT = "cctx%d.txt";
	private static final String BANKACCOUNTTX_FILE_FORMAT = "bankaccounttx%d.txt";
	private static final String WITHDRAWALTX_FILE_NAME = "withdrawaltx.txt";

	protected static final String DATA_DELIM = ",";

	protected static final String NUMBER_OF_PEOPLE = "NUMBER_OF_PEOPLE";
	protected static final String NUMBER_OF_BUSINESSES = "NUMBER_OF_BUSINESSES";

	protected static final String MAX_VEHICLES_PER_PERSON = "MAX_VEHICLES_PER_PERSON";

	protected static final String MIN_CC_PER_PERSON = "MIN_CC_PER_PERSON";
	protected static final String MAX_CC_PER_PERSON = "MAX_CC_PER_PERSON";

	protected static final String MIN_PERSON_AGE_YEARS = "MIN_PERSON_AGE_YEARS";
	protected static final String MAX_PERSON_AGE_YEARS = "MAX_PERSON_AGE_YEARS";

	// Format for People data file entry:
	// ID,FIRSTNAME,MIDDLENAME,LASTNAME,DOB,GENDER{M|F}
	protected static enum PersonEntry {
		ID, FIRSTNAME, MIDDLENAME, LASTNAME, DOB, GENDER
	};

	protected static final String PERSON_ENTRY_FMT = "%d,%s,%s,%s,%d,%c";
	protected static final int PERSON_ENTRY_LEN = PersonEntry.values().length;

	// Format for Businesses data file entry:
	// ID,NAME,MERCHANTID,OWNERID,NUMEMPLOYEES
	protected static enum BusinessEntry {
		ID, NAME, MERCHANTID, OWNERID, NUMEMPLOYEES
	};

	protected static final String BUSINESS_ENTRY_FMT = "%d,%s,%d,%d,%d";
	protected static final int BUSINESS_ENTRY_LEN = BusinessEntry.values().length;

	protected static final String MIN_EMPLOYEES_PER_BUSINESS = "MIN_EMPLOYEES_PER_BUSINESS";
	protected static final String MAX_EMPLOYEES_PER_BUSINESS = "MAX_EMPLOYEES_PER_BUSINESS";

	// Format for Vehicles data file entry:
	// ID,LICENSEPLATE,MAKE,BODY,COLOR,OWNERID
	protected static enum VehicleEntry {
		ID, LICENSE, MAKE, BODY, COLOR, OWNERID
	};

	protected static final String VEHICLE_ENTRY_FMT = "%d,%s,%s,%s,%s,%d";
	protected static final int VEHICLE_ENTRY_LEN = VehicleEntry.values().length;

	// Format for Addresses data file entry:
	// ID,STREETNUMBER,STREETNAME,CITY,STATE,ZIP,OWNERID
	protected static enum AddressEntry {
		ID, STREETNUM, STREETNAME, CITY, STATE, ZIP, GEOLAT, GEOLONG, OWNERID
	};

	protected static final String ADDRESS_ENTRY_FMT = "%d,%d,%s,%s,%s,%s,%s,%s,%d";
	protected static final int ADDRESS_ENTRY_LEN = AddressEntry.values().length;

	// Format for Bank Accounts data file entry:
	// ID,NAME,ACCOUNTNUM,BALANCE,OWNERID
	protected static enum BankAccountEntry {
		ID, NAME, ACCOUNTNUM, BALANCE, OWNERID
	};

	protected static final String BA_ENTRY_FMT = "%d,%s,%d,%d,%d";
	protected static final int BA_ENTRY_LEN = BankAccountEntry.values().length;

	protected static final String MAX_BA_PER_PERSON = "MAX_BA_PER_PERSON";
	protected static final String MIN_BA_BALANCE = "MIN_BA_BALANCE";
	protected static final String MAX_BA_BALANCE = "MAX_BA_BALANCE";

	// Format for Phones data file entry:
	// ID,PHONENUMBER,OWNERID
	protected static enum PhoneEntry {
		ID, NUMBER, OWNERID
	};

	protected static final String PHONE_ENTRY_FMT = "%d,%s,%d";
	protected static final int PHONE_ENTRY_LEN = PhoneEntry.values().length;

	protected static final String MAX_PHONES_PER_PERSON = "MAX_PHONES_PER_PERSON";
	// Phone call settings
	protected static final String CALLS_PER_TARGET = "CALLS_PER_TARGET";
	protected static final String CALLS_PER_FILE = "CALLS_PER_FILE";

	protected static final String MIN_CALL_RECIPIENTS = "MIN_CALL_RECIPIENTS";
	protected static final String MAX_CALL_RECIPIENTS = "MAX_CALL_RECIPIENTS";

	// Call duration ranges (in seconds)
	protected static final String MIN_CALL_DURATION = "MIN_CALL_DURATION";
	protected static final String MAX_CALL_DURATION = "MAX_CALL_DURATION";

	protected static final String CALL_AGE_IN_MONTHS = "CALL_AGE_IN_MONTHS";

	// Format for Phone Call Transaction file entry:
	// Origin Destination Timestamp Duration
	protected static enum CallEntry {
		TARGET, ENDPOINT, DATE, DURATION
	};

	//	BRC	protected static final String CALL_ENTRY_FMT = "%s,%s,%d,%d";
	protected static final String CALL_ENTRY_FMT = "%s,%s,%d,%d,%f,%f,%f,%f";
	protected static final int CALL_ENTRY_LEN = CallEntry.values().length;

	// CreditCard Transaction settings

	// Format for CreditCards data file entry:
	// ID,NAME,EXPIRATIONDATE,OWNERID
	protected static enum CCardEntry {
		ID, NAME, EXPDATE, OWNERID
	};

	protected static final String CCARD_ENTRY_FMT = "%d,%s,%d,%d";
	protected static final int CCARD_ENTRY_LEN = CCardEntry.values().length;

	protected static final String CCTX_PER_FILE = "CCTX_PER_FILE";

	protected static final String MIN_CCTX_PER_TARGET = "MIN_CCTX_PER_TARGET";
	protected static final String MAX_CCTX_PER_TARGET = "MAX_CCTX_PER_TARGET";

	protected static final String MIN_CCTX_RECIPIENTS = "MIN_CCTX_RECIPIENTS";
	protected static final String MAX_CCTX_RECIPIENTS = "MAX_CCTX_RECIPIENTS";

	protected static final String MIN_CCTX_AMOUNT = "MIN_CCTX_AMOUNT";
	protected static final String MAX_CCTX_AMOUNT = "MAX_CCTX_AMOUNT";

	protected static final String MIN_CCTX_FRAUD_AMOUNT = "MIN_CCTX_FRAUD_AMOUNT";
	protected static final String MAX_CCTX_FRAUD_AMOUNT = "MAX_CCTX_FRAUD_AMOUNT";
	protected static final String CCTX_FRAUD_USE_PERIOD = "CCTX_FRAUD_USE_PERIOD";

	protected static final String CCTX_AGE_IN_MONTHS = "CCTX_AGE_IN_MONTHS";
	protected static final String CCTX_FRAUD_NUM = "CCTX_FRAUD_NUM";

	// Format for CreditCard Transaction file entry:
	// CreditCard Business Timestamp Payment
	protected static enum CCTxEntry {
		CARD, BUSINESS, DATE, PAYMENT
	};

	protected static final String CCTX_ENTRY_FMT = "%d,%d,%d,%d,%f,%f,%f,%f";
	protected static final int CCTX_ENTRY_LEN = CCTxEntry.values().length;

	// Bank Account Transaction settings
	protected static final String BATX_TARGETS = "BATX_TARGETS";
	protected static final String BATX_PER_FILE = "BATX_PER_FILE";

	protected static final String MIN_BATX_PER_TARGET = "MIN_BATX_PER_TARGET";
	protected static final String MAX_BATX_PER_TARGET = "MAX_BATX_PER_TARGET";

	protected static final String MIN_BATX_RECIPIENTS = "MIN_BATX_RECIPIENTS";
	protected static final String MAX_BATX_RECIPIENTS = "MAX_BATX_RECIPIENTS";

	protected static final String MIN_BATX_AMOUNT = "MIN_BATX_AMOUNT";
	protected static final String MAX_BATX_AMOUNT = "MAX_BATX_AMOUNT";

	protected static final String BATX_AGE_IN_MONTHS = "BATX_AGE_IN_MONTHS";

	// Transaction entry:
	// A2A BankAccount BankAccount Timestamp Payment
	// A2B BankAccount Business Timestamp Payment
	// A2U BankAccount User Timestamp Payment
	protected static enum BATxEntry {
		TYPE, BANKACCOUNT, DEST, DATE, PAYMENT
	};

	protected static enum BankAcctTxType {
		A, B
	};

	protected static final String BATX_ENTRY_FMT = "%s,%d,%d,%d,%d,%f,%f,%f,%f";
	protected static final int BATX_ENTRY_LEN = BATxEntry.values().length;

	// Format for Withdrawl Transaction data file entry:
	// BANKACCOUNTID,USERID,
	protected static enum WTxEntry {
		BANKACCOUNT, USERID, DATE, PAYMENT
	};

	protected static final String WTX_ENTRY_FMT = "%d,%d,%d,%d,%f,%f,%f,%f";
	protected static final int WTX_ENTRY_LEN = WTxEntry.values().length;

	// Deprecated
	private static final String OWNER_FILE_NAME = "OwnerData.txt";
	private static final String CALL_FILE_FORMAT = "CallData%d.txt";

	protected static final String SAMPLE_LATLONG_ENTRIES = "SAMPLE_LATLONG_ENTRIES";

	private Properties properties = new Properties();

	private Country country = Country.US;
	private boolean fromResource = false;

	protected int numPeople = 0;
	protected int numPhones = 0;
	protected int numOwnedPhones = 0;
	protected int numCreditCards = 0;
	protected int numBankAccounts = 0;
	protected int numVehicles = 0;
	protected int numAddresses = 0;
	protected int numBusinesses = 0;
	protected int numCalls = 0;
	protected int numCCardTxs = 0;
	protected int numBankAccountTxs = 0;
	protected int numWithdrawlTxs = 0;

	public DataSettings() {
		this.country = Country.US;
	}

	public DataSettings(Country c) {
		this.country = c;
	}

	public void setCountry(Country c) {
		this.country = c;
	}

	public void setLoadDataFromResources(boolean useResource) {
		this.fromResource = useResource;
	}

	public String getMaleNamesFileName() {
		return createResourceFileName(MALE_NAMES_DATA_FILE);
	}

	public String getFemaleNamesFileName() {
		return createResourceFileName(FEMALE_NAMES_DATA_FILE);
	}

	public String getSurnamesFileName() {
		return createResourceFileName(SURNAMES_DATA_FILE);
	}

	public String getAreaCodesFileName() {
		return createResourceFileName(US_AREA_CODES_FILE);
	}

	public String getStreetNamesFileName() {
		return createResourceFileName(STREET_NAMES_DATA_FILE);
	}

	public String getCityNamesFileName() {
		return createResourceFileName(CITY_NAMES_DATA_FILE);
	}

	public String getZipCodesFileName() {
		return createResourceFileName(ZIP_CODES_DATA_FILE);
	}

	public String getBusinessesFileName() {
		return createResourceFileName(BUSINESSES_DATA_FILE);
	}

	public String getBankNamesFileName() {
		return createResourceFileName(BANK_NAMES_DATA_FILE);
	}

	// The following provide filenames for the data output files
	public String getPeopleDataFileName() {
		return this.createDataFileName(PEOPLE_FILE_NAME, this.country);
	}

	public String getVehiclesDataFileName() {
		return this.createDataFileName(VEHICLES_FILE_NAME, this.country);
	}

	public String getPhonesDataFileName() {
		return this.createDataFileName(PHONES_FILE_NAME, this.country);
	}

	public String getCreditCardsDataFileName() {
		return this.createDataFileName(CCARDS_FILE_NAME, this.country);
	}

	public String getBankAccountsDataFileName() {
		return this.createDataFileName(BANKACCOUNTS_FILE_NAME, this.country);
	}

	public String getAddressesDataFileName() {
		return this.createDataFileName(ADDRESSES_FILE_NAME, this.country);
	}

	public String getBusinessesDataFileName() {
		return this.createDataFileName(BUSINESSES_FILE_NAME, this.country);
	}

	public String getPhoneCallDataFileName(int fileNum) {
		return this.createDataFileName(
				String.format(PHONECALL_FILE_FORMAT, fileNum), this.country);
	}

	public String getCreditCardTxDataFileName(int fileNum) {
		return this.createDataFileName(
				String.format(CCARDTX_FILE_FORMAT, fileNum), this.country);
	}

	public String getBankAccountTxDataFileName(int fileNum) {
		return this
				.createDataFileName(
						String.format(BANKACCOUNTTX_FILE_FORMAT, fileNum),
						this.country);
	}

	public String getWithdrawlTxDataFileName() {
		return this.createDataFileName(WITHDRAWALTX_FILE_NAME, this.country);
	}

	public String getLatLongDataFileName() {
		return this.createDataFileName(LAT_LONG_DATA_FILE, this.country);
	}

	public int getNumValue(String key) {
		if (properties.containsKey(key))
			return Integer.valueOf(properties.getProperty(key));
		else
			return Integer.MIN_VALUE;
	}

	public void loadProperties(String fileName) {
		InputStream propFile;

		try {
			propFile = new FileInputStream(fileName);
			properties.load(propFile);
			propFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	// Deprecated methods
	public String getOwnersDataFileName() {
		return this.createDataFileName(OWNER_FILE_NAME, this.country);
	}

	public String getCallDataFileName(int fileNum) {
		return this.createDataFileName(
				String.format(CALL_FILE_FORMAT, fileNum), this.country);
	}

	/*
	 * Private Methods
	 */
	private String createDataFileName(String filename, Country country) {
		StringBuilder sb = new StringBuilder();
		sb.append(properties.getProperty(GENERATED_DATA_DIR,
				GENERATED_DATA_DIR_DEF));
		sb.append(File.separatorChar);
		sb.append(country.toString());
		sb.append(File.separatorChar);
		sb.append(filename);
		return sb.toString();
	}

	private String createResourceFileName(String filename) {
		if (fromResource) {
			return SOURCE_DATA_LOCATION + filename;
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(properties.getProperty(SOURCE_DATA_DIR,
					SOURCE_DATA_DIR_DEF));
			sb.append(File.separatorChar);
			sb.append(filename);
			return sb.toString();
		}

	}

	@SuppressWarnings("unused")
	private int calcDataEntryItems(String s) {
		int cntDelimeters = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == DATA_DELIM.charAt(0)) {
				cntDelimeters++;
			}
		}
		return cntDelimeters + 1;
	}

}
