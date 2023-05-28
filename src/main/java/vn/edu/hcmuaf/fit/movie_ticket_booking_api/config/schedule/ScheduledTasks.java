package vn.edu.hcmuaf.fit.movie_ticket_booking_api.config.schedule;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.NoticeType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Notice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Room;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Showtime;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice.InvoiceRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.notice.NoticeCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.room.RoomCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.showtime.ShowtimeCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user.AppUserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@Component
@Service
public class ScheduledTasks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTasks.class);
    private AppUserCustomRepository appUserCustomRepository;
    private InvoiceRepository invoiceRepository;
    private NoticeCustomRepository noticeCustomRepository;
    private RoomCustomRepository roomCustomRepository;
    private ShowtimeCustomRepository showtimeCustomRepository;

    @Autowired
    public ScheduledTasks(AppUserCustomRepository appUserCustomRepository, InvoiceRepository invoiceRepository, NoticeCustomRepository noticeCustomRepository, RoomCustomRepository roomCustomRepository, ShowtimeCustomRepository showtimeCustomRepository) {
        this.appUserCustomRepository = appUserCustomRepository;
        this.invoiceRepository = invoiceRepository;
        this.noticeCustomRepository = noticeCustomRepository;
        this.roomCustomRepository = roomCustomRepository;
        this.showtimeCustomRepository = showtimeCustomRepository;
    }

    //    @Scheduled(fixedRate = 1000)
    public void scheduleUpdateRoomState() {
//        List<Showtime> showtimes = showtimeCustomRepository.searchShowtime(
//                ShowtimeSearch.builder()
//                        .startTime(ZonedDateTime.now())
//                        .build()
//        );
//
//        showtimes.forEach(showtime -> {
//            Room room = showtime.getRoom();
//            room.setRoomState(RoomState.OCCUPIED);
//        });


    }

    @Scheduled(fixedRate = 1000)
    @Transactional
    public void scheduleTaskRemindUser() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        ZonedDateTime now = ZonedDateTime.now().withZoneSameLocal(ZoneId.of("Asia/Ho_Chi_Minh"));

        List<Invoice> invoices = invoiceRepository.search(
                InvoiceSearch.builder()
                        .startTime(now.plusMinutes(30))
                        .build()
        );

        invoices.forEach(invoice -> {
            AppUser appUser = invoice.getAppUser();
            Showtime showtime = invoice.getShowtime();

            ZonedDateTime startTimeShowtime = showtime.getStartTime().withZoneSameLocal(ZoneId.of("Asia/Ho_Chi_Minh"));

            String dateTime = startTimeShowtime.format(formatter);

            System.out.println("showtime: " + dateTime);
            System.out.println("now: " + now.format(formatter));

            Notice notice = Notice.builder()
                    .description("Sắp tới giờ chiếu phim " + showtime.getMovie().getName() + " vào lúc " + dateTime + ". Vui lòng đến đúng giờ để không bỏ lỡ phim")
                    .type(NoticeType.REMIND)
                    .appUser(appUser)
                    .state(ObjectState.ACTIVE)
                    .isRead(false)
                    .build();
            noticeCustomRepository.saveAndFlush(notice);
        });


    }

    public void scheduleTaskWithInitialDelay() {
    }

    public void scheduleTaskWithCronExpression() {
    }
}
