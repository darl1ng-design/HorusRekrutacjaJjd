/**
 * Represents a logical container that groups multiple {@link Folder} instances.
 * <p>
 * The {@code FileCabinet} class provides lookup, filtering, and metadata
 * access for a collection of folders. It implements both {@link Cabinet}
 * and {@link MultiFolder} interfaces, which define the behavior expected
 * from entities capable of holding multiple folders and exposing aggregate
 * operations such as counting and retrieval.
 * </p>
 *
 * <h2>Responsibilities</h2>
 * <ul>
 *     <li>Stores and manages a collection of {@link Folder} objects.</li>
 *     <li>Allows lookup of folders by name or size.</li>
 *     <li>Normalizes and validates user-provided parameters using {@link Util} methods.</li>
 * </ul>
 *
 * <h2>Usage Example</h2>
 * <pre>{@code
 * FileCabinet cabinet = new FileCabinet(
 *     List.of(new FolderImpl("test1", "small"),
 *             new FolderImpl("test2", "medium")),
 *     "MainCabinet",
 *     "medium"
 * );
 *
 * Optional<Folder> folder = cabinet.findFolderByName("Test1");
 * List<Folder> smallFolders = cabinet.findFoldersBySize("small");
 * int count = cabinet.count();
 * }</pre>
 */

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
        return folders == null ? 0 : folders.size();
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
