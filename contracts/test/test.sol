pragma solidity ^0.4.24;

contract Test {
    
    string public test;
    int public integer = 3;
    
    function setTest(string param) public {
        test = param;
    }
    
    function getInteger() public view returns (uint) {
        return uint(integer);
    }
    
}