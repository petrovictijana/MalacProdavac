package server.server.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class CommentDTO {
    private Date date;
    private String text;
    private int grade;

    private String name;
    private String surname;
    private String username;

    private String picture;

}
