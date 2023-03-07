package com.example.stocksystem.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UsualUtil {

    //将传输来的base64字符串解码，并存档到文件中
    public static void saveFile(String base64Str, String path) throws IOException {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(base64Str.getBytes(StandardCharsets.UTF_8));
        FileOutputStream fos = new FileOutputStream(path);
        fos.write(bytes, 0, bytes.length);
        fos.close();
    }

}
