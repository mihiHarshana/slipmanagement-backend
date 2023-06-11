package com.mhsoft.controller;

import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.model.DAOUser;
import com.mhsoft.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileUploadController {
    private static String UPLOADED_FOLDER = "E:\\projects\\fileuploads\\";

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepo userRepo;


/*    @RequestMapping(value="/", method = RequestMethod.GET)
    public String index() {
        return "upload";
    }*/

    @RequestMapping(value = "/upload", method = RequestMethod.POST)// //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes , @RequestHeader String Authorization) {

        int USER_ID = 0;
        String token = Authorization.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        DAOUser DAOUser = userRepo.getUserByUserName(username);
        USER_ID = DAOUser.getUserid();

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

        try {

            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path temp_path = Paths.get(UPLOADED_FOLDER+  LocalDate.now() + "\\" + USER_ID + "\\" );
            Files.createDirectories(temp_path);
            Path path = Paths.get(temp_path + "\\"+ file.getOriginalFilename());
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @RequestMapping(value="/uploadStatus", method = RequestMethod.GET)
    public String uploadStatus() {
        return "uploadStatus";
    }


    @RequestMapping (value="/download", method = RequestMethod.GET)
    public ResponseEntity<Object> downloadFile(@RequestParam String fileName, @RequestParam String path) throws  IOException {
        fileName = path.concat("\\").concat(fileName);
        System.out.println(fileName);
        File file = new File(fileName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidated");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity <Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);
        return responseEntity;

    }

}
