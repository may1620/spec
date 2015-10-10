package edu.iastate.cs.design.spec.analyze;

/**
 * This class contains the main method for the Analyze component of the system. This component
 * will look up open question in the database, download their data from StackExchange and then
 * parse the answers given. If it is decided that a question has been correctly answered, the
 * answer will be stored in the database as a finalized specification.
 */
public class Analyze {

    public void run() {

    }

    // Entry point
    public static void main(String[] args) {
        Analyze program = new Analyze();
        program.run();
    }
}
