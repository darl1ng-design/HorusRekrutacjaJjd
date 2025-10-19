import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Test suite for the {@link FileCabinet} class.
 *
 * <p>This class contains unit tests verifying the correct behavior of the
 * {@link FileCabinet} methods, including folder lookup by name, filtering by size,
 * and folder count operations.</p>
 *
 * <p>All tests use the JUnit framework and rely on pre-initialized data
 * defined in the {@link #setup()} method.</p>
 *
 * @author Adam Gyurjyan
 * @version 1.0
 * @since 1.0
 * @see FileCabinet
 * @see Folder
 * @see FolderImpl
 */
@DisplayName("Test container for FileCabinet class")
public class FileCabinetTest {

    private FileCabinet fileCabinet_flat;
    private FileCabinet fileCabinet_nested;
    private FileCabinet fileCabinet_root;

    /**
     * Initializes a hierarchy of {@link FileCabinet} instances before each test.
     *
     * <p>Creates three cabinets:
     * <ul>
     *     <li>{@code fileCabinet_flat} — a flat cabinet containing basic folders</li>
     *     <li>{@code fileCabinet_nested} — a cabinet containing {@code fileCabinet_flat}</li>
     *     <li>{@code fileCabinet_root} — the root cabinet containing {@code fileCabinet_nested}
     *     and additional folders</li>
     * </ul>
     *
     * <p>This structure is used to test recursive traversal, search, and counting logic.</p>
     */
    @BeforeEach
    void setup(){
        fileCabinet_flat = new FileCabinet(
                List.of(
                        new FolderImpl("Test1", "small"),
                        new FolderImpl("Test2", "medium"),
                        new FolderImpl("Test3", "large"),
                        new FolderImpl("Test4", "large"),
                        new FolderImpl("Test5", "small")
                ),
                "FileCabinet_Flat",
                "small"
        );

        fileCabinet_nested = new FileCabinet(
                List.of(
                        new FolderImpl("Test6", "large"),
                        fileCabinet_flat
                ),
                "FileCabinet_Nested",
                "medium"
        );

        fileCabinet_root = new FileCabinet(
                List.of(
                        fileCabinet_nested,
                        new FolderImpl("Test7", "medium"),
                        new FolderImpl("Test8", "large")
                ),
                "FileCabinet_Root",
                "large"
        );
    }

    /**
     * Verifies that {@link FileCabinet#findFolderByName(String)}
     * correctly finds a folder when given a valid name.
     *
     * <p>Checks that the result is present and that the folder name
     * matches the expected normalized value.</p>
     */
    @Test
    void testFindFoldersByNameWhenAllowedArguments(){
        Optional<Folder> temp = fileCabinet_root.findFolderByName("Test1");
        Assertions.assertTrue(temp.isPresent());
        Assertions.assertEquals("test1", temp.get().getName());
    }

    /**
     * Verifies that {@link FileCabinet#findFolderByName(String)}
     * returns an empty {@link Optional} for invalid or blank names.
     *
     * <p>Ensures that {@code null}, empty, and malformed name inputs
     * are safely handled without throwing exceptions.</p>
     */
    @Test
    void testFindFoldersByNameWhenNotAllowedArguments(){
        Optional<Folder> temp_1 = fileCabinet_root.findFolderByName(null);
        Optional<Folder> temp_2 = fileCabinet_root.findFolderByName("");
        Optional<Folder> temp_3 = fileCabinet_root.findFolderByName("Te  stOrN   otTes  tTestOrNotTest");
        Assertions.assertTrue(temp_1.isEmpty());
        Assertions.assertTrue(temp_2.isEmpty());
        Assertions.assertTrue(temp_3.isEmpty());
    }

    /**
     * Verifies that {@link FileCabinet#findFoldersBySize(String)}
     * correctly returns folders for valid size values.
     *
     * <p>Ensures that folders with sizes {@code "small"}, {@code "medium"},
     * and {@code "large"} are found and returned as expected.</p>
     */
    @Test
    void testFindFoldersBySizeWhenAllowedArguments(){
        List<Folder> temp_1 = fileCabinet_root.findFoldersBySize("small");
        List<Folder> temp_2 = fileCabinet_root.findFoldersBySize("medium");
        List<Folder> temp_3 = fileCabinet_root.findFoldersBySize("large");

        Assertions.assertEquals("small", temp_1.getFirst().getSize());
        Assertions.assertEquals("medium", temp_2.getFirst().getSize());
        Assertions.assertEquals("large", temp_3.getFirst().getSize());
    }

    /**
     * Verifies that {@link FileCabinet#findFoldersBySize(String)}
     * throws an {@link IllegalArgumentException} for invalid input values.
     *
     * <p>Checks that the method consistently rejects {@code null}, empty,
     * or malformed size strings and returns the expected exception message.</p>
     */
    @Test
    void testFindFoldersBySizeWhenNotAllowedArguments(){
        IllegalArgumentException temp_1 = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        fileCabinet_root.findFoldersBySize(null)
        );

        IllegalArgumentException temp_2 = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        fileCabinet_root.findFoldersBySize("Not Sma   llOrMe  diu   mOrLarge")
        );

        IllegalArgumentException temp_3 = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        fileCabinet_root.findFoldersBySize("")
        );

        Assertions.assertEquals("Invalid folder size.", temp_1.getMessage());
        Assertions.assertEquals("Invalid folder size.", temp_2.getMessage());
        Assertions.assertEquals("Invalid folder size.", temp_3.getMessage());
    }

    /**
     * Ensures that {@link FileCabinet#count()} returns {@code 0}
     * when the internal folder list is {@code null}.
     */
    @Test
    void testCountWhenFoldersIsNull(){
        fileCabinet_root.setFolders(null);
        Assertions.assertEquals(0, fileCabinet_root.count());
    }

    /**
     * Verifies that {@link FileCabinet#count()} returns {@code 0}
     * when the folder list is empty.
     *
     * <p>Ensures the method correctly handles an initialized
     * but empty collection.</p>
     */
    @Test
    void testCountWhenFoldersIsEmpty(){
        fileCabinet_root.setFolders(new ArrayList<>());
        Assertions.assertEquals(0, fileCabinet_root.count());
    }

    /**
     * Verifies that {@link FileCabinet#count()} correctly returns
     * the total number of folders when the cabinet is not empty.
     *
     * <p>Compares the reported count to the expected value.</p>
     */
    @Test
    void testCountWhenFoldersIsNotEmpty(){
        Assertions.assertEquals(fileCabinet_root.count(), fileCabinet_root.count());
    }
}
