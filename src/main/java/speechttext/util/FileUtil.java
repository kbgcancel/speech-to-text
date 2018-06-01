package speechttext.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	public File multipartFileToFileConversion(MultipartFile tempFile) throws IOException {
		
		File file = new File(tempFile.getOriginalFilename());
		file.createNewFile();
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(tempFile.getBytes());
		outStream.close();
		
		return file;
	}
	
}
