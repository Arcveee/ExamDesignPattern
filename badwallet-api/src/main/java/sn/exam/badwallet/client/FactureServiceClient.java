package sn.exam.badwallet.client;

import sn.exam.badwallet.dto.BillInfo;

import java.time.LocalDate;
import java.util.List;

public interface FactureServiceClient {
    List<BillInfo> getCurrentFactures(String provider);
    List<BillInfo> getCurrentFacturesByUnite(String provider, String unite);
    List<BillInfo> getFacturesByPeriode(LocalDate debut, LocalDate fin);
    List<BillInfo> getFacturesByReferences(String provider, List<String> references);
}
