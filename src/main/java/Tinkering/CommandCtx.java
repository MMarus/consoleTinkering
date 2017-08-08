//package Tinkering;
//
//import org.jboss.aesh.console.AeshConsoleCallback;
//import org.jboss.aesh.console.ConsoleCallback;
//import org.jboss.aesh.console.ConsoleOperation;
//import org.jboss.aesh.console.Process;
//import org.jboss.aesh.console.helper.InterruptHook;
//import org.jboss.aesh.console.settings.FileAccessPermission;
//import org.jboss.aesh.console.settings.Settings;
//import org.jboss.aesh.console.settings.SettingsBuilder;
//import org.jboss.aesh.edit.actions.Action;
//import org.jboss.as.cli.CliInitializationException;
//import org.jboss.as.cli.CommandLineException;
//import org.jboss.as.cli.impl.CliShutdownHook;
//import org.jboss.as.cli.impl.Console;
//
//import java.io.File;
//import java.io.InputStream;
//
///**
// * Created by Marek Marusic <mmarusic@redhat.com> on 8/3/17.
// */
//public class CommandCtx {
//    org.jboss.as.cli.impl.Console console;
//    private org.jboss.as.cli.impl.CliShutdownHook.Handler shutdownHook;
//
//
//    public CommandCtx() {
//        addShutdownHook();
//
//    }
//
//    protected void addShutdownHook() {
//        shutdownHook = new CliShutdownHook.Handler() {
//            @Override
//            public void shutdown() {
//                if (CommandContextImpl.this.console != null) {
//                    CommandContextImpl.this.console.interrupt();
//                }
//                terminateSession();
//            }};
//        CliShutdownHook.add(shutdownHook);
//    }
//
//    private String readLine(String prompt) throws CommandLineException {
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
//    private Settings createSettings(InputStream consoleInput) {
//        SettingsBuilder settings = new SettingsBuilder();
//        if(consoleInput != null) {
//            settings.inputStream(consoleInput);
//        }
//        settings.enableExport(false);
//        settings.disableHistory(true);
//
//        settings.interruptHook(
//                new InterruptHook() {
//                    @Override
//                    public void handleInterrupt(org.jboss.aesh.console.Console console, Action action) {
//                        if(shutdownHook != null ){
//                            shutdownHook.shutdown();
//                        }else {
//                            terminateSession();
//                        }
//                        Thread.currentThread().interrupt();
//                    }
//                }
//        );
//
//        return settings.create();
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
//
//    public void terminateSession() {
//        if(terminate == 0) {
//            terminate = TERMINATING;
//            disconnectController();
//            restoreStdIO();
//            if(console != null) {
//                console.stop();
//            }
//            if (shutdownHook != null) {
//                CliShutdownHook.remove(shutdownHook);
//            }
//            terminate = TERMINATED;
//        }
//    }
//}
