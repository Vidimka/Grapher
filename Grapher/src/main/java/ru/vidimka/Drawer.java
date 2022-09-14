package ru.vidimka;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public class Drawer{
    private static final double scale = Math.pow(10, 5);
    public static final EquationSolver solver = new EquationSolver();
    private static String param;

    public LineChart draw(List<String> minMaxStepSplit, List<String> eqAndParam, LineChart<Number, Number> lineChart){
        XYChart.Series<Number, Number> data = new XYChart.Series<>();
        double minValue = Double.parseDouble(minMaxStepSplit.get(0));
        double maxValue = Double.parseDouble(minMaxStepSplit.get(1));
        double increment = 0.1;
        if(minMaxStepSplit.size() == 3){ increment = Double.parseDouble(minMaxStepSplit.get(2)) / 10; }
        String equation = eqAndParam.get(1);
        param = eqAndParam.get(0);
        String alternativeParam = "x";
        double prevValue = minValue;
        if(param.equals("x")){ alternativeParam = "y"; }
        for(double i=minValue; i<=maxValue; i+=increment){
            i = Math.round(i * scale) / scale;
            System.out.println(i); // TODO: remove here when it's done
            String eq = equation.replace(alternativeParam, String.valueOf(i));
            double equationValue = solver.solve(eq);
            System.out.println(equationValue);
            addData(equationValue, i, data);
            //bisectionMethod(prevValue, equationValue);
            prevValue = i;
        }
        lineChart.getData().add(data);
        return lineChart;
    }
    public LineChart<Number, Number> draw(double number, LineChart<Number, Number> lineChart, String param){
        XYChart.Series<Number, Number> lineData = new XYChart.Series<>();
        if(param.equals("x")){
            lineData.getData().add(new XYChart.Data<>(number, -100));
            lineData.getData().add(new XYChart.Data<>(number, 0));
            lineData.getData().add(new XYChart.Data<>(number, 100));
        } else{
            lineData.getData().add(new XYChart.Data<>(-100, number));
            lineData.getData().add(new XYChart.Data<>(0, number));
            lineData.getData().add(new XYChart.Data<>(100, number));
        }
        lineChart.getData().add(lineData);
        return lineChart;
    }

    private void addData(double paramValue, double otherValue, XYChart.Series<Number, Number> data){
        if(param.equals("x")){
            data.getData().add(new XYChart.Data<>(paramValue, otherValue));
        } else {
            data.getData().add(new XYChart.Data<>(otherValue, paramValue));
        }
    }

    private void bisectionMethod(double prevValue, double currValue/*, double alternativeParam*/){
        if((prevValue > 0 && currValue < 0) || (prevValue < 0 && currValue > 0)){
            double mean = (prevValue + currValue) / 2;
            System.out.println("Mean is: " + mean);
            if(mean <= 0.001){
                System.out.println("Here we go");
                //addData();
            } else{
                if((prevValue > 0 && mean < 0) ||(prevValue < 0 && mean > 0)){
                    bisectionMethod(prevValue, mean);
                } else if((currValue > 0 && mean < 0) ||(currValue < 0 && mean > 0)){
                    bisectionMethod(currValue, mean);
                }
            }
        }
    }
}
