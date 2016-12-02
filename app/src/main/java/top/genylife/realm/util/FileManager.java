package top.genylife.realm.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.inject.Inject;

import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/**
 * Created by wanqi on 2016/12/1.
 *
 * @since 1.0.0
 */

public class FileManager {

    @Inject
    public FileManager() {

    }

    public void writeFile(@NonNull File dir, @NonNull String fileName, String content) throws Exception {
        if(TextUtils.isEmpty(fileName))
            throw new Exception("文件名不能为空或空字符");
        File file = new File(dir, fileName);
        writeFile(file, content);
    }

    public void writeFile(@NonNull String filePath, String content) throws Exception {
        if(TextUtils.isEmpty(filePath))
            throw new Exception("文件名不能为空或空字符");
        File file = new File(filePath);
        writeFile(file, content);

    }

    public void writeFile(@NonNull File file, String content) {
        try {
            Sink sink = Okio.appendingSink(file);
            BufferedSink buffer = Okio.buffer(sink);
            buffer.writeString(content, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    public String readFile(@NonNull File file) {
        try {
            Source source = Okio.source(file);
            BufferedSource buffer = Okio.buffer(source);
            String string = buffer.readString(Charset.defaultCharset());
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public String readFile(@NonNull String filePath) throws Exception {
        if(TextUtils.isEmpty(filePath))
            throw new Exception("文件名不能为空或空字符");
        File file = new File(filePath);
        return readFile(file);
    }

    @Nullable
    public String readFile(@NonNull File dir, @NonNull String fileName) throws Exception {
        if(TextUtils.isEmpty(fileName))
            throw new Exception("文件名不能为空或空字符");
        File file = new File(dir, fileName);
        return readFile(file);
    }

}
