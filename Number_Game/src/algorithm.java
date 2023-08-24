import java.util.Scanner;
import java.util.Random;
public class algorithm
{

    private Random r = new Random();
    private int x =  r.nextInt(1,101);
    public String find(int g ){

        if (x == g)
            return "true";

        else if (x > g)
            return  "higher than "+g;

        else
            return  "lower than "+g;



    }
}
