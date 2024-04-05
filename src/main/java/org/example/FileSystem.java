package org.example;

import java.util.List;

public interface FileSystem {
    VFile fopen(String fileName);

    List<Byte> fread(VFile vFile);

    void fwrite(VFile vFile, List<Byte> data);

    void fclose(VFile vFile);

}
