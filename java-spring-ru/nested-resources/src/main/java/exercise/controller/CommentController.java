package exercise.controller;

import exercise.dto.CommentDto;
import exercise.model.Comment;
import exercise.repository.CommentRepository;
import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;

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

        return commentRepository.findCommentByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentId + " was not found"));
    }

    @PostMapping(path = "/{postId}/comments")
    public Iterable<Comment> postCommentToPost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody Comment comment) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("The post with id " + postId + " was not found"));

        comment.setPost(post);

        commentRepository.save(comment);

        return commentRepository.findAllCommentsByPostId(postId);
    }

    @PatchMapping(path = "/{postId}/comments/{commentId}")
    public Comment updateComment(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @RequestBody CommentDto commentDto) {

        Comment oldComment = commentRepository.findCommentByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("The comment with id " + commentId + " was not found"));

        oldComment.setContent(commentDto.content());

        return commentRepository.save(oldComment);
    }

    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public void deleteCommentFromPost(@PathVariable(name = "postId") Long postId,
                                      @PathVariable(name = "commentId") Long commentId) {

        Comment comment = commentRepository.findCommentByPostIdAndId(postId, commentId)
                .orElseThrow(() -> new ResourceNotFoundException("The comment with id " + commentId + " was not found"));

        commentRepository.delete(comment);
    }
    // END
}
