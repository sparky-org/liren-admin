package com.sparky.lirenadmin.utils;

import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public final class FileUtils {

	private static final String WEB_ROOT_PATH = "/var/www/html/";
	
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
}
