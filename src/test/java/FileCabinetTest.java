/**
 * Unit test suite for the {@link FileCabinet} class.
 * <p>
 * This class verifies the correctness of folder retrieval and counting logic within
 * {@code FileCabinet}. It uses {@link org.junit.jupiter.api.Test JUnit} to ensure that
 * the implementation behaves consistently under various conditions.
 * </p>
 *
 * <p><strong>Test coverage includes:</strong></p>
 * <ul>
 *   <li>{@link FileCabinet#findFolderByName(String)} – verifies correct folder retrieval
 *       for valid names and proper handling of {@code null} input.</li>
 *   <li>{@link FileCabinet#findFoldersBySize(String)} – ensures that folders are filtered
 *       correctly by size and that invalid arguments throw {@link IllegalArgumentException}.</li>
 *   <li>{@link FileCabinet#count()} – checks accurate folder count for {@code null},
 *       empty, and populated folder lists.</li>
 * </ul>
 *
 * <p>
 * Each test initializes a {@link FileCabinet} instance containing several {@link FolderImpl}
 * objects with different sizes and names. The {@link #setup()} method runs before each test to
 * guarantee a clean, consistent state.
 * </p>
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DisplayName("Test container for FileCabinet class")
public class FileCabinetTest {

    private FileCabinet fileCabinet;

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

    @Test
    void testFindFoldersByNameWhenAllowedArguments(){
        String inputFolderName = "Test1";
        Optional<Folder> temp = fileCabinet.findFolderByName(inputFolderName);
        Assertions.assertTrue(temp.isPresent());
        Assertions.assertEquals("test1", temp.get().getName());
    }

    @Test
    void testFindFoldersByNameWhenNotAllowedArguments(){
        String inputName = null;
        Optional<Folder> temp = fileCabinet.findFolderByName(inputName);
        Assertions.assertTrue(temp.isEmpty());
    }

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

    @Test
    void testCountWhenFoldersIsNull(){
        fileCabinet.setFolders(null);
        Assertions.assertEquals(0, fileCabinet.count());
    }

    @Test
    void testCountWhenFoldersIsEmpty(){
        fileCabinet.setFolders(new ArrayList<>());
        Assertions.assertEquals(0, fileCabinet.count());
    }

    @Test
    void testCountWhenFoldersIsNotEmpty(){
        Assertions.assertEquals(fileCabinet.getFolders().size(), fileCabinet.count());
    }
}
