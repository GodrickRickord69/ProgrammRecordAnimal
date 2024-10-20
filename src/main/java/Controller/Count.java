package Controller;

public class Count implements AutoCloseable{
    static int summa;
    {
        summa = 0;
    }

    public void add() {summa++;}

    @Override
    public void close() {
        System.out.println("Count closer");
    }
}
