package server.server.fileSystem.utilities;

import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FolderUtility {
    public static void deleteFolderContent(Path path){
        if(Files.exists(path) && Files.isDirectory(path)){
            try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)){
                for(Path file: directoryStream){
                    if(Files.isDirectory(file))
                        deleteFolderContent(file);
                    else
                        Files.deleteIfExists(file);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isFolderEmpty(Path path){
        if(Files.exists(path) && Files.isDirectory(path)){
            try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)){
                return !directoryStream.iterator().hasNext();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    public static byte[] convertResourceToByteArray(Resource resource) throws IOException {
        try (InputStream inputStream = resource.getInputStream()) {
            return StreamUtils.copyToByteArray(inputStream);
        }
    }
}
