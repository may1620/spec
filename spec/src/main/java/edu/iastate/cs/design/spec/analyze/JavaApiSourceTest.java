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
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class JavaApiSourceTest {

    public static ExceptionDocStructureAnalysis structureAnalysis = new ExceptionDocStructureAnalysis();

    public static void main(String[] args) {
        File srcDir = new File("C:\\Users\\chanika\\Desktop\\src\\java\\util");
        for (File file : srcDir.listFiles()) {
            if (file.isFile()) {
                testFile(file);
            }
        }
        Map<Integer, ComparisonGraph> frequencyMap = structureAnalysis.getFrequencyMap();
        PriorityQueue<ComparisonGraph> frequencySortingQueue = new PriorityQueue<ComparisonGraph>(frequencyMap.values());
        while (!frequencySortingQueue.isEmpty()) {
            ComparisonGraph graph = frequencySortingQueue.remove();
            System.out.println(graph.getFrequencyCount());
        }
    }

    public static void testFile(File javaSourceFile) {
        System.out.println(javaSourceFile.getName());
        //if (!javaSourceFile.getName().equals("ArrayList.java"))
        //    return;
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
        //ExceptionParseAnalysis parseSuccessAnalysis = new ExceptionParseAnalysis();
        DocumentationVisitor visitor = new DocumentationVisitor(structureAnalysis);
        cu.accept(visitor);
        System.out.println(javaSourceFile.getName() + " completed.");
    }
}
