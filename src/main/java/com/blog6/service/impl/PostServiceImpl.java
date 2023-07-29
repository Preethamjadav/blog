package com.blog6.service.impl;

import com.blog6.entity.Post;
import com.blog6.exception.ResourceNotFoundException;
import com.blog6.payload.PostDto;
import com.blog6.payload.PostResponse;
import com.blog6.repositries.PostRepository;
import com.blog6.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private  PostRepository postRepository;
    private ModelMapper mapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;

    }

    @Override
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();


        PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> content = postRepository.findAll(pageable);
        List<Post> posts = content.getContent();

        List<PostDto> dto = posts.stream().map(post-> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dto);
       postResponse.setPageNo(content.getNumber());
       postResponse.setPageSize(content.getSize());
       postResponse.setTotalElements(content.getTotalElements());
       postResponse.setTotalPageNo(content.getTotalPages());
       postResponse.setLast(content.isLast());
        return postResponse;
    }



    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + id)
        );
        PostDto dto = mapToDto(post);

        return dto;
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + id)
        );
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatePost = postRepository.save(post);
        PostDto dto = mapToDto(updatePost);

        return dto;
    }



    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post not found with id: " + id)
        );
        postRepository.deleteById(id);
    }


    public Post mapToEntity(PostDto postDto) {
        Post post = mapper.map(postDto, Post.class);
//            Post post = new Post();
//            post.setId(postDto.getId());
//            post.setTitle(postDto.getTitle());
//            post.setDescription(postDto.getDescription());
//            post.setContent(postDto.getContent());
            return post;
        }

        public  PostDto mapToDto(Post post) {
            PostDto dto = mapper.map(post, PostDto.class);
//            PostDto dto = new PostDto();
//            dto.setId(post.getId());
//            dto.setTitle(post.getTitle());
//            dto.setDescription(post.getDescription());
//            dto.setContent(post.getContent());
            return dto;
        }
    }


