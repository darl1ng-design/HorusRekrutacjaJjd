import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

public class FileCabinet implements Cabinet, MultiFolder {
    private List<Folder> folders;
    private String name;
    private String size;

    /**
     * Creates a new instance of the file cabinet with the given folders, name, and size.
     *
     * @param folders list of folders to be added to the cabinet
     * @param name    the cabinet name
     * @param size    the cabinet size (e.g. {@code "small"}, {@code "medium"}, {@code "large"})
     * @implNote The provided list is copied, so later modifications to the original
     * collection do not affect this instance.
     */
    public FileCabinet(List<Folder> folders, String name, String size) {
        this.folders = folders == null ? List.of() : new ArrayList<>();
        this.name = name;
        this.size = size;
    }

    /**
     * Finds a folder by its name.
     *
     * @param name the name of the folder to find
     * @return an {@link Optional} containing the folder if found, or empty if not found
     * @implSpec The method normalizes the provided name using {@link utils.Util#normalize(String)}
     * and compares it to folder names within the internal collection.
     * @apiNote The result may be case-insensitive if {@code normalize} removes capitalization differences.
     */
    @Override
    public Optional<Folder> findFolderByName(String name) {
        String normalizedName = Util.normalize(name);
        if (normalizedName == null || folders == null) return Optional.empty();

        return folders.stream()
                .filter(folder -> folder.getName().equals(normalizedName))
                .findFirst();
    }

    /**
     * Returns a list of folders with the specified size.
     *
     * @param size the size to filter by (e.g. {@code "small"}, {@code "medium"}, {@code "large"})
     * @return a list of folders matching the given size; may be empty but never {@code null}
     * @throws IllegalArgumentException if the provided size is not allowed
     * @implNote The method validates the size using {@link utils.Util#assertAllowedSize(String)}.
     */
    @Override
    public List<Folder> findFoldersBySize(String size) {
        String normalizedSize = Util.assertAllowedSize(Util.normalize(size));
        return folders.stream()
                .filter(folders -> folders.getSize().equals(normalizedSize))
                .toList();
    }

    /**
     * Returns the number of folders stored in this cabinet.
     *
     * @return the folder count, or {@code 0} if the list is empty or uninitialized
     */
    @Override
    public int count() {
        return folders == null ? 0 : folders.size();
    }

    /**
     * Returns all folders contained in this cabinet.
     *
     * @return a list of folders (never {@code null})
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
     * Normalizes a string by trimming whitespace and converting it to lowercase.
     *
     * <p>If the input is {@code null} or contains only whitespace,
     * the method returns {@code null}.</p>
     *
     * @param input the input string to normalize
     * @return the normalized string in lowercase, or {@code null} if input is {@code null} or blank
     * @implNote This method is commonly used to make string comparisons case-insensitive.
     */
    public String normalize(String input) {
        if (input == null) return null;
        String trimmed = input.trim();
        return trimmed.isEmpty() ? null : trimmed.toLowerCase();
    }


    /**
     * Validates whether the provided folder size value is allowed.
     *
     * <p>Permitted values are {@code "small"}, {@code "medium"}, and {@code "large"}.
     * If the value is {@code null}, the method throws an {@link IllegalArgumentException}.</p>
     *
     * @param input the folder size value to validate
     * @return the normalized input value if it is valid
     * @throws IllegalArgumentException if the input is {@code null} or not one of the allowed values
     * @implSpec This method uses a {@code switch} expression for compactness and strict validation.
     */
    public String assertAllowedSize(String input) {
        String normalized = normalize(input);
        if(normalized == null) throw new IllegalArgumentException("Invalid folder size.");
        return switch (input) {
            case "small", "medium", "large" -> normalized;
            default -> throw new IllegalArgumentException("Invalid folder size.");
        };
    }
}
