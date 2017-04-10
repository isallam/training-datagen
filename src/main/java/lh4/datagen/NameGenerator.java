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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class NameGenerator {
	private ArrayList<String> _nameList = new ArrayList<String>();
	private int _listCount = 0;
	private Random _numberGen = null;

	public void generateListFromStats(String statsFilePath) {
		// Open the Stats file
		try {
			_nameList.clear();
			_listCount = 0;
			_numberGen = null;

			BufferedReader fileReader = new BufferedReader(new FileReader(
					statsFilePath));

			String nameEntry;
			while ((nameEntry = fileReader.readLine()) != null) {
				try {
					int weight = 0;
					String[] nameParams = nameEntry.split("\\s+");

					// Get the weight of the name relative to the others
					if ((nameParams != null) && (nameParams.length == 4)) {
						weight = (int) (Float.parseFloat(nameParams[1]) * 1000.0f);
						if (weight <= 0) {
							weight = 1;
						}

						while (weight-- > 0) {
							_nameList.add(nameParams[0]);
						}
					}
				} catch (Exception e) {
					// Eat the exception and ignore the entry
				}
			}
			fileReader.close();

			if (_nameList.size() > 0) {
				_numberGen = new Random(System.currentTimeMillis());
				_listCount = _nameList.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String generateName() {
		if (_numberGen != null) {
			return _nameList.get(_numberGen.nextInt(_listCount));
		}

		return null;
	}

	public boolean validateName(String name) {
		return _nameList.contains(name);
	}

}
