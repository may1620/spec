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
            for (Method method : methods) {
                System.out.println(method);
            }
            insertTypesIntoDatabase(types, typeDao);
            insertMethodsIntoDatabase(methods, methodDao);
        } finally {
            closeSequenceFileReader(reader);
        }
    }

    private void readTypesAndMethodsFromSequenceFile(SequenceFile.Reader reader, List<Type> types, List<Method> methods) {
        Ast.ASTRoot astRoot;
        while ((astRoot = getNextFileAst(reader)) != null) {
            for (Ast.Namespace ns : astRoot.getNamespacesList()) {
                for (Ast.Declaration decl : ns.getDeclarationsList()) {
                    Ast.TypeKind kind = decl.getKind();
                    if ((kind == Ast.TypeKind.CLASS || kind == Ast.TypeKind.INTERFACE) && declIsPublic(decl.getModifiersList())) {
                        Type newType = new Type(decl.getName(), ns.getName());
                        types.add(newType);
                        for (Ast.Method method : decl.getMethodsList()) {
                            if (declIsPublic(method.getModifiersList())) {
                                String signature = constructMethodSignature(method, decl.getName());
                                long sequenceFilePos = 0L;
                                try {
                                    sequenceFilePos = reader.getPosition();
                                } catch (IOException ioe) {
                                    // TODO log exception
                                }
                                Method newMethod = new Method(getMethodName(method, decl.getName()), signature, sequenceFilePath, sequenceFilePos);
                                methods.add(newMethod);
                            }
                        }
                    }
                }
            }
        }
    }

    private String constructMethodSignature(Ast.Method method, String typeName) {
        StringBuilder builder = new StringBuilder();
        builder.append(method.getReturnType().getName());
        builder.append(' ');
        builder.append(getMethodName(method, typeName));
        builder.append('(');
        List<Ast.Variable> formals = method.getArgumentsList();
        for (int i = 0; i < formals.size(); ++i) {
            builder.append(formals.get(i).getVariableType().getName());
            builder.append(' ');
            builder.append(formals.get(i).getName());
            if (i != formals.size() - 1) {
                builder.append(", ");
            }
        }
        builder.append(')');
        return builder.toString();
    }

    // will replace <init> on a constructor with the type name
    private String getMethodName(Ast.Method method, String typeName) {
        final String BOA_CONSTRUCTOR_NAME = "<init>";
        if (method.getName().equals(BOA_CONSTRUCTOR_NAME)) {
            return typeName;
        } else {
            return method.getName();
        }
    }

    private boolean declIsPublic(List<Ast.Modifier> modifiers) {
        boolean isPublic = false;
        for (Ast.Modifier modifier : modifiers) {
            if (modifier.hasVisibility() && modifier.getVisibility() == Ast.Modifier.Visibility.PUBLIC) {
                isPublic = true;
            }
        }
        return isPublic;
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
