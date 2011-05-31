package pt.c03ensaios.naxxramas;

/**
 * Exception that should be fired when trying to execute an action that
 * requires an Enquire without having previously set one.
 * 
 * @author Danilo Carvalho Grael<dancgr@gmail.com> - 101961
 * @author Flavia Pisani<flavia.pisani@gmail.com> - 104941
 */
public class ComponentNotSetException extends Exception {
    private static final long serialVersionUID = 6272917786408981186L;

    public ComponentNotSetException() {
        super("No Enquirer has been set.");
    }

    public ComponentNotSetException(String arg0) {
        super(arg0);
    }
}
