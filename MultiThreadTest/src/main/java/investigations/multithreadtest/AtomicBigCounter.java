package investigations.multithreadtest;

import java.math.BigInteger;

/**
 * <a href="http://www.javacodegeeks.com/2015/12/testing-multithreaded-code-java.html">
 * See more information
 * </a>
 */
public class AtomicBigCounter {

    private BigInteger count = BigInteger.ZERO;

    public BigInteger count() {
        return count;
    }

    public void inc() {
        count = count.add(BigInteger.ONE);
    }
}
