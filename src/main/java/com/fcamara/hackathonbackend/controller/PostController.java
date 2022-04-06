package com.fcamara.hackathonbackend.controller;

import com.fcamara.hackathonbackend.model.Post;
import com.fcamara.hackathonbackend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    private PostRepository actions;

    @GetMapping
    public @ResponseBody
    List<Post> listar(){
        return actions.findAll();
    }

    @PostMapping
    public @ResponseBody Post cadastrar(@RequestBody Post post) {
        return actions.save(post);

    }
}
