import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Folder> foldersList = List.of(
                new FolderImpl("Test1", "medium"),
                new FolderImpl("Test13", null),
                new FolderImpl(null, "x"),
                new FolderImpl(null, "x")
        );

        FileCabinet fileCabinet = new FileCabinet(foldersList);
        //Folder f = fileCabinet.findFolderByName(null).orElseThrow();
        List<Folder> modifiedList = fileCabinet.findFoldersBySize("medium");

        System.out.println(modifiedList);
        //System.out.println(f.getSize());
    }
}
