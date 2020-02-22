package com.github.zmilad97.blockchainexample.Controller;

import com.github.zmilad97.blockchainexample.Module.Block;
import com.github.zmilad97.blockchainexample.Service.BlockChainService;
import com.github.zmilad97.blockchainexample.Module.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class BlockChainController {

    BlockChainService blockChainService ;

    @Autowired
    public BlockChainController(BlockChainService blockChainService) {
        this.blockChainService = blockChainService;
    }

    @GetMapping("/chain")
    public List<Block> showChain(){
       try{

        List<Block> chain = blockChainService.findAll();
       return chain;}
       catch (NullPointerException e){
           System.out.println("Error nullPointer");}
        return null;
    }

    @GetMapping("/lastBlock")
    public Block lastBlock(){
        return blockChainService.lastBlock();
    }


    @RequestMapping(value = "/trx/add" , method = RequestMethod.POST)
    public void newTrx(@RequestBody Transactions transactions){
        blockChainService.addTransaction(transactions);
    }

    @GetMapping("/trx/current")
    public List<Transactions> currentTransactions(){
        return blockChainService.showCurrentTransaction();
    }

    @GetMapping("/mine")
    public Object mineBlock(){
        Map jsonResponse = new HashMap();
        Block newBlock = new Block(blockChainService.chain.size(),new java.util.Date(),blockChainService.showCurrentTransaction());
        blockChainService.addBlock(newBlock);
        jsonResponse.put("message" , "the Block has been added to chain");
        jsonResponse.put("Block details" , newBlock);
        return jsonResponse;

    }

    @GetMapping("/pow")
    public String pow() {
        return blockChainService.proofOfWork();
    }

    @GetMapping("/block/{index}")
    public Block findBlockByIndex(@PathVariable int index){
        return blockChainService.findBlockByIndex(index);
    }

}
