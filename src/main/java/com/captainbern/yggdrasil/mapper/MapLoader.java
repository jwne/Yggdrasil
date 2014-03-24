package com.captainbern.yggdrasil.mapper;

import com.captainbern.yggdrasil.core.Yggdrasil;
import net.minecraft.util.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapLoader {

    protected final String MAPPINGS_DIR = "/mappings/%VERSION%/";

    private File versionFile;
    private Mapper CB_MAPPER;
    private Mapper NMS_MAPPER;

    public MapLoader(Yggdrasil yggdrasil) {

    }

    private File loadFile(Class clazz, String version, String fileName) throws IOException {
        final File tempFile = File.createTempFile(fileName, ".tmp");
        tempFile.deleteOnExit();

        InputStream stream = null;
        try {
            stream = clazz.getResourceAsStream(MAPPINGS_DIR.replace("%VERSION%", version) + fileName);
        } catch (NullPointerException e) {
            Yggdrasil.LOGGER.warning("Failed to find a valid mapping-file for: " + fileName);
            Yggdrasil.LOGGER.warning("Using fallback mapping file instead... (Cross your fingers and hope it works)");
        }

        stream = clazz.getResourceAsStream(MAPPINGS_DIR.replace("%VERSION%", "latest") + fileName);
        FileOutputStream outputStream = new FileOutputStream(tempFile);

        IOUtils.copy(stream, outputStream);
        return tempFile;
    }
}
