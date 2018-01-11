package com.dv.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.dv.constants.SystemConst;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;



public class ImageUtil {
	public static Map<String, BufferedImage> createImage() {
		Map<String,BufferedImage> map=new HashMap<String,BufferedImage>();
		BufferedImage image = new BufferedImage(SystemConst.CODE_WIDTH,
				SystemConst.CODE_HEIGHT, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		Random r = new Random();
		g.setColor(new Color(216,216,216));
		g.fillRect(0, 0, SystemConst.CODE_WIDTH, SystemConst.CODE_HEIGHT);

		String str = "1234567890QWERTYUPLKJHGFDSAZXCVBNMqwertyuipkjhgfdsazxcvbnm";
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i <SystemConst.CODE_SIZE; i++) {
			/*g.setColor(new Color(r.nextInt(256), 
			r.nextInt(256), r.nextInt(256)));*/
			g.setColor(new Color(52,52,52));
			int h = (int) (16 +10 * r.nextDouble());
			g.setFont(new Font(null, Font.BOLD | Font.ITALIC, h));
			String ch = String.valueOf(str.charAt(r.nextInt(str.length())));
//			String ch = String.valueOf("1");
			g.drawString(ch, i * SystemConst.CODE_WIDTH /SystemConst.CODE_SIZE, h);
			sb.append(ch);
		}
		// 干扰线
		for (int i = 0; i <SystemConst.CODE_LINES; i++) {
			g.setColor(new Color(r.nextInt(256),
					r.nextInt(256), r.nextInt(256)));
			g.drawLine(r.nextInt(SystemConst.CODE_WIDTH), r
					.nextInt(SystemConst.CODE_HEIGHT), r
					.nextInt(SystemConst.CODE_WIDTH), r
					.nextInt(SystemConst.CODE_HEIGHT));
		}
		map.put(sb.toString(), image);
		return map;
	}
	
	public static InputStream getInputStream(BufferedImage image) throws Exception{
		//将图片给imageStream 赋值
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		JPEGImageEncoder jpegCoder=JPEGCodec.createJPEGEncoder(bos);
		jpegCoder.encode(image);
		//获取压缩处理后的图片信息
		byte[] bys=bos.toByteArray();
		return new ByteArrayInputStream(bys);
		
	}
}
