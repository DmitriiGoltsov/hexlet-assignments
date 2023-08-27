package exercise.controller;

import exercise.dto.CommentDto;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    // BEGIN
    @GetMapping(path = "/{postId}/comments")
    public Iterable<Comment> getAllCommentsFromPost(@PathVariable Long postId) {
        return commentRepository.findAllCommentsByPostId(postId);
    }

    @GetMapping(path = "/{postId}/comments/{commentId}")
    public Comment getCommentFromPost(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId) {

        return commentRepository.findCommentByIdAndPostId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with such id was not found"));
    }

    @PostMapping(path = "/{postId}/comments")
    public Iterable<Comment> postCommentToPost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody Comment comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("The post with current id was not found"));

        comment.setPost(post);

        commentRepository.save(comment);

        return commentRepository.findAllCommentsByPostId(postId);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public void updateCommentInId(@PathVariable(name = "postId") Long postId,
                                  @PathVariable(name = "commentId") Long commentId,
                                  @PathVariable(name = "content") String content) {

        Comment comment = commentRepository.findCommentByIdAndPostId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("The comment with current id was not found"));

        comment.setContent(content);

        commentRepository.save(comment);
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void deleteCommentFromPost(@PathVariable(name = "postId") Long postId,
                                      @PathVariable(name = "commentId") Long commentId) {

        Comment comment = commentRepository.findCommentByIdAndPostId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("The comment with current id was not found"));

        commentRepository.delete(comment);
    }
    // END
}
