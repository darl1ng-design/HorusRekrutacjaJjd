import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

@DisplayName("Test container for FileCabinet class")
public class FileCabinetTest {

    @Test
    void testFindFolderByNameReturnsExpected(){
        String inputFolderName = "test1";
        FileCabinet fileCabinet = new FileCabinet(
                List.of(
                        new FolderImpl("Test1", "small"),
                        new FolderImpl("Test2", "medium")
                ),
                "FileCabinet",
                "small"
        );


        Optional<Folder> temp = fileCabinet.findFolderByName(inputFolderName);


        Assertions.assertTrue(temp.isPresent(), "Ok");
        Assertions.assertEquals("test1", temp.get().getName());
    }
}
