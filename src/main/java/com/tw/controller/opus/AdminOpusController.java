package com.tw.controller.opus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
			@RequestParam("opus_member") String opus_member, @RequestParam("imagefile") MultipartFile imagefile,
			@RequestParam("audiofile") MultipartFile audiofile, @RequestParam("opus_content") String opus_content,
			Model model) throws IllegalStateException, IOException {
		if (memberService.findByMemberId(opus_member).isEmpty()) {
			return "redirect:/singin.html";
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
					opus.setOpus_state('A');
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
//		        //??????pdf??????,????????????????????????pdf??????
//		        String pdf = path.replace(type, "pdf");
//		        File pdfFile = new File(pdf);
//		        if (pdfFile.exists()) {
//		            outFile(buffer, pdfFile, response);
//		        } else {
//		            FileInputStream in = new FileInputStream(path);
//		            ZipSecureFile.setMinInflateRatio(-1.0d);
//		            XMLSlideShow xmlSlideShow = new XMLSlideShow(in);
//		            in.close();
//		            // ????????????
//		            Dimension pgsize = xmlSlideShow.getPageSize();
//		            // ???????????????
//		            List<XSLFSlide> slides = xmlSlideShow.getSlides();
//		            List<File> imageList = new ArrayList<>();
//		            for (int i = 0; i < slides.size(); i++) {
//		                // ??????????????????
//		                List<XSLFShape> shapes = slides.get(i).getShapes();
//		                for (XSLFShape shape : shapes) {
//		                    if (shape instanceof XSLFTextShape) {
//		                        XSLFTextShape sh = (XSLFTextShape) shape;
//		                        List<XSLFTextParagraph> textParagraphs = sh.getTextParagraphs();
//		                        for (XSLFTextParagraph xslfTextParagraph : textParagraphs) {
//		                            List<XSLFTextRun> textRuns = xslfTextParagraph.getTextRuns();
//		                            for (XSLFTextRun xslfTextRun : textRuns) {
//		                                xslfTextRun.setFontFamily("??????");
//		                            }
//		                        }
//		                    }
//		                }
//		                //?????????????????????????????????
//		                BufferedImage img = new BufferedImage(pgsize.width, pgsize.height, BufferedImage.TYPE_INT_RGB);
//		                Graphics2D graphics = img.createGraphics();
//		                graphics.setPaint(Color.white);
//		                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
//		                // ???PPT???????????????img???
//		                slides.get(i).draw(graphics);
//		                //???????????????????????????
//		                String absolutePath = basePath + File.separator+ (i + 1) + ".jpg";
//		                File jpegFile = new File(absolutePath);
//		                if (!jpegFile.exists()) {
//		                    // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
//		                    FileOutputStream fileOutputStream = new FileOutputStream(jpegFile);
//		                    ImageIO.write(img, "jpg", fileOutputStream);
//		                }
//		                // ??????????????????
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
//			//????????????????????????PDF?????????????????????
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
//		            // ?????????????????????png??????
//		            for (File pngFile : pngFiles) {
//		                try {
//		                    FileUtils.delete(pngFile);
//		                } catch (IOException e) {
//		                    e.printStackTrace();
//		                }
//		            }
//		            long endTime = System.currentTimeMillis();
//		            System.out.println("png2Pdf?????????" + (endTime - startTime));
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

		return "admin/opus/update_opus.html";

	}

	@PostMapping("/update_opus/controller/{opus_id}")
//	@ResponseBody
	public String updateOpus(@PathVariable("opus_id") int forum_id, @RequestParam("opus_name") String opus_name,
			@RequestParam("opus_image") MultipartFile opus_image, @RequestParam("opus_content") String opus_content,
			Model model) throws IllegalStateException, IOException {
		String imageNameString = opus_image.getOriginalFilename();

		System.out.println("image: " + imageNameString);
		String saveTempDir = request.getSession().getServletContext().getRealPath("/") + "imageTempDir\\";
		File save = new File(saveTempDir);
		save.mkdirs();

		String saveImagePath = saveTempDir + imageNameString;

		File saveImage = new File(saveImagePath);
		opus_image.transferTo(saveImage);

		System.out.println("save:" + saveImage);

		try (FileInputStream image1 = new FileInputStream(saveImagePath)) {
			byte[] b = new byte[image1.available()];
			image1.read();

			if (imageNameString != null && imageNameString.length() != 0) {
				OpusBean opus = new OpusBean();
				opus.setOpus_name(opus_name);
				opus.setOpus_image(b);
				opus.setOpus_imageName(imageNameString);
				opus.setOpus_content(opus_content);
				opusService.updateOpus(opus);
				System.out.println("inside");
			}
		}
		return "admin/forum/success.html";
	}

	@GetMapping("/delete_opus/{opus_id}")
	public String deleteOpusController(@PathVariable("opus_id") int opus_id, Model model) {
		OpusBean findid = opusService.queryById(opus_id);
		if (findid != null) {
			model.addAttribute("findbyforumid", findid);
			System.out.println(findid);
			return "admin/opus/delete_opus.html";
		}
		return null;

	}

	@PostMapping("/delete_opus/controller/{opus_id}")
	public String deleteOpus(@PathVariable("opus_id") int opus_id, Model model) {
		opusService.deleteOpusById(opus_id);
		System.out.println(opus_id);
		return "admin/forum/success.html";

	}

	@GetMapping("state_opus/01/{id}")
	public String stateOpus01(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('B');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home.html";

	}

	@GetMapping("state_opus/02/{id}")
	public String stateOpus02(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('C');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home.html";

	}

	@GetMapping("state_opus/03/{id}")
	public String stateOpus03(@PathVariable("id") int opus_id) {
		OpusBean opus = opusService.queryById(opus_id);
		opus.setOpus_state('B');
		opusService.updateOpus(opus);
		return "redirect:/admin/opus/opus_home.html";

	}

}
