pragma solidity ^0.4.18;
/// @title Interface for contracts conforming to ERC-721: Non-Fungible Tokens
/// @author Dieter Shirley <dete@axiomzen.co> (https://github.com/dete)
contract ERC721 {
    // Required methods
    function totalSupply() public view returns (uint256 total);
    function balanceOf(address _owner) public view returns (uint256 balance);
    function ownerOf(uint256 _tokenId) external view returns (address owner);
    function approve(address _to, uint256 _tokenId) external;
    function transfer(address _to, uint256 _tokenId) external;
    function transferFrom(address _from, address _to, uint256 _tokenId) external;

    // Events
    event Transfer(address from, address to, uint256 tokenId);
    event Approval(address owner, address approved, uint256 tokenId);

    // Optional
    // function name() public view returns (string name);
    // function symbol() public view returns (string symbol);
    // function tokensOfOwner(address _owner) external view returns (uint256[] tokenIds);
    // function tokenMetadata(uint256 _tokenId, string _preferredTransport) public view returns (string infoUrl);

    // ERC-165 Compatibility (https://github.com/ethereum/EIPs/issues/165)
    function supportsInterface(bytes4 _interfaceID) external view returns (bool);
}


/**
 * @title SafeMath
 * @dev Math operations with safety checks that throw on error
 */
library SafeMath {

  /**
  * @dev Multiplies two numbers, throws on overflow.
  */
  function mul(uint256 a, uint256 b) internal pure returns (uint256) {
    if (a == 0) {
      return 0;
    }
    uint256 c = a * b;
    assert(c / a == b);
    return c;
  }

  /**
  * @dev Integer division of two numbers, truncating the quotient.
  */
  function div(uint256 a, uint256 b) internal pure returns (uint256) {
    // assert(b > 0); // Solidity automatically throws when dividing by 0
    uint256 c = a / b;
    // assert(a == b * c + a % b); // There is no case in which this doesn't hold
    return c;
  }

  /**
  * @dev Subtracts two numbers, throws on overflow (i.e. if subtrahend is greater than minuend).
  */
  function sub(uint256 a, uint256 b) internal pure returns (uint256) {
    assert(b <= a);
    return a - b;
  }

  /**
  * @dev Adds two numbers, throws on overflow.
  */
  function add(uint256 a, uint256 b) internal pure returns (uint256) {
    uint256 c = a + b;
    assert(c >= a);
    return c;
  }
}


/**
 * @title SafeMath32
 * @dev SafeMath library implemented for uint32
 */
library SafeMath32 {

  function mul(uint32 a, uint32 b) internal pure returns (uint32) {
    if (a == 0) {
      return 0;
    }
    uint32 c = a * b;
    assert(c / a == b);
    return c;
  }

  function div(uint32 a, uint32 b) internal pure returns (uint32) {
    // assert(b > 0); // Solidity automatically throws when dividing by 0
    uint32 c = a / b;
    // assert(a == b * c + a % b); // There is no case in which this doesn't hold
    return c;
  }

  function sub(uint32 a, uint32 b) internal pure returns (uint32) {
    assert(b <= a);
    return a - b;
  }

  function add(uint32 a, uint32 b) internal pure returns (uint32) {
    uint32 c = a + b;
    assert(c >= a);
    return c;
  }
}

/**
 * @title SafeMath16
 * @dev SafeMath library implemented for uint16
 */
library SafeMath16 {

  function mul(uint16 a, uint16 b) internal pure returns (uint16) {
    if (a == 0) {
      return 0;
    }
    uint16 c = a * b;
    assert(c / a == b);
    return c;
  }

  function div(uint16 a, uint16 b) internal pure returns (uint16) {
    // assert(b > 0); // Solidity automatically throws when dividing by 0
    uint16 c = a / b;
    // assert(a == b * c + a % b); // There is no case in which this doesn't hold
    return c;
  }

  function sub(uint16 a, uint16 b) internal pure returns (uint16) {
    assert(b <= a);
    return a - b;
  }

  function add(uint16 a, uint16 b) internal pure returns (uint16) {
    uint16 c = a + b;
    assert(c >= a);
    return c;
  }
}


/**
 * @title Ownable
 * @dev The Ownable contract has an owner address, and provides basic authorization control
 * functions, this simplifies the implementation of "user permissions".
 */
contract Ownable {
  address public owner;

  event OwnershipTransferred(address indexed previousOwner, address indexed newOwner);

  /**
   * @dev The Ownable constructor sets the original `owner` of the contract to the sender
   * account.
   */
  function Ownable() public {
    owner = msg.sender;
  }


  /**
   * @dev Throws if called by any account other than the owner.
   */
  modifier onlyOwner() {
    require(msg.sender == owner);
    _;
  }


  /**
   * @dev Allows the current owner to transfer control of the contract to a newOwner.
   * @param newOwner The address to transfer ownership to.
   */
  function transferOwnership(address newOwner) public onlyOwner {
    require(newOwner != address(0));
    OwnershipTransferred(owner, newOwner);
    owner = newOwner;
  }

}



