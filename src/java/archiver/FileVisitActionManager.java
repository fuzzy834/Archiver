package archiver;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitalii on 10/5/2016.
 */
public class FileVisitActionManager {

    private Path root;
    private List<Path> fileList;

    public FileVisitActionManager(Path root) throws IOException {
        this.root = root;
        this.fileList = new ArrayList<>();
        collectFileList(root);
    }

    public List<Path> getFileList() {
        return fileList;
    }

    private void collectFileList(Path path) throws IOException {
        if (Files.isRegularFile(path)) {
            Path relativePath = root.relativize(path);
            fileList.add(relativePath);
        }
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path)) {
                directoryStream.forEach(f -> {
                    try {
                        collectFileList(f);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
