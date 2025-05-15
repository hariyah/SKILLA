package backend.controller;

import backend.exception.LearningPlanNotFoundException;
import backend.model.LearningPlanModel;
import backend.repository.LearningPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("http://localhost:3000")
public class LearningPlanController {
    @Autowired
    private LearningPlanRepository learningPlanRepository;

    //Insert
    @PostMapping("/learningPlan")
    public LearningPlanModel newLearningPlanModel(@RequestBody LearningPlanModel newLearningPlanModel) {
        return learningPlanRepository.save(newLearningPlanModel);
    }

    @GetMapping("/learningPlan")
    List<LearningPlanModel> getAll() {
        return learningPlanRepository.findAll();
    }

    @GetMapping("/learningPlan/{id}")
    LearningPlanModel getById(@PathVariable String id) {
        return learningPlanRepository.findById(id)
                .orElseThrow(() -> new LearningPlanNotFoundException(id));
    }

    @PutMapping("/learningPlan/{id}")
    public LearningPlanModel update(@RequestParam(value = "image", required = false) MultipartFile image,
                                        @RequestParam(value = "pdf", required = false) MultipartFile pdf,
                                        @RequestPart("data") LearningPlanModel newLearningPlanModel,
                                        @PathVariable String id) throws IOException {
        return learningPlanRepository.findById(id)
                .map(learningPlanModel -> {
                    // Update fields
                    learningPlanModel.setSkillTitle(newLearningPlanModel.getSkillTitle());
                    learningPlanModel.setDescription(newLearningPlanModel.getDescription());
                    learningPlanModel.setPostOwnerID(newLearningPlanModel.getPostOwnerID());
                    learningPlanModel.setPostOwnerName(newLearningPlanModel.getPostOwnerName());
                    learningPlanModel.setField(newLearningPlanModel.getField());
                    learningPlanModel.setStartDate(newLearningPlanModel.getStartDate());
                    learningPlanModel.setEndDate(newLearningPlanModel.getEndDate());

                    // Handle image update
                    if (image != null && !image.isEmpty()) {
                        try {
                            // Delete old image if it exists
                            if (learningPlanModel.getImagePath() != null) {
                                File oldImage = new File(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "Plan" + File.separator + learningPlanModel.getImagePath());
                                if (oldImage.exists()) {
                                    oldImage.delete();
                                }
                            }

                            // Save new image
                            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "Plan" + File.separator;
                            String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
                            File file = new File(uploadDir + uniqueFileName);
                            image.transferTo(file);
                            learningPlanModel.setImagePath(uniqueFileName);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to update image", e);
                        }
                    }

                    // Handle PDF update
                    if (pdf != null && !pdf.isEmpty()) {
                        try {
                            // Delete old PDF if it exists
                            if (learningPlanModel.getPdfPath() != null) {
                                File oldPDF = new File(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "PDF" + File.separator + learningPlanModel.getPdfPath());
                                if (oldPDF.exists()) {
                                    oldPDF.delete();
                                }
                            }

                            // Save new PDF
                            String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "PDF" + File.separator;
                            String uniqueFileName = UUID.randomUUID().toString() + "_" + pdf.getOriginalFilename();
                            File file = new File(uploadDir + uniqueFileName);
                            pdf.transferTo(file);
                            learningPlanModel.setPdfPath(uniqueFileName);
                        } catch (IOException e) {
                            throw new RuntimeException("Failed to update PDF", e);
                        }
                    }

                    return learningPlanRepository.save(learningPlanModel);
                }).orElseThrow(() -> new LearningPlanNotFoundException(id));
    }

    @DeleteMapping("/learningPlan/{id}")
    public void delete(@PathVariable String id) {
        learningPlanRepository.deleteById(id);
    }

    @PostMapping("/learningPlan/uploadImage")
    public String uploadImage(@RequestParam("image") MultipartFile image) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "Plan" + File.separator;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
        String uniqueFileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        File file = new File(uploadDir + uniqueFileName);
        image.transferTo(file); // Save the file
        return uniqueFileName;
    }

    @PostMapping("/learningPlan/uploadPDF")
    public String uploadPDF(@RequestParam("pdf") MultipartFile pdf) throws IOException {
        String uploadDir = System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "PDF" + File.separator;
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); // Create the directory if it doesn't exist
        }
        String uniqueFileName = UUID.randomUUID().toString() + "_" + pdf.getOriginalFilename();
        File file = new File(uploadDir + uniqueFileName);
        pdf.transferTo(file); // Save the file
        return uniqueFileName;
    }

    @GetMapping("/learningPlan/image/{fileName}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "Plan" + File.separator + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg"); // Adjust content type as needed
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/learningPlan/pdf/{fileName}")
    public ResponseEntity<Resource> getPDF(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get(System.getProperty("user.dir") + File.separator + "uploads" + File.separator + "PDF" + File.separator + fileName);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf"); // Set content type for PDF
                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
