package sn.exam.badwallet.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import sn.exam.badwallet.dto.BillInfo;
import sn.exam.shared.dto.BillResponse;
import sn.exam.shared.dto.BillsByReferencesRequest;

import java.util.List;

@Component
public class FactureServiceClientImpl implements FactureServiceClient {

    private final RestTemplate restTemplate;
    private final String paymentServiceBaseUrl;

    public FactureServiceClientImpl(RestTemplate restTemplate,
                                     @Value("${payment-service.base-url}") String paymentServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.paymentServiceBaseUrl = paymentServiceBaseUrl;
    }

    @Override
    public List<BillInfo> getCurrentFactures(String provider) {
        String url = paymentServiceBaseUrl + "/api/bills/{provider}/current";
        List<BillResponse> responses = restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BillResponse>>() {},
                provider
        ).getBody();
        return toInfoList(responses);
    }

    @Override
    public List<BillInfo> getFacturesByReferences(String provider, List<String> references) {
        String url = paymentServiceBaseUrl + "/api/bills/by-references";
        List<BillResponse> responses = restTemplate.exchange(
                url, HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(new BillsByReferencesRequest(references)),
                new ParameterizedTypeReference<List<BillResponse>>() {}
        ).getBody();
        return toInfoList(responses);
    }

    private List<BillInfo> toInfoList(List<BillResponse> responses) {
        if (responses == null) return List.of();
        return responses.stream()
                .map(r -> new BillInfo(r.billReference(), r.provider(), r.subscriberName(), r.amount(), r.paid()))
                .toList();
    }
}
