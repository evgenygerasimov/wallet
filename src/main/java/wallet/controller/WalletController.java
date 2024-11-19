package wallet.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wallet.entity.Wallet;
import wallet.entity.WalletRequest;
import wallet.service.WalletService;

import java.util.UUID;

@Tag(name = "WalletService", description = "Operations related to wallet")
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Operation(summary = "Create a transaction in the wallet",
            description = "This endpoint is used to create a transaction in the wallet. " +
                    "The request body should contain the walletId, operationType and amount. " +
                    "The response will contain a message indicating whether the operation was successful or not. " +
                    "If successful, the balance of the wallet will be updated." +
                    "If not successful, an error message will be returned. " +
                    "The request should be in the json format and should contain the following fields: " +
                    "{\"walletId\": \"550e8400-e29b-41d4-a716-446655440000\"," +
                    "\"operationType\": \"DEPOSIT or WITHDRAW\"," +
                    "\"amount\": \"200\"" +
                    "}")
    @PostMapping
    public ResponseEntity<String> updateWallet(
            @Valid @RequestBody @Parameter(description = "Wallet request object") WalletRequest walletRequest) {
        try {
            walletService.updateWallet(walletRequest.getWalletId(), walletRequest.getOperationType(), walletRequest.getAmount());
            return ResponseEntity.ok("Operation successful");
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Get the balance of a wallet", description = "This endpoint is used to get the balance of a wallet. " +
            "The walletId should be passed as a path parameter.")
    @GetMapping("/{walletId}")
    public ResponseEntity<Long> getBalance(@PathVariable("walletId") @Parameter(description = "Wallet Id") UUID walletId) {
        long balance = walletService.getBalance(walletId);
        return ResponseEntity.ok(balance);
    }

    @Operation(summary = "Create a new wallet", description = "This is a service endpoint. It is used to create a new wallet. ")
    @PostMapping("/addNewWallet")
    public ResponseEntity<Wallet> addNewWallet() {
        return ResponseEntity.ok(walletService.createWallet());
    }
}