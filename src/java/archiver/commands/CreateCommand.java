package archiver.commands;

import archiver.ConsoleOperations;
import archiver.ZipOperationExecutor;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleOperations.writeToConsole("Creating an archive.");
            ZipOperationExecutor zipOperationExecutor = getZipOperationExecutor();
            ConsoleOperations.writeToConsole("Please enter full file or directory name for adding to archive:");
            Path sourcePath = Paths.get(ConsoleOperations.readTextFromConsole());
            zipOperationExecutor.createArchive(sourcePath);
            ConsoleOperations.writeToConsole("Archive created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
