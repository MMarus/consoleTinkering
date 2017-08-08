package Tinkering;

import java.util.function.Consumer;

import org.aesh.readline.Prompt;
import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.terminal.Connection;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 8/7/17.
 */
public class ReadLineHandler implements Consumer<Connection> {
    private String line = null;
    private Prompt prompt;

    public ReadLineHandler(Prompt prompt) {
        this.prompt = prompt;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public boolean isLinePresent(){
        return line != null;
    }

    @Override
    public void accept(Connection connection) {
        read(connection, ReadlineBuilder.builder().enableHistory(false).build(), prompt);
        //lets open the connection to the terminal using this thread
        connection.openBlocking();
    }

    private void read(Connection connection, Readline readline, Prompt prompt) {
        readline.readline(connection, prompt, line -> {
            //we specify a simple lambda consumer to read the input thats returned
            if (line != null) {
                setLine(line);
                connection.close();
            } else
                read(connection, readline, prompt);
        });
    }
}
