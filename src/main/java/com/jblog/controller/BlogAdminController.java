package com.jblog.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jblog.service.BlogAdminService;
import com.jblog.service.UserService;
import com.jblog.vo.AttachFileVO;
import com.jblog.vo.BlogVo;
import com.jblog.vo.CategoryVo;
import com.jblog.vo.PostVo;
import com.jblog.vo.UserVo;

import net.coobird.thumbnailator.Thumbnailator;

//내블로그 관리
@Controller
@RequestMapping("/{id}/admin")
public class BlogAdminController {
	final Logger logger = LoggerFactory.getLogger(BlogAdminController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogAdminService blogAdminService;
	
	//내블로그 관리페이지 이동
	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	public String manageBlog(@PathVariable("id") String id, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(id);
		int userNo = userVo.getUserNo();
		model.addAttribute("userNo",userNo);
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
		return "blog/admin/blog-admin-basic";
	}
	
//	//내블로그 관리페이지 > 기본설정변경버튼
//	@RequestMapping(value = "/blogSetting", method = RequestMethod.POST)
//	public String blogSetting(@ModelAttribute BlogVo blogVo, @ModelAttribute UserVo uservo, Model model) {
//		//주소의id에 맞는 userNo가져오기
//		UserVo userVo = userService.getUserNo(uservo.getId());
//		int userNo = userVo.getUserNo();
//		blogVo.setUserNo(userNo);
//		
//		//기존 정보들 블로그 관리 화면에 표출
//		BlogVo basic = new BlogVo();
//		basic = blogAdminService.getData(userNo);
//		model.addAttribute("basic",basic);
//		
//		//입력사항 update로
//		blogAdminService.blogSetting(blogVo);
//		return "redirect:/"+ uservo.getId() +"/admin/basic";
//	}
	
	//내블로그 관리 > 카테고리이동
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(@PathVariable("id") String id, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo uservo = userService.getUserNo(id);
		int userNo = uservo.getUserNo();
		//System.out.println("userNo는?!?: "+userNo);
		model.addAttribute("userNo",userNo);
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
				
		//카테고리 데이터 가져오기(카테고리번호, 카테고리명, 포스트 수, 설명)
		List<CategoryVo> cateVo = blogAdminService.getCateData(userNo);
		model.addAttribute("cateVo",cateVo);
		
		return "blog/admin/blog-admin-cate";
	}
	
	
	//카테고리 리스트 추가
	@ResponseBody
	@RequestMapping(value = "/addCate", method = RequestMethod.POST)
	public CategoryVo addCate(@RequestBody CategoryVo catevo) {
		//insert -> userNo, cateName, description
		System.out.println("catevo.getUserNo(): "+catevo.getUserNo());
		System.out.println("catevo.getCateName(): "+catevo.getCateName());
		System.out.println("catevo.getDescription(): "+catevo.getDescription());
		blogAdminService.addCate(catevo);
		
		return catevo;
	}
	
	//카테고리 리스트 삭제 전 post개수확인
	@ResponseBody
	@RequestMapping(value = "/countPost", method = RequestMethod.POST)
	public CategoryVo countPost(@RequestBody CategoryVo catevo, Model model){
		//선택한 카테고리의 포스트 수를 가져오기 parameter = userNo, cateNo
		CategoryVo cv = blogAdminService.getCountPost(catevo);
		
		return cv;
	}
	
	//카테고리 리스트 삭제
	@ResponseBody
	@RequestMapping(value = "/deleteCate", method = RequestMethod.POST)
	public CategoryVo deleteCate(@RequestBody CategoryVo catevo, Model model){
		int cateNo = catevo.getCateNo();
		blogAdminService.delete(cateNo);
		return catevo;
	}

	//내블로그 관리 > 글작성페이지
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@PathVariable("id") String id, Model model) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(id);
		int userNo = userVo.getUserNo();
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
				
		//카테고리 데이터 가져오기(카테고리번호, 카테고리명, 포스트 수, 설명)
		List<CategoryVo> cateVo = blogAdminService.getCateData(userNo);
		model.addAttribute("cateVo",cateVo);
		
