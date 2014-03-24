package com.captainbern.yggdrasil.mapper;

import com.captainbern.yggdrasil.mapper.csv.CSVReader;
import com.google.common.base.Preconditions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Mapper {

    private final Map<String, String> fieldMap = new HashMap<String, String>();
    private final Map<String, String> methodMap = new HashMap<String, String>();

    public Mapper(File fields, File methods) throws IOException {
        if(fields != null && fields.exists()) {
            fillMap(fields, fieldMap, true);
        }

        if(methods != null && methods.exists()) {
            fillMap(methods, methodMap, true);
        }
    }

    protected void fillMap(File csv, Map<String, String> map, boolean close) throws IOException {
        FileReader reader = null;
        CSVReader csvReader = null;

        try {
            reader = new FileReader(csv);
            csvReader = new CSVReader(reader);

            String[] line;
            while((line = csvReader.readNext()) != null) {
                if(line.length == 0)
                    continue;

                Preconditions.checkArgument(line.length >= 2, "Error while parsing line: " + line);
                map.put(line[0], line[1]);
            }
        } finally {
            if(close) {
                if(reader != null) {
                    reader.close();
                }
                if(csvReader != null) {
                    csvReader.close();
                }
            }
        }
    }

    public boolean hasField(String name) {
        return this.fieldMap.containsKey(name);
    }

    public boolean hasMethod(String name) {
        return this.methodMap.containsKey(name);
    }

    public String getField(String name) {
        if(!hasField(name))
            return name;

        return this.fieldMap.get(name);
    }

    public String getMethod(String name) {
        if(!hasMethod(name))
            return name;

        return this.methodMap.get(name);
    }
}
