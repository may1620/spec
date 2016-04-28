package edu.iastate.cs.design.spec.analysis;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public abstract class ExceptionDocProcessor {

    public abstract void process(String exceptionType, String documentation, List<String> paramTypes, List<String> paramNames, MethodDeclaration methodNode);

    protected StanfordCoreNLP initPipeline(String methodName, List<String> paramTypes, List<String> paramNames) {
        File regexNerFile = createRegexNerFile(methodName, paramTypes, paramNames);
        if (regexNerFile == null) {
            System.out.println("Error creating regexner file, skipping");
            return null;
        }
        Properties nlpProperties = new Properties();
        nlpProperties.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner, parse, relation");
        nlpProperties.put("regexner.mapping", regexNerFile.getAbsolutePath());
        nlpProperties.put("parse.flags", "-makeCopulaHead");
        return new StanfordCoreNLP(nlpProperties);
    }

    private static File createRegexNerFile(String methodName, List<String> paramTypes, List<String> paramNames) {
        File regexNerFile = null;
        try {
            regexNerFile = File.createTempFile(methodName, ".tmp");
        } catch (IllegalArgumentException iae) {
            System.out.println("IAE. methodName=" + methodName);
            return null;
        } catch (IOException ioe) {
            System.out.println("Error creating temp regexner file" + ioe.toString());
        }
        // regexner doesn't like [] or <? extends Object> on the types
        for (int i = 0; i < paramTypes.size(); ++i) {
            String type = paramTypes.get(i);
            if (type.contains("[")) {
                String arrayStrippedType = type.replaceAll("\\[\\]", "");
                paramTypes.set(i, arrayStrippedType);
            }
            if (type.contains("<")) {
                int genericTypeStart = type.indexOf("<");
                int genericTypeIndexEnd = type.lastIndexOf(">");
                String genericTypeStrippedType = type.substring(0, genericTypeStart) + type.substring(genericTypeIndexEnd + 1);
                paramTypes.set(i, genericTypeStrippedType);
            }
        }
        regexNerFile.deleteOnExit();
        String allowOverrideNers = "LOCATION,PERSON,ORGANIZATION,MONEY,PERCENT,DATE,TIME";
        // write custom NER data to file
        FileWriter nerFileWriter = null;
        try {
            nerFileWriter = new FileWriter(regexNerFile);
            nerFileWriter.append(methodName + "\tTARGET_METHOD\t" + allowOverrideNers + "\r\n");
            // TODO return type?
            for (String paramType : paramTypes) {
                nerFileWriter.append(paramType + "\tPARAM_TYPE\t" + allowOverrideNers + "\r\n");
            }
            for (String paramName : paramNames) {
                nerFileWriter.append(paramName + "\tPARAMETER\t" + allowOverrideNers + "\r\n");
            }
            nerFileWriter.append("null" + "\tNULL\t" + allowOverrideNers + "\r\n");
        } catch (IOException ioe) {
            System.out.println("Error writing regexner file");
            return null;
        } finally {
            if (nerFileWriter != null) {
                try {
                    nerFileWriter.close();
                } catch (IOException ioe) {}
            }
        }
        return regexNerFile;
    }
}
