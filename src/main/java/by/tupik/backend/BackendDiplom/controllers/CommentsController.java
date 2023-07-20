package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.CommentDTO;
import by.tupik.backend.BackendDiplom.dto.CommentsResponse;
import by.tupik.backend.BackendDiplom.models.Comment;
import by.tupik.backend.BackendDiplom.servicies.CommentsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {
    private final CommentsService commentsService;
    private final ModelMapper modelMapper;

    @Autowired
    public CommentsController(CommentsService commentsService, ModelMapper modelMapper) {
        this.commentsService = commentsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public Comment createComments(@RequestBody @Valid Comment comment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        commentsService.create(comment);

        return comment;
    }

    @GetMapping()
    public CommentsResponse getAllComments(){
        return new CommentsResponse(commentsService.findAll().stream().map(this::convertToCommentsDTO).collect(Collectors.toList()));
    }

    private Comment convertToComments(CommentDTO commentsDTO) {
        return modelMapper.map(commentsDTO, Comment.class);
    }

    private CommentDTO convertToCommentsDTO(Comment comments) {
        return modelMapper.map(comments, CommentDTO.class);
    }

    @GetMapping("/{id}")
    public Comment getComments(@PathVariable int id){
        return commentsService.findOne(id);
    }

    @PutMapping("/{id}")
    public Comment updateComments(@PathVariable int id, @RequestBody @Valid Comment updatedComment, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        commentsService.update(id, updatedComment);

        return updatedComment;
    }

    @DeleteMapping("/{id}")
    public int deleteComments(@PathVariable int id){
        commentsService.delete(id);
        return id;
    }
}
