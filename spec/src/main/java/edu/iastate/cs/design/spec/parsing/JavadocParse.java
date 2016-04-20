package edu.iastate.cs.design.spec.parsing;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Javadoc;

import edu.iastate.cs.design.spec.tokensRegex.ExceptionParseAnalysis;
import edu.iastate.cs.design.spec.treeMatching.ComparisonGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class JavadocParse {

    public static ExceptionDocStructureAnalysis structureAnalysis = new ExceptionDocStructureAnalysis();
    public static ExceptionParseAnalysis exceptionAnalysis = new ExceptionParseAnalysis();

    public static void run(boolean treeMatching) {
        File srcDir = new File("C:\\Users\\Alex\\Desktop\\src\\java\\util");
        for (File file : srcDir.listFiles()) {
            if (file.isFile()) {
                testFile(file, treeMatching);
            }
        }
        if(treeMatching) {
	        Map<Integer, ComparisonGraph> frequencyMap = structureAnalysis.getFrequencyMap();
	        PriorityQueue<ComparisonGraph> frequencySortingQueue = new PriorityQueue<ComparisonGraph>(frequencyMap.values());
	        while (!frequencySortingQueue.isEmpty()) {
	            ComparisonGraph graph = frequencySortingQueue.remove();
	            System.out.println(graph.getFrequencyCount());
	        }
        }
    }

    public static void testFile(File javaSourceFile, boolean treeMatching) {
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
        if(treeMatching) {
            visitor = new DocumentationVisitor(structureAnalysis);     	
        }
        else {
        	visitor = new DocumentationVisitor(exceptionAnalysis);
        }
        cu.accept(visitor);
        System.out.println(javaSourceFile.getName() + " completed.");
    }
}
