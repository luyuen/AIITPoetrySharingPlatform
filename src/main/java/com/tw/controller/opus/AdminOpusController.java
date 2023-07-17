package com.tw.controller.opus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tw.model.member.MemberBean;
import com.tw.model.member.MemberService;
import com.tw.model.opus.OpusBean;
import com.tw.model.opus.OpusService;

@Controller
@RequestMapping("/admin")
public class AdminOpusController {
	@Autowired
	OpusService opusService;

	@Autowired
	MemberService memberService;

	@Autowired
	private HttpServletRequest request;

	@GetMapping("/opus/opus_home")
//	@ResponseBody
	public String queryAll(Model model) {
		model.addAttribute("opuslist", opusService.queryOpus());
		System.out.println("hello");
		return "admin/opus/opus_home.html";
	}

	@GetMapping("/insert_opus")
	public String insertStart() {
		return "admin/opus/insert_opus.html";
	}

	@PostMapping("/insert_opus/controller")
	public String insertOpus(@RequestParam("opus_name") String opus_name,
//			@RequestParam("pptfile") MultipartFile pptfile,
			@RequestParam("opus_member") String opus_member, @RequestParam("opus_imagefile") MultipartFile imagefile,
			@RequestParam("opus_audiofile") MultipartFile audiofile, @RequestParam("opus_content") String opus_content,
//			@ModelAttribute("opuses") OpusBean opuses, 
//			@RequestParam("opus_imagefile") MultipartFile imagefile1,
//			@RequestParam("opus_audiofile") MultipartFile audiofile, 
			Model model) throws IllegalStateException, IOException {

		boolean memberBoolean = memberService.findbyMember(opus_member);
		if (memberBoolean == false) {
			return "redirect:/login.html";
		}
//		String fileName = (String) String.format("%s\\%s.%s", "forum", Instant.now().toEpochMilli(),
//				image.getContentType().split("/")[1]);
////		if (file == null) {
////			file == "noimage_s.jpg";
////		}
//
//
//		System.out.println("originalFileName:" + fileName);
//		System.out.println(fileName);
//		String pathname = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\images\\" + fileName;
////		 File saveFile = new File(pathname);
////			file.transferTo(saveFile);
////			System.out.println(saveFile);
//
////		File saveTempDirFile = new File(saveTempFile);
////		saveTempDirFile.mkdirs();
//
////		String saveFilePath = pathname + fileName;
//		File saveFile1 = new File(pathname);
//		if (!saveFile1.getParentFile().exists()) {
//			saveFile1.getParentFile().mkdirs();
//		}
//		String pptFileNameString = pptfile.getOriginalFilename();
//		String savePptFileTempDir = request.getSession().getServletContext().getRealPath("/") + "pptFileTempDir\\";
//		File pptFile = new File(savePptFileTempDir);
//		pptFile.mkdirs();
//		
//		String savePptFilePath = savePptFileTempDir + pptFileNameString;
//		File savePptFile = new File(savePptFilePath);
//		imagefile.transferTo(savePptFile);

		String imageFileNameString = imagefile.getOriginalFilename();
		System.out.println(imageFileNameString);
		String saveImageFileTempDir = request.getSession().getServletContext().getRealPath("/") + "imageFileTempDir\\";
		File imageFile = new File(saveImageFileTempDir);
		imageFile.mkdirs();

		String saveImageFilePath = saveImageFileTempDir + imageFileNameString;
		File saveImageFile = new File(saveImageFilePath);
		imagefile.transferTo(saveImageFile);

		String audioFileNameString = audiofile.getOriginalFilename();
		String saveAudioFileTempDir = request.getSession().getServletContext().getRealPath("/") + "audioFileTempDir\\";
		File audioFile = new File(saveAudioFileTempDir);
		audioFile.mkdirs();

		String saveAudioFilePath = saveAudioFileTempDir + audioFileNameString;
		File saveAudioFile = new File(saveAudioFilePath);
		audiofile.transferTo(saveAudioFile);

//		System.out.println("save:" + savePptFile);
		System.out.println("save:" + saveImageFile);
		System.out.println("save:" + saveAudioFile);

		try (FileInputStream image = new FileInputStream(saveImageFilePath)) {
			try (FileInputStream audio = new FileInputStream(saveAudioFilePath)) {
//				try (FileInputStream ppt = new FileInputStream(savePptFilePath)) {

//					byte[] pptByte = new byte[ppt.available()];
				byte[] imageByte = new byte[image.available()];
				byte[] audioByte = new byte[audio.available()];

//				ppt.read();
				image.read();
				audio.read();

				if (imageByte != null && audioByte != null) {

					OpusBean opus = new OpusBean();
					opus.setOpus_name(opus_name);
					opus.setOpus_member(opus_member);

					opus.setOpus_imageName(imageFileNameString);
					opus.setOpus_audioName(audioFileNameString);
					opus.setOpus_image(imageByte);
					opus.setOpus_audio(audioByte);
					opus.setOpus_content(opus_content);
					opus.setOpus_state('B');
					opusService.insertOpus(opus);

					return "redirect:/admin/opus/opus_home";
				}
			}
		}
		return null;

	}

