package password.encryption.decryption;

public class SysLibraryLoader implements LibraryLoader {
    public boolean load(String name, boolean verify) {
        boolean loaded;

        try {
            System.loadLibrary(name);
            loaded = true;
        } catch (Throwable e) {
            loaded = false;
        }

        return loaded;
    }
}
