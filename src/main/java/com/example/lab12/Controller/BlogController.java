package com.example.lab12.Controller;

import com.example.lab12.API.ApiResponse;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
@RequiredArgsConstructor
public class BlogController {


    private final BlogService blogService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAllBlogs() {
        return ResponseEntity.status(200).body(blogService.getAllBlogs());
    }

    @GetMapping("/my-blogs")
    public ResponseEntity<?> getMyBlogs(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(blogService.getMyBlogs(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBlog(@AuthenticationPrincipal User user, @RequestBody @Valid Blog blog) {
        blogService.addBlog(user.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog added successfully"));
    }

    @PutMapping("/update/{blogId}")
    public ResponseEntity<?> updateBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId, @RequestBody @Valid Blog blog) {
        blogService.updateBlog(user.getId(), blogId, blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog updated successfully"));
    }

    @DeleteMapping("/delete/{blogId}")
    public ResponseEntity<?> deleteBlog(@AuthenticationPrincipal User user, @PathVariable Integer blogId) {
        blogService.deleteBlog(user.getId(), blogId);
        return ResponseEntity.status(200).body(new ApiResponse("Blog deleted successfully"));
    }

    @GetMapping("/get/{blogId}")
    public ResponseEntity<?> getBlogById(@AuthenticationPrincipal User user, @PathVariable Integer blogId) {
        return ResponseEntity.status(200).body(blogService.getBlogById(user.getId(), blogId));
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<?> getBlogByTitle(@AuthenticationPrincipal User user, @PathVariable String title) {
        return ResponseEntity.status(200).body(blogService.getBlogByTitle(user.getId(), title));
    }
}