	@GetMapping("/preview_opus/{opus_id}")
	public String previewOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findbyid = opusService.queryById(opus_id);

//		 @GetMapping("/showPPT")
//		    public void showPPT(@RequestParam("path") String path,HttpServletResponse response) throws IOException {
//		        byte[] buffer = new byte[1024 * 4];
//		        String type = path.substring(path.lastIndexOf(".") + 1);
//		        //转换pdf文件,如存在则直接显示pdf文件
//		        String pdf = path.replace(type, "pdf");
//		        File pdfFile = new File(pdf);
//		        if (pdfFile.exists()) {
//		            outFile(buffer, pdfFile, response);
//		        } else {
//		            FileInputStream in = new FileInputStream(path);
//		            ZipSecureFile.setMinInflateRatio(-1.0d);
//		            XMLSlideShow xmlSlideShow = new XMLSlideShow(in);
//		            in.close();
//		            // 获取大小
//		            Dimension pgsize = xmlSlideShow.getPageSize();
//		            // 获取幻灯片
//		            List<XSLFSlide> slides = xmlSlideShow.getSlides();
//		            List<File> imageList = new ArrayList<>();
//		            for (int i = 0; i < slides.size(); i++) {
//		                // 解决乱码问题
//		                List<XSLFShape> shapes = slides.get(i).getShapes();
//		                for (XSLFShape shape : shapes) {
//		                    if (shape instanceof XSLFTextShape) {
//		                        XSLFTextShape sh = (XSLFTextShape) shape;
//		                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
//		                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
//		                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
//		                            for (XSLFTextRun xslfTextRun : textRuns) {
//		                                xslfTextRun.setFontFamily("宋体");
//		                            }
//		                        }
//		                    }
//		                }
//		                //根据幻灯片大小生成图片
//		                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
//		                Graphics2D graphics = img.createGraphics();
//		                graphics.setPaint(Color.white);
//		                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
//		                // 将PPT内容绘制到img上
//		                slides.get(i).draw(graphics);
//		                //图片将要存放的路径
//		                String absolutePath = basePath + File.separator+ (i + 1) + ".jpg";
//		                File jpegFile = new File(absolutePath);
//		                if (!jpegFile.exists()) {
//		                    // 判断如果图片存在则不再重复创建，建议将图片存放到一个特定目录，后面会统一删除
//		                    FileOutputStream fileOutputStream = new FileOutputStream(jpegFile);
//		                    ImageIO.write(img, "jpg", fileOutputStream);
//		                }
//		                // 图片路径存放
//		                imageList.add(jpegFile);
//		            }
//		            File file = png2Pdf(imageList, pdf);
//		            outFile(buffer, file, response);
//		        }
//		    }

//		    private void outFile(byte[] buffer, File pdfFile, HttpServletResponse response) throws IOException {
//		        ByteArrayOutputStream out;
//		        int n = 0;
//		        FileInputStream fileInputStream = new FileInputStream(pdfFile);
//		        out = new ByteArrayOutputStream();
//		        ServletOutputStream outputStream = response.getOutputStream();
//		        while ((n = fileInputStream.read(buffer)) != -1) {
//		            out.write(buffer, 0, n);
//		        }
//		        outputStream.write(out.toByteArray());
//		        outputStream.flush();
//		    }
//			//将图片列表转换为PDF格式文件并存储
//		    public File png2Pdf(List<File> pngFiles, String pdfFilePath) {
//		        Document document = new Document();
//		        File pdfFile = null;
//		        long startTime = System.currentTimeMillis();
//		        try {
//		            pdfFile = new File(pdfFilePath);
//		            if (pdfFile.exists()) {
//		                return pdfFile;
//		            }
//		            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
//		            document.open();
//		            pngFiles.forEach(pngFile -> {
//		                try {
//		                    Image png = Image.getInstance(pngFile.getCanonicalPath());
//		                    png.scalePercent(50);
//		                    document.add(png);
//		                } catch (Exception e) {
//		                    System.out.println("png2Pdf exception");
//		                }
//		            });
//		            document.close();
//		            return pdfFile;
//		        } catch (Exception e) {
//		            System.out.println(String.format("png2Pdf %s exception", pdfFilePath));
//		        } finally {
//		            if (document.isOpen()) {
//		                document.close();
//		            }
//		            // 删除临时生成的png图片
//		            for (File pngFile : pngFiles) {
//		                try {
//		                    FileUtils.delete(pngFile);
//		                } catch (IOException e) {
//		                    e.printStackTrace();
//		                }
//		            }
//		            long endTime = System.currentTimeMillis();
//		            System.out.println("png2Pdf耗时：" + (endTime - startTime));
//		        }
//
//		        return null;
//		    }

