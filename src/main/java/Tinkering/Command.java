//package Tinkering;
//
//import org.jboss.aesh.console.AeshConsoleCallback;
//import org.jboss.aesh.console.ConsoleCallback;
//import org.jboss.aesh.console.ConsoleOperation;
//import org.jboss.aesh.console.Process;
//import org.jboss.aesh.console.settings.Settings;
//import org.jboss.as.cli.CliInitializationException;
//import org.jboss.as.cli.CommandLineException;
//
//import java.io.InputStream;
//
///**
// * Created by Marek Marusic <mmarusic@redhat.com> on 8/2/17.
// */
//public class Command {
//
//    org.jboss.as.cli.impl.Console console;
//
//    private String readLine(String prompt) throws CommandLineException {
//        org.jboss.as.cli.impl.Console console = org.jboss.as.cli.impl.Console.Factory.getConsole(null, null);
//
//        if (console == null) {
//            initBasicConsole(null);
//        } else if(!console.running()) {
//            console.start();
//        }
//
//        return console.readLine(prompt);
//    }
//
//    private String readPassword(String prompt) throws CommandLineException {
//        org.jboss.as.cli.impl.Console console = org.jboss.as.cli.impl.Console.Factory.getConsole(null, null);
//
//        if (console == null) {
//            initBasicConsole(null);
//        } else if(!console.running()) {
//            console.start();
//        }
//
//        return console.readLine(prompt, '*');
//
//    }
//
//    protected void initBasicConsole(InputStream consoleInput) throws CliInitializationException {
//        initBasicConsole(consoleInput, true);
//    }
//
//    protected void initBasicConsole(InputStream consoleInput, boolean start) throws CliInitializationException {
//        // this method shouldn't be called twice during the session
//        assert console == null : "the console has already been initialized";
//        Settings settings = createSettings(consoleInput);
//        this.console = org.jboss.as.cli.impl.Console.Factory.getConsole(this, settings);
//        console.setCallback(initCallback());
//        if(start) {
//            console.start();
//        }
//    }
//
//
//    private ConsoleCallback initCallback() {
//        return new CLIAeshConsoleCallback() {
//
//            @Override
//            public int execute(ConsoleOperation output) throws InterruptedException {
//                // Track the active process
//                setActiveProcess(true);
//
//                try {
//                    // Actual work stuff
//                    if (cmdCompleter == null) {
//                        throw new IllegalStateException("The console hasn't been initialized at construction time.");
//                    }
//                    if (output.getBuffer() == null)
//                        terminateSession();
//                    else {
//                        handleSafe(output.getBuffer().trim());
//                        if (INTERACT && terminate == 0) {
//                            console.setPrompt(getPrompt());
//                        }
//                    }
//                    return 0;
//
//                }finally{
//                    setActiveProcess(false);
//                }
//            }
//
//        };
//    }
//
//    abstract class CLIAeshConsoleCallback extends AeshConsoleCallback {
//
//        private Process process;
//
//        public boolean hasActiveProcess() {
//            return activeProcess;
//        }
//
//        public void setActiveProcess(boolean activeProcess) {
//            this.activeProcess = activeProcess;
//        }
//
//        private boolean activeProcess = false;
//
//        @Override
//        public void setProcess(org.jboss.aesh.console.Process process){
//            this.process = process;
//        }
//
//        public int getProcessPID(){
//            return process.getPID();
//        }
//    }
//}
