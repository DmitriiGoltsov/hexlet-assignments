package exercise;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

class App {

    public static CompletableFuture<Long> getDirectorySize(String directoryPath) {

        return CompletableFuture.supplyAsync(() -> {
            Long size;

            try {
                size = Files.walk(getAbsoluteNormilizedPath(directoryPath), 1)
                        .filter(Files::isRegularFile)
                        .mapToLong(file -> {
                            try {
                                return Files.size(file);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .sum();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return size;
        }).exceptionally(ex -> {
            System.out.println("An exception has occurred " + ex.getMessage());
            return null;
        });
    }

    // BEGIN
    public static CompletableFuture<String> unionFiles(String file1Path,
                                                       String file2Path,
                                                       String resultFilePath)
            throws ExecutionException, InterruptedException {

        CompletableFuture<String> content1 = CompletableFuture.supplyAsync(() -> {
            Path path1 = getAbsoluteNormilizedPath(file1Path);
            try {
                return readFile(path1);
            } catch (IOException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> content2 = CompletableFuture.supplyAsync(() -> {
            Path path2 = getAbsoluteNormilizedPath(file2Path);
            try {
                return readFile(path2);
            } catch (IOException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> combinedContent = content1.thenCombine(content2, (con1, con2) -> {
            Path destinationPath = getAbsoluteNormilizedPath(resultFilePath);
            String result = con1.concat(con2);

            try {
                writeFile(destinationPath, result);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return result;
        }).exceptionally(ex -> {
            System.out.println("An exception has occurred!");
            System.out.println("Exception message: " + ex.getMessage());
            return null;
        });

        return combinedContent;
    }

    private static String readFile(Path path) throws NoSuchFieldException, IOException {
        if (!Files.exists(path)) {
            throw new NoSuchFileException("The file with path " + path + " does not exist!");
        }
        return Files.readString(path);
    }

    private static void writeFile(Path path, String content) throws IOException {
        Files.writeString(path, content);
    }

    private static Path getAbsoluteNormilizedPath(String filePAth) {
        return Path.of(filePAth).toAbsolutePath().normalize();
    }
    // END

    public static void main(String[] args) throws Exception {
        // BEGIN
        CompletableFuture<String> result = unionFiles(
                "src/main/resources/file1.txt",
                "src/main/resources/file2.txt",
                "src/main/resources/result_file.txt");

        System.out.println(result.get());

        CompletableFuture<Long> size = getDirectorySize("src/main/resources");
        result.get();
        System.out.println("done!");
        System.out.println(size.get());
        // END
    }
}

