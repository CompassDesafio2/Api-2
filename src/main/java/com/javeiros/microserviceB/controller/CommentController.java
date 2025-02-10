package com.javeiros.microserviceB.controller;

import com.javeiros.microserviceB.entities.Comment;
import com.javeiros.microserviceB.entities.dto.CommentDTO;
import com.javeiros.microserviceB.exception.ErrorMessage;
import com.javeiros.microserviceB.services.CommentServices;
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
@RequestMapping("/comments")
public class CommentController {


    @Autowired
    private CommentServices services;

    @Operation(summary = "Pegar Todos Comentários", description = "Pegar todos os Comentários salvas no banco",

            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Todos os Comentários foram retornados!"
                    ),

                    @ApiResponse(responseCode = "404",
                            description = "Nenhum Comentário foi encontrado",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CommentDTO.class))
                    ),

            })
    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        List<Comment> list = services.findAll();
        List<CommentDTO> listDTO = list.stream().map(CommentDTO::new).toList();

        return ResponseEntity.ok().body(listDTO);
    }


    @Operation(summary = "Comentarios de um Post", description = "Pegar todos os Comentários associados a um Post",

            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comentário encontrado com Sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CommentDTO.class))
                    ),
            })
    @GetMapping("post/{postId}")
    public ResponseEntity<List<Comment>> getCommentByUserId(@PathVariable String postId) {
        List<Comment> comment = services.getCommentsByPost(postId);
        return ResponseEntity.ok().body(comment);
    }

    @Operation(summary = "Informação de Um Comentario", description = "Pegar as informações de um Comentário",

            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Comentário encontrado com Sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CommentDTO.class))
                    ),
            })
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable String id) {
        Comment comment = services.findById(id);
        return ResponseEntity.ok().body(comment);
    }


    @Operation(summary = "Criar Novo Comentário", description = "Criar Comentário",

            responses = {
                    @ApiResponse(responseCode = "201",
                            description = " Novo Comentário Criado com Sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CommentDTO.class))
                    ),


            })
    @PostMapping("/{postId}")
    public ResponseEntity<Comment> insertComment(@PathVariable String postId, @RequestBody CommentDTO objDTO) {

        System.out.println(objDTO);
        Comment obj = services.fromDTO(objDTO);
        System.out.println(obj);

        obj = services.addComment(postId, obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);

    }

    @Operation(summary = "Atualizar Comentário", description = "Atualizar todas as informações de um Comentário através da ID informada",

            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Atualização feita com sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CommentDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Comentário Não Encontrado",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),

            })
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable String id, @RequestBody CommentDTO objDTO) {
        Comment obj = services.fromDTO(objDTO);
        obj.setId(id);
        obj = services.updateComment(obj.getPostId(), obj);

        return ResponseEntity.status(204).body(obj);
    }


    @Operation(summary = "Deletar Comentário", description = "Deletar uma Comentário através da ID informada",

            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Comentário Deletado Com Sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = CommentDTO.class))
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Comentário Não Encontrado",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))
                    ),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable String id) {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }
}
