package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class CommentsResponse {
    private List<CommentDTO> comments;

    public CommentsResponse(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }
}
