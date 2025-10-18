/**
 * Test suite for the {@link FileCabinet} class.
 * <p>
 * This class validates the behavior of the {@code FileCabinet} API by testing its
 * folder lookup and filtering methods under various input conditions.
 * <br><br>
 * Structure:
 * <ul>
 *     <li>Tests folder lookup by name using {@code findFolderByName(String)}</li>
 *     <li>Tests folder filtering by size using {@code findFoldersBySize(String)}</li>
 *     <li>Tests exception handling for invalid folder size inputs</li>
 * </ul>
 * Each test method follows the <b>Given–When–Then</b> structure to improve readability
 * and to explicitly define test intent.
 * </p>
 */

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        Assertions.assertEquals("Test1", temp.get().getName());
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


}
