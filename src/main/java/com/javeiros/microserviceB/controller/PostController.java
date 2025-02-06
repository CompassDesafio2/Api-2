package com.javeiros.microserviceB.controller;

import com.javeiros.microserviceB.clients.JsonPlaceHolderService;
import com.javeiros.microserviceB.entities.Post;
import com.javeiros.microserviceB.entities.dto.PostDTO;
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
public class PostController  {

    @Autowired
    private PostServices services;

    @Autowired
    private JsonPlaceHolderService jsonApiServices;


    @Operation(summary = "FETCH DATA", description = "Atualiza os dados do banco de dados",

            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Os dados da API foram atualizados!",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
                    ),

                    @ApiResponse(responseCode = "404",
                            description = "Nenhuma Postagem foi encontrada",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
                    ),
            })

    @PostMapping("/fetch-data")
    public ResponseEntity<Void> fetchData() {
        jsonApiServices.fetchData();
        return ResponseEntity.ok().build();

    }




    @Operation(summary = "GET ALL POSTAGEM", description = "Pegar todas as Postagens salvas no banco",

            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Todas as Postagens foram retornadas!"
                    ),

                    @ApiResponse(responseCode = "404",
                            description = "Nenhuma Postagem foi encontrada",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
                    ),

            })
    @GetMapping
    public ResponseEntity<List<PostDTO>> getAllPost() {
        List<Post> list = services.findAll();
        List<PostDTO>  listDTO = list.stream().map(PostDTO::new).toList();

        return ResponseEntity.ok().body(listDTO);
    }


    @Operation(summary = "Informação de Um POST", description = "Pegar as informações de uma Postagem",

            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Postagem encontrada com Sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
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
                            description = " Novo Post Criado com Sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
                    ),

            })
    @PostMapping
    public ResponseEntity<Post> insertPost(@RequestBody PostDTO objDTO) {
        Post obj = services.fromDTO(objDTO);
        obj = services.save(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);

    }

    @Operation(summary = "Atualizar Postagem", description = "Atualizar todas as informações de uma Postagem através da ID informada",

            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Atualização feita com sucesso",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
                    ),

            })
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable String id, @RequestBody PostDTO objDTO) {
        Post obj = services.fromDTO(objDTO);
        obj.setId(id);
        obj = services.update(obj);

        return ResponseEntity.noContent().build();
    }


    @Operation(summary = "Deletar POSTAGEM", description = "Deletar uma Postagem através da ID informada",

            responses = {
                    @ApiResponse(responseCode = "204",
                            description = "Client Created with SUCCESS",
                            content = @Content(
                                    mediaType = "application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PostDTO.class))
                    ),
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable String id) {
        services.delete(id);
        return ResponseEntity.noContent().build();
    }

}
