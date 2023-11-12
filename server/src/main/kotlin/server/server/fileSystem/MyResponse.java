package server.server.fileSystem;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class MyResponse implements Serializable {
    private String ime;
    private byte[] image;
}
