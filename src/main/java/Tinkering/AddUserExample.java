package Tinkering;

import org.aesh.readline.Readline;
import org.aesh.readline.ReadlineBuilder;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.aesh.terminal.Connection;
import org.aesh.utils.Config;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * @author <a href="mailto:stale.pedersen@jboss.org">Ståle W. Pedersen</a>
 */
public class AddUserExample implements Consumer<Connection> {


    private UserType userType;

    public static void main(String... args) throws IOException {
        //we're setting up readline to read when connection receives any input
        //note that this needs to be done after every time Readline.readline returns
        new TerminalConnection(new AddUserExample());
    }

    @Override
    public void accept(Connection connection) {
        connection.write("What type of user do you wish to add?" + Config.getLineSeparator() +
                " a) Management User (mgmt-users.properties)" + Config.getLineSeparator() +
                " b) Application User (application-users.properties)" + Config.getLineSeparator());

        read(connection, ReadlineBuilder.builder().enableHistory(false).build(), "(a): ");
        //lets open the connection to the terminal using this thread
        connection.openBlocking();
        //when we get here the
        System.out.println("we are handling user type: "+userType);
    }

    private void read(Connection connection, Readline readline, String prompt) {
        readline.readline(connection, prompt, input -> {
            if(input.equals("") || input.equals("a")) {
                connection.write("you chose option a."+ Config.getLineSeparator());
                userType = UserType.Management;
                connection.close();

            }
            else if(input.equals("b")) {
                connection.write("you chose option b."+ Config.getLineSeparator());
                userType = UserType.Application;
                connection.close();
            }
            else {
                connection.write("Only accepted values are a or b"+Config.getLineSeparator());
                //lets read until we get exit
                read(connection, readline, prompt);
            }
        });
    }

    enum UserType {
        Management, Application
    }
}