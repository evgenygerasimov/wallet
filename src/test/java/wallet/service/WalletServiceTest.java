package wallet.service;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import wallet.entity.Wallet;
import wallet.exception.InsufficientFundsException;
import wallet.exception.WalletNotFoundException;
import wallet.repository.WalletRepository;

public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    private final UUID walletId = UUID.randomUUID();
    private Wallet wallet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        wallet = new Wallet();
        wallet.setWalletId(walletId);
        wallet.setBalance(1000L);
    }

    @Test
    public void shouldUpdateWalletDepositTest() {
        //setup
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        //execute
        walletService.updateWallet(walletId, "DEPOSIT", 500L);
        //assert
        assertEquals(1500L, wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void shouldUpdateWalletWithdrawTest() {
        //setup
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        //execute
        walletService.updateWallet(walletId, "WITHDRAW", 500L);
        //assert
        assertEquals(500L, wallet.getBalance());
        verify(walletRepository, times(1)).save(wallet);
    }

    @Test
    public void shouldThrowExceptionWhenWithdrawMoreWalletAmountTest() {
        //setup
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        //execute and assert
        assertThrows(InsufficientFundsException.class, () -> {
            walletService.updateWallet(walletId, "WITHDRAW", 1500L);
        });
        verify(walletRepository, times(0)).save(wallet);
    }

    @Test
    public void shouldThrowExceptionWhenUpdateWalletAndWalletNotFoundTest() {
        //setup
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        //execute and assert
        assertThrows(WalletNotFoundException.class, () -> {
            walletService.updateWallet(walletId, "DEPOSIT", 500L);
        });
        verify(walletRepository, times(0)).save(any(Wallet.class));
    }

    @Test
    public void shouldReturnBalanceWalletTest() {
        //setup
        when(walletRepository.findById(walletId)).thenReturn(Optional.of(wallet));
        //execute
        long balance = walletService.getBalance(walletId);
        //assert
        assertEquals(1000L, balance);
    }

    @Test
    public void shouldThrowExceptionWhenGetBalanceWalletAndWalletNotFoundTest() {
        //setup
        when(walletRepository.findById(walletId)).thenReturn(Optional.empty());
        //execute and assert
        assertThrows(WalletNotFoundException.class, () -> {
            walletService.getBalance(walletId);
        });
    }

    @Test
    public void shouldSaveNewWalletInDbTest() {
        //setup
        Wallet wallet = new Wallet();
        wallet.setBalance(0);
        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);
        //execute
        Wallet createdWallet = walletService.createWallet();
        //assert
        assertEquals(0, createdWallet.getBalance());
        verify(walletRepository).save(any(Wallet.class));
    }
}
