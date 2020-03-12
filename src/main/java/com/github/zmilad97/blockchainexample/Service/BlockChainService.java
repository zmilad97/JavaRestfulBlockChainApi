package com.github.zmilad97.blockchainexample.Service;


import com.github.zmilad97.blockchainexample.Module.Block;
import com.github.zmilad97.blockchainexample.Module.HiddenTransactions;
import com.github.zmilad97.blockchainexample.Module.Transactions;
import com.github.zmilad97.blockchainexample.Module.User;
import com.github.zmilad97.blockchainexample.Wallet.WalletCore;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class BlockChainService {

    public List<Block> chain;
    public List<Transactions> currentTransactions;
    public List<HiddenTransactions> currentHiddenTransactions;
    public WalletCore genesis = new WalletCore();
    Transactions trx = new Transactions();
    HiddenTransactions hiddenTransactions = new HiddenTransactions();
    String condition = "ab";
    char conditionChar = 98;

    public BlockChainService() {

        currentTransactions = new ArrayList<>();
        currentHiddenTransactions = new ArrayList<>();
        chain = new ArrayList<>();
        chain.add(generateGenesis());
        genesis.setBalance(21000000);

    }

    private Block generateGenesis() {


        Block genesis = new Block(0, new java.util.Date(), null);
        genesis.setPreviousHash(null);

        return genesis;
    }

    public void rewardTransaction(User user, BlockChainService blockChainService) {


        trx.setAmount(50);

        trx.setSource(genesis.getPublicSignature());

        trx.setDestination(user.getWallet().get(0).getPublicSignature());

        hiddenTransactions.setAmount(50);
        hiddenTransactions.setSource(genesis);
        hiddenTransactions.setDestination(user.getWallet().get(0));

        blockChainService.currentHiddenTransactions.add(hiddenTransactions);
        blockChainService.currentTransactions.add(trx);


    }

    public Block findBlockByIndex(int index) {
        return chain.get(index);
    }

    public void addBlock(Block block, User user, BlockChainService blockChainService, UserService userService) {
        Block newBlock = block;
        newBlock.setPreviousHash(chain.get(chain.size() - 1).getHash());
        if (chain.size()%5==0)
            condition += ++conditionChar;
        newBlock.setHash(newBlock.computeHash(condition));


        for (HiddenTransactions hrx : blockChainService.currentHiddenTransactions)
            if (!(userService.doTransaction(hrx, user, blockChainService, userService))) {
                blockChainService.currentHiddenTransactions.remove(hrx);
                blockChainService.currentTransactions.remove(currentTransactions.get(currentTransactions.indexOf(currentTransactions.contains(hrx.getId()))));
            }
        blockChainService.chain.add(newBlock);

    }

    public String proofOfWork() {
        int i = chain.size();
        boolean b = false;
        while (i > 0) {
            String pow = "" + findBlockByIndex(i - 1).getNonce() + findBlockByIndex(i - 1).getIndex() + findBlockByIndex(i - 1).getDate() + findBlockByIndex(i - 1).getPreviousHash() + findBlockByIndex(i - 1).getTransactions();
            Cryptography cryptography = new Cryptography();

            try {
                String powHash = cryptography.toHexString(cryptography.getSha(pow));
                if (powHash.equals(findBlockByIndex(i - 1).getHash()))
                    b = true;
                else {

                    b = false;
                    break;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            i--;
        }
        if (b)
            return "the chain is valid";
        else
            return "the chain is not valid the " + i + " Block has problem";
    }





    public List<Block> findAll() {
        return chain;
    }


    public Block lastBlock() {
        return chain.get(chain.size() - 1);
    }

    public List<Transactions> showCurrentTransaction() {

        return currentTransactions;

    }

    public void addTransaction(Transactions transactions, HiddenTransactions hiddenTransactions) {
        currentHiddenTransactions.add(hiddenTransactions);
        currentTransactions.add(transactions);
    }


}
