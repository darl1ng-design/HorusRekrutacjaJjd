public class FolderImpl implements Folder{
    private String name;
    private String size;

    public FolderImpl(String name, String size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name.toLowerCase();
    }

    @Override
    public String getSize() {
        return size.toLowerCase();
    }
}
