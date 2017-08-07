package Tinkering;

import java.util.function.Consumer;
import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.aesh.terminal.Connection;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 8/7/17.
 */
public class Terminal implements Consumer<Connection> {
    String line = null;

    public String getLine() {
        return line;
    }
    public boolean isLinePresent(){
        return line != null;
    }

    @Override
    public void accept(Connection connection) {
        read(connection, ReadlineBuilder.builder().enableHistory(false).build(), "[aesh@rules]$ ");
        //lets open the connection to the terminal using this thread
        connection.openBlocking();
    }

    private void read(Connection connection, Readline readline, String prompt) {
        readline.readline(connection, prompt, input -> {
            //we specify a simple lambda consumer to read the input thats returned
            if (input != null) {
                line = input;
                ((TerminalConnection)connection).awake();
                connection.close();
            } else
                read(connection, readline, prompt);
        });
    }
}
