pragma solidity ^0.4.24;

interface Proxy {
    function forward(address destination, uint value, bytes data) external;
}

interface IdentityManager {
    function forwardTo(Proxy identity, address destination, uint value, bytes data) external;
}

contract AEOManager {
    
    // ROLES
    string ROLE_ADMINISTRATOR = "ROLE_1"; // Manage roles
    string ROLE_REGISTRATOR = "ROLE_2"; // Register AEO in the contract
    string ROLE_APPROVER = "ROLE_3"; // Approve and revoke AEO. Triggers a transaction in the Verification registry
    
    // Events
    event RegisteredAEO(bytes32 hash, address user);
    event ApprovedAEO(bytes32 hash, address user);
    event RevokedAEO(bytes32 hash, address user);
    event RoleAdded(address user, string role);
    event RoleRemoved(address user, string role);
    
    // user => role => bool
    mapping(address => mapping(string => bool)) private usersRoles;
    // hash AEO => bool
    mapping(bytes32 => bool) public registryAEO;
    address private aduana;
    
    modifier onlyRole(string role) {
        require(usersRoles[msg.sender][role], "invalid role for user");
        _;
    }
    
    /**@dev Constructor. Set the Aduana address to use and a first administrator
     * @param proxy Aduana proxy address
     * @param firstAdmin Address of the firs administrator
    */
    constructor(address proxy, address firstAdmin) public {
        aduana = proxy;
        usersRoles[firstAdmin][ROLE_ADMINISTRATOR] = true;
    }
    
    /**@dev Assign a role to a user
     * @param user User to assign role
     * @param role Role to assign
    */
    function addRole(address user, string role) public onlyRole(ROLE_ADMINISTRATOR) {
        require(!usersRoles[user][role], "role already added");
        usersRoles[user][role] = true;
        emit RoleAdded(user, role);
    }
    
    /**@dev Validate if a user has a given role
     * @param user User to validate role
     * @param role Role to validate
    */
    function hasRole(address user, string role) public view returns(bool) {
        return usersRoles[user][role];
    }
    
    /**@dev Remove a role to a user
     * @param user User to remove role
     * @param role Role to remove
    */
    function removeRole(address user, string role) public onlyRole(ROLE_ADMINISTRATOR) {
        require(usersRoles[user][role], "role already removed");
        usersRoles[user][role] = false;
        emit RoleRemoved(user, role);
    }
    
    /**@dev Register a certificate hash in the contract
     * @param hash Certificate data hash (OEA).
    */
    function registerAEO(bytes32 hash) public onlyRole(ROLE_REGISTRATOR) {
        require(!registryAEO[hash], "AEO already registered");
        registryAEO[hash] = true;
        emit RegisteredAEO(hash, msg.sender);
    }
    
    /**@dev Approve a Certificate. Generate an accreditation in the verification registry
     * @param hash Certificate data hash (OEA).
     * @param validDays Number of days the certificate will be valid.
     * @param identityManager Contract address where Aduana identity is registered.
     * @param verificationRegistry Contract address where verifications are made.
    */
    function approveAEO(bytes32 hash, uint validDays, IdentityManager identityManager, address verificationRegistry) public onlyRole(ROLE_APPROVER) {
        require(registryAEO[hash], "AEO not registered");
        identityManager.forwardTo(Proxy(aduana), verificationRegistry, 0, abi.encodeWithSignature("verify(bytes32,uint256)", hash, validDays));
        emit ApprovedAEO(hash, msg.sender);
    }
    
    /**@dev Revoke a Certificate. Generate an accreditation to revoke in the verification registry
     * @param hash Certificate data hash (OEA).
     * @param identityManager Contract address where Aduana identity is registered.
     * @param verificationRegistry Contract address where verifications are made.
    */
    function revokeAEO(bytes32 hash, IdentityManager identityManager, address verificationRegistry) public onlyRole(ROLE_APPROVER) {
        identityManager.forwardTo(Proxy(aduana), verificationRegistry, 0, abi.encodeWithSignature("revoke(bytes32)", hash));
        emit RevokedAEO(hash, msg.sender);
    }
    
}