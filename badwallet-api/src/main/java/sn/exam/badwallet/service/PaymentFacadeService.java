package sn.exam.badwallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.client.FactureServiceClient;
import sn.exam.badwallet.dto.BillInfo;
import sn.exam.badwallet.dto.PayBillsByReferencesRequest;
import sn.exam.badwallet.dto.PayCurrentBillRequest;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.InvalidTransactionException;
import sn.exam.badwallet.exception.WalletNotFoundException;
import sn.exam.badwallet.repository.WalletRepository;


import java.util.List;

@Service
public class PaymentFacadeService {

    private final WalletRepository walletRepository;
    private final FactureServiceClient factureServiceClient;
    private final BillPaymentService billPaymentService;

    public PaymentFacadeService(WalletRepository walletRepository,
                                 FactureServiceClient factureServiceClient,
                                 BillPaymentService billPaymentService) {
        this.walletRepository = walletRepository;
        this.factureServiceClient = factureServiceClient;
        this.billPaymentService = billPaymentService;
    }

    @Transactional
    public List<TransactionResponse> payCurrentBills(PayCurrentBillRequest request) {
        Wallet wallet = resolveWallet(request.phoneNumber());
        List<BillInfo> bills = factureServiceClient.getCurrentFactures(request.serviceName());
        if (bills.isEmpty()) {
            throw new InvalidTransactionException("No unpaid bills found for service: " + request.serviceName());
        }
        return bills.stream()
                .filter(bill -> !bill.paid())
                .map(bill -> billPaymentService.payBill(wallet, bill.amount(), bill.reference()))
                .toList();
    }

    @Transactional
    public List<TransactionResponse> payBillsByReferences(PayBillsByReferencesRequest request) {
        Wallet wallet = resolveWallet(request.phoneNumber());
        List<BillInfo> bills = factureServiceClient.getFacturesByReferences(
                request.serviceName(), request.factureReferences());
        if (bills.isEmpty()) {
            throw new InvalidTransactionException("No bills found for provided references");
        }
        return bills.stream()
                .filter(bill -> !bill.paid())
                .map(bill -> billPaymentService.payBill(wallet, bill.amount(), bill.reference()))
                .toList();
    }

    private Wallet resolveWallet(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new WalletNotFoundException(phoneNumber));
    }
}
