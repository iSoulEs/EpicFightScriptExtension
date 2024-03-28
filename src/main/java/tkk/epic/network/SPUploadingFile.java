package tkk.epic.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import tkk.epic.TkkEpic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.function.Supplier;

public class SPUploadingFile {
    public byte[] fileData;
    public String name;
    public SPUploadingFile(){

    }
    public SPUploadingFile(File file, String name){
        byte[] fileData = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fileData);
            fileInputStream.close();
        }catch (Exception e){
            fileData=null;
        }
        this.fileData=fileData;
        this.name=name;
    }


    public static SPUploadingFile fromBytes(PacketBuffer buf) {
        SPUploadingFile msg = new SPUploadingFile();
        msg.fileData=buf.readByteArray();
        msg.name=buf.readUtf();
        return msg;
    }

    public static void toBytes(SPUploadingFile msg, PacketBuffer buf) {
        buf.writeByteArray(msg.fileData);
        buf.writeUtf(msg.name);
    }


    public static void handle(SPUploadingFile msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(msg.name==null || msg.fileData==null){return;}
            try {

                File target=new File(TkkEpic.MOD_DIR.getCanonicalPath() + "/tkkCustomFile/"+msg.name);
                File fileParent=target.getParentFile();
                if (!fileParent.exists()) {
                    fileParent.mkdirs();
                }
                if (!target.exists()) {
                    target.createNewFile();
                }
                FileOutputStream fileOutputStream=new FileOutputStream(target);
                fileOutputStream.write(msg.fileData);
                fileOutputStream.close();
            }catch (Exception e){
                TkkEpic.getInstance().broadcast("tkk.epic.network.CPUploadingFile handle:"+e);
            }
            System.gc();
        });

        ctx.get().setPacketHandled(true);
    }
}
