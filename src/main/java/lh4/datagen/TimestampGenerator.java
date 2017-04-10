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



import java.util.Date;
import java.util.Random;

public class TimestampGenerator {
	private Random random = new Random();
	private long beginDate = 0;
	private long dateRange = 0;

	public TimestampGenerator(Date beginDate, Date endDate) {
		this.beginDate = beginDate.getTime();
		this.dateRange = endDate.getTime() - this.beginDate;
	}

	public long generateTimestampAsLong() {
		return beginDate + (random.nextLong() % dateRange);
	}

	public Date generateTimestampAsDateTime() {
		return new Date(this.generateTimestampAsLong());
	}
}
