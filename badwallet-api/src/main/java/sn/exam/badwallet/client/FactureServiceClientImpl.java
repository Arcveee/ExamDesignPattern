package sn.exam.badwallet.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import sn.exam.badwallet.dto.BillInfo;
import sn.exam.badwallet.exception.ExternalServiceUnavailableException;
import sn.exam.shared.dto.BillResponse;
import sn.exam.shared.dto.BillsByReferencesRequest;

import java.time.LocalDate;
import java.util.List;

@Component
@SuppressWarnings("null")
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
        return execute(() -> restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BillResponse>>() {},
                provider
        ).getBody());
    }

    @Override
    public List<BillInfo> getCurrentFacturesByUnite(String provider, String unite) {
        String url = paymentServiceBaseUrl + "/api/bills/{provider}/current?unite={unite}";
        return execute(() -> restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BillResponse>>() {},
                provider, unite
        ).getBody());
    }

    @Override
    public List<BillInfo> getFacturesByPeriode(LocalDate debut, LocalDate fin) {
        String url = paymentServiceBaseUrl + "/api/bills/periode?debut={debut}&fin={fin}";
        return execute(() -> restTemplate.exchange(
                url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BillResponse>>() {},
                debut.toString(), fin.toString()
        ).getBody());
    }

    @Override
    public List<BillInfo> getFacturesByReferences(String provider, List<String> references) {
        String url = paymentServiceBaseUrl + "/api/bills/by-references";
        return execute(() -> restTemplate.exchange(
                url, HttpMethod.POST,
                new org.springframework.http.HttpEntity<>(new BillsByReferencesRequest(references)),
                new ParameterizedTypeReference<List<BillResponse>>() {}
        ).getBody());
    }

    private List<BillInfo> execute(ExternalCall<List<BillResponse>> call) {
        try {
            List<BillResponse> responses = call.invoke();
            return toInfoList(responses);
        } catch (ResourceAccessException | RestClientResponseException ex) {
            throw new ExternalServiceUnavailableException("payment-service", ex);
        }
    }

    private List<BillInfo> toInfoList(List<BillResponse> responses) {
        if (responses == null) return List.of();
        return responses.stream()
                .map(r -> new BillInfo(r.billReference(), r.provider(), r.subscriberName(),
                        r.amount(), r.paid(), r.billDate()))
                .toList();
    }

    @FunctionalInterface
    private interface ExternalCall<T> {
        T invoke();
    }
}
