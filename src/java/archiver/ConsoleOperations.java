package archiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Vitalii on 10/3/2016.
 */
public class ConsoleOperations
{
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void writeToConsole(String msg){
        if (msg == null || msg.isEmpty()) return;
        System.out.println(msg);
    }

    public static String readTextFromConsole(){
        String result = "";
        try {
            result = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int readIntFromConsole(){
        String temp = "";
        int result = -1;
        try {
            temp = br.readLine();
            result = temp.matches("[0-9]")?Integer.parseInt(temp):result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
