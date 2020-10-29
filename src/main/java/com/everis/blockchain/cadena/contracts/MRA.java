package com.everis.blockchain.cadena.contracts;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple5;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

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
public class MRA extends Contract {
    private static final String BINARY = "6080604052600060045534801561001557600080fd5b506100283364010000000061002d810204565b610092565b600160a060020a03811660008181526020818152604091829020805460ff19166001908117909155600480549091019055815192835290517fb251eb052afc73ffd02ffe85ad79990a8b3fed60d76dbc2fa2fdd7123dffd9149281900390910190a150565b612215806100a16000396000f3006080604052600436106100e55763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166308ae4b0c81146100ea578063170a76351461011f5780631dacef4f146101b8578063348e7b991461024f5780634e3c118e1461036657806356c628bb146103f35780635d0341ba1461041957806376e925591461044c5780638f59c06614610461578063a4874d7714610488578063af2ff8f21461049d578063be64e03c14610534578063d87991a81461055b578063daea85c5146105b8578063e7a16491146105d9578063ed0f2e75146105fa575b600080fd5b3480156100f657600080fd5b5061010b600160a060020a03600435166107af565b604080519115158252519081900360200190f35b34801561012b57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101b694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506107c49650505050505050565b005b3480156101c457600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101b694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750610a6a9650505050505050565b34801561025b57600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101b694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a999881019791965091820194509250829150840183828082843750949750505050913515159250610d0d915050565b34801561037257600080fd5b5061037e60043561116d565b6040805160208082528351818301528351919283929083019185019080838360005b838110156103b85781810151838201526020016103a0565b50505050905090810190601f1680156103e55780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156103ff57600080fd5b506101b6600160a060020a03600435166024351515611214565b34801561042557600080fd5b5061043a600160a060020a0360043516611349565b60408051918252519081900360200190f35b34801561045857600080fd5b5061043a61135b565b34801561046d57600080fd5b5061010b600160a060020a0360043581169060243516611361565b34801561049457600080fd5b506101b6611381565b3480156104a957600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101b694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375094975061142e9650505050505050565b34801561054057600080fd5b5061010b600160a060020a03600435811690602435166116d4565b34801561056757600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526101b69436949293602493928401919081908401838280828437509497505050509135151592506116f4915050565b3480156105c457600080fd5b506101b6600160a060020a036004351661198b565b3480156105e557600080fd5b5061010b600160a060020a0360043516611b51565b34801561060657600080fd5b506040805160206004803580820135601f8101849004840285018401909552848452610653943694929360249392840191908190840183828082843750949750611bd29650505050505050565b604080518315156060820152600160a060020a038316608082015260a080825287519082015286519091829160208084019284019160c08501918b019080838360005b838110156106ae578181015183820152602001610696565b50505050905090810190601f1680156106db5780820380516001836020036101000a031916815260200191505b5084810383528851815288516020918201918a019080838360005b8381101561070e5781810151838201526020016106f6565b50505050905090810190601f16801561073b5780820380516001836020036101000a031916815260200191505b50848103825287518152875160209182019189019080838360005b8381101561076e578181015183820152602001610756565b50505050905090810190601f16801561079b5780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b60006020819052908152604090205460ff1681565b60008260006006826040518082805190602001908083835b602083106107fb5780518252601f1990920191602091820191016107dc565b518151600019602094850361010090810a919091019182169119929092161790915293909101958652604051958690030190942060030154600160a060020a039190041693505050811515905061088a576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b600160a060020a03811633146108d8576040805160e560020a62461bcd02815260206004820152601d602482015260008051602061216a833981519152604482015290519081900360640190fd5b60006108e386611e8a565b1215610927576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b6006856040518082805190602001908083835b602083106109595780518252601f19909201916020918201910161093a565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320875190965061099d93600288019350880191506120ce565b5033600160a060020a03166000805160206121aa83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b838110156109f95781810151838201526020016109e1565b50505050905090810190601f168015610a265780820380516001836020036101000a031916815260200191505b50928303905250600b81527f6f62736572766174696f6e00000000000000000000000000000000000000000060208201526040805191829003019150a25050505050565b60008260006006826040518082805190602001908083835b60208310610aa15780518252601f199092019160209182019101610a82565b518151600019602094850361010090810a919091019182169119929092161790915293909101958652604051958690030190942060030154600160a060020a0391900416935050508115159050610b30576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b600160a060020a0381163314610b7e576040805160e560020a62461bcd02815260206004820152601d602482015260008051602061216a833981519152604482015290519081900360640190fd5b6000610b8986611e8a565b1215610bcd576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b6006856040518082805190602001908083835b60208310610bff5780518252601f199092019160209182019101610be0565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208751909650610c4093879350880191506120ce565b5033600160a060020a03166000805160206121aa83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b83811015610c9c578181015183820152602001610c84565b50505050905090810190601f168015610cc95780820380516001836020036101000a031916815260200191505b50928303905250600481527f646174610000000000000000000000000000000000000000000000000000000060208201526040805191829003019150a25050505050565b3360009081526020819052604090205460ff161515610d64576040805160e560020a62461bcd02815260206004820152600b602482015260008051602061218a833981519152604482015290519081900360640190fd5b836006866040518082805190602001908083835b60208310610d975780518252601f199092019160209182019101610d78565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451610dd895919491909101925090506120ce565b50826006866040518082805190602001908083835b60208310610e0c5780518252601f199092019160209182019101610ded565b6001836020036101000a03801982511681845116808217855250505050505090500191505090815260200160405180910390206001019080519060200190610e559291906120ce565b50816006866040518082805190602001908083835b60208310610e895780518252601f199092019160209182019101610e6a565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208451610ece9560029092019491909101925090506120ce565b50806006866040518082805190602001908083835b60208310610f025780518252601f199092019160209182019101610ee3565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381018420600301805460ff191695151595909517909455505086513392600692899290918291908401908083835b60208310610f765780518252601f199092019160209182019101610f57565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160016101000a815481600160a060020a030219169083600160a060020a03160217905550610fd985611e8a565b60001914156110c65760058054600181018083556000929092528651611026917f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db0019060208901906120ce565b5050604080516020808252875181830152875133937f6dae487072a0ae0f2473b3117e73cde4322a2d1201b97a8aa6e0c73e6adf6dfc938a939092839283019185019080838360005b8381101561108757818101518382015260200161106f565b50505050905090810190601f1680156110b45780820380516001836020036101000a031916815260200191505b509250505060405180910390a2611166565b33600160a060020a03166000805160206121aa83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b83811015611121578181015183820152602001611109565b50505050905090810190601f16801561114e5780820380516001836020036101000a031916815260200191505b50928303905250600081526040805191829003019150a25b5050505050565b600580548290811061117b57fe5b600091825260209182902001805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181529350909183018282801561120c5780601f106111e15761010080835404028352916020019161120c565b820191906000526020600020905b8154815290600101906020018083116111ef57829003601f168201915b505050505081565b3360009081526020819052604090205460ff16151561126b576040805160e560020a62461bcd02815260206004820152600b602482015260008051602061218a833981519152604482015290519081900360640190fd5b3360009081526020819052604090205460ff1615156112d4576040805160e560020a62461bcd02815260206004820152601560248201527f6d656d62657220646f6573206e6f742065786973740000000000000000000000604482015290519081900360640190fd5b336000818152600160209081526040808320600160a060020a03871680855290835292819020805486151560ff19909116811790915581519485529184019290925282820152517f21c7b0bd597e6687a5e0a4972f9cee392d50a498a7b4cc8aee66f403628034889181900360600190a15050565b60036020526000908152604090205481565b60045481565b600260209081526000928352604080842090915290825290205460ff1681565b3360009081526020819052604090205460ff1615156113d8576040805160e560020a62461bcd02815260206004820152600b602482015260008051602061218a833981519152604482015290519081900360640190fd5b3360008181526020818152604091829020805460ff1916905560048054600019019055815192835290517fa59e3b52cc428f9ef295563b34bd6af71489d571edf9d7f38ba0e977d0abc6cf9281900390910190a1565b60008260006006826040518082805190602001908083835b602083106114655780518252601f199092019160209182019101611446565b518151600019602094850361010090810a919091019182169119929092161790915293909101958652604051958690030190942060030154600160a060020a03919004169350505081151590506114f4576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b600160a060020a0381163314611542576040805160e560020a62461bcd02815260206004820152601d602482015260008051602061216a833981519152604482015290519081900360640190fd5b600061154d86611e8a565b1215611591576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b6006856040518082805190602001908083835b602083106115c35780518252601f1990920191602091820191016115a4565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320875190965061160793600188019350880191506120ce565b5033600160a060020a03166000805160206121aa83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b8381101561166357818101518382015260200161164b565b50505050905090810190601f1680156116905780820380516001836020036101000a031916815260200191505b50928303905250600581527f737461746500000000000000000000000000000000000000000000000000000060208201526040805191829003019150a25050505050565b600160209081526000928352604080842090915290825290205460ff1681565b60008260006006826040518082805190602001908083835b6020831061172b5780518252601f19909201916020918201910161170c565b518151600019602094850361010090810a919091019182169119929092161790915293909101958652604051958690030190942060030154600160a060020a03919004169350505081151590506117ba576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b600160a060020a0381163314611808576040805160e560020a62461bcd02815260206004820152601d602482015260008051602061216a833981519152604482015290519081900360640190fd5b600061181386611e8a565b1215611857576040805160e560020a62461bcd02815260206004820152601260248201526000805160206121ca833981519152604482015290519081900360640190fd5b6006856040518082805190602001908083835b602083106118895780518252601f19909201916020918201910161186a565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201852060038101805460ff19168b15151790558186528a5191860191909152895190975033946000805160206121aa83398151915294508a93509182918282019160608401919086019080838360005b8381101561191a578181015183820152602001611902565b50505050905090810190601f1680156119475780820380516001836020036101000a031916815260200191505b50928303905250600781527f76697369626c650000000000000000000000000000000000000000000000000060208201526040805191829003019150a25050505050565b600160a060020a038116600090815260026020908152604080832033845290915290205460ff1615611a07576040805160e560020a62461bcd02815260206004820152600d60248201527f616c726561647920766f74656400000000000000000000000000000000000000604482015290519081900360640190fd5b600160a060020a038116331480611a2d57503360009081526020819052604090205460ff165b1515611aa9576040805160e560020a62461bcd02815260206004820152602960248201527f63616e6e6f7420626520617070726f76656420776974686f7574206265696e6760448201527f2061206d656d6265720000000000000000000000000000000000000000000000606482015290519081900360840190fd5b600160a060020a03811660008181526002602090815260408083203384528252808320805460ff1916600190811790915584845260038352928190208054909301909255815192835290517f3da74045128fff0396b39203f81bcedcbcf28586f0f97fb0f41ceb5521566a159281900390910190a1600454600160a060020a03821660009081526003602052604090205460019091011415611b4e57611b4e81612069565b50565b3360009081526020819052604081205460ff161515611ba8576040805160e560020a62461bcd02815260206004820152600b602482015260008051602061218a833981519152604482015290519081900360640190fd5b50600160a060020a0316600090815260016020908152604080832033845290915290205460ff1690565b336000908152602081905260408120546060918291829190819081908490819081908490819060ff161515611c3f576040805160e560020a62461bcd02815260206004820152600b602482015260008051602061218a833981519152604482015290519081900360640190fd5b60068c6040518082805190602001908083835b60208310611c715780518252601f199092019160209182019101611c52565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f6002600183161590980290950116959095049283018290048202880182019052818752929b508b9450925050830182828015611d285780601f10611cfd57610100808354040283529160200191611d28565b820191906000526020600020905b815481529060010190602001808311611d0b57829003601f168201915b50505050509450856001018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015611dc75780601f10611d9c57610100808354040283529160200191611dc7565b820191906000526020600020905b815481529060010190602001808311611daa57829003601f168201915b5050506002808a018054604080516020601f60001961010060018716150201909416959095049283018590048502810185019091528181529599509093509150830182828015611e585780601f10611e2d57610100808354040283529160200191611e58565b820191906000526020600020905b815481529060010190602001808311611e3b57829003601f168201915b5050505060039790970154959d949c509a505060ff841698505050610100909104600160a060020a0316945092505050565b6000805b60055481101561205d57826040516020018082805190602001908083835b60208310611ecb5780518252601f199092019160209182019101611eac565b6001836020036101000a0380198251168184511680821785525050505050509050019150506040516020818303038152906040526040518082805190602001908083835b60208310611f2e5780518252601f199092019160209182019101611f0f565b5181516020939093036101000a600019018019909116921691909117905260405192018290039091206005805491945092508491508110611f6b57fe5b906000526020600020016040516020018082805460018160011615610100020316600290048015611fd35780601f10611fb1576101008083540402835291820191611fd3565b820191906000526020600020905b815481529060010190602001808311611fbf575b50509150506040516020818303038152906040526040518082805190602001908083835b602083106120165780518252601f199092019160209182019101611ff7565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902060001916141561205557809150612063565b600101611e8e565b60001991505b50919050565b600160a060020a03811660008181526020818152604091829020805460ff19166001908117909155600480549091019055815192835290517fb251eb052afc73ffd02ffe85ad79990a8b3fed60d76dbc2fa2fdd7123dffd9149281900390910190a150565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061210f57805160ff191683800117855561213c565b8280016001018555821561213c579182015b8281111561213c578251825591602001919060010190612121565b5061214892915061214c565b5090565b61216691905b808211156121485760008155600101612152565b9056006f6e6c79206f776e6572206f66207468652063657274696669636174650000006f6e6c79206d656d6265720000000000000000000000000000000000000000008a2d6d69682b8d9bb9bd7d7e1a31bfc28ef8131c421695dbbb4537b35bf65d5454494e20646f6573206e6f742065786973740000000000000000000000000000a165627a7a723058209a47d337b568568b2ed34bbcd318d988f9efaf329035d8c8fca01fcbde8ceb960029";

