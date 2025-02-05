package com.javeiros.microserviceB.controller;

import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostCreateDTO;
import com.javeiros.microserviceB.entities.dto.PostResponseDTO;
import com.javeiros.microserviceB.services.PostServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    private PostServices services;


    @Operation(summary = "Pegar Todos as POSTAGENS", description = "Pegar as Postagens salvas no Banco",

            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostResponseDTO.class))
                    ),
            })
    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPost() {
        List<Post> list = services.findAll();
        List<PostResponseDTO>  listDTO = list.stream().map(PostResponseDTO::new).toList();

        return ResponseEntity.ok().body(listDTO);
    }


    @Operation(summary = "Informação de Um POST", description = "Pegar as inforrmações de uma Postagem",

            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Client Created with SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostCreateDTO.class))
                    ),
            })
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPost(@PathVariable String id) {
        Post post = services.findById(id);
        System.out.println(post);
        return ResponseEntity.ok().body(post);
    }


    @Operation(summary = "Criar Novo Post", description = "CREATE",

            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostCreateDTO.class))
                    ),

            })
    @PostMapping
    public ResponseEntity<Post> insertPost(@RequestBody PostCreateDTO objDTO) {
        Post obj = services.fromDTO(objDTO);
        obj = services.save(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody PostCreateDTO objDTO) {
        Post obj = services.fromDTO(objDTO);
        obj.setId(id);
        obj = services.update(obj);

        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}
