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



import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author mmaagdenberg Portions taken from the authors of Numerical Recipes:
 *         The Art of Scientific Computing
 */
public class BetterRandom extends Random {
	private static final long serialVersionUID = 1696443905015571042L;

	private Lock l = new ReentrantLock();
	private long u;
	private long v = 4101842887655102017L;
	private long w = 1;

	public BetterRandom() {
		this(System.nanoTime());
	}

	public BetterRandom(long seed) {
		l.lock();
		u = seed ^ v;
		nextLong();
		v = u;
		nextLong();
		w = v;
		nextLong();
		l.unlock();
	}

	public int nextInt(int low, int high) {
		return nextInt(high - low) + low;
	}

	public long nextLong(long low, long high) {
		return nextLong(high - low) + low;
	}

	public long nextLong() {
		l.lock();
		try {
			u = u * 2862933555777941757L + 7046029254386353087L;
			v ^= v >>> 17;
			v ^= v << 31;
			v ^= v >>> 8;
			w = 4294957665L * (w & 0xffffffff) + (w >>> 32);
			long x = u ^ (u << 21);
			x ^= x >>> 35;
			x ^= x << 4;
			long ret = (x + v) ^ w;
			return ret;
		} finally {
			l.unlock();
		}
	}

	public long nextLong(long n) {
		// error checking and 2^x checking removed for simplicity.
		long bits, val;
		do {
			bits = (nextLong() << 1) >>> 1;
			val = bits % n;
		} while (bits - val + (n - 1) < 0L);
		return val;
	}

	public int nextGaussianInt(int mean, int stdDeviation) {
		double val = nextGaussian() * 100 + 500;
		return (int) Math.round(val);
	}

	protected int next(int bits) {
		return (int) (nextLong() >>> (64 - bits));
	}
}
