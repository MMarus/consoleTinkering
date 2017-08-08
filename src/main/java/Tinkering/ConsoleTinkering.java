package Tinkering;

import org.jboss.aesh.console.*;
import org.jboss.aesh.console.Prompt;
import org.jboss.aesh.console.settings.SettingsBuilder;

import java.io.IOException;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 7/21/17.
 */
public class ConsoleTinkering {

    public static void main(String[] args) throws IOException {

        ConsoleWrapperImpl test = new ConsoleWrapperImpl();
        String inputLine = test.readLine("Input:");
        System.out.println(inputLine);
        char[] input = test.readPassword("Pass:");
        System.out.println(input);
    }

    private static String promptForInput(String prompt, Character mask) throws IOException, InterruptedException {

        AeshConsole aeshConsole = new AeshConsoleBuilder()
                .settings(new SettingsBuilder().create())
                .prompt(new Prompt(prompt, mask))
                .create();

        ConsoleBuffer consoleBuffer = new AeshConsoleBufferBuilder()
                .shell(aeshConsole.getShell())
                .prompt(new Prompt(prompt, mask))
                .create();
        InputProcessor inputProcessor = new AeshInputProcessorBuilder()
                .consoleBuffer(consoleBuffer)
                .create();


        consoleBuffer.displayPrompt();
        String result;
        do {
            result = inputProcessor.parseOperation(aeshConsole.getConsoleCallback().getInput());
        }
        while(result == null );

        return result;
    }

}