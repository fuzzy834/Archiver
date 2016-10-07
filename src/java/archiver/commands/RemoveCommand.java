package archiver.commands;


import archiver.ConsoleOperations;
import archiver.ZipOperationExecutor;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RemoveCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleOperations.writeToConsole("Removing files from archive.");
        ZipOperationExecutor zipOperationExecutor = getZipOperationExecutor();
        ConsoleOperations.writeToConsole("Enter full path of the file in archive:");
        Path sourcePath = Paths.get(ConsoleOperations.readTextFromConsole());
        zipOperationExecutor.removeFileFromArchive(sourcePath);
        ConsoleOperations.writeToConsole("Removal completed.");
    }
}