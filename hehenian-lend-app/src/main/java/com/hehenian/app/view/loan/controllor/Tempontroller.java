package com.hehenian.app.view.loan.controllor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hehenian.biz.common.filesaving.IFileServerService;
import com.hehenian.biz.common.loan.ILoanPersonService;
  
@Controller 
@RequestMapping(value="/temp") 
public class Tempontroller {
	
	 @Autowired
	 private ILoanPersonService  loanPersonService; 

	 @Autowired
	 private IFileServerService  fileServerService;
	
	
	/**
	 * 初始 图片资料信息
	 * @param loanId
	 * @param loanPersonId
	 * @param model
	 * @author zhengyfmf
	 * @return
	 */
	@RequestMapping(value="/initCertificate")
	public String initCertificateData(Long loanId,Long loanPersonId,Model model){
		/*LoanPersonDo loanPersonDo = new LoanPersonDo();
		loanPersonDo.setLoanId(loanId);
		loanPersonDo.setLoanPersonId(loanPersonId);
		List<CertificateDo> cdList = loanPersonService.initCertificateData(loanId);
		loanPersonDo.setCertificateDoList(cdList);
		model.addAttribute("loanPersonDo",loanPersonDo);*/
		return "app/mhk/upTest";
	}
	
	/**
	 * 修改 图片资料
	 * @param request
	 * @author zhengyfmf
	 * @return  ,LoanPersonDo loanPersonDo,Model model
	 */
	@RequestMapping(value="/saveCertificate")
	@SuppressWarnings("unchecked")
	public String saveOrUpdateCertificate(@RequestParam MultipartFile[] files){
		System.out.println(files.length);
	//	List<CertificateDo> certificateDoList = loanPersonDo.getCertificateDoList();
	//	Map<String, String> params = (Map<String, String>) request.getSession().getAttribute(WebConstants.COLOURLIFE_ADMIN_USER);
		try {
			System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getOriginalFilename());
				String filePath = fileServerService.saveFile(files[i].getInputStream(), files[i].getOriginalFilename(),new int[][] { { 400, 400 } });
				System.out.println("file "+i+" :"+filePath);
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*CertificateDo certificateDo = null;
        String filePath = null;
		try {
			if (certificateDoList == null) {
				certificateDoList = new ArrayList<CertificateDo>();
				for (int i = 0; i < files.size(); i++) {
					filePath = fileServerService.saveFile(files.get(i).getInputStream(), files.get(i).getOriginalFilename(),new int[][] { { 400, 400 } });
					certificateDo = new CertificateDo();
					certificateDo.setLoanId(loanPersonDo.getLoanId());
					certificateDo.setLoanPersonId(loanPersonDo.getLoanPersonId());
					certificateDo.setFilePath(filePath);
					certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
					certificateDo.setCertificateName(files.get(i).getOriginalFilename());
					if (i == 0) {
						certificateDo.setCertificateType(CertificateType.IDCARDZ);
					} else if (i == 1) {
						certificateDo.setCertificateType(CertificateType.IDCARDF);
					} else if (i == 2) {
						certificateDo.setCertificateType(CertificateType.HOUSE);
					} else if (i == 3) {
						certificateDo.setCertificateType(CertificateType.JOB);
					} else if (i == 4) {
						certificateDo.setCertificateType(CertificateType.INCOME);
					} else{
						certificateDo.setCertificateType(CertificateType.OTHERFILE);
					}
					certificateDo.setFileType(FileType.IMAGE);
					certificateDoList.add(certificateDo);
				}
			}else{
				int count = 0;
	            for (int i = 0; i < certificateDoList.size(); i++) {
	                certificateDo = certificateDoList.get(i);
	                if ("".equals(certificateDo.getFilePath()) || certificateDo.getFilePath() == null) {
	                    filePath = fileServerService.saveFile(files.get(count).getInputStream(), files.get(count).getOriginalFilename(),new int[][] { { 400, 400 } });
	                    certificateDo.setCertificateName(files.get(count).getOriginalFilename());
	                    count++;
	                }
	                certificateDo.setCreateUser(params.get("userName"));// 之后要获取值填充
	            }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		loanPersonService.saveOrUpdateCertificate(certificateDoList);
		*/
		return "";
	}
	
	@RequestMapping(value="/testUp")
	@ResponseBody
	public Map<String,Object> testUp(@RequestParam MultipartFile files,Long loanId){
		Map<String,Object> map = new HashMap<String,Object>();
		System.out.println("**************************************");
		System.out.println("loanId="+loanId);
		System.out.println(files.getOriginalFilename());
		System.out.println("**************************************");
		map.put("status", 1);
		return map;
	}
}
