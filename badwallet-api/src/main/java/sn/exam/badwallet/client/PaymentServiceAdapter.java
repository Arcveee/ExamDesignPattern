package sn.exam.badwallet.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sn.exam.shared.dto.BillResponse;

@Component
public class PaymentServiceAdapter implements BillServicePort {

    private final RestTemplate restTemplate;
    private final String paymentServiceBaseUrl;

    public PaymentServiceAdapter(RestTemplate restTemplate,
                                  @Value("${payment-service.base-url}") String paymentServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceBaseUrl = paymentServiceBaseUrl;
    }

    @Override
    public BillResponse fetchBill(String provider, String billReference) {
        String url = paymentServiceBaseUrl + "/api/bills/{provider}/{reference}";
        return restTemplate.getForObject(url, BillResponse.class, provider, billReference);
    }
}
