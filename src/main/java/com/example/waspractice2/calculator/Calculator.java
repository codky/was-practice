package com.example.waspractice2.calculator;

import java.util.List;

public class Calculator {

    private static final List<NewArithmeticOperator> arithmeticOperators = List.of(
            new AdditionOperator(),
            new DivisionOperator(),
            new SubtractionOperator(),
            new MultiplicationOperator()
    );

    // enum 에게 다시 한번 작업을 위임
    // 객체들끼리 협력해서 작업(CalculatorTest - Calculator - ArithmeticOperator)
    public static int calculate(PositiveNumber num1, String operator, PositiveNumber num2) {
        return arithmeticOperators.stream()
                .filter(arithmeticOperator -> arithmeticOperator.supports(operator))
                .map(arithmeticOperator -> arithmeticOperator.calculate(num1, num2))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."));
    }
}
