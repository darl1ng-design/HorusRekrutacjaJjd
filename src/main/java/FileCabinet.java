import utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet, MultiFolder{
    private List<Folder> folders = new ArrayList<>();
    private String name;
    private String size;

    public FileCabinet(List<Folder> folders, String name, String size) {
        this.folders.addAll(folders);
        this.name = name;
        this.size = size;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        String normalizedName = Util.normalize(name);
        if(normalizedName == null || folders == null) return Optional.empty();

      return folders.stream()
              .filter(folder -> folder.getName().equals(normalizedName))
              .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        String normalizedSize = Util.assertAllowedSize(Util.normalize(size));
        return folders.stream()
                .filter(folders -> folders.getSize().equals(normalizedSize))
                .toList();
    }

    @Override
    public int count() {
        int counter = 0;
        for(Folder f : folders) counter ++;
        return counter;
    }

    @Override
    public List<Folder> getFolders() {
        return folders;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSize() {
        return size;
    }
}
