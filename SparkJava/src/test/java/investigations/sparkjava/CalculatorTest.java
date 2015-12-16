package investigations.sparkjava;

import javaslang.control.Either;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    @Test
    public void testCalculate() {
        assertEquals(Either.right(10L), new Calculator().calculate("add", 3, 7));
        assertEquals(Either.right(-6L), new Calculator().calculate("sub", 7, 13));
        assertEquals(Either.right(3L), new Calculator().calculate("mul", 3, 1));
        assertEquals(Either.right(0L), new Calculator().calculate("div", 0, 7));
    }

    @Test(expected = ArithmeticException.class)
    public void testArithmeticException() {
        Either<Error, Long> res = new Calculator().calculate("div", 0, 0);
        assertEquals(true, res.isLeft());
        assertEquals(Calculator.BED_REQUEST, res.left().get().code);
    }

    @Test
    public void testUnknownOperator() {
        Either<Error, Long> res = new Calculator().calculate("lol", 0, 0);
        assertEquals(true, res.isLeft());
        assertEquals(Calculator.NOT_FOUND, res.left().get().code);
    }
}
