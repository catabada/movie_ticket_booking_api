package vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service.image;

import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.FileConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.UnsupportedFileTypeException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.entity.MediaFile;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.service.FileService;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.DateUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class ImageFileService implements FileService {
    private final String imageExtensionSave = ".jpg";

    private final String[] mimeTypeSupport = {MimeTypeUtils.IMAGE_JPEG_VALUE, MimeTypeUtils.IMAGE_PNG_VALUE};

    @Override
    public MediaFile uploadFile(String fileName, MultipartFile file) throws UnsupportedFileTypeException, IOException {
        if (!Arrays.asList(mimeTypeSupport).contains(file.getContentType()))
            throw new UnsupportedFileTypeException(
                    file.getOriginalFilename() + " is not an image file: [" + String.join("; ", mimeTypeSupport) + "]");
        Path imageFolder = Paths.get(FileConstant.IMAGE_FOLDER).toAbsolutePath().normalize();

        if (!imageFolder.toFile().exists())
            Files.createDirectories(imageFolder);

        fileName = StringUtils.normalizeUri(fileName) + "-" + DateUtils.GetCurrentTimeMillis() + imageExtensionSave;


        Files.deleteIfExists(Paths.get(imageFolder + fileName));
        Files.copy(file.getInputStream(), imageFolder.resolve(fileName), REPLACE_EXISTING);

        return MediaFile.builder()
                        .pathFolder(Paths.get(imageFolder.toString(), File.separator, fileName).toString())
                        .pathUrl(FileConstant.USER_URL_PATH + fileName)
                        .build();

    }

    @Override
    public void remove(String path) throws IOException {
        Files.delete(Paths.get(path));
    }
}
