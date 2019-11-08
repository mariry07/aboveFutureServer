package com.tianbo.smartcity.common.utils;

import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * 二维码工具类
 */
@Slf4j
public class QrCodeUtil {

    /**
     * 生成二维码并使用Base64编码
     * @param contents 内容
     */
    public static String createQrCode(String contents) {
        String binary = null;
        //HashMap hints = new HashMap(16);
        HashMap<EncodeHintType, Object> hints = new HashMap<>(16);
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 2);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(
                    contents, BarcodeFormat.QR_CODE, 260, 260, hints);
            // 1、读取文件转换为字节数组
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BufferedImage image = toBufferedImage(bitMatrix);
            //转换成png格式的IO流
            ImageIO.write(image, "png", out);
            byte[] bytes = out.toByteArray();
            // 2、将字节数组转为二进制
            BASE64Encoder encoder = new BASE64Encoder();
            binary = encoder.encodeBuffer(bytes).trim();
        } catch (WriterException e) {
            log.error(e.toString());
        } catch (IOException e) {
            log.error(e.toString());
        }
        return binary;
    }

    /**
     * image流数据处理
     *
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }

    /**
     * 解析二维码
     * @param file
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        BinaryBitmap bb = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        HashMap map = new HashMap(16);
        map.put(DecodeHintType.CHARACTER_SET, "utf-8");
        Result result = new MultiFormatReader().decode(bb,map);
        return result.getText();
    }




}
