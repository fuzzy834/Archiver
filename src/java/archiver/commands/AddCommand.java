package archiver.commands;

import archiver.ConsoleOperations;
import archiver.ZipOperationExecutor;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleOperations.writeToConsole("Adding new file to the archive.");
            ZipOperationExecutor zipOperationExecutor = getZipOperationExecutor();
            ConsoleOperations.writeToConsole("Enter full name of the file you want to add:");
            Path sourcePath = Paths.get(ConsoleOperations.readTextFromConsole());
            zipOperationExecutor.addFileToArchive(sourcePath);
            ConsoleOperations.writeToConsole("Addition completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
