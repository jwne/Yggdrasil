package com.captainbern.yggdrasil.mapper;

import net.minecraft.util.org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MapManager {

    protected Mapper nms_mapper;
    protected Mapper cb_mapper;

    public MapManager(Class clazz, String packageVersion) throws IOException {
        File cbFields = createVirtualFile(clazz.getResourceAsStream("/mappings/" + packageVersion + "/cb/fields.csv"), "cb_fields");
        File cbMethods = createVirtualFile(clazz.getResourceAsStream("/mappings/" + packageVersion + "/cb/methods.csv"), "cb_methods");

        File nmsFields = createVirtualFile(clazz.getResourceAsStream("/mappings/" + packageVersion + "/nms/fields.csv"), "nms_fields");
        File nmsMethods = createVirtualFile(clazz.getResourceAsStream("/mappings/" + packageVersion + "/nms/methods.csv"), "nms_methods");

        cb_mapper = new Mapper(cbFields, cbMethods);
        nms_mapper = new Mapper(nmsFields, nmsMethods);
    }

    protected File createVirtualFile(InputStream in, String fileName) throws IOException {
        final File tempFile = File.createTempFile(fileName, ".temp.csv");
        tempFile.deleteOnExit();

        FileOutputStream out = new FileOutputStream(tempFile);
        IOUtils.copy(in, out);

        return tempFile;
    }
}
