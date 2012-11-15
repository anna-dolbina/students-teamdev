package calculator.element.operator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BinaryOperatorFactory {

    private static final Map<String, BinaryOperator> operators =
            new HashMap<String, BinaryOperator>() {{
                put("+", new AdditionBinaryOperator(1));
                put("-", new SubtractionBinaryOperator(1));
                put("*", new MultiplicationBinaryOperator(2));
                put("/", new DivisionBinaryOperator(2));
            }};

    public BinaryOperator create(String operatorRepresentation) {
        final BinaryOperator binaryOperator =
                operators.get(operatorRepresentation);
        if (binaryOperator == null) {
            throw new IllegalStateException(
                    "Operator not found: " + operatorRepresentation);
        }
        return binaryOperator;
    }

    public Set<String> getOperatorRepresentations() {
        return operators.keySet();
    }
}
