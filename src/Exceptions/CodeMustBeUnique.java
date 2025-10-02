package Exceptions;

public class CodeMustBeUnique extends Exception {
    public CodeMustBeUnique(String name) {
        super("ERROR: There is already airport with " + name + " code.");
    }
}
