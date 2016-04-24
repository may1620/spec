package edu.iastate.cs.design.spec.parsing;

import edu.iastate.cs.design.spec.analysis.ExceptionDocProcessor;
import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.List;

public class DocumentationVisitor extends ASTVisitor {

    private boolean classIsPrivate;
    private ExceptionDocProcessor exceptionProcessor;

    public DocumentationVisitor() {
        super(true);
        classIsPrivate = false;
    }

    public DocumentationVisitor(ExceptionDocProcessor exceptionProcessor) {
        this();
        this.exceptionProcessor = exceptionProcessor;
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
                    // naive traversal to obtain to the full documentation text
                    for (Object fragment : fragments) {
                        if (fragment instanceof TextElement) {
                            if (fragment.toString().length() > 0) {
                                documentation += fragment.toString() + " ";
                            }
                        } else if (fragment instanceof TagElement) {
                            TagElement subTagElement = (TagElement) fragment;
                            for (Object subFragment : subTagElement.fragments()) {
                                if (subFragment instanceof TextElement) {
                                    documentation += subFragment.toString() + " ";
                                }
                            }
                        }
                    }
                    List<SingleVariableDeclaration> params = node.parameters();
                    List<String> paramTypes = new ArrayList<String>();
                    List<String> paramNames = new ArrayList<String>();
                    for (SingleVariableDeclaration param : params) {
                        paramTypes.add(param.getType().toString());
                        paramNames.add(param.getName().toString());
                    }
                    if (exceptionProcessor != null) {
                        exceptionProcessor.process(exceptionType, documentation, paramTypes, paramNames, node);
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
