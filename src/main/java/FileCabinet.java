import lombok.Setter;

import java.util.*;
import java.util.stream.Stream;

/**
 * Represents a file cabinet containing multiple folders with a specific name and size.
 *
 * <p>This class implements {@link Cabinet} and {@link MultiFolder}, providing
 * search and filtering operations for folders by name or size.</p>
 *
 * <p>The object stores its folders in memory.
 * This class is <b>not</b> thread-safe — if concurrent access is required,
 * external synchronization must be applied.</p>
 *
 * @author Adam Gyurjyan
 * @version 1.0
 * @since 1.0
 * @see Folder
 * @see Cabinet
 * @see MultiFolder
 */

@Setter
public class FileCabinet implements Cabinet, MultiFolder {
    private List<Folder> folders;
    private String name;
    private String size;

    /**
     * Creates a new {@link FileCabinet} with the specified folders, name, and size.
     *
     * <p>If the provided folder list is {@code null}, an empty list is used instead.</p>
     *
     * @param folders the list of folders to include in this cabinet (may be {@code null})
     * @param name    the name of the cabinet
     * @param size    the cabinet size (e.g. {@code "small"}, {@code "medium"}, {@code "large"})
     */
    public FileCabinet(List<Folder> folders, String name, String size) {
        this.folders = folders == null ? List.of() : new ArrayList<>(folders);
        this.name = name;
        this.size = size;
    }

    /**
     * Finds the first folder with the given name, including nested ones.
     *
     * <p>The comparison is case-insensitive and ignores leading or trailing spaces.</p>
     *
     * @param name the folder name to search for
     * @return an {@link Optional} containing the found folder, or empty if not found
     */
    @Override
    public Optional<Folder> findFolderByName(String name) {
        String normalizedName = normalize(name);
        if (normalizedName == null) return Optional.empty();
        return streamAllElements(folders)
                .filter(folder -> normalizedName.equals(normalize(folder.getName())))
                .findFirst();
    }

    /**
     * Finds all folders that match the given size, including nested ones.
     *
     * <p>Only sizes {@code "small"}, {@code "medium"}, and {@code "large"}
     * are allowed. The search is case-insensitive.</p>
     *
     * @param size the folder size to filter by
     * @return a list of all matching folders; may be empty but never {@code null}
     * @throws IllegalArgumentException if the size is {@code null}, blank, or invalid
     */
    @Override
    public List<Folder> findFoldersBySize(String size) {
        String normalizedSize = assertAllowedSize(size);
        return streamAllElements(folders)
                .filter(folders -> normalizedSize.equals(assertAllowedSize(folders.getSize())))
                .toList();
    }

    /**
     * Counts all folders in this cabinet, including nested ones.
     *
     * <p>Uses {@link #streamAllElements(List)} to traverse every folder
     * and returns the total number of elements.</p>
     *
     * @return the total number of folders (0 if none)
     */
    @Override
    public int count() {
        return (int) streamAllElements(folders).count();
    }

    /**
     * Returns an unmodifiable copy of all folders in this cabinet.
     *
     * <p>Prevents external modification of the internal folder list.</p>
     *
     * @return an unmodifiable list of folders
     */
    @Override
    public List<Folder> getFolders() {
        return List.copyOf(folders);
    }

    /**
     * Returns the name of the cabinet.
     *
     * @return the cabinet name
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Returns the size of the cabinet.
     *
     * @return the cabinet size (e.g. {@code "small"})
     */
    @Override
    public String getSize() {
        return size;
    }

    /**
     * Creates a flat stream of all {@link Folder} elements from the given list.
     *
     * <p>If a folder is an instance of {@link MultiFolder}, its inner folders are
     * also included in the result by recursive traversal.</p>
     *
     * <p>Returns an empty stream if the input list is {@code null} and skips any
     * {@code null} elements.</p>
     *
     * @param list the list of folders to process, possibly containing nested ones
     * @return a flat {@link Stream} of all non-null folders
     */
    private Stream<Folder> streamAllElements(List<Folder> list){
        if(list == null) return Stream.empty();
        return list.stream()
                .filter(Objects::nonNull)
                .flatMap(folder -> {
                    if(folder instanceof MultiFolder multiFolder){
                        return Stream.concat(
                                Stream.of(folder),
                                streamAllElements(multiFolder.getFolders())
                        );
                    }
                    else return Stream.of(folder);
                });
    }


    /**
     * Trims and lowercases the given string.
     *
     * <p>If the input is {@code null} or blank after trimming,
     * the method returns {@code null}.</p>
     *
     * @param input the string to normalize
     * @return a lowercase, trimmed string, or {@code null} if input is null or blank
     */
    private String normalize(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        return trimmed.isEmpty() ? null : trimmed.toLowerCase();
    }


    /**
     * Validates and normalizes the given folder size value.
     *
     * <p>Accepts only {@code "small"}, {@code "medium"}, or {@code "large"}
     * (case-insensitive). Any other value causes an {@link IllegalArgumentException}.</p>
     *
     * @param input the folder size to validate
     * @return the normalized size string if valid
     * @throws IllegalArgumentException if the input is {@code null}, blank, or invalid
     */
    private String assertAllowedSize(String input) {
        String normalized = normalize(input);
        if(normalized == null) throw new IllegalArgumentException("Invalid folder size.");
        return switch (input) {
            case "small", "medium", "large" -> normalized;
            default -> throw new IllegalArgumentException("Invalid folder size.");
        };
    }
}
