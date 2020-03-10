package com.github.zmilad97.blockchainexample.Controller;

import com.github.zmilad97.blockchainexample.Module.Block;
import com.github.zmilad97.blockchainexample.Module.HiddenTransactions;
import com.github.zmilad97.blockchainexample.Module.User;
import com.github.zmilad97.blockchainexample.Service.BlockChainService;
import com.github.zmilad97.blockchainexample.Module.Transactions;
import com.github.zmilad97.blockchainexample.Service.StringToTransaction;
import com.github.zmilad97.blockchainexample.Service.UserService;
import com.github.zmilad97.blockchainexample.Wallet.WalletCore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BlockChainController {

    BlockChainService blockChainService;

    UserService userService;
    User user;

    @Autowired
    public BlockChainController(BlockChainService blockChainService, UserService userService) {
        this.blockChainService = blockChainService;
        this.userService = userService;
        user = new User(userService);
        this.userService.walletCoreList.add(blockChainService.genesis);

        blockChainService.genesisTransaction(user, blockChainService);

    }


    @GetMapping("/chain")
    public List<Block> showChain() {
        try {

            List<Block> chain = blockChainService.findAll();
            return chain;
        } catch (NullPointerException e) {
            System.out.println("Error nullPointer");
        }
        return null;
    }

    @GetMapping("/lastBlock")
    public Block lastBlock() {
        return blockChainService.lastBlock();
    }


    @RequestMapping(value = "/trx/add", method = RequestMethod.POST)
    public Object newTrx(@RequestBody StringToTransaction transactions) {

        StringToTransaction stt = new StringToTransaction(transactions.getPrivateSource(), transactions.getPublicSource(), transactions.getDestination(), transactions.getAmount());
        Map response = new HashMap();
        try {
            Transactions trx = new Transactions(stt.convertToTransaction(stt.getPublicSource(), stt.getDestination(), stt.getAmount()));
            HiddenTransactions hrx = new HiddenTransactions();


            hrx.setId(stt.getTransactionId());
            hrx.setAmount(stt.getAmount());
            hrx.setSource(userService.findSource(stt.getPrivateSource(), stt.getPublicSource(), user));
            hrx.setDestination(userService.findWallet(stt.getDestination(), user));




            if ((userService.validTransaction(hrx, user, blockChainService, userService))) {
                blockChainService.addTransaction(trx, hrx);
                response.put("The transaction has been added to block", trx);
                stt.convertToHiddenTransactions(transactions.getTransactionId(), transactions.getPrivateSource(), transactions.getPublicSource(), transactions.getDestination(), transactions.getAmount(), userService, user);
                return response;
            }
            response.put("The transaction is invalid 1", null);
            return response;
        } catch (NullPointerException e) {
            response.put("The transaction is invalid 2", null);
            return response;
        }
//        Transactions trx = new Transactions(transactions.getSource(), transactions.getDestination(), transactions.getAmount(), user, userService);

    }

    @GetMapping("/trx/current")
    public List<Transactions> currentTransactions() {
        return blockChainService.showCurrentTransaction();
    }

    @GetMapping("/mine")
    public Object mineBlock() {
        Map jsonResponse = new HashMap();

        Block newBlock = new Block(blockChainService.chain.size(), new java.util.Date(), blockChainService.currentTransactions);

        newBlock.setTransactions(blockChainService.currentTransactions);
        blockChainService.addBlock(newBlock, user, blockChainService, userService);
        jsonResponse.put("message", "the Block has been added to chain");
        jsonResponse.put("Block details",newBlock);

        return jsonResponse;

    }

    @GetMapping("/pow")
    public String pow() {
        return blockChainService.proofOfWork();
    }

    @GetMapping("/block/{index}")
    public Block findBlockByIndex(@PathVariable int index) {
        return blockChainService.findBlockByIndex(index);
    }

    @GetMapping("/user")
    public User user() {
        return user;
    }

    @RequestMapping(value = "/wallet/new", method = RequestMethod.POST)
    public void newWallet(@RequestBody WalletCore walletCore) {
        walletCore.setBalance(0);
        user.addWallet(walletCore);
        userService.walletCoreList.add(walletCore);
    }

    @GetMapping("/wallet/show")
    public List<WalletCore> showUserWallets() {
        return user.getWallet();
    }

}
