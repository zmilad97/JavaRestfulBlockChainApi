package com.github.zmilad97.blockchainexample.Service;


import com.github.zmilad97.blockchainexample.Module.Block;
import com.github.zmilad97.blockchainexample.Module.Transactions;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class BlockChainService {

     public List<Block> chain;
     public List<Transactions> currentTransactions ;
     Transactions trx = new Transactions("0","milad",50);


     public BlockChainService() {
          currentTransactions =  new ArrayList<>();
          chain = new ArrayList<>();
          chain.add(generateGenesis());
     }

     private Block generateGenesis() {

          System.out.println(currentTransactions);
          Block genesis = new Block(0,new java.util.Date(), Arrays.asList(trx) );
          genesis.setPreviousHash(null);

          return genesis;
     }

     public Block findBlockByIndex(int index){
          return chain.get(index);
     }

     public void addBlock (Block block){
          Block newBlock= block;
          newBlock.setPreviousHash(chain.get(chain.size()-1).getHash());
          newBlock.setHash(newBlock.computeHash());
          chain.add(newBlock);
     }

     public String proofOfWork(){
          int i = chain.size();
          boolean b = false;
          while (i > 0) {
               String pow = Integer.toString(findBlockByIndex(i-1).getNonce()) +findBlockByIndex(i-1).getIndex() + findBlockByIndex(i-1).getDate() +findBlockByIndex(i-1).getPreviousHash() + findBlockByIndex(i-1).getTransactions();
               Cryptography cryptography = new Cryptography();

               try {
                    String powHash = cryptography.toHexString(cryptography.getSha(pow));
                    if (powHash.equals(findBlockByIndex(i-1).getHash()))
                         b = true;
                    else{

                         b = false;
                         break;
                    }
               } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
               }
               i--;
          }
          if (b == true)
               return "the chain is valid";
          else
               return "the chain is not valid the "+i+" Block has problem";
     }


     public List<Block> findAll(){
          return chain;
     }


     public Block lastBlock(){
          return chain.get(chain.size()-1);
     }

     public List<Transactions> showCurrentTransaction(){
          return currentTransactions;
     }

     public void addTransaction(Transactions transactions){
               currentTransactions.add(transactions);
     }




}
