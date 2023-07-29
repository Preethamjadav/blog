package com.blog6.payload;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String body;
    private String name;
    private String email;
}