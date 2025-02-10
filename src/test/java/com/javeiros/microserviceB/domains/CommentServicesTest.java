package com.javeiros.microserviceB.domains;

import com.javeiros.microserviceB.entities.Comment;
import com.javeiros.microserviceB.entities.dto.CommentDTO;
import com.javeiros.microserviceB.exception.EntityNotFoundException;
import com.javeiros.microserviceB.repository.CommentRepository;
import com.javeiros.microserviceB.services.CommentServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.javeiros.microserviceB.common.CommentsConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServicesTest {


    @InjectMocks
    private CommentServices commentServices;

    @Mock
    private CommentRepository commentRepository;


    @Test
    public void createComment_With_ValidData_ReturnsComment() {
        when(commentRepository.save(COMMENT)).thenReturn(COMMENT);

        Comment comment = commentServices.addComment(COMMENT.getPostId(), COMMENT);
        assertThat(comment).isEqualTo(COMMENT);
    }


    @Test
    public void getAllComments_ReturnsAllComments() {
        when(commentRepository.findAll()).thenReturn(COMMENT_LIST);

        List<Comment> commentList = commentServices.findAll();


        assertThat(commentList).isNotEmpty();
        assertThat(commentList).hasSize(COMMENT_LIST.size());
        assertThat(commentList.get(0)).isEqualTo(COMMENT_LIST.get(0));

    }


    @Test
    public void getPostById_With_ValidId_ReturnsComment() {
        when(commentRepository.findById(COMMENT.getId())).thenReturn(Optional.of(COMMENT));

        Comment comment = commentServices.findById(COMMENT.getId());

        assertThat(comment).isNotNull();
        assertThat(comment).isEqualTo(COMMENT);
    }

    @Test
    public void getPostById_With_InvalidId_ThrowsEntityNotFoundException() {
        when(commentRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentServices.findById("99"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Comment not found");
    }

    @Test
    public void getCommentByPostId_With_ValidPostId_ReturnsComment() {
        when(commentRepository.findByPostId(COMMENT.getPostId())).thenReturn(COMMENT_LIST_POSTID);

        List<Comment> commentList = commentServices.getCommentsByPost(COMMENT.getPostId());

        assertThat(commentList).isNotEmpty();
        assertThat(commentList).hasSize(COMMENT_LIST_POSTID.size());
        assertThat(commentList.get(0)).isEqualTo(COMMENT_LIST_POSTID.get(0));
    }


    @Test
    public void updateComment_With_ValidData_ReturnsUpdatedComment() {
        when(commentRepository.findById(COMMENT.getId())).thenReturn(Optional.of(COMMENT));
        UPDATE_COMMENT.setId(COMMENT.getId());
        when(commentRepository.save(any(Comment.class))).thenReturn(UPDATE_COMMENT);

        Comment updatedComment = commentServices.updateComment(COMMENT.getId(), UPDATE_COMMENT);


        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment).isEqualTo(UPDATE_COMMENT);
        assertThat(updatedComment.getId()).isEqualTo(COMMENT.getId());

        assertThat(updatedComment.getName()).isEqualTo(UPDATE_COMMENT.getName());
        assertThat(updatedComment.getEmail()).isEqualTo(UPDATE_COMMENT.getEmail());
        assertThat(updatedComment.getBody()).isEqualTo(UPDATE_COMMENT.getBody());
    }

    @Test
    public void updateComment_With_InvalidPostId_ThrowsEntityNotFoundException() {
        when(commentRepository.findById(COMMENT.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentServices.updateComment(COMMENT.getId(), UPDATE_COMMENT))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void deleteComment_With_ValidId_DoesNotThrowException() {
        when(commentRepository.findById(COMMENT.getId())).thenReturn(Optional.of(COMMENT));
        assertThatCode(() -> commentServices.delete(COMMENT.getId())).doesNotThrowAnyException();

    }

    @Test
    public void deleteComment_With_InvalidId_DoesThrowException() {
        when(commentRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentServices.delete("99"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Comment not found");

    }


    @Test
    public void fromDTO_With_ValidData_ReturnsComment() {
       CommentDTO objDTO = COMMENT_DTO;

       Comment comment = commentServices.fromDTO(objDTO);

       assertThat(objDTO).isEqualTo(COMMENT_DTO);

       assertThat(comment).isNotNull();
       assertThat(objDTO.getId()).isEqualTo(comment.getId());
       assertThat(objDTO.getPostId()).isEqualTo(comment.getPostId());
       assertThat(objDTO.getName()).isEqualTo(comment.getName());
       assertThat(objDTO.getEmail()).isEqualTo(comment.getEmail());
       assertThat(objDTO.getBody()).isEqualTo(comment.getBody());


    }


}
