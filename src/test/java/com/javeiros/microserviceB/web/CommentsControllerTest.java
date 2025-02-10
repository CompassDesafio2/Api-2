package com.javeiros.microserviceB.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javeiros.microserviceB.controller.CommentController;
import com.javeiros.microserviceB.services.CommentServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;

import static com.javeiros.microserviceB.common.CommentsConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("it")
@ExtendWith(MockitoExtension.class)
@WebMvcTest(CommentController.class)
public class CommentsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentServices commentServices;
    @Autowired
    private WebTestClient webTestClient;


    @Test
    public void insertComment() throws Exception {
        when(commentServices.addComment(COMMENT.getId(), COMMENT)).thenReturn(COMMENT);
        when(commentServices.fromDTO(COMMENT_DTO)).thenReturn(COMMENT);

        mockMvc.perform(
                post("/comments/{id}", COMMENT.getId())
                        .content(objectMapper.writeValueAsString(COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());


    }

    @Test
    public void getAllComments() throws Exception {
        when(commentServices.findAll()).thenReturn(COMMENT_LIST);

        mockMvc.perform(
                        get("/comments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(COMMENT_LIST.size())))
                .andExpect(jsonPath("$[0]").value(COMMENT_LIST.get(0)));
    }

    @Test
    public void getComments_ByExistingId_ReturnsComments() throws Exception {
        when(commentServices.findById(COMMENT.getId())).thenReturn(COMMENT);

        mockMvc.perform(get("/comments/{id}", COMMENT.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(COMMENT.getId()))
                .andExpect(jsonPath("$.postId").value(COMMENT.getPostId()))
                .andExpect(jsonPath("$.name").value(COMMENT.getName()))
                .andExpect(jsonPath("$.email").value(COMMENT.getEmail()))
                .andExpect(jsonPath("$.body").value(COMMENT.getBody()));
    }

    @Test
    public void getComments_ByExistingPostId_ReturnsComments() throws Exception {
        when(commentServices.fromDTO(COMMENT_DTO)).thenReturn(COMMENT);
        when(commentServices.findById(COMMENT_DTO.getId())).thenReturn(COMMENT);
        when(commentServices.getCommentsByPost(COMMENT.getPostId())).thenReturn(COMMENT_LIST);


        mockMvc.perform(get("/comments/post/{postId}", COMMENT_DTO.getPostId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(COMMENT_LIST.size())))
                .andExpect(jsonPath("$[0]").value(COMMENT_LIST.get(0)));
    }

//    @Test
//    public void updateComment() throws Exception {
//
//        when(commentServices.fromDTO(COMMENT_UPDATE_DTO)).thenReturn(UPDATE_COMMENT);
//        when(commentServices.findById(COMMENT.getId())).thenReturn(COMMENT);
//        when(commentServices.updateComment(UPDATE_COMMENT.getPostId(), COMMENT)).thenReturn(UPDATE_COMMENT);
//
//        mockMvc.perform(
//                put("/comments/{id}", COMMENT.getId()).
//                        content(objectMapper.writeValueAsString(UPDATE_COMMENT))
//                        .contentType(MediaType.APPLICATION_JSON)
//        ).andExpect(status().isNoContent());
//    }

    @Test
    public void deleteComment() throws Exception {
        when(commentServices.findById(COMMENT.getId())).thenReturn(COMMENT);

        mockMvc.perform(
                delete("/comments/{id}", COMMENT.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

}
