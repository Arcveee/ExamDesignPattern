package sn.exam.badwallet.mapper;

import org.mapstruct.Mapper;
import sn.exam.badwallet.dto.WalletResponse;
import sn.exam.badwallet.entity.Wallet;

@Mapper(componentModel = "spring")
public interface WalletMapper {

    WalletResponse toResponse(Wallet wallet);
}
