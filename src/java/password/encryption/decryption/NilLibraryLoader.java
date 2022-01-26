package password.encryption.decryption;
public class NilLibraryLoader implements LibraryLoader {
    public boolean load(String name, boolean verify) {
        return false;
    }
}
