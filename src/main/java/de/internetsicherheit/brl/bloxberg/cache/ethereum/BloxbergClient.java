package de.internetsicherheit.brl.bloxberg.cache.ethereum;

import org.rnorth.ducttape.TimeoutException;
import org.rnorth.ducttape.unreliables.Unreliables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class BloxbergClient {

    private final Web3j web3j;

    private final Map<String, TransactionAddress.Type> addressTypeCache = new HashMap<>();

    private static Logger logger = LoggerFactory.getLogger(BloxbergClient.class);

    /**
     * the bloxbergclient. any other blockchain can be used aswell
     *
     * @param networkUrl the url of the blockchain
     */
    public BloxbergClient(String networkUrl) {
        web3j = Web3j.build(new HttpService(networkUrl));
    }

    public BigInteger getCurrentBlockNumber() throws IOException {
        EthBlockNumber blockNumber = web3j.ethBlockNumber().send();
        return blockNumber.getBlockNumber();

    }

    /**
     * sends a request to the blockchain to extract the number of transactions in a single block.
     * this method is quite timeconsuming.
     *
     * @param block the blocknumber
     * @return the transactioncount
     * @throws IOException connection to client lost/cannot be established
     */
    public int getNumberOfTransactionsInBlock(BigInteger block) throws IOException {
        Request<?, EthGetBlockTransactionCountByNumber> request = web3j.ethGetBlockTransactionCountByNumber(DefaultBlockParameter.valueOf(block));
        EthGetBlockTransactionCountByNumber transactionCountByNumber = request.send();
        return transactionCountByNumber.getTransactionCount().intValue();
    }

    public Block getBlock(BigInteger block) throws IOException {
        EthBlock rawBlock = this.fetchEthBlock(block);
        List<BlockTransaction> transactions = convertToBlockTransactions(rawBlock);
        return new Block(rawBlock.getBlock(), transactions);
    }

    private List<BlockTransaction> convertToBlockTransactions(EthBlock rawBlock) {
        return rawBlock.getBlock().getTransactions().stream()
                .map(t -> (Transaction) t)
                .map(this::convertToBlockTransaction)
                .collect(Collectors.toList());
    }

    private BlockTransaction convertToBlockTransaction(Transaction t) {
        TransactionAddress from = new TransactionAddress(t.getFrom(),
                getAddressTypeFromCache(t.getFrom()));
        TransactionAddress to = new TransactionAddress(t.getTo(),
                getAddressTypeFromCache(t.getTo()));

        return new BlockTransaction(from, to);
    }

    private TransactionAddress.Type getAddressTypeFromCache(String address) {
        return addressTypeCache.computeIfAbsent(address, this::mapToType);
    }

    private EthBlock fetchEthBlock(BigInteger block) throws IOException {
        return web3j.ethGetBlockByNumber(DefaultBlockParameter.valueOf(block), true).send();
    }

    private boolean isSmartContract(String address) {
        EthGetCode addressCodeRequest;
        try {
            addressCodeRequest = Unreliables.retryUntilSuccess(30, TimeUnit.SECONDS,
                    () -> web3j.ethGetCode(address, DefaultBlockParameter.valueOf("latest")).send()
            );
            return !addressCodeRequest.getResult().equals("0x");
        } catch (TimeoutException e) {
            logger.error("Can't get code for SC, better to give up then to get wrong data ¯\\_(ツ)_/¯", e);
            throw new RuntimeException(e);
        }

    }

    private TransactionAddress.Type mapToType(String address) {
        if (address == null) {
            return TransactionAddress.Type.NONE;
        } else if (isSmartContract(address)) {
            return TransactionAddress.Type.SMARTCONTRACT;
        } else {
            return TransactionAddress.Type.USERORVALIDATOR;
        }
    }

}