		return "blog/admin/blog-admin-write";
	}

	//내블로그 관리 > 글작성 실행
	@RequestMapping(value = "/writePost", method = RequestMethod.POST)
	public String writePost(@ModelAttribute PostVo postVo, @ModelAttribute UserVo uservo
							,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		postVo.toString();
		System.out.println(postVo.toString());
		String postTitle = postVo.getPostTitle();
		String postContent = postVo.getPostContent();
		if(postTitle.equals("") || postContent.equals("")) {
			redirectAttributes.addFlashAttribute("msg", "fail");
			String referer = request.getHeader("Referer");
			return "redirect:"+ referer;
		}else {
			 blogAdminService.writePost(postVo);
			redirectAttributes.addFlashAttribute("msg", "success");
			return "redirect:/"+ uservo.getId() +"/admin/write";
		}
		
	}
	
	//내블로그 관리페이지 > 기본설정변경버튼
	@RequestMapping(value = "/blogSetting", method = RequestMethod.POST)
	public String blogSetting(@ModelAttribute BlogVo blogVo, @ModelAttribute UserVo uservo, Model model
								,@RequestParam("file") MultipartFile file
								,HttpServletRequest request, RedirectAttributes redirectAttributes) {
		//주소의id에 맞는 userNo가져오기
		UserVo userVo = userService.getUserNo(uservo.getId());
		int userNo = userVo.getUserNo();
		System.out.println(blogVo.toString());
		blogVo.setUserNo(userNo);
		
		//기존 정보들 블로그 관리 화면에 표출
		BlogVo basic = new BlogVo();
		basic = blogAdminService.getData(userNo);
		model.addAttribute("basic",basic);
		
		///////////////////////////파일업로드/////////////////////////////////////
		String fileRealName = file.getOriginalFilename(); //파일명을 얻어낼 수 있는 메서드!
		long size = file.getSize(); //파일 사이즈
		
		System.out.println("파일명 : "  + fileRealName);
		System.out.println("용량크기(byte) : " + size);
		//서버에 저장할 파일이름 fileextension으로 .jsp이런식의  확장자 명을 구함
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
		String uploadFolder = "D:\\javaStudy\\workspace\\jblog\\src\\main\\webapp\\assets\\upload";
		
		/*
		  파일 업로드시 파일명이 동일한 파일이 이미 존재할 수도 있고 사용자가 
		  업로드 하는 파일명이 언어 이외의 언어로 되어있을 수 있습니다. 
		  타인어를 지원하지 않는 환경에서는 정산 동작이 되지 않습니다.(리눅스가 대표적인 예시)
		  고유한 랜던 문자를 통해 db와 서버에 저장할 파일명을 새롭게 만들어 준다.
		 */
		
		UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		String[] uuids = uuid.toString().split("-");
		
		String uniqueName = uuids[0];
		System.out.println("생성된 고유문자열" + uniqueName);
		System.out.println("확장자명" + fileExtension);
		
		// File saveFile = new File(uploadFolder+"\\"+fileRealName); uuid 적용 전
		
		File saveFile = new File(uploadFolder+"\\"+uniqueName + fileExtension);  // 적용 후
		try {
			file.transferTo(saveFile); // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String saveFileName = uniqueName+fileExtension;
		
		blogVo.setLogoFile(saveFileName);
		///////////////////////////////////////////////////////////////////////
		
		//입력사항 update로
		int count = blogAdminService.blogSetting(blogVo);
		System.out.println("count: "+count);
		if(count == 1) {
			redirectAttributes.addFlashAttribute("msg", "success");
			return "redirect:/"+ uservo.getId() +"/admin/basic";
		}else {
			redirectAttributes.addFlashAttribute("msg", "fail");
			return "redirect:/"+ uservo.getId() +"/admin/basic";
		}
		
	}
	
	
	
	
	
	
//	@PostMapping("/uploadFormAction")
//	public String uploadFormPost(MultipartFile[] uploadFile, Model model) {
//		
//		// (1)파일 저장할 위치 설정
//		String uploadFolder = "D:\\A_TeachingMaterial\\6.JspSpring\\workspace\\springProj2\\src\\main\\webapp\\resources\\upload";
//		
//		List<String> list = new ArrayList<String>();
//		
//		for(MultipartFile multipartFile : uploadFile) {
//			logger.info("-----------");
//			logger.info("파일명 : " + multipartFile.getOriginalFilename());
//			logger.info("파일크기 : " + multipartFile.getSize());
//			
//			// uploadFolder\\gongu03.jpg으로 조립
//			// 이렇게 업로드 하겠다라고 설계
//			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
//			
//			try {
//				//파일 실제 명을 list에 담음
//				list.add(multipartFile.getOriginalFilename());
//				//transferTo() : 물리적으로 파일 업로드가 됨
//				multipartFile.transferTo(saveFile);
//			}catch(Exception e) {
//				logger.info(e.getMessage());
//			}//end catch
//		}//end for
//		//list : 파일명들이 들어있음
//		model.addAttribute("list", list);
//		
//		//forward
//		return "uploadTest/uploadSuccess";
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
		//<form> 태그를 이용하던 방식과 동일한 방식으로 처리됨
		//Ajax 방식으로 결과 데이터를 전달하므로 Model을 사용하지 않음.
//		@ResponseBody
//		@PostMapping("/uploadAjaxAction")
//		public List<AttachFileVO> uploadAjaxAction(MultipartFile[] uploadFile) {
//			final Logger logger = LoggerFactory.getLogger(BlogAdminController.class);
//			
//			String uploadFolder = "D:\\javaStudy\\workspace\\jblog\\src\\main\\webapp\\assets\\upload";
//			//연/월/일 폴더 생성 시작-------
//			File uploadPath = new File(uploadFolder, getFolder());
//			logger.info("uploadPath : " + uploadPath);
//			
//			if(uploadPath.exists()==false) {//해당 경로가 없으면 생성해줘야함
//				uploadPath.mkdirs();			
//			}
//			//연/월/일 폴더 생성 끝-------
//			
//			//첨부된 파일의 이름을 담을 List
//			List<AttachFileVO> list = new ArrayList<AttachFileVO>();
//			
//			for(MultipartFile multipartFile : uploadFile) {
//				
//				logger.info("-----------");
//				logger.info("파일명 : " + multipartFile.getOriginalFilename());
//				logger.info("파일크기 : " + multipartFile.getSize());
//				
//				AttachFileVO attachFileVO = new AttachFileVO();
//				//1) fileName
//				attachFileVO.setFileName(multipartFile.getOriginalFilename());
//				
//				//-----------UUID 파일명 처리 시작 ----------------------------
//				//동일한 이름으로 업로드되면 기존 파일을 지우게 되므로 이를 방지하기 위함
//				UUID uuid = UUID.randomUUID();
//				
//				String uploadFileName = uuid.toString() + "-" + multipartFile.getOriginalFilename();
//				// c:\\upload\\gongu03.jpg으로 조립
//				// 이렇게 업로드 하겠다라고 설계 uploadFolder -> uploadPath
//				File saveFile = new File(uploadPath,uploadFileName);
//				//-----------UUID 파일명 처리 끝 ----------------------------
//				
//				try {
//					
//					//transferTo() : 물리적으로 파일 업로드가 됨
//					multipartFile.transferTo(saveFile);
//				
//					//2) uploadPath
//					attachFileVO.setUploadPath(uploadPath.getPath());
//					//3) uuid
//					attachFileVO.setUuid(uuid.toString());
//					//-------썸네일 처리 시작---------
//					//이미지 파일인지 체킹
//					if(checkImageType(saveFile)) {
//						logger.info("이미지 파일? true");
//						//4) image여부
//						attachFileVO.setImage(true);
//						//uploadPath : 연/월/일이 포함된 경로
//						//uploadFileName : UUID가 포함된 파일명
//						FileOutputStream thumbnail = 
//								new FileOutputStream(
//										new File(uploadPath,"s_"+uploadFileName));
//						Thumbnailator.createThumbnail(multipartFile.getInputStream(),
//								thumbnail, 100, 100);
//						thumbnail.close();
//					}else {
//						logger.info("이미지 파일? false");
//					}
//					//-------썸네일 처리 끝---------
//					
//					//파일 실제 명을 list에 담음
//					list.add(attachFileVO);
//				}catch(Exception e){
//					logger.info(e.getMessage());
//				}//end catch
//			}//end for
//			
//			return list;
//		}//end uploadAjaxAction
//		
//		//첨부파일을 보관하는 폴더를 연/월/일 계층 형태로 생성하기 위함
//		private String getFolder() {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date date = new Date();
//			String str = sdf.format(date);
//			return str.replace("-", File.separator);
//		}
//		
//		//특정한 파일이 이미지 타입인지 검사해주는 메소드
//		private boolean checkImageType(File file) {
//			try {
//				//file.toPath() : 파일의 전체 경로
//				logger.info("file.toPath() : " + file.toPath());
//				String contentType = Files.probeContentType(file.toPath());
//				logger.info("contentType : " + contentType);
//				//contentType이 image로 시작하면 이미지 타입이므로 true를 리턴함
//				return contentType.startsWith("image");
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
//			return false;
//		}
//	}
	

    
