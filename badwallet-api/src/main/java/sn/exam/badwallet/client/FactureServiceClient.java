package sn.exam.badwallet.client;

import sn.exam.badwallet.dto.BillInfo;

import java.util.List;

public interface FactureServiceClient {
    List<BillInfo> getCurrentFactures(String provider);
    List<BillInfo> getFacturesByReferences(String provider, List<String> references);
}
