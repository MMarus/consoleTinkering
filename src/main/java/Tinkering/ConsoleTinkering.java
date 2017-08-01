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

//        Character mask = '*';
//        console.setPrompt(new Prompt("[aesh]$ ", mask));
//
//
//
//        console.setConsoleCallback(new AeshConsoleCallback() {
//            String inputLineStr = "";
//
//            public String getInputLineStr() {
//                return inputLineStr;
//            }
//
//            @Override
//            public int execute(ConsoleOperation output) {
//                try {
//                    inputLineStr = console.getInputLine();
//                    System.out.println("inputline = " + inputLineStr);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                console.stop();
//                return 0;
//            }
//
//        });
//        console.start();


        System.out.println("Hello!");
        String input = null;
        try {
            input = promptForInput("[aesh]$ ", '*');
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("The input was: \"" + input +"\"");

    }


//cmd ctx

    private String readLine(String prompt) throws CommandLineException {
        org.jboss.as.cli.impl.Console console = org.jboss.as.cli.impl.Console.Factory.getConsole(null, null);

        if (console == null) {
            initBasicConsole(null);
        } else if(!console.running()) {
            console.start();
        }

        return console.readLine(prompt);
    }

    private String readPassword(String prompt) throws CommandLineException {
        org.jboss.as.cli.impl.Console console = org.jboss.as.cli.impl.Console.Factory.getConsole(null, null);

        if (console == null) {
            initBasicConsole(null);
        } else if(!console.running()) {
            console.start();
        }

        return console.readLine(prompt, '*');

    }

    protected void initBasicConsole(InputStream consoleInput) throws CliInitializationException {
        initBasicConsole(consoleInput, true);
    }

    protected void initBasicConsole(InputStream consoleInput, boolean start) throws CliInitializationException {
        // this method shouldn't be called twice during the session
        assert console == null : "the console has already been initialized";
        Settings settings = createSettings(consoleInput);
        this.console = org.jboss.as.cli.impl.Console.Factory.getConsole(this, settings);
        console.setCallback(initCallback());
        if(start) {
            console.start();
        }
    }


    private ConsoleCallback initCallback() {
        return new CLIAeshConsoleCallback() {

            @Override
            public int execute(ConsoleOperation output) throws InterruptedException {
                // Track the active process
                setActiveProcess(true);

                try {
                    // Actual work stuff
                    if (cmdCompleter == null) {
                        throw new IllegalStateException("The console hasn't been initialized at construction time.");
                    }
                    if (output.getBuffer() == null)
                        terminateSession();
                    else {
                        handleSafe(output.getBuffer().trim());
                        if (INTERACT && terminate == 0) {
                            console.setPrompt(getPrompt());
                        }
                    }
                    return 0;

                }finally{
                    setActiveProcess(false);
                }
            }

        };
    }

    abstract class CLIAeshConsoleCallback extends AeshConsoleCallback{

        private Process process;

        public boolean hasActiveProcess() {
            return activeProcess;
        }

        public void setActiveProcess(boolean activeProcess) {
            this.activeProcess = activeProcess;
        }

        private boolean activeProcess = false;

        @Override
        public void setProcess(org.jboss.aesh.console.Process process){
            this.process = process;
        }

        public int getProcessPID(){
            return process.getPID();
        }
    }
//cmd ctx



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