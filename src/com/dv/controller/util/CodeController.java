package com.dv.controller.util;

import java.awt.image.BufferedImage;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dv.util.ImageUtil;
/**
 * 
 * @classDesc ：
 *	生成验证码图片controller
 * @creater: 李梦婷
 * @creationDate:2017年3月10日 上午11:16:38
 */
@Controller
@RequestMapping("/code")
public class CodeController {
	
	@RequestMapping(value="getCode.action",method=RequestMethod.GET)
	public void getCode(HttpServletRequest req,HttpServletResponse resp)
	{
		Map<String,BufferedImage> map=ImageUtil.createImage();
		String code=map.keySet().iterator().next();
		req.getSession().setAttribute("validateCode", code);;
		BufferedImage image=map.get(code);
		try {
//			InputStream imageStream=ImageUtil.getInputStream(image);
			resp.setHeader("Pragma", "no-cache");         
	        resp.setHeader("Cache-Control", "no-cache");         
	        resp.setDateHeader("Expires", 0);         
	        resp.setContentType("image/jpeg");         
	        // 将图像输出到Servlet输出流中。         
	        ServletOutputStream sos = resp.getOutputStream();         
	        ImageIO.write(image, "jpeg", sos);         
	        sos.close();         

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
