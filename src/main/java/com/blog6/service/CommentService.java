package com.blog6.service;

import com.blog6.entity.Comment;
import com.blog6.payload.CommentDto;

import java.util.List;

public interface CommentService {



    CommentDto saveComment(Long postId, CommentDto commentDto);

    public List<CommentDto> getCommentsByPostId(Long postId);
    CommentDto getCommentById(Long postId, Long commentId);

    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto);

    void deleteComment(Long postId, Long commentId);
}
