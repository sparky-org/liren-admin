package com.sparky.lirenadmin.utils;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.function.Consumer;

public final class FileUtils {

	private static final String WEB_ROOT_PATH = "/var/www/html/";
//	private static final String WEB_ROOT_PATH = "./";

//	private static final String WEB_APP_PATH = "c:\\workspace\\file\\repo\\";

	private static final String WEB_APP_PATH = "lr-resource/";

	public static String saveFiles(MultipartFile file) throws IllegalStateException, IOException {
		String fileNameMD5 = DigestUtils.md5DigestAsHex(file.getOriginalFilename().getBytes());
		String filePath = fileNameMD5.substring(0, 2) + File.separator + fileNameMD5.substring(2, 4) + File.separator
				+ fileNameMD5;
		String fileName = WEB_APP_PATH + filePath;
		File dest = new File(WEB_ROOT_PATH + fileName);

		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}

		if (!dest.canWrite()){
			dest.setWritable(true);
		}

		if(dest.exists()) {
			dest.delete();
		}
		file.transferTo(dest);
		return fileName;
	}

	/**
	 * 使用方法参照 main 方法示例
	 * @param file
	 * @param copyDataMethod
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	public static String saveFiles(MultipartFile file, Consumer<File> copyDataMethod) throws IllegalStateException, IOException {
		String fileNameMD5 = DigestUtils.md5DigestAsHex(file.getOriginalFilename().getBytes());
		String filePath = fileNameMD5.substring(0, 2) + File.separator + fileNameMD5.substring(2, 4) + File.separator
				+ fileNameMD5;
		String fileName = WEB_APP_PATH + filePath;
		File dest = new File(WEB_ROOT_PATH + fileName);

		if (!dest.getParentFile().exists()) {
			dest.getParentFile().mkdirs();
		}

		if (!dest.canWrite()){
			dest.setWritable(true);
		}

		if(dest.exists()) {
			dest.delete();
		}
		copyDataMethod.accept(dest);
		return fileName;
	}

	private static void addLocationWaterMark(BufferedImage bufferedImage, String address, FileOutputStream fos){
		try {
			Graphics2D graphics2D = bufferedImage.createGraphics();
			drawLocationIcon(graphics2D, 10, bufferedImage.getHeight() - 25, 10, 20);
			drawString(graphics2D, 25, bufferedImage.getHeight() - 25 + 20, address);
			graphics2D.dispose();
			ImageIO.write(bufferedImage, "JPG", fos);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void drawLocationIcon(Graphics2D graphics2D, int x, int y, int width, int height){
		// 单独创建耗费资源，由控制端统一创建
		//		graphics2D.create();

		//画一个坐标
		graphics2D.setColor(Color.RED);
		graphics2D.fillOval(x,y,width,width);

		//画一条黑色竖线
		graphics2D.setColor(Color.black);
		graphics2D.drawLine(x + width / 2, y + width, x + width / 2, y + height);
		//由控制端关闭资源
		//		graphics2D.dispose();
	}

	private static  void drawString(Graphics2D graphics2D, int x, int y, String text){
		graphics2D.setColor(Color.black);
		graphics2D.setFont(new Font("宋体", Font.BOLD, 18));
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.8f));
		graphics2D.drawString(text, x, y);
	}

	public static void main(String[] args) {
		try{
			String imageUrl = "https://www.streamnet-chain.com/kaifile/lr-resource/07/97/0797f532def065d3efe0c61ac2949700";
			URL url = new URL(imageUrl);
			MultipartFile file = createImg(url.openStream());
			saveFiles(file, t -> {
				try {
					BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
					FileOutputStream fos = new FileOutputStream(t);
					String address = "当前定位地址是浙江省杭州市余杭区";
					addLocationWaterMark(bufferedImage, address, fos);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}catch (Exception e){
			System.out.println(e);
			e.printStackTrace();
		}
	}

	private static MultipartFile createImg(InputStream stream){
		try {
			MultipartFile multipartFile = new MockMultipartFile("test", "test", "multipart/form-data", stream);
			return multipartFile;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
