package contract;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
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
 * <p>Generated with web3j version 4.5.11.
 */
@SuppressWarnings("rawtypes")
public class MRA extends Contract {
    public static final String BINARY = "6080604052600060045534801561001557600080fd5b50610028336001600160e01b0361002d16565b610092565b6001600160a01b03811660008181526020818152604091829020805460ff19166001908117909155600480549091019055815192835290517fb251eb052afc73ffd02ffe85ad79990a8b3fed60d76dbc2fa2fdd7123dffd9149281900390910190a150565b61242e806100a16000396000f3fe608060405234801561001057600080fd5b50600436106101005760003560e01c80638f59c06611610097578063d87991a811610066578063d87991a814610855578063daea85c5146108fb578063e7a1649114610921578063ed0f2e751461094757610100565b80638f59c066146106c8578063a4874d77146106f6578063af2ff8f2146106fe578063be64e03c1461082757610100565b80634e3c118e116100d35780634e3c118e146105c857806356c628bb1461065a5780635d0341ba1461068857806376e92559146106c057610100565b806308ae4b0c14610105578063170a76351461013f5780631dacef4f1461026a578063348e7b9914610393575b600080fd5b61012b6004803603602081101561011b57600080fd5b50356001600160a01b0316610b47565b604080519115158252519081900360200190f35b6102686004803603604081101561015557600080fd5b810190602081018135600160201b81111561016f57600080fd5b82018360208201111561018157600080fd5b803590602001918460018302840111600160201b831117156101a257600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156101f457600080fd5b82018360208201111561020657600080fd5b803590602001918460018302840111600160201b8311171561022757600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610b5c945050505050565b005b6102686004803603604081101561028057600080fd5b810190602081018135600160201b81111561029a57600080fd5b8201836020820111156102ac57600080fd5b803590602001918460018302840111600160201b831117156102cd57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561031f57600080fd5b82018360208201111561033157600080fd5b803590602001918460018302840111600160201b8311171561035257600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610df5945050505050565b610268600480360360a08110156103a957600080fd5b810190602081018135600160201b8111156103c357600080fd5b8201836020820111156103d557600080fd5b803590602001918460018302840111600160201b831117156103f657600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561044857600080fd5b82018360208201111561045a57600080fd5b803590602001918460018302840111600160201b8311171561047b57600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156104cd57600080fd5b8201836020820111156104df57600080fd5b803590602001918460018302840111600160201b8311171561050057600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b81111561055257600080fd5b82018360208201111561056457600080fd5b803590602001918460018302840111600160201b8311171561058557600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505050503515159050611084565b6105e5600480360360208110156105de57600080fd5b50356114df565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561061f578181015183820152602001610607565b50505050905090810190601f16801561064c5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6102686004803603604081101561067057600080fd5b506001600160a01b0381351690602001351515611585565b6106ae6004803603602081101561069e57600080fd5b50356001600160a01b03166116a8565b60408051918252519081900360200190f35b6106ae6116ba565b61012b600480360360408110156106de57600080fd5b506001600160a01b03813581169160200135166116c0565b6102686116e0565b6102686004803603604081101561071457600080fd5b810190602081018135600160201b81111561072e57600080fd5b82018360208201111561074057600080fd5b803590602001918460018302840111600160201b8311171561076157600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295949360208101935035915050600160201b8111156107b357600080fd5b8201836020820111156107c557600080fd5b803590602001918460018302840111600160201b831117156107e657600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611788945050505050565b61012b6004803603604081101561083d57600080fd5b506001600160a01b0381358116916020013516611a1b565b6102686004803603604081101561086b57600080fd5b810190602081018135600160201b81111561088557600080fd5b82018360208201111561089757600080fd5b803590602001918460018302840111600160201b831117156108b857600080fd5b91908080601f0160208091040260200160405190810160405280939291908181526020018383808284376000920191909152509295505050503515159050611a3b565b6102686004803603602081101561091157600080fd5b50356001600160a01b0316611cc1565b61012b6004803603602081101561093757600080fd5b50356001600160a01b0316611e33565b6109eb6004803603602081101561095d57600080fd5b810190602081018135600160201b81111561097757600080fd5b82018360208201111561098957600080fd5b803590602001918460018302840111600160201b831117156109aa57600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550611eb3945050505050565b6040805183151560608201526001600160a01b038316608082015260a080825287519082015286519091829160208084019284019160c08501918b019080838360005b83811015610a46578181015183820152602001610a2e565b50505050905090810190601f168015610a735780820380516001836020036101000a031916815260200191505b5084810383528851815288516020918201918a019080838360005b83811015610aa6578181015183820152602001610a8e565b50505050905090810190601f168015610ad35780820380516001836020036101000a031916815260200191505b50848103825287518152875160209182019189019080838360005b83811015610b06578181015183820152602001610aee565b50505050905090810190601f168015610b335780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b60006020819052908152604090205460ff1681565b8160006006826040518082805190602001908083835b60208310610b915780518252601f199092019160209182019101610b72565b518151600019602094850361010090810a9190910191821691199290921617909152939091019586526040519586900301909420600301546001600160a01b0391900416935050508115159050610c24576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b6001600160a01b0381163314610c6f576040805162461bcd60e51b815260206004820152601d60248201526000805160206123ba833981519152604482015290519081900360640190fd5b6000610c7a85612165565b1215610cc2576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b60006006856040518082805190602001908083835b60208310610cf65780518252601f199092019160209182019101610cd7565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208751909450610d3a93600286019350880191506122f5565b50336001600160a01b03166000805160206123da83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b83811015610d96578181015183820152602001610d7e565b50505050905090810190601f168015610dc35780820380516001836020036101000a031916815260200191505b50928303905250600b81526a37b139b2b93b30ba34b7b760a91b60208201526040805191829003019150a25050505050565b8160006006826040518082805190602001908083835b60208310610e2a5780518252601f199092019160209182019101610e0b565b518151600019602094850361010090810a9190910191821691199290921617909152939091019586526040519586900301909420600301546001600160a01b0391900416935050508115159050610ebd576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b6001600160a01b0381163314610f08576040805162461bcd60e51b815260206004820152601d60248201526000805160206123ba833981519152604482015290519081900360640190fd5b6000610f1385612165565b1215610f5b576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b60006006856040518082805190602001908083835b60208310610f8f5780518252601f199092019160209182019101610f70565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381019093208751909450610fd093859350880191506122f5565b50336001600160a01b03166000805160206123da83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b8381101561102c578181015183820152602001611014565b50505050905090810190601f1680156110595780820380516001836020036101000a031916815260200191505b5092830390525060048152636461746160e01b60208201526040805191829003019150a25050505050565b3360009081526020819052604090205460ff166110d6576040805162461bcd60e51b815260206004820152600b60248201526a37b7363c9036b2b6b132b960a91b604482015290519081900360640190fd5b836006866040518082805190602001908083835b602083106111095780518252601f1990920191602091820191016110ea565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320845161114a95919491909101925090506122f5565b50826006866040518082805190602001908083835b6020831061117e5780518252601f19909201916020918201910161115f565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060010190805190602001906111c79291906122f5565b50816006866040518082805190602001908083835b602083106111fb5780518252601f1990920191602091820191016111dc565b51815160209384036101000a600019018019909216911617905292019485525060405193849003810190932084516112409560029092019491909101925090506122f5565b50806006866040518082805190602001908083835b602083106112745780518252601f199092019160209182019101611255565b51815160209384036101000a60001901801990921691161790529201948552506040519384900381018420600301805460ff191695151595909517909455505086513392600692899290918291908401908083835b602083106112e85780518252601f1990920191602091820191016112c9565b6001836020036101000a038019825116818451168082178552505050505050905001915050908152602001604051809103902060030160016101000a8154816001600160a01b0302191690836001600160a01b0316021790555061134b85612165565b60001914156114385760058054600181018083556000929092528651611398917f036b6384b5eca791c62761152d0c79bb0604c104a5fb6f4eb0703f3154bb3db0019060208901906122f5565b5050604080516020808252875181830152875133937f6dae487072a0ae0f2473b3117e73cde4322a2d1201b97a8aa6e0c73e6adf6dfc938a939092839283019185019080838360005b838110156113f95781810151838201526020016113e1565b50505050905090810190601f1680156114265780820380516001836020036101000a031916815260200191505b509250505060405180910390a26114d8565b336001600160a01b03166000805160206123da83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b8381101561149357818101518382015260200161147b565b50505050905090810190601f1680156114c05780820380516001836020036101000a031916815260200191505b50928303905250600081526040805191829003019150a25b5050505050565b600581815481106114ec57fe5b600091825260209182902001805460408051601f600260001961010060018716150201909416939093049283018590048502810185019091528181529350909183018282801561157d5780601f106115525761010080835404028352916020019161157d565b820191906000526020600020905b81548152906001019060200180831161156057829003601f168201915b505050505081565b3360009081526020819052604090205460ff166115d7576040805162461bcd60e51b815260206004820152600b60248201526a37b7363c9036b2b6b132b960a91b604482015290519081900360640190fd5b3360009081526020819052604090205460ff16611633576040805162461bcd60e51b81526020600482015260156024820152741b595b58995c88191bd95cc81b9bdd08195e1a5cdd605a1b604482015290519081900360640190fd5b3360008181526001602090815260408083206001600160a01b03871680855290835292819020805486151560ff19909116811790915581519485529184019290925282820152517f21c7b0bd597e6687a5e0a4972f9cee392d50a498a7b4cc8aee66f403628034889181900360600190a15050565b60036020526000908152604090205481565b60045481565b600260209081526000928352604080842090915290825290205460ff1681565b3360009081526020819052604090205460ff16611732576040805162461bcd60e51b815260206004820152600b60248201526a37b7363c9036b2b6b132b960a91b604482015290519081900360640190fd5b3360008181526020818152604091829020805460ff1916905560048054600019019055815192835290517fa59e3b52cc428f9ef295563b34bd6af71489d571edf9d7f38ba0e977d0abc6cf9281900390910190a1565b8160006006826040518082805190602001908083835b602083106117bd5780518252601f19909201916020918201910161179e565b518151600019602094850361010090810a9190910191821691199290921617909152939091019586526040519586900301909420600301546001600160a01b0391900416935050508115159050611850576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b6001600160a01b038116331461189b576040805162461bcd60e51b815260206004820152601d60248201526000805160206123ba833981519152604482015290519081900360640190fd5b60006118a685612165565b12156118ee576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b60006006856040518082805190602001908083835b602083106119225780518252601f199092019160209182019101611903565b51815160209384036101000a6000190180199092169116179052920194855250604051938490038101909320875190945061196693600186019350880191506122f5565b50336001600160a01b03166000805160206123da83398151915286604051808060200180602001838103835284818151815260200191508051906020019080838360005b838110156119c25781810151838201526020016119aa565b50505050905090810190601f1680156119ef5780820380516001836020036101000a031916815260200191505b509283039052506005815264737461746560d81b60208201526040805191829003019150a25050505050565b600160209081526000928352604080842090915290825290205460ff1681565b8160006006826040518082805190602001908083835b60208310611a705780518252601f199092019160209182019101611a51565b518151600019602094850361010090810a9190910191821691199290921617909152939091019586526040519586900301909420600301546001600160a01b0391900416935050508115159050611b03576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b6001600160a01b0381163314611b4e576040805162461bcd60e51b815260206004820152601d60248201526000805160206123ba833981519152604482015290519081900360640190fd5b6000611b5985612165565b1215611ba1576040805162461bcd60e51b815260206004820152601260248201527115125388191bd95cc81b9bdd08195e1a5cdd60721b604482015290519081900360640190fd5b60006006856040518082805190602001908083835b60208310611bd55780518252601f199092019160209182019101611bb6565b51815160209384036101000a600019018019909216911617905292019485525060408051948590038201852060038101805460ff19168b15151790558186528a5191860191909152895190955033946000805160206123da83398151915294508a93509182918282019160608401919086019080838360005b83811015611c66578181015183820152602001611c4e565b50505050905090810190601f168015611c935780820380516001836020036101000a031916815260200191505b50928303905250600781526676697369626c6560c81b60208201526040805191829003019150a25050505050565b6001600160a01b038116600090815260026020908152604080832033845290915290205460ff1615611d2a576040805162461bcd60e51b815260206004820152600d60248201526c185b1c9958591e481d9bdd1959609a1b604482015290519081900360640190fd5b6001600160a01b038116331480611d5057503360009081526020819052604090205460ff165b611d8b5760405162461bcd60e51b81526004018080602001828103825260298152602001806123916029913960400191505060405180910390fd5b6001600160a01b03811660008181526002602090815260408083203384528252808320805460ff1916600190811790915584845260038352928190208054909301909255815192835290517f3da74045128fff0396b39203f81bcedcbcf28586f0f97fb0f41ceb5521566a159281900390910190a16004546001600160a01b03821660009081526003602052604090205460019091011415611e3057611e3081612290565b50565b3360009081526020819052604081205460ff16611e85576040805162461bcd60e51b815260206004820152600b60248201526a37b7363c9036b2b6b132b960a91b604482015290519081900360640190fd5b506001600160a01b038116600090815260016020908152604080832033845290915290205460ff165b919050565b336000908152602081905260408120546060918291829190819060ff16611f0f576040805162461bcd60e51b815260206004820152600b60248201526a37b7363c9036b2b6b132b960a91b604482015290519081900360640190fd5b60006006876040518082805190602001908083835b60208310611f435780518252601f199092019160209182019101611f24565b518151600019602094850361010090810a820192831692199390931691909117909252949092019687526040805197889003820188208054601f60026001831615909802909501169590950492830182900482028801820190528187529296506060959450869350918401905082828015611fff5780601f10611fd457610100808354040283529160200191611fff565b820191906000526020600020905b815481529060010190602001808311611fe257829003601f168201915b505050505090506060826001018054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156120a05780601f10612075576101008083540402835291602001916120a0565b820191906000526020600020905b81548152906001019060200180831161208357829003601f168201915b50505060028087018054604080516020601f60001961010060018716150201909416959095049283018590048502810185019091528181529596506060959450909250908301828280156121355780601f1061210a57610100808354040283529160200191612135565b820191906000526020600020905b81548152906001019060200180831161211857829003601f168201915b5050505060039590950154939b929a5098505060ff821696506101009091046001600160a01b0316945092505050565b6000805b60055481101561228657826040516020018082805190602001908083835b602083106121a65780518252601f199092019160209182019101612187565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405160208183030381529060405280519060200120600582815481106121ee57fe5b9060005260206000200160405160200180828054600181600116156101000203166002900480156122565780601f10612234576101008083540402835291820191612256565b820191906000526020600020905b815481529060010190602001808311612242575b505091505060405160208183030381529060405280519060200120141561227e579050611eae565b600101612169565b5060001992915050565b6001600160a01b03811660008181526020818152604091829020805460ff19166001908117909155600480549091019055815192835290517fb251eb052afc73ffd02ffe85ad79990a8b3fed60d76dbc2fa2fdd7123dffd9149281900390910190a150565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061233657805160ff1916838001178555612363565b82800160010185558215612363579182015b82811115612363578251825591602001919060010190612348565b5061236f929150612373565b5090565b61238d91905b8082111561236f5760008155600101612379565b9056fe63616e6e6f7420626520617070726f76656420776974686f7574206265696e672061206d656d6265726f6e6c79206f776e6572206f66207468652063657274696669636174650000008a2d6d69682b8d9bb9bd7d7e1a31bfc28ef8131c421695dbbb4537b35bf65d54a265627a7a723158204ab0c005d8341d61b3ee5f58e7c4317c7116132a02db7ed624c57d858eb8894f64736f6c63430005100032";

