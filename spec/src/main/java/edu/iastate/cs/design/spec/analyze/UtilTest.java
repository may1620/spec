package edu.iastate.cs.design.spec.analyze;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;

public class UtilTest {

    public static void main(String[] args) {
        File srcDir = new File("C:\\Users\\Alex\\Desktop\\src\\java\\util");
        for (File file : srcDir.listFiles()) {
            if (file.isFile()) {
                testFile(file);
            }
        }
    }

    public static void testFile(File javaSourceFile) {
        //System.out.println(javaSourceFile.getName());
        if (!javaSourceFile.getName().equals("ArrayList.java"))
            return;
        ASTParser parser = ASTParser.newParser(AST.JLS3);
        parser.setKind(ASTParser.K_COMPILATION_UNIT);
        parser.setResolveBindings(true);
        try {
            Scanner scanner = new Scanner(javaSourceFile).useDelimiter("\\Z");
            String fileString = scanner.next();
            parser.setSource(fileString.toCharArray());
        } catch (FileNotFoundException fnfe) {
            System.out.println("Error reading file! Exiting.");
            return;
        }
        Map options = JavaCore.getOptions();
        //JavaCore.setComplianceOptions(JavaCore.VERSION_1_5, options);
        parser.setCompilerOptions(options);
        CompilationUnit cu = (CompilationUnit) parser.createAST(null);
        DocumentationVisitor visitor = new DocumentationVisitor();
        cu.accept(visitor);
    }
}