contract CharacterBase is Ownable{
    
    using SafeMath for uint256;
    using SafeMath32 for uint32;
    using SafeMath16 for uint16;
    
    
    event birth(address owner, uint256 newCharId, uint256 parent1Id, uint256 parent2Id);
    struct Character{
        //properties
        uint256 parent1Id;
        uint256 parent2Id;
        uint16 generation;
    
        //stats
        uint32 hp;
        uint32 maxHp;
        uint16 str;
        uint16 intel;
        uint16 stam;
        uint16 spd;
        uint16 dex;
        uint16 maxStr;
        uint16 maxIntel;
        uint16 maxStam;
        uint16 maxSpd;
        uint16 maxDex;
        uint16 color1;
        uint16 color2;
        string name;
    
    }

    Character[] Characters;
    
    mapping (uint256 => address) charToOwner;
    mapping (address => uint256) ownerCharCount;
    event Transfer(address from, address to, uint256 tokenId);
    
    function _transfer(address _from, address _to, uint256 _tokenId) public {
        ownerCharCount[_to] = ownerCharCount[_to].add(1);
        charToOwner[_tokenId] = _to;
        if (_from != address(0)) {
            ownerCharCount[msg.sender] = ownerCharCount[msg.sender].sub(1);
        }
    
    Transfer(_from, _to, _tokenId);
    }
    function max(uint a, uint b) private pure returns (uint16) {
        return uint16(a > b ? a : b);
    }

    function _createCharacter(
        uint256 _parent1Id, 
        uint256 _parent2Id,
        address _owner) internal returns(uint)
        {
            
            Character memory _character = Character({
                parent1Id: _parent1Id,
                parent2Id: _parent2Id,
                generation: uint16(max(Characters[_parent1Id].generation, Characters[_parent2Id].generation) + 1),
                hp: uint32(1),
                maxHP: uint32(Characters[_parent1Id].hp + Characters[_parent2Id].hp),
                str: uint16(1),
                intel: uint16(1),
                stam: uint16(1),
                spd: uint16(1),
                dex: uint16(1),
                maxStr: uint16(Characters[_parent1Id].str + Characters[_parent2Id].str),
                maxIntel: uint16(Characters[_parent1Id].intel + Characters[_parent2Id].intel),
                maxStam: uint16(Characters[_parent1Id].stam + Characters[_parent2Id].stam),
                maxSpd: uint16(Characters[_parent1Id].spd + Characters[_parent2Id].spd),
                maxDex: uint16(Characters[_parent1Id].dex + Characters[_parent2Id].dex),
                color1: uint16((Characters[_parent1Id].color1 + Characters[_parent2Id].color2) / 2),
                color2: uint16((Characters[_parent1Id].color2 + Characters[_parent2Id].color1) / 2),
                name: "name"
            });
            uint256 newCharId = Characters.push(_character) - 1;
            birth(_owner, newCharId, _parent1Id, _parent2Id);
            _transfer(0, _owner, newCharId);
            ownerCharCount[_owner].add(1);
            return newCharId;
        }
    

}

contract CharacterOwnership is CharacterBase, ERC721 {
    using SafeMath for uint256;
    mapping (uint => address) charApprovals;
    
    function _owns(address _requester, uint256 _tokenId) public returns(bool){
        return(charToOwner[_tokenId] == _requester);
    }
    
    function balanceOf(address _owner) public view returns (uint256 _balance) {
        return ownerCharCount[_owner];
    }
    
    function ownerOf(uint256 _tokenId) external view returns (address _owner) {
        return charToOwner[_tokenId];
    }
    
    function transfer(address _to, uint256 _tokenId) external{
        require (_owns(msg.sender, _tokenId));
        _transfer(msg.sender, _to, _tokenId);
    }
    
    function approve(address _to, uint256 _tokenId) external{
        require (_owns(msg.sender, _tokenId));
        charApprovals[_tokenId] = _to;
        Approval(msg.sender, _to, _tokenId);
    }
    
    function takeOwnership(uint256 _tokenId) public {
        require (charApprovals[_tokenId] == msg.sender);
        address owner = charToOwner[_tokenId];
        _transfer(owner, msg.sender, _tokenId);
    }
    
    
    
  
}

contract CharacterCombination is CharacterOwnership{
  function combineChars(uint256 _parent1Id, uint256 _parent2Id){
    require (charToOwner[_parent1Id] == msg.sender && charToOwner[_parent2Id] == msg.sender);
    uint256 newCharId = _createCharacter(_parent1Id, _parent2Id, msg.sender);

  }
}


