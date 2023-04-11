package vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.UnsupportedFileTypeException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.entity.MediaFile;

import java.io.IOException;
import java.util.Optional;

public interface FileService {
    MediaFile uploadFile(String fileName, MultipartFile file) throws UnsupportedFileTypeException, IOException;

    void remove(String path) throws IOException;
}
