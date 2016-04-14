package edu.iastate.cs.design.spec.analyze;

import org.eclipse.jdt.core.dom.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DocumentationVisitor extends ASTVisitor {

    private boolean classIsPrivate;

    public DocumentationVisitor() {
        super(true);
        classIsPrivate = false;
    }

    @Override
    public boolean visit(TypeDeclaration node) {
        if (!nodeIsPublic(node)) {
            classIsPrivate = true;
        }
        return super.visit(node);
    }

    @Override
    public void endVisit(TypeDeclaration node) {
        classIsPrivate = false;
        super.endVisit(node);
    }

    @Override
    public boolean visit(MethodDeclaration node) {
        if (classIsPrivate || !nodeIsPublic(node)) {
            return super.visit(node);
        }
        Javadoc docNode = node.getJavadoc();
        if (docNode != null) {
            for (Object tag : docNode.tags()) {
                TagElement tagElement = (TagElement) tag;
                String tagName  = tagElement.getTagName();
                if (tagName != null && tagName.equals("@throws")) {
                    List fragments = tagElement.fragments();
                    String exceptionType = fragments.get(0).toString();
                    String documentation = "";
                    for (Object fragment : fragments) {
                        if (fragment instanceof TextElement) {
                            documentation += fragment.toString() + " ";
                        }
                    }
                    try {
						ExceptionAnalysis.analyzeThrows(exceptionType, documentation.trim(), node);
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
            }
        }
        return super.visit(node);
    }




    private boolean nodeIsPublic(BodyDeclaration node) {
        List a = node.modifiers();
        for (Object o : a) {
            if (o instanceof Modifier) {
                Modifier mod = (Modifier) o;
                if (mod.getKeyword().toString().equals("public")) {
                    return true;
                }
            }
        }
        return false;
    }



    @Override
    public boolean visit(Javadoc node) {
        return super.visit(node);
    }

}
