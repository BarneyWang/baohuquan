import com.baohuquan.utils.HttpClientUtil;
import com.google.common.collect.Maps;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.util.HashMap;

/**
 * Created by wangdi5 on 2016/6/22.
 */
public class TestUploadTest {


    public static void main(String[] args) {
       File file = new File("D://1.png");
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            FileInputStream fin = new FileInputStream(file);
            byte[] b=new byte[fin.available()];
            long fileSize = file.length();
            FileInputStream fi = new FileInputStream(file);
            byte[] buffer = new byte[(int) fileSize];
            int offset = 0;
            int numRead = 0;
            while (offset < buffer.length
                    && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
                offset += numRead;
            }
            // 确保所有数据均被读取
            if (offset != buffer.length) {
                throw new IOException("Could not completely read file "
                        + file.getName());
            }

            String image=encoder.encode(buffer);
            HashMap map = Maps.newHashMap();
            map.put("name","1.png");
            map.put("image",image);

           String str= HttpClientUtil.post("http://testapi.baohuquan.com/upload/image?token=vTngpt3g1n2ukgeWY_1kwW5",map);
            System.out.println(str);
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }


    }
}
