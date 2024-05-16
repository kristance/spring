package com.tjoeun.springWEB_FileUpload;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "fileUploadTest";
	}
	
	@RequestMapping("/fileUploadTest")
	public String fileUploadTest(HttpServletRequest request, Model model) {
		logger.info("HomeController -> fileUploadTest()");
		return "fileUploadTest";
	}
	
	@RequestMapping("/fileUploadResult")
//	form의 enctype 속성이 multipart/form-data일 경우 HttpSevletRequest 인터페이스 객체가 아니라
//	MultipartHttpServletRequest 인터페이스 객체로 받는다.
	public String fileUploadResult(MultipartHttpServletRequest request, Model model) {
		logger.info("HomeController -> fileUploadResult()");
		
//		업로드하는 파일이 저장될 폴더를 지정한다.
//		Spring에서 폴더와 폴더, 폴더와 파일을 구분하는 구분자("\")를 사용해야 할 때 "\" 1개만 입력하면
//		이스케이프 시퀀스(\n, \t, \b, \f, \", \')로 지정되지 않은 문자가 "\" 뒤에 위치하면 에러가 발생되므로
//		"\\"와 같이 입력하거나 File.seperator을 사용한다. 
//		logger.info("{}", File.separator); // \ 
//		String rootUploadDir = "C:\\Upload";
//		String rootUploadDir = "C:" + File.separator + "Upload";
//		logger.info("{}", rootUploadDir); // C:\Upload
//		File dir = new File(rootUploadDir + File.separator + "testFile"); // C:\Upload\testFile
		File dir = new File("C:\\Upload\\testFile"); // C:\Upload\testFile

		
		
//		업로드 폴더가 존재하지 않을 경우 업로드 폴더를 만든다.
//		exists() 메소드는 파일을 업로드할 폴더가 존재하면 true, 존재하지 않으면 false를 리턴한다.
//		logger.info("{}", dir.exists());
//		File 클래스 객체 dir에 지정된 폴더가 존재하지 않을 경우 mkdirs() 메소드로 폴더를 만든다.
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		
//		업로드되는 파일 정보 수집(2개: file1, file2)
		Iterator<String> iterator = request.getFileNames();
		ArrayList<String> list = new ArrayList<String>();
		
//		업로드되는 파일의 갯수만큼 반복하며 업로드 한다.
		while (iterator.hasNext()) {
			String uploadFilename = iterator.next();
			logger.info("uploadFilename : {} ", uploadFilename ); // file1, file2
			MultipartFile multipartFile = request.getFile(uploadFilename);
			String originalFilename = multipartFile.getOriginalFilename();
			logger.info("originalFilename : {} ", originalFilename ); // 실제 업로드하는 파일 이름
			
			if (originalFilename != null && originalFilename.trim().length() != 0) {
				// MultipartFile 인터페이스 객체에서 transferTo() 메소드로 File 객체를 만들어 업로드 한다.
				try {
					multipartFile.transferTo(new File(dir + File.separator + originalFilename +
							System.currentTimeMillis()));
					list.add("원본 파일명 -> " + originalFilename);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("파일 업로드 중 문제 발생");
					list.add("문제 발생한 파일은  -> " + originalFilename);
				} 
			}
			
			
			
			
		}
		model.addAttribute("list", list);
		return "fileUploadResult";
	}
	
	
	
	
	
}
