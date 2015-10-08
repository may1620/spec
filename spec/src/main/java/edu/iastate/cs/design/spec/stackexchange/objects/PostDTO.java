package edu.iastate.cs.design.spec.stackexchange.objects;

import java.util.List;

public class PostDTO {
    public static final String QUESTION_POST_TYPE = "question";

    public static final String ANSWER_POST_TYPE = "answer";

    private String body;

    private String bodyMarkdown;

    private List<String> comments;

    // Number of seconds since 1/1/1970
    private long creationDate;

    private int downVoteCount;

    private boolean downvoted;

    // Number of seconds since 1/1/1970
    private long lastActivityDate;

    // Number of seconds since 1/1/1970
    private long lastEditDate;

    private ShallowUserDTO lastEditor;

    private String link;

    private ShallowUserDTO owner;

    private int postId;

    // Either "question" or "answer"
    private String postType;

    private int score;

    private String shareLink;

    private String title;

    private int upVoteCount;

    private boolean upvoted;

    public String getBody() {
        return body;
    }

    public String getBodyMarkdown() {
        return bodyMarkdown;
    }

    public List<String> getComments() {
        return comments;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public int getDownVoteCount() {
        return downVoteCount;
    }

    public boolean isDownvoted() {
        return downvoted;
    }

    public long getLastActivityDate() {
        return lastActivityDate;
    }

    public long getLastEditDate() {
        return lastEditDate;
    }

    public ShallowUserDTO getLastEditor() {
        return lastEditor;
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

    public int getScore() {
        return score;
    }

    public String getShareLink() {
        return shareLink;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostDTO post = (PostDTO) o;

        if (creationDate != post.creationDate) return false;
        if (downVoteCount != post.downVoteCount) return false;
        if (downvoted != post.downvoted) return false;
        if (lastActivityDate != post.lastActivityDate) return false;
        if (lastEditDate != post.lastEditDate) return false;
        if (postId != post.postId) return false;
        if (score != post.score) return false;
        if (upVoteCount != post.upVoteCount) return false;
        if (upvoted != post.upvoted) return false;
        if (body != null ? !body.equals(post.body) : post.body != null) return false;
        if (bodyMarkdown != null ? !bodyMarkdown.equals(post.bodyMarkdown) : post.bodyMarkdown != null)
            return false;
        if (comments != null ? !comments.equals(post.comments) : post.comments != null) return false;
        if (lastEditor != null ? !lastEditor.equals(post.lastEditor) : post.lastEditor != null) return false;
        if (link != null ? !link.equals(post.link) : post.link != null) return false;
        if (owner != null ? !owner.equals(post.owner) : post.owner != null) return false;
        if (postType != null ? !postType.equals(post.postType) : post.postType != null) return false;
        if (shareLink != null ? !shareLink.equals(post.shareLink) : post.shareLink != null) return false;
        return !(title != null ? !title.equals(post.title) : post.title != null);

    }

    @Override
    public int hashCode() {
        int result = body != null ? body.hashCode() : 0;
        result = 31 * result + (bodyMarkdown != null ? bodyMarkdown.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (int) (creationDate ^ (creationDate >>> 32));
        result = 31 * result + downVoteCount;
        result = 31 * result + (downvoted ? 1 : 0);
        result = 31 * result + (int) (lastActivityDate ^ (lastActivityDate >>> 32));
        result = 31 * result + (int) (lastEditDate ^ (lastEditDate >>> 32));
        result = 31 * result + (lastEditor != null ? lastEditor.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + postId;
        result = 31 * result + (postType != null ? postType.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + (shareLink != null ? shareLink.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + upVoteCount;
        result = 31 * result + (upvoted ? 1 : 0);
        return result;
    }
}
