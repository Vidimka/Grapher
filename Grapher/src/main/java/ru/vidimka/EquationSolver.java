package ru.vidimka; //feat. Daz

public class EquationSolver{
    double scale = Math.pow(10, 5);

    public double solve(String eq) throws RuntimeException{
        Result result = plusMinus(eq);
        while(!(result.remainder.isEmpty())){
            result = plusMinus(Double.toString(result.resultNumber).concat(result.remainder));
        }
        return result.resultNumber;
    }

    public Result plusMinus(String eq) throws RuntimeException{
        Result result = multiplyDivide(eq);
        double number = result.resultNumber;
        String remainder = result.remainder;
        if(!(remainder.isEmpty()) && (remainder.charAt(0) == '+' || remainder.charAt(0) == '-')){
            result = multiplyDivide(result.remainder.substring(1));
            if(remainder.charAt(0) == '+'){
                number += result.resultNumber;
            } else if(remainder.charAt(0) == '-'){
                number -= result.resultNumber;
            }
            remainder = result.remainder;
            number = Math.round(number * scale) / scale; // Rounds to 5 digits max
        }
        if(!(remainder.isEmpty())){ return new Result(number, remainder); }
        return new Result(number, "");
    }

    public Result multiplyDivide(String eq) throws RuntimeException{
        Result result = power(eq);
        double number = result.resultNumber;
        String remainder = result.remainder;
        if(!(remainder.isEmpty()) && (remainder.charAt(0) == '*' || remainder.charAt(0) == '/')){
            result = power(remainder.substring(1));
            if(remainder.charAt(0) == '*'){
                number *= result.resultNumber;
            } else if(remainder.charAt(0) == '/'){
                number /= result.resultNumber;
            }
            remainder = result.remainder;
            number = Math.round(number * scale) / scale; // Rounds to 5 digits max
        }
        if(!(remainder.isEmpty())){ return new Result(number, remainder); }
        return new Result(number, "");
    }

    public Result power(String eq) throws RuntimeException{
        Result result = brackets(eq);
        double number = result.resultNumber;
        String remainder = result.remainder;
        if(!(remainder.isEmpty()) && remainder.charAt(0) == '^'){
            result = numberSeparate(remainder.substring(1));
            double power = result.resultNumber;
            remainder = result.remainder;
            number = Math.pow(number, power);
            number = Math.round(number * scale) / scale; // Rounds to 5 digits max
        }
        if(!(remainder.isEmpty())){ return new Result(number, remainder); }
        return new Result(number, "");
    }

    public Result brackets(String eq) throws RuntimeException{
        String prevRemainder = "";
        if(eq.charAt(0) == '('){
            boolean hasClosing = false;
            for(int i = 0; i<eq.length(); ++i){
                if(eq.charAt(i) == ')'){
                    hasClosing = true;
                    prevRemainder = eq.substring(i+1);
                    eq = eq.substring(1, i);
                    break;
                }
            }
            if(!(hasClosing)){ throw new RuntimeException("Close the bracket please"); }

            Result result = plusMinus(eq);
            while(!(result.remainder.isEmpty())){
                result = plusMinus(Double.toString(result.resultNumber).concat(result.remainder));
            }
            double number = result.resultNumber;
            return new Result(number, prevRemainder);
        } else if(eq.charAt(0) == '{'){
            boolean isArc = false;
            FuncType func = EquationEditor.funcList.get(Integer.parseInt(eq.substring(2, 3))); //A shitty solution of the converting problem
            eq = eq.substring(4); //Cutting out the "{fn}"
            Result result = plusMinus(eq);
            while(!(result.remainder.isEmpty())){
                result = plusMinus(Double.toString(result.resultNumber).concat(result.remainder));
            }
            double number = func.call(result.resultNumber);
            return new Result(number, prevRemainder);
        }
        Result result = numberSeparate(eq);
        if(!(result.remainder.isEmpty())){ return new Result(result.resultNumber, result.remainder); }
        return new Result(result.resultNumber, "");
    }

    public Result numberSeparate(String eq) throws RuntimeException{
        boolean negative = false;
        if(eq.charAt(0) == '-'){
            negative = true;
            eq = eq.substring(1);
        }

        boolean hasDot = false;
        int i = 0;
        if(eq.charAt(0) == '.'){ throw new RuntimeException("The number cannot start with dot"); }
        while(i < eq.length() && (Character.isDigit(eq.charAt(i)) || eq.charAt(i) == '.')){
            if(eq.charAt(i) == '.'){
                if(hasDot){ throw new RuntimeException("The number cannot have only one dot");}
                hasDot = true;
            }
            i++;
        }
        if(i==0){ throw new RuntimeException("There are no numbers here"); }

        double num = Double.parseDouble(eq.substring(0, i));
        if(negative){ num = -num; }

        if(!(eq.substring(1).isEmpty())){ return new Result(num, eq.substring(i)); }
        return new Result(num, "");
    }
}
/*
A simple type that provides saving equation's condition at the moment by saving 2 its parts separately
*/
class Result{
    public double resultNumber;
    public String remainder;

    public Result(double res, String rem){
        resultNumber = res;
        remainder = rem;
    }
}