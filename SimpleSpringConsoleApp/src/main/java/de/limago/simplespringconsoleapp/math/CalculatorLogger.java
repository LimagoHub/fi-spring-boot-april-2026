package de.limago.simplespringconsoleapp.math;

public class CalculatorLogger implements Calculator{

    private final Calculator calculator;

    public CalculatorLogger(final Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public double add(final double a, final double b) {
        System.out.println(String.format("Adding %f and %f", a, b));
        return calculator.add(a, b);
    }

    @Override
    public double sub(final double a, final double b) {
        return calculator.sub(a, b);
    }
}
