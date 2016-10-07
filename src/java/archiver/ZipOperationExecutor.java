package archiver;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by Vitalii on 10/3/2016.
 */

public class ZipOperationExecutor
{
    private final Path archive;

    public ZipOperationExecutor(Path archive){
        this.archive = archive;
    }

    public void createArchive(Path path) throws IOException {
        Path parentDir = archive.getParent();
        if (Files.notExists(parentDir)) Files.createDirectories(parentDir);
        try(ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(archive))) {
             if (Files.isDirectory(path)) {
                FileVisitActionManager fileManager = new FileVisitActionManager(path);
                List<Path> files = fileManager.getFileList();
                files.forEach(f -> newZipEntry(zos, path, f));
             } else if (Files.isRegularFile(path)) {
                newZipEntry(zos, path.getParent(), path.getFileName());
             } else {
                throw new FileNotFoundException();
             }
        }
    }

    public void unzipArchive(Path folder) throws IOException {
        if(!Files.isRegularFile(archive))
            throw new FileNotFoundException();
        try(ZipInputStream zis = new ZipInputStream(Files.newInputStream(archive))) {
            if (Files.notExists(folder))
                Files.createDirectories(folder);
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null){
                Path file = folder.resolve(zipEntry.getName());
                Path parent = file.getParent();
                if (Files.notExists(parent)) Files.createDirectories(parent);
                try(OutputStream os = Files.newOutputStream(file)) {
                    transferData(zis, os);
                }
                zipEntry = zis.getNextEntry();
            }
        }
    }

    public void addFilesToArchive(List<Path> paths) throws IOException{
        if(!Files.isRegularFile(archive))
            throw new FileNotFoundException();
        Path temp = Files.createTempFile(null, null);
        List<Path> files = new ArrayList<>();
        try(ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(temp))){
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(archive))){
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null){
                    files.add(Paths.get(zipEntry.getName()));
                    zos.putNextEntry(new ZipEntry(zipEntry.getName()));
                    transferData(zis, zos);
                    zis.closeEntry();
                    zos.closeEntry();
                    zipEntry = zis.getNextEntry();
                }
            }
            paths.stream()
                    .filter(f-> (Files.isRegularFile(f) && !files.contains(f.getFileName())))
                    .forEach(f -> newZipEntry(zos, f.getParent(), f.getFileName()));
        }
        Files.move(temp, archive, StandardCopyOption.REPLACE_EXISTING);
    }

    public void removeFilesFromArchive(List<Path> paths) throws IOException{
        if(!Files.isRegularFile(archive))
            throw new FileNotFoundException();
        Path temp = Files.createTempFile(null, null);
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(temp))) {
            try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(archive))) {
                ZipEntry zipEntry = zis.getNextEntry();
                while (zipEntry != null){
                    if (!paths.contains(Paths.get(zipEntry.getName()))){
                        zos.putNextEntry(new ZipEntry(zipEntry.getName()));
                        transferData(zis, zos);
                        zis.closeEntry();
                        zos.closeEntry();
                    }
                    zipEntry = zis.getNextEntry();
                }
            }
        }
        Files.move(temp, archive, StandardCopyOption.REPLACE_EXISTING);
    }

    public List<String> getFileList() throws IOException {
        if (!Files.isRegularFile(archive)) {
            throw new FileNotFoundException();
        }
        List<String> files = new ArrayList<>();
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(archive))) {
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                transferData(zis, baos);
                files.add(zipEntry.getName());
                zipEntry = zis.getNextEntry();
            }
        }
        return files;
    }

    public void addFileToArchive(Path path) throws IOException {
        addFilesToArchive(Collections.singletonList(path));
    }

    public void removeFileFromArchive(Path path) throws Exception {
        removeFilesFromArchive(Collections.singletonList(path));
    }

    private void newZipEntry(ZipOutputStream zos, Path path, Path file)  {
        Path fullPath = path.resolve(file);
        try (InputStream is = Files.newInputStream(fullPath)) {
            ZipEntry entry = new ZipEntry(file.toString());
            zos.putNextEntry(entry);
            transferData(is, zos);
            zos.closeEntry();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void transferData(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[in.available()];
        int count;
        while ((count = in.read(buffer))>0)
            out.write(buffer, 0, count);
    }
}
