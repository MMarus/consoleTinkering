package Tinkering;

import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.terminal.Connection;
import org.aesh.utils.Config;

import java.util.function.Consumer;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 8/3/17.
 */
public class AeshConsoleReadLine implements Consumer<Connection> {
    String inputLine = null;
    boolean hasInputLine = false;
    String prompt;

    public AeshConsoleReadLine(String prompt) {
        this.prompt = prompt;
    }

    @Override
    public void accept(Connection connection) {
        readLine(connection, ReadlineBuilder.builder().enableHistory(false).build(), prompt);
        //lets open the connection to the terminal using this thread
        connection.openBlocking();
        //when we get here the
        System.out.println("we are handling user type: ");
    }

    private void readLine(Connection connection, Readline readline, String prompt) {
        readline.readline(connection, prompt, input -> {
            if (! input.isEmpty()) {
                inputLine = input;
                hasInputLine = true;
            } else
                hasInputLine = false;
        });
    }

    public String getInputLine() {
        return inputLine;
    }

    public boolean isInputLinePresent() {
        return hasInputLine;
    }
}
