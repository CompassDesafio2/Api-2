package com.javeiros.microserviceB.common;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;

import java.util.ArrayList;
import java.util.List;

public class PostConstants {

    public static final Post POST = new Post("id", "userID", "title", "content");
    public static final Post POST_UPDATE = new Post("new id", "new userID", "new title", "new content");

    public static final PostDTO POSTDTO = new PostDTO(POST);
    public static final PostDTO POST_UPDATE_DTO = new PostDTO(POST_UPDATE);

    public static final List<Post> POST_LIST = new ArrayList<>(){{
      add(new Post("1", "1", "title 1", "content 1"));
      add(new Post("2", "2", "title 2", "content 2"));
      add(new Post("3", "3", "title 3", "content 3"));
      add(new Post("4", "4", "title 4", "content 4"));
      add(new Post("5", "5", "title 5", "content 5"));
    }};
}
