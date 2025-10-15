import utils.Util;

import java.util.List;
import java.util.Optional;

public class FileCabinet implements Cabinet{
    private List<Folder> folders;

    public FileCabinet(List<Folder> folders) {
        this.folders = folders;
    }

    @Override
    public Optional<Folder> findFolderByName(String name) {
        String normalized = Util.normalize(name);
        if(normalized == null) return Optional.empty();

        return folders.stream()
                .filter(
                        folder -> folder.getName().equals(name))
                .findFirst();
    }

    @Override
    public List<Folder> findFoldersBySize(String size) {
        return List.of();
    }

    @Override
    public int count() {
        return 0;
    }

}
