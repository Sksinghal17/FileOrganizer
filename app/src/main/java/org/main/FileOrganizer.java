package org.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class FileOrganizer {

  public static final String PATH = "YOUR_PATH";

  public static void main(String args[]) {
    final File folder = new File(PATH);
    listFilesForFolder(folder);
  }

  private static void listFilesForFolder(final File folder) {
    for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
      if (!fileEntry.isDirectory()) {
        String fileName = fileEntry.getName();
        String fileExtension = getFileExtension(fileName);
        if (!fileExtension.isEmpty()) {
          File extensionDirectory = new File(PATH + "/" + fileExtension);
          moveFileToDirectory(fileEntry, extensionDirectory);
        }
        System.out.println(fileName);
      }
    }
  }

  private static String getFileExtension(String fileName) {
    int lastIndexOfDot = fileName.lastIndexOf('.');
    if (lastIndexOfDot == -1 || lastIndexOfDot == fileName.length() - 1) {
      return ""; // No extension
    }
    return fileName.substring(lastIndexOfDot + 1).toLowerCase();
  }

  private static void moveFileToDirectory(File file, File directory) {
    try {
      if (!directory.exists()) {
        directory.mkdir();
      }
      Files.move(file.toPath(), new File(directory, file.getName()).toPath(),
          StandardCopyOption.REPLACE_EXISTING);
      System.out.println("Moved file: " + file.getName() + " to " + directory.getPath());
    } catch (IOException e) {
      System.err.println("Failed to move file: " + file.getName());
      e.printStackTrace();
    }
  }
}