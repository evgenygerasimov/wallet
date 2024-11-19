package wallet.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Schema(description = "Request to perform a deposit or withdraw operation on a wallet")
public class WalletRequest {

    @JsonProperty("walletId")
    @Schema(description = "The unique identifier of the wallet to perform the operation on",
            example = "123e4567-e89b-12d3-a456-426655440000", accessMode = Schema.AccessMode.READ_WRITE)
    private UUID walletId;

    @JsonProperty("operationType")
    @Schema(description = "The type of operation to perform on the wallet",
            example = "DEPOSIT", accessMode = Schema.AccessMode.READ_WRITE)
    private String operationType; // DEPOSIT or WITHDRAW

    @JsonProperty("amount")
    @Schema(description = "The amount of money to deposit or withdraw from the wallet",
            example = "1000", accessMode = Schema.AccessMode.READ_WRITE)
    private long amount;
}