package edu.iastate.cs.design.spec.stackexchange.objects;

public class BadgeCountDTO {

    private int bronze;

    private int silver;

    private int gold;

    public int getBronze() {
        return bronze;
    }

    public int getSilver() {
        return silver;
    }

    public int getGold() {
        return gold;
    }

    public BadgeCountDTO(int bronze, int silver, int gold) {
        this.bronze = bronze;
        this.silver = silver;
        this.gold = gold;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BadgeCountDTO that = (BadgeCountDTO) o;

        if (bronze != that.bronze) return false;
        if (gold != that.gold) return false;
        return silver == that.silver;
    }

    @Override
    public int hashCode() {
        int result = bronze;
        result = 31 * result + gold;
        result = 31 * result + silver;
        return result;
    }
}
