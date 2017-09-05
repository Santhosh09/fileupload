package com.santhosh.Controllers;

import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandlerController {

	 //https://jira.spring.io/browse/SPR-14651
    //Spring 4.3.5 supports RedirectAttributes
	@ExceptionHandler({MultipartException.class,FileSizeLimitExceededException.class,IllegalStateException.class,MaxUploadSizeExceededException.class})
    public String handleError1(FileSizeLimitExceededException f
    	,RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", f.getCause().getMessage());
        return "redirect:/upload";

    }
}