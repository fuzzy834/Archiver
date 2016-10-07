package archiver.commands;

import archiver.ConsoleOperations;
import archiver.ZipOperationExecutor;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ZipCommand implements Command {

    public ZipOperationExecutor getZipOperationExecutor() throws Exception{
        ConsoleOperations.writeToConsole("Enter full path to the archive:");
        Path zipPath = Paths.get(ConsoleOperations.readTextFromConsole());
        return new ZipOperationExecutor(zipPath);
    }
}