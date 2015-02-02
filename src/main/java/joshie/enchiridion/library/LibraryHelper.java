package joshie.enchiridion.library;

import net.minecraft.world.WorldServer;

public class LibraryHelper {
    public static ModBooks modBooks; //The Client instance of ModBooks
    public static LibraryStorage storage = null; //The Clients instance of LibraryStorage
    public static LibrarySaveData data = null; //The Servers Save Data

    //Setup the data file
    public static void init(WorldServer world) {
        data = (LibrarySaveData) world.loadItemData(LibrarySaveData.class, LibrarySaveData.DATA_NAME);
        if (data == null) {
            data = new LibrarySaveData(LibrarySaveData.DATA_NAME);
            world.setItemData(LibrarySaveData.DATA_NAME, data);
        }
        
        //Load in the books
        data.reloadBooks();
    }
}
