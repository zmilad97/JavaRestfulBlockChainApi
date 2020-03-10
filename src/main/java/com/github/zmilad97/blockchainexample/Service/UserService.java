package com.github.zmilad97.blockchainexample.Service;


import com.github.zmilad97.blockchainexample.Module.HiddenTransactions;
import com.github.zmilad97.blockchainexample.Module.Transactions;
import com.github.zmilad97.blockchainexample.Module.User;
import com.github.zmilad97.blockchainexample.Wallet.WalletCore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    public List<WalletCore> walletCoreList = new ArrayList<>();

    public UserService() {
    }

    public List<WalletCore> getWallets() {
        return walletCoreList;
    }


    public boolean validTransaction(HiddenTransactions hiddenTransactions, User user, BlockChainService blockChainService, UserService userService) {

        boolean b = true;

        if (hiddenTransactions.getAmount() < 0)
            b = false;
        if (!(userService.walletCoreList.contains(hiddenTransactions.getDestination())))
            b = false;


        if (!(user.getWallet().contains(hiddenTransactions.getSource())))
            if (!(blockChainService.genesis.equals(hiddenTransactions.getSource())))
                b = false;

            else if (!(blockChainService.genesis.equals(hiddenTransactions.getSource())))
                if (user.getWallet().get(user.getWallet().indexOf(hiddenTransactions.getSource())).getBalance() < hiddenTransactions.getAmount())
                    b = false;

        return b;
    }

    public boolean doTransaction(HiddenTransactions hiddenTransactions, User user, BlockChainService blockChainService, UserService userService) {

        if (validTransaction(hiddenTransactions, user, blockChainService, userService))
            if (!(blockChainService.genesis.equals(hiddenTransactions.getSource()))) {

                user.getWallet().get(user.getWallet().indexOf(hiddenTransactions.getSource())).setBalance(
                        user.getWallet().get(user.getWallet().indexOf(
                                hiddenTransactions.getSource())).getBalance() - hiddenTransactions.getAmount());

                userService.walletCoreList.get(userService.walletCoreList.indexOf(hiddenTransactions.getDestination())).setBalance(
                        userService.walletCoreList.get(userService.walletCoreList.indexOf(
                                hiddenTransactions.getDestination())).getBalance() + hiddenTransactions.getAmount());
                return true;
            } else {
                blockChainService.genesis.setBalance(blockChainService.genesis.getBalance() - hiddenTransactions.getAmount());


                userService.walletCoreList.get(userService.walletCoreList.indexOf(hiddenTransactions.getDestination())).setBalance(
                        userService.walletCoreList.get(userService.walletCoreList.indexOf(hiddenTransactions.getDestination())).getBalance() + hiddenTransactions.getAmount());
                return true;
            }
        return false;

    }


    public WalletCore findWallet(String destination, User user) {
        WalletCore destinationWallet = null;

        for (int i = 0; i < user.getWallet().size(); i++) {

            if (user.getWallet().get(i).getPublicSignature().equals(destination)) {

                destinationWallet = user.getWallet().get(i);
                destinationWallet.setPrivateSignature(user.getWallet().get(i).getPrivateSignature());
                destinationWallet.setPublicSignature(user.getWallet().get(i).getPublicSignature());
                destinationWallet.setBalance(user.getWallet().get(i).getBalance());

                return destinationWallet;
            }
        }

        return destinationWallet;
    }

    public WalletCore findSource(String privateSource, String publicSource, User user) {
        WalletCore sourceWallet = null;

        for (int i = 0; i < user.getWallet().size(); i++) {

            if (user.getWallet().get(i).getPrivateSignature().equals(privateSource)&&user.getWallet().get(i).getPublicSignature().equals(publicSource))

            {
                sourceWallet = user.getWallet().get(i);
                sourceWallet.setPrivateSignature(user.getWallet().get(i).getPrivateSignature());
                sourceWallet.setPublicSignature(user.getWallet().get(i).getPublicSignature());
                sourceWallet.setBalance(user.getWallet().get(i).getBalance());

                return sourceWallet;
            }
        }
        return sourceWallet;
    }

}
