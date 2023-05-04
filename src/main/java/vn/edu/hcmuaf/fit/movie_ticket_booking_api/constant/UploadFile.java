package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum UploadFile {
    MOVIE_IMAGE("movies/"),
    AVATAR_IMAGE("avatars/");

    private String pathFolder;

    UploadFile(String pathFolder) {
        this.pathFolder = pathFolder;
    }

    public String getPathFolder() {
        return pathFolder;
    }
}
