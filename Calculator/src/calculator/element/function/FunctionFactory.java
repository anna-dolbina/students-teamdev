package calculator.element.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class FunctionFactory {
    private static final Map<String, Function> functions =
            new HashMap<String, Function>() {{
                put("min", new MinimumFunction());
                put("max", new MaximumFunction());
                put("sum", new SummingFunction());
                put("avg", new AverageFunction());
                put("sqrt", new SquareRootFunction());
                put("pi",new PiFunction());
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

    public Set<String> getFunctionRepresentations() {
        return functions.keySet();
    }
}
