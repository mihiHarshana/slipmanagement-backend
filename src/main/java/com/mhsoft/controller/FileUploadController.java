package com.mhsoft.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mhsoft.config.JwtTokenUtil;
import com.mhsoft.model.DAOUser;
import com.mhsoft.repo.UserRepo;
import com.mhsoft.utils.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
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
//TODO: working
/*    @RequestMapping (value="/download", method = RequestMethod.POST)

    public ResponseEntity<Object> downloadFile(@RequestBody String body) throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData obj = objectMapper.readValue(body, ResponseData.class);
        String slipName = obj.getSlipName();
        String slipLink = obj.getSlipLink();
        slipName = slipLink.concat("\\").concat(slipName);
        System.out.println(slipName);
        File file = new File(slipName);
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
    }*/

/*    @RequestMapping (value="/download", method = RequestMethod.POST)
    public ResponseEntity<Object> downloadFile(@RequestBody String body) throws  IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData obj = objectMapper.readValue(body, ResponseData.class);
        String slipName = obj.getSlipName();
        String slipLink = obj.getSlipLink();
        slipName = slipLink.concat("\\").concat(slipName);
        System.out.println(slipName);
        File file = new File(slipName);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidated");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity <Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
        return responseEntity;

    }*/

/*    @RequestMapping (value="/download", method = RequestMethod.POST)
    public File  downloadFile(@RequestBody String body) throws  IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData obj = objectMapper.readValue(body, ResponseData.class);
        String slipName = obj.getSlipName();
        String slipLink = obj.getSlipLink();
        slipName = slipLink.concat("\\").concat(slipName);
        System.out.println(slipName);
        File file = new File(slipName);
       InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidated");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity <Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/txt")).body(resource);
        return file ;

    }*/


    @RequestMapping (value="/download", method = RequestMethod.POST)
    public ResponseEntity<Resource> downloadFile(@RequestBody String body) throws IOException, SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseData obj = objectMapper.readValue(body, ResponseData.class);
        String slipName = obj.getSlipName();
        String slipLink = obj.getSlipLink();
        slipName = slipLink.concat("\\").concat(slipName);
        System.out.println(slipName);
       // File file = new File(slipName);
      //  Resource resource = new UrlResource(file.toURI());



        Resource resource = new FileSystemResource(slipName);

        // Set the appropriate content type for the response
        String contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);




/*        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"",file.getName()));
        headers.add("Cache-Control", "no-cache, no-store, must-revalidated");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        ResponseEntity <Object> responseEntity = ResponseEntity.ok().headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);*/
/*        byte [] fileinbytes = Files.readAllBytes(file.toPath());
     //   Blob blob = new SerialBlob(fileinbytes);
        Blob blob = new SerialBlob(file.toPath(). );
        return blob;*/

    }



}
