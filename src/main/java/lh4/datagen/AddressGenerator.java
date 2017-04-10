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



import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.io.*;

public class AddressGenerator {
	public class CityName {
		public CityName(String city, String state, String zip) {
			this.city = city;
			this.state = state;
			this.zip = zip;
		}

		public CityName(String city, String state, String zip, String geoLat,
				String geoLong) {
			this.city = city;
			this.state = state;
			this.zip = zip;
			this.geoLat = geoLat;
			this.geoLong = geoLong;
		}

		public String city;
		public String state;
		public String zip;
		public String geoLat;
		public String geoLong;
	}

	private static String[] _streetTypes = { "Street", "Avenue", "Court",
			"Place" };

	private static int MAX_STREET_NUMBER = 200;
	private static int MIN_STREET_NUMBER = 1;
	private static String citySpilt = new String(",");

	private List<String> _streetNames = new ArrayList<String>();
	private List<CityName> _cityNames = new ArrayList<CityName>();

	private Random _rand = new Random();

	public AddressGenerator(String streetNamesResource, String cityNamesResource) {
		BufferedReader fileReader;
		// InputStream in;

		try {
			// in = getClass().getResourceAsStream(streetNamesResource);
			// fileReader = new BufferedReader(new InputStreamReader(in));
			fileReader = new BufferedReader(new FileReader(streetNamesResource));

			// Get the street names from the data file
			String nameEntry;
			while ((nameEntry = fileReader.readLine()) != null) {
				nameEntry = nameEntry.trim();
				if (nameEntry.length() > 0) {
					_streetNames.add(nameEntry);
				}
			}

			// in = getClass().getResourceAsStream(cityNamesResource);
			// fileReader = new BufferedReader(new InputStreamReader(in));
			fileReader = new BufferedReader(new FileReader(cityNamesResource));

			// Get the city names/state from the data file
			while ((nameEntry = fileReader.readLine()) != null) {
				String[] splitEntry = nameEntry.trim().split(citySpilt);
				if (splitEntry.length == 7) // 2
				{
					_cityNames.add(new CityName(splitEntry[3], splitEntry[4],
							splitEntry[0], splitEntry[1], splitEntry[2]));
				}
			}
		} catch (Exception e) {
			_streetNames = new ArrayList<String>();
			_cityNames = new ArrayList<CityName>();

			e.printStackTrace();
			System.out.println(e.getLocalizedMessage());
		}

	}

	public CityName generateCity() {
		if (_cityNames.size() == 0) {
			throw new IllegalStateException("AddressGenerator not initialized");
		}

		return _cityNames.get(_rand.nextInt(_cityNames.size()));
	}

	public String generateStreet() {
		if (_streetNames.size() == 0) {
			throw new IllegalStateException("AddressGenerator not initialized");
		}

		return _streetNames.get(_rand.nextInt(_streetNames.size())) + " "
				+ _streetTypes[_rand.nextInt(_streetTypes.length)];
	}

	public int generateStreetNumber() {
		int result = _rand.nextInt(MAX_STREET_NUMBER - MIN_STREET_NUMBER)
				+ MIN_STREET_NUMBER;

		if (result > 100) {
			// round addresses greater than 100 to avoid numbers that don't
			// exist
			result = (result / 10) * 10;
		}

		return result;
	}

	public List<CityName> getCities() {
		return this._cityNames;
	}
}
