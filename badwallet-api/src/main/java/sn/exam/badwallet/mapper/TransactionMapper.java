package sn.exam.badwallet.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "sourceWallet.id", target = "sourceWalletId")
    @Mapping(source = "targetWallet.id", target = "targetWalletId")
    TransactionResponse toResponse(Transaction transaction);
}
