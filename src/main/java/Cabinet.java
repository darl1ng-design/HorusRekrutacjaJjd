public interface Cabinet {
    Optional<Folder> findFolderByName(String name);
    List<Folder> findFoldersBySize(String size); // Small, medium, large

    // zwraca liste obiektow
    int count();
}
