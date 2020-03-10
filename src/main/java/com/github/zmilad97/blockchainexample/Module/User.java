package com.github.zmilad97.blockchainexample.Module;


import com.github.zmilad97.blockchainexample.Service.BlockChainService;
import com.github.zmilad97.blockchainexample.Service.IdSetter;
import com.github.zmilad97.blockchainexample.Service.UserService;
import com.github.zmilad97.blockchainexample.Wallet.WalletCore;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String id;
    List<WalletCore> wallet = new ArrayList<>();
    IdSetter idSetter = new IdSetter();
    WalletCore walletCore = new WalletCore();

    public User(UserService userService) {

        wallet.add(walletCore);
        this.id = idSetter.idHash();
        userService.walletCoreList.add(walletCore);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<WalletCore> getWallet() {

        return wallet;
    }

    public void addWallet(WalletCore wallet) {
        this.wallet.add(wallet);
    }


}
