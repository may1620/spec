package edu.iastate.cs.design.spec.parsing;

import edu.iastate.cs.design.spec.analysis.IExceptionDocProcessor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class JavadocParse {


    public static void run(String packagePath, IExceptionDocProcessor exceptionDocProcessor) {
        File srcDir = new File(packagePath);
        for (File file : srcDir.listFiles()) {
            if (file.isFile()) {
                testFile(file, exceptionDocProcessor);
            }
        }
    }

    public static void testFile(File javaSourceFile, IExceptionDocProcessor exceptionDocProcessor) {
        System.out.println(javaSourceFile.getName());
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        try {
            Scanner scanner = new Scanner(javaSourceFile).useDelimiter("\\Z");
            String fileString = scanner.next();
            parser.setSource(fileString.toCharArray());
            scanner.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error reading file! Exiting.");
            return;
        }
        Map options = JavaCore.getOptions();
        parser.setCompilerOptions(options);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        DocumentationVisitor visitor;
        visitor = new DocumentationVisitor(exceptionDocProcessor);
        cu.accept(visitor);
        System.out.println(javaSourceFile.getName() + " completed.");
    }
}
