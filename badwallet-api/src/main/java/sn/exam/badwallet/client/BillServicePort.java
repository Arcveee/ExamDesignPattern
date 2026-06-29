package sn.exam.badwallet.client;

import sn.exam.shared.dto.BillResponse;

public interface BillServicePort {
    BillResponse fetchBill(String provider, String billReference);
}
