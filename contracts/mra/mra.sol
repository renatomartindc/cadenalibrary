pragma solidity ^0.4.26;

// Version 1.1.0

contract MRA {
    
    // Members registered in the contract
    mapping (address => bool) public members;
    // Member origin => Member destination => Visibility
    mapping (address => mapping (address => bool)) public visibility;
    // Candidate => Voter => voted
    mapping (address => mapping (address => bool)) public voted;
    // Candidate => total votes
    mapping (address => uint) public approvals;
    // Total members registered in the contract
    uint public totalMembers = 0;
    // List of TIN registered
    string[] public listTIN;
    // Certificate information
    struct Certificate {
        string data;
        string state;
        string observation;
        bool visible;
        address owner;
    }
    // TIN => Certificate (OEA) => Contains JSON string in data key
    mapping (string => Certificate) private certificates;
    
    event MemberApproved(address candidate);
    event MemberAdded(address newMember);
    event MemberRetired(address member);
    event CertificateAdded(string TIN, address indexed owner);
    event CertificateModified(string TIN, string key, address indexed owner);
    event visibilitySetted(address origin, address member, bool visible);
    
    modifier onlyMember() {
        require(members[msg.sender], "only member");
        _;
    }
    
    modifier onlyOwnerCertificate(string TIN) {
        address owner = certificates[TIN].owner;
        require(owner != 0x0, "TIN does not exist");
        require(owner == msg.sender, "only owner of the certificate");
        _;
    }
    
    /**@dev Constructor. Initialize the contract and add as the first member the address of the person making the transaction
    */
    constructor() public {
        addMember(msg.sender);
    }
    
    /**@dev Enables/disables the sharing of certificates for a member
     * @param member Member which is going to indicate visibility
     * @param visible State to be assigned to the member
    */
    function setVisibilityToMember(address member, bool visible) public onlyMember {
        require(members[msg.sender], "member does not exist");
        visibility[msg.sender][member] = visible;
        emit visibilitySetted(msg.sender, member, visible);
    }
    
    /**@dev Returns the visibility status that a member has for certificate sharing
     * @param member Member which is going to get the visibility
    */
    function getVisibilityFromMember(address member) public view onlyMember returns(bool) {
        return visibility[member][msg.sender];
    }
    
    /**@dev Approves the inclusion of a new member in the contract
     * @param newMember Member to add
    */
    function approve(address newMember) public {
        require(!voted[newMember][msg.sender], "already voted");
        require(newMember == msg.sender || members[msg.sender], "cannot be approved without being a member");
        voted[newMember][msg.sender] = true;
        approvals[newMember] += 1;
        emit MemberApproved(newMember);
        if (approvals[newMember] == (totalMembers + 1)) {
            addMember(newMember);
        }
    }
    
    /**@dev Returns the information of a certificate
     * @param TIN Certificate identifier
    */
    function getCertificate(string TIN) public view onlyMember returns(string, string, string, bool, address) {
        Certificate storage cert = certificates[TIN];
        string memory data = cert.data;
        string memory state = cert.state;
        string memory observation = cert.observation;
        address owner = cert.owner;
        bool visible = cert.visible;
        return (data, state, observation, visible, owner);
    }
    
    /**@dev Add a new certificate to share among the members
     * @param TIN Certificate identifier
     * @param certificate Certificate credential (JSON string)
     * @param state Current status of the certificate
     * @param observation Observation of the certificate
     * @param visible Determine if other members can see the certificate
    */
    function addCertificate(string TIN, string certificate, string state, string observation, bool visible) public onlyMember {
        certificates[TIN].data = certificate;
        certificates[TIN].state = state;
        certificates[TIN].observation = observation;
        certificates[TIN].visible = visible;
        certificates[TIN].owner = msg.sender;
        if (existsCertificate(TIN) == -1) {
            listTIN.push(TIN);
            emit CertificateAdded(TIN, msg.sender);
        } else {
            emit CertificateModified(TIN, "", msg.sender);
        }
    }
    
    /**@dev Modify the certificate credential
     * @param TIN Certificate identifier
     * @param data New credential to modify (JSON string)
    */
    function updateCertificateData(string TIN, string data) public onlyOwnerCertificate(TIN) {
        require(existsCertificate(TIN) >= 0, "TIN does not exist");
        Certificate storage cert = certificates[TIN];
        cert.data = data;
        emit CertificateModified(TIN, "data", msg.sender);
    }
    
    /**@dev Modify the certificate state
     * @param TIN Certificate identifier
     * @param state New state to modify
    */
    function updateCertificateState(string TIN, string state) public onlyOwnerCertificate(TIN) {
        require(existsCertificate(TIN) >= 0, "TIN does not exist");
        Certificate storage cert = certificates[TIN];
        cert.state = state;
        emit CertificateModified(TIN, "state", msg.sender);
    }
    
    /**@dev Modify the certificate observation
     * @param TIN Certificate identifier
     * @param observation New observation to modify
    */
    function updateCertificateObservation(string TIN, string observation) public onlyOwnerCertificate(TIN) {
        require(existsCertificate(TIN) >= 0, "TIN does not exist");
        Certificate storage cert = certificates[TIN];
        cert.observation = observation;
        emit CertificateModified(TIN, "observation", msg.sender);
    }
    
    /**@dev Modify the certificate visibility
     * @param TIN Certificate identifier
     * @param visible New visibility to set
    */
    function updateCertificateVisible(string TIN, bool visible) public onlyOwnerCertificate(TIN) {
        require(existsCertificate(TIN) >= 0, "TIN does not exist");
        Certificate storage cert = certificates[TIN];
        cert.visible = visible;
        emit CertificateModified(TIN, "visible", msg.sender);
    }
    
    /**@dev Add a new member to the contract
     * @param newMember Member to add
    */
    function addMember(address newMember) private {
        members[newMember] = true;
        totalMembers++;
        emit MemberAdded(newMember);
    }
    
    /**@dev Verify that a TIN is registered in the contract
     * @param TIN Certificate identifier
    */
    function existsCertificate(string TIN) private view returns (int) {
        for (uint i = 0; i < listTIN.length; i++) {
            if (keccak256(abi.encodePacked(listTIN[i])) == keccak256(abi.encodePacked(TIN))) {
                return int(i);
                break;
            }
        }
        return -1;
    }
    
    /**@dev Unsubscribe from the contract
    */
    function retire() public onlyMember {
        members[msg.sender] = false;
        totalMembers--;
        emit MemberRetired(msg.sender);
    }
    
}