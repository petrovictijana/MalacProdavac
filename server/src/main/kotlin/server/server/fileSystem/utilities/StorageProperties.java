package server.server.fileSystem.utilities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ConfigurationProperties("storage")
@Component
public class StorageProperties {
    private String location = "../resources/fileSystem";
}
