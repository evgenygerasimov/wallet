package wallet.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "wallets")
@Schema(description = "Wallet entity")
public class Wallet {

    @Id
    @GeneratedValue
    @Schema(description = "Wallet ID", example = "123e4567-e89b-12d3-a456-426655440000",
            accessMode = Schema.AccessMode.READ_ONLY )
    private UUID walletId;

    @Column(nullable = false)
    @Schema(description = "Wallet balance", example = "1000", accessMode = Schema.AccessMode.READ_ONLY)
    private long balance = 0;
}
