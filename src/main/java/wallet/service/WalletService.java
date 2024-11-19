package wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wallet.entity.Wallet;
import wallet.exception.InsufficientFundsException;
import wallet.exception.WalletNotFoundException;
import wallet.repository.WalletRepository;

import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Transactional
    public void updateWallet(UUID walletId, String operationType, long amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        synchronized (wallet) {
            if (operationType.equals("DEPOSIT")) {
                wallet.setBalance(wallet.getBalance() + amount);
            } else if (operationType.equals("WITHDRAW")) {
                if (wallet.getBalance() < amount) {
                    throw new InsufficientFundsException("Insufficient funds");
                }
                wallet.setBalance(wallet.getBalance() - amount);
            }
        }

        walletRepository.save(wallet);
    }

    public long getBalance(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"))
                .getBalance();
    }

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        walletRepository.save(wallet);
        return wallet;
    }
}