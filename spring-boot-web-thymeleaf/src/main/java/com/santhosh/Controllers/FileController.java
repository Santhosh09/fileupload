package com.santhosh.Controllers;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.santhosh.Service.SaveFileService;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

@Controller
public class FileController {

	@Autowired
	SaveFileService saveFileService;

	@GetMapping("/upload")
	public String index(ModelMap model, @ModelAttribute("message") String message) {
		if(!message.isEmpty())
		model.put("message", message);
		return "UploadFile";
	}

	@PostMapping("/upload") // //new annotation since 4.3
	public ModelAndView singleFileUpload(MultipartFile file,HttpServletRequest request, RedirectAttributes ra) {
		if(saveFileService.saveFile(file)){
			ra.addFlashAttribute("message",
					"You successfully uploaded " + file.getOriginalFilename());
		}else{
			ra.addFlashAttribute("message",
					"Failed to upload the file");
		}
		return new ModelAndView(new RedirectView("/upload"));
	}

	@GetMapping("/listfiles")
	public ModelAndView getListFiles(ModelMap model) {
		File dir = saveFileService.getStorageLocation();
		JSONArray arr = new JSONArray();
		File[] listOfFiles = dir.listFiles();
		if(listOfFiles != null){
			Integer size = listOfFiles.length;
			for (int i = 0; i < size; i++) {
				if (listOfFiles[i].isFile()) {
					JSONObject obj = new JSONObject();
					obj.put("fileName", listOfFiles[i].getName());
					obj.put("filePath", "/files/"+listOfFiles[i].getName());
					arr.add(obj);
				}		    
			}			
		}
		model.put("files", arr);
		return new ModelAndView("files", model);
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = saveFileService.getFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}


}