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
import org.springframework.test.web.servlet.MockMvc;

import static com.javeiros.microserviceB.common.CommentsConstants.COMMENT;
import static com.javeiros.microserviceB.common.CommentsConstants.COMMENT_DTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

//    @Test
//    public void createComment_Data_ReturnsCreated() throws Exception {
//        when(commentServices.addComment("1", COMMENT)).thenReturn(COMMENT);
//
//        mockMvc.perform(post("/comments/{postId}", "1").content(objectMapper.writeValueAsString(COMMENT_DTO)).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").value(COMMENT_DTO));
//
//    }


    @Test
    public void getComments_ByExistingId_ReturnsComments() throws Exception {
        when(commentServices.findById("1")).thenReturn(COMMENT);

        mockMvc.perform(get("/comments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("new name"))
                .andExpect(jsonPath("$.email").value("new email"))
                .andExpect(jsonPath("$.body").value("new body"))
                .andExpect(jsonPath("$.id").value("id"))
                .andExpect(jsonPath("$.postId").value("postId"));


    }
}
