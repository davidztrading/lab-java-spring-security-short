package com.blog.cms.controller;

import com.blog.cms.model.BlogPost;
import com.blog.cms.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    // Public Route
    @GetMapping
    public List<BlogPost> getAllPosts() {
        return blogPostRepository.findAll();
    }

    // Admin Protected Route
    @PostMapping
    public BlogPost createPost(@RequestBody BlogPost post) {
        return blogPostRepository.save(post);
    }

    // Admin Protected Route
    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updatePost(@PathVariable Long id, @RequestBody BlogPost postDetails) {
        return blogPostRepository.findById(id).map(post -> {
            post.setTitle(postDetails.getTitle());
            post.setPost(postDetails.getPost());
            if (postDetails.getAuthor() != null) {
                post.setAuthor(postDetails.getAuthor());
            }
            return ResponseEntity.ok(blogPostRepository.save(post));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Admin Protected Route
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        if (blogPostRepository.existsById(id)) {
            blogPostRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}