		model.addAttribute("findbyopusid", findbyid);

		return "admin/opus/preview_opus.html";

	}

	@GetMapping("/upate_opus/{opus_id}")
	public String updateOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findbyid = opusService.queryById(opus_id);
		model.addAttribute("findbyopusid", findbyid);

		return "admin/opus/opus_update.html";

	}

	@PostMapping("/update_opus/controller/{opus_id}")
//	@ResponseBody
	public String updateOpus(@PathVariable("opus_id") int opus_id, @RequestParam("opus_name") String opus_name,
//			@RequestParam("pptfile") MultipartFile pptfile,
			@RequestParam("opus_member") String opus_member, @RequestParam("opus_imagefile") MultipartFile imagefile,
			@RequestParam("opus_audiofile") MultipartFile audiofile, @RequestParam("opus_content") String opus_content,
			@RequestParam("opus_state") char opus_state,
//			@PathVariable("opus_id") int opus_id , @ModelAttribute("opuses") OpusBean opuses,
			Model model) throws IllegalStateException, IOException {

		boolean memberBoolean = memberService.findbyMember(opus_member);
		if (memberBoolean == false) {
			return "redirect:/login.html";
		}
		byte[] imageByte = null;
		String saveImageFilePath = null;
		String imageFileNameString = null;
		byte[] audioByte = null;
		String saveAudioFilePath = null;
		String audioFileNameString = null;

		if (Objects.nonNull(imagefile) && imagefile != null) {
			String imageFileNameString1 = imagefile.getOriginalFilename();
			imageFileNameString = imageFileNameString1;
			String saveImageFileTempDir = request.getSession().getServletContext().getRealPath("/")
					+ "imageFileTempDir\\";
			File imageFile = new File(saveImageFileTempDir);
			imageFile.mkdirs();

			String saveImageFilePath1 = saveImageFileTempDir + imageFileNameString;
			File saveImageFile = new File(saveImageFilePath1);
			imagefile.transferTo(saveImageFile);
			saveImageFilePath = saveImageFilePath1;
			try (FileInputStream image = new FileInputStream(saveImageFilePath)) {
				byte[] imageByte1 = new byte[image.available()];
				imageByte = imageByte1;
				image.read();
				System.out.println("save:" + saveImageFile);

			}
		}

		if (Objects.nonNull(audiofile) && imagefile != null) {
			String audioFileNameString1 = audiofile.getOriginalFilename();
			audioFileNameString = audioFileNameString1;
			String saveAudioFileTempDir = request.getSession().getServletContext().getRealPath("/")
					+ "audioFileTempDir\\";
			File audioFile = new File(saveAudioFileTempDir);
			audioFile.mkdirs();

			String saveAudioFilePath1 = saveAudioFileTempDir + audioFileNameString1;

			File saveAudioFile = new File(saveAudioFilePath1);
			audiofile.transferTo(saveAudioFile);
			saveAudioFilePath = saveAudioFilePath1;

//		System.out.println("save:" + savePptFile);
			System.out.println("save:" + saveAudioFile);

			try (FileInputStream audio = new FileInputStream(saveAudioFilePath)) {
				byte[] audioByte1 = new byte[audio.available()];
				audioByte = audioByte1;
//				ppt.read();
				audio.read();

			}
		}
		if (imageFileNameString != null && audioFileNameString != null) {

			OpusBean opus = new OpusBean();
			opus.setOpus_id(opus_id);
			opus.setOpus_name(opus_name);
			opus.setOpus_member(opus_member);
			opus.setOpus_createDateTime(opusService.queryById(opus_id).getOpus_createDateTime());
			opus.setOpus_imageName(imageFileNameString);
			opus.setOpus_audioName(audioFileNameString);
			opus.setOpus_image(imageByte);
			opus.setOpus_audio(audioByte);
			opus.setOpus_content(opus_content);
			opus.setOpus_state('B');
			opusService.updateOpus(opus);
			return "redirect:/admin/opus/opus_home";
		}
		if (imageByte != null) {
			if (audioByte == null) {

				OpusBean opus = new OpusBean();
				opus.setOpus_name(opus_name);
				opus.setOpus_member(opus_member);
				opus.setOpus_imageName(opusService.queryById(opus_id).getOpus_imageName());
				opus.setOpus_audioName(opusService.queryById(opus_id).getOpus_audioName());
				opus.setOpus_image(opusService.queryById(opus_id).getOpus_image());
				opus.setOpus_audio(opusService.queryById(opus_id).getOpus_audio());
				opus.setOpus_content(opus_content);
				opus.setOpus_state(opus_state);
				opusService.updateOpus(opus);
				return "redirect:/admin/opus/opus_home";
			}
		}
		if (audioByte != null && Arrays.equals(audioByte, opusService.queryById(opus_id).getOpus_audio())) {
//		if (imageByte != null && imageByte.equals(opusService.queryById(opus_id).getOpus_image())){

			OpusBean opus = new OpusBean();
			opus.setOpus_name(opus_name);
			opus.setOpus_member(opus_member);
			opus.setOpus_audioName(audioFileNameString);
			opus.setOpus_imageName(opusService.queryById(opus_id).getOpus_imageName());
			opus.setOpus_audio(audioByte);
			opus.setOpus_image(opusService.queryById(opus_id).getOpus_image());
			opus.setOpus_content(opus_content);
			opus.setOpus_state(opus_state);
			opusService.updateOpus(opus);
			return "redirect:/admin/opus/opus_home";
		} else if (imageByte != null && imageByte.equals(opusService.queryById(opus_id).getOpus_audio())) {
			OpusBean opus = new OpusBean();
			opus.setOpus_name(opus_name);
			opus.setOpus_member(opus_member);
			opus.setOpus_imageName(audioFileNameString);
			opus.setOpus_audioName(opusService.queryById(opus_id).getOpus_audioName());
			opus.setOpus_image(imageByte);
			opus.setOpus_image(opusService.queryById(opus_id).getOpus_image());
			opus.setOpus_content(opus_content);
			opus.setOpus_state(opus_state);
			opusService.updateOpus(opus);
//		opusService.updateOpus(opuses);
			return "redirect:/admin/opus/opus_home";
		}
		if (Objects.isNull(imagefile) && Objects.isNull(audiofile)) {
			OpusBean opus = new OpusBean();
			opus.setOpus_id(opus_id);
			opus.setOpus_name(opus_name);
			opus.setOpus_member(opus_member);
			opus.setOpus_imageName(opusService.queryById(opus_id).getOpus_imageName());
			opus.setOpus_audioName(opusService.queryById(opus_id).getOpus_audioName());
			opus.setOpus_image(opusService.queryById(opus_id).getOpus_image());
			opus.setOpus_audio(opusService.queryById(opus_id).getOpus_audio());
			opus.setOpus_content(opus_content);
			opus.setOpus_state(opus_state);
			opusService.updateOpus(opus);
			return "redirect:/admin/opus/opus_home";
		}
		return null;

	}

	@GetMapping("/delete_opus/{opus_id}")
	public String deleteOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findid = opusService.queryById(opus_id);
		if (findid != null) {
			model.addAttribute("findbyopusid", findid);
			System.out.println(findid);
			return "admin/opus/opus_delete.html";
		}
		return null;

	}

	@GetMapping("/delete_opus/controller/{opus_id}")
	public String deleteOpus(@PathVariable("opus_id") int opus_id, Model model) {
		opusService.deleteOpusById(opus_id);
		System.out.println(opus_id);
		Object message = "'已刪除 第' + opus_id + '筆詩歌'";
		request.setAttribute("message", message);
		return "redirect:/admin/opus/opus_home";

	}

	@GetMapping("state_opus/01/{id}")
	public String stateOpus01(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('B');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home";

	}

	@GetMapping("state_opus/02/{id}")
	public String stateOpus02(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('C');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home";

	}

	@GetMapping("state_opus/03/{id}")
	public String stateOpus03(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('B');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home";

	}

}
