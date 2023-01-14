package de.hawh.beta3.application;

/** Annoying workaround!
 * See https://stackoverflow.com/questions/52653836/maven-shade-javafx-runtime-components-are-missing
 */
public class Entrypoint {

    private Entrypoint(){}

    public static void main(String[] args) {
        GameOfTrons.main(args);
    }
}
