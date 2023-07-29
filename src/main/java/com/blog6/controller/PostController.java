package com.blog6.controller;

import com.blog6.payload.PostDto;
import com.blog6.payload.PostResponse;
import com.blog6.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    //Http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {

        if(result.hasErrors()){
            return  new ResponseEntity<>(result.getFieldError().getDefaultMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.createPost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //Http://localhost:8080/api/posts?pageNo=0&pageSize=4&sortBy= description
    @GetMapping
    public PostResponse getAllPosts(@RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
                                     @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
                                     @RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy,
                                     @RequestParam(value = "sortDir",defaultValue = "id",required = false)String sortDir) {
      PostResponse postDtos = postService.getAllPosts( pageNo, pageSize, sortBy, sortDir);

        return postDtos;
    }
    //Http://localhost:8080/api/posts/4
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);
        return new  ResponseEntity<>(postDto,HttpStatus.OK);
    }
    //Http://localhost:8080/api/posts/4
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto) {

        PostDto dto = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable("id") long id) {
        postService.deletePostById(id);
        return new ResponseEntity<>("Post is deleted",HttpStatus.OK);
    }

}