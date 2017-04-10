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



public interface PhoneNumberProvider {
	public Country getCountry();

	public void initialize(String fileName);

	public void generateNumbers(int count);

	public String[] createNumbers(int count);

	public int getNumberLength();

	public String getNumber();

	public String[] getNumberList();

	public String[] getNumberRange(String searchKey);
}
