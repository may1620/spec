package edu.iastate.cs.design.spec.stackexchange.objects;

public class CommentDTO {
    private String body;
    private String bodyMarkdown;
    private boolean canFlag;
    private int commentId;
    private int creationDate;
    private boolean edited;
    private String link;
    private ShallowUserDTO owner;
    private int postId;
    private String postType; // POST.*POST_TYPE
    private ShallowUserDTO replyToUser;
    private boolean upvoted;

    public String getBody() {
        return body;
    }

    public String getBodyMarkdown() {
        return bodyMarkdown;
    }

    public boolean isCanFlag() {
        return canFlag;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getCreationDate() {
        return creationDate;
    }

    public boolean isEdited() {
        return edited;
    }

    public String getLink() {
        return link;
    }

    public ShallowUserDTO getOwner() {
        return owner;
    }

    public int getPostId() {
        return postId;
    }

    public String getPostType() {
        return postType;
    }

    public ShallowUserDTO getReplyToUser() {
        return replyToUser;
    }

    public boolean isUpvoted() {
        return upvoted;
    }
}
