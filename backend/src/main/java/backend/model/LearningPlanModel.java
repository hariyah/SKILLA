package backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "LearningPlan")
public class LearningPlanModel {
    @Id
    @GeneratedValue
    private String id;
    private String postOwnerID;
    private String postOwnerName;
    private String skillTitle;
    private String description;
    private String field;
    private String startDate;
    private String endDate;
    private String imagePath;
    private String pdfPath;

    public LearningPlanModel() {

    }

    public LearningPlanModel(String id, String postOwnerID, String postOwnerName, String skillTitle, String description, String field, String startDate, String endDate) {
        this.id = id;
        this.postOwnerID = postOwnerID;
        this.postOwnerName = postOwnerName;
        this.skillTitle = skillTitle;
        this.description = description;
        this.field = field;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostOwnerID() {
        return postOwnerID;
    }

    public void setPostOwnerID(String postOwnerID) {
        this.postOwnerID = postOwnerID;
    }

    public String getPostOwnerName() {
        return postOwnerName;
    }

    public void setPostOwnerName(String postOwnerName) {
        this.postOwnerName = postOwnerName;
    }

    public String getSkillTitle() {
        return skillTitle;
    }

    public void setSkillTitle(String skillTitle) {
        this.skillTitle = skillTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }
}
