package server.server.fileSystem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MyRequest {
    private String username;
    private byte[] image;
}
