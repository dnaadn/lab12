package com.example.lab12.Service;

import com.example.lab12.API.ApiException;
import com.example.lab12.Model.Blog;
import com.example.lab12.Model.User;
import com.example.lab12.Repository.AuthRepository;
import com.example.lab12.Repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public List<Blog> getMyBlogs(Integer userId) {
        User user = authRepository.findUserById(userId);
        return blogRepository.findAllByUser(user);
    }

    public void addBlog(Integer userId, Blog blog) {
        User user = authRepository.findUserById(userId);
        blog.setUser(user);
        blogRepository.save(blog);
    }

    public void updateBlog(Integer userId, Integer blogId, Blog blog) {
        Blog oldBlog = blogRepository.findBlogById(blogId);
        User user = authRepository.findUserById(userId);
        if(user==null){
            throw new ApiException("User not found");
        }
        if(oldBlog==null){
            throw new ApiException("Blog not found");
        }
        if(oldBlog.getUser().getId()!=userId){
            throw new ApiException("can't update");
        }

        oldBlog.setTitle(blog.getTitle());
        oldBlog.setBody(blog.getBody());
        blogRepository.save(oldBlog);
    }

    public void deleteBlog(Integer userId, Integer blogId) {
        Blog blog = blogRepository.findBlogById(blogId);
        User user = authRepository.findUserById(userId);

        if(user==null){
            throw new ApiException("User not found");
        }
        if(blog==null){
            throw new ApiException("Blog not found");
        }
        if(!blog.getUser().getId().equals(userId)){
            throw new ApiException("Unauthorized, this blog doesn't belong to you");
        }
        blogRepository.delete(blog);
    }

    public Blog getBlogById(Integer userId, Integer blogId) {
        Blog blog = blogRepository.findBlogById(blogId);
        User user = authRepository.findUserById(userId);

        if(user==null){
            throw new ApiException("User not found");
        }
        if(blog==null){
            throw new ApiException("Blog not found");
        }
        if(!blog.getUser().getId().equals(userId)){
            throw new ApiException("Unauthorized, this blog doesn't belong to you");
        }
        return blog;    }

    public Blog getBlogByTitle(Integer userId, String title) {
        User user = authRepository.findUserById(userId);
        Blog blog = blogRepository.findBlogByTitleAndUser(title, user);
        if (blog == null) {
            throw new ApiException("Blog not found");
        }
        return blog;
    }


}