    public static final String FUNC_MEMBERS = "members";

    public static final String FUNC_UPDATECERTIFICATEOBSERVATION = "updateCertificateObservation";

    public static final String FUNC_UPDATECERTIFICATEDATA = "updateCertificateData";

    public static final String FUNC_ADDCERTIFICATE = "addCertificate";

    public static final String FUNC_LISTTIN = "listTIN";

    public static final String FUNC_SETVISIBILITYTOMEMBER = "setVisibilityToMember";

    public static final String FUNC_APPROVALS = "approvals";

    public static final String FUNC_TOTALMEMBERS = "totalMembers";

    public static final String FUNC_VOTED = "voted";

    public static final String FUNC_RETIRE = "retire";

    public static final String FUNC_UPDATECERTIFICATESTATE = "updateCertificateState";

    public static final String FUNC_VISIBILITY = "visibility";

    public static final String FUNC_UPDATECERTIFICATEVISIBLE = "updateCertificateVisible";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_GETVISIBILITYFROMMEMBER = "getVisibilityFromMember";

    public static final String FUNC_GETCERTIFICATE = "getCertificate";

    public static final Event MEMBERAPPROVED_EVENT = new Event("MemberApproved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event MEMBERADDED_EVENT = new Event("MemberAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event MEMBERRETIRED_EVENT = new Event("MemberRetired", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event CERTIFICATEADDED_EVENT = new Event("CertificateAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event CERTIFICATEMODIFIED_EVENT = new Event("CertificateModified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event VISIBILITYSETTED_EVENT = new Event("visibilitySetted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Bool>() {}));
    ;

    @Deprecated
    protected MRA(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MRA(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MRA(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MRA(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<Boolean> members(String param0) {
        final Function function = new Function(FUNC_MEMBERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateObservation(String TIN, String observation) {
        final Function function = new Function(
                FUNC_UPDATECERTIFICATEOBSERVATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(observation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateData(String TIN, String data) {
        final Function function = new Function(
                FUNC_UPDATECERTIFICATEDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> addCertificate(String TIN, String certificate, String state, String observation, Boolean visible) {
        final Function function = new Function(
                FUNC_ADDCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(certificate), 
                new org.web3j.abi.datatypes.Utf8String(state), 
                new org.web3j.abi.datatypes.Utf8String(observation), 
                new org.web3j.abi.datatypes.Bool(visible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> listTIN(BigInteger param0) {
        final Function function = new Function(FUNC_LISTTIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setVisibilityToMember(String member, Boolean visible) {
        final Function function = new Function(
                FUNC_SETVISIBILITYTOMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, member), 
                new org.web3j.abi.datatypes.Bool(visible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> approvals(String param0) {
        final Function function = new Function(FUNC_APPROVALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalMembers() {
        final Function function = new Function(FUNC_TOTALMEMBERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> voted(String param0, String param1) {
        final Function function = new Function(FUNC_VOTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> retire() {
        final Function function = new Function(
                FUNC_RETIRE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateState(String TIN, String state) {
        final Function function = new Function(
                FUNC_UPDATECERTIFICATESTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(state)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> visibility(String param0, String param1) {
        final Function function = new Function(FUNC_VISIBILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateVisible(String TIN, Boolean visible) {
        final Function function = new Function(
                FUNC_UPDATECERTIFICATEVISIBLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Bool(visible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String newMember) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newMember)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> getVisibilityFromMember(String member) {
        final Function function = new Function(FUNC_GETVISIBILITYFROMMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, member)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, Boolean, String>> getCertificate(String TIN) {
        final Function function = new Function(FUNC_GETCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        return new RemoteFunctionCall<Tuple5<String, String, String, Boolean, String>>(function,
                new Callable<Tuple5<String, String, String, Boolean, String>>() {
                    @Override
                    public Tuple5<String, String, String, Boolean, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple5<String, String, String, Boolean, String>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (Boolean) results.get(3).getValue(), 
                                (String) results.get(4).getValue());
                    }
                });
    }

    public List<MemberApprovedEventResponse> getMemberApprovedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MEMBERAPPROVED_EVENT, transactionReceipt);
        ArrayList<MemberApprovedEventResponse> responses = new ArrayList<MemberApprovedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MemberApprovedEventResponse typedResponse = new MemberApprovedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.candidate = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MemberApprovedEventResponse> memberApprovedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MemberApprovedEventResponse>() {
            @Override
            public MemberApprovedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MEMBERAPPROVED_EVENT, log);
                MemberApprovedEventResponse typedResponse = new MemberApprovedEventResponse();
                typedResponse.log = log;
                typedResponse.candidate = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MemberApprovedEventResponse> memberApprovedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MEMBERAPPROVED_EVENT));
        return memberApprovedEventFlowable(filter);
    }

    public List<MemberAddedEventResponse> getMemberAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MEMBERADDED_EVENT, transactionReceipt);
        ArrayList<MemberAddedEventResponse> responses = new ArrayList<MemberAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MemberAddedEventResponse typedResponse = new MemberAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.newMember = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MemberAddedEventResponse> memberAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MemberAddedEventResponse>() {
            @Override
            public MemberAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MEMBERADDED_EVENT, log);
                MemberAddedEventResponse typedResponse = new MemberAddedEventResponse();
                typedResponse.log = log;
                typedResponse.newMember = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MemberAddedEventResponse> memberAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MEMBERADDED_EVENT));
        return memberAddedEventFlowable(filter);
    }

    public List<MemberRetiredEventResponse> getMemberRetiredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MEMBERRETIRED_EVENT, transactionReceipt);
        ArrayList<MemberRetiredEventResponse> responses = new ArrayList<MemberRetiredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            MemberRetiredEventResponse typedResponse = new MemberRetiredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.member = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<MemberRetiredEventResponse> memberRetiredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, MemberRetiredEventResponse>() {
            @Override
            public MemberRetiredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(MEMBERRETIRED_EVENT, log);
                MemberRetiredEventResponse typedResponse = new MemberRetiredEventResponse();
                typedResponse.log = log;
                typedResponse.member = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<MemberRetiredEventResponse> memberRetiredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(MEMBERRETIRED_EVENT));
        return memberRetiredEventFlowable(filter);
    }

    public List<CertificateAddedEventResponse> getCertificateAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CERTIFICATEADDED_EVENT, transactionReceipt);
        ArrayList<CertificateAddedEventResponse> responses = new ArrayList<CertificateAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CertificateAddedEventResponse typedResponse = new CertificateAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.TIN = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CertificateAddedEventResponse> certificateAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CertificateAddedEventResponse>() {
            @Override
            public CertificateAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CERTIFICATEADDED_EVENT, log);
                CertificateAddedEventResponse typedResponse = new CertificateAddedEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.TIN = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CertificateAddedEventResponse> certificateAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CERTIFICATEADDED_EVENT));
        return certificateAddedEventFlowable(filter);
    }

    public List<CertificateModifiedEventResponse> getCertificateModifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CERTIFICATEMODIFIED_EVENT, transactionReceipt);
        ArrayList<CertificateModifiedEventResponse> responses = new ArrayList<CertificateModifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            CertificateModifiedEventResponse typedResponse = new CertificateModifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.TIN = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.key = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<CertificateModifiedEventResponse> certificateModifiedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, CertificateModifiedEventResponse>() {
            @Override
            public CertificateModifiedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CERTIFICATEMODIFIED_EVENT, log);
                CertificateModifiedEventResponse typedResponse = new CertificateModifiedEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.TIN = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.key = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<CertificateModifiedEventResponse> certificateModifiedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CERTIFICATEMODIFIED_EVENT));
        return certificateModifiedEventFlowable(filter);
    }

    public List<VisibilitySettedEventResponse> getVisibilitySettedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(VISIBILITYSETTED_EVENT, transactionReceipt);
        ArrayList<VisibilitySettedEventResponse> responses = new ArrayList<VisibilitySettedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            VisibilitySettedEventResponse typedResponse = new VisibilitySettedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.origin = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.member = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.visible = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<VisibilitySettedEventResponse> visibilitySettedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, VisibilitySettedEventResponse>() {
            @Override
            public VisibilitySettedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(VISIBILITYSETTED_EVENT, log);
                VisibilitySettedEventResponse typedResponse = new VisibilitySettedEventResponse();
                typedResponse.log = log;
                typedResponse.origin = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.member = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.visible = (Boolean) eventValues.getNonIndexedValues().get(2).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<VisibilitySettedEventResponse> visibilitySettedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(VISIBILITYSETTED_EVENT));
        return visibilitySettedEventFlowable(filter);
    }

    @Deprecated
    public static MRA load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MRA(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MRA load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MRA(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MRA load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MRA(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MRA load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MRA(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MRA> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MRA.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    public static RemoteCall<MRA> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(MRA.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MRA> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MRA.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<MRA> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(MRA.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class MemberApprovedEventResponse extends BaseEventResponse {
        public String candidate;
    }

    public static class MemberAddedEventResponse extends BaseEventResponse {
        public String newMember;
    }

    public static class MemberRetiredEventResponse extends BaseEventResponse {
        public String member;
    }

    public static class CertificateAddedEventResponse extends BaseEventResponse {
        public String owner;

        public String TIN;
    }

    public static class CertificateModifiedEventResponse extends BaseEventResponse {
        public String owner;

        public String TIN;

        public String key;
    }

    public static class VisibilitySettedEventResponse extends BaseEventResponse {
        public String origin;

        public String member;

        public Boolean visible;
    }
}
