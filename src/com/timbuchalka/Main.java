package com.timbuchalka;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {


        // Showing regular files in try block later using filter
        /*DirectoryStream.Filter<Path> filter = new DirectoryStream<Path>() {
            public boolean accept(Path path) throws IOException {
                return Files.isRegularFile(path);
            }
        };*/

        // The same thing using lambda expressions
        DirectoryStream.Filter<Path> filter = p -> Files.isRegularFile(p);

        //Path directory = FileSystems.getDefault().getPath("FileTree\\Dir2");
        Path directory = FileSystems.getDefault().getPath("FileTree", File.separator, "Dir2");
        try (DirectoryStream<Path> contents = Files.newDirectoryStream(directory, filter)) {
            for (Path file : contents) {
                System.out.println(file.getFileName());
            }

        } catch (IOException | DirectoryIteratorException e) {
            System.out.println(e.getMessage());
        }

        String separator = File.separator;
        System.out.println(separator);

        separator = FileSystems.getDefault().getSeparator();
        System.out.println(separator);

        try {
            Path tmpFile = Files.createTempFile("myapp", ".appext");
            System.out.println("Temporary file path = " + tmpFile.toAbsolutePath()); //C:\Users\arien\AppData\Local\Temp\myapp837208210237454236.appext

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("--------------");
        Iterable<FileStore> stores = FileSystems.getDefault().getFileStores();
        for (FileStore store : stores) {
            System.out.println("Volume name / Drive letter: " + store);
            System.out.println("File store: " + store.name());
        }

        Iterable<Path> rootPaths = FileSystems.getDefault().getRootDirectories();
        for (Path path : rootPaths) {
            System.out.println(path);
        }

        System.out.println("<<<Walking tree fo DIR2>>>");
        Path dir2Path = FileSystems.getDefault().getPath("FileTree" + File.separator + "Dir2");
        try {
            Files.walkFileTree(dir2Path, new PrintNames());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("<<<Copying DIR2 to DIR4\\DIR2COPY>>>");
        Path copyPath = FileSystems.getDefault().getPath("FileTree" + File.separator + "Dir4" +
                File.separator + "Dir2Copy");
        try {
            Files.walkFileTree(dir2Path, new CopyFiles(dir2Path, copyPath));

        } catch (IOException e) {
            e.getMessage();
        }

        System.out.println("__________________________________________");
        File file = new File("C:\\Examples\\file.txt");   // /Examples/file.txt
        Path convertedPath = file.toPath();
        System.out.println("convertedPath = " + convertedPath);

        File parent = new File("\\Examples");  // /Examples
        File resolvedFile = new File(parent, "dir\\file.txt");  // dir/file.txt
        System.out.println(resolvedFile.toPath());

        resolvedFile = new File("\\Examples", "dir\\file.txt");  // /Examples   dir/file.txt
        System.out.println(resolvedFile.toPath());

        Path parentPath = Paths.get("C:\\Examples");  // /Examples
        Path childRelativePath = Paths.get("dir\\file.txt");  // dir/file.txt
        System.out.println(parentPath.resolve(childRelativePath));

        File workingDirectory = new File("").getAbsoluteFile();
        System.out.println("Working directory = " + workingDirectory.getAbsolutePath());

        System.out.println("--- print Dir2 contents using list() ---");
        File dir2File = new File(workingDirectory, "\\FileTree\\Dir2");   // /FileTree/Dir2
        String[] dir2Contents = dir2File.list();
        for (int i = 0; i < dir2Contents.length; i++) {
            System.out.println("i = " + i + ": " + dir2Contents[i]);
        }

        System.out.println("--- print Dir2 contents using listFiles() ---");
        File[] dir2Files = dir2File.listFiles();
        for(int i=0; i< dir2Files.length; i++) {
            System.out.println("i= " + i + ": " + dir2Files[i].getName());
        }

    }
}
