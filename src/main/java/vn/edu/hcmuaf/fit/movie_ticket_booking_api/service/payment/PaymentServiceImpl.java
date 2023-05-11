package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.payment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.payment.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.middleware.entity.ZonedDateTimeTypeAdapter;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.shared.constant.Currency;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.shared.constant.Language;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.DateUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.*;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapterFactory(new TypeAdapterFactory() {
                @SuppressWarnings("unchecked")
                @Override
                public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
                    if (type.getRawType() == ZonedDateTime.class) {
                        return (TypeAdapter<T>) new ZonedDateTimeTypeAdapter();
                    }
                    return null;
                }
            })
            .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (jsonElement, type, context)
                    -> new Date(jsonElement.getAsJsonPrimitive().getAsLong()))
            .create();

    @Value("${payment.momo.partnerCode}")
    private String partnerCode;

    @Value("${payment.momo.accessKey}")
    private String accessKey;

    @Value("${payment.momo.secretKey}")
    private String secretKey;

    @Value("${payment.momo.returnUrl}")
    private String returnUrl;

    @Value("${payment.momo.ipnUrl}")
    private String ipnUrl;

    @Value("${payment.momo.apiEndpoint}")
    private String apiEndpoint;

    @Value("${payment.vn-pay.vnp_Version}")
    private String vnp_Version;

    @Value("${payment.vn-pay.vnp_OrderType}")
    private String vnp_OrderType;

    @Value("${payment.vn-pay.vnp_TmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vn-pay.vnp_HashSecret}")
    private String vnp_HashSecret;

    @Value("${payment.vn-pay.vnp_Url}")
    private String vnp_Url;

    @Value("${payment.vn-pay.vnp_ReturnUrl}")
    private String vnp_ReturnUrl;

    @Override
    public MomoResponse createMomoCapturePayment(InvoiceDto invoice) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        String orderId = invoice.getCode();
        String requestId = String.valueOf(System.currentTimeMillis());
        String amount = 1000 + "";
        String orderInfo = "Thanh toán hóa đơn " + orderId;
        String requestType = "captureWallet";

        MomoRequest request = MomoRequest.builder()
                .orderId(orderId)
                .requestId(requestId)
                .requestType(requestType)
                .autoCapture(true)
                .amount(amount)
                .orderInfo(orderInfo)
                .extraData("")
                .lang("vi")
                .ipnUrl(ipnUrl)
                .redirectUrl(returnUrl)
                .partnerCode(partnerCode)
                .build();

        String payload = "accessKey=" + accessKey + "&amount=" + amount + "&extraData=" + "&ipnUrl=" +
                ipnUrl + "&orderId=" + orderId + "&orderInfo=" + orderInfo + "&partnerCode=" + partnerCode +
                "&redirectUrl=" + returnUrl + "&requestId=" + requestId + "&requestType=" + requestType;
        String signature = StringUtils.signHmacSHA256(payload, secretKey);
        request.setSignature(signature);

        String json = mapper.writeValueAsString(request);
        System.out.println(json);
        String response = Request.Post(apiEndpoint)
                .bodyString(json, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8))
                .execute().returnContent().asString(StandardCharsets.UTF_8);

        System.out.println(response);

        MomoResponse momoResponse = GSON.fromJson(response, MomoResponse.class);
        momoResponse.setInvoice(invoice);

        return momoResponse;
    }

    @Override
    public String createVNPayPayment(InvoiceDto invoice, HttpServletRequest request) throws Exception {
        VNPayRequest vnPayRequest = VNPayRequest.builder()
                .vnp_Version(vnp_Version)
                .vnp_Command("pay")
                .vnp_TmnCode(vnp_TmnCode)
                .vnp_Amount(100000 + "")
//                .vnp_BankCode(VNP_BANK_CODE)
                .vnp_CreateDate(DateUtils.format(new Date(), "yyyyMMddHHmmss"))
                .vnp_CurrCode(Currency.VND)
                .vnp_IpAddr(request.getRemoteAddr())
                .vnp_Locale(Language.VIETNAMESE.locale())
                .vnp_OrderInfo(invoice.getCode())
                .vnp_OrderType(vnp_OrderType)
                .vnp_ReturnUrl(vnp_ReturnUrl)
                .vnp_TxnRef(invoice.getCode())
                .build();

        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> map = GSON.fromJson(GSON.toJson(vnPayRequest), type);

        // sort map by key and create hash data with key=value&key=value
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> {
                    String name = x.getKey();
                    String value = x.getValue();

                    if (value != null && !value.isEmpty()) {
                        hashData.append(URLEncoder.encode(name, StandardCharsets.US_ASCII)).append("=").append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append("&");
                        query.append(name).append("=").append(URLEncoder.encode(value, StandardCharsets.US_ASCII)).append("&");
                    }
                });

        // remove last "&"
        hashData.deleteCharAt(hashData.length() - 1);
        query.deleteCharAt(query.length() - 1);

        String queryStr = query.toString();

        String vnp_SecureHash = StringUtils.signHmacSHA512(hashData.toString(), vnp_HashSecret);

        return vnp_Url + "?" + queryStr + "&vnp_SecureHash=" + vnp_SecureHash;
    }
}
