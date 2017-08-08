package Tinkering;

import org.aesh.readline.Prompt;
import org.aesh.readline.tty.terminal.TerminalConnection;
import org.jboss.as.domain.management.security.adduser.JavaConsole;


import java.io.IOError;
import java.io.IOException;
import java.util.IllegalFormatException;

/**
 * Created by Marek Marusic <mmarusic@redhat.com> on 7/31/17.
 */
public class ConsoleWrapperImpl extends JavaConsole {

    @Override
    public String readLine(String fmt, Object... args) throws IOError {
        ReadLineHandler term = new ReadLineHandler(new Prompt(fmt));
        return readInputLine(term);
    }

    private String readInputLine(ReadLineHandler term) {
        try {
            TerminalConnection conn = new TerminalConnection(term);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return term.getLine();
    }

    @Override
    public char[] readPassword(String fmt, Object... args) throws IllegalFormatException, IOError {
        ReadLineHandler term = new ReadLineHandler(new Prompt(fmt, Character.MIN_VALUE));
        return readInputLine(term).toCharArray();
    }
}
