package com.jvn.efst.config;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.jvn.efst.tools.Trail;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jline.utils.InputStreamReader;

import java.io.*;
import java.util.Map;

public class ClientConfig {
    private static final Gson GSON = (new GsonBuilder()).create();
    public static ConfigVal cfg = new ConfigVal();

    private static Logger LOGGER = LogManager.getLogger();

    static {

    }

    public static String ReadString(String FileName) {
        String str = "";

        File file = new File(FileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return "";
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                str = readFromInputStream(FileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return str;
    }

    public static void Load(){
        LOGGER.info("EpicFightSwordTrail:Loading Sword Trail Item");
        String cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicFightTrailItem1.json").toString();
        String json = ReadString(cfgpath);

        //LOGGER.info(json);
        if(json != ""){
            RenderConfig.TrailItem.clear();
            RenderConfig.TrailItem = GSON.fromJson(json, new TypeToken<Map<String,Trail>>(){}.getType());
        }
        else{
            WriteString(cfgpath,GSON.toJson(RenderConfig.TrailItem));
        }

        LOGGER.info("EpicFightSwordTrail:Loading Common Config");
        cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicFightSwordTrail1.json").toString();
        json = ReadString(cfgpath);
        //LOGGER.info(json);
        if(json != ""){
            try {
                cfg = GSON.fromJson(json, new  TypeToken<ConfigVal>(){}.getType());
            } catch (JsonSyntaxException e) {
                WriteString(cfgpath,GSON.toJson(cfg));
                throw new RuntimeException(e);
            }
        }
        else{
            WriteString(cfgpath,GSON.toJson(cfg));
        }
    }

    public static void SaveCommon(){
        String cfgpath = FMLPaths.CONFIGDIR.get().resolve("EpicAddonCommon1.json").toString();
        LOGGER.info("EpicAddon:Save Common Config");
        WriteString(cfgpath,GSON.toJson(cfg));
    }


    public static void WriteString(String FileName,String str){
        try(FileOutputStream fos = new FileOutputStream(FileName);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter bw = new BufferedWriter(osw);){
            bw.write(str);
            bw.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readFromInputStream(String s) throws IOException {
        InputStream inputStream = new FileInputStream(new File(s));
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}
