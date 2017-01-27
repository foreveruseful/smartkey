package link.anyauto.smartkey.apt;

import com.squareup.javapoet.TypeName;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Util {

    public static String capFirstLetter(String src) {
        return src.substring(0, 1).toUpperCase() + src.substring(1);
    }


    public static boolean textNotNull(String text) {
        return text != null && !(text.trim().equals(""));
    }

    public static boolean isWrapper(TypeName type) {
        return ClazzNames.WRAPPERS.contains(type);
    }

    public static String getPackageName(String fullClassName) {
        return fullClassName.substring(0, fullClassName.lastIndexOf('.'));
    }

    public static void logMsg(String msg) {
        try {
            FileOutputStream fos = new FileOutputStream("/home/discotek/message.txt", true);
            PrintWriter pw = new PrintWriter(fos);
            pw.append(msg).println();
            pw.flush();
            pw.close();
        } catch (Exception e1){}
    }

}
