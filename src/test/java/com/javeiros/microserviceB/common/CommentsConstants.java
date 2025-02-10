package com.javeiros.microserviceB.common;

import com.javeiros.microserviceB.entities.Comment;
import com.javeiros.microserviceB.entities.dto.CommentDTO;

import java.util.ArrayList;
import java.util.List;

public class CommentsConstants {
    public static final Comment COMMENT = new Comment("id", "postId", "name", "email", "body");
    public static final Comment UPDATE_COMMENT = new Comment("new id", " new postId", "new name", "new email", "new body");
    public static final Comment INVALID_COMMENT = new Comment(null, null, null, null,null);
    public static final Comment EMPTY_COMMENTS = new Comment();
    public static final List<Comment> COMMENT_LIST = new ArrayList<>(){{
        add(new Comment("1", "1", "name", "email", "body"));
        add(new Comment("2", "2", "name", "email", "body"));
        add(new Comment("3", "3", "name", "email", "body"));
        add(new Comment("4", "4", "name", "email", "body"));
        add(new Comment("5", "5", "name", "email", "body"));
    }};

    public static final List<Comment> COMMENT_LIST_POSTID = new ArrayList<>(){{
        add(new Comment("1", "1", "name", "email", "body"));
        add(new Comment("2", "1", "name", "email", "body"));
        add(new Comment("3", "1", "name", "email", "body"));
        add(new Comment("4", "1", "name", "email", "body"));
        add(new Comment("5", "1", "name", "email", "body"));
    }};

    public static final CommentDTO COMMENT_DTO = new CommentDTO(COMMENT);
    public static final CommentDTO COMMENT_UPDATE_DTO = new CommentDTO(UPDATE_COMMENT);

}
