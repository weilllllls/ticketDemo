//var myContractAddress = "0xffABD8aF8239dF8D923e55D089618FaA3afa752E";
var myContractAddress = "0x499F7dD043f0991D8cf86398ba9E89F6755dBd9b";

var abi =[
	{
		"constant": false,
		"inputs": [
			{
				"name": "buyer_id",
				"type": "string"
			},
			{
				"name": "buyer_name",
				"type": "string"
			},
			{
				"name": "buyer_bank_num",
				"type": "string"
			},
			{
				"name": "buyer_address",
				"type": "string"
			},
			{
				"name": "buyer_balance",
				"type": "uint256"
			}
		],
		"name": "addBuyer",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "paper_id",
				"type": "string"
			},
			{
				"name": "idNum",
				"type": "string"
			},
			{
				"name": "buyer_name",
				"type": "string"
			},
			{
				"name": "seller_name",
				"type": "string"
			},
			{
				"name": "begin_date",
				"type": "string"
			},
			{
				"name": "end_date",
				"type": "string"
			},
			{
				"name": "state",
				"type": "string"
			}
		],
		"name": "addCommercialPaper",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "seller_id",
				"type": "string"
			},
			{
				"name": "seller_name",
				"type": "string"
			},
			{
				"name": "seller_bank_num",
				"type": "string"
			},
			{
				"name": "seller_address",
				"type": "string"
			},
			{
				"name": "seller_balance",
				"type": "uint256"
			}
		],
		"name": "addSeller",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "buyer_id",
				"type": "string"
			},
			{
				"name": "buyer_name",
				"type": "string"
			},
			{
				"name": "buyer_bank_num",
				"type": "string"
			},
			{
				"name": "buyer_address",
				"type": "string"
			},
			{
				"name": "buyer_balance",
				"type": "uint256"
			}
		],
		"name": "changeBuyerInfo",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "paper_id",
				"type": "string"
			},
			{
				"name": "buyer_name",
				"type": "string"
			},
			{
				"name": "seller_name",
				"type": "string"
			}
		],
		"name": "changeCommercialPaperInfo",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "seller_id",
				"type": "string"
			},
			{
				"name": "seller_name",
				"type": "string"
			},
			{
				"name": "seller_bank_num",
				"type": "string"
			},
			{
				"name": "seller_address",
				"type": "string"
			},
			{
				"name": "seller_balance",
				"type": "uint256"
			}
		],
		"name": "changeSellerInfo",
		"outputs": [],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "buyer_id",
				"type": "string"
			},
			{
				"name": "num",
				"type": "uint256"
			}
		],
		"name": "increaseBuyerBalance",
		"outputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": false,
		"inputs": [
			{
				"name": "seller_id",
				"type": "string"
			},
			{
				"name": "num",
				"type": "uint256"
			}
		],
		"name": "increaseSellerBalance",
		"outputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "buyer_id",
				"type": "string"
			}
		],
		"name": "getBuyerInfo",
		"outputs": [
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "paper_id",
				"type": "string"
			}
		],
		"name": "getCommercialPaperInfo",
		"outputs": [
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [
			{
				"name": "seller_id",
				"type": "string"
			}
		],
		"name": "getSellerInfo",
		"outputs": [
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "string"
			},
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	},
	{
		"constant": true,
		"inputs": [],
		"name": "test",
		"outputs": [
			{
				"name": "",
				"type": "uint256"
			}
		],
		"payable": false,
		"stateMutability": "view",
		"type": "function"
	}
]