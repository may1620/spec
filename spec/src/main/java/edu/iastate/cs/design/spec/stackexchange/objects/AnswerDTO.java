package edu.iastate.cs.design.spec.stackexchange.objects;

import java.util.List;

public class AnswerDTO {
    private boolean accepted;
    private int answerId;
    private int awardedBountyAmount;
    private List<ShallowUserDTO> awardedBountyUsers;
    private String body;
    private String bodyMarkdown;
    private boolean canFlag;
    private int commentCount;
    private List<CommentDTO> comments;
    private int communityOwnedDate;
    private int creationDate;
    private int downVoteCount;
    private boolean downvoted;
    private boolean isAccepted;
    private int lastActivityDate;
    private int lastEditDate;
    private ShallowUserDTO lastEditor;
    private String link;
    private int lockedDate;
    private ShallowUserDTO owner;
    private int questionId;
    private int score;
    private String shareLink;
    private List<String> tags;
    private String title;
    private int upVoteCount;
    private boolean upvoted;

    public AnswerDTO(String body, int answerId, int questionId) {
        this.body = body;
        this.answerId = answerId;
        this.questionId = questionId;
    }

    public boolean isAccepted() {
        return accepted;
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

    public int getQuestionId() {
        return questionId;
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

    public int getAnswerId() {
        return answerId;
    }

    public int getAwardedBountyAmount() {
        return awardedBountyAmount;
    }

    public List<ShallowUserDTO> getAwardedBountyUsers() {
        return awardedBountyUsers;
    }

    public String getBody() {
        return body;
    }

    public String getBodyMarkdown() {
        return bodyMarkdown;
    }

    public boolean isCanFlag() {
        return canFlag;
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

    public int getDownVoteCount() {
        return downVoteCount;
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AnswerDTO answerDTO = (AnswerDTO) o;

        if (accepted != answerDTO.accepted) return false;
        if (answerId != answerDTO.answerId) return false;
        if (awardedBountyAmount != answerDTO.awardedBountyAmount) return false;
        if (canFlag != answerDTO.canFlag) return false;
        if (commentCount != answerDTO.commentCount) return false;
        if (communityOwnedDate != answerDTO.communityOwnedDate) return false;
        if (creationDate != answerDTO.creationDate) return false;
        if (downVoteCount != answerDTO.downVoteCount) return false;
        if (downvoted != answerDTO.downvoted) return false;
        if (isAccepted != answerDTO.isAccepted) return false;
        if (lastActivityDate != answerDTO.lastActivityDate) return false;
        if (lastEditDate != answerDTO.lastEditDate) return false;
        if (lockedDate != answerDTO.lockedDate) return false;
        if (questionId != answerDTO.questionId) return false;
        if (score != answerDTO.score) return false;
        if (upVoteCount != answerDTO.upVoteCount) return false;
        if (upvoted != answerDTO.upvoted) return false;
        if (awardedBountyUsers != null ? !awardedBountyUsers.equals(answerDTO.awardedBountyUsers) : answerDTO.awardedBountyUsers != null)
            return false;
        if (body != null ? !body.equals(answerDTO.body) : answerDTO.body != null) return false;
        if (bodyMarkdown != null ? !bodyMarkdown.equals(answerDTO.bodyMarkdown) : answerDTO.bodyMarkdown != null)
            return false;
        if (comments != null ? !comments.equals(answerDTO.comments) : answerDTO.comments != null) return false;
        if (lastEditor != null ? !lastEditor.equals(answerDTO.lastEditor) : answerDTO.lastEditor != null) return false;
        if (link != null ? !link.equals(answerDTO.link) : answerDTO.link != null) return false;
        if (owner != null ? !owner.equals(answerDTO.owner) : answerDTO.owner != null) return false;
        if (shareLink != null ? !shareLink.equals(answerDTO.shareLink) : answerDTO.shareLink != null) return false;
        if (tags != null ? !tags.equals(answerDTO.tags) : answerDTO.tags != null) return false;
        return !(title != null ? !title.equals(answerDTO.title) : answerDTO.title != null);

    }

    @Override
    public int hashCode() {
        int result = (accepted ? 1 : 0);
        result = 31 * result + answerId;
        result = 31 * result + awardedBountyAmount;
        result = 31 * result + (awardedBountyUsers != null ? awardedBountyUsers.hashCode() : 0);
        result = 31 * result + (body != null ? body.hashCode() : 0);
        result = 31 * result + (bodyMarkdown != null ? bodyMarkdown.hashCode() : 0);
        result = 31 * result + (canFlag ? 1 : 0);
        result = 31 * result + commentCount;
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + communityOwnedDate;
        result = 31 * result + creationDate;
        result = 31 * result + downVoteCount;
        result = 31 * result + (downvoted ? 1 : 0);
        result = 31 * result + (isAccepted ? 1 : 0);
        result = 31 * result + lastActivityDate;
        result = 31 * result + lastEditDate;
        result = 31 * result + (lastEditor != null ? lastEditor.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + lockedDate;
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + questionId;
        result = 31 * result + score;
        result = 31 * result + (shareLink != null ? shareLink.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + upVoteCount;
        result = 31 * result + (upvoted ? 1 : 0);
        return result;
    }
}
