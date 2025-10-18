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
 * @author Adam
 * @version 1.0
 * @since 1.0
 * @see FileCabinet
 * @see Folder
 * @see FolderImpl
 */
@DisplayName("Test container for FileCabinet class")
public class FileCabinetTest {

    private FileCabinet fileCabinet;

    /**
     * Initializes a {@link FileCabinet} instance with a predefined list of folders
     * before each test case.
     *
     * <p>The test data includes five {@link FolderImpl} objects with various
     * names and sizes.</p>
     */
    @BeforeEach
    void setup(){
        fileCabinet = new FileCabinet(
                List.of(
                        new FolderImpl("test1", "small"),
                        new FolderImpl("Test2", "medium"),
                        new FolderImpl("Test3", "large"),
                        new FolderImpl("Test4", "large"),
                        new FolderImpl("Test5", "small")
                ),
                "FileCabinet",
                "small"
        );
    }

    /**
     * Verifies that {@link FileCabinet#findFolderByName(String)} correctly finds
     * a folder when given a valid name.
     *
     * <p>Checks that the result is present and that the name matches the expected
     * normalized value.</p>
     */
    @Test
    void testFindFoldersByNameWhenAllowedArguments(){
        String inputFolderName = "Test1";
        Optional<Folder> temp = fileCabinet.findFolderByName(inputFolderName);
        Assertions.assertTrue(temp.isPresent());
        Assertions.assertEquals("test1", temp.get().getName());
    }

    /**
     * Verifies that {@link FileCabinet#findFolderByName(String)} returns an empty
     * {@link Optional} when provided with a null name.
     */
    @Test
    void testFindFoldersByNameWhenNotAllowedArguments(){
        String inputName = null;
        Optional<Folder> temp = fileCabinet.findFolderByName(inputName);
        Assertions.assertTrue(temp.isEmpty());
    }

    /**
     * Tests {@link FileCabinet#findFoldersBySize(String)} with valid size arguments.
     *
     * <p>Ensures that folders of size {@code "small"}, {@code "medium"}, and {@code "large"}
     * are correctly filtered and returned.</p>
     */
    @Test
    void testFindFoldersBySizeWhenAllowedArguments(){
        String inputSize_1 = "small";
        String inputSize_2 = "medium";
        String inputSize_3 = "large";

        List<Folder> temp_1 = fileCabinet.findFoldersBySize(inputSize_1);
        List<Folder> temp_2 = fileCabinet.findFoldersBySize(inputSize_2);
        List<Folder> temp_3 = fileCabinet.findFoldersBySize(inputSize_3);

        Assertions.assertEquals("small", temp_1.getFirst().getSize());
        Assertions.assertEquals("medium", temp_2.getFirst().getSize());
        Assertions.assertEquals("large", temp_3.getFirst().getSize());
    }

    /**
     * Verifies that {@link FileCabinet#findFoldersBySize(String)} throws an
     * {@link IllegalArgumentException} for invalid or null size values.
     *
     * <p>Confirms that the exception messages are consistent and descriptive.</p>
     */
    @Test
    void testFindFoldersBySizeWhenNotAllowedArguments(){
        String inputSize_1 = null;
        String inputSize_2 = "NotSmallOrMediumOrLarge";

        IllegalArgumentException temp_1 = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        fileCabinet.findFoldersBySize(inputSize_1)
        );

        IllegalArgumentException temp_2 = Assertions.assertThrows(
                IllegalArgumentException.class, () ->
                        fileCabinet.findFoldersBySize(inputSize_2)
        );

        Assertions.assertEquals("Invalid folder size.", temp_1.getMessage());
        Assertions.assertEquals("Invalid folder size.", temp_2.getMessage());
    }

    /**
     * Ensures that {@link FileCabinet#count()} returns {@code 0}
     * when the internal folder list is {@code null}.
     */
    @Test
    void testCountWhenFoldersIsNull(){
        fileCabinet.setFolders(null);
        Assertions.assertEquals(0, fileCabinet.count());
    }

    /**
     * Ensures that {@link FileCabinet#count()} returns {@code 0}
     * when the internal folder list is empty.
     */
    @Test
    void testCountWhenFoldersIsEmpty(){
        fileCabinet.setFolders(new ArrayList<>());
        Assertions.assertEquals(0, fileCabinet.count());
    }

    /**
     * Ensures that {@link FileCabinet#count()} returns the correct number
     * of folders when the list is populated.
     */
    @Test
    void testCountWhenFoldersIsNotEmpty(){
        Assertions.assertEquals(fileCabinet.getFolders().size(), fileCabinet.count());
    }
}
