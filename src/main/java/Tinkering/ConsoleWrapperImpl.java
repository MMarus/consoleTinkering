package Tinkering;

import org.jboss.aesh.console.AeshConsoleBufferBuilder;
import org.jboss.aesh.console.AeshInputProcessorBuilder;
import org.jboss.aesh.console.ConsoleBuffer;
import org.jboss.aesh.console.InputProcessor;
import org.jboss.aesh.console.command.invocation.CommandInvocation;
import org.jboss.as.cli.CliInitializationException;
import org.jboss.as.cli.CommandContext;
import org.jboss.as.cli.impl.CommandContextFactoryImpl;
import org.jboss.as.domain.management.security.adduser.ConsoleWrapper;

import org.jboss.aesh.console.ConsoleCallback;
import org.jboss.aesh.console.Console;
import org.jboss.aesh.console.Prompt;
import org.jboss.aesh.console.settings.Settings;
import org.jboss.aesh.parser.Parser;


import java.io.IOError;
import java.io.IOException;
import java.util.IllegalFormatException;
import java.util.Optional;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 7/31/17.
 */
public class ConsoleWrapperImpl implements ConsoleWrapper {
//    org.jboss.aesh.console.Console aeshConsole;

//    public ConsoleWrapperImpl() {
//        aeshConsole = new org.jboss.aesh.console.Console();
//
//    }

    @Override
    public void format(String fmt, Object... args) throws IllegalFormatException {

    }

    @Override
    public void printf(String format, Object... args) throws IllegalFormatException {

    }

    @Override
    public String readLine(String fmt, Object... args) throws IOError {
        return null;
    }

//    @Override
//    public String readLine(String prompt) {
//        return read(prompt, null);
//    }
//
//    @Override
//    public String readLine(String prompt, Character mask) {
//        return read(prompt, mask);
//    }
//
//    private String read(String prompt, Character mask) {
//        int PID = -1;
//
//        try {
//            ConsoleCallback callback = console.getConsoleCallback();
//            if (callback instanceof CommandContextImpl.CLIAeshConsoleCallback) {
//                CommandContextImpl.CLIAeshConsoleCallback cliCallback = ((CommandContextImpl.CLIAeshConsoleCallback) callback);
//
//                if (cliCallback.hasActiveProcess()) {
//                    PID = cliCallback.getProcessPID();
//                    console.putProcessInBackground(PID);
//                }
//
//            }
//            Prompt origPrompt = null;
//            if(!console.getPrompt().getPromptAsString().equals(prompt)) {
//                origPrompt = console.getPrompt();
//                console.setPrompt(new Prompt(prompt, mask));
//                redrawPrompt();
//            }
//            try {
//                return console.getInputLine();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } finally {
//                if(origPrompt != null) {
//                    console.setPrompt(origPrompt);
//                }
//            }
//        } finally {
//            if( PID != -1) {
//                console.putProcessInForeground(PID);
//            }
//        }
//
//        // Something is wrong.
//        return null;
//    }


    @Override
    public char[] readPassword(String fmt, Object... args) throws IllegalFormatException, IOError {
        return new char[0];
    }

    @Override
    public boolean hasConsole() {
        return false;
    }

//    private String promptForInput(String prompt, Character mask, CommandInvocation invocation) throws IOException, InterruptedException {
//        ConsoleBuffer consoleBuffer = (new AeshConsoleBufferBuilder()).shell(invocation.getShell()).prompt(new Prompt(prompt, mask)).create();
//        InputProcessor inputProcessor = (new AeshInputProcessorBuilder()).consoleBuffer(consoleBuffer).create();
//        consoleBuffer.displayPrompt();
//
//        String result;
//        do {
//            result = inputProcessor.parseOperation(invocation.getInput());
//        } while(result == null);
//
//        return result;
//    }
}
