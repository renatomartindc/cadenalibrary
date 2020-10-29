pragma solidity ^0.4.24;

contract VerificationRegistry {
  event Verified(bytes32 indexed hash, address by, uint256 date, uint256 expDate);
  event Revoked(bytes32 indexed hash, address by, uint256 date);

  struct Verification {
    // Verification date (0 means "not verified")
    uint iat;
    // Verification expiration date (0 means "never expires")
    uint exp;
  }

  // hash => attester => Verification
  mapping (bytes32 => mapping (address => Verification)) public verifications;

  /**@dev This method is used to accredit a data.
    * @param data Data to accredit.
    * @param validDays The number of days the data will be accredited.
  */
  function verify(bytes32 hash, uint validDays) public {
    uint exp = validDays > 0 ? now + validDays * 1 days : 0;
    verifications[hash][msg.sender] = Verification(now, exp);
    emit Verified(hash, msg.sender, now, exp);
  }

  /**@dev This method is used to revoke a data.
    * @param data Data to revoke.
    * @return Transaction hash.
  */
  function revoke(bytes32 hash) public {
    verifications[hash][msg.sender] = Verification(0, now);
    emit Revoked(hash, msg.sender, now);
  }
}
