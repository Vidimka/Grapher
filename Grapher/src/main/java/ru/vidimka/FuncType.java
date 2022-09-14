package ru.vidimka;

public enum FuncType{
    SQRT{
        @Override
        public double call(double number){ return Math.sqrt(number); }
    },
    SIN{
        @Override
        public double call(double number){ return Math.sin(number); }
    },
    COS{
        @Override
        public double call(double number){ return Math.cos(number); }
    },
    TAN{
        @Override
        public double call(double number){ return Math.tan(number); }
    },
    COTAN{
        @Override
        public double call(double number){ return 1.0 / Math.tan(number); }
    },
    ARCSIN{
        @Override
        public double call(double number){ return Math.asin(number); }
    },
    ARCCOS{
        @Override
        public double call(double number){ return Math.acos(number); }
    },
    ARCTAN{
        @Override
        public double call(double number){ return Math.atan(number); }
    },
    ARCCOTAN{
        @Override
        public double call(double number){ return 1.0 / Math.atan(number); }
    };

    public abstract double call(double number);
}
