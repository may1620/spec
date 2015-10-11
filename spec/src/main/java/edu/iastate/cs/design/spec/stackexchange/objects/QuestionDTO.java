package edu.iastate.cs.design.spec.stackexchange.objects;


import java.util.List;

public class QuestionDTO {
    private int acceptedAnswerId;
    private int answerCount;
    private AnswerDTO answers;
    private String body;
    private String bodyMarkdown;
    private int bountyAmount;
    private int bountyClosesDate;
    private ShallowUserDTO bountyUser;
    private boolean canClose;
    private boolean canFlag;
    private int closeVoteCount;
    private int closedDate;
    // skip closed_details for now
    private String closedReason;
    private int commentCount;
    private List<CommentDTO> comments;
    private int communityOwnedDate;
    private int creationDate;
    private int deleteVoteCount;
    private int downVoteCount;
    private boolean downvoted;
    private int favoriteCount;
    private boolean favorited;
    private boolean isAnswered;
    private int lastActivityDate;
    private int lastEditDate;
    private ShallowUserDTO lastEditor;
    private String link;
    private int lockedDate;
    // skip migrated_from and migrated_to for now
    // skip notice for now
    private ShallowUserDTO owner;
    private int protectedDate;
    private int questionId;
    private int reopenVoteCount;
    private int score;
    private String shareLink;
    private List<String> tags;
    private String title;
    private int upVoteCount;
    private boolean upvoted;
    private int viewCount;

    public QuestionDTO(String title, String body, List<String> tags, int questionId) {
        this.title = title;
        this.body = body;
        this.tags = tags;
        this.questionId = questionId;
    }

    public int getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public AnswerDTO getAnswers() {
        return answers;
    }

    public String getBody() {
        return body;
    }

    public String getBodyMarkdown() {
        return bodyMarkdown;
    }

    public int getBountyAmount() {
        return bountyAmount;
    }

    public int getBountyClosesDate() {
        return bountyClosesDate;
    }

    public ShallowUserDTO getBountyUser() {
        return bountyUser;
    }

    public boolean isCanClose() {
        return canClose;
    }

    public boolean isCanFlag() {
        return canFlag;
    }

    public int getCloseVoteCount() {
        return closeVoteCount;
    }

    public int getClosedDate() {
        return closedDate;
    }

    public String getClosedReason() {
        return closedReason;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public int getCommunityOwnedDate() {
        return communityOwnedDate;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public int getDeleteVoteCount() {
        return deleteVoteCount;
    }

    public int getDownVoteCount() {
        return downVoteCount;
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public int getLastActivityDate() {
        return lastActivityDate;
    }

    public int getLastEditDate() {
        return lastEditDate;
    }

    public ShallowUserDTO getLastEditor() {
        return lastEditor;
    }

    public String getLink() {
        return link;
    }

    public int getLockedDate() {
        return lockedDate;
    }

    public ShallowUserDTO getOwner() {
        return owner;
    }

    public int getProtectedDate() {
        return protectedDate;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getReopenVoteCount() {
        return reopenVoteCount;
    }

    public int getScore() {
        return score;
    }

    public String getShareLink() {
        return shareLink;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getTitle() {
        return title;
    }

    public int getUpVoteCount() {
        return upVoteCount;
    }

    public boolean isUpvoted() {
        return upvoted;
    }

    public int getViewCount() {
        return viewCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionDTO that = (QuestionDTO) o;

        if (acceptedAnswerId != that.acceptedAnswerId) return false;
        if (answerCount != that.answerCount) return false;
        if (bountyAmount != that.bountyAmount) return false;
        if (bountyClosesDate != that.bountyClosesDate) return false;
        if (canClose != that.canClose) return false;
        if (canFlag != that.canFlag) return false;
        if (closeVoteCount != that.closeVoteCount) return false;
        if (closedDate != that.closedDate) return false;
        if (commentCount != that.commentCount) return false;
        if (communityOwnedDate != that.communityOwnedDate) return false;
        if (creationDate != that.creationDate) return false;
        if (deleteVoteCount != that.deleteVoteCount) return false;
        if (downVoteCount != that.downVoteCount) return false;
        if (downvoted != that.downvoted) return false;
        if (favoriteCount != that.favoriteCount) return false;
        if (favorited != that.favorited) return false;
        if (isAnswered != that.isAnswered) return false;
        if (lastActivityDate != that.lastActivityDate) return false;
        if (lastEditDate != that.lastEditDate) return false;
        if (lockedDate != that.lockedDate) return false;
        if (protectedDate != that.protectedDate) return false;
        if (questionId != that.questionId) return false;
        if (reopenVoteCount != that.reopenVoteCount) return false;
        if (score != that.score) return false;
        if (upVoteCount != that.upVoteCount) return false;
        if (upvoted != that.upvoted) return false;
        if (viewCount != that.viewCount) return false;
        if (answers != null ? !answers.equals(that.answers) : that.answers != null) return false;
        if (body != null ? !body.equals(that.body) : that.body != null) return false;
        if (bodyMarkdown != null ? !bodyMarkdown.equals(that.bodyMarkdown) : that.bodyMarkdown != null) return false;
        if (bountyUser != null ? !bountyUser.equals(that.bountyUser) : that.bountyUser != null) return false;
        if (closedReason != null ? !closedReason.equals(that.closedReason) : that.closedReason != null) return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        if (lastEditor != null ? !lastEditor.equals(that.lastEditor) : that.lastEditor != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (shareLink != null ? !shareLink.equals(that.shareLink) : that.shareLink != null) return false;
        if (tags != null ? !tags.equals(that.tags) : that.tags != null) return false;
        return !(title != null ? !title.equals(that.title) : that.title != null);

    }

    @Override
    public int hashCode() {
        int result = acceptedAnswerId;
        result = 31 * result + answerCount;
        result = 31 * result + (answers != null ? answers.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (bodyMarkdown != null ? bodyMarkdown.hashCode() : 0);
        result = 31 * result + bountyAmount;
        result = 31 * result + bountyClosesDate;
        result = 31 * result + (bountyUser != null ? bountyUser.hashCode() : 0);
        result = 31 * result + (canClose ? 1 : 0);
        result = 31 * result + (canFlag ? 1 : 0);
        result = 31 * result + closeVoteCount;
        result = 31 * result + closedDate;
        result = 31 * result + (closedReason != null ? closedReason.hashCode() : 0);
        result = 31 * result + commentCount;
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + communityOwnedDate;
        result = 31 * result + creationDate;
        result = 31 * result + deleteVoteCount;
        result = 31 * result + downVoteCount;
        result = 31 * result + (downvoted ? 1 : 0);
        result = 31 * result + favoriteCount;
        result = 31 * result + (favorited ? 1 : 0);
        result = 31 * result + (isAnswered ? 1 : 0);
        result = 31 * result + lastActivityDate;
        result = 31 * result + lastEditDate;
        result = 31 * result + (lastEditor != null ? lastEditor.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + lockedDate;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + protectedDate;
        result = 31 * result + questionId;
        result = 31 * result + reopenVoteCount;
        result = 31 * result + score;
        result = 31 * result + (shareLink != null ? shareLink.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + upVoteCount;
        result = 31 * result + (upvoted ? 1 : 0);
        result = 31 * result + viewCount;
        return result;
    }
}
