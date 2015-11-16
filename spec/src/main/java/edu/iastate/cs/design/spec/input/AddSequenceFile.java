package edu.iastate.cs.design.spec.input;

import boa.types.Ast;
import com.google.protobuf.CodedInputStream;
import edu.iastate.cs.design.spec.common.IMethodDao;
import edu.iastate.cs.design.spec.common.ITypeDao;
import edu.iastate.cs.design.spec.common.MethodDao;
import edu.iastate.cs.design.spec.common.TypeDao;
import edu.iastate.cs.design.spec.entities.Method;
import edu.iastate.cs.design.spec.entities.Type;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class defines the entry point for the executable which will take a Boa AST sequence file as input and insert
 * all the methods found into our database.
 */
public class AddSequenceFile {
    String sequenceFilePath;
    ITypeDao typeDao;
    IMethodDao methodDao;

    public AddSequenceFile(String sequenceFilePath, ITypeDao typeDao, IMethodDao methodDao) {
        this.sequenceFilePath = sequenceFilePath;
        this.typeDao = typeDao;
        this.methodDao = methodDao;
    }

    public void run() {
        SequenceFile.Reader reader = createSequenceFileReader(sequenceFilePath);
        if (reader == null) {
            throw new RuntimeException("Couldn't read the sequence file. Exiting.");
        }
        try {
            List<Type> types = new ArrayList<Type>();
            List<Method> methods = new ArrayList<Method>();
            readTypesAndMethodsFromSequenceFile(reader, types, methods);
            insertTypesIntoDatabase(types, typeDao);
            insertMethodsIntoDatabase(methods, methodDao);
        } finally {
            closeSequenceFileReader(reader);
        }
    }

    private void readTypesAndMethodsFromSequenceFile(SequenceFile.Reader reader, List<Type> types, List<Method> methods) {
        Ast.ASTRoot astRoot = null;
        while ((astRoot = getNextFileAst(reader)) != null) {

        }
    }

    private Ast.ASTRoot getNextFileAst(SequenceFile.Reader reader) {
        Text key = new Text();
        BytesWritable value = new BytesWritable();
        Ast.ASTRoot astRoot = null;
        try {
            if (reader.next(key, value)) {
                astRoot = Ast.ASTRoot.parseFrom(CodedInputStream.newInstance(value.getBytes(), 0, value.getLength()));
            }
        } catch (IOException ioe) {
            // TODO log the error. Do we quit? Try to recover?
        }
        return astRoot;
    }

    private void insertTypesIntoDatabase(List<Type> types, ITypeDao typeDao) {
        // TODO
    }

    private void insertMethodsIntoDatabase(List<Method> method, IMethodDao methodDao) {
        // TODO
    }

    /**
     * Returns null on error
     */
    private SequenceFile.Reader createSequenceFileReader(String sequenceFilePath) {
        SequenceFile.Reader reader = null;
        Configuration config = new Configuration();
        try {
            FileSystem fileSystem = FileSystem.get(config);
            reader = new SequenceFile.Reader(fileSystem, new Path(sequenceFilePath), config);
        } catch (IOException ioe) {
            // TODO log the exception
            System.out.println("Error opening sequence file for reading.");
        }
        return reader;
    }

    private void closeSequenceFileReader(SequenceFile.Reader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException ioe) {
            // TODO log the exception
            System.out.println("Failed to close the reader");
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Error. Must specify a sequence file to be used.");
            return;
        }
        ITypeDao typeDao = new TypeDao();
        IMethodDao methodDao = new MethodDao();
        AddSequenceFile program = new AddSequenceFile(args[0], typeDao, methodDao);
        program.run();
    }
}
