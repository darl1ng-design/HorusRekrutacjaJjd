import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;



@DisplayName("Test container for FileCabinet class")
public class FileCabinetTest {

    private FileCabinet fileCabinet;


    @Test
    void testFinderFolderByName(){
        Assertions.assertNull(fileCabinet.findFolderByName("test1"));
    }
}
