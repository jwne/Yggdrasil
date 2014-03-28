package com.captainbern.yggdrasil.mapper;

import com.captainbern.yggdrasil.core.Yggdrasil;
import com.captainbern.yggdrasil.utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapManager {

    protected final String MAPPINGS_DIR = "/mappings/%VERSION%/";

    private File versionFile;
    private Mapper CB_MAPPER;
    private Mapper NMS_MAPPER;

    public MapManager(Yggdrasil yggdrasil) {
        try {
            final File versions = File.createTempFile("versions.json", ".tmp");
            versions.deleteOnExit();

            InputStream inputStream = yggdrasil.getClass().getResourceAsStream("/mappings/versions.json");
            FileOutputStream outputStream = new FileOutputStream(versions);

            IOUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Mapper getNMSMapper() {
        return this.NMS_MAPPER;
    }

    public Mapper getCBMapper() {
        return this.CB_MAPPER;
    }
}
