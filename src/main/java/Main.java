import java.util.List;


public class Main {
    public static void main(String[] args) {
        List<Folder> foldersList = List.of(
                new FolderImpl("Test1", "small"),
                new FolderImpl("Test2", "medium"),
                new FolderImpl("Test3", "small"),
                new FolderImpl("Test4", "medium"),
                new FolderImpl("Test5", "large"),
                new FolderImpl("Test6", "large"),
                new FolderImpl("Test7", "small")
        );

        FileCabinet fileCabinet = new FileCabinet(foldersList, "FileCabinet", "medium");

        System.out.println("MultiFolder getFolders() result: " + fileCabinet.getFolders());

        System.out.println("Cabinet findByName() result: " + fileCabinet.findFolderByName("Test2"));
        System.out.println("Cabinet findBySize() result: " + fileCabinet.findFoldersBySize("medium"));
        System.out.println("Cabinet count() result: " + fileCabinet.count());
    }
}
