package edu.iastate.cs.design.spec.analyze;

public class ComparisonGraphNode {

    private String pos;
    private String ner;

    public ComparisonGraphNode(String pos, String ner) {
        this.pos = pos;
        this.ner = ner;
    }

    public String getPos() {
        return pos;
    }

    public String getNer() {
        return ner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComparisonGraphNode that = (ComparisonGraphNode) o;

        if (!pos.equals(that.pos)) return false;
        return ner.equals(that.ner);

    }

    @Override
    public int hashCode() {
        int result = pos.hashCode();
        result = 31 * result + ner.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ComparisonGraphNode{" +
                "pos='" + pos + '\'' +
                ", ner='" + ner + '\'' +
                '}';
    }
}
