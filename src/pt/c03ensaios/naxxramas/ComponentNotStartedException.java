package pt.c03ensaios.naxxramas;

public class ComponentNotStartedException extends Exception {
        public ComponentNotStartedException() {
            super("Component was not started.");
        }

        public ComponentNotStartedException(String arg0) {
            super(arg0);
        }
}
