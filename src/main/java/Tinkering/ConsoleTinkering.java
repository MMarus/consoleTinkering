package Tinkering;

import org.jboss.aesh.cl.Option;
import org.jboss.aesh.console.*;
import org.jboss.aesh.console.Process;
import org.jboss.aesh.console.command.Command;
import org.jboss.aesh.console.command.CommandException;
import org.jboss.aesh.console.command.CommandOperation;
import org.jboss.aesh.console.command.CommandResult;
import org.jboss.aesh.console.command.invocation.CommandInvocation;
import org.jboss.aesh.console.Console;
import org.jboss.aesh.console.ConsoleCallback;
import org.jboss.aesh.console.ConsoleOperation;
import org.jboss.aesh.console.Prompt;
import org.jboss.aesh.console.settings.Settings;
import org.jboss.aesh.console.settings.SettingsBuilder;
import org.jboss.aesh.terminal.Color;
import org.jboss.aesh.terminal.Key;
import org.jboss.aesh.terminal.Shell;
import org.jboss.aesh.terminal.TerminalColor;
import org.jboss.aesh.terminal.TerminalString;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandLineException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 7/21/17.
 */
public class ConsoleTinkering {

    public static void main(String[] args) throws IOException {
//
//        System.out.println("Hello!");
//        String input = null;
//        try {
//            input = promptForInput("[aesh]$ ", '*');
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("The input was: \"" + input +"\"");

        ConsoleWrapperImpl test = new ConsoleWrapperImpl();
        String inputLine = test.readLine("Hello");
        System.out.println(inputLine);

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