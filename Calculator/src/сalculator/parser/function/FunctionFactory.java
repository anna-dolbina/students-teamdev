package сalculator.parser.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 10.11.12
 * Time: 16:08
 * To change this template use File | Settings | File Templates.
 */
public class FunctionFactory {
    private static final Map<String, Function> functions =
            new HashMap<String, Function>() {{
                put("min", new MinimumFunction());
                put("max", new MaximumFunction());
                put("sum", new SummingFunction());
                put("avg", new AverageFunction());
            }};

    public Function create(String functionRepresentation) {
        final Function function =
                functions.get(functionRepresentation);
        if (function == null) {
            throw new IllegalStateException(
                    "Function not found: " + functionRepresentation);
        }
        return function;
    }

    public Set<String> getOperatorRepresentations() {
        return functions.keySet();
    }
}
