package archiver.commands;

import archiver.ConsoleOperations;
import archiver.ZipOperationExecutor;

public class ContentCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleOperations.writeToConsole("Showing contents of the archive.");
        ZipOperationExecutor zipOperationExecutor = getZipOperationExecutor();
        ConsoleOperations.writeToConsole("Contents:");
        zipOperationExecutor.getFileList().forEach(ConsoleOperations::writeToConsole);
        ConsoleOperations.writeToConsole("Reading completed.");
    }
}