    public static final String FUNC_ADDCERTIFICATE = "addCertificate";

    public static final String FUNC_APPROVALS = "approvals";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_GETCERTIFICATE = "getCertificate";

    public static final String FUNC_GETVISIBILITYFROMMEMBER = "getVisibilityFromMember";

    public static final String FUNC_LISTTIN = "listTIN";

    public static final String FUNC_MEMBERS = "members";

    public static final String FUNC_RETIRE = "retire";

    public static final String FUNC_SETVISIBILITYTOMEMBER = "setVisibilityToMember";

    public static final String FUNC_TOTALMEMBERS = "totalMembers";

    public static final String FUNC_UPDATECERTIFICATEDATA = "updateCertificateData";

    public static final String FUNC_UPDATECERTIFICATEOBSERVATION = "updateCertificateObservation";

    public static final String FUNC_UPDATECERTIFICATESTATE = "updateCertificateState";

    public static final String FUNC_UPDATECERTIFICATEVISIBLE = "updateCertificateVisible";

    public static final String FUNC_VISIBILITY = "visibility";

    public static final String FUNC_VOTED = "voted";

    public static final Event CERTIFICATEADDED_EVENT = new Event("CertificateAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event CERTIFICATEMODIFIED_EVENT = new Event("CertificateModified", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event MEMBERADDED_EVENT = new Event("MemberAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event MEMBERAPPROVED_EVENT = new Event("MemberApproved", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
    ;

    public static final Event MEMBERRETIRED_EVENT = new Event("MemberRetired", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, CertificateAddedEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, CertificateModifiedEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, MemberAddedEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, MemberApprovedEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, MemberRetiredEventResponse>() {
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
        return web3j.ethLogFlowable(filter).map(new Function<Log, VisibilitySettedEventResponse>() {
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

    public RemoteFunctionCall<TransactionReceipt> addCertificate(String TIN, String certificate, String state, String observation, Boolean visible) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_ADDCERTIFICATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(certificate), 
                new org.web3j.abi.datatypes.Utf8String(state), 
                new org.web3j.abi.datatypes.Utf8String(observation), 
                new org.web3j.abi.datatypes.Bool(visible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> approvals(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_APPROVALS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String newMember) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, newMember)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Tuple5<String, String, String, Boolean, String>> getCertificate(String TIN) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETCERTIFICATE, 
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

    public RemoteFunctionCall<Boolean> getVisibilityFromMember(String member) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_GETVISIBILITYFROMMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, member)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> listTIN(BigInteger param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LISTTIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> members(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_MEMBERS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<TransactionReceipt> retire() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RETIRE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> setVisibilityToMember(String member, Boolean visible) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETVISIBILITYTOMEMBER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, member), 
                new org.web3j.abi.datatypes.Bool(visible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalMembers() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALMEMBERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateData(String TIN, String data) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATEDATA, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateObservation(String TIN, String observation) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATEOBSERVATION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(observation)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateState(String TIN, String state) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATESTATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Utf8String(state)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> updateCertificateVisible(String TIN, Boolean visible) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_UPDATECERTIFICATEVISIBLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(TIN), 
                new org.web3j.abi.datatypes.Bool(visible)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> visibility(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VISIBILITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> voted(String param0, String param1) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_VOTED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, param0), 
                new org.web3j.abi.datatypes.Address(160, param1)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
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

    public static class CertificateAddedEventResponse extends BaseEventResponse {
        public String owner;

        public String TIN;
    }

    public static class CertificateModifiedEventResponse extends BaseEventResponse {
        public String owner;

        public String TIN;

        public String key;
    }

    public static class MemberAddedEventResponse extends BaseEventResponse {
        public String newMember;
    }

    public static class MemberApprovedEventResponse extends BaseEventResponse {
        public String candidate;
    }

    public static class MemberRetiredEventResponse extends BaseEventResponse {
        public String member;
    }

    public static class VisibilitySettedEventResponse extends BaseEventResponse {
        public String origin;

        public String member;

        public Boolean visible;
    }
}
