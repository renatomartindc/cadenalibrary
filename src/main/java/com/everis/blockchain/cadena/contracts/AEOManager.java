package com.everis.blockchain.cadena.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import com.everis.blockchain.cadena.lib.ContractSimple;
import com.everis.blockchain.cadena.lib.Web3;

import io.reactivex.Flowable;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class AEOManager extends ContractSimple{
    private static final String BINARY = "60c0604052600660808190527f524f4c455f31000000000000000000000000000000000000000000000000000060a0908152620000409160009190620001bc565b506040805180820190915260068082527f524f4c455f32000000000000000000000000000000000000000000000000000060209092019182526200008791600191620001bc565b506040805180820190915260068082527f524f4c455f3300000000000000000000000000000000000000000000000000006020909201918252620000ce91600291620001bc565b50348015620000dc57600080fd5b50604051604080620014b8833981016040818152825160209384015160058054600160a060020a031916600160a060020a038085169190911790915581166000908152600390955291842084549194929360019391928190839060026101008289161502600019019091160480156200018f5780601f106200016c5761010080835404028352918201916200018f565b820191906000526020600020905b8154815290600101906020018083116200017a575b50509283525050604051908190036020019020805491151560ff1990921691909117905550620002619050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10620001ff57805160ff19168380011785556200022f565b828001600101855582156200022f579182015b828111156200022f57825182559160200191906001019062000212565b506200023d92915062000241565b5090565b6200025e91905b808211156200023d576000815560010162000248565b90565b61124780620002716000396000f3006080604052600436106100825763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416631bfe03088114610087578063217fe6c6146100f05780632a8dfab31461016b57806340a55d4514610198578063759e7c98146101c25780637d72aa65146101da578063939e2d0414610241575b600080fd5b34801561009357600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100ee958335600160a060020a03169536956044949193909101919081908401838280828437509497506102599650505050505050565b005b3480156100fc57600080fd5b5060408051602060046024803582810135601f8101859004850286018501909652858552610157958335600160a060020a03169536956044949193909101919081908401838280828437509497506105c39650505050505050565b604080519115158252519081900360200190f35b34801561017757600080fd5b506100ee600435602435600160a060020a0360443581169060643516610646565b3480156101a457600080fd5b506100ee600435600160a060020a03602435811690604435166109a7565b3480156101ce57600080fd5b506100ee600435610c9a565b3480156101e657600080fd5b5060408051602060046024803582810135601f81018590048502860185019096528585526100ee958335600160a060020a0316953695604494919390910191908190840183828082843750949750610eb29650505050505050565b34801561024d57600080fd5b506101576004356111e6565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102df5780601f106102b4576101008083540402835291602001916102df565b820191906000526020600020905b8154815290600101906020018083116102c257829003601f168201915b50505050506003600033600160a060020a0316600160a060020a03168152602001908152602001600020816040518082805190602001908083835b602083106103395780518252601f19909201916020918201910161031a565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff16151591506103b09050576040805160e560020a62461bcd02815260206004820152601560248201526000805160206111fc833981519152604482015290519081900360640190fd5b6003600084600160a060020a0316600160a060020a03168152602001908152602001600020826040518082805190602001908083835b602083106104055780518252601f1990920191602091820191016103e6565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff161515915061048e9050576040805160e560020a62461bcd02815260206004820152601460248201527f726f6c6520616c72656164792072656d6f766564000000000000000000000000604482015290519081900360640190fd5b600160a060020a0383166000908152600360209081526040808320905185519192869282918401908083835b602083106104d95780518252601f1990920191602091820191016104ba565b51815160209384036101000a6000190180199092169116179052920194855250604080519485900382018520805460ff191696151596909617909555600160a060020a038816845283810185815287519585019590955286517fd211483f91fc6eff862467f8de606587a30c8fc9981056f051b897a418df803a95899589955093509160608401919085019080838360005b8381101561058357818101518382015260200161056b565b50505050905090810190601f1680156105b05780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1505050565b600160a060020a0382166000908152600360209081526040808320905184519192859282918401908083835b6020831061060e5780518252601f1990920191602091820191016105ef565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff1695945050505050565b6002805460408051602060018416156101000260001901909316849004601f810184900484028201840190925281815292918301828280156106c95780601f1061069e576101008083540402835291602001916106c9565b820191906000526020600020905b8154815290600101906020018083116106ac57829003601f168201915b50505050506003600033600160a060020a0316600160a060020a03168152602001908152602001600020816040518082805190602001908083835b602083106107235780518252601f199092019160209182019101610704565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff161515915061079a9050576040805160e560020a62461bcd02815260206004820152601560248201526000805160206111fc833981519152604482015290519081900360640190fd5b60008581526004602052604090205460ff161515610802576040805160e560020a62461bcd02815260206004820152601260248201527f41454f206e6f7420726567697374657265640000000000000000000000000000604482015290519081900360640190fd5b6005546040805160248082018990526044808301899052835180840382018152606493840185526020810180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167f382262fc0000000000000000000000000000000000000000000000000000000017815294517f73b40a5c000000000000000000000000000000000000000000000000000000008152600160a060020a03968716600482018181528a89169583019590955260009382018490526080958201958652825160848301528251978b16976373b40a5c9791968b9693909260a490910191808383895b838110156108ff5781810151838201526020016108e7565b50505050905090810190601f16801561092c5780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b15801561094e57600080fd5b505af1158015610962573d6000803e3d6000fd5b50506040805188815233602082015281517f6e0ae1afa4f33ad7db8e7ba62ead03c847974fc6ae37ce0134888e25b2fc6ef49450908190039091019150a15050505050565b6002805460408051602060018416156101000260001901909316849004601f81018490048402820184019092528181529291830182828015610a2a5780601f106109ff57610100808354040283529160200191610a2a565b820191906000526020600020905b815481529060010190602001808311610a0d57829003601f168201915b50505050506003600033600160a060020a0316600160a060020a03168152602001908152602001600020816040518082805190602001908083835b60208310610a845780518252601f199092019160209182019101610a65565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff1615159150610afb9050576040805160e560020a62461bcd02815260206004820152601560248201526000805160206111fc833981519152604482015290519081900360640190fd5b600554604080516024808201889052825180830382018152604492830184526020810180517bffffffffffffffffffffffffffffffffffffffffffffffffffffffff167fb75c7dc60000000000000000000000000000000000000000000000000000000017815293517f73b40a5c000000000000000000000000000000000000000000000000000000008152600160a060020a0395861660048201818152898816948301949094526000948201859052608060648301908152835160848401528351978b16976373b40a5c9792968b96909594909360a490910191808383895b83811015610bf3578181015183820152602001610bdb565b50505050905090810190601f168015610c205780820380516001836020036101000a031916815260200191505b5095505050505050600060405180830381600087803b158015610c4257600080fd5b505af1158015610c56573d6000803e3d6000fd5b50506040805187815233602082015281517f71f95262377fe41184d9682b9791d48c4a5fc89ca3c1fd9ce3031b6a8c1d80e49450908190039091019150a150505050565b60018054604080516020600284861615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610d1f5780601f10610cf457610100808354040283529160200191610d1f565b820191906000526020600020905b815481529060010190602001808311610d0257829003601f168201915b50505050506003600033600160a060020a0316600160a060020a03168152602001908152602001600020816040518082805190602001908083835b60208310610d795780518252601f199092019160209182019101610d5a565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff1615159150610df09050576040805160e560020a62461bcd02815260206004820152601560248201526000805160206111fc833981519152604482015290519081900360640190fd5b60008281526004602052604090205460ff1615610e57576040805160e560020a62461bcd02815260206004820152601660248201527f41454f20616c7265616479207265676973746572656400000000000000000000604482015290519081900360640190fd5b600082815260046020908152604091829020805460ff191660011790558151848152339181019190915281517f40219468246a97885c6991fe9a0abb13aeca76e827d965448cbc1b2713557ae1929181900390910190a15050565b6000805460408051602060026001851615610100026000190190941693909304601f81018490048402820184019092528181529291830182828015610f385780601f10610f0d57610100808354040283529160200191610f38565b820191906000526020600020905b815481529060010190602001808311610f1b57829003601f168201915b50505050506003600033600160a060020a0316600160a060020a03168152602001908152602001600020816040518082805190602001908083835b60208310610f925780518252601f199092019160209182019101610f73565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff16151591506110099050576040805160e560020a62461bcd02815260206004820152601560248201526000805160206111fc833981519152604482015290519081900360640190fd5b6003600084600160a060020a0316600160a060020a03168152602001908152602001600020826040518082805190602001908083835b6020831061105e5780518252601f19909201916020918201910161103f565b51815160209384036101000a600019018019909216911617905292019485525060405193849003019092205460ff161591506110e69050576040805160e560020a62461bcd02815260206004820152601260248201527f726f6c6520616c72656164792061646465640000000000000000000000000000604482015290519081900360640190fd5b60016003600085600160a060020a0316600160a060020a03168152602001908152602001600020836040518082805190602001908083835b6020831061113d5780518252601f19909201916020918201910161111e565b51815160209384036101000a6000190180199092169116179052920194855250604080519485900382018520805460ff191696151596909617909555600160a060020a038816845283810185815287519585019590955286517fbfec83d64eaa953f2708271a023ab9ee82057f8f3578d548c1a4ba0b5b70048995899589955093509160608401919085019080838360008381101561058357818101518382015260200161056b565b60046020526000908152604090205460ff16815600696e76616c696420726f6c6520666f7220757365720000000000000000000000a165627a7a723058204c3d312d9fff09cf6e760db6085ffabc43a63ffc593e6e1a21c8e51e0ecd51960029";

    public static final String FUNC_REMOVEROLE = "removeRole";

    public static final String FUNC_HASROLE = "hasRole";

    public static final String FUNC_APPROVEAEO = "approveAEO";

    public static final String FUNC_REVOKEAEO = "revokeAEO";

    public static final String FUNC_REGISTERAEO = "registerAEO";

    public static final String FUNC_ADDROLE = "addRole";

    public static final String FUNC_REGISTRYAEO = "registryAEO";

    public static final Event REGISTEREDAEO_EVENT = new Event("RegisteredAEO", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event APPROVEDAEO_EVENT = new Event("ApprovedAEO", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event REVOKEDAEO_EVENT = new Event("RevokedAEO", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    public static final Event ROLEADDED_EVENT = new Event("RoleAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    public static final Event ROLEREMOVED_EVENT = new Event("RoleRemoved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected AEOManager(String contractAddress, Web3 web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> removeRole(String user, String role) {
        final Function function = new Function(
                FUNC_REMOVEROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> hasRole(String user, String role) {
        final Function function = new Function(FUNC_HASROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approveAEO(byte[] hash, BigInteger validDays, String identityManager, String verificationRegistry) {
        final Function function = new Function(
                FUNC_APPROVEAEO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash), 
                new org.web3j.abi.datatypes.generated.Uint256(validDays), 
                new org.web3j.abi.datatypes.Address(160, identityManager), 
                new org.web3j.abi.datatypes.Address(160, verificationRegistry)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeAEO(byte[] hash, String identityManager, String verificationRegistry) {
        final Function function = new Function(
                FUNC_REVOKEAEO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash), 
                new org.web3j.abi.datatypes.Address(160, identityManager), 
                new org.web3j.abi.datatypes.Address(160, verificationRegistry)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerAEO(byte[] hash) {
        final Function function = new Function(
                FUNC_REGISTERAEO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(hash)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addRole(String user, String role) {
        final Function function = new Function(
                FUNC_ADDROLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, user), 
                new org.web3j.abi.datatypes.Utf8String(role)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> registryAEO(byte[] param0) {
        final Function function = new Function(FUNC_REGISTRYAEO, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public List<RegisteredAEOEventResponse> getRegisteredAEOEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REGISTEREDAEO_EVENT, transactionReceipt);
        ArrayList<RegisteredAEOEventResponse> responses = new ArrayList<RegisteredAEOEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RegisteredAEOEventResponse typedResponse = new RegisteredAEOEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RegisteredAEOEventResponse> registeredAEOEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RegisteredAEOEventResponse>() {
            @Override
            public RegisteredAEOEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REGISTEREDAEO_EVENT, log);
                RegisteredAEOEventResponse typedResponse = new RegisteredAEOEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RegisteredAEOEventResponse> registeredAEOEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REGISTEREDAEO_EVENT));
        return registeredAEOEventFlowable(filter);
    }

    public List<ApprovedAEOEventResponse> getApprovedAEOEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVEDAEO_EVENT, transactionReceipt);
        ArrayList<ApprovedAEOEventResponse> responses = new ArrayList<ApprovedAEOEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovedAEOEventResponse typedResponse = new ApprovedAEOEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovedAEOEventResponse> approvedAEOEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovedAEOEventResponse>() {
            @Override
            public ApprovedAEOEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVEDAEO_EVENT, log);
                ApprovedAEOEventResponse typedResponse = new ApprovedAEOEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovedAEOEventResponse> approvedAEOEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVEDAEO_EVENT));
        return approvedAEOEventFlowable(filter);
    }

    public List<RevokedAEOEventResponse> getRevokedAEOEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REVOKEDAEO_EVENT, transactionReceipt);
        ArrayList<RevokedAEOEventResponse> responses = new ArrayList<RevokedAEOEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RevokedAEOEventResponse typedResponse = new RevokedAEOEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RevokedAEOEventResponse> revokedAEOEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RevokedAEOEventResponse>() {
            @Override
            public RevokedAEOEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REVOKEDAEO_EVENT, log);
                RevokedAEOEventResponse typedResponse = new RevokedAEOEventResponse();
                typedResponse.log = log;
                typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RevokedAEOEventResponse> revokedAEOEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REVOKEDAEO_EVENT));
        return revokedAEOEventFlowable(filter);
    }

    public List<RoleAddedEventResponse> getRoleAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEADDED_EVENT, transactionReceipt);
        ArrayList<RoleAddedEventResponse> responses = new ArrayList<RoleAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleAddedEventResponse typedResponse = new RoleAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.role = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleAddedEventResponse> roleAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RoleAddedEventResponse>() {
            @Override
            public RoleAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEADDED_EVENT, log);
                RoleAddedEventResponse typedResponse = new RoleAddedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.role = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleAddedEventResponse> roleAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEADDED_EVENT));
        return roleAddedEventFlowable(filter);
    }

    public List<RoleRemovedEventResponse> getRoleRemovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ROLEREMOVED_EVENT, transactionReceipt);
        ArrayList<RoleRemovedEventResponse> responses = new ArrayList<RoleRemovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RoleRemovedEventResponse typedResponse = new RoleRemovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.role = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RoleRemovedEventResponse> roleRemovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, RoleRemovedEventResponse>() {
            @Override
            public RoleRemovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ROLEREMOVED_EVENT, log);
                RoleRemovedEventResponse typedResponse = new RoleRemovedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.role = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RoleRemovedEventResponse> roleRemovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ROLEREMOVED_EVENT));
        return roleRemovedEventFlowable(filter);
    }


    public static AEOManager load(String contractAddress, Web3 web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new AEOManager(contractAddress, web3j, credentials, contractGasProvider);
    }


    public static RemoteCall<AEOManager> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String proxy, String firstAdmin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, proxy), 
                new org.web3j.abi.datatypes.Address(160, firstAdmin)));
        return deployRemoteCall(AEOManager.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<AEOManager> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String proxy, String firstAdmin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, proxy), 
                new org.web3j.abi.datatypes.Address(160, firstAdmin)));
        return deployRemoteCall(AEOManager.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AEOManager> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String proxy, String firstAdmin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, proxy), 
                new org.web3j.abi.datatypes.Address(160, firstAdmin)));
        return deployRemoteCall(AEOManager.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<AEOManager> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String proxy, String firstAdmin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, proxy), 
                new org.web3j.abi.datatypes.Address(160, firstAdmin)));
        return deployRemoteCall(AEOManager.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class RegisteredAEOEventResponse extends BaseEventResponse {
        public byte[] hash;

        public String user;
    }

    public static class ApprovedAEOEventResponse extends BaseEventResponse {
        public byte[] hash;

        public String user;
    }

    public static class RevokedAEOEventResponse extends BaseEventResponse {
        public byte[] hash;

        public String user;
    }

    public static class RoleAddedEventResponse extends BaseEventResponse {
        public String user;

        public String role;
    }

    public static class RoleRemovedEventResponse extends BaseEventResponse {
        public String user;

        public String role;
    }
}
