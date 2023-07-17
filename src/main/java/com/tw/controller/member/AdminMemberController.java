package com.tw.controller.member;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.type.filter.AbstractClassTestingTypeFilter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

@Controller
@RequestMapping("/admin")
public class AdminMemberController {

	@Autowired
	MemberService memberService;
	@Autowired
	private HttpServletRequest request;


	@GetMapping("/member/member_home")
	public String queryAll(Model model) {
		model.addAttribute("memberlist", memberService.queryMember());
		return "admin/member/member_home.html";
	}

	@GetMapping("/insert_member")
	public String insertStart() {
		return "admin/member/insert_member";

	}

	@PostMapping("/insert_member/controller")
	public String createMember
			(@RequestParam(name = "member_name") String member_name,
			@RequestParam(name = "member_place") String member_place, @RequestParam("member_role") String member_role,
			@RequestParam(name = "member_mail") String member_mail,
			@RequestParam(name = "member_password") String member_password,
			@RequestParam(name = "member_image") MultipartFile member_image)
					throws IllegalStateException, IOException {

//		String fileName = (String) String.format("%s\\%s.%s", "forum", Instant.now().toEpochMilli(),
//		image.getContentType().split("/")[1]);
////if (file == null) {
////	file == "noimage_s.jpg";
////}
//
//
//System.out.println("originalFileName:" + fileName);
//System.out.println(fileName);
//String pathname = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\images\\" + fileName;
//// File saveFile = new File(pathname);
////	file.transferTo(saveFile);
////	System.out.println(saveFile);
//
////File saveTempDirFile = new File(saveTempFile);
////saveTempDirFile.mkdirs();
//
////String saveFilePath = pathname + fileName;
//File saveFile1 = new File(pathname);
//if (!saveFile1.getParentFile().exists()) {
//	saveFile1.getParentFile().mkdirs();
//}

		String imageNameString = member_image.getOriginalFilename();
		System.out.println("image: " + imageNameString);

		String saveTempDir = request.getSession().getServletContext().getRealPath("/") + "memberImageFileTempDir\\";
		File save = new File(saveTempDir);
		save.mkdirs();

		String saveImagePath = saveTempDir + imageNameString;

		File saveImage = new File(saveImagePath);
		member_image.transferTo(saveImage);

		System.out.println("save:" + saveImage);

		try (FileInputStream image1 = new FileInputStream(saveImagePath)) {
			byte[] b = new byte[image1.available()];
			image1.read();

			if (imageNameString != null && imageNameString.length() != 0) {
				
				String encoder = new BCryptPasswordEncoder().encode(member_password);

				MemberBean member = new MemberBean();
				member.setMember_name(member_name);
				member.setMember_place(member_place);
				member.setMember_role(member_role);
				member.setMember_mail(member_mail);
				member.setMember_password(encoder);
				member.setMember_image(b);
				member.setMember_imageName(imageNameString);
				member.setMember_state('A');
				memberService.insertMember(member);

				return "redirect:/admin/member/member_home";

			}
		}
		return null;

	}

	@GetMapping("/update")
	public String updateStart() {
		return "admin/member/update_member";
	}

	@PostMapping("/update/{member_name}/controller")
	public String updqteMember(
			@RequestParam(name = "member_name") String member_name,
			@RequestParam(name = "member_place") String member_place, @RequestParam("member_role") String member_role,
			@RequestParam(name = "member_mail") String member_mail,
			@RequestParam(name = "member_imageName") String member_imageName,
			@RequestParam(name = "member_joinTime") Date member_joinTime,
			@RequestParam(name = "member_image") MultipartFile member_image,
			@RequestParam(name = "member_state") char member_state) throws IllegalStateException, IOException {

//		String fileName = (String) String.format("%s\\%s.%s", "forum", Instant.now().toEpochMilli(),
//		image.getContentType().split("/")[1]);
////if (file == null) {
////	file == "noimage_s.jpg";
////}
//
//
//System.out.println("originalFileName:" + fileName);
//System.out.println(fileName);
//String pathname = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\images\\" + fileName;
//// File saveFile = new File(pathname);
////	file.transferTo(saveFile);
////	System.out.println(saveFile);
//
////File saveTempDirFile = new File(saveTempFile);
////saveTempDirFile.mkdirs();
//
////String saveFilePath = pathname + fileName;
//File saveFile1 = new File(pathname);
//if (!saveFile1.getParentFile().exists()) {
//	saveFile1.getParentFile().mkdirs();
//}

		String imageNameString = member_image.getOriginalFilename();
		System.out.println("image: " + imageNameString);

		String saveTempDir = request.getSession().getServletContext().getRealPath("/") + "memberImageTempDir\\";
		File save = new File(saveTempDir);
		save.mkdirs();

		String saveImagePath = saveTempDir + imageNameString;

		File saveImage = new File(saveImagePath);
		member_image.transferTo(saveImage);

		System.out.println("save:" + saveImage);

		try (FileInputStream image1 = new FileInputStream(saveImagePath)) {
			byte[] b = new byte[image1.available()];
			image1.read();

			if (imageNameString != null && imageNameString.length() != 0) {

				MemberBean member = new MemberBean();
				member.setMember_name(member_name);
				member.setMember_place(member_place);
				member.setMember_role(member_role);
				member.setMember_mail(member_mail);
				member.setMember_image(b);
				member.setMember_imageName(member_imageName);
				member.setMember_imageName(member_imageName);
				member.setMember_state(member_state);
				memberService.updateMember(member_name, member);
				return "admin/member/success.html";

			}
		}
		return null;

	}

	@GetMapping("/delete/{member_name}")
	public String deleteMember(@PathVariable("member_id") String member_name, Model model) {
		boolean finByMemberId = memberService.findbyMember(member_name);
		if (finByMemberId = true) {
			model.addAttribute("findbymemberid", finByMemberId);
			return "admin/member/delete_member.html";

		}
		return null;
	}/* 123121233 */

	@PostMapping("delete/{member_name}/controller")
	public String deleteMemberController(@PathVariable("member_name") String member_name) {
		memberService.deleteById(member_name);
		return "admin/member/home.html";

	}
	
	@GetMapping("/preview_member/{member_name}")
	public String previewController(@PathVariable("member_name") String member_name, Model model) {
		boolean findByMemberId = memberService.findbyMember(member_name);
		model.addAttribute("findbymemberid", findByMemberId);
		return "admin/member/member_preview.html" ;
	}
//	@PostMapping("state/{member_id}")
//	public String stateChange(@PathVariable("member_id") Integer member_id,Model model) {
//		Optional<MemberBean> findByMemberId = memberService.findByMemberId(member_id);
//		if (findByMemberId.get().getMember_state() == 'B') {
//			
//		}
//	}

//	public memberBean () {

//	}

}
