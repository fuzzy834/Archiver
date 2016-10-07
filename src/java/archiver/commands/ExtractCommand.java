package archiver.commands;

import archiver.ConsoleOperations;
import archiver.ZipOperationExecutor;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleOperations.writeToConsole("Extracting files from the archive.");
            ZipOperationExecutor zipOperationExecutor = getZipOperationExecutor();
            ConsoleOperations.writeToConsole("Enter path for unpacking:");
            Path destinationPath = Paths.get(ConsoleOperations.readTextFromConsole());
            zipOperationExecutor.unzipArchive(destinationPath);
            ConsoleOperations.writeToConsole("Extracting completed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
