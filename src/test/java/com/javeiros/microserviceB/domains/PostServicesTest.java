package com.javeiros.microserviceB.domains;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
import com.javeiros.microserviceB.exception.EntityNotFoundException;
import com.javeiros.microserviceB.repository.PostRepository;
import com.javeiros.microserviceB.repository.PostRepository;
import com.javeiros.microserviceB.services.PostServices;
import com.javeiros.microserviceB.services.PostServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.javeiros.microserviceB.common.PostConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServicesTest {


    @InjectMocks
    private PostServices postServices;

    @Mock
    private PostRepository postRepository;


    @Test
    public void createPost_With_ValidData_ReturnsPost() {
        when(postRepository.save(POST)).thenReturn(POST);

        Post comment = postServices.save(POST);
        assertThat(comment).isEqualTo(POST);
    }


    @Test
    public void getAllPosts_ReturnsAllPosts() {
        when(postRepository.findAll()).thenReturn(POST_LIST);

        List<Post> commentList = postServices.findAll();


        assertThat(commentList).isNotEmpty();
        assertThat(commentList).hasSize(POST_LIST.size());
        assertThat(commentList.get(0)).isEqualTo(POST_LIST.get(0));

    }


    @Test
    public void getPostById_With_ValidId_ReturnsPost() {
        when(postRepository.findById(POST.getId())).thenReturn(Optional.of(POST));

        Post comment = postServices.findById(POST.getId());

        assertThat(comment).isNotNull();
        assertThat(comment).isEqualTo(POST);
    }

    @Test
    public void getPostById_With_InvalidId_ThrowsEntityNotFoundException() {
        when(postRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postServices.findById("99"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Post not found");
    }



    @Test
    public void updatePost_With_ValidData_ReturnsUpdatedPost() {
        when(postRepository.findById(POST.getId())).thenReturn(Optional.of(POST));
        POST_UPDATE.setId(POST.getId());
        when(postRepository.save(any(Post.class))).thenReturn(POST_UPDATE);

        Post updatedPost = postServices.update(POST_UPDATE);


        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost).isEqualTo(POST_UPDATE);
        assertThat(updatedPost.getId()).isEqualTo(POST.getId());

        assertThat(updatedPost.getTitle()).isEqualTo(POST_UPDATE.getTitle());
        assertThat(updatedPost.getUserId()).isEqualTo(POST_UPDATE.getUserId());
        assertThat(updatedPost.getBody()).isEqualTo(POST_UPDATE.getBody());
    }


    @Test
    public void updatePost_With_InvalidPostId_ThrowsEntityNotFoundException() {
        when(postRepository.findById(POST_UPDATE.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postServices.update(POST_UPDATE))
                .isInstanceOf(EntityNotFoundException.class);
    }


    @Test
    public void deletePost_With_ValidId_DoesNotThrowException() {
        when(postRepository.findById(POST.getId())).thenReturn(Optional.of(POST));
        assertThatCode(() -> postServices.delete(POST.getId())).doesNotThrowAnyException();

    }

    @Test
    public void deletePost_With_InvalidId_DoesThrowException() {
        when(postRepository.findById("99")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> postServices.delete("99"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Post not found");

    }


    @Test
    public void fromDTO_With_ValidData_ReturnsPost() {
        PostDTO objDTO = POSTDTO;

        Post comment = postServices.fromDTO(objDTO);

        assertThat(objDTO).isEqualTo(POSTDTO);

        assertThat(comment).isNotNull();
        assertThat(objDTO.getId()).isEqualTo(comment.getId());
        assertThat(objDTO.getUserId()).isEqualTo(comment.getUserId());
        assertThat(objDTO.getTitle()).isEqualTo(comment.getTitle());
        assertThat(objDTO.getBody()).isEqualTo(comment.getBody());


    }
}