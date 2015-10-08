package edu.iastate.cs.design.spec.stackexchange.objects;

public class ShallowUserDTO {
    private int acceptRate;

    private BadgeCountDTO badgeCounts;

    private String displayName;

    private String link;

    private String profileImage;

    private int reputation;

    private int userId;

    private String userType;

    public int getAcceptRate() {
        return acceptRate;
    }

    public BadgeCountDTO getBadgeCounts() {
        return badgeCounts;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLink() {
        return link;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public int getReputation() {
        return reputation;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public ShallowUserDTO(int acceptRate, BadgeCountDTO badgeCounts, String displayName, String link, String profileImage, int reputation, int userId, String userType) {
        this.acceptRate = acceptRate;
        this.badgeCounts = badgeCounts;
        this.displayName = displayName;
        this.link = link;
        this.profileImage = profileImage;
        this.reputation = reputation;
        this.userId = userId;
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShallowUserDTO that = (ShallowUserDTO) o;

        if (acceptRate != that.acceptRate) return false;
        if (reputation != that.reputation) return false;
        if (userId != that.userId) return false;
        if (badgeCounts != null ? !badgeCounts.equals(that.badgeCounts) : that.badgeCounts != null) return false;
        if (displayName != null ? !displayName.equals(that.displayName) : that.displayName != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (profileImage != null ? !profileImage.equals(that.profileImage) : that.profileImage != null) return false;
        return !(userType != null ? !userType.equals(that.userType) : that.userType != null);

    }

    @Override
    public int hashCode() {
        int result = acceptRate;
        result = 31 * result + (badgeCounts != null ? badgeCounts.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (profileImage != null ? profileImage.hashCode() : 0);
        result = 31 * result + reputation;
        result = 31 * result + userId;
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
    }
}
