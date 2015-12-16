package investigations.sparkjava;

import javaslang.Function2;
import javaslang.Tuple2;
import javaslang.collection.HashMap;
import javaslang.collection.Map;
import javaslang.control.Either;
import javaslang.control.Option;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.exception;
import static spark.Spark.get;

/**
 * <a href="http://www.javacodegeeks.com/2015/12/introduction-spark-next-rest-framework-java.html">
 * See more information.
 * </a>
 */
class Calculator implements Route {

    static final String LEFT = ":left";
    static final String RIGHT = ":right";
    static final String OPERATOR = ":operator";

    static final int NOT_FOUND = 404;
    static final int BED_REQUEST = 400;

    private Map<String, Function2<Long, Long, Long>> functions = HashMap.ofAll(
            new Tuple2<>("add", (a, b) -> a + b),
            new Tuple2<>("mul", (a, b) -> a * b),
            new Tuple2<>("div", (a, b) -> a / b),
            new Tuple2<>("sub", (a, b) -> a - b)
    );

    @Override
    public Object handle(Request request, Response response)
            throws Exception {

        String operator = request.params(OPERATOR);
        long left = Long.parseLong(request.params(LEFT));
        long right = Long.parseLong(request.params(RIGHT));

        Either<Error, Long> res = calculate(operator, left, right);

        if (res.isRight()) {
            return res.get();
        } else {
            response.status(res.left().get().code);
            return null;
        }
    }

    public Either<Error, Long> calculate(String operator, long left, long right) {
        Either<Error, Long> unknownOp = Either.<Error, Long>left(new Error(NOT_FOUND, "Unknown math operator."));
        Option<Either<Error, Long>> eitherOption = functions.get(operator).map(f -> Either.<Error, Long>right(f.apply(left, right)));
        return eitherOption.orElse(unknownOp);
    }
}

class Error {
    int code;
    String message;

    public Error(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

public class SparkService {

    public static void main(String[] args) {

        exception(NumberFormatException.class, (e, req, res) -> res.status(Calculator.NOT_FOUND));

        exception(ArithmeticException.class, (e, req, res) -> {
            res.status(Calculator.BED_REQUEST);
            res.body("This doesn't seem like a good idea.");
        });

        get("/" + Calculator.OPERATOR + "/" + Calculator.LEFT + "/" + Calculator.RIGHT, new Calculator());
    }
}
