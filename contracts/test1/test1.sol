pragma solidity ^0.4.24;

contract Test {
    
    event TestEvent(string param);
    event TestEvent2(string indexed param1, string param2);
    
    function emitEvent(string param) public {
        emit TestEvent(param);
    }
    
    function emitEvent2(string param1, string param2) public {
        emit TestEvent2(param1, param2);
    }
}