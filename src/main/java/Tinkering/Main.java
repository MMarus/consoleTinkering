package Tinkering;

import java.io.IOException;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 7/21/17.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        ConsoleWrapperImpl test = new ConsoleWrapperImpl();
        String inputLine = test.readLine("Input:");
        System.out.println(inputLine);
        char[] input = test.readPassword("Pass:");
        System.out.println(input);
    }
}